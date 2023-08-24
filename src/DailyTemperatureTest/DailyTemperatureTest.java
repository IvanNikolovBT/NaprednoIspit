import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.*;

/**
 * I partial exam 2016
 */
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

class Day {
    int day;
    List<Double> stats;
    Character type;
    DoubleSummaryStatistics doubleSummaryStatistics;

    public Day(int day, List<Double> stats, Character type) {
        this.day = day;
        this.stats = stats;
        this.type = type;
        doubleSummaryStatistics = stats.stream().mapToDouble(Double::doubleValue).summaryStatistics();
    }

    public int getDay() {
        return day;
    }

    public double min() {
        return doubleSummaryStatistics.getMin();
    }

    public double max() {
        return doubleSummaryStatistics.getMax();
    }

    public double avg() {
        return doubleSummaryStatistics.getAverage();
    }

    public double count() {
        return doubleSummaryStatistics.getCount();
    }

    public String print(Character nt) {
        if (type.equals(nt)) {
            return String.format("%3d: Count: %3d Min: %6.2f%s Max: %6.2f%s Avg: %6.2f%s", day, stats.size(), min(),nt, max(),nt, avg(),nt);
        } else if (nt.equals('C')) {
            DoubleSummaryStatistics toC = stats.stream().mapToDouble(Day::toCelsius).summaryStatistics();
            return String.format("%3d: Count: %3d Min: %6.2f%s Max: %6.2f%s Avg: %6.2f%s", day, stats.size(), toC.getMin(),nt, toC.getMax(),nt, toC.getAverage(),nt);
        } else if (nt.equals('F')) {
            DoubleSummaryStatistics toC = stats.stream().mapToDouble(Day::toFahrenheit).summaryStatistics();
            return String.format("%3d: Count: %3d Min: %6.2f%s Max: %6.2f%s Avg: %6.2f%s", day, stats.size(), toC.getMin(), nt,toC.getMax(),nt, toC.getAverage(),nt);
        }
        return "Ne fajka";
    }

    public static double toCelsius(double fahrenheit) {
        return (fahrenheit - 32) * 5 / 9;
    }

    public static double toFahrenheit(double fahrenheit) {
        return (fahrenheit * 9) / 5 + 32;
    }
}

class DailyTemperatures {
    Map<Integer, Day> statsForDay;

    public DailyTemperatures() {
        statsForDay = new HashMap<>();
    }

    public void readTemperatures(InputStream inputStream) {
        Scanner scanner = new Scanner(inputStream);
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] splitter = line.split("\\s+");
            int day = Integer.parseInt(splitter[0]);
            Character type = getType(splitter[1]);
            List<Double> values = new ArrayList<>();
            for (int i=1;i<splitter.length;i++) {
                Double val = Double.parseDouble(splitter[i].substring(0, splitter[i].length()-1));
                values.add(val);
            }
            Day d = new Day(day, values, type);
            statsForDay.put(day, d);
        }
        scanner.close();
    }

    void writeDailyStats(OutputStream outputStream, char scale) {
        PrintWriter printWriter = new PrintWriter(outputStream);
        statsForDay.values().stream().sorted(Comparator.comparing(Day::getDay)).forEach(i->printWriter.println(i.print(scale)));
        printWriter.flush();

    }

    private Character getType(String s) {
        if (s.contains("C"))
            return 'C';
        else return 'F';
    }
}