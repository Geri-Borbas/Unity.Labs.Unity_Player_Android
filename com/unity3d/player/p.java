/*
 * Decompiled with CFR 0_114.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.content.ContextWrapper
 *  android.view.MotionEvent
 *  android.view.MotionEvent$PointerCoords
 *  android.view.View
 *  android.view.Window
 */
package com.unity3d.player;

import android.app.Activity;
import android.content.ContextWrapper;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import com.unity3d.player.e;
import com.unity3d.player.j;
import com.unity3d.player.q;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public final class p
implements j {
    private final Queue a = new ConcurrentLinkedQueue();
    private final Activity b;
    private Runnable c;

    public p(ContextWrapper contextWrapper) {
        this.c = new Runnable(){

            private static void a(View view, MotionEvent motionEvent) {
                if (q.b) {
                    q.j.a(view, motionEvent);
                }
            }

            @Override
            public final void run() {
                MotionEvent motionEvent;
                block3 : while ((motionEvent = (MotionEvent)p.this.a.poll()) != null) {
                    View view = p.this.b.getWindow().getDecorView();
                    int n2 = motionEvent.getSource();
                    if ((n2 & 2) != 0) {
                        switch (motionEvent.getAction() & 255) {
                            case 0: 
                            case 1: 
                            case 2: 
                            case 3: 
                            case 4: 
                            case 5: 
                            case 6: {
                                view.dispatchTouchEvent(motionEvent);
                                continue block3;
                            }
                        }
                        .a(view, motionEvent);
                        continue;
                    }
                    if ((n2 & 4) != 0) {
                        view.dispatchTrackballEvent(motionEvent);
                        continue;
                    }
                    .a(view, motionEvent);
                }
            }
        };
        this.b = (Activity)contextWrapper;
    }

    private static MotionEvent.PointerCoords[] a(int n2, float[] arrf) {
        MotionEvent.PointerCoords[] arrpointerCoords = new MotionEvent.PointerCoords[n2];
        p.a(arrpointerCoords, arrf, 0);
        return arrpointerCoords;
    }

    private static int a(MotionEvent.PointerCoords[] arrpointerCoords, float[] arrf, int n2) {
        for (int i2 = 0; i2 < arrpointerCoords.length; ++i2) {
            MotionEvent.PointerCoords pointerCoords = arrpointerCoords[i2] = new MotionEvent.PointerCoords();
            arrpointerCoords[i2].orientation = arrf[n2++];
            pointerCoords.pressure = arrf[n2++];
            pointerCoords.size = arrf[n2++];
            pointerCoords.toolMajor = arrf[n2++];
            pointerCoords.toolMinor = arrf[n2++];
            pointerCoords.touchMajor = arrf[n2++];
            pointerCoords.touchMinor = arrf[n2++];
            pointerCoords.x = arrf[n2++];
            pointerCoords.y = arrf[n2++];
        }
        return n2;
    }

    @Override
    public final void a(long l2, long l3, int n2, int n3, int[] arrn, float[] arrf, int n4, float f2, float f3, int n5, int n6, int n7, int n8, int n9, long[] arrl, float[] arrf2) {
        if (this.b != null) {
            MotionEvent motionEvent = MotionEvent.obtain((long)l2, (long)l3, (int)n2, (int)n3, (int[])arrn, (MotionEvent.PointerCoords[])p.a(n3, arrf), (int)n4, (float)f2, (float)f3, (int)n5, (int)n6, (int)n7, (int)n8);
            int n10 = 0;
            for (int i2 = 0; i2 < n9; ++i2) {
                MotionEvent.PointerCoords[] arrpointerCoords = new MotionEvent.PointerCoords[n3];
                n10 = p.a(arrpointerCoords, arrf2, n10);
                motionEvent.addBatch(arrl[i2], arrpointerCoords, n4);
            }
            this.a.add(motionEvent);
            this.b.runOnUiThread(this.c);
        }
    }

}

