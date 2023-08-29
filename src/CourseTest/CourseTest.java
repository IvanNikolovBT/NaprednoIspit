package CourseTest;

//package mk.ukim.finki.midterm;

import java.util.*;
import java.util.stream.Collectors;


public class CourseTest {

    public static void printStudents(List<Student> students) {
        students.forEach(System.out::println);
    }

    public static void printMap(Map<Integer, Integer> map) {
        map.forEach((k, v) -> System.out.printf("%d -> %d%n", k, v));
    }

    public static void main(String[] args) {
        AdvancedProgrammingCourse advancedProgrammingCourse = new AdvancedProgrammingCourse();

        Scanner sc = new Scanner(System.in);

        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            String[] parts = line.split("\\s+");

            String command = parts[0];

            if (command.equals("addStudent")) {
                String id = parts[1];
                String name = parts[2];
                advancedProgrammingCourse.addStudent(new Student(id, name));
            } else if (command.equals("updateStudent")) {
                String idNumber = parts[1];
                String activity = parts[2];
                int points = Integer.parseInt(parts[3]);
                advancedProgrammingCourse.updateStudent(idNumber, activity, points);
            } else if (command.equals("getFirstNStudents")) {
                int n = Integer.parseInt(parts[1]);
                printStudents(advancedProgrammingCourse.getFirstNStudents(n));
            } else if (command.equals("getGradeDistribution")) {
                printMap(advancedProgrammingCourse.getGradeDistribution());
            } else {
                advancedProgrammingCourse.printStatistics();
            }
        }
    }
}

class Student {
    String id;
    String name;
    Map<String, Integer> activites;

    public Student(String id, String name) {
        this.id = id;
        this.name = name;
        activites = new TreeMap<>();
    }

    public void addActivity(String activity, int points) {
        checkPoints(activity, points);
        activites.put(activity, points);
    }

    private void checkPoints(String activity, int points) {
        if (activity.equals("labs")) {
            if (points > 10 || points < 0) throw new RuntimeException();
        } else {
            if (points > 100 || points < 0) throw new RuntimeException();

        }
    }

    public double getValue() {
        return activites.get("midterm1") * 0.45 + activites.get("midterm2") * 0.45 + activites.get("labs");
    }

    public int getGrade() {
        double val = getValue();
        if (90 <= val && val <= 100) {
            return 10;
        } else if (80 <= val) {
            return 9;
        } else if (70 <= val) {
            return 8;
        } else if (60 <= val) {
            return 7;
        } else if (50 <= val) {
            return 6;
        } else return 5;

    }
    public boolean passed()
    {
        return getGrade()>=6;
    }

    @Override
    public String toString() {
        return String.format("ID: %s Name: %s First midterm: %d Second midterm %d Labs: %d Summary points: %.2f Grade: %d",
                id,name,activites.get("midterm1"),activites.get("midterm2"),activites.get("labs"),getValue(),getGrade());
    }
}

class AdvancedProgrammingCourse {
    Map<String, Student> students;

    public AdvancedProgrammingCourse() {
        students = new TreeMap<>();
    }

    public void addStudent(Student s) {
        students.put(s.id, s);
    }

    public void updateStudent(String idNumber, String activity, int points) {
        Student student = students.get(idNumber);
        student.addActivity(activity, points);
    }

    public List<Student> getFirstNStudents(int n) {
        return students.values().stream().sorted(Comparator.comparing(Student::getValue, Comparator.reverseOrder())).limit(n).collect(Collectors.toList());
    }

    public Map<Integer, Integer> getGradeDistribution() {
        Map<Integer,Integer>gradesCount=new HashMap<>();
        for(int i=5;i<=10;i++)
        {
            gradesCount.put(i,0);
        }
        for(Student s:students.values())
            gradesCount.computeIfPresent(s.getGrade(),(k,v)->{v=v+1;return v;});
        return gradesCount;
    }
    public void printStatistics()
    {
        DoubleSummaryStatistics ds=students.values().stream().filter(Student::passed).mapToDouble(Student::getValue).summaryStatistics();
        System.out.printf("Count: %d Min: %.2f Average: %.2f Max: %.2f",ds.getCount(),ds.getMin(),ds.getAverage(),ds.getMax());
    }
}