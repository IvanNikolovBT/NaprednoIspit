package Aud3.BANK;
import java.util.ArrayList;
public class Bank {

    ArrayList<Account> accounts=new ArrayList<Account>();
    int max;
    int totalAccounts;
    String ime;
    public Bank(String ime) {
        this.totalAccounts = 0;
        this.ime=ime;
    }

    public double totalAssets() {
        double sum = 0;
        for (Account account : accounts)
            sum += account.getBalance();

        return sum;
    }

    public void addInterest() {
        for (Account account : accounts) {

            if (account instanceof InterestBearingAccount) {
                InterestBearingAccount iba = (InterestBearingAccount) account;
                iba.addInterest();
            }
        }
    }

    public void addAccount(Account account) {
       accounts.add(account);
    }

    @Override
    public String toString() {
        return "Bank{" +
                "accounts=" + accounts +
                ", max=" + max +
                ", totalAccounts=" + totalAccounts +
                ", ime='" + ime + '\'' +
                '}';
    }
}
