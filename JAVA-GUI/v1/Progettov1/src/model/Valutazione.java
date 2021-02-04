package model;

import java.time.LocalDate;

public class Valutazione {
	String titolo;
    String recensione;
    int stelle;
    LocalDate dataValutazione;
    Impiegato recensore;
    Impiegato recensito;
    boolean nuovo;
    
	public Valutazione(String titolo, int stelle, boolean nuovo) {
		this.titolo = titolo;
		this.stelle = stelle;
		this.nuovo = nuovo;
	}

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

    public LocalDate getDataValutazione() {
		return dataValutazione;
	}

	public void setDataValutazione(LocalDate dataValutazione) {
		this.dataValutazione = dataValutazione;
	}

	public Impiegato getRecensore() {
        return recensore;
    }

    public void setRecensore(Impiegato recensore) {
        this.recensore = recensore;
    }

    public Impiegato getRecensito() {
        return recensito;
    }

    public void setRecensito(Impiegato recensito) {
        this.recensito = recensito;
    }
	
	public boolean isNuovo(){
		return nuovo;
	}

    @Override
    public String toString() {
    	if(stelle != -1)
    		return "\"" + titolo + "\" - " + String.valueOf(stelle) + " stelle";
    	else
    		return titolo;
    }
}
