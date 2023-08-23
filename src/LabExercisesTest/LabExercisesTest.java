package LabExercisesTest;//package LabExercisesTest;

import java.util.*;
import java.util.stream.Collectors;

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

class Student {
    String index;
    List<Integer> points;

    public Student(String index, List<Integer> points) {
        this.index = index;
        this.points = points;
    }

    public double getAvg() {
        return points.stream().mapToInt(Integer::intValue).average().orElse(0);
    }

    public String getIndex() {
        return index;
    }

    public double getSumed() {
        return (double) points.stream().mapToInt(Integer::intValue).sum() / 10;
    }

    @Override
    public String toString() {
        return String.format("%s %s %.2f", index, getPassed(), getSumed());
    }

    public String getPassed() {
        if (passeed())
            return "YES";
        else
            return "NO";
    }

    public boolean passeed() {
        if (points.size() >= 8)
            return true;
        else return false;
    }

    public int extractYear() {
        return 20 - Integer.parseInt(getIndex().substring(0, 2));
    }


}

class LabExercises {
    List<Student> students;

    public LabExercises() {
        students = new ArrayList<Student>();
    }

    public void addStudent(Student student) {
        students.add(student);
    }

    public void printByAveragePoints(boolean asc, int n) {
        if (asc)
            students.stream().sorted(Comparator.comparing(Student::getSumed).thenComparing(Student::getIndex)).limit(n).forEach(System.out::println);
        else
            students.stream().sorted(Comparator.comparing(Student::getSumed, Comparator.reverseOrder()).thenComparing(Student::getIndex, Comparator.reverseOrder())).limit(n).forEach(System.out::println);
    }

    public List<Student> failedStudents() {
        return students.stream().filter(i -> !i.passeed()).sorted(Comparator.comparing(Student::getIndex).thenComparing(Student::getSumed)).collect(Collectors.toList());
    }

    public Map<Integer, Double> getStatisticsByYear() {
        TreeMap<Integer, Double> result = new TreeMap<>();
        TreeMap<Integer, List<Student>> yearToStudents = students.stream().collect(Collectors.groupingBy(Student::extractYear, TreeMap::new, Collectors.toList()));
        for (List<Student> year : yearToStudents.values()) {
            double sum = year.stream().filter(Student::passeed).mapToDouble(Student::getSumed).average().orElse(0);
//            System.out.printf("%d : %.2f\n",year.get(0).extractYear(),sum);
            if (sum != 0)
                result.put(year.get(0).extractYear(), sum);
        }
        return result;
    }
}
