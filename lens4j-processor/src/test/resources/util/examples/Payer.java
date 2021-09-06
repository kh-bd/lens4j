package util.examples;

public class Payer {
    private String id;
    private Bank bank;

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setBank(Bank bank) {
        this.bank = bank;
    }

    public Bank getBank() {
        return bank;
    }
}