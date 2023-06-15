package Lab1.zad2;

public class Bank {

    String name;
    Account[] accounts;
    double transferTotal;
    double provistionTotal;

    public Bank(String name, Account[] accounts) {
        this.name = name;
        this.accounts = accounts;
        transferTotal = 0;
        provistionTotal = 0;
    }

    public boolean makeTransaction(Transaction t) {
        int from = 0;
        int to = 0;
        int brojac = 0;
        for (int i = 0; i < accounts.length; i++) {
            if (accounts[i].getId() == t.fromID) {
                from = i;
                brojac++;
            }
            if (accounts[i].getId() == t.fromID) {
                to = i;
                brojac++;
            }
            if (brojac == 2)
                break;
        }
        if (brojac != 2)
            return false;
        double current=accounts[from].getBalanceInDouble();
        if (current < t.getAmountInDouble() + t.getProvision())
            return false;
        current-=t.getAmountInDouble() + t.getProvision();
        accounts[from].setBalance(String.format("%.2f$",current));
        accounts[from].setBalance(String.format("%.2f$",accounts[to].getBalanceInDouble()+t.getAmountInDouble()));
        provistionTotal+=t.getProvision();
        transferTotal+=t.getAmountInDouble();
        return true;
    }

    public double totalTransfers()
    {
        return transferTotal;
    }
    public double totalProvision()
    {
        return  provistionTotal;
    }
    public Account[] getAccounts()
    {
        return accounts;
    }
}
