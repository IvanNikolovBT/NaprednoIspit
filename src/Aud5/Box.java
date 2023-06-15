package Aud5;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Box <T>{

    List<T> lista;

    public Box() {
        lista=new ArrayList<>();
    }
    public void add(T item)
    {
        lista.add(item);
    }
    public boolean isEmpty()
    {
        return lista.size()==0;
    }
    public T drawItem()
    {
        if(isEmpty())
            return null;
        Random random=new Random();
        return lista.get(random.nextInt(lista.size()));
    }

    public static void main(String[] args) {

        Box<String> stringBox=new Box<>();
        stringBox.add("Korle");
        stringBox.add("Marko");
        stringBox.add("Antonio");
        stringBox.add("Ivan");
        stringBox.add("Stefan");
        System.out.println(stringBox.drawItem());
        System.out.println(stringBox.drawItem());
        System.out.println(stringBox.drawItem());
        Box<Integer> intBox=new Box<>();
        intBox.add(2);
        intBox.add(1);
        intBox.add(3);
        intBox.add(4);
        intBox.add(5);
        System.out.println(intBox.drawItem());
        System.out.println(intBox.drawItem());
        System.out.println(intBox.drawItem());

    }




}
