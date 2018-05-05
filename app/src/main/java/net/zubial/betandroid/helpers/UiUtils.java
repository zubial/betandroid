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
}
