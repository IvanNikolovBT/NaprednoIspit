//package Subtitles;

import java.io.InputStream;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SubtitlesTest {
    public static void main(String[] args) {
        Subtitles subtitles = new Subtitles();
        int n = subtitles.loadSubtitles(System.in);
        System.out.println("+++++ ORIGINIAL SUBTITLES +++++");
        subtitles.print();
        int shift = n * 37;
        shift = (shift % 2 == 1) ? -shift : shift;
        System.out.printf("SHIFT FOR %d ms%n", shift);
        subtitles.shift(shift);
        System.out.println("+++++ SHIFTED SUBTITLES +++++");
        subtitles.print();
    }
}

class Sub {
    int num;
    LocalTime start;
    LocalTime end;
    String text;

    public Sub(int num, String time, String text) {
        this.num = num;
        this.text = text;
        String[] splitter = time.split("-->");

        DateTimeFormatter formatter=DateTimeFormatter.ofPattern("HH:mm:ss,SSS");
        start = LocalTime.parse(splitter[0].trim(),formatter);
        end = LocalTime.parse(splitter[1].trim(),formatter);
    }

    @Override
    public String toString() {

        String st=start.toString();
        String[] splitter=st.split("\\.");
        st=splitter[0]+","+splitter[1];
        splitter=end.toString().split("\\.");
        String et;
        if(splitter.length!=1)
            et=splitter[0]+","+splitter[1];
        else
            et=splitter[0]+",000";
        return num + "\n" + st + " --> " + et+ "\n" + text + "\n";

    }
    public  void shift(int ms)
    {
        start=start.plus(ms, ChronoUnit.MILLIS);
        end=end.plus(ms,ChronoUnit.MILLIS);
    }
}

class Subtitles {

    List<Sub>subs;
    int loadSubtitles(InputStream inputStream) {
        subs=new ArrayList<>();
        Scanner scanner=new Scanner(inputStream);

        while (scanner.hasNextLine() )
        {

            String first=scanner.nextLine();
            if(first.equals("kraj"))
                return  subs.size();
            int num=0;
            if(!first.isEmpty())
                 num=Integer.parseInt(first);
            String time=scanner.nextLine();
            String line="";
            while (scanner.hasNextLine())
            {

                String scan=scanner.nextLine();
                if(scan.equals("kraj"))
                    break;
                if(scan.trim().length()==0)
                    break;
                line+=scan+"\n";
            }
            Sub sub=new Sub(num,time,line);
            subs.add(sub);

        }        scanner.close();
        return subs.size();
    }

    public void print() {
    subs.forEach(i-> System.out.printf("%s",i.toString()));

    }

    public void shift(int ms) {
    subs.forEach(i->i.shift(ms));
    }
}
