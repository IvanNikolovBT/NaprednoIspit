package Aud8.ArrangeLetters;

import java.util.Arrays;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

public class ArrangeLetters {

    public static String arrange(String sentence)
    {
        //1. Stream of [Oo pSk,sO]
        //2. map->Stream[Ok, Skp, Os]
        //3, sorted -> Stream[Ok, Os, Skp]
        return Arrays.stream(sentence.split("\\s+")).
                map(ArrangeLetters::arrangeWord).
                sorted().collect(Collectors.joining(" "));

    }

    private static String arrangeWord(String word) {
        //TO DO
        char capitalLetter = word.chars()
                .filter(Character::isUpperCase)
                .mapToObj(c -> (char) c)
                .findFirst().get();
        String otherLetters=word.chars().filter(Character::isLowerCase).mapToObj(c->String.valueOf((char)c)).collect(Collectors.joining());
//        Optional<Character> capitalLetter = word.chars()
//                .filter(Character::isUpperCase)
//                .mapToObj(c -> (char) c)
//                .findFirst();
//
//        if (capitalLetter.isPresent()) {
//            char letter = capitalLetter.get();
//        } else {
//            return String.format("no capital");
//        }
        return null;
    }

    public static void main(String[] args) {
        Scanner scanner=new Scanner(System.in);
        String line= scanner.nextLine();
        System.out.println(arrange(line));
        System.out.println(arrangeWord(line));
    }
}
