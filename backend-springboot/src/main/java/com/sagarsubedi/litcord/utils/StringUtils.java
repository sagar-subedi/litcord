package com.sagarsubedi.litcord.utils;

public class StringUtils {
    public static String extractFileName(String url) {
        if (url == null || !url.contains(".com/")) {
            throw new IllegalArgumentException("Invalid URL format");
        }
        // Extract substring after ".com/"
        int startIndex = url.indexOf(".com/") + 5;
        return url.substring(startIndex);
    }
}
