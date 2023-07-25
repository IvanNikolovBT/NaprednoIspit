package SuperString;

import java.util.LinkedList;
import java.util.Scanner;
import java.util.StringJoiner;

public class SuperStringTest {

    public static void main(String[] args) {
        Scanner jin = new Scanner(System.in);
        int k = jin.nextInt();
        if (k == 0) {
            SuperString s = new SuperString();
            while (true) {
                int command = jin.nextInt();
                if (command == 0) {//append(String s)
                    s.append(jin.next());
                }
                if (command == 1) {//insert(String s)
                    s.insert(jin.next());
                }
                if (command == 2) {//contains(String s)
                    System.out.println(s.contains(jin.next()));
                }
                if (command == 3) {//reverse()
                    s.reverse();
                }
                if (command == 4) {//toString()
                    System.out.println(s);
                }
                if (command == 5) {//removeLast(int k)
                    s.removeLast(jin.nextInt());
                }
                if (command == 6) {//end
                    break;
                }
            }
        }
    }

}

class SuperString {
    String string;
    LinkedList<String> list;

    public SuperString() {
        list = new LinkedList<>();
    }

    public void append(String s) {
        list.add(list.size(), s);
    }

    public void insert(String s) {
        list.add(0, s);
    }

    public boolean contains(String s) {
        return toString().contains(s);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (String s : list)
            stringBuilder.append(s);

        return stringBuilder.toString();
    }

    public void removeLast(int k) {
        for (int i = 0; i < k; i++)
            list.remove();
    }

    public void reverse() {
        LinkedList<String> reversed = new LinkedList<>();
        for (int i = 0; i < list.size(); i++) {
            reversed.add(list.get(list.size()));
            list.remove();
        }
        list=reversed;
    }
}
