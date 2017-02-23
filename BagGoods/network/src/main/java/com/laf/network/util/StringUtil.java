package com.laf.network.util;

/**
 * Created by apple on 17/2/17.
 */
public class StringUtil {

    public static boolean isNullOrEmpty(String str) {
        return str == null || str.trim().length() == 0;
    }
}
