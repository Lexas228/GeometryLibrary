package ru.vsu.model;

public class IncorrectPolygonNumber extends Exception{

    public IncorrectPolygonNumber(int actual, int except){
        super("the number of polygons is " + actual +", but excepted more than " + except);
    }
}
