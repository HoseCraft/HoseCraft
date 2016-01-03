package com.mojang.minecraft.server;

import com.mojang.minecraft.BlockTypes;
import com.mojang.minecraft.level.Level;
import com.mojang.minecraft.level.tile.Block;
import com.mojang.minecraft.net.NetworkManager;
import com.mojang.minecraft.net.PacketType;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

public final class HandleClient {

   private static Logger j = MinecraftServer.a;
   public final NetworkManager NetworkManager;
   private final MinecraftServer k;
   private boolean l = false;
   private boolean m = false;
   public String b = "";
   public final int c;
   private ArrayList n = new ArrayList();
   private long o;
   private List p = new ArrayList();
   private int q = 0;
   public int d;
   public int e;
   public int f;
   public int g;
   public int h;
   private boolean r = false;
   private int s = 0;
   private int t = 0;
   private volatile byte[] u = null;
   public boolean i = false;


   public HandleClient(MinecraftServer var1, NetworkManager var2, int var3) {
      this.k = var1;
      this.NetworkManager = var2;
      this.c = var3;
      this.o = System.currentTimeMillis();
      var2.e = this;
      Level var4 = var1.c;
      this.d = (var4.xSpawn << 5) + 16;
      this.e = (var4.ySpawn << 5) + 16;
      this.f = (var4.zSpawn << 5) + 16;
      this.h = (int)(var4.rotSpawn * 256.0F / 360.0F);
      this.g = 0;
   }

   public final String toString() {
      NetworkManager var1;
      return !this.l?(var1 = this.NetworkManager).f:this.b + " (" + (var1 = this.NetworkManager).f + ")";
   }

   public final void a(PacketType var1, Object[] var2) {
      if(!this.r) {
         if(var1 != PacketType.IDENTIFICATION) {
            if(var1 != PacketType.PING) {
               if(this.l && this.m) {
                  if(var1 == PacketType.PLAYER_SET_BLOCK) {
                     if(this.p.size() > 1200) {
                        this.d("Too much lag");
                     } else {
                        this.p.add(var2);
                     }
                  } else if(var1 == PacketType.CHAT_MESSAGE) {
                     String var7;
                     if((var7 = var2[1].toString().trim()).length() > 0) {
                        this.c(var7);
                     }

                  } else {
                     if(var1 == PacketType.POSITION_ROTATION) {
                        if(this.p.size() > 1200) {
                           this.d("Too much lag");
                           return;
                        }

                        this.p.add(var2);
                     }

                  }
               }
            }
         } else {
            byte var6 = ((Byte)var2[0]).byteValue();
            String var3 = ((String)var2[1]).trim();
            String var8 = (String)var2[2];
            char[] var4 = var3.toCharArray();

            for(int var5 = 0; var5 < var4.length; ++var5) {
               if(var4[var5] < 32 || var4[var5] > 127) {
                  this.d("Bad name!");
                  return;
               }
            }

            if(var3.length() < 2 || var3.length() > 16) {
               this.a("Illegal name.");
            }

            if(this.k.k && !var8.equals(this.k.j.a(var3))) {
               this.a("The name wasn\'t verified by minecraft.net!");
            } else if(var6 != 7) {
               this.a("Wrong protocol version.");
            } else if(this.k.h.c(var3)) {
               this.a("You\'re banned!");
            } else if(this.k.f && !this.k.g.c(var3) && this.k.a() < 1) {
               this.NetworkManager.a(PacketType.DISCONNECT, new Object[]{"The server is full!"});
               j.info(var3 + " connected, but got kicked because the server was almost full and there are reserved admin slots.");
               this.k.a(this);
               this.r = true;
            } else {
               HandleClient var11;
               if((var11 = this.k.c(var3)) != null) {
                  var11.a("You logged in from another computer.");
               }

               j.info(this + " logged in as " + var3);
               this.l = true;
               this.b = var3;
               this.NetworkManager.a(PacketType.IDENTIFICATION, new Object[]{Byte.valueOf((byte)7), this.k.d, this.k.e, Integer.valueOf(this.k.g.c(var3)?100:0)});
               Level var9 = this.k.c;
               byte[] var10 = var9.copyBlocks();
               (new ClientStream(this, var10)).start();
               this.k.i.a(var3);
            }
         }
      }
   }

   private void c(String var1) {
      var1 = var1.trim();
      this.q += var1.length() + 15 << 2;
      if(this.q > 600) {
         this.q = 760;
         this.b(PacketType.CHAT_MESSAGE, new Object[]{Integer.valueOf(-1), "Too much chatter! Muted for eight seconds."});
         j.info("Muting " + this.b + " for chatting too much");
      } else {
         char[] var2 = var1.toCharArray();

         for(int var3 = 0; var3 < var2.length; ++var3) {
            if(var2[var3] < 32 || var2[var3] > 127) {
               this.d("Bad chat message!");
               return;
            }
         }

         if(var1.startsWith("/")) {
            if(this.k.g.c(this.b)) {
               this.k.a(this, var1.substring(1));
            } else {
               this.b(PacketType.CHAT_MESSAGE, new Object[]{Integer.valueOf(-1), "You\'re not net server admin!"});
            }
         } else {
            j.info(this.b + " says: " + var1);
            this.k.a(PacketType.CHAT_MESSAGE, new Object[]{Integer.valueOf(this.c), this.b + ": " + var1});
         }
      }
   }

