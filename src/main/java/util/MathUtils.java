package util;

public class MathUtils {

    public static long modPow(long value, long pow, long mod) {
        long result = 1;

        for (int i = 0; i < pow ; i++) {
            result *= value % mod;
            result %= mod;
        }
        return result;
    }

    public static long eulerFunction(long p, long q) {
        return (p - 1) * (q - 1);
    }

}
