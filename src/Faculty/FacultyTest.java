package Faculty;

//package mk.ukim.finki.vtor_kolokvium;


import java.util.*;

class OperationNotAllowedException extends Exception {

    public OperationNotAllowedException(String s) {
        super(s);
    }
}

class Student {
    String id;

    @Override
    public String toString() {
        return String.format("Student: %s Courses passed: %d Average grade: %.2f", id, passed, getAverageGrade());
    }

    int yearOfStudies;
    Map<Integer, List<Course>> grades;
    int passed;

    public Student(String id, int yearOfStudies) {
        this.id = id;
        this.yearOfStudies = yearOfStudies;
        grades = new TreeMap<>();
        passed = 0;
    }

    public void addGrades(int term, String courseName, int grade) throws OperationNotAllowedException {
        if (grades.containsKey(term))
            checkTerm(term);
        Course course = new Course(term, courseName, grade);
        grades.computeIfPresent(term, (k, v) -> {
            v.add(course);
            return v;
        });
        grades.putIfAbsent(term, new ArrayList<>());
        passed++;
    }

    public double getAverageGrade() {
        return grades.values().stream().flatMap(Collection::stream).mapToDouble(Course::getGrade).sum() / passed;
    }

    public boolean checkIfGraduated() {
        return (yearOfStudies == 3 && passed == 18) || (yearOfStudies == 4 && passed == 24);
    }

    private void checkTerm(int term) throws OperationNotAllowedException {
        if (grades.get(term).size() == 3)
            throw new OperationNotAllowedException("Student " + id + " already has 3 grades in term " + term);
        if (yearOfStudies == 3) {
            if (term < 0 || term > 6)
                throw new OperationNotAllowedException("Term" + term + "is not possible for student with ID " + id + ".");
        }
        if (yearOfStudies == 4) {
            if (term < 0 || term > 8)
                throw new OperationNotAllowedException("Term" + term + "is not possible for student with ID " + id + ".");
        }
    }

    public int getPassed() {
        return passed;
    }
}

class Course {
    int term;
    int grade;
    String courseName;

    public Course(int term, String courseName, int grade) {
        this.term = term;
        this.grade = grade;
        this.courseName = courseName;
    }

    public int getGrade() {
        return grade;
    }
}

class CStats {
    List<Integer> grades;

    public CStats() {
        grades = new ArrayList<>();
    }

    public void add(int grade) {
        grades.add(grade);
    }

    public int size() {
        return grades.size();
    }

    public double avg() {
        int sum = grades.stream().mapToInt(Integer::intValue).sum();
        return (double) sum / grades.size();
    }

}

class Faculty {


    Map<String, Student> students;
    Map<String, Student> graduated;
    Map<String, CStats> courseStats;

    public Faculty() {

        students = new TreeMap<>();
        graduated = new TreeMap<>();
        courseStats = new TreeMap<>();
    }

    void addStudent(String id, int yearsOfStudies) {
        students.put(id, new Student(id, yearsOfStudies));
    }

    void addGradeToStudent(String studentId, int term, String courseName, int grade) throws OperationNotAllowedException {
        Student s = students.get(studentId);
        s.addGrades(term, courseName, grade);
        courseStats.computeIfPresent(courseName, (k, v) -> {
            v.add(grade);
            return v;
        });
        courseStats.putIfAbsent(courseName, new CStats());
        if (s.checkIfGraduated())
            whenGraduated(studentId, s);

    }

    private void whenGraduated(String studentId, Student s) {
        System.out.println("Student with ID " + s.id + " graduated with average grade " + s.getAverageGrade() + " in " + s.yearOfStudies + " years.");
        graduated.put(studentId, s);
        students.remove(studentId);
    }

    String getFacultyLogs() {
        return "";
    }

    String getDetailedReportForStudent(String id) {
        return "";
    }

    void printFirstNStudents(int n) {
        students.values().stream().sorted(Comparator.comparing(Student::getPassed).thenComparing(Student::getAverageGrade).reversed()).limit(n).forEach(System.out::println);
    }

