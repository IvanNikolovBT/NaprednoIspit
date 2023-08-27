import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


public class WordVectorsTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        scanner.nextLine();
        String[] words = new String[n];
        List<List<Integer>> vectors = new ArrayList<>(n);
        for (int i = 0; i < n; ++i) {
            String line = scanner.nextLine();
            String[] parts = line.split("\\s+");
            words[i] = parts[0];
            List<Integer> vector = Arrays.stream(parts[1].split(":"))
                    .map(Integer::parseInt)
                    .collect(Collectors.toList());
            vectors.add(vector);
        }
        n = scanner.nextInt();
        scanner.nextLine();
        List<String> wordsList = new ArrayList<>(n);
        for (int i = 0; i < n; ++i) {
            wordsList.add(scanner.nextLine());
        }
        WordVectors wordVectors = new WordVectors(words, vectors);
        wordVectors.readWords(wordsList);
        n = scanner.nextInt();
        List<Integer> result = wordVectors.slidingWindow(n);
        System.out.println(result.stream()
                .map(Object::toString)
                .collect(Collectors.joining(",")));
        scanner.close();
    }
}
class WordVectors
{
    Map<String,Vector> vectorMap;
    List<Vector>vectorsList;
    public WordVectors(String[] words, List<List<Integer>> vectors)
    {
        vectorMap=new HashMap<>();
        for(int i=0;i<words.length;i++)
            vectorMap.put(words[i],new Vector(vectors.get(i)));

    }

    public void readWords(List<String> words)
    {
        vectorsList=words.stream().map(i->vectorMap.getOrDefault(i,Vector.DEFAULT)).collect(Collectors.toList());
    }
    public List<Integer> slidingWindow(int n)
    {
        return IntStream.range(0, vectorsList.size()-n+1).mapToObj(i->vectorsList.subList(i,i+n).stream().reduce(Vector.SUM,Vector::sum)).map(Vector::max).collect(Collectors.toList());
    }
}
class Vector
{
    public static final Vector DEFAULT = new Vector(Arrays.asList(5,5,5,5,5));
    public static final Vector SUM = new Vector(Arrays.asList(0,0,0,0,0));
    List<Integer> values;

    public Vector(List<Integer> values) {
        this.values = values;
    }

    public Vector sum(Vector vector) {
        List<Integer> temp=new ArrayList<>();
        for(int i=0;i<5;i++)
            temp.add(values.get(i)+vector.values.get(i));
        return new Vector(temp);

    }
    public int max()
    {
        return Collections.max(values);
    }

}



