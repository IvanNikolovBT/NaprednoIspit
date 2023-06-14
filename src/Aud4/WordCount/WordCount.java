package Aud4.WordCount;

import java.io.*;
import java.util.Scanner;

public class WordCount {

    public static void readDataWithScanner(InputStream inputStream) {
        int lines = 0, words = 0, chars = 0;
        Scanner scanner = new Scanner(inputStream);
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            ++lines;
            words += line.split("\\s+").length;
            chars = line.length();
        }
        System.out.printf("Lines %d words %d chars %d", lines, words, chars);
    }

    public static void readDataWithBufferedReader(InputStream inputStream) throws IOException {
        int lines = 0, words = 0, chars = 0;
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            ++lines;
            words += line.split("\\s+").length;
            chars = line.length();
        }
        System.out.printf("Lines %d words %d chars %d", lines, words, chars);

    }

    public static void readDataWithBUfferedReaderANdMapReduce(InputStream inputStream) {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        LineCounter result = bufferedReader.lines().map(l -> new LineCounter(l)).
                reduce(
                        new LineCounter(0, 0, 0),
                        (left, right) -> left.sum(right)
                );

    }

    public static void readDataWithBUfferedReaderANdConsuemr(InputStream inputStream) {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        LineConsumer lineConsumer=new LineConsumer();
        bufferedReader.lines().forEach(lineConsumer);
        System.out.println(lineConsumer);
    }

    public static void main(String[] args) {
        File file = new File("C:\\Users\\PC\\NaprednoIspit\\src\\Aud4\\WordCount\\text");
        try {
            readDataWithScanner(new FileInputStream(file));
            readDataWithBufferedReader(new FileInputStream(file));
            readDataWithBUfferedReaderANdMapReduce(new FileInputStream(file));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
