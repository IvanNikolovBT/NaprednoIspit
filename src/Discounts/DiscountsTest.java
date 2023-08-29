//package Discounts;

import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;

public class DiscountsTest {
    public static void main(String[] args) {
        Discounts discounts = new Discounts();
        int stores = discounts.readStores(System.in);
        System.out.println("Stores read: " + stores);
        System.out.println("=== By average discount ===");
        discounts.byAverageDiscount().forEach(System.out::println);
        System.out.println("=== By total discount ===");
        discounts.byTotalDiscount().forEach(System.out::println);
    }
}

class Product {
    int a;
    int b;

    public Product(int a, int b) {
        this.a = a;
        this.b = b;
    }

    public Product(String s) {
        String[]splitter=s.split(":");
        a=Integer.parseInt(splitter[0]);
        b=Integer.parseInt(splitter[1]);
    }

    public int getDiscounted() {
        return a;
    }

    public int getRegular() {
        return b;
    }
    public int absolute()
    {
        return b-a;
    }
    public int discount()
    {
        return (b-a)*100/b;
    }
    @Override
    public String toString() {
        return String.format("%2d%% %d/%d", discount(), a, b);
    }
}

class Store {
    String name;
    Set<Product> productSet;

    public Store(String name) {
        this.name = name;
        productSet = new HashSet<>();
    }

    public void addPrices(int a, int b) {
        productSet.add(new Product(a, b));
    }

    @Override
    public String toString() {
        String productsStr = productSet.stream()
                .sorted(Comparator.comparing(Product::discount)
                        .thenComparing(Product::absolute).reversed())
                .map(Product::toString)
                .collect(Collectors.joining("\n"));
        double rounded = Math.round(getAvg() * 10) / 10.;

        return String.format("%s\nAverage discount: %.1f%%\nTotal discount: %d\n%s", name,
                rounded,
                totalDiscount(),
                productsStr);
    }

     int totalDiscount() {
        return productSet.stream().mapToInt(Product::absolute).sum();
    }

    double getAvg() {
        return productSet.stream().mapToDouble(Product::discount).average().orElse(0);
    }

    public void addProduct(Product product) {
        productSet.add(product);
    }

    public String  getName() {
        return name;
    }
}

class Discounts {

    List<Store> stores;

    public Discounts() {
        stores = new ArrayList<>();
    }

    public int readStores(InputStream inputStream) {
    Scanner scanner=new Scanner(inputStream);
    while(scanner.hasNextLine())
        stores.add(StoreFactory.CREATE(scanner.nextLine()));
    return stores.size();
    }

    public List<Store> byAverageDiscount() {
        return stores.stream().sorted(Comparator.comparing(Store::getAvg,Comparator.reverseOrder()).thenComparing(Store::getName)).limit(3).collect(Collectors.toList());
    }

    public List<Store> byTotalDiscount() {
    return stores.stream().sorted(Comparator.comparing(Store::totalDiscount).thenComparing(Store::getName)).limit(3).collect(Collectors.toList());
    }

}
class StoreFactory
{
    public  static Store  CREATE(String line)
    {
        String[]splitter=line.split("\\s+");
        Store store=new Store(splitter[0]);
        for(int i=1;i<splitter.length;i++)
        {
            store.addProduct(new Product(splitter[i]));
        }
        return store;
    }
}
