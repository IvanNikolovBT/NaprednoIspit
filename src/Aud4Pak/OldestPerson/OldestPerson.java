package Aud4Pak.OldestPerson;

import java.io.*;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class OldestPerson {
    public static List<Person> readFile(InputStream inputStream)
    {
        BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(inputStream));
        return bufferedReader.lines().map(l->new Person(l)).collect(Collectors.toList());
    }

    public static void main(String[] args) throws FileNotFoundException {
        File file=new File("C:\\Users\\PC\\NaprednoIspit\\src\\Aud4Pak\\OldestPerson\\Text.txt");
        List<Person> list=readFile(new FileInputStream(file));
        Collections.sort(list);
        System.out.println(list.get(list.size()-1));
    }
}
