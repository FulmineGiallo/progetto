package model;

import java.sql.Time;
import java.util.Date;

public class Riunione
{
    private String titolo;
    private String descrizione;
    private String organizzatore;
    private String CFOrganizzatore;
    private Date data;
    private Time orarioInizio;
    private Time orarioFine;

//    public Riunione(String titolo, String descrizione, Date data, Time orarioInizio, Time orarioFine, String organizzatore) {
//        this.titolo = titolo;
//        this.descrizione = descrizione;
//        this.data = data;
//        this.orarioInizio = orarioInizio;
//        this.orarioFine = orarioFine;
//        this.organizzatore = organizzatore;
//    }

    public Riunione(String titolo) {
        this.titolo = titolo;        
    }
    
    
    public String getTitolo() {
        return titolo;
    }

    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }
    
    public String getOrganizzatore() {
        return organizzatore;
    }

    public void setOrganizzatore(String organizzatore) {
        this.organizzatore = organizzatore;
    }
    
    
    public String getCFOrganizzatore() {
        return CFOrganizzatore;
    }

    public void setCFOrganizzatore(String CFOrganizzatore) {
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
        return "titolo: " + titolo + " data: " + data + " orario inizio " + orarioInizio.toString().substring(0,5) + " orario fine " + orarioFine.toString().substring(0,5);
    }
}
