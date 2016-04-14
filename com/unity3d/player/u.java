/*
 * Decompiled with CFR 0_114.
 * 
 * Could not load the following classes:
 *  android.os.Build
 */
package com.unity3d.player;

import android.os.Build;

final class u
implements Thread.UncaughtExceptionHandler {
    private volatile Thread.UncaughtExceptionHandler a;

    u() {
    }

    final synchronized boolean a() {
        Thread.UncaughtExceptionHandler uncaughtExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
        if (uncaughtExceptionHandler == this) {
            return false;
        }
        this.a = uncaughtExceptionHandler;
        Thread.setDefaultUncaughtExceptionHandler(this);
        return true;
    }

    @Override
    public final synchronized void uncaughtException(Thread thread, Throwable throwable) {
        try {
            Error error = new Error(String.format("FATAL EXCEPTION [%s]\n", thread.getName()) + String.format("Unity version     : %s\n", "5.3.1f1") + String.format("Device model      : %s %s\n", Build.MANUFACTURER, Build.MODEL) + String.format("Device fingerprint: %s\n", Build.FINGERPRINT));
            error.setStackTrace(new StackTraceElement[0]);
            error.initCause(throwable);
            this.a.uncaughtException(thread, error);
            return;
        }
        catch (Throwable v0) {
            this.a.uncaughtException(thread, throwable);
            return;
        }
    }
}

