package com.temp.common.java8.current;

import java.util.Arrays;
import java.util.Comparator;
import java.util.concurrent.ThreadLocalRandom;

public class ParallelArrays {
    public static void main( String[] args ) {
        Long[] arrayOfLong = new Long [ 20000 ];

        Arrays.parallelSetAll( arrayOfLong,
            index -> ThreadLocalRandom.current().nextLong( 1000000 ) );
        Arrays.stream( arrayOfLong ).limit( 11 ).forEach(
            i -> System.out.print( i + " " ) );
        System.out.println();

//        Arrays.parallelSort( arrayOfLong );
        Arrays.parallelSort( arrayOfLong , (Comparator<Long>) (Long o1, Long o2) -> (int)(o2-o1));
        Arrays.stream( arrayOfLong ).limit( 10 ).forEach(
            i -> System.out.print( i + " " ) );
        System.out.println();

        Long[] arrayOfLong1 = new Long [ 20000 ];
        Arrays.parallelSetAll( arrayOfLong1,
            index -> new Long(ThreadLocalRandom.current().nextInt( 1000000 )) );
        Arrays.stream( arrayOfLong1 ).limit( 10 ).forEach(
                i -> System.out.print( i + " " ) );
        System.out.println();
        Arrays.parallelSort( arrayOfLong1,(Comparator<Long>) (Long o1, Long o2) -> {
            return (int) (o2 - o1);
        });
        Arrays.stream( arrayOfLong1 ).limit( 10 ).forEach(
                i -> System.out.print( i + " " ) );

        System.out.println();


    }
}