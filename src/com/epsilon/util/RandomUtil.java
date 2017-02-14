package com.epsilon.util;

import java.util.Random;

/**
 * A tool for generating pseudorandom numbers.
 */
public class RandomUtil {

    private static final Random RAND = new Random();

    /**
     * @return a pseudorandom number n for which lower <= n < upper.
     */
    public static int randInt(int lower, int upper) {
        return RAND.nextInt(upper - lower) + lower;
    }

    /**
     * @return a pseudorandom number n for which lower <= n < upper.
     */
    public static double randDouble(double lower, double upper) {
        return RAND.nextDouble() * (upper - lower) + lower;
    }

    /**
     * @return a pseudorandom number n for which 0 <= n < 1.
     */
    public static double random() {
        return RAND.nextDouble();
    }

    /**
     * @return a pseudorandom number n for which lower <= n < upper.
     */
    public static int randIntTriangular(int lower, int upper) {
        return (RAND.nextInt(upper - lower) + RAND.nextInt(upper - lower)) / 2 + lower;
    }

    /**
     * @return a pseudorandom number n for which lower <= n < upper.
     */
    public static double randDoubleTriangular(double lower, double upper) {
        return randomTriangular() * (upper - lower) + lower;
    }

    /**
     * @return a pseudorandom number n for which 0 <= n < 1.
     */
    public static double randomTriangular() {
        return (RAND.nextDouble() + RAND.nextDouble()) / 2;
    }

}
