package ru.vsu.model;

import ru.vsu.algorithms.DoubleHelper;

import java.util.Objects;

public class Point implements Figure{
    private double x;
    private double y;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    @Override
    public String toString() {
        return "(x:" + x + ", y:" + y+")";
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null || obj.getClass() != getClass())  return false;
        Point another = (Point) obj;
        return DoubleHelper.isEqual(another.getX(), getX()) && DoubleHelper.isEqual(another.getY(), getY());
    }

}
