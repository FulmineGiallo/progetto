package model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Progetto
{
    String titolo;
    String descrizione;
    Date dataInizio;
    Date dataFine;
    Date scadenza;
    Impiegato projectManager;
    List<Impiegato> partecipantiProgetto = new ArrayList<>();

    public Progetto(String titolo, String descrizione, Date dataInizio, Date dataFine, Date scadenza, Impiegato projectManager)
    {
        this.titolo = titolo;
        this.descrizione = descrizione;
        this.dataInizio = dataInizio;
        this.dataFine = dataFine;
        this.scadenza = scadenza;
        this.projectManager = projectManager;
    }

    public void partecipanti()
    {

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

    public Date getDataInizio() {
        return dataInizio;
    }

    public void setDataInizio(Date dataInizio) {
        this.dataInizio = dataInizio;
    }

    public Date getDataFine() {
        return dataFine;
    }

    public void setDataFine(Date dataFine) {
        this.dataFine = dataFine;
    }

    public Date getScadenza() {
        return scadenza;
    }

    public void setScadenza(Date scadenza) {
        this.scadenza = scadenza;
    }
}
