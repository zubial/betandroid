package net.zubial.betandroid.helpers;

import java.text.DecimalFormat;

public final class UiFormatter {

    private UiFormatter() {
        // Private const
    }

    public static String formatVoltage(Integer voltage) {
        String result = "";

        if (voltage != null) {
            Double dVoltage = voltage.doubleValue();
            dVoltage = dVoltage / 10;
            result = dVoltage.toString();
            result += "v";
        }

        return result;
    }

    public static String formatBatteryCells(Integer voltage) {
        double nbCells = Math.floor(voltage / 4.3) + 1;
        return nbCells + "S";
    }

    public static String formatByteSize(long size) {
        if (size <= 0) return "0";
        final String[] units = new String[]{"kB", "MB", "GB", "TB"};
        int digitGroups = (int) (Math.log10(size) / Math.log10(1024));
        return new DecimalFormat("#,##0.#").format(size / Math.pow(1024, digitGroups)) + " " + units[digitGroups];
    }
}
