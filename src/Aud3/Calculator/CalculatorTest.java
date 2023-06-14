package Aud3.Calculator;

import java.util.Scanner;

public class CalculatorTest {
    static final char RESULT='r';

    public static void main(String[] args) {
        Scanner scanner=new Scanner(System.in);
        while(true)
        {
            Calculator calculator=new Calculator();
            System.out.println(calculator.init());
            while(true)
            {
                String line=scanner.nextLine();
                char choice = getCharLower(line);
                if (choice== RESULT)
                {
                    System.out.println(String.format("final result = %f",calculator.getValue()));
                    break;
                }
                String [] parts=line.split("\\s+");
                char operator=parts[0].charAt(0);
                double value= Double.parseDouble(parts[1]);
                try {
                    String result = calculator.execute(operator, value);
                    System.out.println(result);
                    System.out.println(calculator);
                } catch (UnknownOperatorException e) {
                    System.out.println(e.getMessage());
                }
            }
            System.out.println("(Y/N)");
            String line = scanner.nextLine();
            char choice = getCharLower(line);
            if (choice == 'n') {
                break;
            }
            }
        }

    static char getCharLower(String line)
    {
        if(line.trim().length() >0)
        {
            return Character.toLowerCase(line.charAt(0));
        }
        return '?';
    }
}
