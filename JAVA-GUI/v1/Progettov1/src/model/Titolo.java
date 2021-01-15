package model;

public class Titolo {
	private String tipoTitolo;
	
	public Titolo(String tipoTitolo) {
		this.tipoTitolo = tipoTitolo;
	}

	public String getTipoTitolo() {
		return tipoTitolo;
	}

	public void setTipoTitolo(String tipoTitolo) {
		this.tipoTitolo = tipoTitolo;
	}
	
	@Override
	public String toString() {
		return tipoTitolo;
	}
}
