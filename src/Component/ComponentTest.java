//package Component;

import java.util.*;

public class ComponentTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String name = scanner.nextLine();
        Window window = new Window(name);
        Component prev = null;
        while (true) {
            try {
                int what = scanner.nextInt();
                scanner.nextLine();
                if (what == 0) {
                    int position = scanner.nextInt();
                    window.addComponent(position, prev);
                } else if (what == 1) {
                    String color = scanner.nextLine();
                    int weight = scanner.nextInt();
                    Component component = new Component(color, weight);
                    prev = component;
                } else if (what == 2) {
                    String color = scanner.nextLine();
                    int weight = scanner.nextInt();
                    Component component = new Component(color, weight);
                    prev.addComponent(component);
                    prev = component;
                } else if (what == 3) {
                    String color = scanner.nextLine();
                    int weight = scanner.nextInt();
                    Component component = new Component(color, weight);
                    prev.addComponent(component);
                } else if (what == 4) {
                    break;
                }

            } catch (InvalidPositionException e) {
                System.out.println(e.getMessage());
            }
            scanner.nextLine();
        }

        System.out.println("=== ORIGINAL WINDOW ===");
        System.out.println(window);
        int weight = scanner.nextInt();
        scanner.nextLine();
        String color = scanner.nextLine();
        window.changeColor(weight, color);
        System.out.println(String.format("=== CHANGED COLOR (%d, %s) ===", weight, color));
        System.out.println(window);
        int pos1 = scanner.nextInt();
        int pos2 = scanner.nextInt();
        System.out.println(String.format("=== SWITCHED COMPONENTS %d <-> %d ===", pos1, pos2));
        window.swichComponents(pos1, pos2);
        System.out.println(window);
    }
}

class Component implements Comparable<Component> {
    String colour;
    int weight;
    TreeSet<Component> components;

    public Component(String colour, int weight) {
        this.colour = colour;
        this.weight = weight;
        components = new TreeSet<>();
    }

    public void addComponent(Component component) {
        components.add(component);
    }


    @Override
    public int compareTo(Component o) {
        int val = Integer.compare(weight, o.weight);
        if (val == 0) return colour.compareTo(o.colour);
        return val;
    }

    public void setColour(int weight, String colour) {
        if (this.weight < weight)
            this.colour = colour;
        components.forEach(i -> i.setColour(weight, colour));

    }

    public static void buildString(StringBuilder stringBuilder, Component component, int level) {
        for (Component c : component.components) {
            for (int i = 0; i < level; i++)
                stringBuilder.append("---");
            stringBuilder.append(String.format("%d:%s\n",c.weight,c.colour));
            buildString(stringBuilder,c,level+1);
        }
    }

}

class Window {
    String name;
    TreeMap<Integer, Component> components;

    public Window(String name) {
        this.name = name;
        components = new TreeMap<>();
    }

    public void addComponent(int position, Component component) throws InvalidPositionException {
        checkPosition(position);
        components.put(position, component);
    }

    private void checkPosition(int position) throws InvalidPositionException {
        if (components.containsKey(position))
            throw new InvalidPositionException(String.format("Invalid position %d, alredy taken!", position));
    }

    public void changeColor(int weight, String color) {
        components.values().forEach(i -> i.setColour(weight, color));
    }

    public void swichComponents(int pos1, int pos2) {
        Component c = components.get(pos1);
        Component c2 = components.get(pos2);
        components.put(pos1, c2);
        components.put(pos2, c);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(String.format("WINDOW %s\n", name));
        for (Map.Entry<Integer, Component> c : components.entrySet()) {
            stringBuilder.append(String.format("%d:",c.getKey()));
            stringBuilder.append(String.format("%d:%s\n",c.getValue().weight,c.getValue().colour));
            Component.buildString(stringBuilder,c.getValue(),1);

        }
        return stringBuilder.toString();
    }
}

class InvalidPositionException extends Exception {
    public InvalidPositionException(String msg) {
        super(msg);
    }
}
