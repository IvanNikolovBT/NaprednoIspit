package Aud8.BenfordsLaw;

import javax.print.DocFlavor;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class BenfordLawExperiment {
    List<Integer> number;
    Counter counter;
    public BenfordLawExperiment() {
        this.number = new ArrayList<>();
        counter=new Counter();
    }

    public void readData(InputStream inputStream) {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        number = bufferedReader.lines().filter(line -> Character.isDigit(line.charAt(0))).map(Integer::parseInt).collect(Collectors.toList());
    }
    public void conductExperiment()
    {
        number.stream().map(this::getFirstDigit).forEach(firstDigit->counter.addToCounter(firstDigit));
    }

    private int getFirstDigit(int l) {
        while(l>=10)
            l/=10;
        return l;
    }
}

class Counter {

    int countingArray[];
    public Counter()
    {
        countingArray=new int[9];
    }
    public void addToCounter(int digit)
    {
        countingArray[digit]++;
    }

    @Override
    public String toString() {
       StringBuilder stringBuilder=new StringBuilder();
       double sum=Arrays.stream(countingArray).sum();
       for(int i=1;i<countingArray.length;i++)
           stringBuilder.append(String.format("%d %.2f,",i,(float)countingArray[i]/ sum));
        System.out.println(IntStream.range(1,10).mapToObj(i->String.format("%d %.2f,",i,(float)countingArray[i]/ Arrays.stream(countingArray).sum())).collect(Collectors.joining("\n")));
       return stringBuilder.toString();
    }
}

public class BenfordLawTest {
    public static void main(String[] args) {
        BenfordLawExperiment experiment = new BenfordLawExperiment();
        try {
            experiment.readData(new FileInputStream(""));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
