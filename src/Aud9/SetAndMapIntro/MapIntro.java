package Aud9.SetAndMapIntro;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;

public class MapIntro {

    public static void main(String[] args) {
        //kolekcija na key and value

        //MORA KLUCHOT DA E COMPARABLE
        //izbegnuba duplikat kluchevi
        //kmapata e sortirana spored klucot
        // O(log n) za dodvanje ,za contains, O(nlog n) za iteriranje
        Map<String,String> treeMap=new TreeMap<>();
        treeMap.put("FINKI","FINKI");
        treeMap.put("FInKi","Finki");
        treeMap.put("NP","NAPREDNO");
        treeMap.put("F","FFUUUUUUU");
        treeMap.put("F","drugo");
        //ne mozi duplikat kluchevi vo treeMap
        System.out.println(treeMap);
        //Hash map
        //O(1) dodavanjem O(1) contains, O(n) iteriranje
        // se dodava elementot vo nekoja koficka, spored klucot se presmetuva
        //elementite sho se vo tip kluc mora da imaat oerriden hashCode metod

        Map<String,String> hashmap=new HashMap<>();
        hashmap.put("FINKI","FINKI");
        hashmap.put("FInKi","Finki");
        hashmap.put("NP","NAPREDNO");
        hashmap.put("F","FFUUUUUUU");
        hashmap.put("F","drugo");
        System.out.println(hashmap);
        //LinkedHashMap
        //ista so hash map
        //no se zadruzva redosledot

        Map<String,String> linkedHashMap=new LinkedHashMap<>();

        linkedHashMap.put("FINKI","FINKI");
        linkedHashMap.put("FInKi","Finki");
        linkedHashMap.put("NP","NAPREDNO");
        linkedHashMap.put("F","FFUUUUUUU");
        linkedHashMap.put("F","drugo");
        //se koristat za broenje mapite
        //za grupiranje se koristat
        // mapiraj gi site stringovi koi sho zapochunvaat s oodredena bukva
        // Map<String,List<String>> hash so closed kofi


        System.out.println(linkedHashMap);

    }
}
