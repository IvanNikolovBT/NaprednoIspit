package Aud11;

import java.util.ArrayList;
import java.util.List;

class Printer extends Thread
{
   int numbberToPrint;

   public Printer(int numberToPrint)
   {
       this.numbberToPrint=numberToPrint;
   }

    @Override
    public void run() {
        try {
            Thread.sleep(numbberToPrint*100);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println(numbberToPrint);

    }
}

public class PrintingWithThreadsTest {

    public static void main(String[] args) {
    List<Thread> threadList=new ArrayList<>();
    int n=100;
    for(int i=1;i<=n;i++) {
        int finalI = i;
        threadList.add(new Printer(i));
    }
    threadList.forEach(Thread::start);
        for (Thread thread : threadList) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
