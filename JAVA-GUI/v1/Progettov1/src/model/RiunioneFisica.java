package model;

import java.time.LocalDateTime;

public class RiunioneFisica extends Riunione {

	private String sede;
    private String piano;
    private String nomeStanza;
    
    public RiunioneFisica(Impiegato organizzatore, String titolo,
    					  LocalDateTime orarioDiInizio, LocalDateTime orarioDiFine,
    					  String sede, String piano, String nomeStanza)
    {
		super(organizzatore, titolo, orarioDiInizio, orarioDiFine, "Riunione in sede fisica");
		this.sede = sede;
		this.piano = piano;
		this.nomeStanza = nomeStanza;
	}

    public String getSede() {
        return sede;
    }

    public void setSede(String sede) {
        this.sede = sede;
    }

    public String getPiano() {
        return piano;
    }

    public void setPiano(String piano) {
        this.piano = piano;
    }

    public String getNomeStanza() {
        return nomeStanza;
    }

    public void setNomeStanza(String nomeStanza) {
        this.nomeStanza = nomeStanza;
    }
}
