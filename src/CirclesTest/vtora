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

    public static void main(String[] args) {

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
            try {
                if (Integer.parseInt(parts[0]) == 0) { //point
                    collection.addMovableObject(new MovablePoint(x, y, xSpeed, ySpeed));
                } else { //circle
                    int radius = Integer.parseInt(parts[5]);
                    collection.addMovableObject(new MovableCircle(radius, new MovablePoint(x, y, xSpeed, ySpeed)));
                }
            } catch (MovableObjectNotFittableException e) {
                System.out.println(e.getMessage());
            }

        }
        System.out.println(collection);

        System.out.println("MOVE POINTS TO THE LEFT");
        collection.moveObjectsFromTypeWithDirection(TYPE.POINT, DIRECTION.LEFT);
        System.out.println(collection);

        System.out.println("MOVE CIRCLES DOWN");
        collection.moveObjectsFromTypeWithDirection(TYPE.CIRCLE, DIRECTION.DOWN);
        System.out.println(collection);

        System.out.println("CHANGE X_MAX AND Y_MAX");
        MovablesCollection.setxMax(90);
        MovablesCollection.setyMax(90);

        System.out.println("MOVE POINTS TO THE RIGHT");
        collection.moveObjectsFromTypeWithDirection(TYPE.POINT, DIRECTION.RIGHT);
        System.out.println(collection);

        System.out.println("MOVE CIRCLES UP");
        collection.moveObjectsFromTypeWithDirection(TYPE.CIRCLE, DIRECTION.UP);
        System.out.println(collection);


    }


}

interface Movable {
    void moveUp() throws ObjectCanNotBeMovedException;

     void moveLeft() throws ObjectCanNotBeMovedException;

     void moveRight() throws ObjectCanNotBeMovedException;

     void moveDown() throws ObjectCanNotBeMovedException;

     int getCurrentXPosition();

     int getCurrentYPosition();

     TYPE getType();
     boolean canBeFitted(int x,int y);
      String exceptionMessage();

}

class MovablePoint implements Movable {
    int x;
    int y;
    int xSpeed;
    int ySpeed;

    public MovablePoint(int x, int y, int xSpeed, int ySpeed) {
        this.x = x;
        this.y = y;
        this.xSpeed = xSpeed;
        this.ySpeed = ySpeed;
    }

    @Override
    public void moveUp() throws ObjectCanNotBeMovedException {
        if (y + ySpeed > MovablesCollection.yMAX)
            throw new ObjectCanNotBeMovedException(String.format("Point (%d,%d) is out of bounds", x, y + ySpeed));
        y += ySpeed;
    }

    @Override
    public void moveLeft() throws ObjectCanNotBeMovedException {
        if (x - xSpeed < 0)
            throw new ObjectCanNotBeMovedException(String.format("Point (%d,%d) is out of bounds", x - xSpeed, y));
        x -= xSpeed;
    }

    @Override
    public void moveRight() throws ObjectCanNotBeMovedException {
        if (x + xSpeed > MovablesCollection.xMAX)
            throw new ObjectCanNotBeMovedException(String.format("Point (%d,%d) is out of bounds", x + xSpeed, y));
        x += xSpeed;
    }

    @Override
    public void moveDown() throws ObjectCanNotBeMovedException {
        if (y - ySpeed < 0)
            throw new ObjectCanNotBeMovedException(String.format("Point (%d,%d) is out of bounds", x, y - ySpeed));
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
        return String.format("Movable point with coordinates (%d,%d)", x, y);
    }

    public TYPE getType() {
        return TYPE.POINT;
    }

    @Override
    public boolean canBeFitted(int xMax, int yMax) {
        return (x >= 0&&x <= xMax&&y >= 0 && y <= yMax);
    }
    @Override
    public String exceptionMessage() {
        return String.format("Movable point with coordinates (%d,%d) can not be fitted into the collection", x, y);
    }
}

class MovableCircle implements Movable {
    int radius;
    MovablePoint movablePoint;

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
        return ((movablePoint.x - radius) >= 0 && (movablePoint.x + radius) <= xMax && (movablePoint.y + radius) <= yMax && (movablePoint.y - radius) >= 0);
    }

    @Override
    public String exceptionMessage() {
        return String.format("Movable circle with center (%d,%d) and radius %d can not be fitted into the collection", movablePoint.x, movablePoint.y, radius);
    }
}

class MovablesCollection {
    List<Movable> movables;
    static int xMAX;
    static int yMAX;

    public MovablesCollection(int xMAx, int yMAx) {
        this.movables = new ArrayList<>();
        xMAX = xMAx;
        yMAX = yMAx;
    }

    public static void setxMax(int i) {
        xMAX = i;
    }

    public static void setyMax(int i) {
        yMAX = i;
    }

    public static int getyMax() {
        return yMAX;
    }

    public static int getxMAX() {
        return xMAX;
    }

    public void addMovableObject(Movable m) throws MovableObjectNotFittableException {
        checkAdd(m);
        movables.add(m);

    }

    public void moveObjectsFromTypeWithDirection(TYPE type, DIRECTION direction) {

        movables.stream()
                .filter(movable -> movable.getType().equals(type))
                .forEach(movable -> {
                    try {
                        switch (direction) {
                            case UP:
                                movable.moveUp();
                                break;
                            case DOWN:
                                movable.moveDown();
                                break;
                            case LEFT:
                                movable.moveLeft();
                                break;
                            case RIGHT:
                                movable.moveRight();
                                break;
                        }
                    } catch (ObjectCanNotBeMovedException e) {
                        System.out.println(e.getMessage());
                    }
                });

    }

    private static void move(TYPE type, DIRECTION direction, Movable m) throws ObjectCanNotBeMovedException {
        if (m.getType().equals(type)) {
            switch (direction) {
                case UP:
                    m.moveUp();
                case DOWN:
                    m.moveDown();
                case LEFT:
                    m.moveLeft();
                case RIGHT:
                    m.moveRight();
                default:
            }
        }
    }


    private void checkAdd(Movable m) throws MovableObjectNotFittableException {
        if (!m.canBeFitted(xMAX,yMAX))
            throw new MovableObjectNotFittableException(m.exceptionMessage());
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder=new StringBuilder();
        stringBuilder.append("Collection of movable objects with size ").append(movables.size()).append(":").append("\n");
        for(Movable m:movables)
            stringBuilder.append(m).append("\n");

        return stringBuilder.toString();

    }
}

class ObjectCanNotBeMovedException extends Exception {
    public ObjectCanNotBeMovedException(String msg) {
        super(msg);
    }
}

class MovableObjectNotFittableException extends Exception {

    public MovableObjectNotFittableException(String msg) {
        super(msg);
    }
}
