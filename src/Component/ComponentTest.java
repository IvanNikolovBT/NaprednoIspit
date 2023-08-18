package Component;

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
                } else if(what == 4) {
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
class Component implements Comparable<Component>
{
    String colour;
    int weight;
    Set<Component> inner;

    public Component(String colour, int weight) {
        this.colour = colour;
        this.weight = weight;
        inner=new TreeSet<>();
    }
    public void addComponent(Component component)
    {
    inner.add(component);
    }

    public void setColour(String colour) {
        this.colour = colour;
    }
    public void changeColor(int weight, String color)
    {
        if(this.weight<weight)
            this.colour=color;
        for(Component component:inner)
            change(component,weight,color);
    }
    @Override
    public int compareTo(Component o) {
        int val=weight-o.weight;
        if(val==0)
            return colour.compareTo(o.colour);
        return val;
    }
    static void createString(StringBuilder line, Component component, int val)
    {
        for(int i=0;i<val;i++)
            line.append("---");
        line.append(String.format("%d:%s\n",component.weight,component.colour));
        for(Component c:component.inner)
            createString(line,component,val+1);
    }
    static void change(Component component,int weight,String color)
    {
        if(component.weight<weight)
            component.colour=color;
        for(Component c:component.inner)
            change(c,weight,color);
    }
}
class Window
{
    String name;
    Map<Integer,Component> componentMap;
    public Window(String name) {
        this.name = name;
        componentMap=new TreeMap<Integer, Component>();
    }
    void addComponent(int position, Component component) throws InvalidPositionException {
        if(componentMap.containsKey(position))
            throw new InvalidPositionException(position);
        componentMap.put(position,component);
    }
    public void changeColor(int weight,String color)
    {
        for(Component c:componentMap.values())
            c.changeColor(weight,color);
    }
    public  void  swichComponents(int pos1,int pos2)
    {
    Component c1=componentMap.get(pos1);
    Component c2=componentMap.get(pos2);
    componentMap.put(pos1,c2);
    componentMap.put(pos2,c1);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder=new StringBuilder();
        stringBuilder.append(String.format("WINDOW %s",name));
        for(Map.Entry<Integer,Component> entry: componentMap.entrySet())
        {
            stringBuilder.append(String.format("%d:",entry.getKey()));
            Component.createString(stringBuilder,entry.getValue(),0);
        }
        return  stringBuilder.toString();
    }
}
class InvalidPositionException extends Exception
{
    public InvalidPositionException(int pos)
    {
        super(String.format("Invalid position ,already taken",pos));
    }
}