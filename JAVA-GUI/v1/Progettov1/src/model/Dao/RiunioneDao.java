package model.Dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.DaoInterface.ImpiegatoDaoInterface;
import model.DaoInterface.RiunioneDaoInterface;
import model.Impiegato;
import model.Riunione;
import model.RiunioneFisica;
import model.RiunioneTelematica;

import java.sql.Timestamp;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

import com.sun.net.httpserver.Authenticator.Result;

import java.time.*;

public class RiunioneDao implements RiunioneDaoInterface {
	private final Connection connection;

    private final PreparedStatement riunioniImpiegato;
    
    private	final PreparedStatement insertNuovaRiunione;
    private final PreparedStatement	insertNuovaRiunioneFisica;
    private final PreparedStatement insertNuovaRiunioneTelematica;
    private final PreparedStatement insertOrganizzatoreRiunione;
    
    private final PreparedStatement queryRiunione;
    private final PreparedStatement queryRiunioneFisica;
    private final PreparedStatement queryRiunioneTelematica;
    private final PreparedStatement partecipanti;
    private final PreparedStatement queryImpiegatoInvitato;
    private final Statement updateImpiegatoPresente;
    private final Statement updateImpiegatoAssente;
    private final PreparedStatement queryIdRiunione;
    private int idRiunione;
    private  PreparedStatement updateInfo;
    
    private ImpiegatoDaoInterface impiegatoDao;

    public RiunioneDao(Connection connection) throws SQLException {
        this.connection = connection;
        impiegatoDao = new ImpiegatoDao(connection);
        
        partecipanti			= connection.prepareStatement("SELECT DISTINCT partecipante " +
				  											  "FROM listariunioni " 		  +
				  											  "WHERE idriunione = ?");
        
        riunioniImpiegato 		= connection.prepareStatement("SELECT idriunione " 		+
        													  "FROM listariunioni " 	+
        													  "WHERE partecipante = ?");
        
        queryRiunione			= connection.prepareStatement("SELECT * FROM riunione WHERE idriunione = ?");
        queryRiunioneFisica 	= connection.prepareStatement("SELECT * FROM riunionefisica WHERE idriunione = ?");
        queryRiunioneTelematica = connection.prepareStatement("SELECT * FROM riunionetelematica WHERE idriunione = ?");
        
        insertNuovaRiunione				= connection.prepareStatement("INSERT INTO riunione VALUES (NEXTVAL('id_riunione_seq'), ?, ?, ?, ?, ?, null)");
        insertNuovaRiunioneFisica		= connection.prepareStatement("INSERT INTO riunionefisica VALUES (?, ?, ?, ?)");
        insertNuovaRiunioneTelematica	= connection.prepareStatement("INSERT INTO riunionetelematica VALUES (?, ?, ?)");
        insertOrganizzatoreRiunione		= connection.prepareStatement("INSERT INTO riunioneimpiegato VALUES (?, ?, 'presente')");
        
        queryImpiegatoInvitato 	= connection.prepareStatement("SELECT COUNT(*), idriunione " 							+
														  	  "FROM riunione AS r NATURAL JOIN riunioneimpiegato AS ri "+
														  	  "WHERE r.titolo LIKE ? "									+
														  	  "AND r.organizzatore LIKE ? "								+
														  	  "AND ri.partecipante LIKE ? "								+
														  	  "AND presenza IS null "									+
														  	  "GROUP BY idriunione");
        
        queryIdRiunione 		= connection.prepareStatement("SELECT idriunione " 		+
											        		  "FROM riunione "			+
											        		  "WHERE titolo LIKE ? "	+
											        		  "AND organizzatore LIKE ?");
        
        updateImpiegatoPresente = connection.createStatement();
        updateImpiegatoAssente = connection.createStatement();
        updateInfo = connection.prepareStatement("");
    }
    
    @Override
    public int updateRiunione(Riunione riunione) throws SQLException {
        int result = updateInfo.executeUpdate();
        return result;
    }
    
    public ObservableList<Impiegato> getPartecipanti(int idriunione) throws SQLException {
    	
        ObservableList<Impiegato> lista = FXCollections.observableArrayList();
        Impiegato partecipante;
        
        partecipanti.setInt(1, idriunione);
        ResultSet rs = partecipanti.executeQuery();
        
        while(rs.next()) {

        	partecipante = impiegatoDao.creaImpiegato(rs.getString("partecipante"));
            lista.add(partecipante);
        }
        
        rs.close();

        return lista;
    }

