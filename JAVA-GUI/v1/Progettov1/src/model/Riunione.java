package model;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;

public class Riunione
{
    private String titolo;
    private String descrizione;
    private Impiegato organizzatore;
    private Impiegato CFOrganizzatore;
    private Date data;
    private Time orarioInizio;
    private Time orarioFine;
    private ArrayList<Impiegato> partecipantiRiunione = new ArrayList<>();

    public Riunione(String titolo)
    {
        this.titolo = titolo;        
    }
    
    
    public String getTitolo() {
        return titolo;
    }

    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }
    
    public Impiegato getOrganizzatore() {
        return organizzatore;
    }

    public void setOrganizzatore(Impiegato organizzatore) {
        this.organizzatore = organizzatore;
    }
    
    
    public Impiegato getCFOrganizzatore() {
        return CFOrganizzatore;
    }

    public void setCFOrganizzatore(Impiegato CFOrganizzatore) {
        this.CFOrganizzatore = CFOrganizzatore;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public Time getOrarioInizio() {
        return orarioInizio;
    }

    public void setOrarioInizio(Time orarioInizio) {
        this.orarioInizio = orarioInizio;
    }

    public Time getOrarioFine() {
        return orarioFine;
    }

    public void setOrarioFine(Time orarioFine) {
        this.orarioFine = orarioFine;
    }
    @Override
    public String toString() {
    	if(data != null) {
    		return "titolo: " 		 + titolo 								  +
    			   " data: "  		 + data   								  +
    			   " orario inizio " + orarioInizio.toString().substring(0,5) +
    			   " orario fine "   + orarioFine.toString().substring(0,5);
    	} else {
    		return titolo;
    	}
    }
}
