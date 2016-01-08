package com.mojang.minecraft.render;


public final class Frustum {

    public float[][] a = new float[16][16];


    public final boolean a(float var1, float var2, float var3, float var4, float var5, float var6) {
        for (int var7 = 0; var7 < 6; ++var7) {
            if (this.a[var7][0] * var1 + this.a[var7][1] * var2 + this.a[var7][2] * var3 + this.a[var7][3] <= 0.0F && this.a[var7][0] * var4 + this.a[var7][1] * var2 + this.a[var7][2] * var3 + this.a[var7][3] <= 0.0F && this.a[var7][0] * var1 + this.a[var7][1] * var5 + this.a[var7][2] * var3 + this.a[var7][3] <= 0.0F && this.a[var7][0] * var4 + this.a[var7][1] * var5 + this.a[var7][2] * var3 + this.a[var7][3] <= 0.0F && this.a[var7][0] * var1 + this.a[var7][1] * var2 + this.a[var7][2] * var6 + this.a[var7][3] <= 0.0F && this.a[var7][0] * var4 + this.a[var7][1] * var2 + this.a[var7][2] * var6 + this.a[var7][3] <= 0.0F && this.a[var7][0] * var1 + this.a[var7][1] * var5 + this.a[var7][2] * var6 + this.a[var7][3] <= 0.0F && this.a[var7][0] * var4 + this.a[var7][1] * var5 + this.a[var7][2] * var6 + this.a[var7][3] <= 0.0F) {
                return false;
            }
        }

        return true;
    }
}
