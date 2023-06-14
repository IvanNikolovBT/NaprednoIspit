package Aud4.OldestPerson;

public class Person implements Comparable<Person> {
    private String name;
    private int age;

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public int compareTo(Person other) {
        return Integer.compare(age,other.age);
    }
    public Person(String s)
    {
        this.name=s.split("\\s+")[0];
        this.age=Integer.parseInt(s.split("\\s+")[1]);
    }
    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
