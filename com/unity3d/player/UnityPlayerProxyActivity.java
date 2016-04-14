/*
 * Decompiled with CFR 0_114.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.content.Context
 *  android.content.Intent
 *  android.os.Bundle
 */
package com.unity3d.player;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.unity3d.player.UnityPlayerActivity;

public class UnityPlayerProxyActivity
extends Activity {
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        bundle = new Intent((Context)this, (Class)UnityPlayerActivity.class);
        bundle.addFlags(65536);
        Bundle bundle2 = this.getIntent().getExtras();
        if (bundle2 != null) {
            bundle.putExtras(bundle2);
        }
        this.startActivity((Intent)bundle);
    }
}

