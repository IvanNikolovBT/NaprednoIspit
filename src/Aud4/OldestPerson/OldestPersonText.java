package Aud4.OldestPerson;

import java.io.*;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class OldestPersonText {

    public static List<Person> readData(InputStream inputStream) {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        return bufferedReader.lines().map(Person::new).collect(Collectors.toList());
    }

    public static void main(String[] args) throws FileNotFoundException {
        File file = new File("C:\\Users\\PC\\NaprednoIspit\\src\\Aud4\\OldestPerson\\OLDERSPERSON");
        List<Person> people = readData(new FileInputStream(file));
        Collections.sort(people);
        System.out.println(people.get(people.size() - 1));

        if (people.stream().max(Comparator.naturalOrder()).isPresent()) {
            System.out.println(people.stream().max(Comparator.naturalOrder()).get());
        }
    }
}
