package CanvasTest;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

class InvalidDimensionException extends Exception {
    public InvalidDimensionException() {
        super("Dimension 0 is not allowed!");
    }
}

class InvalidIDException extends Exception {

    public InvalidIDException(String id) {
        super(String.format("ID %s is not valid", id));
    }
}

interface IShape {
    double getArea();

    double getPerimetar();

    void scale(double coef);
}

class Circle implements IShape {
    double radius;

    public Circle(double radius) {
        this.radius = radius;
    }

    @Override
    public double getArea() {
        return Math.pow(radius, 2) * Math.PI;
    }

    @Override
    public double getPerimetar() {
        return radius * 2 * Math.PI;
    }

    @Override
    public void scale(double coef) {
        radius *= coef;
    }

    @Override
    public String toString() {
        return String.format("Circle -> Radius: %.2f Area: %.2f Perimeter: %.2f", radius, getArea(), getPerimetar());
    }
}

class Square implements IShape {
    double a;

    public Square(double a) {
        this.a = a;
    }

    @Override
    public double getArea() {
        return Math.pow(a, 2);
    }

    @Override
    public double getPerimetar() {
        return 4 * a;
    }

    @Override
    public void scale(double coef) {
        a *= coef;
    }

    @Override
    public String toString() {
        return String.format("Square: -> Side: %.2f Area: %.2f Perimeter: %.2f", a, getArea(), getPerimetar());
    }

}

class Rectangle extends Square {
    double b;

    public Rectangle(double a, double b) {
        super(a);
        this.b = b;
    }

    @Override
    public double getArea() {
        return a * b;
    }

    @Override
    public double getPerimetar() {
        return 2 * (a + b);
    }

    @Override
    public void scale(double coef) {
        a *= coef;
        b *= coef;
    }

    @Override
    public String toString() {
        return String.format("Rectangle: -> Sides: %.2f, %.2f Area: %.2f Perimeter: %.2f", a, b, getArea(), getPerimetar());

    }
}

class ShapeFactory {
    private static boolean checkID(String id) {


        if (id.length() != 6) return false;
        for (Character c : id.toCharArray()) {
            if (!Character.isLetterOrDigit(c)) return false;
        }
        return true;
    }

    public static String extractID(String line) throws InvalidIDException {
        String[] splitter = line.split("\\s+");
        String id = splitter[1];
        if (checkID(id)) return id;
        throw new InvalidIDException(id);
    }

    public static IShape createShape(String line) throws InvalidDimensionException {
        String[] splitter = line.split("\\s+");
        int type = Integer.parseInt(splitter[0]);
        double a = Double.parseDouble(splitter[2]);
        if (a <= 0) throw new InvalidDimensionException();
        if (type == 1) {
            return new Circle(a);

        } else if (type == 2) {
            return new Square(a);
        } else {
            double b = Double.parseDouble(splitter[3]);
            if (b <= 0) throw new InvalidDimensionException();
            return new Rectangle(a, b);
        }
    }


}

class Canvas {
    Set<IShape> allShapes;
    Map<String, Set<IShape>> shapesByUser;

    public Canvas() {
        allShapes = new TreeSet<>(Comparator.comparing(IShape::getArea));
        shapesByUser = new TreeMap<>();
    }

    public void readShapes(InputStream is) throws InvalidDimensionException {
        Scanner scanner = new Scanner(is);
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (line.equals("END")) break;
            try {
                String id = ShapeFactory.extractID(line);
                IShape newShape = ShapeFactory.createShape(line);
                allShapes.add(newShape);
                shapesByUser.putIfAbsent(id, new TreeSet<>(Comparator.comparing(IShape::getPerimetar)));
                shapesByUser.get(id).add(newShape);
            } catch (InvalidIDException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public void scaleShapes(String userID, double coef) {
        shapesByUser.getOrDefault(userID, new HashSet<>()).forEach(iShape -> iShape.scale(coef));
    }

    public void printAllShapes(OutputStream os) {
        PrintWriter printWriter = new PrintWriter(os);
        allShapes.forEach(printWriter::println);
        printWriter.flush();
    }

    public void printByUserId(OutputStream os) {
        PrintWriter pw = new PrintWriter(os);

        Comparator<Map.Entry<String, Set<IShape>>> entryComparator = Comparator.comparing(entry -> entry.getValue().size());

        shapesByUser.entrySet().stream().sorted(entryComparator.reversed().thenComparing(entry -> entry.getValue().stream().mapToDouble(IShape::getArea).sum())).forEach(entry -> {
            pw.println("Shapes of user: " + entry.getKey());
            entry.getValue().forEach(pw::println);
        });

        pw.flush();
    }

    public void statistics(OutputStream os) {
        PrintWriter pw = new PrintWriter(os);
        DoubleSummaryStatistics dss = allShapes.stream().mapToDouble(IShape::getArea).summaryStatistics();
        pw.println(String.format("count: %d\nsum: %.2f\nmin: %.2f\naverage: %.2f\nmax: %.2f", dss.getCount(), dss.getSum(), dss.getMin(), dss.getAverage(), dss.getMax()));
        pw.flush();
    }

}


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