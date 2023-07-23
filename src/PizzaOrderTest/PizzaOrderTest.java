//package PizzaOrderTest;

import java.util.*;

interface Item {
    int getPrice();

    String getType();
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
    public ItemOutOfStockException() {
        super();
    }
}

class ExtraItem implements Item {
    String type;

    int price;

    @Override
    public String getType() {
        return type;
    }

    public ExtraItem(String type) throws InvalidExtraTypeException {
        this.type = type;
        this.price=calculatePrice();
    }

    int calculatePrice() throws InvalidExtraTypeException {
        if (type.equals("Ketchup")) return 3;
        else if (type.equals("Coke"))
            return 5;
        else throw new InvalidExtraTypeException();
    }

    @Override
    public int getPrice() {
        return price;
    }
}

class PizzaItem implements Item {
    String type;
    int price;

    @Override
    public int getPrice() {
        return price;
    }

    public PizzaItem(String type) throws InvalidPizzaTypeException {
        this.type = type;
        this.price=calculatePrice();
    }

    int calculatePrice() throws InvalidPizzaTypeException {
        if (type.equals("Standard")) return 10;
        else if (type.equals("Pepperoni")) {
            return 12;
        } else if (type.equals("Vegetarian")) return 8;
        else throw new InvalidPizzaTypeException();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
class EmptyOrder extends  Exception
{
    public EmptyOrder( ) {
        super();
    }
}
class OrderLockedException  extends  Exception
{
    public OrderLockedException () {
        super();
    }
}
class Order {
    private class ItemC {
        Item item;
        int count;
        public ItemC(Item item, int count) {
            this.item = item;
            this.count = count;

        }

        public ItemC(Item item) {
            this.item = item;
        }

        public Item getItem() {
            return item;
        }

        public void setItem(Item item) {
            this.item = item;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }


        public int getPrice() {
            return  getItem().getPrice()*getCount();
        }
    }
    List<ItemC> items;
    boolean locked;
    public Order() {
        this.items = new ArrayList<>();
        locked=false;
    }
    public boolean locked()
    {
        return  locked;
    }
    public void addItem(Item item, int count) throws OrderLockedException, ItemOutOfStockException {
        if (locked) {
            throw new OrderLockedException();
        }
        if (count > 10) {
            throw new ItemOutOfStockException();
        }
        Optional<ItemC> orderItem = items.stream()
                .filter(each -> each.getItem().getType().equals(item.getType()))
                .findFirst();
        if (orderItem.isPresent()) {
            orderItem.ifPresent(oi -> oi.setCount(count));
            return;
        }
        items.add(new ItemC(item, count));
    }

    public int getPrice()
    {
        return items.stream().mapToInt(ItemC::getPrice).sum();
    }
    public void displayOrder() {

        for (int i = 0; i < items.size(); i++)
        {
            System.out.printf("%3d.%-15sx%2d%5d$\n",i+1,items.get(i).item.getType(),items.get(i).count,items.get(i).item.getPrice()*items.get(i).getCount());
        }
        System.out.printf("%-22s%5d$\n", "Total:", getPrice());

    }
    public void removeItem(int idx) throws OrderLockedException {
        if(locked())
            throw new OrderLockedException();

        items.remove(idx);

    }
    public void lock() throws EmptyOrder {
        if(items.isEmpty())
            throw  new EmptyOrder();
        locked=true;
    }

}

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