package model;

public class Tipologia {

	private String tipoProgetto;
	private boolean nuovo;

	public Tipologia(String tipoProgetto, boolean nuovo) {
		this.tipoProgetto = tipoProgetto;
		this.nuovo = nuovo;
	}

	public String getTipoProgetto() {
		return tipoProgetto;
	}

	public void setTipoProgetto(String tipoProgetto) {
		this.tipoProgetto = tipoProgetto;
	}
	
	public boolean isNuovo() {
		return nuovo;
	}

	@Override
	public String toString() {
		return tipoProgetto;
	}
}
