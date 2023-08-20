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

class Word {
    String word;
    int count;
    int uniqe;

    public Word(String word) {
        this.word = word;
        count = 1;
        calcualteUnique(word);
    }

    private void calcualteUnique(String word) {
        Set<Character> uc = new HashSet<>();
        for (Character c : word.toCharArray())
            uc.add(Character.toLowerCase(c));
        uniqe = uc.size();
    }

    public void increment() {
        count++;
    }

    @Override
    public String toString() {
        return String.format("%s (%d) %d", word, count, uniqe);
    }

    public String getWord() {
        return word;
    }

    public int getCount() {
        return count;
    }


}

class Names {
    Map<String, Word> words;

    public Names() {
        words = new TreeMap<>();
    }

    public void addName(String name) {
        Word w;
        if (words.containsKey(name)) {
            w = words.get(name);
            w.increment();
        } else w = new Word(name);

        words.put(name, w);
    }

    public void printN(int n) {
        words.values().stream().filter(i -> i.count >= n).sorted(Comparator.comparing(Word::getWord)).forEach(System.out::println);
    }

    public String findName(int len, int x) {
        List<String> list = words.values().stream().filter(i -> i.word.length() < len).map(i -> i.word).collect(Collectors.toList());
        return list.get(x % list.size());
    }

}