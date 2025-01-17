package model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javafx.collections.ObservableList;

public class Progetto {
    private Impiegato projectManager;
    private String titolo;
    private String descrizione;
    private LocalDate dataInizio;
    private LocalDate dataFine;
    private LocalDate scadenza;
    private Tipologia tipoProgetto;
    private ObservableList<Ambito> listaAmbiti;
	private String note;
	
    private String ruolo;
	
	public Progetto (String titolo)
    {
		this.titolo = titolo;
	}

    public Progetto(Impiegato projectManager, String titolo, LocalDate dataInizio, LocalDate scadenza, Tipologia tipoProgetto) {
		this.projectManager = projectManager;
    	this.titolo = titolo;
		this.dataInizio = dataInizio;
		this.scadenza = scadenza;
		this.tipoProgetto = tipoProgetto;
	}

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

    public LocalDate getDataInizio() {
        return dataInizio;
    }

    public void setDataInizio(LocalDate dataInizio) {
        this.dataInizio = dataInizio;
    }

    public LocalDate getDataFine() {
        return dataFine;
    }

    public void setDataFine(LocalDate dataFine) {
        this.dataFine = dataFine;
    }

    public LocalDate getScadenza() {
        return scadenza;
    }

    public void setScadenza(LocalDate scadenza) {
        this.scadenza = scadenza;
    }
    
    public Tipologia getTipoProgetto() {
		return tipoProgetto;
	}

	public void setTipoProgetto(Tipologia tipoProgetto) {
		this.tipoProgetto = tipoProgetto;
	}

	public ObservableList<Ambito> getListaAmbiti() {
		return listaAmbiti;
	}

	public void setListaAmbiti(ObservableList<Ambito> listaAmbiti) {
		this.listaAmbiti = listaAmbiti;
	}
	
	    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }


    @Override
    public String toString() {
    	if(ruolo != null)
    		return titolo + "\n" + ruolo;
    	else 
    		return titolo;
    }
}
