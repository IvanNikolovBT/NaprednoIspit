package Aud3.BANK;

public class NonInterestCheckingAccount  extends  Account{
    public NonInterestCheckingAccount(String ime, double balance) {
        super(ime, balance);
    }

    public NonInterestCheckingAccount(String ime) {
        super(ime);
    }

    @Override
    public boolean hasInterest() {
        return false;
    }
}
