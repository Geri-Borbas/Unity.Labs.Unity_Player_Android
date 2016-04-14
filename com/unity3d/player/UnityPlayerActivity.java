/*
 * Decompiled with CFR 0_114.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.content.ContextWrapper
 *  android.content.res.Configuration
 *  android.os.Bundle
 *  android.view.InputEvent
 *  android.view.KeyEvent
 *  android.view.MotionEvent
 *  android.view.View
 *  android.view.Window
 */
package com.unity3d.player;

import android.app.Activity;
import android.content.ContextWrapper;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.InputEvent;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import com.unity3d.player.UnityPlayer;

public class UnityPlayerActivity
extends Activity {
    protected UnityPlayer mUnityPlayer;

    protected void onCreate(Bundle bundle) {
        this.requestWindowFeature(1);
        super.onCreate(bundle);
        this.getWindow().setFormat(2);
        this.mUnityPlayer = new UnityPlayer((ContextWrapper)this);
        this.setContentView((View)this.mUnityPlayer);
        this.mUnityPlayer.requestFocus();
    }

    protected void onDestroy() {
        this.mUnityPlayer.quit();
        super.onDestroy();
    }

    protected void onPause() {
        super.onPause();
        this.mUnityPlayer.pause();
    }

    protected void onResume() {
        super.onResume();
        this.mUnityPlayer.resume();
    }

    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        this.mUnityPlayer.configurationChanged(configuration);
    }

    public void onWindowFocusChanged(boolean bl) {
        super.onWindowFocusChanged(bl);
        this.mUnityPlayer.windowFocusChanged(bl);
    }

    public boolean dispatchKeyEvent(KeyEvent keyEvent) {
        if (keyEvent.getAction() == 2) {
            return this.mUnityPlayer.injectEvent((InputEvent)keyEvent);
        }
        return super.dispatchKeyEvent(keyEvent);
    }

    public boolean onKeyUp(int n2, KeyEvent keyEvent) {
        return this.mUnityPlayer.injectEvent((InputEvent)keyEvent);
    }

    public boolean onKeyDown(int n2, KeyEvent keyEvent) {
        return this.mUnityPlayer.injectEvent((InputEvent)keyEvent);
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        return this.mUnityPlayer.injectEvent((InputEvent)motionEvent);
    }

    public boolean onGenericMotionEvent(MotionEvent motionEvent) {
        return this.mUnityPlayer.injectEvent((InputEvent)motionEvent);
    }
}

