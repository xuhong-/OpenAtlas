package com.taobao.android.task;

import com.taobao.android.task.Coordinator.TaggedRunnable;

import android.os.MessageQueue.IdleHandler;

final class a implements IdleHandler {
    a() {
    }

    public boolean queueIdle() {
        TaggedRunnable taggedRunnable = (TaggedRunnable) Coordinator.mIdleTasks
                .poll();
        if (taggedRunnable == null) {
            return false;
        }
        Coordinator.postTask(taggedRunnable);
        return !Coordinator.mIdleTasks.isEmpty();
    }
}