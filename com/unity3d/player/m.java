/*
 * Decompiled with CFR 0_114.
 * 
 * Could not load the following classes:
 *  android.util.Log
 */
package com.unity3d.player;

import android.util.Log;

final class m {
    protected static boolean a = false;

    protected static void Log(int n2, String string) {
        if (a) {
            return;
        }
        if (n2 == 6) {
            Log.e((String)"Unity", (String)string);
        }
        if (n2 == 5) {
            Log.w((String)"Unity", (String)string);
        }
    }
}

