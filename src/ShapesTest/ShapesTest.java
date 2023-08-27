package ShapesTest;//package ShapesTest;

import java.util.Scanner;
import java.util.*;

enum Color {
    RED, GREEN, BLUE
}

public class ShapesTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Canvas canvas = new Canvas();
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] parts = line.split(" ");
            int type = Integer.parseInt(parts[0]);
            String id = parts[1];
            if (type == 1) {
                Color color = Color.valueOf(parts[2]);
                float radius = Float.parseFloat(parts[3]);
                canvas.add(id, color, radius);
            } else if (type == 2) {
                Color color = Color.valueOf(parts[2]);
                float width = Float.parseFloat(parts[3]);
                float height = Float.parseFloat(parts[4]);
                canvas.add(id, color, width, height);
            } else if (type == 3) {
                float scaleFactor = Float.parseFloat(parts[2]);
                System.out.println("ORIGNAL:");
                System.out.print(canvas);
                canvas.scale(id, scaleFactor);
                System.out.printf("AFTER SCALING: %s %.2f\n", id, scaleFactor);
                System.out.print(canvas);
            }

        }
    }
}

interface Scalable {
    void scale(float scaleFactor);
}

interface Stackable {
    float weight();
}

abstract class Shape implements Stackable, Scalable,Comparable<Shape> {
    Color color;
    String id;

    public Shape(Color color, String id) {
        this.color = color;
        this.id = id;
    }

}

class Circle extends Shape {
    float radius;

    public Circle(Color color, String id, float radius) {
        super(color, id);
        this.radius = radius;
    }

    @Override
    public void scale(float scaleFactor) {
        radius *= scaleFactor;
    }

    @Override
    public float weight() {
        return (float) (radius * radius * Math.PI);
    }

    @Override
    public String toString() {
        return String.format("C: %-5s%-10s%10.2f", id, color, weight());
    }

    @Override
    public int compareTo(Shape o) {
        return Float.compare(weight(),o.weight());
    }
}

class Rectangle extends Shape {
    float width;
    float height;

    public Rectangle(Color color, String id, float width, float height) {
        super(color, id);
        this.width = width;
        this.height = height;
    }

    @Override
    public void scale(float scaleFactor) {
        width *= scaleFactor;
        height *= scaleFactor;
    }

    @Override
    public float weight() {
        return width * height;
    }

    @Override
    public String toString() {
        return String.format("R: %-5s%-10s%10.2f", id, color, weight());
    }
    @Override
    public int compareTo(Shape o) {
        return Float.compare(weight(),o.weight());
    }
}

class Canvas {

    List<Shape> shapes;

    public Canvas() {
        shapes = new ArrayList<>();
    }

    void add(String id, Color color, float radius) {
        //add circle
        Circle circle = new Circle(color, id, radius);
        shapes.add(circle);
        Collections.sort(shapes,Comparator.reverseOrder());
    }

    void add(String id, Color color, float width, float height) {
        //add rectangle
        Rectangle rectangle = new Rectangle(color, id, width, height);
        shapes.add(rectangle);
        Collections.sort(shapes,Comparator.reverseOrder());
    }

    public void scale(String id, float scaleFactor) {
        Shape shape = findShape(id);
        shape.scale(scaleFactor);
        Collections.sort(shapes,Comparator.reverseOrder());
    }

    public Shape findShape(String id) {
        for (Shape s : shapes) {
            if (s.id.equals(id))
                return s;
        }
        return null;
    }

    @Override
    public String toString() {
        StringBuilder sb=new StringBuilder();
        for(Shape s:shapes)
            sb.append(s).append("\n");

        return sb.toString();
    }
}