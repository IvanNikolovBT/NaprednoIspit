package ShapesApplication;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class Shape{

    String id;
    List<Integer> sides;

    public Shape(String id, List<Integer> sides) {
        this.id = id;
        this.sides = sides;
    }
    public Shape(String line)
    {
        String[] splitter=line.split("\\s+");
        id=splitter[0];
        sides= Stream.of(splitter).skip(1).map(Integer::parseInt).collect(Collectors.toList());

    }
    public int getSum()
    {
        return sides.stream().mapToInt(Integer::intValue).map(i->i*4).sum();
    }
    public int getSize()
    {
        return sides.size();
    }
}
class ShapesApplication {
    List<Shape> shapes;
    int num;

    public ShapesApplication() {
        this.shapes = new ArrayList<>();
        this.num = 0;
    }

    public int readCanvases(InputStream inputstream)
    {
        BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(inputstream));
        shapes= bufferedReader.lines().map(l->new Shape(l)).collect(Collectors.toList());
        return shapes.stream().mapToInt(Shape::getSize).sum();
    }
    void printLargestCanvasTo(OutputStream outputStream)
    {
        List<Shape> s =shapes.stream().sorted(Comparator.comparing(Shape::getSum)).collect(Collectors.toList());
        Shape a=s.get(shapes.size()-1);
        System.out.printf("%s %d %d%n",a.id,a.getSize(),a.getSum());

    }
}

public class Shapes1Test {

    public static void main(String[] args) {
        ShapesApplication shapesApplication = new ShapesApplication();

        System.out.println("===READING SQUARES FROM INPUT STREAM===");
        System.out.println(shapesApplication.readCanvases(System.in));
        System.out.println("===PRINTING LARGEST CANVAS TO OUTPUT STREAM===");
        shapesApplication.printLargestCanvasTo(System.out);

    }
}