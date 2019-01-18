package at.htl.bank.model;

public class GiroKonto extends BankKonto {

    private double gebuehr;

    public GiroKonto(String name, double gebuehr) {
        super(name);
        this.gebuehr = gebuehr;
    }

    public GiroKonto(String name, double kontoStand, double gebuehr) {
        super(name, kontoStand);
        this.gebuehr = gebuehr;
    }


    public void einzahlen(double betrag) {
        kontoStand += betrag - gebuehr;
    }

    public void abheben(double betrag) {
        kontoStand -= betrag + gebuehr;
    }
}
