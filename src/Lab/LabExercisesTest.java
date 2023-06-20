package Lab;


import java.util.*;
import java.util.function.IntToDoubleFunction;
import java.util.function.Predicate;
import java.util.stream.Collectors;

class Student {
    String index;
    List<Integer> points;
    String sign;

    public Student(String index, List<Integer> points) {
        this.index = index;
        this.points = points;
    }

    public int getSum() {
        return points.stream().mapToInt(Integer::intValue).sum();
    }

    public double getAverage() {
        return (double) getSum() / 10;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public List<Integer> getPoints() {
        return points;
    }

    public void setPoints(List<Integer> points) {
        this.points = points;
    }

    public String getSign() {
        return sign;
    }

    public String setSign() {
        if (passed())
            return sign = "YES";
        else
            return sign = "NO";
    }

    public boolean passed() {
        return points.stream().filter(this::hasPassed).count() >= 8;
    }

    private boolean hasPassed(int broj) {
        return broj >= 0;
    }

    @Override
    public String toString() {
        return String.format("%s %s %.2f", getIndex(), setSign(), getAverage());
    }

    public int getYearOfStudies() {
        return 20 - Integer.parseInt(getIndex().substring(0, 2));
    }

}

class LabExercises {

    Collection<Student> studentCollection;

    public LabExercises() {
        this.studentCollection = new ArrayList<>();
    }

    public void addStudent(Student student) {
        studentCollection.add(student);
    }

    public void printByAveragePoints(boolean ascending, int n) {
        if (ascending)
            studentCollection.stream().sorted(Comparator.comparing(Student::getSum).thenComparing(Student::getIndex)).limit(n).forEach(System.out::println);
        else
            studentCollection.stream().sorted(Comparator.comparing(Student::getSum).thenComparing(Student::getIndex).reversed()).limit(n).forEach(System.out::println);
    }

    public List<Student> failedStudents() {
        return studentCollection.stream().filter(i -> !i.passed()).sorted(Comparator.comparing(Student::getIndex).thenComparing(Student::getSum)).collect(Collectors.toList());
    }

    public Map<Integer, Double> getStatisticsByYear() {
        // da se ignorirat studentite koi ne dobile potpis
        Map<Integer, Double> sumOfPointsByYear = new HashMap<>();
        Map<Integer, Integer> countByYear = new HashMap<>();
        studentCollection.stream().filter(Student::passed).forEach(s -> {
            sumOfPointsByYear.putIfAbsent(s.getYearOfStudies(), 0.0);
            countByYear.putIfAbsent(s.getYearOfStudies(), 0);

            sumOfPointsByYear.computeIfPresent(s.getYearOfStudies(), (k, v) -> v + s.getSum());
            countByYear.computeIfPresent(s.getYearOfStudies(), (k, v) -> ++v);

        });
        sumOfPointsByYear.keySet().stream().forEach(year-> sumOfPointsByYear.computeIfPresent(year,
                (k,v)-> (v/countByYear.get(year))/10));


        return sumOfPointsByYear;
        //return studentCollection.stream().collect(Collectors.groupingBy(Student::getYearOfStudies,Collectors.averagingDouble(Student::getSum)));
    }
}

public class LabExercisesTest {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        LabExercises labExercises = new LabExercises();
        while (sc.hasNextLine()) {
            String input = sc.nextLine();
            String[] parts = input.split("\\s+");
            String index = parts[0];
            List<Integer> points = Arrays.stream(parts).skip(1)
                    .mapToInt(Integer::parseInt)
                    .boxed()
                    .collect(Collectors.toList());

            labExercises.addStudent(new Student(index, points));
        }

        System.out.println("===printByAveragePoints (ascending)===");
        labExercises.printByAveragePoints(true, 100);
        System.out.println("===printByAveragePoints (descending)===");
        labExercises.printByAveragePoints(false, 100);
        System.out.println("===failed students===");
        labExercises.failedStudents().forEach(System.out::println);
        System.out.println("===statistics by year");
        labExercises.getStatisticsByYear().entrySet().stream()
                .map(entry -> String.format("%d : %.2f", entry.getKey(), entry.getValue()))
                .forEach(System.out::println);

    }
}

