package model;

import java.sql.Time;
import java.util.Date;

public class Riunione
{
    private String titolo;
    private String descrizione;
    private Date data;
    private Time orarioInizio;
    private Time orarioFine;

    public Riunione(String titolo, String descrizione, Date data, Time orarioInizio, Time orarioFine) {
        this.titolo = titolo;
        this.descrizione = descrizione;
        this.data = data;
        this.orarioInizio = orarioInizio;
        this.orarioFine = orarioFine;
    }

    public String getTitolo() {
        return titolo;
    }

    public void setTitolo(String titolo) {
        this.titolo = titolo;
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
}
