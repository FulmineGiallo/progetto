package model;

public class Titolo {
	private String tipoTitolo;
	private boolean nuovo;
	
	public Titolo(String tipoTitolo, boolean nuovo) {
		this.tipoTitolo = tipoTitolo;
		this.setNuovo(nuovo);
	}

	public String getTipoTitolo() {
		return tipoTitolo;
	}

	public void setTipoTitolo(String tipoTitolo) {
		this.tipoTitolo = tipoTitolo;
	}

	public boolean isNuovo() {
		return nuovo;
	}

	public void setNuovo(boolean nuovo) {
		this.nuovo = nuovo;
	}
	
	@Override
	public String toString() {
		return tipoTitolo;
	}
}
