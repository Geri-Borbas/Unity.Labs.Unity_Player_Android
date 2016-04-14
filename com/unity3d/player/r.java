/*
 * Decompiled with CFR 0_114.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.hardware.GeomagneticField
 *  android.location.Criteria
 *  android.location.Location
 *  android.location.LocationListener
 *  android.location.LocationManager
 *  android.location.LocationProvider
 *  android.os.Bundle
 *  android.os.Looper
 */
package com.unity3d.player;

import android.content.Context;
import android.hardware.GeomagneticField;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.os.Looper;
import com.unity3d.player.UnityPlayer;
import com.unity3d.player.m;
import java.util.List;

final class r
implements LocationListener {
    private final Context a;
    private final UnityPlayer b;
    private Location c;
    private float d = 0.0f;
    private boolean e = false;
    private int f = 0;
    private boolean g = false;
    private int h = 0;

    protected r(Context context, UnityPlayer unityPlayer) {
        this.a = context;
        this.b = unityPlayer;
    }

    public final boolean a() {
        if (!((LocationManager)this.a.getSystemService("location")).getProviders(new Criteria(), true).isEmpty()) {
            return true;
        }
        return false;
    }

    public final void a(float f2) {
        this.d = f2;
    }

    public final void b(float f2) {
        if (f2 < 100.0f) {
            this.f = 1;
            return;
        }
        if (f2 < 500.0f) {
            this.f = 1;
            return;
        }
        this.f = 2;
    }

    public final void b() {
        this.g = false;
        if (this.e) {
            m.Log(5, "Location_StartUpdatingLocation already started!");
            return;
        }
        if (!this.a()) {
            this.a(3);
            return;
        }
        LocationManager locationManager = (LocationManager)this.a.getSystemService("location");
        this.a(1);
        List list = locationManager.getProviders(true);
        if (list.isEmpty()) {
            this.a(3);
            return;
        }
        String string = null;
        if (this.f == 2) {
            for (String string2 : list) {
                if ((string2 = locationManager.getProvider(string2)).getAccuracy() != 2) continue;
                string = string2;
                break;
            }
        }
        for (String string2 : list) {
            if (string != null && locationManager.getProvider(string2).getAccuracy() == 1) continue;
            this.a(locationManager.getLastKnownLocation(string2));
            locationManager.requestLocationUpdates(string2, 0, this.d, (LocationListener)this, this.a.getMainLooper());
            this.e = true;
        }
    }

    public final void c() {
        ((LocationManager)this.a.getSystemService("location")).removeUpdates((LocationListener)this);
        this.e = false;
        this.c = null;
        this.a(0);
    }

    public final void d() {
        if (this.h == 1 || this.h == 2) {
            this.g = true;
            this.c();
        }
    }

    public final void e() {
        if (this.g) {
            this.b();
        }
    }

    public final void onLocationChanged(Location location) {
        this.a(2);
        this.a(location);
    }

    public final void onStatusChanged(String string, int n2, Bundle bundle) {
    }

    public final void onProviderEnabled(String string) {
    }

    public final void onProviderDisabled(String string) {
        this.c = null;
    }

    private void a(Location location) {
        if (location == null) {
            return;
        }
        if (r.a(location, this.c)) {
            this.c = location;
            GeomagneticField geomagneticField = new GeomagneticField((float)this.c.getLatitude(), (float)this.c.getLongitude(), (float)this.c.getAltitude(), this.c.getTime());
            this.b.nativeSetLocation((float)location.getLatitude(), (float)location.getLongitude(), (float)location.getAltitude(), location.getAccuracy(), (double)location.getTime() / 1000.0, geomagneticField.getDeclination());
        }
    }

    private static boolean a(Location location, Location location2) {
        boolean bl;
        if (location2 == null) {
            return true;
        }
        long l2 = location.getTime() - location2.getTime();
        boolean bl2 = l2 > 120000;
        boolean bl3 = l2 < -120000;
        boolean bl4 = bl = l2 > 0;
        if (bl2) {
            return true;
        }
        if (bl3) {
            return false;
        }
        int n2 = (int)(location.getAccuracy() - location2.getAccuracy());
        bl2 = n2 > 0;
        bl3 = n2 < 0;
        n2 = n2 > 200 | location.getAccuracy() == 0.0f;
        boolean bl5 = r.a(location.getProvider(), location2.getProvider());
        if (bl3) {
            return true;
        }
        if (bl && !bl2) {
            return true;
        }
        if (bl && n2 == 0 && bl5) {
            return true;
        }
        return false;
    }

    private static boolean a(String string, String string2) {
        if (string == null) {
            if (string2 == null) {
                return true;
            }
            return false;
        }
        return string.equals(string2);
    }

    private void a(int n2) {
        this.h = n2;
        this.b.nativeSetLocationStatus(n2);
    }
}

