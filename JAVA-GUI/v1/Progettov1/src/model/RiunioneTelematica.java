package model;

import java.time.LocalDateTime;

public class RiunioneTelematica extends Riunione {
	
	private String piattaforma;
    private String codiceAccesso;

	public RiunioneTelematica(Impiegato organizzatore, String titolo,
							  LocalDateTime orarioDiInizio, LocalDateTime orarioDiFine,
							  String piattaforma, String codiceAccesso) {
		super(organizzatore, titolo, orarioDiInizio, orarioDiFine, "Riunione in modalità telematica");
		this.piattaforma = piattaforma;
		this.codiceAccesso = codiceAccesso;
	}
    
	public String getPiattaforma() {
        return piattaforma;
    }

    public void setPiattaforma(String piattaforma) {
        this.piattaforma = piattaforma;
    }

    public String getCodiceAccesso() {
        return codiceAccesso;
    }

    public void setCodiceAccesso(String codiceAccesso) {
        this.codiceAccesso = codiceAccesso;
    }
}
