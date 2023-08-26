package Patterns.Behavioural.Observer;

public class Youtube {

    public static void main(String[] args) {
        Channel telusko=new Channel();
        Subscriber s1=new Subscriber("Prv");
        Subscriber s2=new Subscriber("Vtor");
        Subscriber s3=new Subscriber("Tret");
        Subscriber s4=new Subscriber("Chetvrt");

        telusko.subscribe(s1);
        telusko.subscribe(s2);
        telusko.subscribe(s3);
        telusko.subscribe(s4);

        s1.subcribeChannel(telusko);
        s2.subcribeChannel(telusko);
        s3.subcribeChannel(telusko);
        s4.subcribeChannel(telusko);
        telusko.upload("Observer");

        System.out.println("\nemtpy line\n");

        telusko.unSubscribe(s1);
        s2.subcribeChannel(telusko);
        s3.subcribeChannel(telusko);
        s4.subcribeChannel(telusko);
        telusko.upload("unsubscribed");


    }
}
