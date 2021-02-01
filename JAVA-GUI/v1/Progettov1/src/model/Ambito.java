package model;

public class Ambito {

	String tipoAmbito;
	boolean nuovo;

	public Ambito(String tipoAmbito, boolean nuovo) {
		this.tipoAmbito = tipoAmbito;
		this.nuovo = nuovo;
	}

	public String getTipoAmbito() {
		return tipoAmbito;
	}

	public void setTipoAmbito(String tipoAmbito) {
		this.tipoAmbito = tipoAmbito;
	}
	
	public boolean isNuovo() {
		return nuovo;
	}

	@Override
	public String toString() {
		return tipoAmbito;
	}
}
