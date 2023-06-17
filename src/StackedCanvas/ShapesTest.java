package StackedCanvas;

import java.util.*;

enum Color {
    RED, GREEN, BLUE
}

interface Scalable {
    public void scale(float scaleFactor);
}

interface Stackable {
    public float weight();
}

abstract class Shape implements Stackable, Scalable {
    String id;
    Color color;

    public Shape(String id, Color color) {
        this.id = id;
        this.color = color;
    }
}

class Circle extends Shape {
    float radius;

    public Circle(String id, Color color, float radius) {
        super(id, color);
        this.radius = radius;
    }

    @Override
    public void scale(float scaleFactor) {
        radius *= scaleFactor;
    }

    @Override
    public float weight() {
        return (float) (Math.PI * radius * radius);
    }

    @Override
    public String toString() {
        return String.format("C: %-5s%-10s%10.2f\n", id, color, weight());
    }
}

class Rectangle extends Circle {
    float width;

    public Rectangle(String id, Color color, float radius, float width) {
        super(id, color, radius);
        this.width = width;
    }

    @Override
    public void scale(float scaleFactor) {
        radius *= scaleFactor;
        width *= scaleFactor;
    }

    @Override
    public float weight() {
        return (float) (radius * width);
    }

    @Override
    public String toString() {
        return String.format("R: %-5s%-10s%10.2f\n", id, color, weight());
    }
}

class Canvas {

    List<Shape> shapeList;

    public Canvas() {
        shapeList = new ArrayList<Shape>();
    }

    int find(float weight) {
        int i;
        for (i = 0; i < shapeList.size(); i++) {
            if (shapeList.get(i).weight() < weight)
                return i;
        }
        return shapeList.size();
    }

    public void add(String id, Color color, float radius) {
        Circle c = new Circle(id, color, radius);
        int i = find(c.weight());
        shapeList.add(i, c);
    }

    public void add(String id, Color color, float width, float height) {
        Rectangle r = new Rectangle(id, color, width, height);
        int i = find(r.weight());
        shapeList.add(i, r);
    }

    public void scale(String id, float scaleFactor) {
        Shape s = null;
        for (int i = 0; i < shapeList.size(); i++) {
            if(shapeList.get(i).id==id)
            {
                s=shapeList.get(i);
                shapeList.remove(i);
                break;
            }
        }
        s.scale(scaleFactor);
        shapeList.add(find(s.weight()),s);
    }
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Shape shape : shapeList) {
            sb.append(shape);
        }
        return sb.toString();
    }
}

public class ShapesTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Canvas canvas = new Canvas();
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] parts = line.split(" ");
            int type = Integer.parseInt(parts[0]);
            String id = parts[0];
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

