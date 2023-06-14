package Aud3.DodatniRaboti;

public class AnonymousClass {
   Functional Additiong=new Functional() {
       @Override
       public double doOperationg(double a, double b) {
           return a+b;
       }
   };

    public static void main(String[] args) {

        AnonymousClass example=new AnonymousClass();
        System.out.println(example.Additiong.doOperationg(1,2));
            // ova gore e nepotrebno
            //mozi so anonima koga rabotime so interfejs koj ne e funkciski
            //ako rabotime so anoinma znachi deka nemame funkciski
    }


}
