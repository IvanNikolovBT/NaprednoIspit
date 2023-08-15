package CirclesTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

enum TYPE {
    POINT, CIRCLE
}

enum DIRECTION {
    UP, DOWN, LEFT, RIGHT
}

public class CirclesTest {

    public static void main(String[] args) throws MovableObjectNotFittableException {

        System.out.println("===COLLECTION CONSTRUCTOR AND ADD METHOD TEST===");
        MovablesCollection collection = new MovablesCollection(100, 100);
        Scanner sc = new Scanner(System.in);
        int samples = Integer.parseInt(sc.nextLine());
        for (int i = 0; i < samples; i++) {
            String inputLine = sc.nextLine();
            String[] parts = inputLine.split(" ");

            int x = Integer.parseInt(parts[1]);
            int y = Integer.parseInt(parts[2]);
            int xSpeed = Integer.parseInt(parts[3]);
            int ySpeed = Integer.parseInt(parts[4]);

            if (Integer.parseInt(parts[0]) == 0) { //point
                collection.addMovableObject(new MovablePoint(x, y, xSpeed, ySpeed));
            } else { //circle
                int radius = Integer.parseInt(parts[5]);
                collection.addMovableObject(new MovableCircle(radius, new MovablePoint(x, y, xSpeed, ySpeed)));
            }

        }
        System.out.println(collection.toString());

        System.out.println("MOVE POINTS TO THE LEFT");
        collection.moveObjectsFromTypeWithDirection(TYPE.POINT, DIRECTION.LEFT);
        System.out.println(collection.toString());

        System.out.println("MOVE CIRCLES DOWN");
        collection.moveObjectsFromTypeWithDirection(TYPE.CIRCLE, DIRECTION.DOWN);
        System.out.println(collection.toString());

        System.out.println("CHANGE X_MAX AND Y_MAX");
        MovablesCollection.setxMax(90);
        MovablesCollection.setyMax(90);

        System.out.println("MOVE POINTS TO THE RIGHT");
        collection.moveObjectsFromTypeWithDirection(TYPE.POINT, DIRECTION.RIGHT);
        System.out.println(collection.toString());

        System.out.println("MOVE CIRCLES UP");
        collection.moveObjectsFromTypeWithDirection(TYPE.CIRCLE, DIRECTION.UP);
        System.out.println(collection.toString());


    }


}

interface Movable {
    public void moveUp() throws ObjectCanNotBeMovedException;

    public void moveLeft() throws ObjectCanNotBeMovedException;

    public void moveRight() throws ObjectCanNotBeMovedException;

    public void moveDown() throws ObjectCanNotBeMovedException;

    public int getCurrentXPosition();

    public int getCurrentYPosition();

    public boolean canBeFitted(int xMax, int yMax);

    public String exceptionMessage();

    public TYPE getType();
}

class MovablePoint implements Movable {
    int x;
    int y;
    int xSpeed;

    @Override
    public boolean canBeFitted(int xMax, int yMax) {
        return (x >= 0 && x <= xMax && y >= 0 && y <= yMax);
    }

    @Override
    public String exceptionMessage() {
        return String.format("Movable point with coordinates (%d,%d) can not be fitted into the collection", x, y);
    }

    int ySpeed;

    public MovablePoint(int x, int y, int xSpeed, int ySpeed) {
        this.x = x;
        this.y = y;
        this.xSpeed = xSpeed;
        this.ySpeed = ySpeed;
    }

    @Override
    public void moveUp() throws ObjectCanNotBeMovedException {
        if (y + ySpeed > MovablesCollection.y_Max) throw new ObjectCanNotBeMovedException(x, y);
        y += ySpeed;
    }

    @Override
    public void moveLeft() throws ObjectCanNotBeMovedException {
        if (x - xSpeed < 0) throw new ObjectCanNotBeMovedException(x, y);
        x -= xSpeed;
    }

    @Override
    public void moveRight() throws ObjectCanNotBeMovedException {
        if (x + xSpeed > MovablesCollection.x_Max) throw new ObjectCanNotBeMovedException(x, y);
        x += xSpeed;
    }

