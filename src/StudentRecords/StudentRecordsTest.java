package StudentRecords;


import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.*;

public class StudentRecordsTest {
    public static void main(String[] args) {
        System.out.println("=== READING RECORDS ===");
        StudentRecords studentRecords = new StudentRecords();
        int total = studentRecords.readRecords(System.in);
        System.out.printf("Total records: %d\n", total);
        System.out.println("=== WRITING TABLE ===");
        studentRecords.writeTable(System.out);
        System.out.println("=== WRITING DISTRIBUTION ===");
        studentRecords.writeDistribution(System.out);
    }
}

class Student {
    String code;
    String path;
    List<Integer> grades;

    public Student(String code, String path, List<Integer> grades) {
        this.code = code;
        this.path = path;
        this.grades = new ArrayList<>();
    }

    public Student(String s) {
        String[] splitter = s.split("\\s+");
        grades=new ArrayList<>();
        code = splitter[0];
        path = splitter[1];
        for (int i = 2; i < splitter.length; i++) {
            if(!splitter[i].equals("...snip..."))
            grades.add(Integer.parseInt(splitter[i]));
        }
    }

    public double getAvg() {
        return grades.stream().mapToInt(i -> i).average().orElse(0);
    }

    @Override
    public String toString() {
        return String.format("%s %.2f", code, getAvg());
    }

    public String getCode() {
        return code;
    }
}

class Path {
    String path;

    HashMap<Integer, Integer> grades;

    public Path(String path) {
        this.path = path;
        grades = new HashMap<>();

    }

    public void addGrade(int grade) {
        if (grades.containsKey(grade))
            grades.put(grade, grades.get(grade) + 1);
        else
            grades.put(grade, 1);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(path).append("\n");
        for (int i = 6; i <= 10; i++) {
            stringBuilder.append(String.format("%2d | ", i));
            stringBuilder.append(printStars(i));
            stringBuilder.append("(").append(grades.get(i)).append(")");
            if(i!=10)
                stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }

    private String printStars(int i) {
        String a = "";
        int number = grades.get(i);
        int val = (int) Math.ceil((double) grades.get(i) / 10);
        for (int j = 0; j < val; j++) {
            a += "*";
        }
        return a;
    }
    public int get10()
    {
        return grades.get(10);
    }

}

class StudentRecords {
    Map<String, List<Student>> students;
    Map<String, Path> paths;

    public StudentRecords() {
        students = new TreeMap<>();
        paths = new TreeMap<>();
    }

    int readRecords(InputStream inputStream) {
        Scanner scanner = new Scanner(inputStream);
        int i = 0;
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            Student student = new Student(line);
            studentsWork(student);
            pathWork(student);
            i++;
        }
        return i;
    }

    private void studentsWork(Student student) {
        if (!students.containsKey(student.path))
            students.put(student.path, new ArrayList<>());

        students.computeIfPresent(student.path, (k, v) -> {
            v.add(student);
            return v;
        });
    }

    private void pathWork(Student student) {
        Path path;
        if (!paths.containsKey(student.path))
            path = new Path(student.path);
        else
            path = paths.get(student.path);
        for (int i = 0; i < student.grades.size(); i++)
            path.addGrade(student.grades.get(i));
        paths.put(student.path, path);
    }

    public void writeTable(OutputStream outputStream) {
        PrintWriter printWriter = new PrintWriter(outputStream);
        //ovde spored nasoka treba
        //znachi prvo treba imeto na nasokata da se ispechati
        //spored prosekot vo opagjachki red
        //treba mapa kaj sho kluchot e nasokata
        for (Map.Entry<String, List<Student>> stringListEntry : students.entrySet()) {
            printWriter.println(stringListEntry.getKey());
            stringListEntry.getValue().stream().sorted(Comparator.comparing(Student::getAvg).reversed().thenComparing(Student::getCode)).forEach(i -> printWriter.println(i.toString()));
        }
        printWriter.flush();
    }

    public void writeDistribution(OutputStream outputStream) {
        PrintWriter printWriter = new PrintWriter(outputStream);
        //brojot na ocenki po nasoka
        //sortirani spored brojot na desetki(rastechki)
        //mozi da se klaj kako kluch pak nasokata
        //ama da se stavi nova klasa vnatre
        //za da ima dobra print metoda i site podatoci da se postapni
        //lesno
        paths.values().stream().sorted(Comparator.comparing(Path::get10).reversed()).forEach(printWriter::println);
        printWriter.flush();
    }

}

