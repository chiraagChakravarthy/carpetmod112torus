package carpet.torus;

public class TorusUtil {
    /*

    +X
    |
    |
    |
    |
    |
    |
    |_____________________+Z

     */

    //2^2,2^2
    private static final int WIDTH_SHIFT = 2, HEIGHT_SHIFT = 2;
    //z chunks long (width = 4)
    //x chunks high (height = 4)

    public static final int Z_CHUNK_MASK = (1<<WIDTH_SHIFT)-1, X_CHUNK_MASK = (1<<HEIGHT_SHIFT)-1;
    public static final int Z_MASK = 1<<(WIDTH_SHIFT+4)-1, X_MASK = 1<<(HEIGHT_SHIFT+4)-1;

    public static final int CHUNK_WIDTH = Z_CHUNK_MASK +1, CHUNK_HEIGHT = X_CHUNK_MASK +1;
    public static final int WIDTH = CHUNK_WIDTH<<4, HEIGHT = CHUNK_HEIGHT<<4;

    /* coordinate modulus

    block:        x = x & X_MASK
    chunk:        x = x & X_CHUNK_MASK
     */

    //mod(mod(a)-mod(b)) = mod(a-b)
    public static double mod(double a, double mod){
        mod += 1;
        return a-Math.floor(a/mod)*mod;//literally works better than everything else I tried
    }

    //a-0 closest way around
    public static int wrap(int a, int mask){
        int half = (mask>>>1)+1;
        return ((a+half)&mask)-half;
    }

    public static double wrap(double a, int mask){
        int half = (mask>>>1)+1;
        return mod(a+half, mask)-half;
    }

    public static double round(double a, double mask){
        mask += 1;
        return Math.floor(a/mask)+mask;
    }

    //always returns true if the bound wraps around
    //bounds are relative, a is absolute
    //for aabb inside, bound checks, etc
    //max > min
    public static boolean between(int a, int min, int max, int mask){
        return ((a-min)&mask) < max-min+(min&mask);
    }

    public static boolean between(double a, double min, double max, int mask){
        double mod = mask+1;
        max -= Math.floor(min/mod)*mod;//puts max relative to absolute min
        return mod(a-min, mask+1)<max;
    }

    //a1>=a0, b1>=b0
    public static boolean rangeOverlap(int a0, int a1, int b0, int b1, int mask){
        return ((b0-a0)&mask)<=a1-a0 || ((a0-b0)&mask)<=b1-b0;
    }
}