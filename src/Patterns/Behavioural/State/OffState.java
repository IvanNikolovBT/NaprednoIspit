package Patterns.Behavioural.State;

public class OffState  extends  State{
    public OffState(Phone phone)
    {
        super(phone);
    }

    @Override
    public String onHome() {
        phone.setState(new LockedState(phone));
        return phone.home();
    }

    @Override
    public String onOffon() {
        phone.setState(new LockedState(phone));
        return phone.turnOn();
    }
}
