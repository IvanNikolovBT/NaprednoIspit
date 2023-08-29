package OnlinePaymentsTest;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.*;
import java.util.stream.*;

public class OnlinePaymentsTest {
    public static void main(String[] args) {
        OnlinePayments onlinePayments = new OnlinePayments();

        onlinePayments.readItems(System.in);

        IntStream.range(151020, 151025).mapToObj(String::valueOf).forEach(id -> onlinePayments.printStudentReport(id, System.out));
    }
}

class Payment {
    String name;
    int price;

    public Payment(String name, int price) {
        this.name = name;
        this.price = price;
    }

    public double getTax() {
        return price * 0.0114;
    }

    public int getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return String.format("%s %d", name, price);
    }


    public String getName() {
        return name;
    }
}

class Student {
    Set<Payment> payments;
    String id;

    public Student(String id) {
        this.id = id;
        payments = new TreeSet<>(Comparator.comparing(Payment::getPrice, Comparator.reverseOrder()).thenComparing(Payment::getName, Comparator.reverseOrder()));
    }

    public void addPayment(Payment p) {
        payments.add(p);
    }

    public int netSum() {
        return payments.stream().mapToInt(Payment::getPrice).sum();
    }

    public int taxSum() {
        int val = (int) Math.round(payments.stream().mapToDouble(Payment::getTax).sum());
        if (val < 3) return 3;
        return Math.min(val, 300);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        int i = 1;
        sb.append(String.format("Student: %s Net: %d Fee: %d Total: %d\n", id, netSum(), taxSum(), netSum() + taxSum()));
        sb.append("Items:\n");
        for (Payment p : payments)
            sb.append(String.format("%d. %s\n", i++, p));
        return sb.toString();
    }
}

class OnlinePayments {
    Map<String, Student> students;

    public OnlinePayments() {
        students = new TreeMap<>();
    }

    public void readItems(InputStream in) {
        Scanner scanner = new Scanner(in);
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (line.equals("kraj")) break;
            String[] splitter = line.split(";");
            Student s;
            if (students.containsKey(splitter[0])) s = students.get(splitter[0]);
            else s = new Student(splitter[0]);
            {
                s.addPayment(new Payment(splitter[1], Integer.parseInt(splitter[2])));
                students.put(s.id,s);
            }

        }
        scanner.close();
    }


    public void printStudentReport(String id, OutputStream out) {
        PrintWriter pw = new PrintWriter(out);
        try {
            check(id);
            System.out.printf("%s",students.get(id));
        } catch (StudentNotFountException e) {
            System.out.printf("Student %s not found!\n",id);
        }

        pw.flush();
    }

    private void check(String id) throws StudentNotFountException {
        if (!students.containsKey(id))
            throw new StudentNotFountException();
    }
}

class StudentNotFountException extends Exception {
    public StudentNotFountException() {
        super();
    }
}
