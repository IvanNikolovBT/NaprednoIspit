package PhonebookTester;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.*;

public class PhonebookTester {

    public static void main(String[] args) throws Exception {
        Scanner jin = new Scanner(System.in);
        String line = jin.nextLine();
        switch (line) {
            case "test_contact":
                testContact(jin);
                break;
            case "test_phonebook_exceptions":
                testPhonebookExceptions(jin);
                break;
            case "test_usage":
                testUsage(jin);
                break;
        }
    }

    private static void testFile(Scanner jin) throws Exception {
        PhoneBook phonebook = new PhoneBook();
        while (jin.hasNextLine())
            phonebook.addContact(new Contact(jin.nextLine(), jin.nextLine().split("\\s++")));
        String text_file = "phonebook.txt";
        PhoneBook.saveAsTextFile(phonebook, text_file);
        PhoneBook pb = PhoneBook.loadFromTextFile(text_file);
        if (!pb.equals(phonebook)) System.out.println("Your file saving and loading doesn't seem to work right");
        else System.out.println("Your file saving and loading works great. Good job!");
    }

    private static void testUsage(Scanner jin) throws Exception {
        PhoneBook phonebook = new PhoneBook();
        while (jin.hasNextLine()) {
            String command = jin.nextLine();
            switch (command) {
                case "add":
                    phonebook.addContact(new Contact(jin.nextLine(), jin.nextLine().split("\\s++")));
                    break;
                case "remove":
                    phonebook.removeContact(jin.nextLine());
                    break;
                case "print":
                    System.out.println(phonebook.numberOfContacts());
                    System.out.println(Arrays.toString(phonebook.getContacts()));
                    System.out.println(phonebook.toString());
                    break;
                case "get_name":
                    System.out.println(phonebook.getContactForName(jin.nextLine()));
                    break;
                case "get_number":
                    System.out.println(Arrays.toString(phonebook.getContactsForNumber(jin.nextLine())));
                    break;
            }
        }
    }

    private static void testPhonebookExceptions(Scanner jin) {
        PhoneBook phonebook = new PhoneBook();
        boolean exception_thrown = false;
        try {
            while (jin.hasNextLine()) {
                phonebook.addContact(new Contact(jin.nextLine()));
            }
        } catch (InvalidNameException e) {
            System.out.println(e.name);
            exception_thrown = true;
        } catch (Exception e) {
        }
        if (!exception_thrown) System.out.println("Your addContact method doesn't throw InvalidNameException");
        /*
		exception_thrown = false;
		try {
		phonebook.addContact(new Contact(jin.nextLine()));
		} catch ( MaximumSizeExceddedException e ) {
			exception_thrown = true;
		}
		catch ( Exception e ) {}
		if ( ! exception_thrown ) System.out.println("Your addContact method doesn't throw MaximumSizeExcededException");
        */
    }

    private static void testContact(Scanner jin) throws Exception {
        boolean exception_thrown = true;
        String names_to_test[] = {"And\nrej", "asd", "AAAAAAAAAAAAAAAAAAAAAA", "Ð�Ð½Ð´Ñ€ÐµÑ˜A123213", "Andrej#", "Andrej<3"};
        for (String name : names_to_test) {
            try {
                new Contact(name);
                exception_thrown = false;
            } catch (InvalidNameException e) {
                exception_thrown = true;
            }
            if (!exception_thrown) System.out.println("Your Contact constructor doesn't throw an InvalidNameException");
        }
        String numbers_to_test[] = {"+071718028", "number", "078asdasdasd", "070asdqwe", "070a56798", "07045678a", "123456789", "074456798", "073456798", "079456798"};
        for (String number : numbers_to_test) {
            try {
                new Contact("Andrej", number);
                exception_thrown = false;
            } catch (InvalidNumberException e) {
                exception_thrown = true;
            }
            if (!exception_thrown)
                System.out.println("Your Contact constructor doesn't throw an InvalidNumberException");
        }
        String nums[] = new String[10];
        for (int i = 0; i < nums.length; ++i) nums[i] = getRandomLegitNumber();
        try {
            new Contact("Andrej", nums);
            exception_thrown = false;
        } catch (MaximumSizeExceddedException e) {
            exception_thrown = true;
        }
        if (!exception_thrown)
            System.out.println("Your Contact constructor doesn't throw a MaximumSizeExceddedException");
        Random rnd = new Random(5);
        Contact contact = new Contact("Andrej", getRandomLegitNumber(rnd), getRandomLegitNumber(rnd), getRandomLegitNumber(rnd));
        System.out.println(contact.getName());
        System.out.println(Arrays.toString(contact.getNumbers()));
        System.out.println(contact.toString());
        contact.addNumber(getRandomLegitNumber(rnd));
        System.out.println(Arrays.toString(contact.getNumbers()));
        System.out.println(contact.toString());
        contact.addNumber(getRandomLegitNumber(rnd));
        System.out.println(Arrays.toString(contact.getNumbers()));
        System.out.println(contact.toString());
    }

    static String[] legit_prefixes = {"070", "071", "072", "075", "076", "077", "078"};
    static Random rnd = new Random();

    private static String getRandomLegitNumber() {
        return getRandomLegitNumber(rnd);
    }

    private static String getRandomLegitNumber(Random rnd) {
        StringBuilder sb = new StringBuilder(legit_prefixes[rnd.nextInt(legit_prefixes.length)]);
        for (int i = 3; i < 9; ++i)
            sb.append(rnd.nextInt(10));
        return sb.toString();
    }


}

