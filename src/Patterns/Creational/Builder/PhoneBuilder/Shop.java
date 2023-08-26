package Patterns.Creational.Builder.PhoneBuilder;

public class Shop {

    public static void main(String[] args) {
        PhoneBuilder phoneBuilder=new PhoneBuilder();

        phoneBuilder.setBattery(31);
        phoneBuilder.setOs("Andorid");
        phoneBuilder.setProccesor("nvida");
        phoneBuilder.setScreenSize(21.2);
        phoneBuilder.setRam(4);
        System.out.println(phoneBuilder.getPhone());
        Phone phone=phoneBuilder.getPhone();
        System.out.println(phone);
    }
}
