/*
 * Decompiled with CFR 0_114.
 * 
 * Could not load the following classes:
 *  android.media.AudioRecord
 *  android.util.Log
 */
package org.fmod;

import android.media.AudioRecord;
import android.util.Log;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import org.fmod.FMODAudioDevice;

final class a
implements Runnable {
    private final FMODAudioDevice a;
    private final ByteBuffer b;
    private final int c;
    private final int d;
    private final int e;
    private volatile Thread f;
    private volatile boolean g;
    private AudioRecord h;
    private boolean i;

    a(FMODAudioDevice fMODAudioDevice, int n2, int n3) {
        this.a = fMODAudioDevice;
        this.c = n2;
        this.d = n3;
        this.e = 2;
        this.b = ByteBuffer.allocateDirect(AudioRecord.getMinBufferSize((int)n2, (int)n3, (int)2));
    }

    public final int a() {
        return this.b.capacity();
    }

    public final void b() {
        if (this.f != null) {
            this.c();
        }
        this.g = true;
        this.f = new Thread(this);
        this.f.start();
    }

    public final void c() {
        while (this.f != null) {
            this.g = false;
            try {
                this.f.join();
                this.f = null;
            }
            catch (InterruptedException v0) {}
        }
    }

    @Override
    public final void run() {
        int n2 = 3;
        while (this.g) {
            if (!this.i && n2 > 0) {
                this.d();
                this.h = new AudioRecord(1, this.c, this.d, this.e, this.b.capacity());
                boolean bl = this.i = this.h.getState() == 1;
                if (this.i) {
                    n2 = 3;
                    this.b.position(0);
                    this.h.startRecording();
                } else {
                    Log.e((String)"FMOD", (String)("AudioRecord failed to initialize (status " + this.h.getState() + ")"));
                    --n2;
                    this.d();
                }
            }
            if (!this.i || this.h.getRecordingState() != 3) continue;
            int n3 = this.h.read(this.b, this.b.capacity());
            this.a.fmodProcessMicData(this.b, n3);
            this.b.position(0);
        }
        this.d();
    }

    private void d() {
        if (this.h != null) {
            if (this.h.getState() == 1) {
                this.h.stop();
            }
            this.h.release();
            this.h = null;
        }
        this.b.position(0);
        this.i = false;
    }
}

