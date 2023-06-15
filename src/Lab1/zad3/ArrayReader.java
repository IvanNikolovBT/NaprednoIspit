package Lab1.zad3;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Scanner;

class ArrayReader {
    public static IntegerArray readIntegerArray(InputStream inputStream)
    {
        Scanner scanner=new Scanner(inputStream);
        int n=scanner.nextInt();
        int[] a=new int[n];
        for(int i=0;i<n;i++)
            a[i]=scanner.nextInt();
        scanner.close();
        return new IntegerArray(a);
    }
}
