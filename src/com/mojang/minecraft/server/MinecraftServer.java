package com.mojang.minecraft.server;

import com.infermc.hosecraft.command.CommandSource;
import com.infermc.hosecraft.command.ConsoleSource;
import com.infermc.hosecraft.events.player.PlayerKickEvent;
import com.infermc.hosecraft.plugins.Plugin;
import com.infermc.hosecraft.server.Server;
import com.mojang.minecraft.level.LevelLoader;
import com.mojang.minecraft.level.generator.LevelGenerator;
import com.mojang.minecraft.net.NetworkManager;
import com.mojang.minecraft.net.PacketType;
import com.mojang.minecraft.net.SocketListener;

import java.io.*;
import java.net.URLEncoder;
import java.nio.channels.SocketChannel;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MinecraftServer implements Runnable {

    static Logger a = Logger.getLogger("MinecraftServer");
    static DateFormat b = new SimpleDateFormat("HH:mm:ss");
    private SocketListener l;
    private Map m = new HashMap();
    private List n = new ArrayList();
    private List o = new ArrayList();
    private int p;
    private Properties q = new Properties();
    public com.mojang.minecraft.level.Level c;
    private boolean r = false;
    public String d;
    public String e;
    private int s;
    public boolean f;
    private HandleClient[] t;
    public ConfigurationFile g = new ConfigurationFile("Admins", new File("admins.txt"));
    public ConfigurationFile h = new ConfigurationFile("Banned", new File("banned.txt"));
    private ConfigurationFile u = new ConfigurationFile("Banned (IP)", new File("banned-ip.txt"));
    public ConfigurationFile i = new ConfigurationFile("Players", new File("players.txt"));
    private List v = new ArrayList();
    private String w = "" + (new Random()).nextLong();
    private String x = "";
    public GenerateMD5 j;
    public boolean k;
    private boolean y;
    private int z;

    // HoseCraft stuff
    public Server HoseCraft = new Server(this);
    public File workingDirectory = new File(System.getProperty("user.dir"));
    public String heartbeatURL;
    public String worldName;
    public String worldGenerator;

    public MinecraftServer() {
        this.j = new GenerateMD5(this.w);
        this.k = false;
        this.y = false;

        a.info("Starting Classic Server running HoseCraft v" + HoseCraft.getVersion() + "-" + HoseCraft.getFlavour());

        try {
            this.q.load(new FileReader("server.properties"));
        } catch (Exception var4) {
            a.warning("Failed to load server.properties!");
        }

        try {
            this.d = this.q.getProperty("server-name", "Minecraft Server");
            this.e = this.q.getProperty("motd", "Welcome to my Minecraft Server!");
            this.s = Integer.parseInt(this.q.getProperty("port", "25565"));
            this.p = Integer.parseInt(this.q.getProperty("max-players", "16"));
            this.r = Boolean.parseBoolean(this.q.getProperty("public", "true"));
            this.k = Boolean.parseBoolean(this.q.getProperty("verify-names", "true"));
            this.y = Boolean.parseBoolean(this.q.getProperty("grow-trees", "false"));
            this.f = Boolean.parseBoolean(this.q.getProperty("admin-slot", "false"));
            this.worldName = this.q.getProperty("world-name", "world");
            this.worldGenerator = this.q.getProperty("world-generator", "ClassicGenerator");
            this.heartbeatURL = this.q.getProperty("heartbeat", "http://www.classicube.net/heartbeat.jsp");
            if (this.p < 1) {
                this.p = 1;
            }

            if (this.p > 32) {
                this.p = 32;
            }

            this.z = Integer.parseInt(this.q.getProperty("max-connections", "3"));
            this.q.setProperty("server-name", this.d);
            this.q.setProperty("motd", this.e);
            this.q.setProperty("max-players", "" + this.p);
            this.q.setProperty("port", "" + this.s);
            this.q.setProperty("public", "" + this.r);
            this.q.setProperty("verify-names", "" + this.k);
            this.q.setProperty("max-connections", "3");
            this.q.setProperty("grow-trees", "" + this.y);
            this.q.setProperty("admin-slot", "" + this.f);
            this.q.setProperty("world-name", this.worldName);
            this.q.setProperty("world-generator", this.worldGenerator);
            this.q.setProperty("heartbeat", "" + this.heartbeatURL);
        } catch (Exception var3) {
            var3.printStackTrace();
            a.warning("server.properties is broken! Delete it or fix it!");
            System.exit(0);
        }

        if (!this.k) {
            a.warning("######################### WARNING #########################");
            a.warning("verify-names is set to false! This means that anyone who");
            a.warning("connects to this server can choose any username he or she");
            a.warning("wants! This includes impersonating an OP!");
            if (this.r) {
                a.warning("");
                a.warning("AND SINCE THIS IS A PUBLIC SERVER, IT WILL HAPPEN TO YOU!");
                a.warning("");
            }

            a.warning("If you wish to fix this, edit server.properties, and change");
            a.warning("verify-names to true.");
            a.warning("###########################################################");
        }

        try {
            this.q.store(new FileWriter("server.properties"), "Minecraft server properties");
        } catch (Exception var2) {
            a.warning("Failed to save server.properties!");
        }

        this.t = new HandleClient[this.p];
        this.l = new SocketListener(this.s, this);
        (new ConsoleInput(this)).start();
    }

    public final void a(NetworkManager var1) {
        HandleClient var2;
        if ((var2 = (HandleClient) this.m.get(var1)) != null) {
            this.i.b(var2.b);
            a.info(var2 + " disconnected");
            this.m.remove(var2.NetworkManager);
            this.n.remove(var2);
            if (var2.c >= 0) {
                this.t[var2.c] = null;
            }

            this.a(PacketType.DESPAWN_PLAYER, new Object[]{Integer.valueOf(var2.c)});
        }

    }

    private void b(NetworkManager var1) {
        this.o.add(new IntegerStorage(var1, 100));
    }

    public final void a(HandleClient var1) {
        this.o.add(new IntegerStorage(var1.NetworkManager, 100));
    }

    public static void b(HandleClient var0) {
        var0.NetworkManager.a();
    }

    public final void a(PacketType var1, Object... var2) {
        for (int var3 = 0; var3 < this.n.size(); ++var3) {
            try {
                ((HandleClient) this.n.get(var3)).b(var1, var2);
            } catch (Exception var5) {
                ((HandleClient) this.n.get(var3)).a(var5);
            }
        }

    }

    public final void a(HandleClient var1, PacketType var2, Object... var3) {
        for (int var4 = 0; var4 < this.n.size(); ++var4) {
            if (this.n.get(var4) != var1) {
                try {
                    ((HandleClient) this.n.get(var4)).b(var2, var3);
                } catch (Exception var6) {
                    ((HandleClient) this.n.get(var4)).a(var6);
                }
            }
        }

    }


    // Startup Method with running loop.
    public void run() {
        a.info("Now accepting input on " + this.s);
        int var1 = 50000000;
        int var2 = 500000000;


        try {

            long var3 = System.nanoTime();
            long var5 = System.nanoTime();
            int var7 = 0;

            // Started? Enable plugins.
            a.info("Enabling plugins...");
            if (HoseCraft.getPluginManager().getPlugins().size() > 0) {
                for (Plugin pl : HoseCraft.getPluginManager().getPlugins()) {
                    a.info("Enabling " + pl.getName() + "..");

                    try {
                        pl.setEnabled(true);
                    } catch (Throwable e1) {
                        a.info("Error enabling "+ pl.getName() +"!");
                        e1.printStackTrace();
                    }
                }
            }

            while (true) {

                this.d();

                for (; System.nanoTime() - var5 > (long) var1; ++var7) {
                    var5 += (long) var1;
                    this.c();
                    if (var7 % 1200 == 0) {
                        MinecraftServer var8 = this;

                        try {
                            new LevelLoader(var8);
                            LevelLoader.a(var8.c, new FileOutputStream(this.worldName + "/level.dat"));
                        } catch (Exception var10) {
                            a.severe("Failed to save the level! " + var10);
                        }

                        a.info("Level saved! Load: " + this.n.size() + "/" + this.p);
                    }

                    if (var7 % 900 == 0) {
                        HashMap var9;
                        (var9 = new HashMap()).put("name", this.d);
                        var9.put("users", Integer.valueOf(this.n.size()));
                        var9.put("max", Integer.valueOf(this.p - (this.f ? 1 : 0)));
                        var9.put("public", Boolean.valueOf(this.r));
                        var9.put("port", Integer.valueOf(this.s));
                        var9.put("salt", this.w);
                        var9.put("admin-slot", Boolean.valueOf(this.f));
                        var9.put("version", Byte.valueOf((byte) 7));
                        var9.put("software", "HoseCraft v" + this.HoseCraft.getVersion());
                        String var12 = a((Map) var9);
                        (new HeartBeat(this, var12)).start();
                    }
                }

                while (System.nanoTime() - var3 > (long) var2) {
                    var3 += (long) var2;
                    this.a(PacketType.PING, new Object[0]);
                }

                Thread.sleep(5L);
            }
        } catch (Exception var11) {
            a.log(Level.SEVERE, "Error in main loop, server shutting down!", var11);
            var11.printStackTrace();
        }
    }

    private static String a(Map var0) {
        try {
            String var1 = "";

            String var3;
            for (Iterator var2 = var0.keySet().iterator(); var2.hasNext(); var1 = var1 + var3 + "=" + URLEncoder.encode(var0.get(var3).toString(), "UTF-8")) {
                var3 = (String) var2.next();
                if (var1 != "") {
                    var1 = var1 + "&";
                }
            }

            return var1;
        } catch (Exception var4) {
            var4.printStackTrace();
            throw new RuntimeException("Failed to assemble heartbeat! This is pretty fatal");
        }
    }

    private void c() {
        Iterator var1 = this.n.iterator();

        while (var1.hasNext()) {
            HandleClient var2 = (HandleClient) var1.next();

            try {
                var2.a();
            } catch (Exception var8) {
                var2.a(var8);
            }
        }

        this.c.tick();

        for (int var9 = 0; var9 < this.o.size(); ++var9) {
            IntegerStorage var10 = (IntegerStorage) this.o.get(var9);
            this.a(var10.NetworkManager);

            try {
                NetworkManager var3 = var10.NetworkManager;

                try {
                    if (var3.d.position() > 0) {
                        var3.d.flip();
                        var3.b.write(var3.d);
                        var3.d.compact();
                    }
                } catch (IOException var6) {
                    ;
                }

                if (var10.b-- <= 0) {
                    try {
                        var10.NetworkManager.a();
                    } catch (Exception var5) {
                        ;
                    }

                    this.o.remove(var9--);
                }
            } catch (Exception var7) {
                try {
                    var10.NetworkManager.a();
                } catch (Exception var4) {
                    ;
                }
            }
        }

    }

    public final void a(String var1) {
        a.info(var1);
    }

    public final void b(String var1) {
        a.fine(var1);
    }

    private void d() {
        List var1 = this.v;
        synchronized (this.v) {
            while (this.v.size() > 0) {
                this.a((HandleClient) null, (String) this.v.remove(0));
            }
        }

        try {
            SocketListener var13 = this.l;

            SocketChannel var14;
            while ((var14 = var13.a.accept()) != null) {
                try {
                    var14.configureBlocking(false);
                    NetworkManager var2 = new NetworkManager(var14);
                    var13.c.add(var2);
                    NetworkManager var4 = var2;
                    MinecraftServer var3 = var13.b;
                    if (var13.b.u.c(var2.f)) {
                        var2.a(PacketType.DISCONNECT, new Object[]{"You\'re banned!"});
                        a.info(var2.f + " tried to connect, but is banned.");
                        var3.b(var2);
                    } else {
                        int var5 = 0;
                        Iterator var6 = var3.n.iterator();

                        while (var6.hasNext()) {
                            if (((HandleClient) var6.next()).NetworkManager.f.equals(var4.f)) {
                                ++var5;
                            }
                        }

                        if (var5 >= var3.z) {
                            var4.a(PacketType.DISCONNECT, new Object[]{"Too many connection!"});
                            a.info(var4.f + " tried to connect, but is already connected " + var5 + " times.");
                            var3.b(var4);
                        } else {
                            int var22;
                            if ((var22 = var3.e()) < 0) {
                                var4.a(PacketType.DISCONNECT, new Object[]{"The server is full!"});
                                a.info(var4.f + " tried to connect, but failed because the server was full.");
                                var3.b(var4);
                            } else {
                                HandleClient var16 = new HandleClient(var3, var4, var22);
                                a.info(var16 + " connected");
                                var3.m.put(var4, var16);
                                var3.n.add(var16);
                                if (var16.c >= 0) {
                                    var3.t[var16.c] = var16;
                                }
                            }
                        }
                    }
                } catch (IOException var10) {
                    var14.close();
                    throw var10;
                }
            }

            for (int var17 = 0; var17 < var13.c.size(); ++var17) {
                NetworkManager var15 = (NetworkManager) var13.c.get(var17);

                try {
                    NetworkManager var18 = var15;
                    var15.b.read(var15.c);
                    int var19 = 0;

                    while (var18.c.position() > 0 && var19++ != 100) {
                        var18.c.flip();
                        byte var20 = var18.c.get(0);
                        PacketType var24;

                        // Do we know what this packet even is?
                        if ((var24 = PacketType.packets[var20]) == null) {
                            throw new IOException("Bad command: " + var20);
                        }

                        if (var18.c.remaining() < var24.length + 1) {
                            var18.c.compact();
                            break;
                        }

                        // Unsure
                        var18.c.get();
                        Object[] var21 = new Object[var24.params.length];
                        for (int var7 = 0; var7 < var21.length; ++var7) {
                            var21[var7] = var18.a(var24.params[var7]);
                        }

                        var18.e.a(var24, var21);
                        if (!var18.a) {
                            break;
                        }

                        var18.c.compact();
                    }

                    if (var18.d.position() > 0) {
                        var18.d.flip();
                        var18.b.write(var18.d);
                        var18.d.compact();
                    }
                } catch (Exception var9) {
                    MinecraftServer var10001 = var13.b;
                    HandleClient var23;
                    if ((var23 = (HandleClient) var13.b.m.get(var15)) != null) {
                        var23.a(var9);
                    }
                }

                try {
                    if (!var15.a) {
                        var15.a();
                        var13.b.a(var15);
                        var13.c.remove(var17--);
                    }
                } catch (Exception var8) {
                    var8.printStackTrace();
                }
            }

        } catch (IOException var11) {
            throw new RuntimeException("IOException while ticking socketserver", var11);
        }
    }

    public final void a(HandleClient var1, String var2) {
        String fullCommand = var2;
        while (var2.startsWith("/")) {
            var2 = var2.substring(1);
        }

        String[] var3 = var2.split(" ");
        String[] args = Arrays.copyOfRange(var3, 1, var3.length);

        CommandSource source;
        if (var1 == null) {
            source = new ConsoleSource(this.HoseCraft);
        } else {
            source = var1.player;
            this.a.info(var1.player.getName() + ": /" + fullCommand);
        }
        this.HoseCraft.getCommandRegistry().runCommand(source, var3[0], args);
        return;
        /*
		CommandEvent ev = new CommandEvent(source,var3[0],args);
		this.HoseCraft.getPluginManager().callEvent(ev);
		if (ev.isCancelled()) {
			return;
		}

		// This all needs replacing with command classes
		a.info((var1 == null?"[console]":var1.b) + " admins: " + var2);
		if (var1 == null || var1.player.isOperator()) {
			if (var3[0].toLowerCase().equals("ban") && var3.length > 1) {
				this.e(var3[1]); // DONE
			} else if (var3[0].toLowerCase().equals("kick") && var3.length > 1) {
				this.d(var3[1]); // DONE
			} else if (var3[0].toLowerCase().equals("banip") && var3.length > 1) {
				this.h(var3[1]); // AWAITING
			} else if (var3[0].toLowerCase().equals("unban") && var3.length > 1) {
				String var5 = var3[1];
				this.h.b(var5); // DONE
			} else if (var3[0].toLowerCase().equals("op") && var3.length > 1) {
				this.f(var3[1]); // DONE
			} else if (var3[0].toLowerCase().equals("deop") && var3.length > 1) {
				this.g(var3[1]); // DONE
			} else if (var3[0].toLowerCase().equals("setspawn")) {
				if (var1 != null) {
					this.c.setSpawnPos(var1.d / 32, var1.e / 32, var1.f / 32, (float) (var1.h * 320 / 256));
				} else {
					a.info("Can\'t set spawn from console!");
				}
			} else {
				if (var3[0].toLowerCase().equals("solid")) {
					// Done
					if (var1 != null) {
						var1.i = !var1.i;
						if (var1.i) {
							var1.b("Now placing unbreakable stone");
							return;
						}

						var1.b("Now placing normal stone");
						return;
					}
				} else {
					if (var3[0].toLowerCase().equals("broadcast") && var3.length > 1) {
						// Done
						this.a(PacketType.CHAT_MESSAGE, new Object[]{Integer.valueOf(-1), var2.substring("broadcast ".length()).trim()});
						return;
					}

					if (var3[0].toLowerCase().equals("say") && var3.length > 1) {
						// Done
						this.a(PacketType.CHAT_MESSAGE, new Object[]{Integer.valueOf(-1), var2.substring("say ".length()).trim()});
						return;
					}

					if ((var3[0].toLowerCase().equals("teleport") || var3[0].toLowerCase().equals("tp")) && var3.length > 1) {
						// Done
						if (var1 == null) {
							a.info("Can\'t teleport from console!");
							return;
						}

						HandleClient var4;
						if ((var4 = this.c(var3[1])) == null) {
							var1.b(PacketType.CHAT_MESSAGE, new Object[]{Integer.valueOf(-1), "No such player"});
							return;
						}
						// Assuming Teleport
						var1.NetworkManager.a(PacketType.POSITION_UPDATE, new Object[]{Integer.valueOf(-1), Integer.valueOf(var4.d), Integer.valueOf(var4.e), Integer.valueOf(var4.f), Integer.valueOf(var4.h), Integer.valueOf(var4.g)});
					} else if (var1 != null) {
						var1.b(PacketType.CHAT_MESSAGE, new Object[]{Integer.valueOf(-1), "Unknown command!"});
					}
				}

			}
		} else {
			var1.b(PacketType.CHAT_MESSAGE, new Object[]{Integer.valueOf(-1), "Unknown command!"});
		}
		*/
    }

    public final void a(int var1, int var2, int var3) {
        this.a(PacketType.BLOCK_CHANGE, new Object[]{Integer.valueOf(var1), Integer.valueOf(var2), Integer.valueOf(var3), Integer.valueOf(this.c.getTile(var1, var2, var3))});
    }

    public final int a() {
        int var1 = 0;

        for (int var2 = 0; var2 < this.p; ++var2) {
            if (this.t[var2] == null) {
                ++var1;
            }
        }

        return var1;
    }

    private int e() {
        for (int var1 = 0; var1 < this.p; ++var1) {
            if (this.t[var1] == null) {
                return var1;
            }
        }

        return -1;
    }

    public final List b() {
        return this.n;
    }

    public boolean kick(String var1, String reason) {
        boolean var2 = false;
        Iterator var3 = this.n.iterator();

        while (var3.hasNext()) {
            HandleClient var4;
            if ((var4 = (HandleClient) var3.next()).b.equalsIgnoreCase(var1)) {
                var2 = true;
                if (reason == null) {
                    var4.a("You were kicked");
                } else {
                    var4.a("Kicked: " + reason);
                }
            }
        }

        if (var2) {
            this.a(PacketType.CHAT_MESSAGE, new Object[]{Integer.valueOf(-1), var1 + " got kicked from the server!"});
        }

        // If they were online and kicked
        return var2;
    }

    public boolean ban(String var1, String reason) {
        boolean res = false;
        boolean var2 = false;
        if (!this.h.c(var1)) {
            this.h.a(var1);
            res = true;
        }

        Iterator var3 = this.n.iterator();

        while (var3.hasNext()) {
            HandleClient var4;
            if ((var4 = (HandleClient) var3.next()).b.equalsIgnoreCase(var1)) {
                var2 = true;
                res = true;
                if (reason == null) {
                    var4.a("You were banned");
                } else {
                    var4.a("Banned: " + reason);
                }
            }
        }

        if (var2) {
            this.a(PacketType.CHAT_MESSAGE, new Object[]{Integer.valueOf(-1), var1 + " got banned!"});
        }
        return res;
    }

    public void opPlayer(String var1) {
        this.g.a(var1);
        Iterator var3 = this.n.iterator();

        while (var3.hasNext()) {
            HandleClient var2;
            if ((var2 = (HandleClient) var3.next()).b.equalsIgnoreCase(var1)) {
                var2.b("You\'re now op!");
                var2.b(PacketType.UPDATE_PLAYER_TYPE, new Object[]{Integer.valueOf(100)});
            }
        }

    }

    public void deopPlayer(String var1) {
        this.g.b(var1);
        Iterator var3 = this.n.iterator();

        while (var3.hasNext()) {
            HandleClient var2;
            if ((var2 = (HandleClient) var3.next()).b.equalsIgnoreCase(var1)) {
                var2.i = false;
                var2.b("You\'re no longer op!");
                var2.b(PacketType.UPDATE_PLAYER_TYPE, new Object[]{Integer.valueOf(0)});
            }
        }

    }

    private void ipBan(String var1) {
        boolean var2 = false;
        String var3 = "";
        Iterator var4 = this.n.iterator();

        while (var4.hasNext()) {
            HandleClient var5;
            NetworkManager var6;
            if (!(var5 = (HandleClient) var4.next()).b.equalsIgnoreCase(var1)) {
                var6 = var5.NetworkManager;
                if (!var5.NetworkManager.f.equalsIgnoreCase(var1)) {
                    var6 = var5.NetworkManager;
                    if (!var5.NetworkManager.f.equalsIgnoreCase("/" + var1)) {
                        continue;
                    }
                }
            }

            var6 = var5.NetworkManager;
            this.u.a(var5.NetworkManager.f);
            var5.a("You were banned");
            if (var3 == "") {
                var3 = var3 + ", ";
            }

            var3 = var3 + var5.b;
            var2 = true;
        }

        if (var2) {
            this.a(PacketType.CHAT_MESSAGE, new Object[]{Integer.valueOf(-1), var3 + " got ip banned!"});
        }

    }

    public final HandleClient c(String var1) {
        Iterator var3 = this.n.iterator();

        HandleClient var2;
        do {
            if (!var3.hasNext()) {
                return null;
            }
        } while (!(var2 = (HandleClient) var3.next()).b.equalsIgnoreCase(var1));

        return var2;
    }

    public static void main(String[] var0) {
        try {

            MinecraftServer var6;
            MinecraftServer var1 = var6 = new MinecraftServer();
            a.info("Setting up");

            a.info("Loading Plugins...");

            List<Plugin> plugins = new ArrayList<Plugin>();
            File pluginDir = new File(var1.workingDirectory + "/plugins");
            if (!pluginDir.exists()) pluginDir.mkdir();
            try {
                plugins = var1.HoseCraft.getPluginManager().loadPlugins(pluginDir);
            } catch (FileNotFoundException e1) {
                e1.printStackTrace();
            }

            if (plugins.size() > 0) {
                for (Plugin pl : plugins) {
                    pl.onLoad();
                }
            }

            File worldDir = new File(var1.worldName + "/");
            File worldFile = new File(var1.worldName + "/level.dat");
            if (worldDir.exists() && worldFile.exists()) {
                try {
                    var1.c = (new LevelLoader(var1)).a(new FileInputStream(worldFile));
                } catch (Exception var4) {
                    a.warning("Failed to load level. Generating new level");
                    var4.printStackTrace();
                }
            } else {
                worldDir.mkdir();
                a.warning("No level file found. Generating new level");
            }

            // Generating new level?
            if (var1.c == null) {
                LevelGenerator levelGenerator = var1.HoseCraft.getLevelGenerator(var1.worldGenerator);
                if (levelGenerator == null) {
                    a.severe("Unable to load level generator '"+var1.worldGenerator+"'");
                } else {
                    a.warning("Using level generator '"+ var1.worldGenerator+"'");
                }
                var1.c = levelGenerator.generate("--", 256, 256, 64);
            }

            try {
                new LevelLoader(var1);
                LevelLoader.a(var1.c, new FileOutputStream(worldFile));
            } catch (Exception var3) {
                // Do nothing apparently. Okay notch.
            }

            var1.c.creativeMode = true;
            var1.c.growTrees = var1.y;
            var1.c.addListener$74652038(var1);

            (new Thread(var6)).start();
        } catch (Exception var5) {
            a.severe("Failed to start the server!");
            var5.printStackTrace();
        }
    }

    // $FF: synthetic method
    static List a(MinecraftServer var0) {
        return var0.v;
    }

    // $FF: synthetic method
    static String b(MinecraftServer var0) {
        return var0.x;
    }

    // $FF: synthetic method
    static String a(MinecraftServer var0, String var1) {
        return var0.x = var1;
    }

    static {
        TimestampFormatter var0 = new TimestampFormatter();
        Handler[] var1;
        int var2 = (var1 = a.getParent().getHandlers()).length;

        for (int var3 = 0; var3 < var2; ++var3) {
            Handler var4 = var1[var3];
            a.getParent().removeHandler(var4);
        }

        ConsoleHandler var6;
        (var6 = new ConsoleHandler()).setFormatter(var0);
        a.addHandler(var6);

        try {
            SaveServerLog var7;
            (var7 = new SaveServerLog(new FileOutputStream("server.log"), var0)).setFormatter(var0);
            a.addHandler(var7);
        } catch (Exception var5) {
            a.warning("Failed to open file server.log for writing: " + var5);
        }
    }
}
