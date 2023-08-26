package Patterns.Behavioural.State;

public class Phone {


    private State state;

    public Phone()
    {
        state=new OffState(this);
    }

    public void setState(State state)
    {
        this.state=state;
    }
    public String lock()
    {
        return "locking phone";
    }
    public String home()
    {
        return "going home";
    }
    public String unlock()
    {
        return "unlocking phone";
    }
    public String turnOn()
    {
        return "Turning on screen, device locked";
    }

}
