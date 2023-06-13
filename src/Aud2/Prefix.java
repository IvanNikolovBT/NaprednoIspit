package Aud2;

import java.util.stream.IntStream;

public class Prefix {

    public static boolean isPrefix( String str1, String str2)
    {
        if (str1.length()>str2.length())
            return false;
        for (int i =0;i<str1.length();i++)
        {
            if (str1.charAt(i)!=str2.charAt(i))
                return false;
        }
        return true;

    }
    public static boolean isPrefixStream( String str1, String str2)
    {
        if (str1.length()>str2.length())
            return false;
        return IntStream.range(0, str1.length()).noneMatch(i -> str1.charAt(i) != str2.charAt(i));

    }
    public static double sum(double [][] matrica)
    {
        double suma=0;
        for (double[] doubles : matrica) {
            for (int j = 0; j < matrica.length; j++)
                suma += doubles[j];

        }
        return suma;
    }

    public static void main(String[] args) {

    }
}
