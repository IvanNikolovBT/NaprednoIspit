package Aud4Pak.OldestPerson;

public class Person implements  Comparable<Person> {
    String name;
    int age;

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }
    public Person(String line)
    {
        String[] splitter=line.split("\\s+");
        name=splitter[0];
        age=Integer.parseInt(splitter[1]);
    }

    @Override
    public int compareTo(Person o) {
        return Integer.compare(age,o.age);
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
