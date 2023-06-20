package Aud11;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

class Searcher extends Thread {
    int start;
    int end;
    int max;

    public Searcher(int start, int end) {
        this.start = start;
        this.end = end;
        max = ParallelMax.ARRAY[start];
    }

    @Override
    public void run() {
        for (int i = start; i < end; i++) {
            if (ParallelMax.ARRAY[i] > max)
                max = ParallelMax.ARRAY[i];
        }
    }

    public int getMax() {
        return max;
    }
}

public class ParallelMax {
    static int SIZE = 1000000;
    static int SEARCHER = 1000;
    static int ARRAY[] = new int[SIZE];
    static Random RANDOM = new Random();

    public static void main(String[] args) {
        for (int i = 0; i < SIZE; i++) {
            ARRAY[i] = RANDOM.nextInt(SIZE);
        }
        List<Searcher> searcherList=new ArrayList<>();
        int step=SIZE/SEARCHER;
        for(int start=0;start<SIZE;start+=step)
        {
            int end=start+step;
            searcherList.add(new Searcher(start,end));
        }
        searcherList.forEach(Searcher::start);
        for(Searcher searcher:searcherList)
        {
            try {
                searcher.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        LocalDateTime start=LocalDateTime.now();
        int max=Arrays.stream(ARRAY).max().getAsInt();
        LocalDateTime end=LocalDateTime.now();
        System.out.println(Duration.between(start,end).toMillis());
        System.out.println(searcherList.stream().mapToInt(Searcher::getMax).max().getAsInt());
    }
}
