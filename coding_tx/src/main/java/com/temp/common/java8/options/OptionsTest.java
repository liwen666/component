package com.temp.common.java8.options;

import com.pivotal.jdbc.greenplumbase.ddd1;

import java.util.Date;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * <p>
 * 描述
 * </p>
 *
 * @author LW
 * @since 2021/3/27  10:24
 */
public class OptionsTest {

    public static void main(String[] args) {
        Optional<String> aaa = Optional.of("aaa");
        boolean present = aaa.isPresent();
        System.out.println(present);
        System.out.println("-----------------------------------------------------------------------");
        Optional<String> bbb = Optional.empty();
        boolean present1 = bbb.isPresent();
        System.out.println("-----------------------------------------------------------------------");

        Consumer<Data> consumer = (data) -> {
            data.setName("ccccc");
        };
        Optional<Data> ddd = Optional.of(new Data("ddd"));
        System.out.println(present1);
        ddd.ifPresent(consumer);
        System.out.println(ddd.get().getName());

        System.out.println("-----------------------------------------------------------------------");
        Predicate<Data> predicate = (dat) -> false;
        Optional<Data> data = ddd.filter(predicate);
        System.out.println(data.isPresent());
        System.out.println("-----------------------------------------------------------------------");
        Predicate<Data> predicate1 = (dat) -> true;
        Optional<Data> data11 = ddd.filter(predicate1);
        System.out.println(data11.isPresent());
        System.out.println("-----------------------------------------------------------------------");
        Data other = data.orElse(new Data("other"));
        Data other1 = data11.orElse(new Data("other"));
        System.out.println(other + "   " + other1);

        System.out.println("-----------------------------------------------------------------------");
        Supplier supplier = () -> new Data("supplier");
        Data data2 = data.orElseGet(supplier);
        Data data111 = data11.orElseGet(supplier);
        System.out.println(data2 + "  " + data111);


        System.out.println("-----------------------------------------------------------------------");
        Function<Data ,Optional<Data>> function = (t) -> {
            System.out.println(t+"-------====");
            return Optional.of(new Data("cckkk"));
        };
        Optional<Data> data4 = data.flatMap(function);
        System.out.println(data4);
        Optional optional11 = data11.flatMap(function);
        System.out.println(optional11);
        System.out.println("-----------------------------------------------------------------------");
        Function<Data ,DataChild> map = (t) -> {
            System.out.println(t+"mmmmmmmmmmmmmmmmmmm");
            return new DataChild("mmmmmmmmmmmm");
        };
        Optional<Data> data5 = data.map(map);
        Optional<Data> data8 = data11.map(map);
        System.out.println(data5);
        System.out.println(data8);


        System.out.println("-----------------------------------------------------------------------");

        Supplier supplier1 = () -> new RuntimeException("抛出异常");
        Data data3 = data11.orElseThrow(supplier1);
        Data data1 = data.orElseThrow(supplier1);
        System.out.println(data1 + "" + data3);


    }
}
