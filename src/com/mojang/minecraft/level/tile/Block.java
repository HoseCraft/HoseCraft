package com.mojang.minecraft.level.tile;

import com.mojang.minecraft.MovingObjectPosition;
import com.mojang.minecraft.level.Level;
import com.mojang.minecraft.level.liquid.LiquidType;
import com.mojang.minecraft.phys.AABB;
import com.mojang.util.IntersectionHelper;
import com.mojang.util.Vec3D;

import java.util.Random;

public class Block {

   protected static Random a = new Random();
   public static final Block[] blocks = new Block[256];
   public static final boolean[] c = new boolean[256];
   private static boolean[] ad = new boolean[256];
   private static boolean[] ae = new boolean[256];
   public static final boolean[] liquid = new boolean[256];
   private static int[] af = new int[256];
   public static final Block STONE; // e
   public static final Block GRASS; // f
   public static final Block DIRT; // g
   public static final Block COBBLESTONE; // h
   public static final Block WOOD; // i
   public static final Block SAPLING; // j
   public static final Block BEDROCK; // k
   public static final Block WATER; // l
   public static final Block STATIONARY_WATER; // m
   public static final Block LAVA; // n
   public static final Block STATIONARY_LAVA; // o
   public static final Block SAND; // p
   public static final Block GRAVEL; // q
   public static final Block GOLD_ORE; // r
   public static final Block IRON_ORE; // s
   public static final Block COAL_ORE; // t
   public static final Block LOG; // u
   public static final Block LEAVES; // v
   public static final Block SPONGE; // w
   public static final Block GLASS; // x
   public static final Block RED_WOOL; // y
   public static final Block ORANGE_WOOL; // z
   public static final Block YELLOW_WOOL; // A
   public static final Block LIME_WOOL; // B
   public static final Block GREEN_WOOL; // C
   public static final Block AQUA_GREEN_WOOL; // D
   public static final Block CYAN_WOOL; // E
   public static final Block BLUE_WOOL; // F
   public static final Block PURPLE_WOOL; // G
   public static final Block INDIGO_WOOL; // H
   public static final Block VIOLET_WOOL; // I
   public static final Block MAGENTA_WOOL; // J
   public static final Block PINK_WOOL; // K
   public static final Block BLACK_WOOL; // L
   public static final Block GRAY_WOOL; // M
   public static final Block WHITE_WOOL; // N
   public static final Block DANDELION; // O
   public static final Block ROSE; // P
   public static final Block BROWN_MUSHROOM; // Q
   public static final Block RED_MUSHROOM; // R
   public static final Block GOLD_BLOCK; // S
   public static final Block IRON_BLOCK; // T
   public static final Block DOUBLE_SLAB; // U
   public static final Block SLAB; // V
   public static final Block BRICK; // W
   public static final Block TNT; //X
   public static final Block BOOKSHELF; // Y
   public static final Block MOSSY_COBBLESTONE; // Z
   public static final Block OBSIDIAN; // aa
   public final int id;
   public Tile$SoundType ac;
   private boolean explodes;
   private float ah;
   private float ai;
   private float aj;
   private float ak;
   private float al;
   private float am;


