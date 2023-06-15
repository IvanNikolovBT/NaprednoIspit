package Aud4Pak.Grades;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Course {

    private List<Student> studentList;

    public Course()
    {
        studentList = new ArrayList<Student>();
    }
   public void addStudent(Student st)
   {
       studentList.add(st);
   }
   public void readData(InputStream inputStream)
   {
       BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(inputStream));
       this.studentList=bufferedReader.lines().map(s->Student.createStudent(s)).collect(Collectors.toList());
   }
    public void printSortedData(OutputStream outputStream)
    {
        PrintWriter printWriter=new PrintWriter(outputStream);
        this.studentList.stream().sorted().forEach(s-> System.out.println(s));
        printWriter.flush();
        printWriter.close();

    }

    @Override
    public String toString() {
        return "Course{" +
                "studentList=" + studentList +
                '}';
    }
}
