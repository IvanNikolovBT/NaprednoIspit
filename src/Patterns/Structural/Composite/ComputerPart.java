package Patterns.Structural.Composite;

import java.util.ArrayList;
import java.util.List;

interface Component {

    void showPrice();

    int getPrice();
}

class Leaf implements Component {

    int price;
    String name;

    public Leaf(int price, String name) {
        this.price = price;
        this.name = name;
    }

    @Override
    public void showPrice() {
        System.out.println(name+" : "+price);
    }

    @Override
    public int getPrice() {
        return price;
    }
}

class Composite implements Component {

    String name;
    List<Component> components = new ArrayList<>();

    public Composite(String name) {
        this.name = name;
    }

    public void addComponent(Component c)
    {
        components.add(c);
    }
    @Override
    public void showPrice() {
        System.out.println(name);
        for(Component c:components)
            c.showPrice();

    }

    @Override
    public int getPrice() {
        return 0;
    }
}