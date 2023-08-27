package TimesTest;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.*;

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
class UnsupportedFormatException extends Exception
{
    public UnsupportedFormatException(String msg) {
        super(msg);
    }
}
class InvalidTimeException extends  Exception
{
    public InvalidTimeException(String msg) {
        super(msg);
    }
}

class Time
{
    int hour;
    int minute;

    public Time(int hour, int minute) {
        this.hour = hour;
        this.minute = minute;
    }
    public Time(String time) throws UnsupportedFormatException, InvalidTimeException {
        String[]splitter=time.split("\\.");
        if(splitter.length==1)
            splitter=time.split(":");
        if(splitter.length==1)
            throw  new UnsupportedFormatException(time);
        this.hour=Integer.parseInt(splitter[0]);
        this.minute=Integer.parseInt(splitter[1]);
        if(hour<0 || hour >23 || minute<0 || minute>59)
            throw  new InvalidTimeException(time);
    }

    public int getHour() {
        return hour;
    }

    public int getMinute() {
        return minute;
    }

    @Override
    public String toString() {
        return String.format("%2d:%02d",hour,minute);
    }
    public String  toStringAMPM()
    {
        int h=hour;
        String part="AM";
        if(h==0)
        {
            h+=12;
        }
        else if(h==12)
        {
            part="PM";
        }
        else if(h>12)
        {
            h-=12;
            part="PM";
        }
        return  String.format("%2d:%02d %s",h,minute,part);
    }
}
class TimeTable
{
    List<Time> timeList;

    public TimeTable() {
        this.timeList = new ArrayList<>();
    }

    public void readTimes(InputStream in) throws InvalidTimeException, UnsupportedFormatException {
        Scanner scanner=new Scanner(new InputStreamReader(in));
        while(scanner.hasNextLine())
        {
            String line=scanner.nextLine();
            String parts[]=line.split("\\s+");
            for(String s:parts){
                Time time=new Time(s);
                timeList.add(time);
            }

        }
    }

    public void writeTimes(PrintStream out, TimeFormat format24) {
        PrintWriter printWriter=new PrintWriter(out);
        if(format24.equals(TimeFormat.FORMAT_AMPM))
        {
            timeList.stream().sorted(Comparator.comparing(Time::getHour).thenComparing(Time::getMinute)).forEach(time -> printWriter.println(time.toStringAMPM()));
        }
        else
        {
            timeList.stream().sorted(Comparator.comparing(Time::getHour).thenComparing(Time::getMinute)).forEach(time -> printWriter.println(time.toString()));

        }

        printWriter.flush();
    }
}