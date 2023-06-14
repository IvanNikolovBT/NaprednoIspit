package Aud3.BANK;

public class InterestCheckingAccount extends  Account implements  InterestBearingAccount{

    static double interestRate=0.03;
    public InterestCheckingAccount(String ime, double balance) {
        super(ime, balance);
    }

    public InterestCheckingAccount(String ime) {
        super(ime);
    }

    @Override
    public boolean hasInterest() {
        return true;
    }

    @Override
    public void addInterest() {
    balance*=(1+interestRate);
    }
}
