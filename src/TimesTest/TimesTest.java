package TimesTest;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.*;
import java.util.stream.Collectors;


class UnsupportedFormatException extends  Exception
{
    public UnsupportedFormatException(String poraka) {
    super(poraka);
    }
}
class InvalidTimeException extends Exception {
    public InvalidTimeException(String msg) {
        super(msg);
    }
}
class Time {
    int hour;
    int minute;

    public Time(int hour, int minute) {
        this.hour = hour;
        this.minute = minute;
    }

    public int getHour() {
        return hour;
    }

    public int getMinute() {
        return minute;
    }
    public Time(String time) throws UnsupportedFormatException, InvalidTimeException {
    String[] splitter=time.split("\\.");
    if(splitter.length==1)
    {
        splitter=time.split(",");
    }
    if(splitter.length==1)
    {
        throw  new UnsupportedFormatException(time);
    }
    checkTime(Integer.parseInt(splitter[0]),Integer.parseInt(splitter[1]),time);
    this.hour=Integer.parseInt(splitter[0]);
    this.minute=Integer.parseInt(splitter[1]);

    }

    private void checkTime(int hour,int minute,String time) throws InvalidTimeException {
        if(hour<0 || hour>23 || minute<0 || minute>59)
            throw  new InvalidTimeException(time);
    }

    @Override
    public String toString() {
        return hour+":"+minute;
    }
}



class TimeTable {


    List<Time> times;

    public TimeTable() {
        times = new ArrayList<>();
    }

    public void readTimes(InputStream is) throws UnsupportedFormatException,InvalidTimeException {
        Scanner scanner=new Scanner(new InputStreamReader(is));
        while(scanner.hasNextLine())
        {
            String  line= scanner.nextLine();
            String[] parts=line.split(" ");
            for(String p:parts)
            {
                Time time=new Time(p);
                times.add(time);
            }
        }

    }
    public void writeTimes(OutputStream outputStream,TimeFormat format)
    {
        if(format.equals(TimeFormat.FORMAT_24))
        {
            times.stream().forEach(time -> time.toString() );
        }
        else
        {

        }

    }


}

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