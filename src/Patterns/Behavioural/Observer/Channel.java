package Patterns.Behavioural.Observer;

import java.util.ArrayList;
import java.util.List;

public class Channel implements Subject {
    private List<Subscriber> subs=new ArrayList<>();
    private String title;

    @Override
    public void subscribe(Subscriber s)
    {
        subs.add(s);
    }
    @Override
    public  void unSubscribe(Subscriber s)
    {
        subs.remove(s);
    }
    @Override
    public void notifySubs()
    {
        for(Subscriber s:subs)
        {
            s.update();
        }
    }
    @Override
    public  void upload(String title)
    {
        this.title=title;
        notifySubs();
    }

    public String getTitle() {
        return title;
    }
}