    @Override
    public ObservableList<Riunione> getRiunioniImpiegato(Impiegato impiegato) throws SQLException{
    	int idRiunione = 0;
    	
    	ObservableList<Riunione> listaRiunioni = FXCollections.observableArrayList();
    	
    	LocalDateTime 	orarioDiInizio 	= null;
    	LocalDateTime 	orarioDiFine	= null;
    	String 			titolo			= null;
    	String 			descrizione		= null;
    	Impiegato 		organizzatore	= null;
    	String			note			= null;
    	
    	riunioniImpiegato.setString(1, impiegato.getCF());
    	ResultSet riunioniResultSet = riunioniImpiegato.executeQuery();
    	
    	while(riunioniResultSet.next()) {
    		idRiunione = riunioniResultSet.getInt("idriunione");
    		
    		queryRiunione.setInt(1, idRiunione);
    		ResultSet queryRiunioneResultSet = queryRiunione.executeQuery();
    		
    		while(queryRiunioneResultSet.next()) {
    			orarioDiInizio	= queryRiunioneResultSet.getObject("orarioinizio",	LocalDateTime.class);
    			orarioDiFine	= queryRiunioneResultSet.getObject("orariofine",	LocalDateTime.class);
    			titolo			= queryRiunioneResultSet.getString("titolo");
    			descrizione		= queryRiunioneResultSet.getString("descrizione");
    			organizzatore	= impiegatoDao.creaImpiegato(queryRiunioneResultSet.getString("organizzatore"));
    			note			= queryRiunioneResultSet.getString("note");
    		}
    		
    		queryRiunioneResultSet.close();
    		
    		queryRiunioneFisica.setInt(1, idRiunione);
    		ResultSet queryRiunioneFisicaResultSet = queryRiunioneFisica.executeQuery();
    		
    		while(queryRiunioneFisicaResultSet.next()) {
    			RiunioneFisica nuovaRiunioneFisica
    				= new RiunioneFisica(organizzatore, titolo,
    									 orarioDiInizio, orarioDiFine,
    									 queryRiunioneFisicaResultSet.getString("sede"),
    									 queryRiunioneFisicaResultSet.getString("piano"),
    									 queryRiunioneFisicaResultSet.getString("nomestanza"));
    			
    			nuovaRiunioneFisica.setDescrizione(descrizione);  
    			nuovaRiunioneFisica.setNote(note);
    			listaRiunioni.add(nuovaRiunioneFisica);
    		}
    		
    		queryRiunioneFisicaResultSet.close();
    		
    		queryRiunioneTelematica.setInt(1, idRiunione);
    		ResultSet queryRiunioneTelematicaResultSet = queryRiunioneTelematica.executeQuery();
    		
    		while(queryRiunioneTelematicaResultSet.next()) {
    			RiunioneTelematica nuovaRiunioneTelematica
    				= new RiunioneTelematica(organizzatore, titolo,
    										 orarioDiInizio, orarioDiFine,
    										 queryRiunioneTelematicaResultSet.getString("piattaforma"),
    										 queryRiunioneTelematicaResultSet.getString("codiceaccesso"));
    			
    			nuovaRiunioneTelematica.setDescrizione(descrizione);
    			nuovaRiunioneTelematica.setNote(note);
    			listaRiunioni.add(nuovaRiunioneTelematica);
    		}
    		
    		queryRiunioneTelematicaResultSet.close();
    	}
    	
    	return listaRiunioni;
    }
    
    @Override
    public String creaRiunione(RiunioneFisica nuovaRiunioneFisica) throws SQLException{
    	
    	int riunioniInserite = 0;
    	
    	insertNuovaRiunioneFisica.setString(1, nuovaRiunioneFisica.getSede());
    	insertNuovaRiunioneFisica.setString(2, nuovaRiunioneFisica.getPiano());
    	insertNuovaRiunioneFisica.setString(3, nuovaRiunioneFisica.getNomeStanza());
    	insertNuovaRiunioneFisica.setInt   (4, getIdNuovaRiunione(nuovaRiunioneFisica));
    	
    	riunioniInserite = insertNuovaRiunioneFisica.executeUpdate();
    	
    	return "Riunioni inserite: " + riunioniInserite;
    }
    
