package Aud4;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Vezba {

    public static void main(String[] args) {
        List<Integer> integerList=new ArrayList<Integer>();
        List<String> stringList=new ArrayList<String>();
        integerList.add(3);
        integerList.add(5);
        integerList.add(2);
        integerList.add(5);
        integerList.add(1);
        System.out.println(integerList);
        System.out.println(integerList.removeIf(i -> i >3 || i<2));
        System.out.println(integerList);
        //integerList.stream().map().filter().collect(Collectors.toList());


    }




}
