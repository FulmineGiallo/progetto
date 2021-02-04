package model.Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import model.Impiegato;
import model.Ruolo;
import model.DaoInterface.ProgettoImpiegatoDaoInterface;

public class progettoImpiegatoDao implements ProgettoImpiegatoDaoInterface{
	
	 private final Connection connection;
	 private final Statement eliminaImpiegato;
	 private final PreparedStatement inserisciImpiegatoInProgetto;
	 
	 
	public progettoImpiegatoDao(Connection connection) throws SQLException {
		this.connection = connection;
		eliminaImpiegato = connection.createStatement();
		inserisciImpiegatoInProgetto = connection.prepareStatement("INSERT INTO progettoImpiegato VALUES(?,?,?)");
	}
	 
	public int EliminaImpiegatoDalProgetto(Impiegato impiegato, int idProgetto)throws SQLException {
		
		int eliminato=0;
		eliminato = eliminaImpiegato.executeUpdate("DELETE  FROM progettoimpiegato AS pi WHERE pi.idprogetto = " + idProgetto + "AND pi.cf LIKE '" + impiegato.getCF()+"'");
		return eliminato;
	 }
	
	
	public int InserisciImpiegatoNelProgetto(Impiegato impiegato, int idProgetto, int idRuolo)throws SQLException {
		
		int inserito=0;
		
		inserisciImpiegatoInProgetto.setInt(1, idProgetto);
		inserisciImpiegatoInProgetto.setInt(2, idRuolo);
		inserisciImpiegatoInProgetto.setString(3, impiegato.getCF());
		
		inserito = inserisciImpiegatoInProgetto.executeUpdate();
		return inserito;
	 }
}
