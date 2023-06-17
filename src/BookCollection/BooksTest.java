package BookCollection;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

class Book {
    String title;
    String category;
    float price;

    public String getTitle() {
        return title;
    }

    public String getCategory() {
        return category;
    }

    public float getPrice() {
        return price;
    }

    public Book(String title, String category, float price) {
        this.title = title;
        this.category = category;
        this.price = price;
    }

    @Override
    public String toString() {
        return String.format("%s (%s) %f", title, category, price);
    }

//   @Override
//    public int compareTo(Book o) {
//        int res=this.title.compareTo(o.title);
//        if(res==0)
//        {
//            return Float.compare(this.price,o.price);
//        }
//        return res;
//    }`
}
class TitleAndPriceComparator implements   Comparator<Book>
{
    @Override
    public int compare(Book o1, Book o2) {
        int res=o1.title.compareTo(o2.title);
        if(res==0)
        {
            return Float.compare(o1.price,o2.price);
        }
        return res;
    }

    @Override
    public boolean equals(Object obj) {
        return false;
    }
}
class PriceComparator implements Comparator<Book>
{
    @Override
    public int compare(Book o1, Book o2) {
        int res=Float.compare(o1.price,o2.price);
        if(res==0)
         return o1.title.compareTo(o2.title);

        return res;
    }
}
class BookCollection {
    List<Book> books;

    final Comparator<Book> titleAndPriceComparator = (o1, o2) -> {
        int res = o1.title.compareTo(o2.title);
        if (res == 0)
            return Float.compare(o1.price, o2.price);
        return res;
    };
    final Comparator<Book> priceComparator = (o1, o2) -> {
        int res = Float.compare(o1.price, o2.price);
        if (res == 0)
            return o1.title.compareTo(o2.title);
        return res;
    };

    public BookCollection() {
        books = new ArrayList<>();
    }

    public void addBook(Book book) {
        books.add(book);
    }

    public void printByCategory(String category) {
        books.stream().
                filter(book -> book.category.equalsIgnoreCase(category)).sorted(titleAndPriceComparator).forEach(System.out::println);
    }

    public List<Book> getCheapestN(int n) {
        return books.stream().
                sorted(priceComparator)
                .limit(n).collect(Collectors.toList());
    }

    final Comparator<Book> getTitleAndPriceComparator = Comparator.
            comparing(Book::getTitle).
            thenComparing(Book::getPrice);
    final Comparator<Book> getPriceComparator=Comparator.comparing(Book::getPrice).thenComparing(Book::getTitle);


}
public class BooksTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        scanner.nextLine();
        BookCollection booksCollection = new BookCollection();
        Set<String> categories = fillCollection(scanner, booksCollection);
        System.out.println("=== PRINT BY CATEGORY ===");
        for (String category : categories) {
            System.out.println("CATEGORY: " + category);
            booksCollection.printByCategory(category);
        }
        System.out.println("=== TOP N BY PRICE ===");
        print(booksCollection.getCheapestN(n));
    }

    static void print(List<Book> books) {
        for (Book book : books) {
            System.out.println(book);
        }
    }

    static TreeSet<String> fillCollection(Scanner scanner,
                                          BookCollection collection) {
        TreeSet<String> categories = new TreeSet<String>();
        while (scanner.hasNext()) {
            String line = scanner.nextLine();
            String[] parts = line.split(":");
            Book book = new Book(parts[0], parts[1], Float.parseFloat(parts[2]));
            collection.addBook(book);
            categories.add(parts[1]);
        }
        return categories;
    }
}

// Вашиот код овде