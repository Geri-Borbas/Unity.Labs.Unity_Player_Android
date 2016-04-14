/*
 * Decompiled with CFR 0_114.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.content.Context
 *  android.view.SurfaceHolder
 *  android.view.SurfaceHolder$Callback
 *  android.view.SurfaceView
 *  android.view.View
 */
package com.unity3d.player;

import android.app.Activity;
import android.content.Context;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import com.unity3d.player.t;

abstract class b
implements SurfaceHolder.Callback {
    private final Activity a = (Activity)t.a.a();
    private final int b = 3;
    private SurfaceView c;

    b(int n2) {
    }

    final void a() {
        this.a.runOnUiThread(new Runnable(){

            @Override
            public final void run() {
                if (b.this.c == null) {
                    b.this.c = new SurfaceView(t.a.a());
                    b.this.c.getHolder().setType(b.this.b);
                    b.this.c.getHolder().addCallback((SurfaceHolder.Callback)b.this);
                    t.a.a((View)b.this.c);
                    b.this.c.setVisibility(0);
                }
            }
        });
    }

    final void b() {
        this.a.runOnUiThread(new Runnable(){

            @Override
            public final void run() {
                if (b.this.c != null) {
                    t.a.b((View)b.this.c);
                }
                b.this.c = null;
            }
        });
    }

    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
    }

    public void surfaceChanged(SurfaceHolder surfaceHolder, int n2, int n3, int n4) {
    }

}

