/*
 * Decompiled with CFR 0_114.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.app.Fragment
 *  android.app.FragmentManager
 *  android.app.FragmentTransaction
 *  android.content.pm.PackageInfo
 *  android.content.pm.PackageManager
 *  android.content.pm.PackageManager$NameNotFoundException
 *  android.content.pm.PermissionInfo
 */
package com.unity3d.player;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PermissionInfo;
import com.unity3d.player.i;
import com.unity3d.player.m;
import java.util.LinkedList;
import java.util.List;

public final class n
implements i {
    @Override
    public final void a(Activity activity, final Runnable runnable) {
        if (activity == null) {
            return;
        }
        try {
            PackageManager packageManager = activity.getPackageManager();
            final FragmentManager fragmentManager = packageManager.getPackageInfo(activity.getPackageName(), 4096);
            if (fragmentManager.requestedPermissions == null) {
                fragmentManager.requestedPermissions = new String[0];
            }
            final LinkedList<String> linkedList = new LinkedList<String>();
            for (String string : fragmentManager.requestedPermissions) {
                try {
                    if (packageManager.getPermissionInfo((String)string, (int)128).protectionLevel != 1 || activity.checkCallingOrSelfPermission(string) == 0) continue;
                    linkedList.add(string);
                    continue;
                }
                catch (PackageManager.NameNotFoundException v0) {
                    m.Log(5, "Failed to get permission info for " + string + ", manifest likely missing custom permission declaration");
                    m.Log(5, "Permission " + string + " ignored");
                }
            }
            if (linkedList.isEmpty()) {
                runnable.run();
                return;
            }
            fragmentManager = activity.getFragmentManager();
            Fragment fragment = new Fragment(){

                public final void onStart() {
                    super.onStart();
                    this.requestPermissions(linkedList.toArray(new String[0]), 15881);
                }

                public final void onRequestPermissionsResult(int n2, String[] arrstring, int[] arrn) {
                    if (n2 != 15881) {
                        return;
                    }
                    for (n2 = 0; n2 < arrstring.length && n2 < arrn.length; ++n2) {
                        m.Log(4, arrstring[n2] + (arrn[n2] == 0 ? " granted" : " denied"));
                    }
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.remove((Fragment)this);
                    fragmentTransaction.commit();
                    runnable.run();
                }
            };
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(0, fragment);
            fragmentTransaction.commit();
            return;
        }
        catch (Exception var3_4) {
            m.Log(6, "Unable to query for permission: " + var3_4.getMessage());
            return;
        }
    }

}

