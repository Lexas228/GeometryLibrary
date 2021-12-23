package ru.vsu.model;

import java.util.ArrayList;
import java.util.List;

public class Polygon implements Figure{
    protected List<Point> points;

    public Polygon() {
        this(new ArrayList<>());
    }

    public Polygon(List<Point> points){
        this.points = new ArrayList<>(points);
    }

    public List<Point> getPoints() {
        return new ArrayList<>(points);
    }


    public boolean add(Point p){
        return points.add(p);
    }
}
