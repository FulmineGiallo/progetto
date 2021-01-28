package model.Dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import model.Impiegato;

import model.DaoInterface.ProgettoImpiegatoDaoInterface;

public class progettoImpiegatoDao implements ProgettoImpiegatoDaoInterface{
	
	 private final Connection connection;
	 private final Statement eliminaImpiegato;

	 
	 
	public progettoImpiegatoDao(Connection connection) throws SQLException {
		this.connection = connection;
		eliminaImpiegato = connection.createStatement();
	}
	 
	public int EliminaImpiegatoDalProgetto(Impiegato impiegato, int idProgetto)throws SQLException {
		
		int eliminato=0;
		

		
		eliminato = eliminaImpiegato.executeUpdate("DELETE  FROM progettoimpiegato AS pi WHERE pi.idprogetto = " + idProgetto + "AND pi.cf LIKE '" + impiegato.getCF()+"'");
				
		
		 return eliminato;
	 }
	
}
