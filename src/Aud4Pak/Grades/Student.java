package Aud4Pak.Grades;

public class Student  implements  Comparable<Student>{
    String lastname;
    String firstname;
    int exam1;
    int exam2;
    int exam3;
   char grade;
   double points;

    public Student(String lastname, String firstname, int exam1, int exam2, int exam3) {
        this.lastname = lastname;
        this.firstname = firstname;
        this.exam1 = exam1;
        this.exam2 = exam2;
        this.exam3 = exam3;

    }
    public double finalPoints()
    {
        return exam1*0.25+exam2*0.3+exam3*0.45;
    }
    public void setGrade()
    {
        double points=finalPoints();
        if(points>=90)
            grade='A';
        else if(points>=80)
            grade='B';
        else if (points>=70)
            grade='C';
        else if (points>=60)
            grade='D';
        else if (points>=50)
            grade='E';
        else
            grade='F';
    }
    public static Student createStudent(String line)
    {
        String[] splitter=line.split("\\s+");
        return new Student(splitter[0],splitter[1],Integer.parseInt(splitter[2]),Integer.parseInt(splitter[3]),Integer.parseInt(splitter[4]));
    }


    public String printFullInfo()
    {
        return  String.format("%s %s %d %d %.2d %c",firstname,lastname,exam1,exam1,exam1,finalPoints(),grade);
    }
    @Override
    public String toString() {
        return lastname+" "+" "+firstname+" "+grade;
    }

    @Override
    public int compareTo(Student o) {
        return Double.compare(finalPoints(),o.finalPoints());
    }
}
