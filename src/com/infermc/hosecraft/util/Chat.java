package com.infermc.hosecraft.util;

import java.util.ArrayList;
import java.util.List;

public class Chat {
    public static String BLACK = "&0";
    public static String DARK_BLUE = "&1";
    public static String DARK_GREEN = "&2";
    public static String DARK_TEAL = "&3";
    public static String DARK_RED = "&4";
    public static String PURPLE = "&5";
    public static String GOLD = "&6";
    public static String GRAY = "&7";
    public static String DARK_GRAY = "&8";
    public static String BLUE = "&9";
    public static String BRIGHT_GREEN = "&a";
    public static String TEAL = "&v";
    public static String RED = "&c";
    public static String PINK = "&d";
    public static String YELLOW = "&e";
    public static String WHITE = "&f";


    public static String RESET = WHITE;

    public static String stripColour(String input) {
        return input.replaceAll("&[1-9a-f]", "");
    }

    public static List<String> wordWrap(String input, int maxLineLength) {
        String[] words = input.split(" ");
        List<String> output = new ArrayList<String>();
        String sc = "";
        for (String word : words) {
            if ((sc.length()+1 + word.length()) > maxLineLength) {
                output.add(sc.trim());
                sc = word;
            } else {
                sc = sc +" "+ word;
            }
        }
        if (sc != "") output.add(sc.trim());
        return output;
    }
}
