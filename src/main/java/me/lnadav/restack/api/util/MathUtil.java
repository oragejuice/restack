package me.lnadav.restack.api.util;

public class MathUtil {

    public static boolean safeIntegerCompare(Integer a, int b)
    {
        if (a != null)
            return a.intValue() == b;
        return false;
    }

    public static int clamp(int value, int min, int max){
        return Math.max(min, Math.min(value, max));
    }

    public static float clamp(float value, float min, float max){
        return Math.max(min, Math.min(value, max));
    }

    public static double clamp(double value, double min, double max){
        return Math.max(min, Math.min(value, max));
    }
}
