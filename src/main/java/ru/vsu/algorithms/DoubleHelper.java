package ru.vsu.algorithms;

public class DoubleHelper {

    public static final double EPS = 1e-9;

    public static boolean isEqual(double one, double two){
        return Math.abs(one-two) <= EPS;
    }

}
