package TimesTest;//package TimesTest;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class TimesTest {

    public static void main(String[] args) {
        TimeTable timeTable = new TimeTable();
        try {
            timeTable.readTimes(System.in);
        } catch (UnsupportedFormatException e) {
            System.out.println("UnsupportedFormatException: " + e.getMessage());
        } catch (InvalidTimeException e) {
            System.out.println("InvalidTimeException: " + e.getMessage());
        }
        System.out.println("24 HOUR FORMAT");
        timeTable.writeTimes(System.out, TimeFormat.FORMAT_24);
        System.out.println("AM/PM FORMAT");
        timeTable.writeTimes(System.out, TimeFormat.FORMAT_AMPM);
    }

}

enum TimeFormat {
    FORMAT_24, FORMAT_AMPM
}

class Times implements  Comparable<Times> {
    public int hour;
    public int minute;

    public Times(int hour, int minute) {
        this.hour = hour;
        this.minute = minute;
    }

    public String print24() {
        return String.format("%2d:%02d", hour, minute);

    }

    public String printAMPM() {
        int dhour=hour;
        String part="AM";
        if (hour == 0)
            dhour+=12;
        if (hour >= 1 && hour <= 11)
            dhour=hour;
        if (hour == 12) part="PM";
        if (hour >= 13 && hour <= 23)
        {
            dhour-=12;
            part="PM";
        }
        return String.format("%2d:%02d %s", dhour, minute,part);
    }

    @Override
    public int compareTo(Times o) {
        if (hour == o.hour)
            return minute - o.minute;
        else
            return hour - o.hour;
    }
}

class TimeTable {
    List<Times> timesList;

    public TimeTable() {
        timesList = new ArrayList<>();
    }

    public void readTimes(InputStream inputStream) throws InvalidTimeException, UnsupportedFormatException {
        Scanner scanner = new Scanner(inputStream);
        while (scanner.hasNext()) {
            String time = scanner.next();
            Times times;
            times=createTimes(time);
            timesList.add(times);

        }
        scanner.close();
    }

    private Times createTimes(String time) throws UnsupportedFormatException, InvalidTimeException {
        Times times;
        check(time);
        String split="";
        split = getSplit(time, split);
        String[] splitter = time.split(split);
        int hour = Integer.parseInt(splitter[0]);
        int minutes = Integer.parseInt(splitter[1]);
        checkTime(hour, minutes);
        times = new Times(hour, minutes);
        return times;
    }

    private static String getSplit(String time, String split) throws UnsupportedFormatException {
        if(time.contains(":"))
            split =":";
        else if (time.contains("."))
            split ="\\.";
        if(split.equals(""))
            throw new UnsupportedFormatException(time) ;
        return split;
    }

    private static void checkTime(int hour, int minutes) throws InvalidTimeException {
        if (hour < 0 || hour > 23 || minutes < 0 || minutes > 59) throw new InvalidTimeException();
    }

    private void check(String time) throws UnsupportedFormatException {
        if(time.contains(":")||time.contains("."))
            return;
        throw new UnsupportedFormatException(time);
    }

    public void writeTimes(OutputStream outputStream, TimeFormat timeFormat) {
        PrintWriter printWriter = new PrintWriter(outputStream);
        if (TimeFormat.FORMAT_AMPM.equals(timeFormat))
            timesList.stream().sorted().forEach(i->printWriter.println(i.printAMPM()));
        else timesList.stream().sorted().forEach(i->printWriter.println(i.print24()));
        printWriter.flush();
    }
}

class UnsupportedFormatException extends Exception {
    public UnsupportedFormatException(String msg) {
        super(msg);
    }
}

class InvalidTimeException extends Exception {

    public InvalidTimeException() {
        super();
    }
}