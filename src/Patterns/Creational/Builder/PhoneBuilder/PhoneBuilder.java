package Patterns.Creational.Builder.PhoneBuilder;

public class PhoneBuilder {

    private String os;
    private int ram;
    private String proccesor;
    private double screenSize;
    private int battery;

    public PhoneBuilder() {

    }

    public PhoneBuilder setOs(String os) {
        this.os = os;
        return this;
    }

    public PhoneBuilder setRam(int ram) {
        this.ram = ram;
        return this;
    }

    public PhoneBuilder setProccesor(String proccesor) {
        this.proccesor = proccesor;
        return this;
    }

    public PhoneBuilder setScreenSize(double screenSize) {
        this.screenSize = screenSize;
        return this;
    }

    public PhoneBuilder setBattery(int battery) {
        this.battery = battery;
        return this;
    }

    public Phone getPhone()
    {
        return new Phone(os,ram,proccesor,screenSize,battery);
    }
}
