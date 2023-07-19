//package TextProcessor;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
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

class TextProcessor {
    Map<String, Integer> allWords;
    List<String> rawText;
    List<Map<String, Integer>> documents;

    public TextProcessor() {
        allWords = new TreeMap<>();
        rawText = new ArrayList<>();
        documents = new ArrayList<>();
    }

    public void readText(InputStream is) {
        Scanner scanner = new Scanner(new InputStreamReader(is));
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            line = line.replaceAll("[^A-Za-z\\s+]", "");
            line = line.toLowerCase();
            Map<String, Integer> mapper = new TreeMap<>();
            rawText.add(line);
            String[] splitter = line.split("\\s+");
            for (String s : splitter) {
                allWords.computeIfPresent(s, (k, v) -> v + 1);
                allWords.putIfAbsent(s, 1);
                mapper.computeIfPresent(s, (k, v) -> v + 1);
                mapper.putIfAbsent(s, 1);

            }
            documents.add(mapper);
        }

    }

    public  void printTextsVectors(OutputStream os)
    {
        PrintWriter printWriter=new PrintWriter(os);
        documents.stream().map(Map::values).forEach(printWriter::println);
        printWriter.flush();
    }
    public void printCorpus(OutputStream os,int n,boolean asc)
    {
        PrintWriter printWriter=new PrintWriter(os);
        allWords.entrySet().stream().sorted(asc ? Map.Entry.comparingByValue(Comparator.naturalOrder()):Map.Entry.comparingByValue(Comparator.reverseOrder())).
        limit(n).forEach(l->printWriter.println(String.format("%s : %d",l.getKey(),l.getValue())));
                printWriter.flush();
    }
    public void mostSimilarTexts(OutputStream os)
    {
        PrintWriter printWriter=new PrintWriter(os);
        double max=0;
        int maxJ=0,maxI=0;
        for(int i=0;i< documents.size();i++) {
            for (int j = 0;j < documents.size(); j++)
            {
                if(i!=j)
                {
                    double sim=CosineSimilarityCalculator.cosineSimilarity(documents.get(i).values(),documents.get(j).values());
                    if(sim>max)
                    {
                        max=sim;
                        maxI=i;
                        maxJ=j;
                    }



                }
            }
        }
        printWriter.flush();
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