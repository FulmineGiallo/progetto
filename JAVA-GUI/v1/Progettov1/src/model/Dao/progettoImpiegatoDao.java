package model.Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import model.Impiegato;
import model.Progetto;
import model.Ruolo;
import model.DaoInterface.ProgettoImpiegatoDaoInterface;

public class progettoImpiegatoDao implements ProgettoImpiegatoDaoInterface{
	
	 private final Connection connection;
	 private final Statement eliminaImpiegato;
	 private final PreparedStatement inserisciImpiegatoInProgetto;
	 private final PreparedStatement getRuolo;
	 
	public progettoImpiegatoDao(Connection connection) throws SQLException {
		this.connection = connection;
		eliminaImpiegato = connection.createStatement();
		inserisciImpiegatoInProgetto = connection.prepareStatement("INSERT INTO progettoImpiegato VALUES(?,?,?)");
		getRuolo = connection.prepareStatement("SELECT tiporuolo FROM progettoimpiegato AS pi NATURAL JOIN ruolo AS r WHERE pi.idprogetto = ? AND pi.cf LIKE ?");
		
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
	
	
	public Ruolo getRuoloImpiegato(Impiegato impiegato, int idProgetto) throws SQLException {
		Ruolo ruolo = null;
		
		getRuolo.setInt(1, idProgetto);
		getRuolo.setString(2, impiegato.getCF());
		
		ResultSet rs = getRuolo.executeQuery();
		
		while(rs.next()) {
			ruolo = new Ruolo(rs.getString("tiporuolo"));
		}
		
		return ruolo;
	}
}
