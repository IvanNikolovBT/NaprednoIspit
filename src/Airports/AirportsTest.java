import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class AirportsTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Airports airports = new Airports();
        int n = scanner.nextInt();
        scanner.nextLine();
        String[] codes = new String[n];
        for (int i = 0; i < n; ++i) {
            String al = scanner.nextLine();
            String[] parts = al.split(";");
            airports.addAirport(parts[0], parts[1], parts[2], Integer.parseInt(parts[3]));
            codes[i] = parts[2];
        }
        int nn = scanner.nextInt();
        scanner.nextLine();
        for (int i = 0; i < nn; ++i) {
            String fl = scanner.nextLine();
            String[] parts = fl.split(";");
            airports.addFlights(parts[0], parts[1], Integer.parseInt(parts[2]), Integer.parseInt(parts[3]));
        }
        int f = scanner.nextInt();
        int t = scanner.nextInt();
        String from = codes[f];
        String to = codes[t];
        System.out.printf("===== FLIGHTS FROM %S =====\n", from);
        airports.showFlightsFromAirport(from);
        System.out.printf("===== DIRECT FLIGHTS FROM %S TO %S =====\n", from, to);
        airports.showDirectFlightsFromTo(from, to);
        t += 5;
        t = t % n;
        to = codes[t];
        System.out.printf("===== DIRECT FLIGHTS TO %S =====\n", to);
        airports.showDirectFlightsTo(to);
    }
}

class Flight {
    String from;
    String to;
    int time;
    int duration;

    public Flight(String from, String to, int time, int duration) {
        this.from = from;
        this.to = to;
        this.time = time;
        this.duration = duration;
    }

    @Override
    public String toString() {
        int end = time + duration;
        int plus = end / (24 * 60);
        end %= (24 * 60);
        return String.format("%s-%s %02d:%02d-%02d:%02d%s %dh%02dm", from, to, time / 60, time % 60, end / 60, end % 60, plus > 0 ? " +1d" : "", duration / 60, duration % 60);
    }

    public String getTo() {
        return to;
    }

    public int getTime() {
        return time;
    }

    public int getDuration() {

        return duration;
    }
}

class Airport {
    String name;
    String country;
    String code;
    int passengers;
    List<Flight> flights;

    public Airport(String name, String country, String code, int passengers) {
        this.name = name;
        this.country = country;
        this.code = code;
        this.passengers = passengers;
        flights = new ArrayList<>();
    }

    public void addFlight(Flight f) {
        flights.add(f);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(name).append(" (" + code + ")").append("\n");
        stringBuilder.append(country).append("\n");
        stringBuilder.append(passengers).append("\n");
        int i = 1;
        List<Flight> flights1 = flights.stream().sorted(Comparator.comparing(Flight::getTo).thenComparing(Flight::getTime)).collect(Collectors.toList());
        for (Flight f : flights1)
            stringBuilder.append(i++).append(". ").append(f).append("\n");

        return stringBuilder.toString();
    }

}

class Airports {
    Map<String, Airport> airports;

    public Airports() {
        airports = new HashMap<>();
    }

    public void addAirport(String name, String country, String code, int passengers) {
        Airport airport = new Airport(name, country, code, passengers);
        airports.put(code, airport);
    }

    public void addFlights(String from, String to, int time, int duration) {
        Flight flight = new Flight(from, to, time, duration);
        Airport airport = airports.get(from);
        airport.addFlight(flight);
    }

    public void showFlightsFromAirport(String code) {
        System.out.printf("%s", airports.get(code));
    }

    public void showDirectFlightsFromTo(String from, String to) {
        Airport airport = airports.get(from);
        List<Flight> flights = airport.flights.stream().filter(i -> i.from.equals(from) && i.to.equals(to)).collect(Collectors.toList());
        if (flights.isEmpty()) System.out.printf("No flights from %s to %s\n", from, to);
        else flights.forEach(System.out::println);
    }

    public void showDirectFlightsTo(String to) {
        List<Flight> flights = new ArrayList<>();

        for (Airport a : airports.values()) {
            for (Flight f : a.flights) {
                if (f.to.equals(to)) flights.add(f);
            }
        }
        if (flights.isEmpty()) System.out.printf("No flights to %s\n", to);
        else
            flights.stream().sorted(Comparator.comparing(Flight::getTo).thenComparing(Flight::getTime).thenComparing(Flight::getDuration)).forEach(System.out::println);
    }
}


