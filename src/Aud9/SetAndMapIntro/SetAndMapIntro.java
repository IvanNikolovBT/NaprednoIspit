package Aud9.SetAndMapIntro;

import java.util.*;

public class SetAndMapIntro {
    public static void main(String[] args) {
        Set<Integer> treeInset = new TreeSet<>(Comparator.reverseOrder());
        for (int i = 1; i <= 10; i++)
            treeInset.add(i);
        // O(log n) pristap do element
        // iteriranje e O(n log n)
        // O(n log n)
        // zadaci vo koi se bara da se cuvat unikatni elmenti i istite da se sortirani

        System.out.println(treeInset);
        Set<Integer> hashSet = new HashSet<>();

        // mora da e hashable klasata
        // O(1) dodavanje, brishenje i site operacii . AKo e pobarano
        // da se dodat element ibez da nadmini O(n) znachi hash
        // ne se zachuvuva redosledot na vnesuvanje

        for (int i = 1; i <= 10; i++)
            hashSet.add(i);

        System.out.println(hashSet);
        Set<String> lindHashStringSet=new LinkedHashSet<>();
        //LinkedHash set
        //za da se zachuva redosledot na vnesuvanje
        lindHashStringSet.add("zdravo");
        lindHashStringSet.add("kako si");
        lindHashStringSet.add("i am very young");
        System.out.println(lindHashStringSet);
    }
}
