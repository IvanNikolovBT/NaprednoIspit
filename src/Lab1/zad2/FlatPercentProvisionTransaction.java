package Lab1.zad2;

import java.util.Objects;

public class FlatPercentProvisionTransaction extends  Transaction {
    int centsPerDolar;

    public FlatPercentProvisionTransaction(long fromID, long toID, String amount, int centsPerDolar) {
        super(fromID, toID, "FlatPercentProvisionTransaction", amount);
        this.centsPerDolar = centsPerDolar;
    }
    @Override
    public double getProvision() {
        return (centsPerDolar/100)*Double.parseDouble(getAmount().substring(0,amount.length()-1));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FlatPercentProvisionTransaction that = (FlatPercentProvisionTransaction) o;
        return centsPerDolar == that.centsPerDolar;
    }

    @Override
    public int hashCode() {
        return Objects.hash(centsPerDolar);
    }
}
