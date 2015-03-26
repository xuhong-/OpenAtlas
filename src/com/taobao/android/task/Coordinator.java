package com.taobao.android.task;

import android.annotation.TargetApi;
import android.os.AsyncTask;
import android.os.Build.VERSION;
import android.os.Debug;
import android.os.Looper;
import android.util.Log;

import com.taobao.android.task.Coordinator.TaggedRunnable;

import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Coordinator {
    private static final String TAG = "Coord";
    private static final Executor mExecutor;
    static final Queue<TaggedRunnable> mIdleTasks;
    private static final BlockingQueue<Runnable> mPoolWorkQueue;

    public static class CoordinatorRejectHandler implements
            RejectedExecutionHandler {
        public void rejectedExecution(Runnable runnable,
                ThreadPoolExecutor threadPoolExecutor) {
            Object[] toArray = mPoolWorkQueue.toArray();
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append('[');
            for (Object obj : toArray) {
                if (obj.getClass().isAnonymousClass()) {
                    stringBuilder.append(getOuterClass(obj));
                    stringBuilder.append(',').append(' ');
                } else {
                    stringBuilder.append(obj.getClass());
                    stringBuilder.append(',').append(' ');
                }
            }
            stringBuilder.append(']');
            throw new RejectedExecutionException("Task " + runnable.toString()
                    + " rejected from " + threadPoolExecutor.toString()
                    + " in " + stringBuilder.toString());
        }

        private Object getOuterClass(Object obj) {
            try {
                Field declaredField = obj.getClass().getDeclaredField("this$0");
                declaredField.setAccessible(true);
                obj = declaredField.get(obj);
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e2) {
                e2.printStackTrace();
            } catch (IllegalArgumentException e3) {
                e3.printStackTrace();
            }
            return obj;
        }
    }

    public static abstract class TaggedRunnable implements Runnable {
        public final String tag;

        public TaggedRunnable(String str) {
            this.tag = str;
        }

        public String toString() {
            return getClass().getName() + "@" + this.tag;
        }
    }

    static class axx extends AsyncTask<Void, Void, Void> {
        private final TaggedRunnable a;

        protected Void doInBackground(Void... params) {
            return a((Void[]) params);
        }

        public axx(TaggedRunnable taggedRunnable) {
            this.a = taggedRunnable;
        }

        protected Void a(Void... voidArr) {
            Coordinator.runWithTiming(this.a);
            return null;
        }

        public String toString() {
            return getClass().getSimpleName() + "@" + this.a;
        }

    }

    @TargetApi(11)
    public static void postTask(TaggedRunnable taggedRunnable) {
        axx aVar = new axx(taggedRunnable);
        if (VERSION.SDK_INT < 11) {
            aVar.execute(new Void[0]);
        } else {
            aVar.executeOnExecutor(mExecutor, new Void[0]);
        }
    }

    public static void postTasks(TaggedRunnable... taggedRunnableArr) {
        for (TaggedRunnable taggedRunnable : taggedRunnableArr) {
            if (taggedRunnable != null) {
                postTask(taggedRunnable);
            }
        }
    }

    public static void postIdleTask(TaggedRunnable taggedRunnable) {
        mIdleTasks.add(taggedRunnable);
    }

    public static void runTask(TaggedRunnable taggedRunnable) {
        runWithTiming(taggedRunnable);
    }

    public static void runTasks(TaggedRunnable... taggedRunnableArr) {
        for (TaggedRunnable taggedRunnable : taggedRunnableArr) {
            if (taggedRunnable != null) {
                runWithTiming(taggedRunnable);
            }
        }
    }

    public static void scheduleIdleTasks() {
        Looper.myQueue().addIdleHandler(new a());
    }

    private static void runWithTiming(TaggedRunnable taggedRunnable) {
        long nanoTime;
        long j = 0;
        boolean isDebug = true;
        if (isDebug) {
            nanoTime = System.nanoTime();
            j = Debug.threadCpuTimeNanos();
        } else {
            nanoTime = 0;
        }
        try {
            taggedRunnable.run();
            if (isDebug) {
                System.out.println("Timing - "
                        + Thread.currentThread().getName() + " "
                        + taggedRunnable.tag + ": "
                        + ((Debug.threadCpuTimeNanos() - j) / 1000000)
                        + "ms (cpu) / "
                        + ((System.nanoTime() - nanoTime) / 1000000)
                        + "ms (real)");

            }
        } catch (RuntimeException e) {
            System.out.println("Exception in " + taggedRunnable.tag);
            ;
            if (isDebug) {
                System.out.println("Timing - "
                        + Thread.currentThread().getName() + " "
                        + taggedRunnable.tag + " (failed): "
                        + ((Debug.threadCpuTimeNanos() - j) / 1000000)
                        + "ms (cpu) / "
                        + ((System.nanoTime() - nanoTime) / 1000000)
                        + "ms (real)");
            }
        } catch (Throwable th) {
            Throwable th2 = th;
            int i = 1;
            Throwable th3 = th2;
            if (isDebug) {
                System.out.println("Timing - "
                        + Thread.currentThread().getName() + " "
                        + taggedRunnable.tag + (i != 0 ? " (failed): " : ": ")
                        + ((Debug.threadCpuTimeNanos() - j) / 1000000)
                        + "ms (cpu) / "
                        + ((System.nanoTime() - nanoTime) / 1000000)
                        + "ms (real)");
                ;
            }
        }
    }

    @TargetApi(11)
    static Executor getDefaultAsyncTaskExecutor() {
        if (VERSION.SDK_INT >= 11) {
            return AsyncTask.SERIAL_EXECUTOR;
        }
        try {
            Field declaredField = AsyncTask.class.getDeclaredField("sExecutor");
            declaredField.setAccessible(true);
            return (Executor) declaredField.get(null);
        } catch (Exception e) {
            return null;
        }
    }

    static Executor getCurrentExecutor() {
        return mExecutor;
    }

    static {
        mIdleTasks = new LinkedList();
        mPoolWorkQueue = new LinkedBlockingQueue(128);
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(8, 16,
                1, TimeUnit.SECONDS, mPoolWorkQueue, new b(),
                new CoordinatorRejectHandler());
        mExecutor = (Executor) threadPoolExecutor;
        SaturativeExecutor
                .installAsDefaultAsyncTaskExecutor(threadPoolExecutor);
    }

    @TargetApi(11)
    private static ThreadPoolExecutor getDefaultThreadPoolExecutor() {
        try {
            return (ThreadPoolExecutor) AsyncTask.THREAD_POOL_EXECUTOR;
        } catch (Throwable th) {
            Log.e(TAG,
                    "Unexpected failure to get default ThreadPoolExecutor of AsyncTask.",
                    th);
            return null;
        }
    }
}
