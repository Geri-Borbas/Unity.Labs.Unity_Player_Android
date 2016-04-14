/*
 * Decompiled with CFR 0_114.
 * 
 * Could not load the following classes:
 *  android.os.Build
 *  android.os.Build$VERSION
 */
package com.unity3d.player;

import android.os.Build;
import com.unity3d.player.c;
import com.unity3d.player.d;
import com.unity3d.player.e;
import com.unity3d.player.f;
import com.unity3d.player.g;
import com.unity3d.player.h;
import com.unity3d.player.i;
import com.unity3d.player.k;
import com.unity3d.player.l;
import com.unity3d.player.n;

public final class q {
    static final boolean a = Build.VERSION.SDK_INT >= 11;
    static final boolean b = Build.VERSION.SDK_INT >= 12;
    static final boolean c = Build.VERSION.SDK_INT >= 14;
    static final boolean d = Build.VERSION.SDK_INT >= 16;
    static final boolean e = Build.VERSION.SDK_INT >= 17;
    static final boolean f = Build.VERSION.SDK_INT >= 19;
    static final boolean g = Build.VERSION.SDK_INT >= 21;
    static final boolean h = Build.VERSION.SDK_INT >= 23;
    static final f i = a ? new d() : null;
    static final e j = b ? new c() : null;
    static final h k = d ? new l() : null;
    static final g l = e ? new k() : null;
    static final i m = h ? new n() : null;
}

