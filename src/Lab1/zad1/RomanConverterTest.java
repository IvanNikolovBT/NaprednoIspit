package Lab1.zad1;

import java.util.Scanner;
import java.util.stream.IntStream;

public class RomanConverterTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        IntStream.range(0, n)
                .forEach(x -> System.out.println(RomanConverter.toRoman(scanner.nextInt())));
        scanner.close();
    }
}


class RomanConverter {

    public static String toRoman(int n) {
        int [] vrednosti  = {1000,900,500,400,100,90,50,40,10,9,5,4,1};
        String [] bukvi = {"M","CM","D","CD","C","XC","L","XL","X","IX","V","IV","I"};
        StringBuilder roman = new StringBuilder();
        for (int i=0; i<vrednosti .length; i++){
            while (n>=vrednosti [i]){
                roman.append(bukvi[i]);
                n=n-vrednosti [i];
            }
        }
        return roman.toString();
    }

}
