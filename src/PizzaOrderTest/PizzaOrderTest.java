//package PizzaOrderTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

public class PizzaOrderTest {

    public static void main(String[] args) {
        Scanner jin = new Scanner(System.in);
        int k = jin.nextInt();
        if (k == 0) { //test Item
            try {
                String type = jin.next();
                String name = jin.next();
                Item item = null;
                if (type.equals("Pizza")) item = new PizzaItem(name);
                else item = new ExtraItem(name);
                System.out.println(item.getPrice());
            } catch (Exception e) {
                System.out.println(e.getClass().getSimpleName());
            }
        }
        if (k == 1) { // test simple order
            Order order = new Order();
            while (true) {
                try {
                    String type = jin.next();
                    String name = jin.next();
                    Item item = null;
                    if (type.equals("Pizza")) item = new PizzaItem(name);
                    else item = new ExtraItem(name);
                    if (!jin.hasNextInt()) break;
                    order.addItem(item, jin.nextInt());
                } catch (Exception e) {
                    System.out.println(e.getClass().getSimpleName());
                }
            }
            jin.next();
            System.out.println(order.getPrice());
            order.displayOrder();
            while (true) {
                try {
                    String type = jin.next();
                    String name = jin.next();
                    Item item = null;
                    if (type.equals("Pizza")) item = new PizzaItem(name);
                    else item = new ExtraItem(name);
                    if (!jin.hasNextInt()) break;
                    order.addItem(item, jin.nextInt());
                } catch (Exception e) {
                    System.out.println(e.getClass().getSimpleName());
                }
            }
            System.out.println(order.getPrice());
            order.displayOrder();
        }
        if (k == 2) { // test order with removing
            Order order = new Order();
            while (true) {
                try {
                    String type = jin.next();
                    String name = jin.next();
                    Item item = null;
                    if (type.equals("Pizza")) item = new PizzaItem(name);
                    else item = new ExtraItem(name);
                    if (!jin.hasNextInt()) break;
                    order.addItem(item, jin.nextInt());
                } catch (Exception e) {
                    System.out.println(e.getClass().getSimpleName());
                }
            }
            jin.next();
            System.out.println(order.getPrice());
            order.displayOrder();
            while (jin.hasNextInt()) {
                try {
                    int idx = jin.nextInt();
                    order.removeItem(idx);
                } catch (Exception e) {
                    System.out.println(e.getClass().getSimpleName());
                }
            }
            System.out.println(order.getPrice());
            order.displayOrder();
        }
        if (k == 3) { //test locking & exceptions
            Order order = new Order();
            try {
                order.lock();
            } catch (Exception e) {
                System.out.println(e.getClass().getSimpleName());
            }
            try {
                order.addItem(new ExtraItem("Coke"), 1);
            } catch (Exception e) {
                System.out.println(e.getClass().getSimpleName());
            }
            try {
                order.lock();
            } catch (Exception e) {
                System.out.println(e.getClass().getSimpleName());
            }
            try {
                order.removeItem(0);
            } catch (Exception e) {
                System.out.println(e.getClass().getSimpleName());
            }
        }
    }

}

interface Item {
    public int getPrice();

    public void setCount(int count);

    public int getCount();

    public String getType();

    public int getFull();
}

class ExtraItem implements Item {
    String type;
    int count;

    int price;

    public ExtraItem(String type) throws InvalidExtraTypeException {
        this.type = type;
        calculatePrice();
        count = 1;
    }

    public String getType() {
        return type;
    }

    private void calculatePrice() throws InvalidExtraTypeException {
        if (type.equals("Ketchup")) price = 3;
        else if (type.equals("Coke")) price = 5;
        else throw new InvalidExtraTypeException();
    }

    @Override
    public int getPrice() {
        return price;
    }

    @Override
    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public int getCount() {
        return count;
    }

    public int getFull() {
        return count * price;
    }

    @Override
    public String toString() {

        return String.format("%-15sx%2d%5d$\n", getType(), getCount(), getFull());
    }
}

class PizzaItem implements Item {
    String type;
    int price;
    int count;

    public PizzaItem(String type) throws InvalidPizzaTypeException {
        this.type = type;
        calculatePrice();
        count = 1;
    }


    private void calculatePrice() throws InvalidPizzaTypeException {

        if (type.equals("Standard")) price = 10;
        else if (type.equals("Pepperoni")) price = 12;
        else if (type.equals("Vegetarian")) price = 8;
        else throw new InvalidPizzaTypeException();
    }

    @Override
    public int getPrice() {
        return price;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getType() {
        return type;
    }

    @Override
    public int getCount() {
        return count;
    }

    public int getFull() {
        return count * price;
    }
    @Override
    public String toString() {

        return String.format("%-15sx%2d%5d$\n", getType(), getCount(), getFull());
    }
}

class Order {
    List<Item> items;
    boolean locked;

    public Order() {
        items = new ArrayList<>();
        locked = false;
    }

    public void addItem(Item item, int count) throws ItemOutOfStockException, OrderLockedException {
        checkLock();
        checkCount(item, count);
        int index=findItem(item.getType());
        item.setCount(count);
        if(index!=-1)
        {
            Item i=items.get(index);
            i.setCount(count);
        }
        else
            items.add(item);


    }

    private static void checkCount(Item item, int count) throws ItemOutOfStockException {
        if (count > 10) throw new ItemOutOfStockException(item);
    }

    private void checkLock() throws OrderLockedException {
        if (locked) throw new OrderLockedException();
    }

    private int findItem(String type)
    {
        int i=0;
        for(i=0;i<items.size();i++)
        {
            if(items.get(i).getType().equals(type))
                return i;
        }
        return -1;
    }

    public int getPrice() {
        return items.stream().mapToInt(Item::getFull).sum();
    }

    public void displayOrder() {
        AtomicInteger z = new AtomicInteger(1);
        items.forEach(i-> System.out.printf("%3d.%s",z.getAndIncrement(),i));
        System.out.printf("%-22s%5d$\n","Total:",getPrice());
    }

    public void removeItem(int idx) throws OrderLockedException {
        checkLock();
        items.remove(idx);
    }

    public void lock() throws EmptyOrder {
        if (items.isEmpty()) throw new EmptyOrder();
        locked = true;
    }


}

class InvalidExtraTypeException extends Exception {
    public InvalidExtraTypeException() {
        super();
    }
}

class InvalidPizzaTypeException extends Exception {
    public InvalidPizzaTypeException() {
        super();
    }
}

class ItemOutOfStockException extends Exception {
    public ItemOutOfStockException(Item item) {
        super();
    }
}

class EmptyOrder extends Exception {
    public EmptyOrder() {
        super();
    }
}

class OrderLockedException extends Exception {

    public OrderLockedException() {
        super();
    }
}
