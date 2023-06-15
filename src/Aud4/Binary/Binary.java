//package Aud4.Binary;
//
//import java.io.*;
//import java.util.Random;
//
//public class Binary {
//
//    public static String fileNAME = "C:\\Users\\PC\\NaprednoIspit\\src\\Aud4\\Binary\\text.txt";
//
//    private  void generateFile(int n) {
//        try {
//            ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(fileNAME));
//            Random random = new Random();
//            for (int i = 0; i < n; i++) {
//                int next = random.nextInt(1000);
//                objectOutputStream.writeObject(next);
//            }
//
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    public  double findNumbersAverage() {
//        int total = 0;
//        double sum = 0;
//        try {
//            ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(fileNAME));
//            try {
//                while (true) {
//                    int num = objectInputStream.read();
//                    sum += num;
//                    total++;
//                }
//            } catch (IOException e) {
//                System.out.println("end of file");
//            }
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//        return (double) (sum) / total;
//    }
//
//    public static void main(String[] args) {
//    Binary binary=new Binary();
//    binary.generateFile(10);
//        System.out.println(binary.findNumbersAverage());
//    }
//}

package Aud4.Binary;

import java.io.*;
import java.util.Random;

public class Binary {

    public static String fileNAME = "C:\\Users\\PC\\NaprednoIspit\\src\\Aud4\\Binary\\text.txt";

    private void generateFile(int n) {
        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(fileNAME));
            Random random = new Random();
            for (int i = 0; i < n; i++) {
                int next = random.nextInt(1000);
                objectOutputStream.writeObject(next);
            }
            objectOutputStream.close(); // Close the ObjectOutputStream
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public double findNumbersAverage() {
        int total = 0;
        double sum = 0;
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(fileNAME));
            try {
                while (true) {
                    int num = (int) objectInputStream.readObject();
                    sum += num;
                    total++;
                }
            } catch (EOFException e) {
                System.out.println("End of file");
            }
            objectInputStream.close(); // Close the ObjectInputStream
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return sum / total;
    }

    public static void main(String[] args) {
        Binary binary = new Binary();
        binary.generateFile(10);
        System.out.println(binary.findNumbersAverage());
    }
}

