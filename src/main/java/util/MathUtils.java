package util;

public class MathUtils {

    private static final RandomPrimaryNumberGenerator randomPrimaryNumberGenerator = new RandomPrimaryNumberGenerator(1000);

    public static long modPow(long value, long pow, long mod) {
        long result = 1;

        for (int i = 0; i < pow ; i++) {
            result *= value % mod;
            result %= mod;
        }
        return result;
    }

    public static int gcd(int e, int z)
    {
        if (e == 0)
            return z;
        else
            return gcd(z % e, e);
    }

    public static long eulerFunction(long p, long q) {
        return (p - 1) * (q - 1);
    }

    public static long getPublicPartOfKey(long d, long n) {
        long e = d + 1;

        while ((e * d) % n != 1) {
            e++;
        }

        return d;
    }

    static boolean isPrime(int n)
    {
        // Corner case
        if (n <= 1)
            return false;

        // Check from 2 to n-1
        for (int i = 2; i < n; i++)
            if (n % i == 0)
                return false;

        return true;
    }

}
