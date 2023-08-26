package BlockContainerTest;

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

    List<Set<T>> blocks;
    int n;

    public BlockContainer(int n) {
        blocks = new ArrayList<>();
        this.n = n;
    }

    public void add(T a) {
        if (blocks.size() == 0) {
            Set<T> single = new TreeSet<>();
            single.add(a);
            blocks.add(single);
        } else {
            Set<T> single = blocks.get(blocks.size() - 1);
            if (single.size() == n) {
                single = new TreeSet<>();
                single.add(a);
                blocks.add(single);

            } else {
                single.add(a);
            }
        }
    }

    public boolean remove(T a) {
        boolean result = false;
        if (blocks.size() > 0) {
            Set<T> single = blocks.get(blocks.size() - 1);
            result = single.remove(a);
            if (single.size() == 0) blocks.remove(blocks.size() - 1);
        }
        return result;
    }

    public void sort() {
        ArrayList<T> all = new ArrayList<T>();
        for (int i = 0; i < blocks.size(); ++i) {
            Iterator<T> it = blocks.get(i).iterator();
            while (it.hasNext()) {
                all.add(it.next());
            }
        }
        Collections.sort(all);
        blocks = new ArrayList<Set<T>>();
        for (T element : all) {
            add(element);
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < blocks.size(); ++i) {
            sb.append(blocks.get(i).toString());
            if (i < blocks.size() - 1) {
                sb.append(",");
            }
        }
        return sb.toString();
    }
}