class Contact implements  Comparable<Contact>{
    String name;
    String[] numbers;
    int present;

    public Contact(String name, String... phonenumber) throws InvalidNameException, InvalidNumberException, MaximumSizeExceddedException {
        checkName(name);
        this.name = name;
        numbers = new String[5];

        present = 0;
        for (String number : phonenumber) {
            checkNumber(number);
            addNumber(number);
        }

    }

    private static void checkName(String name) throws InvalidNameException {
        if (name.length() < 4 || name.length() > 10)
            throw new InvalidNameException();
        for (Character c : name.toCharArray()) {
            if (!Character.isLetterOrDigit(c))
                throw new InvalidNameException();
        }
    }

    public String getName() {
        return name;
    }

    public String[] getNumbers() {
        String[] array = Arrays.copyOf(numbers, present);
        Arrays.sort(array);
        return array;
    }

    private void checkNumber(String number) throws InvalidNumberException {
        if (number.length() != 9)
            throw new InvalidNumberException();
        List<String> allowed = new ArrayList<>();
        allowed.add("070");
        allowed.add("071");
        allowed.add("072");
        allowed.add("075");
        allowed.add("076");
        allowed.add("077");
        allowed.add("078");
        String numberCut = number.substring(0, 3);
        if (!allowed.contains(numberCut))
            throw new InvalidNumberException();
    }

    public void addNumber(String number) throws InvalidNumberException, MaximumSizeExceddedException {
        checkNumber(number);
        if (present == 5)
            throw new MaximumSizeExceddedException();
        numbers[present++] = number;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(name).append("\n");
        stringBuilder.append(present).append("\n");
        String[] numbrs = getNumbers();
        for (int i = 0; i < present; i++)
            stringBuilder.append(numbrs[i]).append("\n");
        return stringBuilder.toString();
    }

    static public Contact valueOf(String s) throws InvalidFormatException {
        String[] splitter = s.split("\n");
        try {
            return new Contact(splitter[0], Arrays.copyOfRange(splitter, 2, splitter.length));
        } catch (MaximumSizeExceddedException | InvalidNumberException | InvalidNameException e) {
            throw new InvalidFormatException();
        }
    }

    @Override
    public int compareTo(Contact o) {
        return name.compareTo(o.name);
    }
}

class PhoneBook {
    Contact[] contacts;
    int numberOfContacts;
    int MAX = 250;

    public PhoneBook() {
        contacts = new Contact[MAX];
        numberOfContacts = 0;
    }

    public void addContact(Contact contact) throws MaximumSizeExceddedException, InvalidNameException {
        if (numberOfContacts == MAX)
            throw new MaximumSizeExceddedException();
        for (int i = 0; i < numberOfContacts; i++) {
            if (contacts[i].name.equals(contact.name))
                throw new InvalidNameException();
        }
        contacts[numberOfContacts++] = contact;
    }

    public int numberOfContacts() {
        return numberOfContacts;
    }

    public Contact getContactForName(String name) {
        int INDEX = indexOf(name);
        if (INDEX != -1)
            return contacts[INDEX];
        return null;
    }

    private int indexOf(String name) {
        for (int i = 0; i < numberOfContacts; i++) {
            if (contacts[i].name.equals(name))
                return i;

        }
        return -1;
    }

    public boolean removeContact(String name) {
    int INDEX=indexOf(name);
    if(INDEX==-1)
        return false;
    contacts[INDEX]=contacts[numberOfContacts-1];
    contacts[numberOfContacts--]=null;
    return true;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder=new StringBuilder();
        Contact[]contacts1=Arrays.copyOf(contacts,numberOfContacts);
        Arrays.sort(contacts1);
        for(int i=0;i<numberOfContacts;i++)
            stringBuilder.append(contacts1[i]).append("\n");
        return  stringBuilder.toString();
    }
    public static boolean saveAsTextFile(PhoneBook phoneBook,String path)
    {
        try {
            PrintWriter printWriter=new PrintWriter(new File(path));
            printWriter.println(phoneBook.toString());
        } catch (FileNotFoundException e) {
            return  false;
        }
        return true;


    }

    public static PhoneBook loadFromTextFile(String path) throws IOException,InvalidFormatException {
        try (Scanner jin  =
                     new Scanner(new File(path))) {
            PhoneBook res = new PhoneBook();
            StringBuilder sb = new StringBuilder();
            while ( jin.hasNextLine() ) {
                String line = jin.nextLine();
                if ( line.length() == 0 ) {
                    if ( sb.toString().length() > 1 )
                        res.addContact(Contact.valueOf(sb.toString()));
                    sb = new StringBuilder();
                }
                else {
                    sb.append(line).append('\n');
                }

            }
            return res;
        }
        catch (InvalidNameException|MaximumSizeExceddedException e) {
            throw new InvalidFormatException();
        }
    }
}

class InvalidNameException extends Exception {
    public InvalidNameException() {
        super();
    }
}

class InvalidNumberException extends Exception {
    public InvalidNumberException() {
        super();
    }
}

class MaximumSizeExceddedException extends Exception {
    public MaximumSizeExceddedException() {
        super();
    }
}

class InvalidFormatException extends Exception {
    public InvalidFormatException() {
        super();
    }
}