package com.softi;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class HelloOtus {

    public static void main(String[] args) {
        System.out.println("Hello Otus!)");

        List<Integer> collect = IntStream.range(1, 20).boxed().collect(Collectors.toList());

        System.out.println(Lists.reverse(collect));
    }

}
