import java.util.*;

public class TripleTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int a = scanner.nextInt();
        int b = scanner.nextInt();
        int c = scanner.nextInt();
        Triple<Integer> tInt = new Triple<Integer>(a, b, c);
        System.out.printf("%.2f\n", tInt.max());
        System.out.printf("%.2f\n", tInt.avarage());
        tInt.sort();
        System.out.println(tInt);
        float fa = scanner.nextFloat();
        float fb = scanner.nextFloat();
        float fc = scanner.nextFloat();
        Triple<Float> tFloat = new Triple<Float>(fa, fb, fc);
        System.out.printf("%.2f\n", tFloat.max());
        System.out.printf("%.2f\n", tFloat.avarage());
        tFloat.sort();
        System.out.println(tFloat);
        double da = scanner.nextDouble();
        double db = scanner.nextDouble();
        double dc = scanner.nextDouble();
        Triple<Double> tDouble = new Triple<Double>(da, db, dc);
        System.out.printf("%.2f\n", tDouble.max());
        System.out.printf("%.2f\n", tDouble.avarage());
        tDouble.sort();
        System.out.println(tDouble);
    }
}
class Triple <T extends  Number>
{
    List<T> elements;

    public Triple(T a, T b, T c ) {

        elements=new ArrayList<>();
        elements.add(a);
        elements.add(b);
        elements.add(c);
    }
    public  double max()
    {
        return elements.stream().mapToDouble(Number::doubleValue).max().orElse(0);
    }
    public double avarage()
    {
        return elements.stream().mapToDouble(Number::doubleValue).sum()/elements.size();
    }
    public void sort()
    {
        elements.sort(Comparator.comparing(Number::doubleValue));
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder=new StringBuilder();

        for(T t:elements)
            stringBuilder.append(String.format("%.2f ",t.doubleValue()));
        return  stringBuilder.toString();
    }
}

