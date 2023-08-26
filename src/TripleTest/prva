package TripleTest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class TripleTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int a = scanner.nextInt();
        int b = scanner.nextInt();
        int c = scanner.nextInt();
        Triple<Integer> tInt = new Triple<Integer>(a, b, c);
        System.out.printf("%.2f\n", tInt.max());
        System.out.printf("%.2f\n", tInt.avarage());
        tInt.sort();
        System.out.println(tInt);
        float fa = scanner.nextFloat();
        float fb = scanner.nextFloat();
        float fc = scanner.nextFloat();
        Triple<Float> tFloat = new Triple<Float>(fa, fb, fc);
        System.out.printf("%.2f\n", tFloat.max());
        System.out.printf("%.2f\n", tFloat.avarage());
        tFloat.sort();
        System.out.println(tFloat);
        double da = scanner.nextDouble();
        double db = scanner.nextDouble();
        double dc = scanner.nextDouble();
        Triple<Double> tDouble = new Triple<Double>(da, db, dc);
        System.out.printf("%.2f\n", tDouble.max());
        System.out.printf("%.2f\n", tDouble.avarage());
        tDouble.sort();
        System.out.println(tDouble);
    }
}

class Triple<T extends Number & Comparable<T>> {
    List<T> numbers;

    public Triple(T a, T b, T c) {
        numbers=new ArrayList<>();
        numbers.add(a);
        numbers.add(b);
        numbers.add(c);
    }

    public double max() {
        double val=Collections.max(numbers).doubleValue();
        return val;

    }
    public double avarage()
    {
        return sum()/3;
    }
    private double sum()
    {
        double val=0;
        for(T v:numbers)
            val+=v.doubleValue();
        return val;
    }
    public void sort()
    {
        Collections.sort(numbers);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder=new StringBuilder();
        for(T v:numbers)
        {
           stringBuilder.append(String.format("%.2f ",v.doubleValue()));
        }

        return  stringBuilder.toString();
    }
}


