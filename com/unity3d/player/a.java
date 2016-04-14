/*
 * Decompiled with CFR 0_114.
 * 
 * Could not load the following classes:
 *  android.graphics.ImageFormat
 *  android.hardware.Camera
 *  android.hardware.Camera$Parameters
 *  android.hardware.Camera$PreviewCallback
 *  android.hardware.Camera$Size
 *  android.view.SurfaceHolder
 */
package com.unity3d.player;

import android.graphics.ImageFormat;
import android.hardware.Camera;
import android.view.SurfaceHolder;
import com.unity3d.player.b;
import com.unity3d.player.f;
import com.unity3d.player.m;
import com.unity3d.player.q;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

final class a {
    private final Object[] g = new Object[0];
    private final int h;
    private final int i;
    private final int j;
    private final int k;
    Camera a;
    Camera.Parameters b;
    Camera.Size c;
    int d;
    int[] e;
    b f;

    public a(int n2, int n3, int n4, int n5) {
        this.h = n2;
        this.i = a.a(n3, 640);
        this.j = a.a(n4, 480);
        this.k = a.a(n5, 24);
    }

    private void b(a object) {
        Object[] arrobject = this.g;
        synchronized (arrobject) {
            this.a = Camera.open((int)this.h);
            this.b = this.a.getParameters();
            this.c = this.f();
            this.e = this.e();
            this.d = this.d();
            a.a(this.b);
            this.b.setPreviewSize(this.c.width, this.c.height);
            this.b.setPreviewFpsRange(this.e[0], this.e[1]);
            this.a.setParameters(this.b);
            object = new Camera.PreviewCallback((a)object){
                long a;
                final /* synthetic */ a b;

                public final void onPreviewFrame(byte[] arrby, Camera camera) {
                    if (a.this.a != camera) {
                        return;
                    }
                    this.b.onCameraFrame(a.this, arrby);
                }
            };
            int n2 = this.c.width * this.c.height * this.d / 8 + 4096;
            this.a.addCallbackBuffer(new byte[n2]);
            this.a.addCallbackBuffer(new byte[n2]);
            this.a.setPreviewCallbackWithBuffer((Camera.PreviewCallback)object);
            return;
        }
    }

    private static void a(Camera.Parameters parameters) {
        if (parameters.getSupportedColorEffects() != null) {
            parameters.setColorEffect("none");
        }
        if (parameters.getSupportedFocusModes().contains("continuous-video")) {
            parameters.setFocusMode("continuous-video");
        }
    }

    public final int a() {
        return this.h;
    }

    public final Camera.Size b() {
        return this.c;
    }

    public final void a(a a2) {
        Object[] arrobject = this.g;
        synchronized (arrobject) {
            if (this.a == null) {
                this.b(a2);
            }
            if (q.a && q.i.a(this.a)) {
                this.a.startPreview();
                return;
            }
            if (this.f == null) {
                this.f = new b(){
                    Camera a;

                    public final void surfaceCreated(SurfaceHolder surfaceHolder) {
                        Object[] arrobject = a.this.g;
                        synchronized (arrobject) {
                            if (a.this.a != this.a) {
                                return;
                            }
                            try {
                                a.this.a.setPreviewDisplay(surfaceHolder);
                                a.this.a.startPreview();
                            }
                            catch (Exception var1_2) {
                                m.Log(6, "Unable to initialize webcam data stream: " + var1_2.getMessage());
                            }
                            return;
                        }
                    }

                    @Override
                    public final void surfaceDestroyed(SurfaceHolder arrobject) {
                        arrobject = a.this.g;
                        synchronized (arrobject) {
                            if (a.this.a != this.a) {
                                return;
                            }
                            a.this.a.stopPreview();
                            return;
                        }
                    }
                };
                this.f.a();
            }
            return;
        }
    }

    public final void a(byte[] arrby) {
        Object[] arrobject = this.g;
        synchronized (arrobject) {
            if (this.a != null) {
                this.a.addCallbackBuffer(arrby);
            }
            return;
        }
    }

    public final void c() {
        Object[] arrobject = this.g;
        synchronized (arrobject) {
            if (this.a != null) {
                this.a.setPreviewCallbackWithBuffer(null);
                this.a.stopPreview();
                this.a.release();
                this.a = null;
            }
            if (this.f != null) {
                this.f.b();
                this.f = null;
            }
            return;
        }
    }

    private final int d() {
        this.b.setPreviewFormat(17);
        return ImageFormat.getBitsPerPixel((int)17);
    }

    private final int[] e() {
        double d2 = this.k * 1000;
        ArrayList arrayList = this.b.getSupportedPreviewFpsRange();
        if (arrayList == null) {
            arrayList = new ArrayList();
        }
        int[] arrn = new int[]{this.k * 1000, this.k * 1000};
        double d3 = Double.MAX_VALUE;
        for (int[] arrn2 : arrayList) {
            double d4 = Math.abs(Math.log(d2 / (double)arrn2[0])) + Math.abs(Math.log(d2 / (double)arrn2[1]));
            if (d4 >= d3) continue;
            d3 = d4;
            arrn = arrn2;
        }
        return arrn;
    }

    private final Camera.Size f() {
        double d2 = this.i;
        double d3 = this.j;
        Object object = this.b.getSupportedPreviewSizes();
        Camera.Size size = null;
        double d4 = Double.MAX_VALUE;
        object = object.iterator();
        while (object.hasNext()) {
            Camera.Size size2 = (Camera.Size)object.next();
            double d5 = Math.abs(Math.log(d2 / (double)size2.width)) + Math.abs(Math.log(d3 / (double)size2.height));
            if (d5 >= d4) continue;
            d4 = d5;
            size = size2;
        }
        return size;
    }

    private static final int a(int n2, int n3) {
        if (n2 != 0) {
            return n2;
        }
        return n3;
    }

    static interface a {
        public void onCameraFrame(a var1, byte[] var2);
    }

}

