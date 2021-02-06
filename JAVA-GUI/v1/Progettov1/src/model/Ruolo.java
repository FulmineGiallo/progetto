package model;

public class Ruolo
{
	private String tipoRuolo;

	public Ruolo(String tipoRuolo)
	{
		this.tipoRuolo = tipoRuolo;
	}
	
	public String getTipoRuolo()
	{
		return tipoRuolo;
	}
	
	public void setTipoRuolo(String tipoRuolo)
	{
		this.tipoRuolo = tipoRuolo;
	}

	@Override
	public String toString() {
		return tipoRuolo;
	}
	
	
	
	
}
