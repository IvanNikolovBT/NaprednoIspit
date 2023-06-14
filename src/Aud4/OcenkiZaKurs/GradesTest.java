package Aud4.OcenkiZaKurs;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class GradesTest {

    public static void main(String[] args) {

        Course course = new Course();
        File inputFile = new File("C:\\Users\\PC\\NaprednoIspit\\src\\Aud4\\OcenkiZaKurs\\grades");
        File outputFile = new File("C:\\Users\\PC\\NaprednoIspit\\src\\Aud4\\OcenkiZaKurs\\Output");
        try {
            course.readData(new FileInputStream(inputFile));
            System.out.println("===Printing sorted students to screen===");
            course.printSortedData(System.out);

            System.out.println("===Printing detailed report to file===");
            course.printDetailedData(new FileOutputStream(outputFile));

            System.out.println("===Printing grade distribution to screen===");
            course.printDistribution(System.out);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
