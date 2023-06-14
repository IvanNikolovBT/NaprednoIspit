package Aud4;

import java.util.Random;
import java.util.function.*;

public class FuncionalnterfaceTest {

    public static void main(String[] args) {

        //predikat se koristi kaj filter ili kaj i da ima uslov
        Predicate<Integer> lesstThan100=new Predicate<Integer>() {
            @Override
            public boolean test(Integer integer) {
                return integer<100;
            }
        };
        Predicate<Integer> less1than100=integer -> { return integer < 100;};
        System.out.println(lesstThan100.test(32));
        System.out.println(less1than100.test(42));
        Supplier<Integer> integerSupplier=new Supplier<Integer>() {
            @Override
            public Integer get() {
                return new Random().nextInt(1000);
            }
        };
        Supplier<Integer> integerSupplier1=()-> new Random().nextInt(1000);
        System.out.println(integerSupplier);
        System.out.println(integerSupplier1);

        Consumer<String> stringConsumer=new Consumer<String>() {
            @Override
            public void accept(String s) {
                System.out.println(s);
            }
        };
        Consumer<String> stringConsumer1= System.out::println;

        Function<Integer,String> convertToString= new Function<Integer, String>() {
            @Override
            public String apply(Integer integer) {
                return String.format("%d",integer);
            }
        };

        Function<Integer,String> formatedNumberString=num->String.format("%d",num);
        BiFunction<Integer,Integer,String> sumNumbersAndFormated=(a,b)->String.format("%d",a+b);
        BiFunction<Integer,Integer,String> sumNumbersFormated= new BiFunction<Integer, Integer, String>() {
            @Override
            public String apply(Integer integer, Integer integer2) {
                return String.format("%d",integer+integer2);
            }


        };
        System.out.println(sumNumbersFormated.apply(1,2));
        System.out.println(sumNumbersAndFormated.apply(1,4));
    }
}
