package Aud4.OcenkiZaKurs;

import Aud3.Date;

public class Student implements  Comparable<Student> {


    private  String lastname;
    private  String name;
    private int exam1;
    private int exam2;
    private int exam3;
    private char grade;
    public Student(String lastname, String name, int exam1, int exam2, int exam3) {
        this.lastname = lastname;
        this.name = name;
        this.exam1 = exam1;
        this.exam2 = exam2;
        this.exam3 = exam3;
        setGrade();
    }
    public static Student createStudent(String line)
    {
        String[] splitter=line.split("\\s+");
        return new Student(splitter[0],splitter[1],Integer.parseInt(splitter[2]),Integer.parseInt(splitter[3]),Integer.parseInt(splitter[4]));
    }
    public double totalPoints()
    {
        return 0.25*exam1+0.3*exam2+0.45*exam3;
    }
    public char getGrade() {
        return grade;
    }

    public void setGrade() {
        double points=totalPoints();
        if(points>=90)
            grade='A';
        else if (points>=80) {
            grade='B';
        }
        else if (points>=70) {
            grade='C';
        }
        else if (points>=60) {
            grade='D';
        }
        else if (points>=50) {
            grade='E';
        }
        else  {
            grade='F';
        }
    }

    @Override
    public String toString() {
        return lastname+" "+" "+name+" "+grade;
    }
    public String printFullInfo()
    {
        return  String.format("%s %s %d %d %.2f %c",name,lastname,exam1,exam1,exam1,totalPoints(),grade);
    }

    @Override
    public int compareTo(Student o) {
        return Double.compare(totalPoints(),o.totalPoints());
    }
}
