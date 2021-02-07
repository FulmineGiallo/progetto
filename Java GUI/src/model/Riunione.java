package model;

import java.time.LocalDateTime;
import java.util.ArrayList;

import utilities.MetodiComuni;

public class Riunione {
    protected Impiegato organizzatore;
    protected String titolo;
    protected String descrizione;
    protected LocalDateTime orarioDiInizio;
    protected LocalDateTime orarioDiFine;
    protected String note;
    protected ArrayList<Impiegato> partecipantiRiunione = new ArrayList<>();
    
    private String tipologia;
    
    private MetodiComuni utils = new MetodiComuni();

    protected Riunione(Impiegato organizzatore, String titolo,
    				   LocalDateTime orarioDiInizio, LocalDateTime orarioDiFine,
    				   String tipologia)
    {
        this.organizzatore = organizzatore;
        this.titolo = titolo;
        this.orarioDiInizio = orarioDiInizio;
        this.orarioDiFine = orarioDiFine;
        this.tipologia = tipologia;
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
    
    public LocalDateTime getOrarioDiInizio() {
		return orarioDiInizio;
	}

	public void setOrarioDiInizio(LocalDateTime orarioDiInizio) {
		this.orarioDiInizio = orarioDiInizio;
	}
	
	public LocalDateTime getOrarioDiFine() {
		return orarioDiFine;
	}
	
	public void setOrarioDiFine(LocalDateTime orarioDiFine) {
		this.orarioDiFine = orarioDiFine;
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
	
	public String getTipologia() {
		return tipologia;
	}
	
	@Override
    public String toString() {
    	if (orarioDiInizio != null)
			return utils.orarioToString(titolo, orarioDiInizio.toLocalDate(), orarioDiInizio.toLocalTime());
    	else
    		return titolo;
    }
}
