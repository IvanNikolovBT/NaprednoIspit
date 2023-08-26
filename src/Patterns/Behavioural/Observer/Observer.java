package Patterns.Behavioural.Observer;

public interface Observer {
    void update();

    void subcribeChannel(Channel ch); //register
}
