package ShapesTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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

abstract class Shape implements Scalable, Stackable {
    String  id;
    Color color;
    public Shape(String id,Color color)
    {
        this.id=id;
        this.color=color;
    }
}

class Circle extends Shape {
    float radius;

    public Circle(String id, Color color, float radius) {
        super(id,color);
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

    public String getId() {
        return id;
    }
}

class Rectangle extends Shape {
    float radius;
    float height;


    public String getId() {
        return id;
    }

    public Rectangle(String id, Color color, float radius, float height) {
        super(id,color);
        this.radius = radius;
        this.height = height;
    }


    @Override
    public void scale(float scaleFactor) {
        radius *= scaleFactor;
        height *= scaleFactor;
    }

    @Override
    public float weight() {
        return (float) (radius * height);
    }

    @Override
    public String toString() {
        return String.format("R: %-5s%-10s%10.2f\n", id, color, weight());
    }

}

class Canvas {


    List<Shape> shapeList;

    public Canvas() {
        shapeList = new ArrayList<>();
    }
    private int findI(float weight) {
        int i = 0;
        for (i = 0; i < shapeList.size(); i++) {
            if (shapeList.get(i).weight() < weight) return i;

        }
        return shapeList.size();
    }


    void add(String id, Color color, float radius) {
        Circle circle = new Circle(id, color, radius);
        float weight = circle.weight();
        int i = findI(weight);
        shapeList.add(i, circle);
    }


    void add(String id, Color color, float width, float height) {
        //pravoagolnik
        Rectangle rectangle = new Rectangle(id, color, width, height);
        float weight = rectangle.weight();
        int i = findI(weight);
        shapeList.add(i, rectangle);
    }
    public void scale(String id,float scaleFactor)
    {
        Shape s=null;
        for(int i=shapeList.size()-1;i>=0;i--)
        {
            if(shapeList.get(i).id.equals(id))
            {
                s=shapeList.get(i);
                shapeList.remove(i);
                break;
            }
        }

        s.scale(scaleFactor);
        int index=findI(s.weight());
        shapeList.add(index,s);
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
