//package CanvasTest;

import java.io.*;
import java.util.*;

public class CanvasTest {

    public static void main(String[] args) {
        Canvas canvas = new Canvas();

        System.out.println("READ SHAPES AND EXCEPTIONS TESTING");
        try {
            canvas.readShapes(System.in);
        } catch (InvalidDimensionException e) {
            System.out.println(e.getMessage());
        }

        System.out.println("BEFORE SCALING");
        canvas.printAllShapes(System.out);
        canvas.scaleShapes("123456", 1.5);
        System.out.println("AFTER SCALING");
        canvas.printAllShapes(System.out);

        System.out.println("PRINT BY USER ID TESTING");
        canvas.printByUserId(System.out);

        System.out.println("PRINT STATISTICS");
        canvas.statistics(System.out);
    }
}

class Canvas {

    Map<String, List<Shape>> shapesByID;
    HashSet<Shape> shapes;

    public Canvas() {
        shapesByID = new HashMap<>();
        shapes = new HashSet<>();
    }

    public void readShapes(InputStream is) throws InvalidDimensionException {
        Scanner scanner = new Scanner(is);
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
//            if(line.equals("2 ...... 11.4539"))
//                break;
            try {
                Shape shape = ShapeFactory.create(line);
                shapesByID.computeIfPresent(shape.id, (k, v) -> {
                    v.add(shape);
                    return v;
                });
                if (!shapesByID.containsKey(shape.id)) {
                    List<Shape> shapes = new ArrayList<>();
                    shapes.add(shape);
                    shapesByID.put(shape.id, shapes);
                }
                shapes.add(shape);
            } catch (InvalidIDException e) {
                System.out.println(e.getMessage());
            }


        }
    }

    public void printAllShapes(OutputStream out) {
        PrintWriter printWriter = new PrintWriter(out);
        shapes.stream().sorted(Comparator.comparing(Shape::getArea)).forEach(printWriter::println);
        printWriter.flush();
    }

    public void scaleShapes(String number, double v) {
        shapesByID.getOrDefault(number, new ArrayList<>() {
        }).forEach(i -> i.scale(v));
    }

    public void printByUserId(OutputStream out) {
        PrintWriter printWriter = new PrintWriter(out);
        Comparator<Map.Entry<String, List<Shape>>> entryComparator = Comparator.comparing(i -> i.getValue().size());
        shapesByID.entrySet().stream().sorted(entryComparator.reversed().thenComparing(i -> i.getValue().stream().mapToDouble(Shape::getArea).sum())).forEach(i -> {
            printWriter.println("Shapes of user: " + i.getKey());
            i.getValue().forEach(printWriter::println);
        });
        printWriter.flush();
    }

    public void statistics(OutputStream out) {
        PrintWriter printWriter = new PrintWriter(out);
        DoubleSummaryStatistics stats=shapes.stream().mapToDouble(Shape::getArea).summaryStatistics();
        printWriter.printf("count: %d\n",stats.getCount());
        printWriter.printf("sum: %.2f\n",stats.getSum());
        printWriter.printf("min: %.2f\n",stats.getMin());
        printWriter.printf("average: %.2f\n",stats.getAverage());
        printWriter.printf("max: %.2f\n",stats.getMax());
        printWriter.flush();
    }
}

enum TYPE {
    CIRLCE, SQUARE, RECTANGLE
}

abstract class Shape {
    TYPE type;
    String id;

    public Shape(TYPE type, String id) {
        this.type = type;
        this.id = id;
    }

    abstract public double getArea();

    abstract public double getPer();

    abstract void scale(double coef);

}

class Circle extends Shape {
    double radius;

    public Circle(String id, double radius) {
        super(TYPE.CIRLCE, id);
        this.radius = radius;
    }

    @Override
    public double getArea() {
        return Math.pow(radius, 2) * Math.PI;
    }

    @Override
    public double getPer() {
        return 2 * radius * Math.PI;
    }

    @Override
    void scale(double coef) {
        radius *= coef;
    }

    @Override
    public String toString() {
        return String.format("Circle -> Radius: %.2f Area: %.2f Perimeter: %.2f", radius, getArea(), getPer());
    }
}

class Square extends Shape {
    double side;

    public Square(String id, double side) {
        super(TYPE.SQUARE, id);
        this.side = side;
    }

    @Override
    public double getArea() {
        return side*side;
    }

    @Override
    public double getPer() {
        return 4 * side;
    }

    @Override
    void scale(double coef) {
        side *= coef;
    }

    @Override
    public String toString() {
        return String.format("Square: -> Side: %.2f Area: %.2f Perimeter: %.2f", side, getArea(), getPer());
    }
}

class Rectangle extends Shape {
    double width;
    double height;

    public Rectangle(String id, double width, double height) {
        super(TYPE.RECTANGLE, id);
        this.width = width;
        this.height = height;
    }

    @Override
    public double getArea() {
        return width * height;
    }

    @Override
    public double getPer() {
        return 2 * (width + height);
    }

    @Override
    void scale(double coef) {
        width *= coef;
        height *= coef;
    }

    @Override
    public String toString() {
        return String.format("Rectangle: -> Sides: %.2f, %.2f Area: %.2f Perimeter: %.2f", width, height, getArea(), getPer());
    }
}


class ShapeFactory {
    public static Shape create(String line) throws InvalidIDException, InvalidDimensionException {

        String[] splitter = line.split("\\s+");
        TYPE type = extractType(splitter[0]);
        String id = splitter[1];
        checkInvalidId(id);
        double width = Double.parseDouble(splitter[2]);
        double height = -1;
        if (splitter.length == 4) height = Double.parseDouble(splitter[3]);
        checkSide(width);
        checkSide(height);
        switch (type) {
            case CIRLCE:
                return new Circle(id, width);
            case SQUARE:
                return new Square(id, width);
            case RECTANGLE:
                return new Rectangle(id, width, height);
        }
        return null;
    }

    private static void checkSide(double width) throws InvalidDimensionException {
        if (width == 0) throw new InvalidDimensionException("Dimension 0 is not allowed!");
    }

    private static void checkInvalidId(String id) throws InvalidIDException {
        if (id.length() != 6) throw new InvalidIDException("ID "+id+" is not valid");
        for (Character c : id.toCharArray()) {
            if (!Character.isLetterOrDigit(c)) throw new InvalidIDException("ID "+id+" is not valid");
        }
    }

    private static TYPE extractType(String s) {
        if (s.equals("1")) return TYPE.CIRLCE;
        else if (s.equals("2")) return TYPE.SQUARE;
        else return TYPE.RECTANGLE;
    }
}

class InvalidIDException extends Exception {
    public InvalidIDException(String msg) {
        super(msg);
    }
}

class InvalidDimensionException extends Exception {
    public InvalidDimensionException() {
        super();
    }

    public InvalidDimensionException(String s) {
        super(s);
    }
}