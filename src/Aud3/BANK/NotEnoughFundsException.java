package Aud3.BANK;

public class NotEnoughFundsException extends  Exception{
    public NotEnoughFundsException(double amount) {
        super(String.format("You dont have enough %.2f$ on your account",amount));
    }
}
