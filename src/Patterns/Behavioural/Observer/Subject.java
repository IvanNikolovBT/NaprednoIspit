package Patterns.Behavioural.Observer;

public interface Subject {
    void subscribe(Subscriber s);  //register

    void unSubscribe(Subscriber s); //unregister

    void notifySubs(); //notify

    void upload(String title);  //akcijata sho aktivira notify
}
