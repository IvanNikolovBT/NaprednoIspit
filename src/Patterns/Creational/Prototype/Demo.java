package Patterns.Creational.Prototype;

public class Demo  {
    public static void main(String[] args) throws  CloneNotSupportedException {
        BookShop bookShop=new BookShop();
        bookShop.setShopName("bukva");
        bookShop.loadData();
        System.out.println(bookShop);
        BookShop bs1=(BookShop) bookShop.clone();
        bs1.setShopName("antolog");
        System.out.println(bs1);
    }
}
