package Patterns.Structural.Composite;

public class CompositeTest {

    public static void main(String[] args) {
        Component hd=new Leaf(4000,"hardrive");
        Component mouse=new Leaf(100,"mouse");
        Component mointor=new Leaf(3000,"monitor");
        Component ram=new Leaf(5000,"RAM");
        Component cpu=new Leaf(6000,"CPU");


        Composite ph=new Composite("Peri");
        Composite cabinet=new Composite("Cabinet");
        Composite MB=new Composite("Motherboard");
        Composite computer=new Composite("Computer");

        MB.addComponent(ram);
        MB.addComponent(cpu);

        ph.addComponent(mointor);
        ph.addComponent(mouse);

        cabinet.addComponent(hd);
        cabinet.addComponent(MB);

        computer.addComponent(cabinet);
        computer.addComponent(ph);

        computer.showPrice();
    }


}
