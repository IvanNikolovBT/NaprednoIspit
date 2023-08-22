package MojDDV;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

enum TaxType {
    A, B, V
}

class Item {

    TaxType taxType;
    double price;

    public Item(TaxType taxType, double price) {
        this.taxType = taxType;
        this.price = price;
    }

    public Item(int parseInt) {
        price = parseInt;
    }

    public TaxType getTaxType() {
        return taxType;
    }

    public void setTaxType(TaxType taxType) {
        this.taxType = taxType;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getTax() {
        if (getTaxType() == TaxType.A) return price * 0.18;
        if (getTaxType() == TaxType.B) return price * 0.05;
        return 0;
    }
}

class Receipt implements Comparable<Receipt> {
    @Override
    public int compareTo(Receipt o) {
        return Comparator.comparing(Receipt::totalDDV).thenComparing(Receipt::totalAmount).compare(this,o);
    }

    private long id;
    List<Item> items;

    public Receipt(long id) {
        this.id = id;
        this.items = new ArrayList<>();
    }

    public Receipt(long id, List<Item> items) {
        this.id = id;
        this.items = items;
    }

    public static Receipt create(String s) throws AmountNotAllowedException {
        String[] parts = s.split("\\s+");
        long id = Long.parseLong(parts[0]);
        List<Item> items1 = new ArrayList<>();
        Arrays.stream(parts).skip(1).forEach(i -> {
                    if (Character.isDigit(i.charAt(0))) items1.add(new Item(Integer.parseInt(i)));
                    else {
                        items1.get(items1.size() - 1).setTaxType(TaxType.valueOf(i));
                    }

                }

        );
        if (totalAmount(items1) > 30000) throw new AmountNotAllowedException(totalAmount(items1));

        return new Receipt(id, items1);
    }

    public static double totalAmount(List<Item> items) {
        return items.stream().mapToDouble(l -> l.price).sum();
    }
    public  double totalAmount() {
        return items.stream().mapToDouble(l -> l.price).sum();
    }


    public double totalDDV() {
        return items.stream().mapToDouble(l -> l.getTax()).sum();
    }

    @Override
    public String toString() {
        return id+" "+totalAmount()+" "+totalDDV();
    }
}

class AmountNotAllowedException extends Exception {
    public AmountNotAllowedException(double amount) {
        super(String.format("Receipt with amount %f is not allowed to be scanned", amount));
    }
}

class MojDDV {
    List<Receipt> receiptList;

    public MojDDV() {
        this.receiptList = new ArrayList<>();
    }

    public void readRecords(InputStream inputStream) {
        receiptList = new BufferedReader(new InputStreamReader(inputStream)).lines().map(l -> {
            try {
                return Receipt.create(l);
            } catch (AmountNotAllowedException e) {
                System.out.println(e.getMessage());
                return null;
            }
        }).collect(Collectors.toList());
        receiptList = receiptList.stream().filter(Objects::nonNull).collect(Collectors.toList());

    }

    public void printTaxReturns(PrintStream outputStream) {
        PrintWriter printWriter=new PrintWriter(outputStream);

        receiptList.stream().sorted().forEach(i->outputStream.println(i.toString()));

        printWriter.flush();

    }
    public void printStatitistics(PrintStream out)
    {
        PrintWriter printWriter=new PrintWriter(out);
        DoubleSummaryStatistics summaryStatistics= receiptList.stream().mapToDouble(Receipt::totalDDV).summaryStatistics();
        printWriter.println(String.format("min: %.2f max: %.2f sum: %.2f count: %.2f average %.2f",
                summaryStatistics.getMin(),
                summaryStatistics.getMax(),
                summaryStatistics.getSum(),
                summaryStatistics.getCount(),
                summaryStatistics.getAverage()
        ));

        printWriter.flush();
    }
}


public class MojDDVTest {

    public static void main(String[] args) {

        MojDDV mojDDV = new MojDDV();

        System.out.println("===READING RECORDS FROM INPUT STREAM===");
        mojDDV.readRecords(System.in);

        System.out.println("===PRINTING TAX RETURNS RECORDS TO OUTPUT STREAM ===");
        mojDDV.printTaxReturns(System.out);

    }
}
