package model;

import java.sql.Date;
import java.time.LocalDate;

public class Progetto
{
    String titolo;
    String descrizione;
    Date dataInizio;
    Date dataFine;
    Date scadenza;
    Impiegato projectManager;
    String ruolo;

    public String getRuolo() {
        return ruolo;
    }

    public void setRuolo(String ruolo) {
        this.ruolo = ruolo;
    }

    public Impiegato getProjectManager()
    {
        return projectManager;
    }

    public void setProjectManager(Impiegato projectManager) {
        this.projectManager = projectManager;
    }

    public Progetto(String titolo)
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

    @Override
    public String toString()
    {
        return titolo + "\n" + ruolo;
    }


}
