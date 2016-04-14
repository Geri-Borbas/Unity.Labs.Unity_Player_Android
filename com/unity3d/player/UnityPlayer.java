/*
 * Decompiled with CFR 0_114.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.app.AlertDialog
 *  android.app.AlertDialog$Builder
 *  android.app.NativeActivity
 *  android.content.BroadcastReceiver
 *  android.content.Context
 *  android.content.ContextWrapper
 *  android.content.DialogInterface
 *  android.content.DialogInterface$OnClickListener
 *  android.content.pm.ApplicationInfo
 *  android.content.pm.PackageInfo
 *  android.content.pm.PackageManager
 *  android.content.pm.PackageManager$NameNotFoundException
 *  android.content.res.AssetManager
 *  android.content.res.Configuration
 *  android.hardware.Camera
 *  android.hardware.Camera$CameraInfo
 *  android.hardware.Camera$Size
 *  android.net.NetworkInfo
 *  android.os.Bundle
 *  android.os.Environment
 *  android.os.Process
 *  android.util.AttributeSet
 *  android.view.InputEvent
 *  android.view.KeyEvent
 *  android.view.MotionEvent
 *  android.view.Surface
 *  android.view.SurfaceHolder
 *  android.view.SurfaceHolder$Callback
 *  android.view.SurfaceView
 *  android.view.View
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.view.Window
 *  android.view.WindowManager
 *  android.widget.FrameLayout
 *  android.widget.FrameLayout$LayoutParams
 *  android.widget.ProgressBar
 *  org.xmlpull.v1.XmlPullParser
 *  org.xmlpull.v1.XmlPullParserFactory
 */
package com.unity3d.player;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.NativeActivity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.hardware.Camera;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Environment;
import android.os.Process;
import android.util.AttributeSet;
import android.view.InputEvent;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import com.unity3d.player.NativeLoader;
import com.unity3d.player.UnityWebRequest;
import com.unity3d.player.WWW;
import com.unity3d.player.a;
import com.unity3d.player.f;
import com.unity3d.player.g;
import com.unity3d.player.h;
import com.unity3d.player.i;
import com.unity3d.player.j;
import com.unity3d.player.m;
import com.unity3d.player.o;
import com.unity3d.player.p;
import com.unity3d.player.q;
import com.unity3d.player.r;
import com.unity3d.player.s;
import com.unity3d.player.t;
import com.unity3d.player.u;
import com.unity3d.player.v;
import com.unity3d.player.w;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

