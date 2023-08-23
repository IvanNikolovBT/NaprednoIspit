package MojDDV;//package MojDDV;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.Scanner;

public class MojDDVTest {

    public static void main(String[] args) {

        MojDDV mojDDV = new MojDDV();

        System.out.println("===READING RECORDS FROM INPUT STREAM===");
        mojDDV.readRecords(System.in);

        System.out.println("===PRINTING TAX RETURNS RECORDS TO OUTPUT STREAM ===");
        mojDDV.printTaxReturns(System.out);

        System.out.println("===PRINTING SUMMARY STATISTICS FOR TAX RETURNS TO OUTPUT STREAM===");
        mojDDV.printStatistics(System.out);

    }
}

class Item {
    double tax;
    int price;

    public Item(double tax, int price) {
        this.tax = tax;
        this.price = price;
    }

    public double getTax() {
        return tax;
    }

    public void setTax(double tax) {
        this.tax = tax;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public double getWithTax() {
        return price * tax;
    }

}

class ItemFactory {
    public static Item create(int price, String letter) {
        switch (letter) {
            case "A":
                return new Item(0.18*0.15, price);
            case "B":
                return new Item(0.05*0.15, price);
            case "V":
                return new Item(0.0, price);
            default:
                return null;
        }
    }
}

class Bill {
    String id;
    List<Item> items;

    public Bill(String id) {
        this.id = id;
        this.items = new ArrayList<>();
    }
    public Bill(String id,List<Item> items) throws AmountNotAllowedException {
        this.id=id;
        this.items=new ArrayList<>();
        int sum=items.stream().mapToInt(Item::getPrice).sum();
        if(sum>30000)
            throw  new AmountNotAllowedException(String.format("Receipt with amount %d is not allowed to be scanned\n", sum));
        this.items=items;
    }


    public void addItem(Item item) throws AmountNotAllowedException {

        items.add(item);
        if (sum() > 30000)
            throw new AmountNotAllowedException(String.format("Receipt with amount %d is not allowed to be scanned\n", sum()));
    }

    public int sum() {
        return items.stream().mapToInt(Item::getPrice).sum();
    }
    public  double taxReturn()
    {
        return  items.stream().mapToDouble(Item::getWithTax).sum();
    }


}

class MojDDV {
    List<Bill> bills;

    public MojDDV() {
        bills = new ArrayList<>();
    }

    public void readRecords(InputStream inputStream) {
        Scanner scanner = new Scanner(inputStream);
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] splitter = line.split("\\s+");
            String id = splitter[0];
            try {
                bills.add(new Bill(id,fillBill(splitter)));
            } catch (AmountNotAllowedException e) {
                System.out.print(e.getMessage());
            }

        }
        scanner.close();

    }
    private List<Item> fillBill(String[] splitter) throws AmountNotAllowedException {
        List<Item> items=new ArrayList<>();
        for (int i = 1; i < splitter.length; i += 2) {
            items.add(ItemFactory.create(Integer.parseInt(splitter[i]),splitter[i+1]));
        }
        return items;



    }
    void printTaxReturns(OutputStream outputStream)
    {
        PrintWriter printWriter=new PrintWriter(outputStream);
        bills.forEach(i->printWriter.printf("%10s\t%10d\t%10.5f\n",i.id,i.sum(),i.taxReturn()));
        printWriter.flush();
    }
    public void printStatistics(OutputStream outputStream)
    {
        PrintWriter printWriter=new PrintWriter(outputStream);
        DoubleSummaryStatistics stats=bills.stream().mapToDouble(Bill::taxReturn).summaryStatistics();
        printWriter.println(String.format("min:\t%05.03f\nmax:\t%05.03f\nsum:\t%05.03f\ncount:\t%-5d\navg:\t%05.03f",
                stats.getMin(),
                stats.getMax(),
                stats.getSum(),
                stats.getCount(),
                stats.getAverage()));
        printWriter.flush();
    }



}

class AmountNotAllowedException extends Exception {
    public AmountNotAllowedException(String msg) {
        super(msg);
    }

}
