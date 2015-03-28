/**
 *  OpenAtlasForAndroid Project
The MIT License (MIT) Copyright (OpenAtlasForAndroid) 2015 Bunny Blue,achellies

Permission is hereby granted, free of charge, to any person obtaining mApp copy of this software
and associated documentation files (the "Software"), to deal in the Software 
without restriction, including without limitation the rights to use, copy, modify, 
merge, publish, distribute, sublicense, and/or sell copies of the Software, and to 
permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies 
or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, 
INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR
PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE 
FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE,
ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
@author BunnyBlue
 * **/
package com.taobao.android.task;

import java.io.File;
import java.lang.Thread.State;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Iterator;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.LockSupport;
import java.util.regex.Pattern;

import android.os.AsyncTask;
import android.os.Build.VERSION;

public class SaturativeExecutor extends ThreadPoolExecutor {
    private static final boolean DEBUG = false;
    private static final int KEEP_ALIVE = 1;
    private static final int MAX_POOL_SIZE = 128;
    private static final int MIN_THREADS_BEFORE_SATURATION = 3;
    static final Pattern PATTERN_CPU_ENTRIES;
    private static final int QUEUE_CAPACITY = 1024;
    static final String TAG = "SatuExec";
    private static SaturationAwareBlockingQueue<Runnable> mQueue;
    private static final HashSet<Thread> mThreads;
    private static final ThreadFactory sThreadFactory;

    protected static class CountedTask implements Runnable {
        static final AtomicInteger mNumRunning;
        Runnable mRunnable;

        public CountedTask(Runnable runnable) {
            this.mRunnable = runnable;
        }

        @Override
		public void run() {
            mNumRunning.incrementAndGet();
            try {
                this.mRunnable.run();
            } finally {
                mNumRunning.decrementAndGet();
            }
        }

        static {
            mNumRunning = new AtomicInteger();
        }
    }

    protected static class SaturationAwareBlockingQueue<T> extends
            LinkedBlockingQueue<T> {
        private static final long serialVersionUID = 1;
        private SaturativeExecutor mExecutor;

        public SaturationAwareBlockingQueue(int i) {
            super(i);
        }

        void setExecutor(SaturativeExecutor saturativeExecutor) {
            this.mExecutor = saturativeExecutor;
        }

        @Override
		public boolean add(T t) {
            if (!this.mExecutor.isReallyUnsaturated()) {
                return super.add(t);
            }
            throw new IllegalStateException("Unsaturated");
        }

        @Override
		public boolean offer(T t) {
            return this.mExecutor.isReallyUnsaturated() ? DEBUG : super
                    .offer(t);
        }

        @Override
		public void put(T t) {
            throw new UnsupportedOperationException();
        }

        @Override
		public boolean offer(T t, long j, TimeUnit timeUnit) {
            throw new UnsupportedOperationException();
        }
    }

    static {
        PATTERN_CPU_ENTRIES = Pattern.compile("cpu[0-9]+");
        sThreadFactory = new d();
        mThreads = new HashSet();
    }

    @Override
	public void execute(Runnable runnable) {
        super.execute(new CountedTask(runnable));
    }

    public static final boolean installAsDefaultAsyncTaskExecutor(
            ThreadPoolExecutor threadPoolExecutor) {
        if (VERSION.SDK_INT >= 11) {
            try {
                Field declaredField = AsyncTask.class
                        .getDeclaredField("THREAD_POOL_EXECUTOR");
                declaredField.setAccessible(true);
                declaredField.set(null, threadPoolExecutor);
            } catch (Exception e) {
            }
        }
        try {
            Method method = AsyncTask.class.getMethod("setDefaultExecutor",
                    new Class[] { Executor.class });
            method.setAccessible(true);
            method.invoke(null, new Object[] { threadPoolExecutor });
            return true;
        } catch (Exception e2) {
            Field declaredField;
            try {
                declaredField = AsyncTask.class
                        .getDeclaredField("sDefaultExecutor");
                declaredField.setAccessible(true);
                declaredField.set(null, threadPoolExecutor);
                return true;
            } catch (Exception e3) {
                try {
                    declaredField = AsyncTask.class
                            .getDeclaredField("sExecutor");
                    declaredField.setAccessible(true);
                    declaredField.set(null, threadPoolExecutor);
                    return true;
                } catch (Exception e4) {
                    return false;
                }
            }
        }
    }

    public SaturativeExecutor() {
        this(determineBestMinPoolSize());
    }

    public SaturativeExecutor(int i) {
        super(i, 128, 1, TimeUnit.SECONDS, new SaturationAwareBlockingQueue(
                1024), sThreadFactory, new CallerRunsPolicy());
        TimeUnit timeUnit = TimeUnit.SECONDS;
        // BlockingQueue saturationAwareBlockingQueue = new
        // SaturationAwareBlockingQueue(1024);
        // mQueue = saturationAwareBlockingQueue;
        mQueue = (SaturationAwareBlockingQueue<Runnable>) getQueue();
        ((SaturationAwareBlockingQueue) getQueue()).setExecutor(this);
    }

    protected boolean isReallyUnsaturated() {
        if (isSaturated()) {
            return DEBUG;
        }
        LockSupport.parkNanos(10);
        return !isSaturated() ? true : DEBUG;
    }

    protected boolean isSaturated() {
        if (getPoolSize() <= 3) {
            return DEBUG;
        }
        int corePoolSize = getCorePoolSize();
        int i = CountedTask.mNumRunning.get();
        int size = mThreads.size();
        if (i < corePoolSize || i < size) {
            return true;
        }
        boolean z;
        synchronized (mThreads) {
            Iterator it = mThreads.iterator();
            size = 0;
            while (it.hasNext()) {
                State state = ((Thread) it.next()).getState();
                if (state == State.RUNNABLE || state == State.NEW) {
                    i = size + 1;
                } else {
                    if (state == State.TERMINATED) {
                        it.remove();
                    }
                    i = size;
                }
                size = i;
            }
        }
        if (size >= corePoolSize) {
            z = true;
        } else {
            z = false;
        }
        return z;
    }

    public static void collectThread(Thread thread) {
        synchronized (mThreads) {
            mThreads.add(thread);
        }
    }

    private static int determineBestMinPoolSize() {
        int countCpuCores = countCpuCores();
        return countCpuCores > 0 ? countCpuCores : Runtime.getRuntime()
                .availableProcessors() * 2;
    }

    private static int countCpuCores() {
        try {
            return new File("/sys/devices/system/cpu/").listFiles(new c()).length;
        } catch (Exception e) {
            return 0;
        }
    }

}
