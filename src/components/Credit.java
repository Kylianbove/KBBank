package components;

public class Credit extends Flow {
    public Credit(String comment, int identifier, double amount, int targetAccountNumber, boolean effect, String date) {
        super(comment, identifier, amount, targetAccountNumber, effect, date);
    }

}