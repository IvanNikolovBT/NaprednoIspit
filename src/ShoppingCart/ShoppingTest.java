package ShoppingCart;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.*;

enum Type {
    WS, PS;
}

class InvalidOperationException extends Exception {
    public InvalidOperationException(String id) {
        super("The quantity of the product with id " + id + " can not be 0.");
    }
}

abstract class Item {
    public abstract Type getType();

    public abstract String getID();

    public abstract String getName();

    public abstract double getPrice();

    public abstract double getQuantity();
    public abstract  double getCost();
}

class WSI extends Item {
    Type type = Type.WS;
    String id;
    String name;
    double price;
    int quantity;

    public WSI(String s) {
        String[] splitter = s.split(";");
        id = splitter[1];
        name = splitter[2];
        price = Double.parseDouble(splitter[3]);
        quantity = Integer.parseInt(splitter[4]);

    }

    @Override
    public Type getType() {
        return type;
    }

    @Override
    public String getID() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public double getPrice() {
        return price;
    }

    @Override
    public double getQuantity() {
        return quantity;
    }

    @Override
    public double getCost() {
        return price*quantity;
    }
}

class PSI extends Item {
    Type type = Type.PS;
    String id;
    String name;
    double price;
    double quantity;

    public PSI(String s) {
        String[] splitter = s.split(";");
        id = splitter[1];
        name = splitter[2];
        price = Double.parseDouble(splitter[3]);
        quantity = Double.parseDouble(splitter[4]);
    }

    @Override
    public double getCost() {
        return quantity*price/1000;
    }

    @Override
    public Type getType() {
        return type;
    }

    @Override
    public String getID() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public double getPrice() {
        return price;
    }

    @Override
    public double getQuantity() {
        return quantity;
    }
}

class ShoppingCart {
    List<Item> items;

    public ShoppingCart(List<Item> items) {
        this.items = items;
    }

    public ShoppingCart() {
        this.items = new ArrayList<>();
    }

    public void addItem(String itemData) throws InvalidOperationException {
        String[] spliter = itemData.split(";");
        if (spliter[spliter.length - 1].equalsIgnoreCase("0")) throw new InvalidOperationException(spliter[1]);

        if (Objects.equals(spliter[0], "WS")) {
            items.add(new WSI(itemData));
        } else {
            items.add(new PSI(itemData));
        }

    }
    public void printShoppingCart(OutputStream os)
    {
        PrintWriter writer=new PrintWriter(os);
        Collections.sort(items,Comparator.comparingDouble(Item::getCost).reversed());
        for (Item item:items)
        {
            writer.printf("%s - %.2f\n",item.getID(),item.getCost());
        }
        writer.flush();
    }
    public void blackFridayOffer(List<Integer> discountItems,OutputStream os) throws InvalidOperationException {
        if(discountItems.size()==0) {
            System.out.println("There are no products with discount.");
            return;
        }
        PrintWriter writer=new PrintWriter(os);
        for(Item item:items)
        {
            if(discountItems.contains(Integer.parseInt(item.getID())))
            {
                writer.printf("%s - %.2f\n",item.getID(),item.getCost()*0.1);
            }
        }
        writer.flush();
    }
}

public class ShoppingTest {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ShoppingCart cart = new ShoppingCart();

        int items = Integer.parseInt(sc.nextLine());
        for (int i = 0; i < items; i++) {
            try {
                cart.addItem(sc.nextLine());
            } catch (InvalidOperationException e) {
                System.out.println(e.getMessage());
            }
        }

        List<Integer> discountItems = new ArrayList<>();
        int discountItemsCount = Integer.parseInt(sc.nextLine());
        for (int i = 0; i < discountItemsCount; i++) {
            discountItems.add(Integer.parseInt(sc.nextLine()));
        }

        int testCase = Integer.parseInt(sc.nextLine());
        if (testCase == 1) {
            cart.printShoppingCart(System.out);
        } else if (testCase == 2) {
            try {
                cart.blackFridayOffer(discountItems, System.out);
            } catch (InvalidOperationException e) {
                System.out.println(e.getMessage());
            }
        } else {
            System.out.println("Invalid test case");
        }
    }
}