package Aud9.Names;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;

public class Names {

    public static Map<String, Integer> createFromFile(String path) throws FileNotFoundException {
        Map<String, Integer> result = new HashMap<>();
        InputStream is = new FileInputStream(path);
        Scanner sc = new Scanner(is);
        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            String[] parts = line.split("\\s+");
            String name = parts[0];
            Integer freq = Integer.parseInt(name);
            result.put(name, freq);
        }
        return result;
    }

    public static void main(String[] args) throws FileNotFoundException {
        Map<String, Integer> boyNamesMap = createFromFile("bla bla bla");
        Map<String, Integer> girlNamesMap = createFromFile("zenski iminja");
        System.out.println(boyNamesMap);
        System.out.println(girlNamesMap);
        //zaradi hash mapa nema redosled
        Set<String> allNames = new HashSet<>();
        allNames.addAll(boyNamesMap.keySet());
        allNames.addAll(girlNamesMap.keySet());
        allNames.stream().filter(name -> boyNamesMap.containsKey(name)).map(name -> girlNamesMap.get(name)).count();
        allNames.stream().filter(name -> boyNamesMap.containsKey(name) && girlNamesMap.containsKey(name)).
                forEach(name -> System.out.println
                        (String.format("%s: Male: %d Female %d Total: %d ", name, boyNamesMap.get(name), girlNamesMap.get(name), boyNamesMap.get(name) + girlNamesMap.get(name))));
        Map<String,Integer> unisexNames=new HashMap<>();
//        allNames.stream()
//                .filter(name->boyNamesMap.containsKey(name)&& girlNamesMap.containsKey(name))
//                .forEach(name->unisexNames.put(name,boyNamesMap.get(name)+girlNamesMap.get(name)));
        System.out.println(unisexNames);
        Set<Map.Entry<String, Integer>> entrySet = unisexNames.entrySet();
        unisexNames.entrySet().stream().sorted(Map.Entry.comparingByValue(Comparator.reverseOrder())).forEach(System.out::println);
    }
}
