package at.htl.bank.model;

public class BankKonto {

    private String name;
    protected double kontoStand;

    public BankKonto() {
    }

    public BankKonto(String name) {
        this.name = name;
    }

    public BankKonto(String name, double kontoStand) {
        this.name = name;
        this.kontoStand = kontoStand;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getKontoStand() {
        return kontoStand;
    }

    public void einzahlen(double betrag) {
        kontoStand = kontoStand + betrag;
    }

    public void abheben(double betrag) {
        kontoStand = kontoStand - betrag;
    }

}
