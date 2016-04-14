/*
 * Decompiled with CFR 0_114.
 * 
 * Could not load the following classes:
 *  android.media.AudioTrack
 *  android.util.Log
 */
package org.fmod;

import android.media.AudioTrack;
import android.util.Log;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import org.fmod.a;

public class FMODAudioDevice
implements Runnable {
    private volatile Thread a = null;
    private volatile boolean b = false;
    private AudioTrack c = null;
    private boolean d = false;
    private ByteBuffer e = null;
    private byte[] f = null;
    private volatile a g;
    private static int h = 0;
    private static int i = 1;
    private static int j = 2;
    private static int k = 3;

    public synchronized void start() {
        if (this.a != null) {
            this.stop();
        }
        this.a = new Thread((Runnable)this, "FMODAudioDevice");
        this.a.setPriority(10);
        this.b = true;
        this.a.start();
        if (this.g != null) {
            this.g.b();
        }
    }

    public synchronized void stop() {
        while (this.a != null) {
            this.b = false;
            try {
                this.a.join();
                this.a = null;
            }
            catch (InterruptedException v0) {}
        }
        if (this.g != null) {
            this.g.c();
        }
    }

    public synchronized void close() {
        this.stop();
    }

    public boolean isRunning() {
        if (this.a != null && this.a.isAlive()) {
            return true;
        }
        return false;
    }

    @Override
    public void run() {
        int n2 = 3;
        while (this.b) {
            if (!this.d && n2 > 0) {
                this.releaseAudioTrack();
                int n3 = this.fmodGetInfo(h);
                int n4 = Math.round((float)AudioTrack.getMinBufferSize((int)n3, (int)3, (int)2) * 1.1f) & -4;
                int n5 = this.fmodGetInfo(i);
                int n6 = this.fmodGetInfo(j);
                if (n5 * n6 * 4 > n4) {
                    n4 = n5 * n6 * 4;
                }
                this.c = new AudioTrack(3, n3, 3, 2, n4, 1);
                boolean bl = this.d = this.c.getState() == 1;
                if (this.d) {
                    n2 = 3;
                    this.e = ByteBuffer.allocateDirect(n5 * 2 * 2);
                    this.f = new byte[this.e.capacity()];
                    this.c.play();
                } else {
                    Log.e((String)"FMOD", (String)("AudioTrack failed to initialize (status " + this.c.getState() + ")"));
                    this.releaseAudioTrack();
                    --n2;
                }
            }
            if (!this.d) continue;
            if (this.fmodGetInfo(k) == 1) {
                this.fmodProcess(this.e);
                this.e.get(this.f, 0, this.e.capacity());
                this.c.write(this.f, 0, this.e.capacity());
                this.e.position(0);
                continue;
            }
            this.releaseAudioTrack();
        }
        this.releaseAudioTrack();
    }

    private void releaseAudioTrack() {
        if (this.c != null) {
            if (this.c.getState() == 1) {
                this.c.stop();
            }
            this.c.release();
            this.c = null;
        }
        this.e = null;
        this.f = null;
        this.d = false;
    }

    private native int fmodGetInfo(int var1);

    private native int fmodProcess(ByteBuffer var1);

    native int fmodProcessMicData(ByteBuffer var1, int var2);

    public synchronized int startAudioRecord(int n2, int n3, int n4) {
        if (this.g == null) {
            this.g = new a(this, n2, n3);
            this.g.b();
        }
        return this.g.a();
    }

    public synchronized void stopAudioRecord() {
        if (this.g != null) {
            this.g.c();
            this.g = null;
        }
    }
}

