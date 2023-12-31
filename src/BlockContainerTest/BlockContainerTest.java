package BlockContainerTest;//package BlockContainerTest;

import java.util.*;

public class BlockContainerTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int size = scanner.nextInt();
        BlockContainer<Integer> integerBC = new BlockContainer<Integer>(size);
        scanner.nextLine();
        Integer lastInteger = null;
        for (int i = 0; i < n; ++i) {
            int element = scanner.nextInt();
            lastInteger = element;
            integerBC.add(element);
        }
        System.out.println("+++++ Integer Block Container +++++");
        System.out.println(integerBC);
        System.out.println("+++++ Removing element +++++");
        integerBC.remove(lastInteger);
        System.out.println("+++++ Sorting container +++++");
        integerBC.sort();
        System.out.println(integerBC);
        BlockContainer<String> stringBC = new BlockContainer<String>(size);
        String lastString = null;
        for (int i = 0; i < n; ++i) {
            String element = scanner.next();
            lastString = element;
            stringBC.add(element);
        }
        System.out.println("+++++ String Block Container +++++");
        System.out.println(stringBC);
        System.out.println("+++++ Removing element +++++");
        stringBC.remove(lastString);
        System.out.println("+++++ Sorting container +++++");
        stringBC.sort();
        System.out.println(stringBC);
    }
}

class BlockContainer<T extends Comparable<T>> {

    List<Set<T>> elements;
    int n;

    public BlockContainer(int n) {
        this.n = n;
        elements = new ArrayList<>();
    }

    public void add(T a) {
        if (elements.size() == 0) {
            Set<T> s = new TreeSet<>();
            s.add(a);
            elements.add(s);
        } else {
            Set<T> s = elements.get(elements.size() - 1);
            if (s.size() < n) s.add(a);
            else {
                s = new TreeSet<>();
                s.add(a);
                elements.add(s);
            }
        }
    }

    public boolean remove(T a) {

        boolean res=false;
        if (elements.size() != 0) {
            Set<T> s = elements.get(elements.size() - 1);
            res=s.remove(a);
            if (s.size() == 0)
                elements.remove(s);


        }
        return res;
    }

    public void sort() {
    List<T> all=new ArrayList<>();
    for(Set<T> a:elements)
        all.addAll(a);
    Collections.sort(all);
    elements=new ArrayList<>();
    for(T element:all)
        add(element);

    }

    @Override
    public String toString() {
        StringBuilder stringBuilder=new StringBuilder();
        for(Set<T> set:elements)
            stringBuilder.append(set).append(",");

        return stringBuilder.substring(0,stringBuilder.length()-1).toString();
    }
}



