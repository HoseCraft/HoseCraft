package com.mojang.minecraft.server;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

final class HeartBeat extends Thread {

    // $FF: synthetic field
    private String a;
    // $FF: synthetic field
    private MinecraftServer b;


    HeartBeat(MinecraftServer var1, String var2) {
        super();
        this.b = var1;
        this.a = var2;
    }

    public final void run() {
        HttpURLConnection var1 = null;

        try {
            (var1 = (HttpURLConnection) (new URL(this.b.heartbeatURL)).openConnection()).setRequestMethod("POST");
            var1.setDoOutput(true);
            var1.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            var1.setRequestProperty("Content-Length", "" + Integer.toString(this.a.getBytes().length));
            var1.setRequestProperty("Content-Language", "en-US");
            var1.setUseCaches(false);
            var1.setDoInput(true);
            var1.setDoOutput(true);
            var1.connect();
            DataOutputStream var2;
            (var2 = new DataOutputStream(var1.getOutputStream())).writeBytes(this.a);
            var2.flush();
            var2.close();
            BufferedReader var9;
            String var3 = (var9 = new BufferedReader(new InputStreamReader(var1.getInputStream()))).readLine();
            if (!MinecraftServer.b(this.b).equals(var3)) {
                MinecraftServer.a.info("To connect directly to this server, surf to: " + var3);
                PrintWriter var4;
                (var4 = new PrintWriter(new FileWriter("externalurl.txt"))).println(var3);
                var4.close();
                MinecraftServer.a.info("(This is also in externalurl.txt)");
                MinecraftServer.a(this.b, var3);
            }

            var9.close();
        } catch (Exception var7) {
            MinecraftServer.a.severe("Failed to assemble heartbeat: " + var7);
            var7.printStackTrace();
        } finally {
            if (var1 != null) {
                var1.disconnect();
            }

        }

    }
}
