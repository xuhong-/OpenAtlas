package com.taobao.android.task;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/* compiled from: SaturativeExecutor.java */
final class d implements ThreadFactory {
    private final AtomicInteger a;

    d() {
        this.a = new AtomicInteger(1);
    }

    public Thread newThread(Runnable runnable) {
        Thread thread = new Thread(runnable, "SaturativeThread #"
                + this.a.getAndIncrement());
        SaturativeExecutor.collectThread(thread);
        return thread;
    }
}
