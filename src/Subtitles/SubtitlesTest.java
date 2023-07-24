package Subtitles;

import java.io.InputStream;
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
        String[] splitter = time.split(" --> ");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss,SSS");
        timeFrom = LocalTime.parse(splitter[0], formatter);
        timeTo=LocalTime.parse(splitter[1],formatter);
    }
    public void shift(int ms)
    {
        timeFrom=timeFrom.plus(ms,ChronoUnit.MILLIS);
        timeTo=timeTo.plus(ms,ChronoUnit.MILLIS);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder=new StringBuilder();
        String[]splitter=timeFrom.toString().split("\\.");
        stringBuilder.append(splitter[0]).append(",").append(splitter[1]);
        splitter=timeTo.toString().split("\\.");
        stringBuilder.append(" --> ");
        stringBuilder.append(splitter[0]).append(",").append(splitter[1]);
        return number+"\n"+stringBuilder.toString()+"\n"+text+"\n";
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
        int number=Integer.parseInt(line);
        String time=scanner.nextLine();
        String text=scanner.nextLine();
        Titles titles=new Titles(number,text,time);
        this.titles.add(titles);
        StringBuilder stringBuilder=new StringBuilder();
        if(!scanner.hasNextLine())
        break;
        while(true)
        {
            line=scanner.nextLine();
            if(line.trim().length()==0)
                break;
            stringBuilder.append(line);
            stringBuilder.append("\n");
        }
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

