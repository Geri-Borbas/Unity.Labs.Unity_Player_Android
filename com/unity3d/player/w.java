/*
 * Decompiled with CFR 0_114.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.AssetFileDescriptor
 *  android.content.res.AssetManager
 *  android.content.res.Resources
 *  android.media.MediaPlayer
 *  android.media.MediaPlayer$OnBufferingUpdateListener
 *  android.media.MediaPlayer$OnCompletionListener
 *  android.media.MediaPlayer$OnPreparedListener
 *  android.media.MediaPlayer$OnVideoSizeChangedListener
 *  android.net.Uri
 *  android.util.Log
 *  android.view.Display
 *  android.view.KeyEvent
 *  android.view.MotionEvent
 *  android.view.SurfaceHolder
 *  android.view.SurfaceHolder$Callback
 *  android.view.SurfaceView
 *  android.view.View
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.view.WindowManager
 *  android.widget.FrameLayout
 *  android.widget.FrameLayout$LayoutParams
 *  android.widget.MediaController
 *  android.widget.MediaController$MediaPlayerControl
 */
package com.unity3d.player;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.media.MediaPlayer;
import android.net.Uri;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.MediaController;
import com.unity3d.player.UnityPlayer;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.IOException;

public final class w
extends FrameLayout
implements MediaPlayer.OnBufferingUpdateListener,
MediaPlayer.OnCompletionListener,
MediaPlayer.OnPreparedListener,
MediaPlayer.OnVideoSizeChangedListener,
SurfaceHolder.Callback,
MediaController.MediaPlayerControl {
    private static boolean a = false;
    private final UnityPlayer b;
    private final Context c;
    private final SurfaceView d;
    private final SurfaceHolder e;
    private final String f;
    private final int g;
    private final int h;
    private final boolean i;
    private final long j;
    private final long k;
    private final FrameLayout l;
    private final Display m;
    private int n;
    private int o;
    private int p;
    private int q;
    private MediaPlayer r;
    private MediaController s;
    private boolean t = false;
    private boolean u = false;
    private int v = 0;
    private boolean w = false;
    private int x = 0;
    private boolean y;

    private static void a(String string) {
        Log.v((String)"Video", (String)("VideoPlayer: " + string));
    }

    protected w(UnityPlayer unityPlayer, Context context, String string, int n2, int n3, int n4, boolean bl, long l2, long l3) {
        super(context);
        this.b = unityPlayer;
        this.c = context;
        this.l = this;
        this.d = new SurfaceView(context);
        this.e = this.d.getHolder();
        this.e.addCallback((SurfaceHolder.Callback)this);
        this.e.setType(3);
        this.l.setBackgroundColor(n2);
        this.l.addView((View)this.d);
        unityPlayer = (WindowManager)this.c.getSystemService("window");
        this.m = unityPlayer.getDefaultDisplay();
        this.f = string;
        this.g = n3;
        this.h = n4;
        this.i = bl;
        this.j = l2;
        this.k = l3;
        if (a) {
            w.a("fileName: " + this.f);
        }
        if (a) {
            w.a("backgroundColor: " + n2);
        }
        if (a) {
            w.a("controlMode: " + this.g);
        }
        if (a) {
            w.a("scalingMode: " + this.h);
        }
        if (a) {
            w.a("isURL: " + this.i);
        }
        if (a) {
            w.a("videoOffset: " + this.j);
        }
        if (a) {
            w.a("videoLength: " + this.k);
        }
        this.setFocusable(true);
        this.setFocusableInTouchMode(true);
        this.y = true;
    }

    public final void onControllerHide() {
    }

    protected final void onPause() {
        if (a) {
            w.a("onPause called");
        }
        if (!this.w) {
            this.pause();
            this.w = false;
        }
        if (this.r != null) {
            this.x = this.r.getCurrentPosition();
        }
        this.y = false;
    }

    protected final void onResume() {
        if (a) {
            w.a("onResume called");
        }
        if (!this.y && !this.w) {
            this.start();
        }
        this.y = true;
    }

    protected final void onDestroy() {
        this.onPause();
        this.doCleanUp();
        UnityPlayer.a(new Runnable(){

            @Override
            public final void run() {
                w.this.b.hideVideoPlayer();
            }
        });
    }

    private void a() {
        this.doCleanUp();
        try {
            this.r = new MediaPlayer();
            if (this.i) {
                this.r.setDataSource(this.c, Uri.parse((String)this.f));
            } else if (this.k != 0) {
                FileInputStream fileInputStream = new FileInputStream(this.f);
                this.r.setDataSource(fileInputStream.getFD(), this.j, this.k);
                fileInputStream.close();
            } else {
                Object object = this.getResources().getAssets();
                try {
                    object = object.openFd(this.f);
                    this.r.setDataSource(object.getFileDescriptor(), object.getStartOffset(), object.getLength());
                    object.close();
                }
                catch (IOException v0) {
                    object = new FileInputStream(this.f);
                    this.r.setDataSource(object.getFD());
                    object.close();
                }
            }
            this.r.setDisplay(this.e);
            this.r.setScreenOnWhilePlaying(true);
            this.r.setOnBufferingUpdateListener((MediaPlayer.OnBufferingUpdateListener)this);
            this.r.setOnCompletionListener((MediaPlayer.OnCompletionListener)this);
            this.r.setOnPreparedListener((MediaPlayer.OnPreparedListener)this);
            this.r.setOnVideoSizeChangedListener((MediaPlayer.OnVideoSizeChangedListener)this);
            this.r.setAudioStreamType(3);
            this.r.prepare();
            if (this.g == 0 || this.g == 1) {
                this.s = new MediaController(this.c);
                this.s.setMediaPlayer((MediaController.MediaPlayerControl)this);
                this.s.setAnchorView((View)this);
                this.s.setEnabled(true);
                this.s.show();
            }
            return;
        }
        catch (Exception var1_3) {
            if (a) {
                w.a("error: " + var1_3.getMessage() + var1_3);
            }
            this.onDestroy();
            return;
        }
    }

    public final boolean onKeyDown(int n2, KeyEvent keyEvent) {
        if (n2 == 4 || this.g == 2 && n2 != 0 && !keyEvent.isSystem()) {
            this.onDestroy();
            return true;
        }
        if (this.s != null) {
            return this.s.onKeyDown(n2, keyEvent);
        }
        return super.onKeyDown(n2, keyEvent);
    }

    public final boolean onTouchEvent(MotionEvent motionEvent) {
        int n2 = motionEvent.getAction() & 255;
        if (this.g == 2 && n2 == 0) {
            this.onDestroy();
            return true;
        }
        if (this.s != null) {
            return this.s.onTouchEvent(motionEvent);
        }
        return super.onTouchEvent(motionEvent);
    }

    public final void onBufferingUpdate(MediaPlayer mediaPlayer, int n2) {
        if (a) {
            w.a("onBufferingUpdate percent:" + n2);
        }
        this.v = n2;
    }

    public final void onCompletion(MediaPlayer mediaPlayer) {
        if (a) {
            w.a("onCompletion called");
        }
        this.onDestroy();
    }

    public final void onVideoSizeChanged(MediaPlayer mediaPlayer, int n2, int n3) {
        if (a) {
            w.a("onVideoSizeChanged called " + n2 + "x" + n3);
        }
        if (n2 == 0 || n3 == 0) {
            if (a) {
                w.a("invalid video width(" + n2 + ") or height(" + n3 + ")");
            }
            return;
        }
        this.t = true;
        this.p = n2;
        this.q = n3;
        if (this.u && this.t) {
            this.b();
        }
    }

    public final void onPrepared(MediaPlayer mediaPlayer) {
        if (a) {
            w.a("onPrepared called");
        }
        this.u = true;
        if (this.u && this.t) {
            this.b();
        }
    }

    public final void surfaceChanged(SurfaceHolder surfaceHolder, int n2, int n3, int n4) {
        if (a) {
            w.a("surfaceChanged called " + n2 + " " + n3 + "x" + n4);
        }
        if (this.n != n3 || this.o != n4) {
            this.n = n3;
            this.o = n4;
            this.updateVideoLayout();
        }
    }

    public final void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        if (a) {
            w.a("surfaceDestroyed called");
        }
        this.doCleanUp();
    }

    public final void surfaceCreated(SurfaceHolder surfaceHolder) {
        if (a) {
            w.a("surfaceCreated called");
        }
        this.a();
        this.seekTo(this.x);
    }

    protected final void doCleanUp() {
        if (this.r != null) {
            this.r.release();
            this.r = null;
        }
        this.p = 0;
        this.q = 0;
        this.u = false;
        this.t = false;
    }

    private void b() {
        if (this.isPlaying()) {
            return;
        }
        if (a) {
            w.a("startVideoPlayback");
        }
        this.updateVideoLayout();
        if (!this.w) {
            this.start();
        }
    }

    protected final void updateVideoLayout() {
        if (a) {
            w.a("updateVideoLayout");
        }
        if (this.n == 0 || this.o == 0) {
            WindowManager windowManager = (WindowManager)this.c.getSystemService("window");
            this.n = windowManager.getDefaultDisplay().getWidth();
            this.o = windowManager.getDefaultDisplay().getHeight();
        }
        int n2 = this.n;
        int n3 = this.o;
        float f2 = (float)this.p / (float)this.q;
        float f3 = (float)this.n / (float)this.o;
        if (this.h == 1) {
            if (f3 <= f2) {
                n3 = (int)((float)this.n / f2);
            } else {
                n2 = (int)((float)this.o * f2);
            }
        } else if (this.h == 2) {
            if (f3 >= f2) {
                n3 = (int)((float)this.n / f2);
            } else {
                n2 = (int)((float)this.o * f2);
            }
        } else if (this.h == 0) {
            n2 = this.p;
            n3 = this.q;
        }
        if (a) {
            w.a("frameWidth = " + n2 + "; frameHeight = " + n3);
        }
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(n2, n3, 17);
        this.l.updateViewLayout((View)this.d, (ViewGroup.LayoutParams)layoutParams);
    }

    public final boolean canPause() {
        return true;
    }

    public final boolean canSeekBackward() {
        return true;
    }

    public final boolean canSeekForward() {
        return true;
    }

    public final int getBufferPercentage() {
        if (this.i) {
            return this.v;
        }
        return 100;
    }

    public final int getCurrentPosition() {
        if (this.r == null) {
            return 0;
        }
        return this.r.getCurrentPosition();
    }

    public final int getDuration() {
        if (this.r == null) {
            return 0;
        }
        return this.r.getDuration();
    }

    public final boolean isPlaying() {
        boolean bl;
        boolean bl2 = bl = this.u && this.t;
        if (this.r == null) {
            if (!bl) {
                return true;
            }
            return false;
        }
        if (this.r.isPlaying() || !bl) {
            return true;
        }
        return false;
    }

    public final void pause() {
        if (this.r == null) {
            return;
        }
        this.r.pause();
        this.w = true;
    }

    public final void seekTo(int n2) {
        if (this.r == null) {
            return;
        }
        this.r.seekTo(n2);
    }

    public final void start() {
        if (this.r == null) {
            return;
        }
        this.r.start();
        this.w = false;
    }

}

