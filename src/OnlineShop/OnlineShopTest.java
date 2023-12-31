package OnlineShop;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

enum COMPARATOR_TYPE {
    NEWEST_FIRST, OLDEST_FIRST, LOWEST_PRICE_FIRST, HIGHEST_PRICE_FIRST, MOST_SOLD_FIRST, LEAST_SOLD_FIRST
}

class ProductNotFoundException extends Exception {
    ProductNotFoundException(String message) {
        super(message);
    }
}


class Product {
    String category;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public double getPrice() {
        return price;
    }

    public int getTimesSold() {
        return timesSold;
    }

    String id;
    String name;
    LocalDateTime createdAt;
    double price;
    int timesSold;

    public String getCategory() {
        return category;
    }

    public Product(String category, String id, String name, LocalDateTime createdAt, double price) {
        this.category = category;
        this.id = id;
        this.name = name;
        this.createdAt = createdAt;
        this.price = price;
        timesSold = 0;
    }

    public void addSold() {
        timesSold++;
    }

    @Override
    public String toString() {
        return "Product{" + "id='" + id + '\'' + ", name='" + name + '\'' + '}';
    }
}


class OnlineShop {

    Map<String, List<Product>> productsByCategory;
    Map<String, Product> products;

    OnlineShop() {
        products = new TreeMap<>();
        productsByCategory = new TreeMap<>();
    }

    void addProduct(String category, String id, String name, LocalDateTime createdAt, double price) {
        Product p = new Product(category, id, name, createdAt, price);
        products.put(id, p);
        productsByCategory.putIfAbsent(category, new ArrayList<>());
        productsByCategory.computeIfPresent(category, (k, v) -> {
            v.add(p);
            return v;
        });

    }

    double buyProduct(String id, int quantity) throws ProductNotFoundException {
        if (!products.containsKey(id)) throw new ProductNotFoundException("");
        Product p = products.get(id);
        p.addSold();
        return p.price * quantity;


    }

    List<List<Product>> listProducts(String category, COMPARATOR_TYPE comparatorType, int pageSize) {
        List<Product> products1;
        if (category == null) products1 = new ArrayList<>(products.values());
        else products1 = productsByCategory.get(category);
        Comparator<Product> comparator = ComparatorFactory.createComparator(comparatorType);
        products1.sort(comparator);
        List<List<Product>> result = new ArrayList<>();
        if (products1.size() < pageSize) result.add(products1);
        else {
            int ratio = (int) Math.ceil(products.size() * 1.0 / pageSize);
            List<Integer> starts = IntStream.range(0, ratio).map(i -> i * pageSize).boxed().collect(Collectors.toList());
            starts.forEach(i -> result.add(products1.subList(i, Math.min((i + pageSize), products.size()))));
        }
        return result;
    }

}

class ComparatorFactory {
    static Comparator<Product> createComparator(COMPARATOR_TYPE comparatorType) {
        switch (comparatorType) {
            case NEWEST_FIRST:
                return Comparator.comparing(Product::getCreatedAt);
            case OLDEST_FIRST:
                return Comparator.comparing(Product::getCreatedAt, Comparator.reverseOrder());
            case MOST_SOLD_FIRST:
                return Comparator.comparing(Product::getTimesSold, Comparator.reverseOrder());
            case LEAST_SOLD_FIRST:
                return Comparator.comparing(Product::getTimesSold);
            case LOWEST_PRICE_FIRST:
                return Comparator.comparing(Product::getPrice);
            case HIGHEST_PRICE_FIRST:
                return Comparator.comparing(Product::getPrice, Comparator.reverseOrder());
            default:
                return null;
        }
    }
}

public class OnlineShopTest {

    public static void main(String[] args) {
        OnlineShop onlineShop = new OnlineShop();
        double totalAmount = 0.0;
        Scanner sc = new Scanner(System.in);
        String line;
        while (sc.hasNextLine()) {
            line = sc.nextLine();
            String[] parts = line.split("\\s+");
            if (parts[0].equalsIgnoreCase("addproduct")) {
                String category = parts[1];
                String id = parts[2];
                String name = parts[3];
                LocalDateTime createdAt = LocalDateTime.parse(parts[4]);
                double price = Double.parseDouble(parts[5]);
                onlineShop.addProduct(category, id, name, createdAt, price);
            } else if (parts[0].equalsIgnoreCase("buyproduct")) {
                String id = parts[1];
                int quantity = Integer.parseInt(parts[2]);
                try {
                    totalAmount += onlineShop.buyProduct(id, quantity);
                } catch (ProductNotFoundException e) {
                    System.out.println(e.getMessage());
                }
            } else {
                String category = parts[1];
                if (category.equalsIgnoreCase("null")) category = null;
                String comparatorString = parts[2];
                int pageSize = Integer.parseInt(parts[3]);
                COMPARATOR_TYPE comparatorType = COMPARATOR_TYPE.valueOf(comparatorString);
                printPages(onlineShop.listProducts(category, comparatorType, pageSize));
            }
        }
        System.out.println("Total revenue of the online shop is: " + totalAmount);

    }

    private static void printPages(List<List<Product>> listProducts) {
        for (int i = 0; i < listProducts.size(); i++) {
            System.out.println("PAGE " + (i + 1));
            listProducts.get(i).forEach(System.out::println);
        }
    }
}
