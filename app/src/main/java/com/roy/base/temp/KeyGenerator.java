package com.roy.base.temp;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class KeyGenerator {
    public static String generateRondomKey(int length){
        //StringBuilder builder = new StringBuilder(length);
        //for (int i = 0; i < length; i++) {
        //    builder.append((char) (ThreadLocalRandom.current().nextInt(33, 128)));
        //}
        //return builder.toString();

        String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int num = random.nextInt(str.length());
            buf.append(str.charAt(num));
        }
        return buf.toString();
    }

    public static String generateRondomHexKey(int length){
        String str = "0123456789ABCDEF";
        Random random = new Random();
        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int num = random.nextInt(str.length());
            buf.append(str.charAt(num));
        }
        return buf.toString();
    }
}
