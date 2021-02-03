package model;

import java.sql.Date;

public class Valutazione
{
	String titolo;
    String recensione;
    int stelle;
    Date dataV;
    String CFrecensore; //Implementare Impiegato recensore
    Impiegato recensito;

    public Valutazione(){ }
    
	public String getTitolo() {
		return titolo;
	}

	public void setTitolo(String titolo) {
		this.titolo = titolo;
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

    public void setDataV(Date date) {
        this.dataV = date;
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
    public String toString() {
        return "\"" + titolo + "\" - " + String.valueOf(stelle) + " stelle";
    }
}
