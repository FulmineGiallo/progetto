package model;

public class Ambito {

	String tipoAmbito;

	public Ambito(String tipoAmbito) {

		this.tipoAmbito = tipoAmbito;
	}

	public String getTipoAmbito() {
		return tipoAmbito;
	}

	public void setTipoAmbito(String tipoAmbito) {
		this.tipoAmbito = tipoAmbito;
	}

	@Override
	public String toString() {
		return this.tipoAmbito;
	}
	
	
	
}
