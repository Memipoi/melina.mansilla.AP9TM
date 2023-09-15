package com.mindhub.homebanking.utils;

public final class CardUtils {
    private CardUtils() {
    }
    public static int getCVV() {
        return Math.toIntExact(Math.round(100 + Math.random() * 900));
    }

    public static String getCardNumber() {
        return Math.round(1000 + Math.random() * 9000) + "-" + Math.round(1000 + Math.random() * 9000) + "-" + Math.round(1000 + Math.random() * 9000) + "-" + Math.round(1000 + Math.random() * 9000);
    }
}
