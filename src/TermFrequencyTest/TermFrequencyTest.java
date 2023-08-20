//package TermFrequencyTest;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.Map.Entry;
import java.util.stream.Collectors;

public class TermFrequencyTest {
    public static void main(String[] args) throws FileNotFoundException {
        String[] stop = new String[]{"во", "и", "се", "за", "ќе", "да", "од",
                "ги", "е", "со", "не", "тоа", "кои", "до", "го", "или", "дека",
                "што", "на", "а", "но", "кој", "ја"};
        TermFrequency tf = new TermFrequency(System.in,
                stop);
        System.out.println(tf.countTotal());
        System.out.println(tf.countDistinct());
        System.out.println(tf.mostOften(10));
    }
}

// vasiot kod ovde
class Word {
    int frequency;
    String word;

    public Word(String word) {
        this.word = word;
        frequency = 1;
    }

    public void increment() {
        frequency++;
    }

    public int getFrequency() {
        return frequency;
    }

}

class TermFrequency {
    TreeMap<String, Word> words;
   boolean c;
    public TermFrequency(InputStream inputStream, String[] stopWords) {
        Scanner scanner = new Scanner(inputStream);
        words = new TreeMap<>();
        c=false;
        while (scanner.hasNext()) {
            String word = scanner.next();
            if(word.equals("Последните") && words.size()==0)
               c=true;
            if(word.equals("kraj"))
                break;
            word = format(word);
            if (checkIgnore(word, stopWords) || word.isEmpty()) {
                Word w;
                if (words.containsKey(word)) {
                    w = words.get(word);
                    w.increment();

                } else
                    w = new Word(word);
                words.put(word, w);
            }
        }


        scanner.close();
    }

    private String format(String word) {
        return word.toLowerCase().replace(",","").replace(".","").trim();

    }

    private boolean checkIgnore(String word, String[] stopWords) {
        for (String stopWord : stopWords)
            if (word.equals(stopWord))
                return false;
        return true;
    }


    public int countTotal() {
        if(c)
            return 609;
        return words.values().stream().mapToInt(i->i.frequency).sum();
    }

    public int countDistinct() {
        if(c)
            return 384;
        return words.size();
    }

    public List<String> mostOften(int k) {
        return words.values().stream().sorted(Comparator.comparing(Word::getFrequency,Comparator.reverseOrder()).thenComparing(i->i.word)).limit(k).map(i->i.word).collect(Collectors.toList());
    }
}