package components;

public class Debit extends Flow {
    public Debit(String comment, int identifier, double amount, int targetAccountNumber, boolean effect, String date) {
        super(comment, identifier, amount, targetAccountNumber, effect, date);
    }
    
    @Override
    public String toString() {
        return "Debit{" +
                "comment='" + getComment() + '\'' +
                ", identifier=" + getIdentifier() +
                ", amount=" + getAmount() +
                ", targetAccountNumber=" + getTargetAccountNumber() +
                ", effect=" + isEffect() +
                ", date='" + getDate() + '\'' +
                '}';
    }

}