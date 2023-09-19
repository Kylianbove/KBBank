package components;
import components.Flow;

//1.2.2 Creation of the CurrentAccount and SavingsAccount
public class CurrentAccount extends Account {
    public CurrentAccount(String label, Client client) {
        super(label, client);
    }

    @Override
    public void modifyBalance(Flow flow) {
        // Modifier le solde en fonction du type de flux (transfer, credit, debit)
    	if (flow instanceof Transfert) {
            Transfert transfert = (Transfert) flow;
            if (transfert.isEffect()) {
                // Si le transfert est effectif, mettez à jour le solde
                double amount = transfert.getAmount();
                this.balance -= amount;
            }
        } else if (flow instanceof Debit) {
            Debit debit = (Debit) flow;
            if (debit.isEffect()) {
                // Si le débit est effectif, mettez à jour le solde
                double amount = debit.getAmount();
                this.balance -= amount;
            }
        } else if (flow instanceof Credit) {
            Credit credit = (Credit) flow;
            if (credit.isEffect()) {
                // Si le crédit est effectif, mettez à jour le solde
                double amount = credit.getAmount();
                this.balance += amount;
            }
        }
    }
}