    void printCourses() {

       courseStats.entrySet().stream().sorted(
               Comparator.comparing(i->i.getValue().size())
       ).forEach(i-> System.out.printf("%s %s %s%n",i.getKey(),i.getValue().size(),i.getValue().avg()));
    }
//void printCourses() {
//    Comparator<Map.Entry<String, CStats>> comparator = Comparator.comparing(entry -> entry.)
//            .thenComparing(entry -> entry.getKey());
//
//    courseStats.entrySet().stream()
//            .sorted(comparator)
//            .forEach(entry -> System.out.printf("%s %d %f%n", entry.getKey(), entry.getValue().size(), entry.getValue().avg()));
//}


}

public class FacultyTest {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int testCase = sc.nextInt();

        if (testCase == 1) {
            System.out.println("TESTING addStudent AND printFirstNStudents");
            Faculty faculty = new Faculty();
            for (int i = 0; i < 10; i++) {
                faculty.addStudent("student" + i, (i % 2 == 0) ? 3 : 4);
            }
            faculty.printFirstNStudents(10);

        } else if (testCase == 2) {
            System.out.println("TESTING addGrade and exception");
            Faculty faculty = new Faculty();
            faculty.addStudent("123", 3);
            faculty.addStudent("1234", 4);
            try {
                faculty.addGradeToStudent("123", 7, "NP", 10);
            } catch (OperationNotAllowedException e) {
                System.out.println(e.getMessage());
            }
            try {
                faculty.addGradeToStudent("1234", 9, "NP", 8);
            } catch (OperationNotAllowedException e) {
                System.out.println(e.getMessage());
            }
        } else if (testCase == 3) {
            System.out.println("TESTING addGrade and exception");
            Faculty faculty = new Faculty();
            faculty.addStudent("123", 3);
            faculty.addStudent("1234", 4);
            for (int i = 0; i < 4; i++) {
                try {
                    faculty.addGradeToStudent("123", 1, "course" + i, 10);
                } catch (OperationNotAllowedException e) {
                    System.out.println(e.getMessage());
                }
            }
            for (int i = 0; i < 4; i++) {
                try {
                    faculty.addGradeToStudent("1234", 1, "course" + i, 10);
                } catch (OperationNotAllowedException e) {
                    System.out.println(e.getMessage());
                }
            }
        } else if (testCase == 4) {
            System.out.println("Testing addGrade for graduation");
            Faculty faculty = new Faculty();
            faculty.addStudent("123", 3);
            faculty.addStudent("1234", 4);
            int counter = 1;
            for (int i = 1; i <= 6; i++) {
                for (int j = 1; j <= 3; j++) {
                    try {
                        faculty.addGradeToStudent("123", i, "course" + counter, (i % 2 == 0) ? 7 : 8);
                    } catch (OperationNotAllowedException e) {
                        System.out.println(e.getMessage());
                    }
                    ++counter;
                }
            }
            counter = 1;
            for (int i = 1; i <= 8; i++) {
                for (int j = 1; j <= 3; j++) {
                    try {
                        faculty.addGradeToStudent("1234", i, "course" + counter, (j % 2 == 0) ? 7 : 10);
                    } catch (OperationNotAllowedException e) {
                        System.out.println(e.getMessage());
                    }
                    ++counter;
                }
            }
            System.out.println("LOGS");
            System.out.println(faculty.getFacultyLogs());
            System.out.println("PRINT STUDENTS (there shouldn't be anything after this line!");
            faculty.printFirstNStudents(2);
        } else if (testCase == 5 || testCase == 6 || testCase == 7) {
            System.out.println("Testing addGrade and printFirstNStudents (not graduated student)");
            Faculty faculty = new Faculty();
            for (int i = 1; i <= 10; i++) {
                faculty.addStudent("student" + i, ((i % 2) == 1 ? 3 : 4));
                int courseCounter = 1;
                for (int j = 1; j < ((i % 2 == 1) ? 6 : 8); j++) {
                    for (int k = 1; k <= ((j % 2 == 1) ? 3 : 2); k++) {
                        try {
                            faculty.addGradeToStudent("student" + i, j, ("course" + courseCounter), i % 5 + 6);
                        } catch (OperationNotAllowedException e) {
                            System.out.println(e.getMessage());
                        }
                        ++courseCounter;
                    }
                }
            }
            if (testCase == 5)
                faculty.printFirstNStudents(10);
            else if (testCase == 6)
                faculty.printFirstNStudents(3);
            else
                faculty.printFirstNStudents(20);
        } else if (testCase == 8 || testCase == 9) {
            System.out.println("TESTING DETAILED REPORT");
            Faculty faculty = new Faculty();
            faculty.addStudent("student1", ((testCase == 8) ? 3 : 4));
            int grade = 6;
            int counterCounter = 1;
            for (int i = 1; i < ((testCase == 8) ? 6 : 8); i++) {
                for (int j = 1; j < 3; j++) {
                    try {
                        faculty.addGradeToStudent("student1", i, "course" + counterCounter, grade);
                    } catch (OperationNotAllowedException e) {
                        e.printStackTrace();
                    }
                    grade++;
                    if (grade == 10)
                        grade = 5;
                    ++counterCounter;
                }
            }
            System.out.println(faculty.getDetailedReportForStudent("student1"));
        } else if (testCase == 10) {
            System.out.println("TESTING PRINT COURSES");
            Faculty faculty = new Faculty();
            for (int i = 1; i <= 10; i++) {
                faculty.addStudent("student" + i, ((i % 2) == 1 ? 3 : 4));
                int courseCounter = 1;
                for (int j = 1; j < ((i % 2 == 1) ? 6 : 8); j++) {
                    for (int k = 1; k <= ((j % 2 == 1) ? 3 : 2); k++) {
                        int grade = sc.nextInt();
                        try {
                            faculty.addGradeToStudent("student" + i, j, ("course" + courseCounter), grade);
                        } catch (OperationNotAllowedException e) {
                            System.out.println(e.getMessage());
                        }
                        ++courseCounter;
                    }
                }
            }
            faculty.printCourses();
        } else if (testCase == 11) {
            System.out.println("INTEGRATION TEST");
            Faculty faculty = new Faculty();
            for (int i = 1; i <= 10; i++) {
                faculty.addStudent("student" + i, ((i % 2) == 1 ? 3 : 4));
                int courseCounter = 1;
                for (int j = 1; j <= ((i % 2 == 1) ? 6 : 8); j++) {
                    for (int k = 1; k <= ((j % 2 == 1) ? 2 : 3); k++) {
                        int grade = sc.nextInt();
                        try {
                            faculty.addGradeToStudent("student" + i, j, ("course" + courseCounter), grade);
                        } catch (OperationNotAllowedException e) {
                            System.out.println(e.getMessage());
                        }
                        ++courseCounter;
                    }
                }

            }

            for (int i = 11; i < 15; i++) {
                faculty.addStudent("student" + i, ((i % 2) == 1 ? 3 : 4));
                int courseCounter = 1;
                for (int j = 1; j <= ((i % 2 == 1) ? 6 : 8); j++) {
                    for (int k = 1; k <= 3; k++) {
                        int grade = sc.nextInt();
                        try {
                            faculty.addGradeToStudent("student" + i, j, ("course" + courseCounter), grade);
                        } catch (OperationNotAllowedException e) {
                            System.out.println(e.getMessage());
                        }
                        ++courseCounter;
                    }
                }
            }
            System.out.println("LOGS");
            System.out.println(faculty.getFacultyLogs());
            System.out.println("DETAILED REPORT FOR STUDENT");
            System.out.println(faculty.getDetailedReportForStudent("student2"));
            try {
                System.out.println(faculty.getDetailedReportForStudent("student11"));
                System.out.println("The graduated students should be deleted!!!");
            } catch (NullPointerException e) {
                System.out.println("The graduated students are really deleted");
            }
            System.out.println("FIRST N STUDENTS");
            faculty.printFirstNStudents(10);
            System.out.println("COURSES");
            faculty.printCourses();
        }
    }
}

