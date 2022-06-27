package com.concierge.apiblog.Utils;

import java.text.Normalizer;

public class Utils {
    public static String removeSpecialCharacters(String str) {
        String normalized = Normalizer.normalize(str, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
        return normalized.replaceAll(
                "[^a-zA-Z0-9]", " ");
    }

    public static String modifySpaces(String str) {
        return str.replaceAll("\\s+","-");
    }
}