public class UnityPlayer
extends FrameLayout
implements a.a {
    public static Activity currentActivity = null;
    private boolean c = false;
    private boolean d = false;
    private boolean e = true;
    private final j f;
    private final t g;
    private boolean h = false;
    private v i = new v();
    private final ConcurrentLinkedQueue j = new ConcurrentLinkedQueue();
    private BroadcastReceiver k = null;
    private boolean l = false;
    b a;
    private ContextWrapper m;
    private SurfaceView n;
    private WindowManager o;
    private static boolean p;
    private boolean q;
    private boolean r;
    private int s;
    private int t;
    private final r u;
    private String v;
    private NetworkInfo w;
    private Bundle x;
    private List y;
    private w z;
    s b;
    private ProgressBar A;
    private Runnable B;
    private Runnable C;
    private static Lock D;

    public UnityPlayer(ContextWrapper contextWrapper) {
        super((Context)contextWrapper);
        this.a = new b();
        this.r = true;
        this.s = 0;
        this.t = 0;
        this.v = null;
        this.w = null;
        this.x = new Bundle();
        this.y = new ArrayList();
        this.b = null;
        this.A = null;
        this.B = new Runnable(){

            @Override
            public final void run() {
                int n2 = UnityPlayer.this.nativeActivityIndicatorStyle();
                if (n2 >= 0) {
                    if (UnityPlayer.this.A == null) {
                        int[] arrn = new int[]{16842874, 16843401, 16842873, 16843400};
                        UnityPlayer.this.A = new ProgressBar((Context)UnityPlayer.this.m, null, arrn[n2]);
                        UnityPlayer.this.A.setIndeterminate(true);
                        UnityPlayer.this.A.setLayoutParams((ViewGroup.LayoutParams)new FrameLayout.LayoutParams(-2, -2, 51));
                        UnityPlayer.this.addView((View)UnityPlayer.this.A);
                    }
                    UnityPlayer.this.A.setVisibility(0);
                    UnityPlayer.this.bringChildToFront((View)UnityPlayer.this.A);
                }
            }
        };
        this.C = new Runnable(){

            @Override
            public final void run() {
                if (UnityPlayer.this.A != null) {
                    UnityPlayer.this.A.setVisibility(8);
                    UnityPlayer.this.removeView((View)UnityPlayer.this.A);
                    UnityPlayer.this.A = null;
                }
            }
        };
        if (contextWrapper instanceof Activity) {
            currentActivity = (Activity)contextWrapper;
        }
        this.g = new t((ViewGroup)this);
        this.m = contextWrapper;
        this.f = contextWrapper instanceof Activity ? new p(contextWrapper) : null;
        this.u = new r((Context)contextWrapper, this);
        this.a();
        if (q.a) {
            q.i.a((View)this);
        }
        this.setFullscreen(true);
        UnityPlayer.a(this.m.getApplicationInfo());
        if (!v.c()) {
            contextWrapper = new AlertDialog.Builder((Context)this.m).setTitle((CharSequence)"Failure to initialize!").setPositiveButton((CharSequence)"OK", new DialogInterface.OnClickListener(){

                public final void onClick(DialogInterface dialogInterface, int n2) {
                    UnityPlayer.this.b();
                }
            }).setMessage((CharSequence)"Your hardware does not support this application, sorry!").create();
            contextWrapper.setCancelable(false);
            contextWrapper.show();
            return;
        }
        this.nativeFile(this.m.getPackageCodePath());
        this.j();
        this.n = new SurfaceView((Context)contextWrapper);
        this.n.getHolder().setFormat(2);
        this.n.getHolder().addCallback(new SurfaceHolder.Callback(){

            public final void surfaceCreated(SurfaceHolder surfaceHolder) {
                UnityPlayer.this.a(0, surfaceHolder.getSurface());
            }

            public final void surfaceChanged(SurfaceHolder surfaceHolder, int n2, int n3, int n4) {
                UnityPlayer.this.a(0, surfaceHolder.getSurface());
            }

            public final void surfaceDestroyed(SurfaceHolder surfaceHolder) {
                UnityPlayer.this.a(0, null);
            }
        });
        this.n.setFocusable(true);
        this.n.setFocusableInTouchMode(true);
        this.g.c((View)this.n);
        this.q = false;
        this.c();
        this.initJni((Context)contextWrapper);
        this.nativeInitWWW(WWW.class);
        this.nativeInitWebRequest(UnityWebRequest.class);
        if (q.e) {
            q.l.a(this, (Context)this.m);
        }
        if (q.h && currentActivity != null) {
            q.m.a(currentActivity, new Runnable(){

                @Override
                public final void run() {
                    UnityPlayer.this.b(new Runnable(){

                        @Override
                        public final void run() {
                            UnityPlayer.this.i.d();
                            UnityPlayer.this.g();
                        }
                    });
                }

            });
        }
        if (q.d) {
            q.k.a(this);
        }
        this.o = (WindowManager)this.m.getSystemService("window");
        this.k();
        this.a.start();
    }

    private void a(int n2, Surface surface) {
        if (this.c) {
            return;
        }
        this.b(0, surface);
    }

    private boolean b(int n2, Surface surface) {
        if (!v.c()) {
            return false;
        }
        this.nativeRecreateGfxState(n2, surface);
        return true;
    }

    public boolean displayChanged(int n2, Surface surface) {
        if (n2 == 0) {
            this.c = surface != null;
            this.b(new Runnable(){

                @Override
                public final void run() {
                    if (UnityPlayer.this.c) {
                        UnityPlayer.this.g.d((View)UnityPlayer.this.n);
                        return;
                    }
                    UnityPlayer.this.g.c((View)UnityPlayer.this.n);
                }
            });
        }
        return this.b(n2, surface);
    }

    protected boolean installPresentationDisplay(int n2) {
        if (q.e) {
            return q.l.a(this, (Context)this.m, n2);
        }
        return false;
    }

    private void a() {
        try {
            Object object = new File(this.m.getPackageCodePath(), "assets/bin/Data/settings.xml");
            object = object.exists() ? new FileInputStream((File)object) : this.m.getAssets().open("bin/Data/settings.xml");
            XmlPullParserFactory xmlPullParserFactory = XmlPullParserFactory.newInstance();
            xmlPullParserFactory.setNamespaceAware(true);
            xmlPullParserFactory = xmlPullParserFactory.newPullParser();
            xmlPullParserFactory.setInput((InputStream)object, null);
            object = null;
            String string = null;
            int n2 = xmlPullParserFactory.getEventType();
            while (n2 != 1) {
                if (n2 == 2) {
                    object = xmlPullParserFactory.getName();
                    for (n2 = 0; n2 < xmlPullParserFactory.getAttributeCount(); ++n2) {
                        if (!xmlPullParserFactory.getAttributeName(n2).equalsIgnoreCase("name")) continue;
                        string = xmlPullParserFactory.getAttributeValue(n2);
                    }
                } else if (n2 == 3) {
                    object = null;
                } else if (n2 == 4 && string != null) {
                    if (object.equalsIgnoreCase("integer")) {
                        this.x.putInt(string, Integer.parseInt(xmlPullParserFactory.getText()));
                    } else if (object.equalsIgnoreCase("string")) {
                        this.x.putString(string, xmlPullParserFactory.getText());
                    } else if (object.equalsIgnoreCase("bool")) {
                        this.x.putBoolean(string, Boolean.parseBoolean(xmlPullParserFactory.getText()));
                    } else if (object.equalsIgnoreCase("float")) {
                        this.x.putFloat(string, Float.parseFloat(xmlPullParserFactory.getText()));
                    }
                    string = null;
                }
                n2 = xmlPullParserFactory.next();
            }
            return;
        }
        catch (Exception var1_2) {
            m.Log(6, "Unable to locate player settings. " + var1_2.getLocalizedMessage());
            this.b();
            return;
        }
    }

    public Bundle getSettings() {
        return this.x;
    }

    public static native void UnitySendMessage(String var0, String var1, String var2);

    private void b() {
        if (this.m instanceof Activity && !((Activity)this.m).isFinishing()) {
            ((Activity)this.m).finish();
        }
    }

    static void a(Runnable runnable) {
        new Thread(runnable).start();
    }

    final void b(Runnable runnable) {
        if (this.m instanceof Activity) {
            ((Activity)this.m).runOnUiThread(runnable);
            return;
        }
        m.Log(5, "Not running Unity from an Activity; ignored...");
    }

    public void init(int n2, boolean bl) {
    }

    public View getView() {
        return this;
    }

    private void c() {
        o o2 = new o((Activity)this.m);
        if (this.m instanceof NativeActivity) {
            this.l = o2.a();
            this.nativeForwardEventsToDalvik(this.l);
        }
    }

    protected void kill() {
        Process.killProcess((int)Process.myPid());
    }

    public void quit() {
        this.q = true;
        if (!this.i.e()) {
            this.pause();
        }
        this.a.a();
        try {
            this.a.join(4000);
        }
        catch (InterruptedException v0) {
            this.a.interrupt();
        }
        if (this.k != null) {
            this.m.unregisterReceiver(this.k);
        }
        this.k = null;
        if (v.c()) {
            this.removeAllViews();
        }
        if (q.e) {
            q.l.a((Context)this.m);
        }
        if (q.d) {
            q.k.a();
        }
        this.kill();
        UnityPlayer.h();
    }

    private void d() {
        Iterator<E> iterator = this.y.iterator();
        while (iterator.hasNext()) {
            ((com.unity3d.player.a)iterator.next()).c();
        }
    }

    private void e() {
        for (com.unity3d.player.a a2 : this.y) {
            try {
                a2.a(this);
            }
            catch (Exception var3_4) {
                String string = "Unable to initialize camera: " + var3_4.getMessage();
                m.Log(6, string);
                a2.c();
            }
        }
    }

    public void pause() {
        if (this.z != null) {
            this.z.onPause();
            return;
        }
        this.reportSoftInputStr(null, 1, true);
        if (!this.i.g()) {
            return;
        }
        if (v.c()) {
            final Semaphore semaphore = new Semaphore(0);
            if (this.isFinishing()) {
                this.c(new Runnable(){

                    @Override
                    public final void run() {
                        UnityPlayer.this.f();
                        semaphore.release();
                    }
                });
            } else {
                this.c(new Runnable(){

                    @Override
                    public final void run() {
                        if (UnityPlayer.this.nativePause()) {
                            UnityPlayer.this.q = true;
                            UnityPlayer.this.f();
                            semaphore.release(2);
                            return;
                        }
                        semaphore.release();
                    }
                });
            }
            try {
                if (!semaphore.tryAcquire(4, TimeUnit.SECONDS)) {
                    m.Log(5, "Timeout while trying to pause the Unity Engine.");
                }
            }
            catch (InterruptedException v0) {
                m.Log(5, "UI thread got interrupted while trying to pause the Unity Engine.");
            }
            if (semaphore.drainPermits() > 0) {
                this.quit();
            }
        }
        this.i.c(false);
        this.i.b(true);
        this.d();
        this.a.c();
        this.u.d();
    }

    public void resume() {
        if (q.a) {
            q.i.b((View)this);
        }
        this.i.b(false);
        this.g();
    }

    private void f() {
        this.nativeDone();
    }

    private void g() {
        if (!this.i.f()) {
            return;
        }
        if (this.z != null) {
            this.z.onResume();
            return;
        }
        this.i.c(true);
        this.e();
        this.u.e();
        this.v = null;
        this.w = null;
        if (v.c()) {
            this.j();
        }
        this.c(new Runnable(){

            @Override
            public final void run() {
                UnityPlayer.this.nativeResume();
            }
        });
        this.a.b();
    }

    public void configurationChanged(Configuration configuration) {
        if (this.n instanceof SurfaceView) {
            this.n.getHolder().setSizeFromLayout();
        }
        if (this.z != null) {
            this.z.updateVideoLayout();
        }
    }

    public void windowFocusChanged(final boolean bl) {
        this.i.a(bl);
        if (bl && this.b != null) {
            this.reportSoftInputStr(null, 1, false);
        }
        if (q.a && bl) {
            q.i.b((View)this);
        }
        this.c(new Runnable(){

            @Override
            public final void run() {
                UnityPlayer.this.nativeFocusChanged(bl);
            }
        });
        this.a.a(bl);
        this.g();
    }

    protected static boolean loadLibraryStatic(String string) {
        try {
            System.loadLibrary(string);
        }
        catch (UnsatisfiedLinkError v0) {
            m.Log(6, "Unable to find " + string);
            return false;
        }
        catch (Exception var0_1) {
            m.Log(6, "Unknown error " + var0_1);
            return false;
        }
        return true;
    }

    protected boolean loadLibrary(String string) {
        return UnityPlayer.loadLibraryStatic(string);
    }

    protected void startActivityIndicator() {
        this.b(this.B);
    }

    protected void stopActivityIndicator() {
        this.b(this.C);
    }

    private final native void nativeFile(String var1);

    private final native void initJni(Context var1);

    private final native void nativeSetExtras(Bundle var1);

    private final native void nativeSetTouchDeltaY(float var1);

    private final native boolean nativeRender();

    private final native void nativeSetInputString(String var1);

    private final native void nativeSetInputCanceled(boolean var1);

    private final native boolean nativePause();

    private final native void nativeResume();

    private final native void nativeFocusChanged(boolean var1);

    private final native void nativeRecreateGfxState(int var1, Surface var2);

    private final native void nativeDone();

    private final native void nativeSoftInputClosed();

    private final native void nativeInitWWW(Class var1);

    private final native void nativeInitWebRequest(Class var1);

    private final native void nativeVideoFrameCallback(int var1, byte[] var2, int var3, int var4);

    private final native int nativeActivityIndicatorStyle();

    private final native boolean nativeInjectEvent(InputEvent var1);

    protected final native void nativeAddVSyncTime(long var1);

    final native void nativeForwardEventsToDalvik(boolean var1);

    protected static void lockNativeAccess() {
        D.lock();
    }

    protected static void unlockNativeAccess() {
        D.unlock();
    }

    private static void a(ApplicationInfo applicationInfo) {
        if (p && NativeLoader.load(applicationInfo.nativeLibraryDir)) {
            v.a();
        }
    }

    private static void h() {
        if (!v.c()) {
            return;
        }
        UnityPlayer.lockNativeAccess();
        if (!NativeLoader.unload()) {
            UnityPlayer.unlockNativeAccess();
            throw new UnsatisfiedLinkError("Unable to unload libraries from libmain.so");
        }
        v.b();
        UnityPlayer.unlockNativeAccess();
    }

    protected void forwardMotionEventToDalvik(long l2, long l3, int n2, int n3, int[] arrn, float[] arrf, int n4, float f2, float f3, int n5, int n6, int n7, int n8, int n9, long[] arrl, float[] arrf2) {
        this.f.a(l2, l3, n2, n3, arrn, arrf, n4, f2, f3, n5, n6, n7, n8, n9, arrl, arrf2);
    }

    protected void setFullscreen(final boolean bl) {
        this.e = bl;
        if (q.a) {
            this.b(new Runnable(){

                @Override
                public final void run() {
                    q.i.a((View)UnityPlayer.this, bl);
                }
            });
        }
    }

    protected void showSoftInput(final String string, final int n2, final boolean bl, final boolean bl2, final boolean bl3, final boolean bl4, final String string2) {
        final UnityPlayer unityPlayer = this;
        this.b(new Runnable(){

            @Override
            public final void run() {
                UnityPlayer.this.b = new s((Context)UnityPlayer.this.m, unityPlayer, string, n2, bl, bl2, bl3, string2);
                UnityPlayer.this.b.show();
            }
        });
    }

    protected void hideSoftInput() {
        final Runnable runnable = new Runnable(){

            @Override
            public final void run() {
                if (UnityPlayer.this.b != null) {
                    UnityPlayer.this.b.dismiss();
                    UnityPlayer.this.b = null;
                }
            }
        };
        if (q.g) {
            this.a(new c(){

                @Override
                public final void a() {
                    UnityPlayer.this.b(runnable);
                }
            });
            return;
        }
        this.b(runnable);
    }

    protected void setSoftInputStr(final String string) {
        this.b(new Runnable(){

            @Override
            public final void run() {
                if (UnityPlayer.this.b != null && string != null) {
                    UnityPlayer.this.b.a(string);
                }
            }
        });
    }

    protected void reportSoftInputStr(final String string, final int n2, final boolean bl) {
        if (n2 == 1) {
            this.hideSoftInput();
        }
        this.a(new c(){

            @Override
            public final void a() {
                if (bl) {
                    UnityPlayer.this.nativeSetInputCanceled(true);
                } else if (string != null) {
                    UnityPlayer.this.nativeSetInputString(string);
                }
                if (n2 == 1) {
                    UnityPlayer.this.nativeSoftInputClosed();
                }
            }
        });
    }

    protected int[] initCamera(int n2, int n3, int n4, int n5) {
        com.unity3d.player.a a2 = new com.unity3d.player.a(n2, n3, n4, n5);
        try {
            a2.a(this);
            this.y.add(a2);
            Camera.Size size = a2.b();
            return new int[]{size.width, size.height};
        }
        catch (Exception var2_5) {
            String string = "Unable to initialize camera: " + var2_5.getMessage();
            m.Log(6, string);
            a2.c();
            return null;
        }
    }

    protected void closeCamera(int n2) {
        for (com.unity3d.player.a a2 : this.y) {
            if (a2.a() != n2) continue;
            a2.c();
            this.y.remove(a2);
            return;
        }
    }

    protected int getNumCameras() {
        if (!this.i()) {
            return 0;
        }
        return Camera.getNumberOfCameras();
    }

    @Override
    public void onCameraFrame(final com.unity3d.player.a a2, final byte[] arrby) {
        final int n2 = a2.a();
        final Camera.Size size = a2.b();
        this.a(new c(){

            @Override
            public final void a() {
                UnityPlayer.this.nativeVideoFrameCallback(n2, arrby, size.width, size.height);
                a2.a(arrby);
            }
        });
    }

    protected boolean isCameraFrontFacing(int n2) {
        Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
        Camera.getCameraInfo((int)n2, (Camera.CameraInfo)cameraInfo);
        if (cameraInfo.facing == 1) {
            return true;
        }
        return false;
    }

    protected int getCameraOrientation(int n2) {
        Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
        Camera.getCameraInfo((int)n2, (Camera.CameraInfo)cameraInfo);
        return cameraInfo.orientation;
    }

    protected void showVideoPlayer(final String string, final int n2, final int n3, final int n4, final boolean bl, final int n5, final int n6) {
        this.b(new Runnable(){

            @Override
            public final void run() {
                if (UnityPlayer.this.z != null) {
                    return;
                }
                UnityPlayer.this.pause();
                UnityPlayer.this.z = new w(UnityPlayer.this, (Context)UnityPlayer.this.m, string, n2, n3, n4, bl, n5, n6);
                UnityPlayer.this.addView((View)UnityPlayer.this.z);
                UnityPlayer.this.z.requestFocus();
                UnityPlayer.this.g.d((View)UnityPlayer.this.n);
            }
        });
    }

    protected void hideVideoPlayer() {
        this.b(new Runnable(){

            @Override
            public final void run() {
                if (UnityPlayer.this.z == null) {
                    return;
                }
                UnityPlayer.this.g.c((View)UnityPlayer.this.n);
                UnityPlayer.this.removeView((View)UnityPlayer.this.z);
                UnityPlayer.this.z = null;
                UnityPlayer.this.resume();
            }
        });
    }

    protected void Location_SetDesiredAccuracy(float f2) {
        this.u.b(f2);
    }

    protected void Location_SetDistanceFilter(float f2) {
        this.u.a(f2);
    }

    protected native void nativeSetLocationStatus(int var1);

    protected native void nativeSetLocation(float var1, float var2, float var3, float var4, double var5, float var7);

    protected void Location_StartUpdatingLocation() {
        this.u.b();
    }

    protected void Location_StopUpdatingLocation() {
        this.u.c();
    }

    protected boolean Location_IsServiceEnabledByUser() {
        return this.u.a();
    }

    private boolean i() {
        if (this.m.getPackageManager().hasSystemFeature("android.hardware.camera") || this.m.getPackageManager().hasSystemFeature("android.hardware.camera.front")) {
            return true;
        }
        return false;
    }

    protected int getSplashMode() {
        return this.x.getInt("splash_mode");
    }

    protected void executeGLThreadJobs() {
        Runnable runnable;
        while ((runnable = (Runnable)this.j.poll()) != null) {
            runnable.run();
        }
    }

    protected void disableLogger() {
        m.a = true;
    }

    private void c(Runnable runnable) {
        if (!v.c()) {
            return;
        }
        if (Thread.currentThread() == this.a) {
            runnable.run();
            return;
        }
        this.j.add(runnable);
    }

    private void a(c c2) {
        if (this.isFinishing()) {
            return;
        }
        this.c(c2);
    }

    protected boolean isFinishing() {
        if (this.q || (this.q = this.m instanceof Activity && ((Activity)this.m).isFinishing())) {
            return true;
        }
        return false;
    }

    private void j() {
        if (!this.x.getBoolean("useObb")) {
            return;
        }
        for (String string : UnityPlayer.a((Context)this.m)) {
            String string2 = UnityPlayer.a(string);
            if (this.x.getBoolean(string2)) {
                this.nativeFile(string);
            }
            this.x.remove(string2);
        }
    }

    private static String[] a(Context context) {
        int n2;
        Object object;
        String string = context.getPackageName();
        Vector<String> vector = new Vector<String>();
        try {
            n2 = context.getPackageManager().getPackageInfo((String)string, (int)0).versionCode;
        }
        catch (PackageManager.NameNotFoundException v0) {
            return new String[0];
        }
        if (Environment.getExternalStorageState().equals("mounted")) {
            object = Environment.getExternalStorageDirectory();
            if ((object = new File(object.toString() + "/Android/obb/" + string)).exists()) {
                String string2;
                if (n2 > 0 && new File(string2 = object + File.separator + "main." + n2 + "." + string + ".obb").isFile()) {
                    vector.add(string2);
                }
                if (n2 > 0 && new File(string2 = object + File.separator + "patch." + n2 + "." + string + ".obb").isFile()) {
                    vector.add(string2);
                }
            }
        }
        object = new String[vector.size()];
        vector.toArray((T[])object);
        return object;
    }

    private static String a(String arrby) {
        int n2;
        Object object;
        byte[] arrby2;
        arrby2 = null;
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            object = new FileInputStream((String)arrby);
            long l2 = new File((String)arrby).length();
            object.skip(l2 - Math.min(l2, 65558));
            arrby = new byte[1024];
            n2 = 0;
            while (n2 != -1) {
                messageDigest.update(arrby, 0, n2);
                n2 = object.read(arrby);
            }
            arrby2 = messageDigest.digest();
        }
        catch (FileNotFoundException v0) {
        }
        catch (IOException v1) {
        }
        catch (NoSuchAlgorithmException v2) {}
        if (arrby2 == null) {
            return null;
        }
        object = new StringBuffer();
        for (n2 = 0; n2 < arrby2.length; ++n2) {
            object.append(Integer.toString((arrby2[n2] & 255) + 256, 16).substring(1));
        }
        return object.toString();
    }

    private void k() {
        ((Activity)this.m).getWindow().setFlags(1024, 1024);
    }

    public boolean injectEvent(InputEvent inputEvent) {
        return this.nativeInjectEvent(inputEvent);
    }

    public boolean onKeyUp(int n2, KeyEvent keyEvent) {
        return this.injectEvent((InputEvent)keyEvent);
    }

    public boolean onKeyDown(int n2, KeyEvent keyEvent) {
        return this.injectEvent((InputEvent)keyEvent);
    }

    public boolean onKeyMultiple(int n2, int n3, KeyEvent keyEvent) {
        return this.injectEvent((InputEvent)keyEvent);
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        return this.injectEvent((InputEvent)motionEvent);
    }

    public boolean onGenericMotionEvent(MotionEvent motionEvent) {
        return this.injectEvent((InputEvent)motionEvent);
    }

    static {
        new u().a();
        p = false;
        p = UnityPlayer.loadLibraryStatic("main");
        D = new ReentrantLock();
    }

    private abstract class c
    implements Runnable {
        private c() {
        }

        @Override
        public final void run() {
            if (!UnityPlayer.this.isFinishing()) {
                this.a();
            }
        }

        public abstract void a();

        /* synthetic */ c(byte by) {
            this();
        }
    }

    private final class b
    extends Thread {
        ArrayBlockingQueue a;
        boolean b;

        b() {
            this.b = false;
            this.a = new ArrayBlockingQueue(32);
        }

        @Override
        public final void run() {
            this.setName("UnityMain");
            try {
                a a2;
                block2 : while ((a2 = (a)((Object)this.a.take())) != a.c) {
                    if (a2 == a.b) {
                        this.b = true;
                    } else if (a2 == a.a) {
                        this.b = false;
                        UnityPlayer.this.executeGLThreadJobs();
                    } else if (a2 == a.e && !this.b) {
                        UnityPlayer.this.executeGLThreadJobs();
                    }
                    if (!this.b) continue;
                    do {
                        UnityPlayer.this.executeGLThreadJobs();
                        if (this.a.peek() != null) continue block2;
                        if (UnityPlayer.this.isFinishing() || UnityPlayer.this.nativeRender()) continue;
                        UnityPlayer.this.b();
                    } while (!b.interrupted());
                }
                return;
            }
            catch (InterruptedException v0) {
                return;
            }
        }

        public final void a() {
            this.a(a.c);
        }

        public final void b() {
            this.a(a.b);
        }

        public final void c() {
            this.a(a.a);
        }

        public final void a(boolean bl) {
            this.a(bl ? a.d : a.e);
        }

        private void a(a a2) {
            try {
                this.a.put(a2);
                return;
            }
            catch (InterruptedException v0) {
                this.interrupt();
                return;
            }
        }
    }

    static final class a
    extends Enum {
        public static final /* enum */ a a = new a();
        public static final /* enum */ a b = new a();
        public static final /* enum */ a c = new a();
        public static final /* enum */ a d = new a();
        public static final /* enum */ a e = new a();
        private static final /* synthetic */ a[] f;

        private a() {
            super(string, n2);
        }

        static {
            f = new a[]{a, b, c, d, e};
        }
    }

}

