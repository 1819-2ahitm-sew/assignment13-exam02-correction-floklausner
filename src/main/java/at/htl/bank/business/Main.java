package at.htl.bank.business;

import at.htl.bank.model.BankKonto;
import at.htl.bank.model.GiroKonto;
import at.htl.bank.model.SparKonto;

import javax.print.Doc;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Legen Sie eine statische Liste "konten" an, in der Sie die einzelnen Konten speichern
 *
 */
public class Main {

  public static ArrayList<BankKonto> konten = new ArrayList<>();

  // die Konstanten sind package-scoped wegen der Unit-Tests
  static final double GEBUEHR = 0.02;
  static final double ZINSSATZ = 3.0;

  static final String KONTENDATEI = "erstellung.csv";
  static final String BUCHUNGSDATEI = "buchungen.csv";
  static final String ERGEBNISDATEI = "ergebnis.csv";


  /**
   * Führen Sie die drei Methoden erstelleKonten, fuehreBuchungenDurch und
   * findKontoPerName aus
   *
   * @param args
   */
  public static void main(String[] args) {

    erstelleKonten(KONTENDATEI);
    fuehreBuchungenDurch(BUCHUNGSDATEI);
    schreibeKontostandInDatei(ERGEBNISDATEI);

  }

  /**
   * Lesen Sie aus der Datei (erstellung.csv) die Konten ein.
   * Je nach Kontentyp erstellen Sie ein Spar- oder Girokonto.
   * Gebühr und Zinsen sind als Konstanten angegeben.
   *
   * Nach dem Anlegen der Konten wird auf der Konsole folgendes ausgegeben:
   * Erstellung der Konten beendet
   *
   * @param datei KONTENDATEI
   */
  private static void erstelleKonten(String datei) {

    String[] arr = new String[3];
    String name;
    double anfangsBestand;

    try (Scanner scanner = new Scanner(new FileReader(datei))) {
      scanner.nextLine();
      while (scanner.hasNextLine()) {
        arr = scanner.nextLine().split(";");
        name = arr[1];
        anfangsBestand = Double.parseDouble(arr[2]);

        if (arr[0].equals("Sparkonto")) {
          konten.add(new SparKonto(name, anfangsBestand, ZINSSATZ));
        }

        if (arr[0].equals("Girokonto")) {
          konten.add(new GiroKonto(name, anfangsBestand, GEBUEHR));
        }

      }

    } catch (FileNotFoundException e) {
      System.err.println(e.getMessage());
    }

    System.out.println("Erstellung der Konten beendet!");
  }

  /**
   * Die einzelnen Buchungen werden aus der Datei eingelesen.
   * Es wird aus der Liste "konten" jeweils das Bankkonto für
   * kontoVon und kontoNach gesucht.
   * Anschließend wird der Betrag vom kontoVon abgebucht und
   * der Betrag auf das kontoNach eingezahlt
   *
   * Nach dem Durchführen der Buchungen wird auf der Konsole folgendes ausgegeben:
   * Buchung der Beträge beendet
   *
   * Tipp: Verwenden Sie hier die Methode 'findeKontoPerName(String name)'
   *
   * @param datei BUCHUNGSDATEI
   */
  private static void fuehreBuchungenDurch(String datei) {

    String[] arr;
    String vonKonto, kontoNach;
    double betrag;

    try (Scanner scanner = new Scanner(new FileReader(datei))) {
      scanner.nextLine();
      while (scanner.hasNextLine()) {
        arr = scanner.nextLine().split(";");

        vonKonto = arr[0];
        kontoNach = arr[1];
        betrag = Double.parseDouble(arr[2]);

        findeKontoPerName(vonKonto).abheben(betrag);
        findeKontoPerName(kontoNach).einzahlen(betrag);
      }

    } catch (FileNotFoundException e) {
      System.err.println(e.getMessage());
    }
    
    System.out.println("Buchung der Datei beendet!");
  }

  /**
   * Es werden die Kontostände sämtlicher Konten in die ERGEBNISDATEI
   * geschrieben. Davor werden bei Sparkonten noch die Zinsen dem Konto
   * gutgeschrieben
   *
   * Die Datei sieht so aus:
   *
   * name;kontotyp;kontostand
   * Susi;SparKonto;875.5
   * Mimi;GiroKonto;949.96
   * Hans;GiroKonto;1199.96
   *
   * Vergessen Sie nicht die Überschriftenzeile
   *
   * Nach dem Schreiben der Datei wird auf der Konsole folgendes ausgegeben:
   * Ausgabe in Ergebnisdatei beendet
   *
   * @param datei ERGEBNISDATEI
   */
  private static void schreibeKontostandInDatei(String datei) {

    for (int i = 0; i < konten.size(); i++) {
      if (konten.get(i) instanceof SparKonto) {
        ((SparKonto) konten.get(i)).zinsenAnrechnen();
      }
    }

    try (PrintWriter printWriter = new PrintWriter(new FileWriter(datei))) {
      printWriter.println("name;kontotyp;kontostand");
      for (int i = 0; i < konten.size(); i++) {
        konten.get(i).getKontoStand();
        printWriter.print(konten.get(i).getName() + ";");
        if (konten.get(i) instanceof GiroKonto) {
          printWriter.print("GiroKonto;");
        } else {
          printWriter.print("SparKonto;");
        }
        printWriter.println(konten.get(i).getKontoStand());

      }
    } catch (IOException e) {
      e.printStackTrace();
    }

    System.out.println("Ausgabe in Ergebnisdatei beendet!");
  }

  /**
   */
  /**
   * Durchsuchen Sie die Liste "konten" nach dem ersten Konto mit dem als Parameter
   * übergebenen Namen
   * @param name
   * @return Bankkonto mit dem gewünschten Namen oder NULL, falls der Namen
   *         nicht gefunden wird
   */
  public static BankKonto findeKontoPerName(String name) {
    BankKonto konto = null;

    for (int i = 0; i < konten.size(); i++) {
      if (konten.get(i).getName().equals(name)) {
        konto = konten.get(i);
      }
    }
       return konto;
  }

}
