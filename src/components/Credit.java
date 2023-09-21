package components;

public class Credit extends Flow {
    public Credit(String comment, int identifier, double amount, int targetAccountNumber, boolean effect, String date) {
        super(comment, identifier, amount, targetAccountNumber, effect, date);
    }
    

    @Override
    public String toString() {
        return "Credit{" +
                "comment='" + getComment() + '\'' +
                ", identifier=" + getIdentifier() +
                ", amount=" + getAmount() +
                ", targetAccountNumber=" + getTargetAccountNumber() +
                ", effect=" + isEffect() +
                ", date='" + getDate() + '\'' +
                '}';
    }

}