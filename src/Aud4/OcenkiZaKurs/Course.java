package Aud4.OcenkiZaKurs;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Course {
    private List<Student> studentList;

    public Course() {
        studentList = new ArrayList<Student>();
    }

    public void readData(InputStream inputStream) {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        this.studentList = bufferedReader.lines()
                .map(line -> Student.createStudent(line))
                .collect(Collectors.toList());
    }

    public void printSortedData(OutputStream outputStream) {
        PrintWriter printWriter = new PrintWriter(outputStream);
        this.studentList.stream().sorted().forEach(s ->printWriter.println(s));
        printWriter.flush();


    }

    public void printDetailedData(OutputStream outputStream) {
        PrintWriter printWriter = new PrintWriter(outputStream);
        this.studentList.stream().sorted().forEach(s -> printWriter.println(s.printFullInfo()));
        printWriter.flush();
    }

    public void printDistribution(OutputStream outputStream) {
        PrintWriter printWriter = new PrintWriter(outputStream);
        int[] gradeDistribution = new int[6];
        for (Student s : studentList)
            gradeDistribution[s.getGrade() - 'A']++;

        for(int i=0;i<6;i++)
            printWriter.printf("%c -> d\n",i+'A',gradeDistribution[i]);

        printWriter.flush();

    }}
