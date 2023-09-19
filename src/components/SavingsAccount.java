package components;

//1.2.2 Creation of the CurrentAccount and SavingsAccount
public class SavingsAccount extends Account {
    public SavingsAccount(String label, Client client) {
        super(label, client);
    }

    @Override
    public void modifyBalance(double amount, String flowType) {
        // Modifier le solde en fonction du type de flux (transfer, credit, debit)
        if ("transfer".equals(flowType)) {
            this.balance -= amount;
        } else if ("credit".equals(flowType)) {
            this.balance += amount;
        } else if ("debit".equals(flowType)) {
            this.balance -= amount;
        }
    }
}
