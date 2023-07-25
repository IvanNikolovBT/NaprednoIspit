package StudentRecords;////package StudentRecords;
//
//import java.io.*;
//import java.util.*;
//import java.util.stream.IntStream;
//
//public class StudentRecordsTest {
//    public static void main(String[] args) {
//        System.out.println("=== READING RECORDS ===");
//        StudentRecords studentRecords = new StudentRecords();
//        int total = studentRecords.readRecords(System.in);
//        System.out.printf("Total records: %d\n", total);
//        System.out.println("=== WRITING TABLE ===");
//        studentRecords.writeTable(System.out);
//        System.out.println("=== WRITING DISTRIBUTION ===");
//        studentRecords.writeDistribution(System.out);
//    }
//}
//
//class Student {
//    List<Integer> grades;
//    double avg;
//
//    String id;
//    public Student(String id) {
//        this.grades = new ArrayList<>();
//        fill0();
//        this.avg = 0;
//        this.id=id;
//    }
//
//    private void fill0() {
//        for(int i=0;i<5;i++)
//            grades.add(0);
//    }
//
//    public void add(int integer) {
//        grades.set(10-integer,grades.get(10-integer)+1);
////        System.out.println(grades.get(10-integer).toString());
//    }
//
//    public void calculateAvg() {
//
//        int sum = IntStream.range(0,5).map(i->grades.get(i)*convert(i)).sum();
//        int c= IntStream.range(0,5).map(i->grades.get(i)).sum();
//                avg = 1. * sum / c;
//    }
//    int convert(int i)
//    {
//       switch (i)
//       {
//           case 0:
//               return 10;
//           case 1:
//               return 9;
//           case 2:
//               return 8;
//           case 3:
//               return 7;
//           default:
//               return 6;
//       }
//    }
//
//    public double getAvg() {
//        return avg;
//    }
//
//    @Override
//    public String toString() {
//        return String.format("%s %.2f",id,getAvg());
//    }
//
//    public  String getId()
//    {
//        return  id;
//    }
//}
//
//class StudentRecords {
//
//    Map<String,List<Student>> studentsByCategory;
//    public StudentRecords()
//    {
//        studentsByCategory=new TreeMap<>();
//    }
//    public int readRecords(InputStream inputStream) {
//        Scanner scanner=new Scanner(inputStream);
//        while (scanner.hasNextLine())
//        {
//            String line=scanner.nextLine();
//            String[]splitter=line.split("\\s+");
//            String id=splitter[0];
//            String category=splitter[1];
//            int n=splitter.length-2;
//            Student student=new Student(id);
//            for(int i=0;i<n;i++)
//            {
//                if(!splitter[i+2].equals("...snip..."))
//                student.add(Integer.parseInt(splitter[i+2]));
//            }
//            student.calculateAvg();
//            studentsByCategory.putIfAbsent(category,new ArrayList<>());
//            studentsByCategory.computeIfPresent(category,(k,v)->{v.add(student);
//            return v;});
//
//        }
//        return getRecordsLen();
//    }
//    public int getRecordsLen()
//    {
//        return  studentsByCategory.values().stream().mapToInt(i->i.size()).sum();
//    }
//    public void writeTable(OutputStream outputStream) {
//        //spored nasoka gi pechati grupirani
//        //prven se pechati imeto na nasokata
//        //site zapisi za studentite od taa nasoka
//        //sortirani spored prosek vo opagacki redosled
//        //prosekot e decimanel broj
//        PrintWriter printWriter=new PrintWriter(outputStream);
//        for(int i=0;i<studentsByCategory.size();i++)
//        {
//            Set<String> keys=studentsByCategory.keySet();
//             String []kluc= (String[]) keys.toArray();
//            printWriter.println(kluc[i]);
//            for()
//        }
//        studentsByCategory.values().stream().forEach(i->i.stream().sorted(Comparator.comparing(Student::getAvg,Comparator.reverseOrder()).thenComparing(Student::getId)).forEach(a->printWriter.println(a.toString())));
//        printWriter.flush();
//
//
//    }
//
//    public void writeDistribution(OutputStream ot) {
//
//    }
//}

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.*;

/**
 * January 2016 Exam problem 1
 */
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

class StudentRecords {

    Map<String, List<Student>> students;
    Map<String, Stat > distirbution;

    public StudentRecords() {
        students = new TreeMap<>();
        distirbution = new TreeMap<>();
    }

    public void writeTable(OutputStream outputStream) {
        PrintWriter printWriter = new PrintWriter(outputStream);
        for (String key : students.keySet()) {
            printWriter.println(key);
            //mozi ovde proveri
            for (Student student : students.get(key))
                printWriter.println(student);
        }
        printWriter.flush();
    }

    public void writeDistribution(OutputStream outputStream) {
        PrintWriter printWriter = new PrintWriter(outputStream);

        for (int i = 6; i < 11; i++)
        {
            printWriter.print(String.format("%2d |"));

        }
        printWriter.flush();

    }

    private class Stat {
        TreeMap<Integer,Integer>num;


        public Stat() {
           num=new TreeMap<>();

        }
        public void add(int n)
        {
            num.computeIfPresent(n,(k,v)->v+1);
            num.putIfAbsent(n,1);
        }


    }

    public int readRecords(InputStream in) {
        Scanner scanner = new Scanner(in);
        int total = 0;
        while (scanner.hasNextLine()) {
            total++;
            String line = scanner.nextLine();
            String[] splitter = line.split("\\s+");
            String id = splitter[0];
            String category = splitter[1];
            int n = splitter.length - 2;
            Student student = new Student(id);
            for (int i = 0; i < n; i++) {
                int index = i + 2;
                if (!splitter[index].equals("...snip...")) {
                    int value = Integer.parseInt(splitter[index]);
                    student.addGrade(value);
                    distirbution.putIfAbsent(category, new Stat());
                    distirbution.computeIfPresent(category,(k,v)-> {v.add(value);
                    return v;});
                }
            }
            students.putIfAbsent(category, new ArrayList<>());
            students.computeIfPresent(category, (k, v) -> {
                v.add(student);
                return v;
            });
        }
        return total;
    }
}

class Student implements Comparable<Student> {
    String id;
    int sum;
    List<Integer> grades;

    public Student(String id) {
        this.id = id;
        sum = 0;
        grades = new ArrayList<>();
    }

    public void addGrade(int a) {
        sum += a;
        grades.add(a);
    }

    public String getId() {
        return id;
    }

    public int getSum() {
        return sum;
    }

    public double getAvg() {
        return (double) sum / grades.size();
    }

    @Override
    public String toString() {
        return String.format("%s %.2f", id, getAvg());
    }

    @Override
    public int compareTo(Student o) {
        int avg = Double.compare(getAvg(), o.getAvg());
        if (avg == 0)
            return id.compareTo(o.id);
        return -avg;
    }
}