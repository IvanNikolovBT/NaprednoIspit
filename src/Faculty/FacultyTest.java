package Faculty;


import java.util.*;

class OperationNotAllowedException extends Exception {
    public OperationNotAllowedException(String msg) {
        super(msg);
    }
}

class Course {
    int term;
    String courseName;
    int grade;

    public Course(int term, String courseName, int grade) {
        this.term = term;
        this.courseName = courseName;
        this.grade = grade;
    }

    public int getGrade() {
        return grade;
    }
}

class Student {
    String id;
    int yearsOfStudies;
    TreeMap<Integer, List<Course>> grades;
    TreeSet<String> allAttended;
    int passed;

    public Student(String id, int yearsOfStudies) {
        this.id = id;
        this.yearsOfStudies = yearsOfStudies;
        grades = new TreeMap<>();
        passed = 0;
        allAttended = new TreeSet<>();
        fillEmptyTerms();
    }

    private void fillEmptyTerms() {
        int val;
        if (yearsOfStudies == 3)
            val = 6;
        else val = 8;
        for (int i = 1; i <= val; i++) {
            grades.put(i,new ArrayList<>());
        }
    }

    public void addGrade(int term, String courseName, int grade) throws OperationNotAllowedException {
        //kluchot nie semestarot,a value ni e lista od kursoj, mozi maks da ima 3

        checkTerm(term);
        List<Course> courses;
        Course course = new Course(term, courseName, grade);
        if (grades.containsKey(term)) {
            check3(term);
            courses = grades.get(term);
        } else
            courses = new ArrayList<>();
        allAttended.add(courseName);
        courses.add(course);
        grades.put(term, courses);
        passed++;
    }

    public boolean checkGraduated() {
        if (yearsOfStudies == 3 && passed == 18)
            return true;
        return yearsOfStudies == 4 && passed == 24;

    }


    private void checkTerm(int term) throws OperationNotAllowedException {
        if (yearsOfStudies == 3) if (term < 0 || term > 6)
            throw new OperationNotAllowedException(String.format("Term %d is not possible for student with ID %s", term, id));//n
        if (yearsOfStudies == 4) if (term < 0 || term > 8)
            throw new OperationNotAllowedException(String.format("Term %d is not possible for student with ID %s", term, id));//n
    }

    private void check3(int term) throws OperationNotAllowedException {
        if (grades.get(term).size() >= 3)
            throw new OperationNotAllowedException(String.format("Student %s already has 3 grades in term %d", id, term));//n
    }

    public double getAvg() {


        int sum = grades.values().stream().map(i -> i.stream().mapToInt(Course::getGrade).sum()).mapToInt(Integer::intValue).sum();
        if (sum == 0)
            return 5;
        if (yearsOfStudies == 3)
            return (double) sum / passed;
        else
            return (double) sum / passed;

    }

    public String detatiled() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(String.format("Student: %s\n", id));
        for (Map.Entry<Integer, List<Course>> term : grades.entrySet()) {
            stringBuilder.append(String.format("Term %d\n", term.getKey()));
            stringBuilder.append(String.format("Courses: %d\n", term.getValue().size()));
            stringBuilder.append(String.format("Average grade for term: %.2f\n", avgForTerm(term.getKey())));
        }
        endOfReport(stringBuilder);
        return stringBuilder.toString();
    }

    private void endOfReport(StringBuilder stringBuilder) {
        stringBuilder.append(String.format("Average grade: %.2f\n", getAvg()));
        stringBuilder.append("Courses attended: ");
        for (String s : allAttended) {
            stringBuilder.append(s).append(",");
        }
        stringBuilder.deleteCharAt(stringBuilder.length() - 1);
    }

    int numberOfTerms() {
        return grades.size();
    }

    double avgForTerm(int term) {
        double val = 0;
        if(grades.get(term).size()==0)
            return 5;
        for (int i = 0; i < grades.get(term).size(); i++)
            val += grades.get(term).get(i).getGrade();
        return val / grades.get(term).size();
    }

    public String forBestN() {
        return String.format("Student: %s Courses passed: %d Average grade: %.2f", id, passed, getAvg());
    }

    public int getCount() {
        return passed;
    }

    public String getId() {
        return id;
    }
}

class Stat {
    int count;
    int grades;

    String courseName;

    public Stat(String courseName) {
        count = 0;
        grades = 0;
        this.courseName = courseName;
    }

    public void add(int grade) {
        count++;
        grades += grade;
    }

    public double avg() {
        return (double) grades / count;
    }

    public int count() {
        return count;
    }

    public String   getName() {
        return courseName;
    }
}

class Faculty {

    HashMap<String, Student> students;
    HashMap<String, Student> graduated;
    HashMap<String, Stat> stats;
    StringBuilder logBuilder;

    public Faculty() {
        students = new HashMap<>();
        graduated = new HashMap<>();
        logBuilder = new StringBuilder();
        stats = new HashMap<>();
    }

    void addStudent(String id, int yearsOfStudies) {
        students.put(id, new Student(id, yearsOfStudies));
    }

    void addGradeToStudent(String studentId, int term, String courseName, int grade) throws OperationNotAllowedException {
        //treba mapa kade sho kluch e id na studentot,a value e studentot.
        //max mozi 3 predmeti vo semestar, poke ako se iskluchok
        //so istiot da se spravime so nesoodvetnen semestar
        Student student = students.get(studentId);
        student.addGrade(term, courseName, grade);
        if (student.checkGraduated()) {
            graduated.put(studentId, students.get(studentId));
            students.remove(studentId);
            logBuilder.append(String.format("Student with ID %s graduated with average grade %.2f in %d years.\n", studentId, student.getAvg(), student.yearsOfStudies)); //n
        }
        Stat stat;
        if (stats.containsKey(courseName))
            stat = stats.get(courseName);
        else
            stat = new Stat(courseName);
        stat.add(grade);
        stats.put(courseName, stat);
    }

    String getFacultyLogs() {
        return logBuilder.toString();
    }

    String getDetailedReportForStudent(String id) {
        return students.get(id).detatiled();
    }

    void printFirstNStudents(int n) {
        //najdobrite n studenti spored brojot na polozeni predmeti
        //ako se isti spored prosechna ocenka
        //sortirani vo opagjachki redosled
        students.values().stream().sorted(Comparator.comparing(Student::getCount, Comparator.reverseOrder()).thenComparing(Student::getAvg, Comparator.reverseOrder()).thenComparing(Student::getId, Comparator.reverseOrder())).limit(n).forEach(student -> System.out.println(student.forBestN()));
    }

    void printCourses() {
        stats.values().stream().sorted(Comparator.comparing(Stat::count).thenComparing(Stat::avg).thenComparing(Stat::getName)).forEach(stat -> System.out.printf("%s %d %.2f\n", stat.courseName, stat.count, stat.avg()));
    }
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
            System.out.printf(faculty.getFacultyLogs());
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
            if (testCase == 5) faculty.printFirstNStudents(10);
            else if (testCase == 6) faculty.printFirstNStudents(3);
            else faculty.printFirstNStudents(20);
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
                    if (grade == 10) grade = 5;
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
            System.out.printf(faculty.getFacultyLogs());
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



