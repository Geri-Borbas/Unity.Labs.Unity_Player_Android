/*
 * Decompiled with CFR 0_114.
 */
package com.unity3d.player;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

class WWW
extends Thread {
    private int a;
    private int b;
    private String c;
    private byte[] d;
    private Map e;

    WWW(int n2, String string, byte[] arrby, Map map) {
        this.b = n2;
        this.c = string;
        this.d = arrby;
        this.e = map;
        this.a = 0;
        this.start();
    }

    @Override
    public void run() {
        int n2;
        URLConnection uRLConnection;
        Object object2;
        int n3;
        byte[] arrby;
        if (++this.a > 5) {
            WWW.errorCallback(this.b, "Too many redirects");
            return;
        }
        try {
            object2 = new URL(this.c);
            uRLConnection = object2.openConnection();
        }
        catch (MalformedURLException var3_3) {
            WWW.errorCallback(this.b, var3_3.toString());
            return;
        }
        catch (IOException var3_4) {
            WWW.errorCallback(this.b, var3_4.toString());
            return;
        }
        if (object2.getProtocol().equalsIgnoreCase("file") && object2.getHost() != null && object2.getHost().length() != 0) {
            WWW.errorCallback(this.b, object2.getHost() + object2.getFile() + " is not an absolute path!");
            return;
        }
        if (this.e != null) {
            for (Map.Entry object3 : this.e.entrySet()) {
                uRLConnection.addRequestProperty((String)object3.getKey(), (String)object3.getValue());
            }
        }
        int n4 = 1428;
        if (this.d != null) {
            uRLConnection.setDoOutput(true);
            try {
                OutputStream n22 = uRLConnection.getOutputStream();
                n2 = 0;
                while (n2 < this.d.length) {
                    int n5 = Math.min(1428, this.d.length - n2);
                    n22.write(this.d, n2, n5);
                    this.progressCallback(n2 += n5, this.d.length, 0, 0, 0, 0);
                }
            }
            catch (Exception var5_15) {
                WWW.errorCallback(this.b, var5_15.toString());
                return;
            }
        }
        if (uRLConnection instanceof HttpURLConnection) {
            HttpURLConnection httpURLConnection = (HttpURLConnection)uRLConnection;
            try {
                n2 = httpURLConnection.getResponseCode();
            }
            catch (IOException var6_17) {
                WWW.errorCallback(this.b, var6_17.toString());
                return;
            }
            Map<String, List<String>> map = httpURLConnection.getHeaderFields();
            if (!(map == null || n2 != 301 && n2 != 302 || (arrby = map.get("Location")) == null || arrby.isEmpty())) {
                httpURLConnection.disconnect();
                this.c = arrby.get(0);
                this.run();
                return;
            }
        }
        Map<String, List<String>> map = uRLConnection.getHeaderFields();
        n2 = (int)this.headerCallback(map) ? 1 : 0;
        if (!(map != null && map.containsKey("content-length") || uRLConnection.getContentLength() == -1)) {
            int n6 = n2 = n2 != 0 || this.headerCallback("content-length", String.valueOf(uRLConnection.getContentLength())) ? 1 : 0;
        }
        if (!(map != null && map.containsKey("content-type") || uRLConnection.getContentType() == null)) {
            int n7 = n2 = n2 != 0 || this.headerCallback("content-type", uRLConnection.getContentType()) ? 1 : 0;
        }
        if (n2 != 0) {
            WWW.errorCallback(this.b, this.c + " aborted");
            return;
        }
        int n8 = n3 = uRLConnection.getContentLength() > 0 ? uRLConnection.getContentLength() : 0;
        if (object2.getProtocol().equalsIgnoreCase("file") || object2.getProtocol().equalsIgnoreCase("jar")) {
            n4 = n3 == 0 ? 32768 : Math.min(n3, 32768);
        }
        n2 = 0;
        try {
            long l2 = System.currentTimeMillis();
            arrby = new byte[n4];
            object2 = null;
            n4 = 1;
            String string = "";
            if (uRLConnection instanceof HttpURLConnection) {
                HttpURLConnection httpURLConnection = (HttpURLConnection)uRLConnection;
                object2 = httpURLConnection.getErrorStream();
                string = "" + httpURLConnection.getResponseCode() + ": " + httpURLConnection.getResponseMessage();
            }
            if (object2 == null) {
                object2 = uRLConnection.getInputStream();
                n4 = 0;
            }
            int n9 = 0;
            while (n9 != -1) {
                if (this.readCallback(arrby, n9)) {
                    WWW.errorCallback(this.b, this.c + " aborted");
                    return;
                }
                if (n4 == 0) {
                    this.progressCallback(0, 0, n2 += n9, n3, System.currentTimeMillis(), l2);
                }
                n9 = object2.read(arrby);
            }
            if (n4 != 0) {
                WWW.errorCallback(this.b, string);
            }
        }
        catch (Exception var6_20) {
            WWW.errorCallback(this.b, var6_20.toString());
            return;
        }
        this.progressCallback(0, 0, n2, n2, 0, 0);
        WWW.doneCallback(this.b);
    }

    private static native boolean headerCallback(int var0, String var1);

    protected boolean headerCallback(Map object) {
        if (object == null || object.size() == 0) {
            return false;
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (Map.Entry<K, V> entry : object.entrySet()) {
            for (String string22 : (List)entry.getValue()) {
                stringBuilder.append((String)entry.getKey());
                stringBuilder.append(": ");
                stringBuilder.append(string22);
                stringBuilder.append("\r\n");
            }
            if (entry.getKey() != null) continue;
            for (String string22 : (List)entry.getValue()) {
                stringBuilder.append("Status: ");
                stringBuilder.append(string22);
                stringBuilder.append("\r\n");
            }
        }
        return WWW.headerCallback(this.b, stringBuilder.toString());
    }

    protected boolean headerCallback(String string, String string2) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(string);
        stringBuilder.append(": ");
        stringBuilder.append(string2);
        stringBuilder.append("\n\r");
        return WWW.headerCallback(this.b, stringBuilder.toString());
    }

    private static native boolean readCallback(int var0, byte[] var1, int var2);

    protected boolean readCallback(byte[] arrby, int n2) {
        return WWW.readCallback(this.b, arrby, n2);
    }

    private static native void progressCallback(int var0, float var1, float var2, double var3, int var5);

    protected void progressCallback(int n2, int n3, int n4, int n5, long l2, long l3) {
        float f2;
        double d2;
        float f3;
        if (n5 > 0) {
            double d3;
            f2 = (float)n4 / (float)n5;
            f3 = 1.0f;
            n3 = Math.max(n5 - n4, 0);
            d2 = (double)n3 / (d3 = 1000.0 * (double)n4 / Math.max((double)(l2 - l3), 0.1));
            if (Double.isInfinite(d2) || Double.isNaN(d2)) {
                d2 = 0.0;
            }
        } else if (n3 > 0) {
            f2 = 0.0f;
            f3 = n2 / n3;
            d2 = 0.0;
        } else {
            return;
        }
        WWW.progressCallback(this.b, f3, f2, d2, n5);
    }

    private static native void errorCallback(int var0, String var1);

    private static native void doneCallback(int var0);
}

