package com.nativapps.arpia.util;

/**
 * @author Gustavo Pacheco <ryctabo@gmail.com>
 * @version 1.0
 */
public class KeyGenerator {

    private KeyGenerator() {}

    private static final String NUMBERS = "0123456789";

    private static final String MAYUS = "ABCDEFGHIJKLMNOPQRSTUWXYZ";

    private static final String MINUS = "abcdefghijklmnopqrstuwxyz";

    public static String getKey() {
        return buildKey(MAYUS + NUMBERS + MINUS ,(int) (3 * Math.random() + 6));
    }

    public static String getKey(int length) {
        return buildKey(MAYUS + NUMBERS + MINUS , length);
    }

    public static String getNumberKey(int length){
        return buildKey(NUMBERS, length);
    }

    public static String buildKey(String sample, int length) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < length; i++)
            builder.append(sample.charAt((int) (Math.random() * sample.length())));
        return builder.toString();
    }

}
