package Patterns.Behavioural.Observer;

public class Subscriber implements Observer {

    String name;
    Channel channel=new Channel();

    public Subscriber(String name) {
        this.name = name;
    }

    @Override
    public void update()
    {
        System.out.println("Video uploaded"+name+channel.getTitle());
    }
    @Override
    public  void subcribeChannel(Channel ch)
    {
        channel=ch;
    }

}


