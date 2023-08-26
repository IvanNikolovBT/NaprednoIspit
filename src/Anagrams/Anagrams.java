//package Anagrams;

import java.io.InputStream;
import java.util.*;

public class Anagrams {

    public static void main(String[] args) {
        findAll(System.in);
    }

    public static void findAll(InputStream inputStream) {
             Scanner scanner=new Scanner(inputStream);
             Map<String,Set<String>>anagrams=new TreeMap<>();
             List<String> order=new ArrayList<>();
             while(scanner.hasNext())
             {
                 String word=scanner.next();
                 char[]w=word.toCharArray();
                 Arrays.sort(w);
                 String rep=new String(w);
                 if (anagrams.containsKey(rep)) {
                     Set<String> words = anagrams.get(rep);
                     words.add(word);
                 } else {
                     order.add(rep);
                     Set<String> words = new TreeSet<>();
                     words.add(word);
                     anagrams.put(rep, words);
                 }
                 ListIterator<String> it = order.listIterator();
                 while (it.hasNext()) {
                     Set<String> values = anagrams.get(it.next());
                     if (values.size() >= 5) {
                         Iterator<String> setIt = values.iterator();
                         while (setIt.hasNext()) {
                             System.out.print(setIt.next());
                             if(setIt.hasNext()) {
                                 System.out.print(" ");
                             }
                         }
                         System.out.println();
                     }
                 }

             }


    }
}
class Ang
{
    Set<Character> letters;
    List<String> words;

    public Ang(Set<Character> letters) {
        this.letters = letters;
        words=new ArrayList<>();
    }
    public void addWord(String word)
    {
        words.add(word);
    }
    public String getFirst()
    {
        return words.get(0);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder=new StringBuilder();
        for(String s:words)
        {
            stringBuilder.append(s).append(" ");
        }
        stringBuilder.substring(0,stringBuilder.toString().length());

        return stringBuilder.toString();
    }
}
