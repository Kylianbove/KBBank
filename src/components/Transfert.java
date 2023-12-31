package components;

public class Transfert extends Flow {
    private int sourceAccountNumber;

    public Transfert(String comment, int identifier, double amount, int sourceAccountNumber, int targetAccountNumber, boolean effect, String date) {
        super(comment, identifier, amount, targetAccountNumber, effect, date);
        this.sourceAccountNumber = sourceAccountNumber;
    }

    public void setSourceAccountNumber(int sourceAccountNumber) {
        this.sourceAccountNumber = sourceAccountNumber;
    }

    public int getSourceAccountNumber() {
        return sourceAccountNumber;
    }
    
    @Override
    public String toString() {
        return "Transfert{" +
                "comment='" + getComment() + '\'' +
                ", identifier=" + getIdentifier() +
                ", amount=" + getAmount() +
                ", sourceAccountNumber=" + getSourceAccountNumber() +
                ", targetAccountNumber=" + getTargetAccountNumber() +
                ", effect=" + isEffect() +
                ", date='" + getDate() + '\'' +
                '}';
    }

}
