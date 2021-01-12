package model;

import java.util.Date;

public class Valutazione
{
    String recensione;
    int stelle;
    Date dataV;
    String CFrecensore;
    Impiegato recensito;

    public Valutazione()
    {

    }

    public String getRecensione() {
        return recensione;
    }

    public void setRecensione(String recensione) {
        this.recensione = recensione;
    }

    public int getStelle() {
        return stelle;
    }

    public void setStelle(int stelle) {
        this.stelle = stelle;
    }

    public Date getDataV() {
        return dataV;
    }

    public void setDataV(Date dataV) {
        this.dataV = dataV;
    }

    public String getCFrecensore() {
        return CFrecensore;
    }

    public void setCFrecensore(String CFrecensore) {
        this.CFrecensore = CFrecensore;
    }

    public Impiegato getRecensito() {
        return recensito;
    }

    public void setRecensito(Impiegato recensito) {
        this.recensito = recensito;
    }

    @Override
    public String toString()
    {
        return "Valutazione{}";
    }
}
