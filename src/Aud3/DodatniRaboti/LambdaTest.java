package Aud3.DodatniRaboti;

public class LambdaTest {


    public static void main(String[] args) {
        Functional functional1=(a,b)->{
            System.out.println("vleze");

            return a+b;

        };
        Functional functional2=(x,y)-> x*y;
        System.out.println(functional1.doOperationg(1,5));
        System.out.println(functional2.doOperationg(1,3));
        //lambda za funkciski
    }
}
