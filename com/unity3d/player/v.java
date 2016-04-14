/*
 * Decompiled with CFR 0_114.
 */
package com.unity3d.player;

import com.unity3d.player.q;

final class v {
    private static boolean a = false;
    private boolean b = !q.h;
    private boolean c = false;
    private boolean d = false;
    private boolean e = true;

    v() {
    }

    static void a() {
        a = true;
    }

    static void b() {
        a = false;
    }

    static boolean c() {
        return a;
    }

    final void d() {
        this.b = true;
    }

    final void a(boolean bl) {
        this.c = bl;
    }

    final void b(boolean bl) {
        this.e = bl;
    }

    final boolean e() {
        return this.e;
    }

    final void c(boolean bl) {
        this.d = bl;
    }

    final boolean f() {
        if (a && this.c && this.b && !this.e && !this.d) {
            return true;
        }
        return false;
    }

    final boolean g() {
        return this.d;
    }

    public final String toString() {
        return super.toString();
    }
}

