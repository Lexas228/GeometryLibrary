package ru.vsu.algorithms;

import ru.vsu.model.Point;
import ru.vsu.model.Section;

import static ru.vsu.algorithms.DoubleHelper.isEqual;

public class SectionAlgorithms {


    //длинна отрезка
    public static<T extends Section> double distance(T section){
        return PointAlgorithms.distance(section.getStart(), section.getEnd());
    }
    //проверка приндлежит ли точка отрезку
    public static<T extends Section, K extends Point> boolean isBelong(T section, K point){
        double distanceOne = PointAlgorithms.distance(section.getStart(), point);
        double distanceTwo = PointAlgorithms.distance(section.getEnd(), point);
        return isEqual(distance(section), distanceTwo+distanceOne);
    }

    //Проверка на то, пересекаются ли два отрезка
    public static <T extends Section> boolean isIntersect(T one, T two){
      return getIntersectionPoint(one, two) != null;
    }

    //найти точку пересечения двух отрезков (null если пересечения нету)
    public static <T extends Section> Point getIntersectionPoint(T sectionOne, T sectionTwo){
        double A1 = sectionOne.getEnd().getY() - sectionOne.getStart().getY();
        double B1 = sectionOne.getStart().getX() - sectionOne.getEnd().getX();
        double C1 = A1 * sectionOne.getStart().getX() + B1 * sectionOne.getStart().getY();
        double A2 = sectionTwo.getEnd().getY() - sectionTwo.getStart().getY();
        double B2 = sectionTwo.getStart().getX() - sectionTwo.getEnd().getX();
        double C2 = A2 * sectionTwo.getStart().getX() + B2 * sectionTwo.getStart().getY();
        //lines are parallel
        double det = A1 * B2 - A2 * B1;
        if (isEqual(det, 0d))
        {
            return null; //parallel lines
        }
        else
        {
            double x = (B2 * C1 - B1 * C2) / det;
            double y = (A1 * C2 - A2 * C1) / det;
            boolean online1 = isOnline(x, y, sectionOne);
            boolean online2 = isOnline(x, y, sectionTwo);
            if (online1 && online2)
                return new Point(x, y);
        }
        return null; //intersection is at out of at least one segment.
    }

    private static <T extends Section> boolean isOnline(double x, double y, T section){
        return ((Math.min(section.getStart().getX(),section.getEnd().getX()) < x
                || isEqual(Math.min(section.getStart().getX(),section.getEnd().getX()), x))
                && (Math.max(section.getStart().getX(),section.getEnd().getX()) > x
                || isEqual(Math.max(section.getStart().getX(),section.getEnd().getX()), x))
                && (Math.min(section.getEnd().getY(), section.getStart().getY()) < y
                || isEqual(Math.min(section.getEnd().getY(), section.getStart().getY()), y))
                && (Math.max(section.getEnd().getY(), section.getStart().getY()) > y
                || isEqual(Math.max(section.getEnd().getY(), section.getStart().getY()), y)));
    }





}
