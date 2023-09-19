package components;

public class Debit extends Flow {
    public Debit(String comment, int identifier, double amount, int targetAccountNumber, boolean effect, String date) {
        super(comment, identifier, amount, targetAccountNumber, effect, date);
    }

}