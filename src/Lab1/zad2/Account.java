package Lab1.zad2;

public class Account {
    String ime;
    long id;
    String balance;

    public Account(String ime, String balance) {
        this.ime = ime;
        this.balance = balance;
    }

    public String getIme() {
        return ime;
    }

    public long getId() {
        return id;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }
    public double getBalanceInDouble()
    {
        return Double.parseDouble(getBalance().substring(0,balance.length()-1));
    }
    @Override
    public String toString() {
        return String.format("Name:%s\nBalance:%s$\n",ime,balance);
    }

}
