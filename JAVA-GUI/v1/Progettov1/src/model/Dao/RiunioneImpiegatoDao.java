package model.Dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import model.Impiegato;
import model.DaoInterface.RiunioneImpiegatoDaoInterface;

public class RiunioneImpiegatoDao implements RiunioneImpiegatoDaoInterface{
	
	 private final Connection connection;
	 private final Statement eliminaImpiegato;

	 
	 
	public RiunioneImpiegatoDao(Connection connection) throws SQLException {
		this.connection = connection;
		eliminaImpiegato = connection.createStatement();
	}
	 
	public int EliminaImpiegatoDallaRiunione(Impiegato impiegato, int idRiunione)throws SQLException {
		
		int eliminato=0;
		
		eliminato = eliminaImpiegato.executeUpdate("DELETE  FROM riunioneimpiegato AS ri WHERE ri.idriunione = " + idRiunione + "AND ri.partecipante LIKE '" + impiegato.getCF()+"'");
				
		
		 return eliminato;
	 }

}
