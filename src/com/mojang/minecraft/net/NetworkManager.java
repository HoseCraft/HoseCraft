package com.mojang.minecraft.net;

import com.mojang.minecraft.server.HandleClient;

import java.io.IOException;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Arrays;

public final class NetworkManager {

   public volatile boolean a;
   public SocketChannel b;
   public ByteBuffer c = ByteBuffer.allocate(1048576);
   public ByteBuffer d = ByteBuffer.allocate(1048576);
   public HandleClient e;
   private Socket g;
   private boolean h = false;
   public String f;
   private byte[] i = new byte[64];


   public NetworkManager(SocketChannel var1) {
      this.b = var1;
      try {
         this.b.configureBlocking(false);
         System.currentTimeMillis();
         this.g = this.b.socket();
         this.a = true;
         this.c.clear();
         this.d.clear();
         this.g.setTcpNoDelay(true);
         this.g.setTrafficClass(24);
         this.g.setKeepAlive(false);
         this.g.setReuseAddress(false);
         this.g.setSoTimeout(100);
         this.f = this.g.getInetAddress().toString();
      } catch (IOException e1) {
         e1.printStackTrace();
      }
   }

   public final void a() {
      try {
         if(this.d.position() > 0) {
            this.d.flip();
            this.b.write(this.d);
            this.d.compact();
         }
      } catch (Exception var2) {
         ;
      }

      this.a = false;

      try {
         this.b.close();
      } catch (Exception var1) {
         ;
      }

      this.g = null;
      this.b = null;
   }

   public final void a(PacketType var1, Object ... var2) {
      if(this.a) {
         this.d.put(var1.opcode);

         for(int var3 = 0; var3 < var2.length; ++var3) {
            Class var10001 = var1.params[var3];
            Object var6 = var2[var3];
            Class var5 = var10001;
            NetworkManager var4 = this;
            if(this.a) {
               try {
                  if(var5 == Long.TYPE) {
                     var4.d.putLong(((Long)var6).longValue());
                  } else if(var5 == Integer.TYPE) {
                     var4.d.putInt(((Number)var6).intValue());
                  } else if(var5 == Short.TYPE) {
                     var4.d.putShort(((Number)var6).shortValue());
                  } else if(var5 == Byte.TYPE) {
                     var4.d.put(((Number)var6).byteValue());
                  } else if(var5 == Double.TYPE) {
                     var4.d.putDouble(((Double)var6).doubleValue());
                  } else if(var5 == Float.TYPE) {
                     var4.d.putFloat(((Float)var6).floatValue());
                  } else {
                     byte[] var8;
                     if(var5 != String.class) {
                        if(var5 == byte[].class) {
                           if((var8 = (byte[])((byte[])var6)).length < 1024) {
                              var8 = Arrays.copyOf(var8, 1024);
                           }

                           var4.d.put(var8);
                        }
                     } else {
                        var8 = ((String)var6).getBytes("UTF-8");
                        Arrays.fill(var4.i, (byte)32);

                        int var9;
                        for(var9 = 0; var9 < 64 && var9 < var8.length; ++var9) {
                           var4.i[var9] = var8[var9];
                        }

                        for(var9 = var8.length; var9 < 64; ++var9) {
                           var4.i[var9] = 32;
                        }

                        var4.d.put(var4.i);
                     }
                  }
               } catch (Exception var7) {
                  this.e.a(var7);
               }
            }
         }

      }
   }

   public Object a(Class var1) {
      if(!this.a) {
         return null;
      } else {
         try {
            if(var1 == Long.TYPE) {
               return Long.valueOf(this.c.getLong());
            } else if(var1 == Integer.TYPE) {
               return Integer.valueOf(this.c.getInt());
            } else if(var1 == Short.TYPE) {
               return Short.valueOf(this.c.getShort());
            } else if(var1 == Byte.TYPE) {
               return Byte.valueOf(this.c.get());
            } else if(var1 == Double.TYPE) {
               return Double.valueOf(this.c.getDouble());
            } else if(var1 == Float.TYPE) {
               return Float.valueOf(this.c.getFloat());
            } else if(var1 == String.class) {
               this.c.get(this.i);
               return (new String(this.i, "UTF-8")).trim();
            } else if(var1 == byte[].class) {
               byte[] var3 = new byte[1024];
               this.c.get(var3);
               return var3;
            } else {
               return null;
            }
         } catch (Exception var2) {
            this.e.a(var2);
            return null;
         }
      }
   }
}
