
import java.io.*;
import java.util.*;

class CosineSimilarityCalculator {
    public static double cosineSimilarity(Collection<Integer> c1, Collection<Integer> c2) {
        int[] array1;
        int[] array2;
        array1 = c1.stream().mapToInt(i -> i).toArray();
        array2 = c2.stream().mapToInt(i -> i).toArray();
        double up = 0.0;
        double down1 = 0, down2 = 0;

        for (int i = 0; i < c1.size(); i++) {
            up += (array1[i] * array2[i]);
        }

        for (int i = 0; i < c1.size(); i++) {
            down1 += (array1[i] * array1[i]);
        }

        for (int i = 0; i < c1.size(); i++) {
            down2 += (array2[i] * array2[i]);
        }

        return up / (Math.sqrt(down1) * Math.sqrt(down2));
    }
}

public class TextProcessorTest {

    public static void main(String[] args) {
        TextProcessor textProcessor = new TextProcessor();

        textProcessor.readText(System.in);

        System.out.println("===PRINT VECTORS===");
        textProcessor.printTextsVectors(System.out);

        System.out.println("PRINT FIRST 20 WORDS SORTED ASCENDING BY FREQUENCY ");
        textProcessor.printCorpus(System.out, 20, true);

        System.out.println("PRINT FIRST 20 WORDS SORTED DESCENDING BY FREQUENCY");
        textProcessor.printCorpus(System.out, 20, false);

        System.out.println("===MOST SIMILAR TEXTS===");
        textProcessor.mostSimilarTexts(System.out);
    }
}

class TextProcessor {
    List<String> rawTexts;
    List<Map<String, Integer>> vectorsForTexts;
    Map<String, Integer> allWordsMap;

    public TextProcessor() {
        rawTexts = new ArrayList<>();
        vectorsForTexts = new ArrayList<>();
        allWordsMap = new TreeMap<>();
    }

    public void readText(InputStream in) {
        Scanner scanner = new Scanner(in);
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] splitter = line.split("\\s+");
            Map<String, Integer> wordFrequency = new TreeMap<>();
            for (String s : splitter) {
                String word = exctractWord(s);
                fillMaps(wordFrequency, word);
            }
            vectorsForTexts.add(wordFrequency);
            rawTexts.add(line);
        }

        for(Map.Entry<String ,Integer> entry :allWordsMap.entrySet())
        {
            for(Map<String, Integer> individaul:vectorsForTexts)
                individaul.putIfAbsent(entry.getKey(),0);

        }
        scanner.close();
    }

    private void fillMaps(Map<String, Integer> wordFrequency, String word) {
        wordFrequency.computeIfPresent(word, (k, v) -> {
            v++;
            return v;
        });
        wordFrequency.putIfAbsent(word, 1);
        allWordsMap.computeIfPresent(word, (k, v) -> {
            v++;
            return v;
        });
        allWordsMap.putIfAbsent(word, 1);
    }

    public void printTextsVectors(OutputStream os) {
        PrintWriter printWriter = new PrintWriter(os);
        vectorsForTexts.stream().map(Map::values).forEach(printWriter::println);
        printWriter.flush();
    }

    private String exctractWord(String s) {
        StringBuilder stringBuilder = new StringBuilder();
        for (Character c : s.toCharArray()) {
            if (Character.isLetter(c)) stringBuilder.append(Character.toLowerCase(c));
            else return stringBuilder.toString();
        }
        return stringBuilder.toString();
    }

    public void printCorpus(OutputStream os, int n, boolean ascending) {
        PrintWriter printWriter = new PrintWriter(os);
        allWordsMap.entrySet().stream().sorted(ascending ? Map.Entry.comparingByValue(Comparator.comparing(Integer::intValue)) : Map.Entry.comparingByValue(Comparator.comparing(Integer::intValue, Comparator.reverseOrder()))).limit(n).forEach(i -> printWriter.println(String.format("%s : %d", i.getKey(), i.getValue())));
        printWriter.flush();
    }

    public void mostSimilarTexts(OutputStream os) {
        PrintWriter printWriter = new PrintWriter(os);
        int imax = 0, jmax = 0;
        double max = 0;
        for (int i = 0; i < rawTexts.size(); i++) {
            for (int j = 0; j < rawTexts.size(); j++) {
                if (i != j) {
                    double val = CosineSimilarityCalculator.cosineSimilarity(vectorsForTexts.get(i).values(), vectorsForTexts.get(j).values());
                    if (val > max) {
                        max = val;
                        imax = i;
                        jmax = j;
                    }
                }
            }
        }
        printWriter.println(rawTexts.get(imax));
        printWriter.println(rawTexts.get(jmax));
        printWriter.println(max);
        printWriter.flush();
    }


}