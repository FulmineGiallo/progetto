package model.Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import model.Impiegato;
import model.Progetto;
import model.Riunione;
import model.DaoInterface.RiunioneDaoInterface;
import model.DaoInterface.RiunioneImpiegatoDaoInterface;

public class RiunioneImpiegatoDao implements RiunioneImpiegatoDaoInterface{
	
	private final Connection connection;
	private final Statement eliminaImpiegato;
	private final PreparedStatement insertImpiegatoInRiunione;
	
	private RiunioneDaoInterface riunioneDao;

	public RiunioneImpiegatoDao(Connection connection) throws SQLException {
		this.connection = connection;
		insertImpiegatoInRiunione = connection.prepareStatement("INSERT INTO riunioneimpiegato VALUES(?,?, null)");
		eliminaImpiegato = connection.createStatement();
	}
	 
	public int EliminaImpiegatoDallaRiunione(Impiegato impiegato, int idRiunione)throws SQLException {
		
		int eliminato=0;
		
		eliminato = eliminaImpiegato.executeUpdate("DELETE  FROM riunioneimpiegato AS ri WHERE ri.idriunione = " + idRiunione + "AND ri.partecipante LIKE '" + impiegato.getCF()+"'");
				
		return eliminato;
	}
	
	public int inserisciImpiegato(Impiegato impiegato, Riunione riunione) throws SQLException {
		
		int inserito=0;
		riunioneDao = new RiunioneDao(connection);
		
		insertImpiegatoInRiunione.setInt(1, riunioneDao.getIdRiunione(riunione));
		insertImpiegatoInRiunione.setString(2, impiegato.getCF());
		
		inserito = insertImpiegatoInRiunione.executeUpdate();
		return inserito;
	 }

}
