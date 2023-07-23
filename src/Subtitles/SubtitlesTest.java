//package Subtitles;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class SubtitlesTest {
    public static void main(String[] args) {
        Subtitles subtitles = new Subtitles();
        int n = subtitles.loadSubtitles(System.in);
        System.out.println("+++++ ORIGINIAL SUBTITLES +++++");
        subtitles.print();
        int shift = n * 37;
        shift = (shift % 2 == 1) ? -shift : shift;
        System.out.println(String.format("SHIFT FOR %d ms", shift));
        subtitles.shift(shift);
        System.out.println("+++++ SHIFTED SUBTITLES +++++");
        subtitles.print();
    }
}

class Titles {
    LocalTime timeFrom;
    LocalTime timeTo;
    String text;
    public int number;

    public Titles(int number, String text, String time) {
        this.text = text;
        this.number = number;
        String[] splitter = text.split("--->");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss,SSS");
        timeFrom = LocalTime.parse(splitter[0], formatter);
        timeTo=LocalTime.parse(splitter[1],formatter);
//        timeFrom.plus(100, ChronoUnit.MILLIS);
    }
    public void shift(int ms)
    {
        timeFrom.plus(ms,ChronoUnit.MILLIS);
        timeTo.plus(ms,ChronoUnit.MILLIS);
    }

    @Override
    public String toString() {
        return number+"\n"+timeFrom+" ---> "+timeTo+"\n"+text+"\n";
    }
}

class Subtitles {
   List<Titles> titles;

    public Subtitles() {
        titles = new ArrayList<>();
    }

    public int loadSubtitles(InputStream inputStream) {
    Scanner scanner=new Scanner(inputStream);
    while (scanner.hasNextLine())
    {
        String line=scanner.nextLine();
        int number=Integer.parseInt(scanner.nextLine());
        String time=scanner.nextLine();
        String text=scanner.nextLine();
        Titles titles=new Titles(number,text,line);
        this.titles.add(titles);
    }
    return  titles.size();
    }

    public void print() {
    for(Titles titles1:titles)
        System.out.println(titles1);
    }

    public void shift(int ms) {
        for(Titles titles1:titles)
            titles1.shift(ms);
    }
}