    @Override
    public String creaRiunione(RiunioneTelematica nuovaRiunioneTelematica) throws SQLException{
    	
    	int riunioniInserite = 0;
    	
    	insertNuovaRiunioneTelematica.setString(1, nuovaRiunioneTelematica.getPiattaforma());
    	insertNuovaRiunioneTelematica.setString(2, nuovaRiunioneTelematica.getCodiceAccesso());
    	insertNuovaRiunioneTelematica.setInt   (3, getIdNuovaRiunione(nuovaRiunioneTelematica));
    	
    	riunioniInserite = insertNuovaRiunioneTelematica.executeUpdate();
    	
    	return "Riunioni inserite: " + riunioniInserite;
    }
    
    private int getIdNuovaRiunione(Riunione nuovaRiunione) throws SQLException{
    	int idRiunione = 0;
    	
    	insertNuovaRiunione.setObject(1, nuovaRiunione.getOrarioDiInizio());
    	insertNuovaRiunione.setObject(2, nuovaRiunione.getOrarioDiFine());
    	insertNuovaRiunione.setString(3, nuovaRiunione.getTitolo());
    	
    	if(nuovaRiunione.getDescrizione() != null) {
    		insertNuovaRiunione.setString(4, nuovaRiunione.getDescrizione());
    	} else {
    		insertNuovaRiunione.setNull(4, java.sql.Types.NULL);
    	}
    	
    	insertNuovaRiunione.setString(5, nuovaRiunione.getOrganizzatore().getCF());
    	
    	insertNuovaRiunione.executeUpdate();
    	idRiunione = getIdRiunione(nuovaRiunione);
    	
    	insertOrganizzatoreRiunione.setInt	 (1, idRiunione);
    	insertOrganizzatoreRiunione.setString(2, nuovaRiunione.getOrganizzatore().getCF());
    	
    	insertOrganizzatoreRiunione.executeUpdate();
    	
    	return idRiunione;
    }
    
    public boolean isInvitato(Impiegato impiegato, Riunione riunione) throws SQLException {
    	
    	int invitato = 0;
    	
    	queryImpiegatoInvitato.setString(1, riunione.getTitolo());
    	queryImpiegatoInvitato.setString(2, riunione.getOrganizzatore().getCF());
    	queryImpiegatoInvitato.setString(3, impiegato.getCF());
    	
    	System.out.println(queryImpiegatoInvitato);

    	
    	ResultSet rs = queryImpiegatoInvitato.executeQuery();
    	
    	while(rs.next()) {
    		invitato = rs.getInt("count");
    		idRiunione = rs.getInt("idriunione");
    	}
    	rs.close();
    	
    	if(invitato == 0) {
    		return false;
    	} else {
    		return true;
    	}
    }
	
    public int UpdatePresenza(Impiegato impiegato, Riunione riunione) throws SQLException{
    	
    	int successoUpdatePresenza;
    	successoUpdatePresenza =  updateImpiegatoPresente.executeUpdate("UPDATE riunioneimpiegato SET presenza = 'presente' WHERE partecipante LIKE '" + impiegato.getCF()+ "' AND idriunione = '" + idRiunione + "'");
    	System.out.print("UPDATE riunioneimpiegato SET presenza = 'presente' WHERE partecipante LIKE '" + impiegato.getCF()+ "' AND idriunione = '" + idRiunione + "'");
    	return successoUpdatePresenza;
    }
    
    public int UpdateAssenza(Impiegato impiegato, Riunione riunione) throws SQLException{
    	
    	int successoUpdateAssenza;
    	successoUpdateAssenza =  updateImpiegatoAssente.executeUpdate("UPDATE riunioneimpiegato SET presenza = 'assente' WHERE partecipante LIKE '" + impiegato.getCF()+ "' AND idriunione = '" + idRiunione + "'");
    	
    	return successoUpdateAssenza;
    }
    
    public int getIdRiunione(Riunione riunione) throws SQLException{
    	
    	int idRiunione = 0;
    	
    	queryIdRiunione.setString(1, riunione.getTitolo());
    	queryIdRiunione.setString(2, riunione.getOrganizzatore().getCF());
    	ResultSet rs = queryIdRiunione.executeQuery();
    	
    	while(rs.next())
    		idRiunione = rs.getInt("idriunione");
    	
    	rs.close();
    	
    	return idRiunione;
    	
    }
}
