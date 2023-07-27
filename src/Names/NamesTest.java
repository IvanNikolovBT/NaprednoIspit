package Names;

import java.util.*;
import java.util.stream.Collectors;

public class NamesTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        scanner.nextLine();
        Names names = new Names();
        for (int i = 0; i < n; ++i) {
            String name = scanner.nextLine();
            names.addName(name);
        }
        n = scanner.nextInt();
        System.out.printf("===== PRINT NAMES APPEARING AT LEAST %d TIMES =====\n", n);
        names.printN(n);
        System.out.println("===== FIND NAME =====");
        int len = scanner.nextInt();
        int index = scanner.nextInt();
        System.out.println(names.findName(len, index));
        scanner.close();

    }
}

class Names
{

    Map<String,Integer> count;
    public Names()
    {

        count=new TreeMap<>(String::compareToIgnoreCase);
    }
    public void addName(String name) {
        count.computeIfPresent(name, (k, v) -> v+1);
        count.putIfAbsent(name, 1);
    }
    public void printN(int n)
    {
        count.entrySet().stream().filter(a->a.getValue()>=n).forEach(i-> System.out.println(String.format("%s (%d) %d",i.getKey(),i.getValue(),countUnique(i.getKey()))));
    }
    public String findName(int len,int x)
    {
        List<String>names=count.entrySet().stream().filter(a->a.getKey().length()<len).map(i->i.getKey()).collect(Collectors.toList());
        x=x%names.size();
        return names.get(x);
    }
    public int countUnique(String name)
    {
        Set<Character>letters=new HashSet<>();
        for(Character c:name.toCharArray())
        {
            letters.add(Character.toLowerCase(c));
        }
        return letters.size();
    }
}
