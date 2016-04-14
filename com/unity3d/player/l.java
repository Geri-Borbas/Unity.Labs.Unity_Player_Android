/*
 * Decompiled with CFR 0_114.
 * 
 * Could not load the following classes:
 *  android.view.Choreographer
 *  android.view.Choreographer$FrameCallback
 */
package com.unity3d.player;

import android.view.Choreographer;
import com.unity3d.player.UnityPlayer;
import com.unity3d.player.h;
import com.unity3d.player.m;
import com.unity3d.player.v;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public final class l
implements h {
    private Choreographer a = null;
    private long b = 0;
    private Choreographer.FrameCallback c;
    private Lock d = new ReentrantLock();

    @Override
    public final void a(final UnityPlayer unityPlayer) {
        this.d.lock();
        if (this.a == null) {
            this.a = Choreographer.getInstance();
            if (this.a != null) {
                m.Log(4, "Choreographer available: Enabling VSYNC timing");
                this.c = new Choreographer.FrameCallback(){

                    public final void doFrame(long l2) {
                        UnityPlayer.lockNativeAccess();
                        if (v.c()) {
                            unityPlayer.nativeAddVSyncTime(l2);
                        }
                        UnityPlayer.unlockNativeAccess();
                        l.this.d.lock();
                        if (l.this.a != null) {
                            l.this.a.postFrameCallback(l.this.c);
                        }
                        l.this.d.unlock();
                    }
                };
                this.a.postFrameCallback(this.c);
            }
        }
        this.d.unlock();
    }

    @Override
    public final void a() {
        this.d.lock();
        if (this.a != null) {
            this.a.removeFrameCallback(this.c);
        }
        this.a = null;
        this.d.unlock();
    }

}

