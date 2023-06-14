package Aud3.BANK;

public abstract class Account {

    String ime;
    int number;
    double balance;
    static int NUMBER=1000;

    public Account(String ime, double balance) {
        this.ime = ime;
        number=NUMBER++;
        this.balance = balance;
    }

    public Account(String ime) {
        this.ime = ime;
        number=NUMBER++;
        this.balance = 0;
    }
    public double getBalance()
    {
        return  balance;
    }
    public void withdraw(double amount) throws NotEnoughFundsException {
        if(amount>balance)
            throw new NotEnoughFundsException(amount);
        else balance-=amount;
    }
    public void deposit(double amount)
    {
        balance+=amount;
    }
    public abstract boolean  hasInterest();
}
