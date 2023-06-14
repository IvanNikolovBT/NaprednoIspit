package Aud3.Calculator;

public class UnknownOperatorException extends  Exception{

    public UnknownOperatorException(char s) {
        super(String.format("Unkown %s operator ",s));
    }
}
