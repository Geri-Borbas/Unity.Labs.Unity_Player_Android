/*
 * Decompiled with CFR 0_114.
 * 
 * Could not load the following classes:
 *  android.app.Presentation
 *  android.content.Context
 *  android.hardware.display.DisplayManager
 *  android.hardware.display.DisplayManager$DisplayListener
 *  android.os.Bundle
 *  android.os.Handler
 *  android.view.Display
 *  android.view.Surface
 *  android.view.SurfaceHolder
 *  android.view.SurfaceHolder$Callback
 *  android.view.SurfaceView
 *  android.view.View
 */
package com.unity3d.player;

import android.app.Presentation;
import android.content.Context;
import android.hardware.display.DisplayManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.Display;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import com.unity3d.player.UnityPlayer;
import com.unity3d.player.g;

public final class k
implements g {
    private Object a = new Object[0];
    private Presentation b;
    private DisplayManager.DisplayListener c;

    @Override
    public final void a(final UnityPlayer unityPlayer, Context context) {
        if ((context = (DisplayManager)context.getSystemService("display")) == null) {
            return;
        }
        context.registerDisplayListener(new DisplayManager.DisplayListener(){

            public final void onDisplayAdded(int n2) {
                unityPlayer.displayChanged(-1, null);
            }

            public final void onDisplayRemoved(int n2) {
                unityPlayer.displayChanged(-1, null);
            }

            public final void onDisplayChanged(int n2) {
                unityPlayer.displayChanged(-1, null);
            }
        }, null);
    }

    @Override
    public final void a(Context context) {
        if (this.c == null) {
            return;
        }
        if ((context = (DisplayManager)context.getSystemService("display")) == null) {
            return;
        }
        context.unregisterDisplayListener(this.c);
    }

    @Override
    public final boolean a(final UnityPlayer unityPlayer, final Context context, int n2) {
        Object object = this.a;
        synchronized (object) {
            DisplayManager displayManager;
            if (this.b != null && this.b.isShowing() && (displayManager = this.b.getDisplay()) != null && displayManager.getDisplayId() == n2) {
                return true;
            }
            displayManager = (DisplayManager)context.getSystemService("display");
            if (displayManager == null) {
                return false;
            }
            final Display display = displayManager.getDisplay(n2);
            if (display == null) {
                return false;
            }
            unityPlayer.b(new Runnable(){

                @Override
                public final void run() {
                    Object object = k.this.a;
                    synchronized (object) {
                        if (k.this.b != null) {
                            k.this.b.dismiss();
                        }
                        k.this.b = new Presentation(context, display){

                            protected final void onCreate(Bundle bundle) {
                                bundle = new SurfaceView(context);
                                bundle.getHolder().addCallback(new SurfaceHolder.Callback(){

                                    public final void surfaceCreated(SurfaceHolder surfaceHolder) {
                                        unityPlayer.displayChanged(1, surfaceHolder.getSurface());
                                    }

                                    public final void surfaceChanged(SurfaceHolder surfaceHolder, int n2, int n3, int n4) {
                                        unityPlayer.displayChanged(1, surfaceHolder.getSurface());
                                    }

                                    public final void surfaceDestroyed(SurfaceHolder surfaceHolder) {
                                        unityPlayer.displayChanged(1, null);
                                    }
                                });
                                this.setContentView((View)bundle);
                            }

                            public final void onDisplayRemoved() {
                                this.dismiss();
                                Object object = k.this.a;
                                synchronized (object) {
                                    k.this.b = null;
                                    return;
                                }
                            }

                        };
                        k.this.b.show();
                        return;
                    }
                }

            });
            return true;
        }
    }

}

