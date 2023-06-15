package Lab1.zad2;

import java.util.Objects;

public class FlatAmountProvisionTransaction extends Transaction {

    private String flatProvision;
    public FlatAmountProvisionTransaction(long fromID, long toID, String amount, String flatProvision) {
        super(fromID, toID, "FlatAmountProvisionTransaction", amount);
        this.flatProvision=flatProvision;
    }

    @Override
    double getProvision() {
        return Double.parseDouble(flatProvision.substring(0,flatProvision.length()-1));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FlatAmountProvisionTransaction that = (FlatAmountProvisionTransaction) o;
        return Objects.equals(flatProvision, that.flatProvision);
    }

    @Override
    public int hashCode() {
        return Objects.hash(flatProvision);
    }
}
