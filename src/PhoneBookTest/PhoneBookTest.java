//package PhoneBookTest;

import java.util.*;
import java.util.stream.Collectors;

public class PhoneBookTest {

    public static void main(String[] args) {
        PhoneBook phoneBook = new PhoneBook();
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        scanner.nextLine();
        for (int i = 0; i < n; ++i) {
            String line = scanner.nextLine();
            String[] parts = line.split(":");
            try {
                phoneBook.addContact(parts[0], parts[1]);
            } catch (DuplicateNumberException e) {
                System.out.println(e.getMessage());
            }
        }
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            System.out.println(line);
            String[] parts = line.split(":");
            if (parts[0].equals("NUM")) {
                phoneBook.contactsByNumber(parts[1]);
            } else {
                phoneBook.contactsByName(parts[1]);
            }
        }
    }

}
class Contact
{
    String name;
    String number;

    public Contact(String name, String number) {
        this.name = name;
        this.number = number;
    }

    @Override
    public String toString() {
        return String.format("%s %s",name,number);
    }

    public String getName() {
        return name;
    }

    public String getNumber() {
        return number;
    }
}
class PhoneBook
{
    Set<Contact> contacts;

    public PhoneBook() {
        this.contacts = new HashSet<>();
    }
    public void addContact(String name,String number) throws DuplicateNumberException {
        checkException(number);
        contacts.add(new Contact(name,number));
    }
    public void checkException(String number) throws DuplicateNumberException {
        for(Contact c:contacts)
        {
            if(c.number.equals(number))
                throw new DuplicateNumberException(String.format(" Duplicate number: %s",number));
        }
    }
    public void contactsByNumber(String number)
    {
        List<Contact> check= contacts.stream().filter(i->i.number.contains(number)).collect(Collectors.toList());
        if(check.size()!=0)
        contacts.stream().filter(i->i.number.contains(number)).sorted(Comparator.comparing(Contact::getName).thenComparing(Contact::getNumber)).forEach(System.out::println);
        else
            System.out.println("NOT FOUND");
    }
    public void contactsByName(String name)
    {
        List<Contact> check= contacts.stream().filter(i->i.name.equals(name)).collect(Collectors.toList());
        if(check.size()!=0)
        contacts.stream().filter(i->i.name.equals(name)).sorted(Comparator.comparing(Contact::getName).thenComparing(Contact::getNumber)).forEach(System.out::println);
        else
            System.out.println("NOT FOUND");
    }
}

class DuplicateNumberException  extends  Exception
{

    public DuplicateNumberException(String msg)
    {
        super(msg);
    }
}
