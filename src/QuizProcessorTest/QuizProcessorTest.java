package QuizProcessorTest;

import java.io.InputStream;
import java.util.*;

public class QuizProcessorTest {
    public static void main(String[] args) {
        QuizProcessor.processAnswers(System.in).forEach((k, v) -> System.out.printf("%s -> %.2f%n", k, v));
    }
}

class QuizProcessor {
    static Map<String, Double> processAnswers(InputStream is) {

        Scanner scanner = new Scanner(is);
        Map<String, Double> map = new TreeMap<>();
        while (scanner.hasNextLine()) {
            try {
                String line = scanner.nextLine();
                if (line.equals("kraj")) break;
                String[] splitter = line.split(";");
                String[] csplit = splitter[1].split(",");
                String[] asplit = splitter[2].split(",");
                if (csplit.length != asplit.length) throw new Error();
                int c = 0, w = 0;
                for (int i = 0; i < csplit.length; i++) {
                    if (csplit[i].equals(asplit[i])) c++;
                    else w++;
                }
                map.put(splitter[0], c - w * 0.25);
            } catch (Error e) {
                System.out.print("A quiz must have same number of correct and selected answers\n");
            }
        }
        scanner.close();
        return map;
    }
}

class Error extends Exception {
    public Error() {
        super();
    }
}