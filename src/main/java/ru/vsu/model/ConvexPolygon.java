package ru.vsu.model;

import ru.vsu.algorithms.PolygonAlgorithms;

import java.util.ArrayList;
import java.util.List;

public class ConvexPolygon extends Polygon{
    public ConvexPolygon(List<Point> points) throws NotConvexPolygonException{
        if(!PolygonAlgorithms.isConvex(new Polygon(points))){
            throw new NotConvexPolygonException();
        }
        this.points = points;
    }

    public ConvexPolygon() {
        super();
    }

    @Override
    public boolean add(Point p) {
        points.add(p);
        if(!PolygonAlgorithms.isConvex(this)){
            points.remove(p);
            return false;
        }
        return true;
    }
}
