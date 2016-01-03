package a;

// Completely unused!

public final class a {

   private static float[] a = new float[65536];


   public static final float a(float var0) {
      return a[(int)(var0 * 10430.378F) & '\uffff'];
   }

   public static final float b(float var0) {
      return a[(int)(var0 * 10430.378F + 16384.0F) & '\uffff'];
   }

   public static final float c(float var0) {
      return (float)Math.sqrt((double)var0);
   }

   static {
      for(int var0 = 0; var0 < 65536; ++var0) {
         a[var0] = (float)Math.sin((double)var0 * 3.141592653589793D * 2.0D / 65536.0D);
      }

   }
}
