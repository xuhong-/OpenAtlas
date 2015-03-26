package com.taobao.android.task;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/* compiled from: Coordinator.java */
final class b implements ThreadFactory {
    private final AtomicInteger a;

    b() {
        this.a = new AtomicInteger(1);
    }

    public Thread newThread(Runnable runnable) {
        return new Thread(runnable, "CoordTask #" + this.a.getAndIncrement());
    }
}