   protected Block(int var1) {
      this.explodes = true; // Explodes
      blocks[var1] = this;
      this.id = var1;
       
      this.a(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
       
      ad[var1] = this.c();
      ae[var1] = this.a();
      liquid[var1] = false;
   }

   public boolean a() {
      return true;
   }

   protected final void a(boolean var1) {
      c[this.id] = var1;
   }

   protected final void a(float var1, float var2, float var3, float var4, float var5, float var6) {
      this.ah = var1;
      this.ai = var2;
      this.aj = var3;
      this.ak = var4;
      this.al = var5;
      this.am = var6;
   }

   protected Block(int var1, int var2) {
      this(var1);
   }

   public final void a(int var1) {
      af[this.id] = 16;
   }

   public AABB a(int var1, int var2, int var3) {
      return new AABB((float)var1 + this.ah, (float)var2 + this.ai, (float)var3 + this.aj, (float)var1 + this.ak, (float)var2 + this.al, (float)var3 + this.am);
   }

   public boolean b() {
      return true;
   }

   public boolean c() {
      return true;
   }

   public void a(Level var1, int var2, int var3, int var4, Random var5) {}

   public LiquidType getLiquidType() {
      return LiquidType.notLiquid;
   }

   public void a(Level var1, int var2, int var3, int var4, int var5) {}

   public void a(Level var1, int var2, int var3, int var4) {}

   public int e() {
      return 0;
   }

   public void b(Level var1, int var2, int var3, int var4) {}

   public void c(Level var1, int var2, int var3, int var4) {}

   public int f() {
      return 1;
   }

   public void a(Level var1, float var2) {
      if(!var1.creativeMode) {
         int var3 = this.f();

         for(int var4 = 0; var4 < var3; ++var4) {
            if(a.nextFloat() <= var2) {
               a.nextFloat();
               a.nextFloat();
               a.nextFloat();
            }
         }

      }
   }

   public final boolean g() {
      return this.explodes;
   }

   // Assuming collision.
   public final MovingObjectPosition a(int var1, int var2, int var3, Vec3D var4, Vec3D var5) {
      var4 = var4.add((float)(-var1), (float)(-var2), (float)(-var3));
      var5 = var5.add((float)(-var1), (float)(-var2), (float)(-var3));

      Vec3D var6 = var4.getXIntersection(var5, this.ah);
      Vec3D var7 = var4.getXIntersection(var5, this.ak);
      Vec3D var8 = var4.getYIntersection(var5, this.ai);
      Vec3D var9 = var4.getYIntersection(var5, this.al);
      Vec3D var10 = var4.getZIntersection(var5, this.aj);
      var5 = var4.getZIntersection(var5, this.am);
      if(!this.a(var6)) {
         var6 = null;
      }

      if(!this.a(var7)) {
         var7 = null;
      }

      if(!this.b(var8)) {
         var8 = null;
      }

      if(!this.b(var9)) {
         var9 = null;
      }

      if(!this.c(var10)) {
         var10 = null;
      }

      if(!this.c(var5)) {
         var5 = null;
      }

      Vec3D var11 = null;
      if(var6 != null) {
         var11 = var6;
      }

      if(var7 != null && (var11 == null || var4.distance(var7) < var4.distance(var11))) {
         var11 = var7;
      }

      if(var8 != null && (var11 == null || var4.distance(var8) < var4.distance(var11))) {
         var11 = var8;
      }

      if(var9 != null && (var11 == null || var4.distance(var9) < var4.distance(var11))) {
         var11 = var9;
      }

      if(var10 != null && (var11 == null || var4.distance(var10) < var4.distance(var11))) {
         var11 = var10;
      }

      if(var5 != null && (var11 == null || var4.distance(var5) < var4.distance(var11))) {
         var11 = var5;
      }

      if(var11 == null) {
         return null;
      } else {
         byte var12 = -1;
         if(var11 == var6) {
            var12 = 4;
         }

         if(var11 == var7) {
            var12 = 5;
         }

         if(var11 == var8) {
            var12 = 0;
         }

         if(var11 == var9) {
            var12 = 1;
         }

         if(var11 == var10) {
            var12 = 2;
         }

         if(var11 == var5) {
            var12 = 3;
         }

         return new MovingObjectPosition(var1, var2, var3, var12, var11.add((float)var1, (float)var2, (float)var3));
      }
   }

   private boolean a(Vec3D var1) {
      //return var1 == null?false:var1.b >= this.ai && var1.b <= this.al && var1.c >= this.aj && var1.c <= this.am;
       return IntersectionHelper.xIntersects(var1, this.ai, this.al, this.aj, this.am);
   }

   private boolean b(Vec3D var1) {
      //return var1 == null?false:var1.a >= this.ah && var1.a <= this.ak && var1.c >= this.aj && var1.c <= this.am;
       return IntersectionHelper.yIntersects(var1, this.ah, this.ak, this.aj, this.am);
   }

   private boolean c(Vec3D var1) {
      //return var1 == null?false:var1.a >= this.ah && var1.a <= this.ak && var1.b >= this.ai && var1.b <= this.al;
       return IntersectionHelper.zIntersects(var1, this.ah, this.ak, this.ai, this.al);
   }

   static {

      StoneBlock var10000 = new StoneBlock(1, 1);
      //float var0 = 1.0F;
      //var0 = 1.0F;
      //var0 = 1.0F;
      Tile$SoundType var1 = Tile$SoundType.stone;
      StoneBlock var2 = var10000;
      var10000.ac = var1;
      boolean var3 = false;
      //var2.explodes = false;
      STONE = var2;

      GrassBlock var11 = new GrassBlock(2);
      //var0 = 0.6F;
      //var0 = 1.0F;
      //var0 = 0.9F;
      var1 = Tile$SoundType.grass;
      GrassBlock var4 = var11;
      var11.ac = var1;
      GRASS = var4;

      DirtBlock var12 = new DirtBlock(3, 2);
      //var0 = 0.5F;
      //var0 = 1.0F;
      //var0 = 0.8F;
      var1 = Tile$SoundType.grass;
      DirtBlock var5 = var12;
      var12.ac = var1;
      DIRT = var5;

      Block var13 = new Block(4, 16);
      //var0 = 1.5F;
      //var0 = 1.0F;
      //var0 = 1.0F;
      var1 = Tile$SoundType.stone;
      Block var6 = var13;
      var13.ac = var1;
      var3 = false;
      var6.explodes = false;
      COBBLESTONE = var6;

      var13 = new Block(5, 4);
      //var0 = 1.5F;
      //var0 = 1.0F;
      //var0 = 1.0F;
      var1 = Tile$SoundType.wood;
      var6 = var13;
      var13.ac = var1;
      WOOD = var6;

      SaplingBlock var14 = new SaplingBlock(6, 15);
      //var0 = 0.0F;
      //var0 = 1.0F;
      //var0 = 0.7F;
      //var1 = Tile$SoundType.none;
      SaplingBlock var7 = var14;
      var14.ac = var1;
      SAPLING = var7;

      var13 = new Block(7, 17);
      //var0 = 999.0F;
      //var0 = 1.0F;
      //var0 = 1.0F;
      var1 = Tile$SoundType.stone;
      var6 = var13;
      var13.ac = var1;
      var3 = false;
      var6.explodes = false;
      BEDROCK = var6;

      LiquidBlock var16 = new LiquidBlock(8, LiquidType.water);
      //var0 = 100.0F; // Hardness
      //var0 = 1.0F; // Particle Gravity(?)
      //var0 = 1.0F;
      var1 = Tile$SoundType.none;
      LiquidBlock var8 = var16;
      var16.ac = var1;
      WATER = var8;

      StillLiquidBlock var17 = new StillLiquidBlock(9, LiquidType.water);
      //var0 = 100.0F;
      //var0 = 1.0F;
      //var0 = 1.0F;
      var1 = Tile$SoundType.none;
      StillLiquidBlock var9 = var17;
      var17.ac = var1;
       STATIONARY_WATER = var9;

      var16 = new LiquidBlock(10, LiquidType.lava);
      //var0 = 100.0F;
      //var0 = 1.0F;
      //var0 = 1.0F;
      var1 = Tile$SoundType.none;
      var8 = var16;
      var16.ac = var1;
      LAVA = var8;

      var17 = new StillLiquidBlock(11, LiquidType.lava);
      //var0 = 100.0F;
      //var0 = 1.0F;
      //var0 = 1.0F;
      var1 = Tile$SoundType.none;
      var9 = var17;
      var17.ac = var1;
      STATIONARY_LAVA = var9;

      SandBlock var18 = new SandBlock(12, 18);
      //var0 = 0.5F;
      //var0 = 1.0F;
      //var0 = 0.8F;
      var1 = Tile$SoundType.gravel;
      SandBlock var10 = var18;
      var18.ac = var1;
      SAND = var10;

      var18 = new SandBlock(13, 19);
      //var0 = 0.6F;
      //var0 = 1.0F;
      //var0 = 0.8F;
      var1 = Tile$SoundType.gravel;
      var10 = var18;
      var18.ac = var1;
      GRAVEL = var10;

      OreBlock var19 = new OreBlock(14, 32);
      //var0 = 3.0F;
      //var0 = 1.0F;
      //var0 = 1.0F;
      var1 = Tile$SoundType.stone;
      OreBlock var15 = var19;
      var19.ac = var1;
      var3 = false;
      //var15.explodes = false;
      GOLD_ORE = var15;

      var19 = new OreBlock(15, 33);
      //var0 = 3.0F;
      //var0 = 1.0F;
      //var0 = 1.0F;
      var1 = Tile$SoundType.stone;
      var15 = var19;
      var19.ac = var1;
      var3 = false;
      //var15.explodes = false;
      IRON_ORE = var15;

      var19 = new OreBlock(16, 34);
      //var0 = 3.0F;
      //var0 = 1.0F;
      //var0 = 1.0F;
      var1 = Tile$SoundType.stone;
      var15 = var19;
      var19.ac = var1;
      var3 = false;
      //var15.explodes = false;
      COAL_ORE = var15;

      WoodBlock var20 = new WoodBlock(17);
      //var0 = 2.5F;
      //var0 = 1.0F;
      //var0 = 1.0F;
      var1 = Tile$SoundType.wood;
      WoodBlock var23 = var20;
      var20.ac = var1;
      LOG = var23;

      LeavesBlock var21 = new LeavesBlock(18, 22);
      //var0 = 0.2F;
      //var0 = 0.4F;
      //var0 = 1.0F;
      var1 = Tile$SoundType.grass;
      LeavesBlock var25 = var21;
      var21.ac = var1;
      LEAVES = var25;

      SpongeBlock var22 = new SpongeBlock(19);
      //var0 = 0.6F;
      //var0 = 0.9F;
      //var0 = 1.0F;
      var1 = Tile$SoundType.cloth;
      SpongeBlock var26 = var22;
      var22.ac = var1;
      SPONGE = var26;

      GlassBlock var24 = new GlassBlock(20, 49, false);
      //var0 = 0.3F;
      //var0 = 1.0F;
      //var0 = 1.0F;
      var1 = Tile$SoundType.metal;
      GlassBlock var27 = var24;
      var24.ac = var1;
      GLASS = var27;

      var13 = new Block(21, 64);
      //var0 = 0.8F;
      //var0 = 1.0F;
      //var0 = 1.0F;
      var1 = Tile$SoundType.cloth;
      var6 = var13;
      var13.ac = var1;
      RED_WOOL = var6;

      var13 = new Block(22, 65);
      //var0 = 0.8F;
      //var0 = 1.0F;
      //var0 = 1.0F;
      var1 = Tile$SoundType.cloth;
      var6 = var13;
      var13.ac = var1;
      ORANGE_WOOL = var6;

      var13 = new Block(23, 66);
      //var0 = 0.8F;
      //var0 = 1.0F;
      //var0 = 1.0F;
      var1 = Tile$SoundType.cloth;
      var6 = var13;
      var13.ac = var1;
      YELLOW_WOOL = var6;

      var13 = new Block(24, 67);
      //var0 = 0.8F;
      //var0 = 1.0F;
      //var0 = 1.0F;
      var1 = Tile$SoundType.cloth;
      var6 = var13;
      var13.ac = var1;
      LIME_WOOL = var6;

      var13 = new Block(25, 68);
      //var0 = 0.8F;
      //var0 = 1.0F;
      //var0 = 1.0F;
      var1 = Tile$SoundType.cloth;
      var6 = var13;
      var13.ac = var1;
      GREEN_WOOL = var6;

      var13 = new Block(26, 69);
      //var0 = 0.8F;
      //var0 = 1.0F;
      //var0 = 1.0F;
      var1 = Tile$SoundType.cloth;
      var6 = var13;
      var13.ac = var1;
      AQUA_GREEN_WOOL = var6;

      var13 = new Block(27, 70);
      //var0 = 0.8F;
      //var0 = 1.0F;
      //var0 = 1.0F;
      var1 = Tile$SoundType.cloth;
      var6 = var13;
      var13.ac = var1;
      CYAN_WOOL = var6;

      var13 = new Block(28, 71);
      //var0 = 0.8F;
      //var0 = 1.0F;
      //var0 = 1.0F;
      var1 = Tile$SoundType.cloth;
      var6 = var13;
      var13.ac = var1;
      BLUE_WOOL = var6;

      var13 = new Block(29, 72);
      //var0 = 0.8F;
      //var0 = 1.0F;
      //var0 = 1.0F;
      var1 = Tile$SoundType.cloth;
      var6 = var13;
      var13.ac = var1;
      PURPLE_WOOL = var6;

      var13 = new Block(30, 73);
      //var0 = 0.8F;
      //var0 = 1.0F;
      //var0 = 1.0F;
      var1 = Tile$SoundType.cloth;
      var6 = var13;
      var13.ac = var1;
      INDIGO_WOOL = var6;

      var13 = new Block(31, 74);
      //var0 = 0.8F;
      //var0 = 1.0F;
      //var0 = 1.0F;
      var1 = Tile$SoundType.cloth;
      var6 = var13;
      var13.ac = var1;
      VIOLET_WOOL = var6;

      var13 = new Block(32, 75);
      //var0 = 0.8F;
      //var0 = 1.0F;
      //var0 = 1.0F;
      var1 = Tile$SoundType.cloth;
      var6 = var13;
      var13.ac = var1;
      MAGENTA_WOOL = var6;

      var13 = new Block(33, 76);
      //var0 = 0.8F;
      //var0 = 1.0F;
      //var0 = 1.0F;
      var1 = Tile$SoundType.cloth;
      var6 = var13;
      var13.ac = var1;
      PINK_WOOL = var6;

      var13 = new Block(34, 77);
      //var0 = 0.8F;
      //var0 = 1.0F;
      //var0 = 1.0F;
      var1 = Tile$SoundType.cloth;
      var6 = var13;
      var13.ac = var1;
      BLACK_WOOL = var6;

      var13 = new Block(35, 78);
      //var0 = 0.8F;
      //var0 = 1.0F;
      //var0 = 1.0F;
      var1 = Tile$SoundType.cloth;
      var6 = var13;
      var13.ac = var1;
      GRAY_WOOL = var6;

      var13 = new Block(36, 79);
      //var0 = 0.8F;
      //var0 = 1.0F;
      //var0 = 1.0F;
      var1 = Tile$SoundType.cloth;
      var6 = var13;
      var13.ac = var1;
      WHITE_WOOL = var6;

      FlowerBlock var28 = new FlowerBlock(37, 13);
      //var0 = 0.0F;
      //var0 = 1.0F;
      //var0 = 0.7F;
      var1 = Tile$SoundType.none;
      FlowerBlock var34 = var28;
      var28.ac = var1;
      DANDELION = var34;

      var28 = new FlowerBlock(38, 12);
      //var0 = 0.0F;
      //var0 = 1.0F;
      //var0 = 0.7F;
      var1 = Tile$SoundType.none;
      var34 = var28;
      var28.ac = var1;
      ROSE = var34;

      MushroomBlock var29 = new MushroomBlock(39, 29);
      //var0 = 0.0F;
      //var0 = 1.0F;
      //var0 = 0.7F;
      var1 = Tile$SoundType.none;
      MushroomBlock var35 = var29;
      var29.ac = var1;
      BROWN_MUSHROOM = var35;

      var29 = new MushroomBlock(40, 28);
      //var0 = 0.0F;
      //var0 = 1.0F;
      //var0 = 0.7F;
      var1 = Tile$SoundType.none;
      var35 = var29;
      var29.ac = var1;
      RED_MUSHROOM = var35;

      MetalBlock var30 = new MetalBlock(41, 40);
      //var0 = 3.0F;
      //var0 = 1.0F;
      //var0 = 0.7F;
      var1 = Tile$SoundType.metal;
      MetalBlock var36 = var30;
      var30.ac = var1;
      var3 = false;
      //var36.explodes = false;
      GOLD_BLOCK = var36;

      var30 = new MetalBlock(42, 39);
      //var0 = 5.0F;
      //var0 = 1.0F;
      //var0 = 0.7F;
      var1 = Tile$SoundType.metal;
      var36 = var30;
      var30.ac = var1;
      var3 = false;
      //var36.explodes = false;
      IRON_BLOCK = var36;

      SlabBlock var31 = new SlabBlock(43, true);
      //var0 = 2.0F;
      //var0 = 1.0F;
      //var0 = 1.0F;
      var1 = Tile$SoundType.stone;
      SlabBlock var37 = var31;
      var31.ac = var1;
      var3 = false;
      //var37.explodes = false;
      DOUBLE_SLAB = var37;

      var31 = new SlabBlock(44, false);
      //var0 = 2.0F;
      //var0 = 1.0F;
      //var0 = 1.0F;
      var1 = Tile$SoundType.stone;
      var37 = var31;
      var31.ac = var1;
      var3 = false;
      //var37.explodes = false;
      SLAB = var37;

      var13 = new Block(45, 7);
      //var0 = 2.0F;
      //var0 = 1.0F;
      //var0 = 1.0F;
      var1 = Tile$SoundType.stone;
      var6 = var13;
      var13.ac = var1;
      var3 = false;
      var6.explodes = false;
      BRICK = var6;

      TNTBlock var32 = new TNTBlock(46, 8);
      //var0 = 0.0F;
      //var0 = 1.0F;
      //var0 = 1.0F;
      var1 = Tile$SoundType.cloth;
      TNTBlock var38 = var32;
      var32.ac = var1;
      TNT = var38;

      BookshelfBlock var33 = new BookshelfBlock(47, 35);
      //var0 = 1.5F;
      //var0 = 1.0F;
      //var0 = 1.0F;
      var1 = Tile$SoundType.wood;
      BookshelfBlock var39 = var33;
      var33.ac = var1;
      BOOKSHELF = var39;

      var13 = new Block(48, 36);
      //var0 = 1.0F;
      //var0 = 1.0F;
      //var0 = 1.0F;
      var1 = Tile$SoundType.stone;
      var6 = var13;
      var13.ac = var1;
      var3 = false;
      var6.explodes = false;
      MOSSY_COBBLESTONE = var6;

      var10000 = new StoneBlock(49, 37);
      //var0 = 10.0F;
      //var0 = 1.0F;
      //var0 = 1.0F;
      var1 = Tile$SoundType.stone;
      var2 = var10000;
      var10000.ac = var1;
      var3 = false;
      //var2.explodes = false;
      OBSIDIAN = var2;
   }
}
