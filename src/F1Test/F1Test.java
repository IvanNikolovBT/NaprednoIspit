package F1Test;

import java.io.*;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class F1Test {

    public static void main(String[] args) {
        F1Race f1Race = new F1Race();
        f1Race.readResults(System.in);
        f1Race.printSorted(System.out);
    }

}

class Driver{

    String name;
    List<LocalTime> times;
    public Driver(String s) {
        String[]splitter=s.split("\\s+");
        this.name= splitter[0];
        DateTimeFormatter formatter=DateTimeFormatter.ofPattern("m:s:SSS");
        times=new ArrayList<>();
        for(int i=1;i<=3;i++)
        {
            String[]time=splitter[i].split(":");
            LocalTime l1=LocalTime.of(0,Integer.parseInt(time[0]),Integer.parseInt(time[1]),Integer.parseInt(time[2]));
            times.add(l1);
        }
//        LocalTime l1=LocalTime.parse(splitter[1].trim(),formatter);
//        LocalTime l2=LocalTime.parse(splitter[2].trim(),formatter);
//        LocalTime l3=LocalTime.parse(splitter[3].trim(),formatter);
//        times.add(l1);
//        times.add(l2);
//        times.add(l3);


    }

    @Override
    public String toString() {
       return  String.format("%-10s %10s",name,Collections.min(times));
    }
    public LocalTime bestLap()
    {
        return  Collections.min(times);
    }

}
class F1Race {
    List<Driver>drivers;

    public F1Race() {
       drivers=new ArrayList<>();

    }

    public void readResults(InputStream inputStream) {
        BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(inputStream));
        drivers=bufferedReader.lines().map(s->new Driver(s)).collect(Collectors.toList());
    }

    public void printSorted(OutputStream outputStream) {
        PrintWriter printWriter=new PrintWriter(outputStream);
        AtomicInteger z=new AtomicInteger(1);
        drivers.stream().sorted(Comparator.comparing(Driver::bestLap)).forEach(i->printWriter.println(String.format("%d. %s",z.getAndIncrement(), i)));
        printWriter.flush();
    }
}