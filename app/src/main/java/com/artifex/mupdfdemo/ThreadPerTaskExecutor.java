package com.artifex.mupdfdemo;

import java.util.concurrent.Executor;

public class ThreadPerTaskExecutor implements Executor {
    public void execute(Runnable runnable) {
        new Thread(runnable).start();
    }
}
