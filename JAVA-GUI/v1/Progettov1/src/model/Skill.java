package model;

import java.time.LocalDate;

public class Skill
{
	private String		descrizione;
	private String		tipoSkill;
	private LocalDate	dataCertificazione;
	private Titolo		titolo;
	
	public Skill(String tipoSkill, LocalDate dataCertificazione, Titolo titolo, String descrizione) {
		this.tipoSkill = tipoSkill;
		this.dataCertificazione = dataCertificazione;
		this.titolo = titolo;
		this.descrizione = descrizione;
	}
	
	public Skill(String tipoSkill, String descrizione) {
		this.tipoSkill = tipoSkill;
		this.descrizione = descrizione;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public String getTipoSkill() {
		return tipoSkill;
	}

	public void setTipoSkill(String tipoSkill) {
		this.tipoSkill = tipoSkill;
	}

	public LocalDate getDataCertificazione() {
		return dataCertificazione;
	}

	public void setDataCertificazione(LocalDate dataCertificazione) {
		this.dataCertificazione = dataCertificazione;
	}

	public Titolo getTitolo() {
		return titolo;
	}

	public void setTitolo(Titolo titolo) {
		this.titolo = titolo;
	}
	
	@Override
	public String toString() {
		
		String valoreRitorno = "";
		if(tipoSkill.equals("Hard-Skill")) {
			valoreRitorno = titolo.getTipoTitolo() + " del " + dataCertificazione;
		} else {
			valoreRitorno = descrizione;
		}
		
		if(valoreRitorno.length() <= 35) {
			return valoreRitorno;
		} else {
			return valoreRitorno.substring(0, 32) + "...";
		}
	}
	
}
