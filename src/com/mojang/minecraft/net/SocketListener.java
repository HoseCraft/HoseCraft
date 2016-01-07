package com.mojang.minecraft.net;

import com.mojang.minecraft.server.MinecraftServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.util.LinkedList;
import java.util.List;

public final class SocketListener {

    public ServerSocketChannel a;
    public MinecraftServer b;
    public List c = new LinkedList();


    public SocketListener(int var1, MinecraftServer var2) {
        this.b = var2;
        try {
            this.a = ServerSocketChannel.open();
            this.a.socket().bind(new InetSocketAddress(var1));
            this.a.configureBlocking(false);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
