package com.temp.common.base.lombok.singular;

import lombok.Builder;
import lombok.Getter;
import lombok.Singular;

import java.util.*;
import java.util.concurrent.ConcurrentSkipListMap;

import com.google.common.collect.ImmutableList;

import static javax.swing.UIManager.put;

@Builder
public class SingularExample<T extends Number> {
    private @Singular Set<String> occupations;
    private @Singular("axis") ImmutableList<String> axes;
    private @Getter@Singular SortedMap<Integer, T> elves;
    private @Singular Collection<?> minutiae;

    public static void main(String[] args) {
        SingularExampleBuilder<Number> builder = SingularExample.builder();
        builder.elves(new TreeMap<Integer,Integer>(){
            {
                put(1,1);
                put(2,2);
            }
        });
        builder.elf(3,3);
        builder.elf(1,2);
        builder.elf(4,5);
        SingularExample<Number> build = builder.build();
        System.out.println(build.getElves());
        SortedMap<Integer, Number> elves = build.getElves();
        System.out.println(elves.lastKey());
//        System.out.println(elves.descendingMap());



    }
}