package Lab1.zad2;

abstract class Transaction {

    long fromID;
    long toID;
    String description;
    String amount;

    public Transaction(long fromID, long toID, String description, String amount) {
        this.fromID = fromID;
        this.toID = toID;
        this.description = description;
        this.amount = amount;
    }

    public long getFromID() {
        return fromID;
    }

    public long getToID() {
        return toID;
    }

    public double getAmountInDouble() {
        return Double.parseDouble(amount.substring(0,amount.length()-1));
    }
    public String getAmount()
    {
        return  amount;
    }
    abstract double getProvision();

    public String getDescription() {
        return description;
    }
}
