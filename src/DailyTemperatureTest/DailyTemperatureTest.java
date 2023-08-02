package DailyTemperatureTest;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.*;

public class DailyTemperatureTest {
    public static void main(String[] args) {
        DailyTemperatures dailyTemperatures = new DailyTemperatures();
        dailyTemperatures.readTemperatures(System.in);
        System.out.println("=== Daily temperatures in Celsius (C) ===");
        dailyTemperatures.writeDailyStats(System.out, 'C');
        System.out.println("=== Daily temperatures in Fahrenheit (F) ===");
        dailyTemperatures.writeDailyStats(System.out, 'F');
    }
}
enum Type
{

}
//class Info
//{
//    List<Integer> integerList;
//    Type type;
//    public Info(Type type)
//    {
//        integerList=new ArrayList<>();
//        this.type=type;
//    }
//
//
//}
interface  Info
{

}
class DailyTemperatures
{
    Map<Integer,Info> map;

    public DailyTemperatures() {
        this.map = new HashMap<>();
    }
     public void readTemperatures(InputStream inputStream)
    {}
    public  void writeDailyStats(OutputStream outputStream, char scale)
    {
        PrintWriter printWriter=new PrintWriter(outputStream);

        printWriter.flush();
    }
}
