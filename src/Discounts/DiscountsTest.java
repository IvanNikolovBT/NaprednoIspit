package Discounts;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

class Product
{
    int price;
    int discount;

    public Product(int discount, int price) {
        this.price = price;
        this.discount = discount;
    }
    public int discounted()
    {
        return (price-discount)*100/price;
    }
    public int absoluteDiscount()
    {
        return price-discount;
    }
    @Override
    public String toString() {
        return String.format("%2d%% %d/%d", discounted(), discount, price);
    }
}
class Store
{
    String name;
    List<Product> productList;
    public Store(String name, List<Product> productList) {
        this.name = name;
        this.productList=productList;
    }

    public static Store  createStore(String s) {
        String[]a=s.split("\\s+");
        String name=a[0];
        List<Product> productList1=new ArrayList<>();
        for(int i=1;i<a.length;i++)
        {
            String[]second=a[i].split(":");
            Product p=new Product(Integer.parseInt(second[0]),Integer.parseInt(second[1]));
            productList1.add(p);
        }
        return new Store(name,productList1);
    }
    public double getAverage()
    {
        return productList.stream().mapToDouble(Product::discounted).average().orElse(0);
    }
    public String getName()
    {
        return  name;
    }
    public int getTotalDiscount()
    {
        return productList.stream().mapToInt(Product::absoluteDiscount).sum();
    }

    @Override
    public String toString() {
        StringBuilder sb=new StringBuilder();
        sb.append(getName()).append("\n").append("Average discount: ").
                append(String.format("%.1f%%",getAverage())).
                append("\n").append("Total discount: ").append(String.format("%d",getTotalDiscount())).append("\n");

        List<Product> productList1= productList.stream().sorted(Comparator.comparing(Product::discounted).thenComparing(Product::absoluteDiscount).reversed()).collect(Collectors.toList());
        for(int i=0;i<productList1.size();i++)
        {
            sb.append(productList1.get(i));
            if(i!= (productList1.size()-1))
                sb.append("\n");
        }
        return sb.toString();
    }
}
class Discounts
{
    List<Store> stores;
    public Discounts()
    {
        stores=new ArrayList<>();
    }

    public int readStores(InputStream inputStream)
    {
        BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(inputStream));
        stores=bufferedReader.lines().map(Store::createStore).collect(Collectors.toList());
        return  stores.size();
    }
    public List<Store> byAverageDiscount()
    {
        return stores.stream().sorted(Comparator.comparing(Store::getAverage).reversed().thenComparing(Store::getName)).limit(3).collect(Collectors.toList());
    }
    public List<Store> byTotalDiscount()
    {
        return stores.stream().sorted(Comparator.comparing(Store::getTotalDiscount).thenComparing(Store::getName)).limit(3).collect(Collectors.toList());
    }
}
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

