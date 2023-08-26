package Patterns.Creational.Singleton;

public class Singleton {

    private static Singleton instance;
    private String data;

    private Singleton(String data) {
        this.data = data;
    }

//    public static Singleton getInstance(String data) {
//                if (instance == null) instance = new Singleton(data);
//
//        return instance;
//    }
    public static Singleton getInstance(String data) {
        if (instance == null)
            synchronized (Singleton.class) {
                if (instance == null) instance = new Singleton(data);
            }
        return instance;
    }
}
