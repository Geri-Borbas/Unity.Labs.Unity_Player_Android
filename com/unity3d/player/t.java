/*
 * Decompiled with CFR 0_114.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.view.View
 *  android.view.ViewGroup
 */
package com.unity3d.player;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import java.util.HashSet;
import java.util.Set;

final class t {
    public static t a;
    private final ViewGroup b;
    private Set c = new HashSet();
    private View d;
    private View e;

    t(ViewGroup viewGroup) {
        this.b = viewGroup;
        a = this;
    }

    public final Context a() {
        return this.b.getContext();
    }

    public final void a(View view) {
        this.c.add(view);
        if (this.d != null) {
            this.e(view);
        }
    }

    public final void b(View view) {
        this.c.remove((Object)view);
        if (this.d != null) {
            this.f(view);
        }
    }

    public final void c(View object) {
        if (this.d != object) {
            this.d = object;
            this.b.addView((View)object);
            for (View view : this.c) {
                this.e(view);
            }
            if (this.e != null) {
                this.e.setVisibility(4);
            }
        }
    }

    public final void d(View view) {
        if (this.d == view) {
            for (View view2 : this.c) {
                this.f(view2);
            }
            this.b.removeView(view);
            this.d = null;
            if (this.e != null) {
                this.e.setVisibility(0);
            }
        }
    }

    private void e(View view) {
        this.b.addView(view, this.b.getChildCount());
    }

    private void f(View view) {
        this.b.removeView(view);
        this.b.requestLayout();
    }
}

