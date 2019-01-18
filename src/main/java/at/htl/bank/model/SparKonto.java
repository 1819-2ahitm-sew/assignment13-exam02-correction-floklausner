package at.htl.bank.model;

public class SparKonto extends BankKonto {

    private double zinsSatz;

    public SparKonto(String name, double zinsSatz) {
        super(name);
        this.zinsSatz = zinsSatz;
    }

    public SparKonto(String name, double kontoStand, double zinsSatz) {
        super(name, kontoStand);
        this.zinsSatz = zinsSatz;
    }

    public void zinsenAnrechnen() {
        kontoStand += kontoStand * (zinsSatz / 100);
    }
}
