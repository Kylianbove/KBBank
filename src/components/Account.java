package components;

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

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public int getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(int accountNumber) {
        this.accountNumber = accountNumber;
    }

    // Méthode abstraite pour modifier le solde en fonction du type de flux
    public abstract void modifyBalance(double amount, String flowType);

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
