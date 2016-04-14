/*
 * Decompiled with CFR 0_114.
 */
package com.unity3d.player;

import com.unity3d.player.m;
import com.unity3d.player.q;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.JarURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.security.SecureRandom;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLHandshakeException;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

class UnityWebRequest
implements Runnable {
    private long a;
    private String b;
    private String c;
    private Map d;
    private static final String[] e = new String[]{"TLSv1.2", "TLSv1.1"};
    private static volatile SSLSocketFactory f;

    private static SSLSocketFactory getSSLSocketFactory() {
        if (q.g) {
            return null;
        }
        if (f != null) {
            return f;
        }
        String[] arrstring = e;
        synchronized (arrstring) {
            for (String string : e) {
                try {
                    SSLContext sSLContext = SSLContext.getInstance(string);
                    sSLContext.init(null, null, null);
                    f = sSLContext.getSocketFactory();
                    return f;
                }
                catch (Exception var5_6) {
                    m.Log(5, "UnityWebRequest: No support for " + string + " (" + var5_6.getMessage() + ")");
                    continue;
                }
            }
        }
        return null;
    }

    UnityWebRequest(long l2, String string, Map map, String string2) {
        this.a = l2;
        this.b = string2;
        this.c = string;
        this.d = map;
    }

    @Override
    public void run() {
        URLConnection uRLConnection;
        Object object;
        Object object2;
        int n2;
        Iterator iterator;
        try {
            object2 = new URL(this.b);
            uRLConnection = object2.openConnection();
            if (uRLConnection instanceof HttpsURLConnection && (iterator = UnityWebRequest.getSSLSocketFactory()) != null) {
                ((HttpsURLConnection)uRLConnection).setSSLSocketFactory((SSLSocketFactory)((Object)iterator));
            }
        }
        catch (MalformedURLException var3_5) {
            this.malformattedUrlCallback(var3_5.toString());
            return;
        }
        catch (IOException var3_6) {
            this.errorCallback(var3_6.toString());
            return;
        }
        if (object2.getProtocol().equalsIgnoreCase("file") && !object2.getHost().isEmpty()) {
            this.malformattedUrlCallback("file:// must use an absolute path");
            return;
        }
        if (uRLConnection instanceof JarURLConnection) {
            this.badProtocolCallback("A URL Connection to a Java ARchive (JAR) file or an entry in a JAR file is not supported");
            return;
        }
        if (uRLConnection instanceof HttpURLConnection) {
            try {
                iterator = (HttpURLConnection)uRLConnection;
                iterator.setRequestMethod(this.c);
                iterator.setInstanceFollowRedirects(false);
            }
            catch (ProtocolException var3_7) {
                this.badProtocolCallback(var3_7.toString());
                return;
            }
        }
        if (this.d != null) {
            for (Map.Entry entry : this.d.entrySet()) {
                uRLConnection.addRequestProperty((String)entry.getKey(), (String)entry.getValue());
            }
        }
        int n3 = 1428;
        int n4 = this.uploadCallback(null);
        if (n4 > 0) {
            uRLConnection.setDoOutput(true);
            try {
                ByteBuffer byteBuffer = ByteBuffer.allocateDirect(Math.min(n4, 1428));
                OutputStream outputStream = uRLConnection.getOutputStream();
                object = this.uploadCallback(byteBuffer);
                while (object > 0) {
                    outputStream.write(byteBuffer.array(), byteBuffer.arrayOffset(), (int)object);
                    object = this.uploadCallback(byteBuffer);
                }
            }
            catch (Exception var4_12) {
                this.errorCallback(var4_12.toString());
                return;
            }
        }
        if (uRLConnection instanceof HttpURLConnection) {
            HttpURLConnection httpURLConnection = (HttpURLConnection)uRLConnection;
            try {
                int n5 = httpURLConnection.getResponseCode();
                this.responseCodeCallback(n5);
            }
            catch (UnknownHostException var6_27) {
                this.unknownHostCallback(var6_27.toString());
            }
            catch (IOException var6_28) {
                this.errorCallback(var6_28.toString());
                return;
            }
        }
        Map<String, List<String>> map = uRLConnection.getHeaderFields();
        this.headerCallback(map);
        if (!(map != null && map.containsKey("content-length") || uRLConnection.getContentLength() == -1)) {
            this.headerCallback("content-length", String.valueOf(uRLConnection.getContentLength()));
        }
        if (!(map != null && map.containsKey("content-type") || uRLConnection.getContentType() == null)) {
            this.headerCallback("content-type", uRLConnection.getContentType());
        }
        if ((n2 = uRLConnection.getContentLength()) > 0) {
            this.contentLengthCallback(n2);
        }
        if (object2.getProtocol().equalsIgnoreCase("file")) {
            n3 = n2 == 0 ? 32768 : Math.min(n2, 32768);
        }
        try {
            void var5_20;
            void var5_22;
            Object var5_18 = null;
            if (uRLConnection instanceof HttpURLConnection) {
                HttpURLConnection httpURLConnection = (HttpURLConnection)uRLConnection;
                this.responseCodeCallback(httpURLConnection.getResponseCode());
                InputStream inputStream = httpURLConnection.getErrorStream();
            }
            if (var5_20 == null) {
                InputStream inputStream = uRLConnection.getInputStream();
            }
            object = Channels.newChannel((InputStream)var5_22);
            object2 = ByteBuffer.allocateDirect(n3);
            int n6 = object.read((ByteBuffer)object2);
            while (n6 != -1 && this.downloadCallback((ByteBuffer)object2, n6)) {
                object2.clear();
                n6 = object.read((ByteBuffer)object2);
            }
            object.close();
            return;
        }
        catch (UnknownHostException var5_23) {
            this.unknownHostCallback(var5_23.toString());
            return;
        }
        catch (SSLHandshakeException var5_24) {
            this.sslCannotConnectCallback(var5_24.toString());
            return;
        }
        catch (Exception var5_25) {
            this.errorCallback(var5_25.toString());
            return;
        }
    }

    private static native void headerCallback(long var0, String var2, String var3);

    protected void headerCallback(String string, String string2) {
        UnityWebRequest.headerCallback(this.a, string, string2);
    }

    protected void headerCallback(Map object) {
        if (object == null || object.size() == 0) {
            return;
        }
        object = object.entrySet().iterator();
        while (object.hasNext()) {
            Map.Entry entry = (Map.Entry)object.next();
            String string = (String)entry.getKey();
            if (string == null) {
                string = "Status";
            }
            for (String string2 : (List)entry.getValue()) {
                this.headerCallback(string, string2);
            }
        }
    }

    private static native int uploadCallback(long var0, ByteBuffer var2);

    protected int uploadCallback(ByteBuffer byteBuffer) {
        return UnityWebRequest.uploadCallback(this.a, byteBuffer);
    }

    private static native void contentLengthCallback(long var0, int var2);

    protected void contentLengthCallback(int n2) {
        UnityWebRequest.contentLengthCallback(this.a, n2);
    }

    private static native boolean downloadCallback(long var0, ByteBuffer var2, int var3);

    protected boolean downloadCallback(ByteBuffer byteBuffer, int n2) {
        return UnityWebRequest.downloadCallback(this.a, byteBuffer, n2);
    }

    protected void responseCodeCallback(int n2) {
        UnityWebRequest.responseCodeCallback(this.a, n2);
    }

    private static native void responseCodeCallback(long var0, int var2);

    protected void unknownHostCallback(String string) {
        UnityWebRequest.errorCallback(this.a, 7, string);
    }

    protected void badProtocolCallback(String string) {
        UnityWebRequest.errorCallback(this.a, 4, string);
    }

    protected void malformattedUrlCallback(String string) {
        UnityWebRequest.errorCallback(this.a, 5, string);
    }

    protected void sslCannotConnectCallback(String string) {
        UnityWebRequest.errorCallback(this.a, 16, string);
    }

    protected void errorCallback(String string) {
        UnityWebRequest.errorCallback(this.a, 2, string);
    }

    private static native void errorCallback(long var0, int var2, String var3);
}

