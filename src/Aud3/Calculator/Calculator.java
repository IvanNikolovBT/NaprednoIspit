package Aud3.Calculator;

public class Calculator {

    private double value;
    private static final char PLUS='+';
    private static final char MINUS='-';
    private static final char MULPTIPLY='*';
    private static final char DIVIDE='/';

    public Calculator()
    {
        value=0;
    }
    public String init() {
        return String.format("result = %f", value);
    }

    public double getValue()
    {
        return value;
    }

    public String execute(char operator, double value) throws UnknownOperatorException {
        if(operator == PLUS)
            this.value+=value;
        else if (operator== MINUS)
            this.value-=value;
        else if (operator == MULPTIPLY)
            this.value*=value;
        else if (operator == DIVIDE)
            this.value/=value;
        else
        {
            throw new UnknownOperatorException(operator);
        }
        return String.format("result %c %f=%f",operator,value,this.value);
        }

}