    @Override
    public void moveDown() throws ObjectCanNotBeMovedException {
        if (y - ySpeed < 0) throw new ObjectCanNotBeMovedException(x, y);
        y -= ySpeed;
    }

    @Override
    public int getCurrentXPosition() {
        return x;
    }

    @Override
    public int getCurrentYPosition() {
        return y;
    }

    @Override
    public String toString() {
        return String.format("Movable point with coordinates (%d,%d)", getCurrentXPosition(), getCurrentYPosition());
    }

    public TYPE getType() {
        return TYPE.POINT;
    }
}

class MovableCircle implements Movable {
    int radius;
    MovablePoint movablePoint;


    @Override
    public String exceptionMessage() {
        return String.format("Movable circle with center (%d,%d) and radius %d can not be fitted into the collection", movablePoint.x, movablePoint.y, radius);
    }

    public MovableCircle(int radius, MovablePoint movablePoint) {
        this.radius = radius;
        this.movablePoint = movablePoint;
    }

    @Override
    public void moveUp() throws ObjectCanNotBeMovedException {
        movablePoint.moveUp();
    }

    @Override
    public void moveLeft() throws ObjectCanNotBeMovedException {
        movablePoint.moveLeft();
    }

    @Override
    public void moveRight() throws ObjectCanNotBeMovedException {
        movablePoint.moveRight();
    }

    @Override
    public void moveDown() throws ObjectCanNotBeMovedException {
        movablePoint.moveDown();
    }

    @Override
    public int getCurrentXPosition() {
        return movablePoint.x;
    }

    @Override
    public int getCurrentYPosition() {
        return movablePoint.y;
    }

    @Override
    public String toString() {
        return String.format("Movable circle with center coordinates (%d,%d) and radius %d", movablePoint.x, movablePoint.y, radius);
    }

    public TYPE getType() {
        return TYPE.CIRCLE;
    }

    public boolean canBeFitted(int xMax, int yMax) {
        return ((movablePoint.x - radius) >= 0) && ((movablePoint.x + radius) <= xMax) && ((movablePoint.y - radius) >= 0) && ((movablePoint.y + radius) < yMax);
    }
}

class MovablesCollection {
    List<Movable> movableList;
    static int x_Max;
    static int y_Max;

    public MovablesCollection(int X_Max, int Y_Max) {
        movableList = new ArrayList<>();
        x_Max = X_Max;
        y_Max = Y_Max;
    }

    public static void setxMax(int i) {
        x_Max = i;
    }

    public static void setyMax(int i) {
        y_Max = i;
    }

    public void addMovableObject(Movable m) throws MovableObjectNotFittableException {
        int x = m.getCurrentXPosition();
        int y = m.getCurrentYPosition();
        if (!m.canBeFitted(x_Max, y_Max))
            throw new MovableObjectNotFittableException(m.exceptionMessage());
        movableList.add(m);
    }

    public void moveObjectsFromTypeWithDirection(TYPE type, DIRECTION direction)  {
        for (Movable m : movableList) {

            try {
                if (m.getType().equals(type)) {
                    switch (direction) {
                        case UP:
                            m.moveUp();
                            break;
                        case DOWN:
                            m.moveDown();
                            break;
                        case LEFT:
                            m.moveLeft();
                            break;
                        case RIGHT:
                            m.moveRight();
                            break;
                    }
                }
            }
            catch (ObjectCanNotBeMovedException e)
            {
                System.out.println(e.getMessage());
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(String.format("Collection of movable objects with size %d\n", movableList.size()));
        for (Movable m : movableList)
            stringBuilder.append(m);
        return stringBuilder.toString();

    }
}

class MovableObjectNotFittableException extends Exception {
    MovableObjectNotFittableException(String msg) {
        super(msg);
    }

}

class ObjectCanNotBeMovedException extends Exception {


    ObjectCanNotBeMovedException(int x, int y) {
        super(String.format("Point (%d,%d) is out of bounds", x, y));
    }
}
