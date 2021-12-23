package ru.vsu.algorithms;

import ru.vsu.model.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static ru.vsu.algorithms.DoubleHelper.isEqual;
import static ru.vsu.algorithms.SectionAlgorithms.getIntersectionPoint;

public class PolygonAlgorithms {

    public static <T extends Polygon> double getPerimeter(T polygon){
        List<Point> pointList = polygon.getPoints();
        int size = pointList.size();
        if(size < 3) return 0;
        double answer = 0;
        for (int i = 0, j = pointList.size() - 1; i < pointList.size(); j = i++) {
            answer += PointAlgorithms.distance(pointList.get(i), pointList.get(j));
        }
        return answer;
    }

    public static<T extends Polygon> double getArea(T polygon){
        double x = 0;
        double y = 0;
        List<Point> points = polygon.getPoints();
        for (int i = 0; i < points.size(); i++) {
            int next = (i + 1 == points.size()) ? 0 : i + 1;
            x += points.get(i).getX() * points.get(next).getY();
            y += points.get(i).getY() * points.get(next).getX();
        }
        return Math.abs((x-y))/2;
    }

    // проверка на то что выпуклый
    public static <T extends Polygon> boolean isConvex(T polygon){
        List<Point> pointList = new ArrayList<>(polygon.getPoints());
        int size = pointList.size();
        if(size < 3) return false;
        for(int i = 1; i < size-1; i++){
            Point ab = new Point(pointList.get(i).getX() - pointList.get(i-1).getX(), pointList.get(i).getY() - pointList.get(i-1).getY());
            Point bc = new Point(pointList.get(i + 1).getX() - pointList.get(i).getX(), pointList.get(i+1).getY() - pointList.get(i).getY());
            double product = ab.getX() * bc.getY() - ab.getY() * bc.getX();
            if(product > 0) return false;
        }
        return true;
    }

    //проверка на то что правильный
    public static <T extends Polygon> boolean isRegular(T polygon){
        List<Point> pointList = new ArrayList<>(polygon.getPoints());
        int size = pointList.size();
        if(size < 3) return false;
        pointList.add(pointList.get(0));
        double length = PointAlgorithms.distance(pointList.get(0), pointList.get(1));
        double ang = PointAlgorithms.getAngle(pointList.get(0), pointList.get(2), pointList.get(1));
        for(int i = 1; i < size-1; i++){
            double l = PointAlgorithms.distance(pointList.get(0), pointList.get(1));
            double a = PointAlgorithms.getAngle(pointList.get(i-1), pointList.get(i+1), pointList.get(i));
            if(!isEqual(length, l) || !isEqual(ang, a)) return false;
        }
        return true;
    }

    //проверка на то что точка внутри полигона
    public static <T extends Polygon, K extends Point> boolean isInside(T polygon, K point){
        List<Point> points = polygon.getPoints();
        double x = point.getX(), y = point.getY();
        boolean inside = false;
        for (int i = 0, j = points.size() - 1; i < points.size(); j = i++) {
            double xi = points.get(i).getX(), yi = points.get(i).getY();
            double xj = points.get(j).getX(), yj = points.get(j).getY();
            boolean intersect = ((yi > y) != (yj > y))
                    && (x < (xj - xi) * (y - yi) / (yj - yi) + xi);
            if (intersect) inside = !inside;
        }
        return inside;
        }




        public static <T extends ConvexPolygon> Polygon getIntersectionOfPolygons(T one, T two){
           return getSmth(one, two, point -> isInside(one, point), point -> isInside(two, point));
        }


        public static <T extends ConvexPolygon> Polygon getUnionOfPolygons(T one, T two){
            return getSmth(one, two,point -> !isInside(two, point), point -> !isInside(one, point));
        }

        public static <T extends ConvexPolygon> Polygon getSubtraction(T a, T b){
            return getSmth(a, b,point -> isInside(a, point), point -> !isInside(b, point));
        }

        private static <T extends ConvexPolygon> Polygon getSmth(T a, T b, Predicate<Point> firstCondition, Predicate<Point> secondCondition){
            List<Point> clippedCorners = new ArrayList<>();
            List<Point> pointsOfA = a.getPoints();
            List<Point> pointsOfB = b.getPoints();
            pointsOfB.stream()
                    .filter(firstCondition)
                    .forEach(point -> addPoints(clippedCorners, List.of(point)));
            pointsOfA.stream()
                    .filter(secondCondition)
                    .forEach(clippedCorners::add);
            addIntersectionPoints(clippedCorners, a, b);
            return new Polygon(toClockwiseOrder(clippedCorners));
        }

        private static <T extends  Polygon> void addIntersectionPoints(List<Point> pool, T one, T two){
            List<Point> pointsOfOne = one.getPoints();
            for (int i = 0, next = 1; i < pointsOfOne.size(); i++, next = (i + 1 == pointsOfOne.size()) ? 0 : i + 1) {
                addPoints(pool, findIntersectionPoints(new Section(pointsOfOne.get(i), pointsOfOne.get(next)), two));
            }
        }


    private static <T extends  Polygon> List<Point> findIntersectionPoints(Section section, T polygon){
        List<Point> intersectionPoints = new ArrayList<>();
        List<Point> points = polygon.getPoints();
        for (int i = 0; i < points.size(); i++) {
            int next = (i + 1 == points.size()) ? 0 : i + 1;
            Point ip = getIntersectionPoint(section, new Section(points.get(i), points.get(next)));
            if (ip != null) intersectionPoints.add(ip);
        }
        return intersectionPoints;
    }

    private static void addPoints(List<Point> pool, List<Point> newPoints){
        newPoints.stream().filter(point -> !pool.contains(point)).forEach(pool::add);
    }


    private static List<Point> toClockwiseOrder(List<Point> list) {
        double mX = 0;
        double my = 0;
        for (Point p : list) {
            mX += p.getX();
            my += p.getY();
        }
        mX /= list.size();
        my /= list.size();
        return list.stream().sorted(new MyPointComparator(mX, my)).collect(Collectors.toList());
    }

    private static class MyPointComparator implements Comparator<Point>{
        private final double mX;
        private final double mY;
        public MyPointComparator(double mX, double my){
            this.mX = mX;
            this.mY = my;
        }

        @Override
        public int compare(Point o1, Point o2) {
            double v1 = Math.atan2(o1.getY() - mY, o1.getX() - mX);
            double v2 =  Math.atan2(o2.getY() - mY, o2.getX() - mX);
            if(isEqual(v1, v2)) return 0;
            return v2 > v1 ? -1 : 1;
        }
    }




    }
