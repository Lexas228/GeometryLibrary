package ru.vsu.algorithms;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class IntroTest {

    @Test
    public void testingOfStream(){

        List<Integer> integerList = List.of(2, 4, 5, 6, 1, 6, 8);
        List<Integer> answer = new ArrayList<>();

        for(Integer k : integerList){
            if(k != 6) answer.add(k);
        }
        System.out.println(answer);


        Stream<Integer> answer2 = integerList.stream()
                .filter(integer -> integer != 6);

        System.out.println(answer2);

    }
}
