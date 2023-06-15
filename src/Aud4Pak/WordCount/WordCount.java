package Aud4Pak.WordCount;

import java.io.*;
import java.util.Scanner;

public class WordCount {

    int lines;
    int words;
    int chars;

    public WordCount(int lines, int words, int chars) {
        this.lines = lines;
        this.words = words;
        this.chars = chars;
    }



    public static void readDataWithScanner(InputStream inputStream) {
        Scanner scanner = new Scanner(inputStream);
        int lines = 0, words = 0, chars = 0;
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            lines++;
            words += line.split("\\s+").length;
            chars += line.length();
        }
        System.out.printf("skener %d %d %d\n", lines, words, chars);
    }

    public static void readDataWithBufferedReader(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        int lines = 0, words = 0, chars = 0;
        while ((line = bufferedReader.readLine()) != null) {
            lines++;
            words += line.split("\\s+").length;
            chars += line.length();
        }
        System.out.printf("buffered %d %d %d\n", lines, words, chars);
    }


    public static void readDataWithBufferedReaderAndMapReduce(InputStream inputStream) {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        Line reduce = bufferedReader.lines().map(l -> new Line(l)).reduce(new Line(0, 0, 0), (left, right) -> left.sum(right));
        System.out.println(reduce);
    }
    public static void readDataWithReaderAndConsumer(InputStream inputStream)
    {
        BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(inputStream));
        LineConsumer lineConsumer=new LineConsumer();
        bufferedReader.lines().forEach(lineConsumer);
        System.out.println(lineConsumer);
    }

    public static void main(String[] args) {
        File file=new File("C:\\Users\\PC\\NaprednoIspit\\src\\Aud4Pak\\WordCount\\Text.txt");
        try {

            readDataWithBufferedReader(new FileInputStream(file));

            readDataWithScanner(new FileInputStream(file));
            System.out.println("reduce");
            readDataWithBufferedReaderAndMapReduce(new FileInputStream(file));
            System.out.println("consumer");
            readDataWithReaderAndConsumer(new FileInputStream(file));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
