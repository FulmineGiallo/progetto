package model;

import java.time.LocalDate;

public class Skill
{
	private String		descrizione;
	private String		tipoSkill;
	private LocalDate	dataCertificazione;
	private String		titolo;
	
	public Skill(String tipoSkill, LocalDate dataCertificazione, String titolo) {
		this.tipoSkill = tipoSkill;
		this.dataCertificazione = dataCertificazione;
		this.titolo = titolo;
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

	public String getTitolo() {
		return titolo;
	}

	public void setTitolo(String titolo) {
		this.titolo = titolo;
	}
	
	@Override
	public String toString() {
		return titolo + " del " + dataCertificazione;
	}
	
}
