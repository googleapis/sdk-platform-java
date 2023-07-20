package com.google.api.gax.util;

public class TimeConversionUtils {
    public static java.time.Duration toJavaTimeDuration(org.threeten.bp.Duration source) {
        if (source == null) {
            return null;
        }
        return toJavaTimeDuration(source);
    }

    public static org.threeten.bp.Duration toThreetenDuration(java.time.Duration source) {
        if (source == null) {
            return null;
        }
        return toThreetenDuration(source);
    }
}
