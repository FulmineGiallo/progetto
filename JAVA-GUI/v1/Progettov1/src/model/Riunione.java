package model;

import java.sql.Time;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class Riunione
{
    private String titolo;
    private String descrizione;
    private Impiegato organizzatore;
    private Impiegato CFOrganizzatore; //DA ELIMINARE
    private Date data; //DA ELIMINARE
    private Date orarioInizio;
    private Date orarioFine;
    private String note;
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

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
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

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public Date getOrarioInizio() {
        return orarioInizio;
    }

    public void setOrarioInizio(Date orarioInizio) {
        this.orarioInizio = orarioInizio;
    }

    public Date getOrarioFine() {
        return orarioFine;
    }

    public void setOrarioFine(Date orarioFine) {
        this.orarioFine = orarioFine;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public ArrayList<Impiegato> getPartecipantiRiunione() {
        return partecipantiRiunione;
    }

    public void setPartecipantiRiunione(ArrayList<Impiegato> partecipantiRiunione) {
        this.partecipantiRiunione = partecipantiRiunione;
    }

    @Override
    public String toString()
    {
    	return titolo.toUpperCase(Locale.ROOT) + "-" + (organizzatore.getNome() + organizzatore.getCognome()).toUpperCase();
    }
}
