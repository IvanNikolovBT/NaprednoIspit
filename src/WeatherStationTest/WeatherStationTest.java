//package WeatherStationTest;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class WeatherStationTest {
    public static void main(String[] args) throws ParseException {
        Scanner scanner = new Scanner(System.in);
        DateFormat df = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        int n = scanner.nextInt();
        scanner.nextLine();
        WeatherStation ws = new WeatherStation(n);
        while (true) {
            String line = scanner.nextLine();
            if (line.equals("=====")) {
                break;
            }
            String[] parts = line.split(" ");
            float temp = Float.parseFloat(parts[0]);
            float wind = Float.parseFloat(parts[1]);
            float hum = Float.parseFloat(parts[2]);
            float vis = Float.parseFloat(parts[3]);
            line = scanner.nextLine();
            Date date = df.parse(line);
            ws.addMeasurment(temp, wind, hum, vis, date);
        }
        String line = scanner.nextLine();
        Date from = df.parse(line);
        line = scanner.nextLine();
        Date to = df.parse(line);
        scanner.close();
        System.out.println(ws.total());
        try {
            ws.status(from, to);
        } catch (RuntimeException e) {
            System.out.println(e);
        }
    }
}

class Weather implements Comparable<Weather> {
    double temp;
    double humidity;
    double wind;
    double sight;
    Date time;

    @Override
    public int compareTo(Weather o) {
        long t1 = time.getTime();
        long t2 = o.time.getTime();
        if (Math.abs(t1 - t2) < 150 * 1000)
            return 0;
        return time.compareTo(o.time);
    }

    long Days;

    public Weather(double temp, double wind, double humidity, double sight, Date time) {
        this.temp = temp;
        this.humidity = humidity;
        this.wind = wind;
        this.sight = sight;
        this.time = time;
        Days = getDays();
    }

    public long getDays() {
        this.Days = this.time.getTime() / (24 * 60 * 60 * 1000);
        return this.time.getTime() / (24 * 60 * 60 * 1000);

    }

    public long toMinutes() {
        return time.getTime() / (1000 * 150);
    }

    public double getTemp() {
        return temp;
    }

    public Date getDate() {
        return time;
    }

    @Override
    public String toString() {
        return String.format("%.1f %.1f km/h %.1f%% %.1f km %s", temp,
                wind, humidity, sight, time);
    }
}

class WeatherStation {
    int days;
    Set<Weather> weatherList;

    public WeatherStation(int days) {
        this.days = days;
        weatherList = new TreeSet<>();
    }

    public void addMeasurment(float temperature, float wind, float humidity, float visibility, Date date) {
        Weather weather = new Weather(temperature, wind, humidity, visibility, date);
        Iterator<Weather> iterator= weatherList.iterator();
        while (iterator.hasNext()) {
            Weather w = iterator.next();
            long d = weather.getDate().getTime() - w.getDate().getTime();
            if (d > days * 24 * 60 * 60 * 1000) {
                iterator.remove();
            }
        }
        weatherList.add(weather);
    }

    public int total() {
        return weatherList.size();
    }

    public void status(Date from, Date to) {
        List<Weather> weathers = weatherList.stream().filter(i -> i.time.after(from) && i.time.before(to)).sorted(Comparator.comparing(Weather::getDate)).collect(Collectors.toList());
        if (weathers.isEmpty())
            throw new RuntimeException();
        else
            weathers.forEach(System.out::println);
        System.out.printf("Average temperature: %.2f\n", getAvgTemp());
    }

    double getAvgTemp() {
        return weatherList.stream().mapToDouble(Weather::getTemp).sum() / weatherList.size();
    }
}