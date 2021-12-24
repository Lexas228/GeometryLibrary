package ru.vsu.algorithms;

import org.junit.Before;
import org.junit.Test;
import ru.vsu.model.ConvexPolygon;
import ru.vsu.model.NotConvexPolygonException;
import ru.vsu.model.Point;
import ru.vsu.model.Polygon;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class PolygonAlgorithmsTest {

    private ConvexPolygon polygon;

    @Before
    public void setUp() throws NotConvexPolygonException {
        List<Point> pointList = new ArrayList<>(List.of(new Point(5, 3), new Point(5, 10), new Point(12, 10), new Point(12, 3)));
        polygon = new ConvexPolygon(pointList);
    }

    @Test
    public void perimeterTest(){
        double p = PolygonAlgorithms.getPerimeter(polygon);
        assertTrue(DoubleHelper.isEqual(28, p));
    }

    @Test
    public void areaTest(){
        double area = PolygonAlgorithms.getArea(polygon);
        assertTrue(DoubleHelper.isEqual(49, area));
    }


    @Test
    public void  shouldReturnTrueOnConvexTest(){
        boolean isConvex = PolygonAlgorithms.isConvex(polygon);
        assertTrue(isConvex);
    }

    @Test
    public void shouldReturnFalseOnConvexTest(){
        //add some bad point to polygon
        List<Point> pointList = List.of(new Point(5, 3), new Point(5, 10), new Point(12, 10), new Point(12, 3), new Point(17, 4));
        assertFalse(PolygonAlgorithms.isConvex(new Polygon(pointList)));
    }

    @Test
    public void shouldReturnTrueOnRegular(){
        assertTrue(PolygonAlgorithms.isRegular(polygon));
    }

    @Test
    public void shouldReturnFalseOnRegular(){
        List<Point> pointList = new ArrayList<>(List.of(new Point(5, 3), new Point(5, 10), new Point(12, 10), new Point(12, 3), new Point(17, 4), new Point(17, 4)));
        assertFalse(PolygonAlgorithms.isRegular(new Polygon(pointList)));
    }

    @Test
    public void shouldReturnTrueOnPointIsInside(){
        Point p = new Point(7, 4);
        assertTrue(PolygonAlgorithms.isInside(polygon, p));
    }

    @Test
    public void shouldReturnFalseOnPointIsInside(){
        Point p = new Point(1, 4);
        assertFalse(PolygonAlgorithms.isInside(polygon, p));
    }

    @Test
    public void shouldCorrectFindIntersection() throws NotConvexPolygonException {
        //ok lets create new Polygon
        List<Point> newPoints = new ArrayList<>();
        newPoints.add(new Point(9, 8));
        newPoints.add(new Point(9, 13));
        newPoints.add(new Point(14, 13));
        newPoints.add(new Point(14, 8));
        ConvexPolygon newOne = new ConvexPolygon(newPoints);
        Polygon answer = PolygonAlgorithms.getIntersectionOfPolygons(newOne, polygon);
        List<Point> points = answer.getPoints();
        System.out.println(points);
        //expect four points
        assertEquals(4, points.size());
    }

    @Test
    public void shouldCorrectFindUnion() throws NotConvexPolygonException {
        List<Point> newPoints = new ArrayList<>();
        newPoints.add(new Point(9, 8));
        newPoints.add(new Point(9, 13));
        newPoints.add(new Point(14, 13));
        newPoints.add(new Point(14, 8));
        ConvexPolygon newOne = new ConvexPolygon(newPoints);
        Polygon answer = PolygonAlgorithms.getUnionOfPolygons(polygon, newOne);
        System.out.println(answer.getPoints());
        assertEquals(8, answer.getPoints().size());
    }


    @Test
    public void shouldCorrectFindFirstNotSecond() throws NotConvexPolygonException {
        List<Point> newPoints = new ArrayList<>();
        newPoints.add(new Point(9, 8));
        newPoints.add(new Point(9, 13));
        newPoints.add(new Point(14, 13));
        newPoints.add(new Point(14, 9));
        ConvexPolygon newOne = new ConvexPolygon(newPoints);
        Polygon answer = PolygonAlgorithms.getSubtraction(polygon, newOne);
        System.out.println(answer.getPoints());
        assertEquals(6, answer.getPoints().size());
    }

    @Test
    public void test(){
        List<Point> points = new ArrayList<>();
        points.add(new Point(1, 1));
        points.add(new Point(2, 1));
        points.add(new Point(2, 0));
        points.add(new Point(1, 0));
        System.out.println(PolygonAlgorithms.getArea(new Polygon(points)));
    }



}
