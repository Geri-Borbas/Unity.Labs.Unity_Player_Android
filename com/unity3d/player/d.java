/*
 * Decompiled with CFR 0_114.
 * 
 * Could not load the following classes:
 *  android.graphics.SurfaceTexture
 *  android.hardware.Camera
 *  android.os.Handler
 *  android.view.View
 *  android.view.View$OnSystemUiVisibilityChangeListener
 */
package com.unity3d.player;

import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.os.Handler;
import android.view.View;
import com.unity3d.player.f;
import com.unity3d.player.q;

public final class d
implements f {
    private static final SurfaceTexture a = new SurfaceTexture(-1);
    private static final int b = q.f ? 5894 : 1;
    private volatile boolean c;

    @Override
    public final boolean a(Camera camera) {
        try {
            camera.setPreviewTexture(a);
            return true;
        }
        catch (Exception v0) {
            return false;
        }
    }

    @Override
    public final void a(View view, boolean bl) {
        this.c = bl;
        view.setSystemUiVisibility(this.c ? view.getSystemUiVisibility() | b : view.getSystemUiVisibility() & ~ b);
    }

    @Override
    public final void a(final View view) {
        if (q.f) {
            return;
        }
        view.setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener(){

            public final void onSystemUiVisibilityChange(int n2) {
                d.this.a(view, 1000);
            }
        });
    }

    @Override
    public final void b(View view) {
        if (!q.f && this.c) {
            this.a(view, false);
            this.c = true;
        }
        this.a(view, 1000);
    }

    private void a(final View view, int n2) {
        Handler handler = view.getHandler();
        if (handler == null) {
            this.a(view, this.c);
            return;
        }
        handler.postDelayed(new Runnable(){

            @Override
            public final void run() {
                d.this.a(view, d.this.c);
            }
        }, 1000);
    }

}

