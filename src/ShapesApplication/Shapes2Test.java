package ShapesApplication;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.*;

public class Shapes2Test {

    public static void main(String[] args) {

        ShapesApplication shapesApplication = new ShapesApplication(10000);

        System.out.println("===READING CANVASES AND SHAPES FROM INPUT STREAM===");
        shapesApplication.readCanvases(System.in);

        System.out.println("===PRINTING SORTED CANVASES TO OUTPUT STREAM===");
        shapesApplication.printCanvases(System.out);


    }
}

enum Type {
    S, C
}

interface Shape {

    double getSize();

    Type getType();
}

class Circle implements Shape {
    double side;

    Type type;

    public Circle(double side) {
        this.side = side;
        type = Type.C;
    }


    @Override
    public double getSize() {
        return side*side*Math.PI;
    }

    @Override
    public Type getType() {
        return Type.C;
    }
}

class Square implements Shape {
    double side;

    Type type;

    public Square(double side) {
        this.side = side;

        type = Type.S;
    }


    @Override
    public double getSize() {
        return side*side;
    }

    @Override
    public Type getType() {
        return Type.S;
    }
}

class ShapeFactory {
    public static Shape CREATE(String s, String b) {

        if (s.equals("C"))
            return new Circle(Double.parseDouble(b));
        return new Square(Double.parseDouble(b));
    }
}

class Canvas {
    String id;
    List<Shape> shapes;

    public Canvas(String id) {
        this.id = id;
        shapes=new ArrayList<>();
    }

    public void addShape(Shape s) {
        shapes.add(s);
    }
    public double sum()
    {
        return shapes.stream().mapToDouble(Shape::getSize).sum();
    }
    public int circles()
    {
        return (int) shapes.stream().filter(i->i.getType().equals(Type.C)).count();
    }
    public int squares()
    {
        return (int) shapes.stream().filter(i->i.getType().equals(Type.S)).count();
    }

    @Override
    public String toString() {
        StringBuilder sb=new StringBuilder();
        DoubleSummaryStatistics stats=shapes.stream().mapToDouble(Shape::getSize).summaryStatistics();
        sb.append(String.format("%s %d %d %d %.2f %.2f %.2f",id,shapes.size(),circles(),squares(),stats.getMin(),stats.getMax(),stats.getAverage()));

        return  sb.toString();
    }
}

class CanvasFactory {
    public static Canvas CREATE(String s) throws InvalidCanvasException {
        String[] sp = s.split("\\s+");
        Canvas canvas = new Canvas(sp[0]);
        for (int i = 1; i < sp.length; i += 2) {
            Shape shape=ShapeFactory.CREATE(sp[i],sp[i+1]);
            if(shape.getSize()>ShapesApplication.maxArea)
                throw new InvalidCanvasException(String.format("Canvas %s has a shape with area larger than %.2f", canvas.id,ShapesApplication.maxArea));
            canvas.addShape(shape);
        }

        return canvas;
    }
}

class ShapesApplication {
    static double maxArea;
    List<Canvas> canvases;

    public ShapesApplication(double maxArea) {
        ShapesApplication.maxArea = maxArea;
        canvases=new ArrayList<>();
    }

    public void readCanvases(InputStream inputStream) {
        Scanner scanner = new Scanner(inputStream);
        while (scanner.hasNextLine()) {

            try {
                String line=scanner.nextLine();
                if(line.equals("kraj"))
                    break;
                Canvas canvas=CanvasFactory.CREATE(line);
                canvases.add(canvas);
            } catch (InvalidCanvasException e) {
                System.out.println(e.getMessage());
            }

        }
        scanner.close();
    }
    public void printCanvases(OutputStream os)
    {
        PrintWriter pw=new PrintWriter(os);
        canvases.stream().sorted(Comparator.comparing(Canvas::sum,Comparator.reverseOrder())).forEach(pw::println);
        pw.flush();
    }


}
class InvalidCanvasException extends  Exception
{
    public  InvalidCanvasException(String s)
    {
        super(s);
    }
}