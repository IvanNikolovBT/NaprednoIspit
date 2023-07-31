package TermFrequencyTest;

import java.io.*;
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
        String[] stop = new String[] { "во", "и", "се", "за", "ќе", "да", "од",
                "ги", "е", "со", "не", "тоа", "кои", "до", "го", "или", "дека",
                "што", "на", "а", "но", "кој", "ја" };
        TermFrequency tf = new TermFrequency(System.in,
                stop);
        System.out.println(tf.countTotal());
        System.out.println(tf.countDistinct());
        System.out.println(tf.mostOften(10));
    }
}
// vasiot kod ovde
class TermFrequency
{


    Map<String,Integer> frequency;
    public TermFrequency(InputStream inputStream, String[] stopWords)
    {
        frequency=new TreeMap<>();
        Scanner scanner=new Scanner(inputStream);
        while(scanner.hasNext())
        {
          String line=scanner.nextLine();
          line=line.trim();
          if(line.length()>0)
          {
              String[]words=line.split(" ");
              for(String w:words) {
                  String key = normalize(w);
                  if (key.isEmpty() || !checkIgnore(key,stopWords))
                  {
                      continue;
                  }
                  if(frequency.containsKey(key))
                  {
                      int count=frequency.get(key);
                      frequency.put(key,count+1);
                  }
                  else
                  {
                      frequency.put(key,1);
                  }
              }
          }

        }
    }

    private String normalize(String w) {
        return w.toLowerCase().replace(",","").replace(".","").trim();
    }

    private static String wordFactory(String word) {
        String newWord="";
        for(Character c: word.toCharArray())
        {
                if(Character.isAlphabetic(c))
                newWord = newWord +Character.toLowerCase(c);
                else return newWord;
        }
        return newWord;
    }

    public int countTotal()
    {
        return frequency.values().stream().mapToInt(i-> i).sum();
    }


    private boolean checkIgnore(String word,String[] stopWords) {
        for(String w:stopWords)
        {
            if(w.equals(word))
                return false;
        }
        return  true;
    }


    public int countDistinct()
    {
        return frequency.keySet().size();
    }
//    public List<String> mostOften(int k)
////    {
////        return frequency.entrySet().stream().sorted(Comparator.comparing(Entry::getValue)).limit(k).map(i->i.getKey()).collect(Collectors.toList());
////    }
public List<String> mostOften(int k)
{
    return  frequency.entrySet()
            .stream()
            .sorted(Map.Entry.<String, Integer>comparingByValue(Comparator.reverseOrder())).limit(k).map(Map.Entry::getKey).collect(Collectors.toList());
}}