package ru.vsu.algorithms;

import org.junit.Before;
import org.junit.Test;
import ru.vsu.model.Point;
import ru.vsu.model.Section;


import static org.junit.Assert.*;

public class SectionAlgorithmsTest {
    private Section sectionOne;
    private Section sectionTwo;

    @Before
    public void setUp(){
        sectionOne = new Section(new Point(2, 15), new Point(8, 21));
        sectionTwo = new Section(new Point(2, 18), new Point(9, 18));
    }

    @Test
    public void shouldReturnTrueOnBelong(){
        Point p = new Point(5, 18);
        assertTrue(SectionAlgorithms.isBelong(sectionTwo, p));
    }

    @Test
    public void shouldReturnFalseOnBelong(){
        Point p = new Point(2, 18);
        assertFalse(SectionAlgorithms.isBelong(sectionOne, p));
    }

    @Test
    public void shouldReturnTrueOnIntersect(){
        assertTrue(SectionAlgorithms.isIntersect(sectionOne, sectionTwo));
    }

    @Test
    public void shouldCorrectFindPointOfIntersect(){
        Point p = SectionAlgorithms.getIntersectionPoint(sectionOne, sectionTwo);
        assertNotNull(p);
        assertTrue(DoubleHelper.isEqual(5, p.getX()) && DoubleHelper.isEqual(18, p.getY()));
    }



}
