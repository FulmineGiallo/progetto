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
        
        partecipanti = connection.prepareStatement("SELECT DISTINCT i.* FROM riunioneimpiegato AS ri NATURAL JOIN riunione JOIN impiegato AS i ON ri.partecipante = i.cf  WHERE idriunione = ?");
        
        riunioniImpiegato 		= connection.prepareStatement("SELECT idriunione " 		+
        													  "FROM riunioneimpiegato " +
        													  "WHERE partecipante = ?");
        
        queryRiunione			= connection.prepareStatement("SELECT * FROM riunione WHERE idriunione = ?");
        queryRiunioneFisica 	= connection.prepareStatement("SELECT * FROM riunionefisica WHERE idriunione = ?");
        queryRiunioneTelematica = connection.prepareStatement("SELECT * FROM riunionetelematica WHERE idriunione = ?");
        
        queryImpiegatoInvitato 	= connection.prepareStatement("SELECT COUNT(*) " 										+
														  	  "FROM riunione AS r NATURAL JOIN riunioneimpiegato AS ri "+
														  	  "WHERE r.titolo LIKE ? "									+
														  	  "AND r.organizzatore LIKE ? "								+
														  	  "AND ri.partecipante LIKE ? "								+
														  	  "AND presenza LIKE 'null' "								+
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
    
    public ObservableList<Impiegato> getPartecipanti(int idriunione) throws SQLException
    {

        ObservableList<Impiegato> lista = FXCollections.observableArrayList();
        partecipanti.setInt(1,idriunione);
        ResultSet rs = partecipanti.executeQuery();
        Impiegato partecipante;
        while(rs.next())
        {

            partecipante = new Impiegato(rs.getString("cf"));
            partecipante.setNome(rs.getString("nome"));
            partecipante.setCognome(rs.getString("cognome"));
            partecipante.setComuneNascita(rs.getString("comunen"));
            partecipante.setGenere(rs.getString("genere"));
            partecipante.setEmail(rs.getString("email"));
            partecipante.setDataNascita(rs.getObject("datan", LocalDate.class));
            partecipante.setPassword(rs.getString("password"));
            partecipante.setIdImpiegato(rs.getInt("idimpiegato"));
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
    			listaRiunioni.add(nuovaRiunioneTelematica);
    		}
    		
    		queryRiunioneTelematicaResultSet.close();
    	}
    	
    	return listaRiunioni;
    }

    public boolean isInvitato(Impiegato impiegato, Riunione riunione) throws SQLException {
    	
    	int invitato = 0;
    	
    	queryImpiegatoInvitato.setString(1, riunione.getTitolo());
    	queryImpiegatoInvitato.setString(2, riunione.getOrganizzatore().getCF());
    	queryImpiegatoInvitato.setString(3, impiegato.getCF());
    	
    	ResultSet rs = queryImpiegatoInvitato.executeQuery();
    	
    	while(rs.next())
    		invitato = rs.getInt("count");
    	
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
