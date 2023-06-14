package Aud3.BANK;

public class PlatinumCheckingAccount extends  InterestCheckingAccount implements InterestBearingAccount{
    public PlatinumCheckingAccount(String ime, double balance) {
        super(ime, balance);
    }

    public PlatinumCheckingAccount(String ime) {
        super(ime);
    }

    @Override
    public void addInterest() {
        balance*=(1+2*interestRate);
    }
}
