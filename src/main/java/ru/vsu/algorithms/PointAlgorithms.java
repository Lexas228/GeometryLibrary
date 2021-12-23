package ru.vsu.algorithms;

import ru.vsu.model.Point;

import static java.lang.Math.acos;
import static java.lang.Math.sqrt;

public class PointAlgorithms {

    public static<T extends Point> double distance(T a, T b){
        double one = b.getX() - a.getX();
        double two = b.getY() - a.getY();
        return sqrt(one * one + two * two);
    }

    public static<T extends Point> double getAngle(T a, T b, T c){
        double x1 = a.getX() - b.getX(), x2 = c.getX() - b.getX();
        double y1 = a.getY() - b.getY(), y2 = c.getY() - b.getY();
        double d1 = sqrt (x1 * x1 + y1 * y1);
        double d2 = sqrt (x2 * x2 + y2 * y2);
        return acos ((x1 * x2 + y1 * y2) / (d1 * d2));
    }

}
