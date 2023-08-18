package ContactsTester;

import java.lang.reflect.Type;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;


public class ContactsTester {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int tests = scanner.nextInt();
        Faculty faculty = null;

        int rvalue = 0;
        long rindex = -1;

        DecimalFormat df = new DecimalFormat("0.00");

        for (int t = 0; t < tests; t++) {

            rvalue++;
            String operation = scanner.next();

            switch (operation) {
                case "CREATE_FACULTY": {
                    String name = scanner.nextLine().trim();
                    int N = scanner.nextInt();

                    Student[] students = new Student[N];

                    for (int i = 0; i < N; i++) {
                        rvalue++;

                        String firstName = scanner.next();
                        String lastName = scanner.next();
                        String city = scanner.next();
                        int age = scanner.nextInt();
                        long index = scanner.nextLong();

                        if ((rindex == -1) || (rvalue % 13 == 0)) rindex = index;

                        Student student = new Student(firstName, lastName, city, age, index);
                        students[i] = student;
                    }

                    faculty = new Faculty(name, students);
                    break;
                }

                case "ADD_EMAIL_CONTACT": {
                    long index = scanner.nextInt();
                    String date = scanner.next();
                    String email = scanner.next();

                    rvalue++;

                    if ((rindex == -1) || (rvalue % 3 == 0)) rindex = index;

                    faculty.getStudent(index).addEmailContact(date, email);
                    break;
                }

                case "ADD_PHONE_CONTACT": {
                    long index = scanner.nextInt();
                    String date = scanner.next();
                    String phone = scanner.next();

                    rvalue++;

                    if ((rindex == -1) || (rvalue % 3 == 0)) rindex = index;

                    faculty.getStudent(index).addPhoneContact(date, phone);
                    break;
                }

                case "CHECK_SIMPLE": {
                    System.out.println("Average number of contacts: " + df.format(faculty.getAverageNumberOfContacts()));

                    rvalue++;

                    String city = faculty.getStudent(rindex).getCity();
                    System.out.println("Number of students from " + city + ": " + faculty.countStudentsFromCity(city));

                    break;
                }

                case "CHECK_DATES": {

                    rvalue++;

                    System.out.print("Latest contact: ");
                    Contact latestContact = faculty.getStudent(rindex).getLatestContact();
                    if (latestContact.getType().equals("Email"))
                        System.out.println(((EmailContact) latestContact).getEmail());
                    if (latestContact.getType().equals("Phone"))
                        System.out.println(((PhoneContact) latestContact).getPhone() + " (" + ((PhoneContact) latestContact).getOperator().toString() + ")");

                    if (faculty.getStudent(rindex).getEmailContacts().length > 0 && faculty.getStudent(rindex).getPhoneContacts().length > 0) {
                        System.out.print("Number of email and phone contacts: ");
                        System.out.println(faculty.getStudent(rindex).getEmailContacts().length + " " + faculty.getStudent(rindex).getPhoneContacts().length);

                        System.out.print("Comparing dates: ");
                        int posEmail = rvalue % faculty.getStudent(rindex).getEmailContacts().length;
                        int posPhone = rvalue % faculty.getStudent(rindex).getPhoneContacts().length;

                        System.out.println(faculty.getStudent(rindex).getEmailContacts()[posEmail].isNewerThan(faculty.getStudent(rindex).getPhoneContacts()[posPhone]));
                    }

                    break;
                }

                case "PRINT_FACULTY_METHODS": {
                    System.out.println("Faculty: " + faculty.toString());
                    System.out.println("Student with most contacts: " + faculty.getStudentWithMostContacts().toString());
                    break;
                }

            }

        }

        scanner.close();
    }
}

abstract class Contact implements Comparable<Contact> {
    String date;

    public Contact(String date) {
        this.date = date;
    }

    public boolean isNewerThan(Contact c) {
        return this.date.compareTo(c.date) > 0;
    }

    @Override
    public int compareTo(Contact o) {
        return o.date.compareTo(date);
    }

    public abstract String getType();

}

class EmailContact extends Contact {
    String email;

    @Override
    public String getType() {
        return "Email";
    }

    public EmailContact(String date, String email) {
        super(date);
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

}

class PhoneContact extends Contact {
    String phone;
    Operator operator;

    public PhoneContact(String date, String phone) {
        super(date);
        this.phone = phone;
        setOperator();
    }

    private void setOperator() {
        String op = "";
        for (int i = 0; i < 3; i++) {
            op += phone.charAt(i);
        }
        if (op.equals("070") || op.equals("071") || op.equals("072")) operator = Operator.TMOBILE;
        if (op.equals("072") || op.equals("076")) operator = Operator.ONE;
        if (op.equals("077") || op.equals("078")) operator = Operator.VIP;
    }

    @Override
    public String getType() {
        return "Phone";
    }

    public String getPhone() {
        return phone;
    }

    public Operator getOperator() {
        return operator;
    }
}


enum Operator {VIP, ONE, TMOBILE}

class Student {
    String firstName;
    String lastName;
    String city;
    int age;
    long index;
    Contact[] contacts;

    public Student(String firstName, String lastName, String city, int age, long index) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.city = city;
        this.age = age;
        this.index = index;
        this.contacts = new Contact[0];
    }

    public void addEmailContact(String date, String email) {
        EmailContact emailContact = new EmailContact(date, email);
        contacts = Arrays.copyOf(contacts, contacts.length + 1);
        contacts[contacts.length - 1] = emailContact;
    }

    public void addPhoneContact(String date, String phone) {
        PhoneContact phoneContact = new PhoneContact(date, phone);
        contacts = Arrays.copyOf(contacts, contacts.length + 1);
        contacts[contacts.length - 1] = phoneContact;
    }

    public Contact[] getEmailContacts() {
        return getFiltered("Email");
    }

    public Contact[] getFiltered(String type) {
        return Arrays.stream(contacts).filter(contact -> contact.getType().equals(type)).toArray(Contact[]::new);
    }


    public Contact[] getPhoneContacts() {
        return getFiltered("Phone");
    }

    public String getCity() {
        return city;
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }

    public long getIndex() {
        return index;
    }

    public Contact getLatestContact() {
        return Arrays.stream(contacts).sorted().findFirst().orElse(null);
    }
    public int getContactsSize()
    {
        return  contacts.length;
    }

}

class Faculty {
    String name;
    Student[] students;

    public Faculty(String name, Student[] students) {
        this.name = name;
        this.students = students;
    }

    public int countStudentsFromCity(String cityName) {
        return (int) Arrays.stream(students).filter(student -> student.getCity().equals(cityName)).count();
    }

    public Student getStudent(long index) {
        return Arrays.stream(students).filter(student -> student.getIndex() == index).findFirst().orElse(null);
    }

    public double getAverageNumberOfContacts() {
        return Arrays.stream(students).mapToInt(Student::getContactsSize).average().orElse(0);
    }
    public Student getStudentWithMostContacts()
    {
        return  Arrays.stream(students).max(Comparator.comparing(Student::getContactsSize).thenComparing(Student::getIndex)).orElse(null);
    }
}