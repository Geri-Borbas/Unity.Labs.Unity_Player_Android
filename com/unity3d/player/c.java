/*
 * Decompiled with CFR 0_114.
 * 
 * Could not load the following classes:
 *  android.view.MotionEvent
 *  android.view.View
 */
package com.unity3d.player;

import android.view.MotionEvent;
import android.view.View;
import com.unity3d.player.e;

public final class c
implements e {
    @Override
    public final boolean a(View view, MotionEvent motionEvent) {
        return view.dispatchGenericMotionEvent(motionEvent);
    }
}

