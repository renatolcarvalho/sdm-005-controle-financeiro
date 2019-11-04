package br.edu.ifsp.controlefinanceiro.util;

public final class Utils {

    private Utils(){

    }

    public static String formatDecimal(Double number) {
        float epsilon = 0.004f;
        if (Math.abs(Math.round(number) - number) < epsilon) {
            return String.format("%10.0f", number);
        } else {
            return String.format("%10.2f", number);
        }
    }
}