   public final void a(String var1) {
      this.NetworkManager.a(PacketType.DISCONNECT, new Object[]{var1});
      j.info("Kicking " + this + ": " + var1);
      this.k.a(this);
      this.r = true;
   }

   private void d(String var1) {
      this.a("Cheat detected: " + var1);
   }

   public final void b(String var1) {
      this.b(PacketType.CHAT_MESSAGE, new Object[]{Integer.valueOf(-1), var1});
   }

   public final void a(byte[] var1) {
      this.u = var1;
   }

   public final void a() {
      if(this.s >= 2) {
         this.s -= 2;
      }

      if(this.q > 0) {
         --this.q;
         if(this.q == 600) {
            this.b(PacketType.CHAT_MESSAGE, new Object[]{Integer.valueOf(-1), "You can now talk again."});
            this.q = 300;
         }
      }

      Object[] var2;
      boolean var10000;
      if(this.p.size() > 0) {
         for(boolean var1 = true; this.p.size() > 0 && var1; var1 = var10000) {
            short var3;
            short var4;
            byte var5;
            byte var6;
            short var13;
            short var10001;
            short var10002;
            short var10003;
            byte var10004;
            if((var2 = (Object[])this.p.remove(0))[0] instanceof Short) {
               var10001 = ((Short)var2[0]).shortValue();
               var10002 = ((Short)var2[1]).shortValue();
               var10003 = ((Short)var2[2]).shortValue();
               var10004 = ((Byte)var2[3]).byteValue();
               var6 = ((Byte)var2[4]).byteValue();
               var5 = var10004;
               var4 = var10003;
               var3 = var10002;
               var13 = var10001;
               ++this.s;
               if(this.s == 100) {
                  this.d("Too much clicking!");
               } else {
                  Level var7 = this.k.c;
                  float var8 = (float)var13 - (float)this.d / 32.0F;
                  float var9 = (float)var3 - ((float)this.e / 32.0F - 1.62F);
                  float var10 = (float)var4 - (float)this.f / 32.0F;
                  var8 = var8 * var8 + var9 * var9 + var10 * var10;
                  var9 = 8.0F;
                  if(var8 >= var9 * var9) {
                     System.out.println("Distance: " + NetworkManager.equals(var8));
                     this.d("Distance");
                  } else if(!BlockTypes.a.contains(Block.blocks[var6])) {
                     this.d("Tile type");
                  } else if(var13 >= 0 && var3 >= 0 && var4 >= 0 && var13 < var7.width && var3 < var7.depth && var4 < var7.height) {
                     if(var5 == 0) {
                        if(var7.getTile(var13, var3, var4) != Block.BEDROCK.id || this.k.g.c(this.b)) {
                           var7.setTile(var13, var3, var4, 0);
                        }
                     } else {
                        Block var18;
                        if((var18 = Block.blocks[var7.getTile(var13, var3, var4)]) == null || var18 == Block.WATER || var18 == Block.STATIONARY_WATER || var18 == Block.LAVA || var18 == Block.STATIONARY_LAVA) {
                           if(this.i && var6 == Block.STONE.id) {
                              var7.setTile(var13, var3, var4, Block.BEDROCK.id);
                           } else {
                              var7.setTile(var13, var3, var4, var6);
                           }

                           Block.blocks[var6].a(var7, var13, var3, var4);
                        }
                     }
                  }
               }

               var10000 = true;
            } else {
               ((Byte)var2[0]).byteValue();
               var10001 = ((Short)var2[1]).shortValue();
               var10002 = ((Short)var2[2]).shortValue();
               var10003 = ((Short)var2[3]).shortValue();
               var10004 = ((Byte)var2[4]).byteValue();
               var6 = ((Byte)var2[5]).byteValue();
               var5 = var10004;
               var4 = var10003;
               var3 = var10002;
               var13 = var10001;
               if(var13 == this.d && var3 == this.e && var4 == this.f && var5 == this.h && var6 == this.g) {
                  var10000 = true;
               } else {
                  boolean var21 = var13 == this.d && var3 == this.e && var4 == this.f;
                  if(this.t++ % 2 == 0) {
                     int var22 = var13 - this.d;
                     int var23 = var3 - this.e;
                     int var24 = var4 - this.f;
                     if(var22 >= 128 || var22 < -128 || var23 >= 128 || var23 < -128 || var24 >= 128 || var24 < -128 || this.t % 20 <= 1) {
                        this.d = var13;
                        this.e = var3;
                        this.f = var4;
                        this.h = var5;
                        this.g = var6;
                        this.k.a(this, PacketType.POSITION_ROTATION, new Object[]{Integer.valueOf(this.c), Short.valueOf(var13), Short.valueOf(var3), Short.valueOf(var4), Byte.valueOf(var5), Byte.valueOf(var6)});
                        var10000 = false;
                        continue;
                     }

                     if(var13 == this.d && var3 == this.e && var4 == this.f) {
                        this.h = var5;
                        this.g = var6;
                        this.k.a(this, PacketType.ROTATION_UPDATE, new Object[]{Integer.valueOf(this.c), Byte.valueOf(var5), Byte.valueOf(var6)});
                     } else if(var5 == this.h && var6 == this.g) {
                        this.d = var13;
                        this.e = var3;
                        this.f = var4;
                        this.k.a(this, PacketType.POSITION_UPDATE, new Object[]{Integer.valueOf(this.c), Integer.valueOf(var22), Integer.valueOf(var23), Integer.valueOf(var24)});
                     } else {
                        this.d = var13;
                        this.e = var3;
                        this.f = var4;
                        this.h = var5;
                        this.g = var6;
                        this.k.a(this, PacketType.POSITION_ROTATION_UPDATE, new Object[]{Integer.valueOf(this.c), Integer.valueOf(var22), Integer.valueOf(var23), Integer.valueOf(var24), Byte.valueOf(var5), Byte.valueOf(var6)});
                     }
                  }

                  var10000 = var21;
               }
            }
         }
      }

      if(!this.l && System.currentTimeMillis() - this.o > 5000L) {
         this.a("You need to log in!");
      } else if(this.u != null) {
         Level var11 = this.k.c;
         byte[] var15 = new byte[1024];
         int var16 = 0;
         int var17 = this.u.length;
         this.NetworkManager.a(PacketType.LEVEL_INIT, new Object[0]);

         int var19;
         while(var17 > 0) {
            var19 = var17;
            if(var17 > var15.length) {
               var19 = var15.length;
            }

            System.arraycopy(this.u, var16, var15, 0, var19);
            this.NetworkManager.a(PacketType.LEVEL_DATA, new Object[]{Integer.valueOf(var19), var15, Integer.valueOf((var16 + var19) * 100 / this.u.length)});
            var17 -= var19;
            var16 += var19;
         }

         this.NetworkManager.a(PacketType.LEVEL_FINALIZE, new Object[]{Integer.valueOf(var11.width), Integer.valueOf(var11.depth), Integer.valueOf(var11.height)});
         this.NetworkManager.a(PacketType.SPAWN_PLAYER, new Object[]{Integer.valueOf(-1), this.b, Integer.valueOf(this.d), Integer.valueOf(this.e), Integer.valueOf(this.f), Integer.valueOf(this.h), Integer.valueOf(this.g)});
         this.k.a(this, PacketType.SPAWN_PLAYER, new Object[]{Integer.valueOf(this.c), this.b, Integer.valueOf((var11.xSpawn << 5) + 16), Integer.valueOf((var11.ySpawn << 5) + 16), Integer.valueOf((var11.zSpawn << 5) + 16), Integer.valueOf((int)(var11.rotSpawn * 256.0F / 360.0F)), Integer.valueOf(0)});
         this.k.a(PacketType.CHAT_MESSAGE, new Object[]{Integer.valueOf(-1), this.b + " joined the game"});
         Iterator var20 = this.k.b().iterator();

         while(var20.hasNext()) {
            HandleClient var12;
            if((var12 = (HandleClient)var20.next()) != null && var12 != this && var12.l) {
               this.NetworkManager.a(PacketType.SPAWN_PLAYER, new Object[]{Integer.valueOf(var12.c), var12.b, Integer.valueOf(var12.d), Integer.valueOf(var12.e), Integer.valueOf(var12.f), Integer.valueOf(var12.h), Integer.valueOf(var12.g)});
            }
         }

         this.m = true;
         var19 = 0;

         while(var19 < this.n.size()) {
            PacketType var14 = (PacketType) this.n.get(var19++);
            var2 = (Object[])((Object[])this.n.get(var19++));
            this.b(var14, var2);
         }

         this.n = null;
         this.u = null;
      }
   }

   public final void b(PacketType var1, Object ... var2) {
      if(!this.m) {
         this.n.add(var1);
         this.n.add(var2);
      } else {
         this.NetworkManager.a(var1, var2);
      }
   }

   public final void a(Exception var1) {
      if(var1 instanceof IOException) {
         j.info(this + " lost connection suddenly. (" + var1 + ")");
      } else {
         j.warning(this + ":" + var1);
         j.log(java.util.logging.Level.WARNING, "Exception handling " + this + "!", var1);
         var1.printStackTrace();
      }

      this.k.a(this, PacketType.CHAT_MESSAGE, new Object[]{Integer.valueOf(-1), this.b + " left the game"});
      MinecraftServer.b(this);
   }

}
