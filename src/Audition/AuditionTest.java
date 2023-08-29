import java.util.*;

public class AuditionTest {
    public static void main(String[] args) {
        Audition audition = new Audition();
        List<String> cities = new ArrayList<String>();
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] parts = line.split(";");
            if (parts.length > 1) {
                audition.addParticpant(parts[0], parts[1], parts[2], Integer.parseInt(parts[3]));
            } else {
                cities.add(line);
            }
        }
        for (String city : cities) {
            System.out.printf("+++++ %s +++++\n", city);
            audition.listByCity(city);
        }
        scanner.close();
    }
}

class Participant {
    String city;
    String code;
    String name;
    int age;

    public Participant(String city, String code, String name, int age) {
        this.city = city;
        this.code = code;
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    @Override
    public String toString() {
        return String.format("%s %s %d", code, name, age);
    }
}

class Audition {

    Map<String, Set<Participant>> byCity;
    Map<String, Set<String>> codesForCity;

    public Audition() {
        byCity = new TreeMap<>();
        codesForCity = new TreeMap<>();
    }

    public void addParticpant(String city, String code, String name, int age) {
        Participant p = new Participant(city, code, name, age);
        byCity.putIfAbsent(city, new HashSet<>());
        codesForCity.putIfAbsent(city, new TreeSet<>());
        if (!codesForCity.get(city).contains(code)) {
            byCity.computeIfPresent(city, (k, v) -> {
                v.add(p);
                return v;
            });
            codesForCity.computeIfPresent(city, (k, v) -> {
                v.add(code);
                return v;
            });
        }

    }

    public void listByCity(String city) {
        byCity.get(city).stream().sorted(Comparator.comparing(Participant::getName).thenComparing(Participant::getAge)).forEach(System.out::println);
    }


}