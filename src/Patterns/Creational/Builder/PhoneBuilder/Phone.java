package Patterns.Creational.Builder.PhoneBuilder;

public class Phone {

    private String os;
    private int ram;
    private String proccesor;
    private double screenSize;
    private int battery;

    public Phone(String os, int ram, String proccesor, double screenSize, int battery) {
        this.os = os;
        this.ram = ram;
        this.proccesor = proccesor;
        this.screenSize = screenSize;
        this.battery = battery;
    }

    @Override
    public String toString() {
        return "Phone{" +
                "os='" + os + '\'' +
                ", ram=" + ram +
                ", proccesor='" + proccesor + '\'' +
                ", screenSize=" + screenSize +
                ", battery=" + battery +
                '}';
    }
}
