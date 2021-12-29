package me.lnadav.restack.api.util;

public class MathUtil {

    public static boolean safeIntegerCompare(Integer a, int b)
    {
        if (a != null)
            return a.intValue() == b;
        return false;
    }
}
