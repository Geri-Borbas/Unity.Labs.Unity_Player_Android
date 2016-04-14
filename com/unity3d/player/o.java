/*
 * Decompiled with CFR 0_114.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.content.ComponentName
 *  android.content.pm.ActivityInfo
 *  android.content.pm.PackageManager
 *  android.content.pm.PackageManager$NameNotFoundException
 *  android.os.Bundle
 */
package com.unity3d.player;

import android.app.Activity;
import android.content.ComponentName;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import com.unity3d.player.m;

public final class o {
    private final Bundle a;

    public o(Activity activity) {
        Bundle bundle = Bundle.EMPTY;
        PackageManager packageManager = activity.getPackageManager();
        activity = activity.getComponentName();
        try {
            packageManager = packageManager.getActivityInfo((ComponentName)activity, 128);
            if (packageManager != null && packageManager.metaData != null) {
                bundle = packageManager.metaData;
            }
        }
        catch (PackageManager.NameNotFoundException v0) {
            m.Log(6, "Unable to retreive meta data for activity '" + (Object)activity + "'");
        }
        this.a = new Bundle(bundle);
    }

    public final boolean a() {
        return this.a.getBoolean(o.a("ForwardNativeEventsToDalvik"));
    }

    private static String a(String string) {
        return String.format("%s.%s", "unityplayer", string);
    }
}

