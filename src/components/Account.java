package components;
import components.Flow;

//1.2.1 Creation of the account class
public abstract class Account {
    protected String label;
    protected double balance;
    protected int accountNumber;
    protected Client client;
    // Compteur statique pour générer automatiquement les numéros de compte
    private static int accountCounter = 1;

    public Account(String label, Client client) {
        this.label = label;
        this.client = client;
        this.accountNumber = generateAccountNumber();
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(Flow flow) {
        if (flow instanceof Credit) {
            Credit credit = (Credit) flow;
            if (credit.isEffect()) {
                // Si c'est un crédit effectif, ajoutez le montant au solde
                this.balance += credit.getAmount();
            }
        } else if (flow instanceof Debit) {
            Debit debit = (Debit) flow;
            if (debit.isEffect()) {
                // Si c'est un débit effectif, soustrayez le montant du solde
                this.balance -= debit.getAmount();
            }
        } else if (flow instanceof Transfert) {
            Transfert transfert = (Transfert) flow;
            if (transfert.isEffect()) {
                if (transfert.getSourceAccountNumber() == this.accountNumber) {
                    // Si c'est un transfert émis depuis ce compte, soustrayez le montant du solde
                    this.balance -= transfert.getAmount();
                } else if (transfert.getTargetAccountNumber() == this.accountNumber) {
                    // Si c'est un transfert reçu sur ce compte, ajoutez le montant au solde
                    this.balance += transfert.getAmount();
                }
            }
        }
    }
    public void setBalance(double balance) {
        this.balance = balance;
    }

    public int getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(int accountNumber) {
        this.accountNumber = accountNumber;
    }



    @Override
    public String toString() {
        return "Account [Label=" + label + ", Balance=" + balance + ", Account Number=" + accountNumber
                + ", Client=" + client + "]";
    }

    // Méthode pour générer automatiquement le numéro de compte
    private static int generateAccountNumber() {
    	// Utilisation du compteur statique pour générer un numéro de compte unique
        int accountNumber = accountCounter;
        accountCounter++;
        return accountNumber;
    }
}
