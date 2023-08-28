package MinMax;

import java.util.Scanner;

class MinMax<T extends Comparable<T>> {
    T min;
    T max;
    int minCount;
    int maxCount;
    int total;
    public MinMax() {
        minCount = 0;
        maxCount = 0;
        total=0;
    }

    public void update(T element) {
        if(total==0)
            min=max=element;
        total++;
        if (element.compareTo(max) == 0) {
            maxCount++;
            max=element;
        }
        if(element.compareTo(max)>0) {
            max=element;
            maxCount=1;
        }
        if(element.compareTo(min)==0)
        {
            minCount++;
            min=element;
        }
        else if(element.compareTo(min)<0)
        {
            minCount=1;
            min=element;
        }
    }

    @Override
    public String toString() {
        return String.format("%s %s %d\n",min,max,total-(maxCount+minCount));
    }

    public T getMin() {
        return min;
    }

    public T getMax() {
        return max;
    }
}


public class MinAndMax {
    public static void main(String[] args) throws ClassNotFoundException {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        MinMax<String> strings = new MinMax<String>();
        for (int i = 0; i < n; ++i) {
            String s = scanner.next();
            strings.update(s);
        }
        System.out.println(strings);
        MinMax<Integer> ints = new MinMax<Integer>();
        for (int i = 0; i < n; ++i) {
            int x = scanner.nextInt();
            ints.update(x);
        }
        System.out.println(ints);
    }
}
