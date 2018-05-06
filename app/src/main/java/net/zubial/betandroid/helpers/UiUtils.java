package net.zubial.betandroid.helpers;

public final class UiUtils {

    private UiUtils() {
        // Private const
    }

    public static Boolean isTrue(Boolean value) {
        return (value != null && value);
    }

    public static Boolean isGtZero(Integer value) {
        return (value != null && value > 0);
    }

    public static Integer getPercent(Integer gtValue, Integer ltValue) {
        Double result = ltValue.doubleValue() / gtValue.doubleValue();
        result = result * 100.0;
        result = Math.floor(result);

        return result.intValue();
    }

    public static Integer getBatteryCells(Double voltage, Double maxCellVoltage) {
        Double nbCells = Math.floor(voltage / maxCellVoltage) + 1;
        return nbCells.intValue();
    }
}
