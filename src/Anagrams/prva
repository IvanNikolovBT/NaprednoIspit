package Anagrams;

import java.io.InputStream;
import java.util.*;

public class Anagrams {

    public static void main(String[] args) {
        findAll(System.in);
    }

    public static void findAll(InputStream inputStream) {
        // Vasiod kod ovde
        Scanner scanner = new Scanner(inputStream);
        TreeMap<Set<Character>, List<String>> anagrams = new TreeMap<>();
        while (scanner.hasNext()) {
            String word = scanner.next();
            Set<Character> uniqeletters = new TreeSet<>();
            for (Character c : word.toCharArray())
                uniqeletters.add(c);
            anagrams.putIfAbsent(uniqeletters,new ArrayList<>());
            if(!anagrams.get(uniqeletters).contains(word))
            {
                anagrams.computeIfPresent(uniqeletters, (k, v) -> {
                    v.add(word);
                    return v;
                });
            }
            scanner.close();



        }
    }
}
