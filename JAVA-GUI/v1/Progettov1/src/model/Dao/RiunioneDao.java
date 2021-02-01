package model.Dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.DaoInterface.RiunioneDaoInterface;
import model.Impiegato;
import model.Riunione;

//import java.security.Timestamp;
import java.sql.Timestamp;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

import com.sun.net.httpserver.Authenticator.Result;

import java.time.*;

public class RiunioneDao implements RiunioneDaoInterface
{

	private final Connection connection;
    private final PreparedStatement riunioniImpiegato;
    private final PreparedStatement partecipanti;
    private final Statement isImpiegatoPresente;
    private final Statement updateImpiegatoPresente;
    private final Statement updateImpiegatoAssente;
    private final Statement getId;
    private int idRiunione;
    

    public RiunioneDao(Connection connection) throws SQLException {
        this.connection = connection;
        partecipanti = connection.prepareStatement("SELECT DISTINCT i.*FROM riunioneimpiegato AS ri NATURAL JOIN riunione JOIN impiegato AS i ON ri.partecipante = i.cf  WHERE idriunione = ?");
        riunioniImpiegato = connection.prepareStatement("SELECT r.orarioinizio, r.organizzatore, r.descrizione, r.orariofine, r.data, r.titolo, i.cognome , i.nome FROM (impiegato as i join riunioneimpiegato as ri ON i.cf = ri.partecipante) JOIN riunione AS r ON ri.idriunione=r.idriunione WHERE CF = ?");
        isImpiegatoPresente = connection.createStatement();
        updateImpiegatoPresente = connection.createStatement();
        updateImpiegatoAssente = connection.createStatement();
        getId = connection.createStatement();
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
    public ObservableList<Riunione> getRiunioniImpiegato(Impiegato impiegato) throws SQLException
    {
        ObservableList<Riunione> lista = FXCollections.observableArrayList();
        Riunione riunione;
        riunioniImpiegato.setString(1,impiegato.getCF());
        ResultSet rs = riunioniImpiegato.executeQuery();

        while (rs.next())
        {
            riunione = new Riunione(rs.getString("titolo"));
            riunione.setOrarioInizio(rs.getTime("orarioinizio"));
            riunione.setOrarioFine(rs.getTime("orariofine"));
            riunione.setData(rs.getDate("data"));
            riunione.setDescrizione(rs.getString("descrizione"));
            riunione.setCFOrganizzatore(impiegato);
            riunione.setOrganizzatore(impiegato);
            lista.add(riunione);
        }
        rs.close();

        return lista;
    }
    
    public int isPresente(Impiegato impiegato, Riunione riunione) throws SQLException {
    	
    	int presente=0;

    	
    	ResultSet rs = isImpiegatoPresente.executeQuery("SELECT COUNT(*), r.idriunione FROM riunione AS r NATURAL JOIN riunioneimpiegato AS ri WHERE titolo LIKE '"+riunione.getTitolo()+"' AND orarioinizio = '"+riunione.getData().toString() + " "+ riunione.getOrarioInizio().toString()+"' AND orariofine = '"+ riunione.getData().toString() + " " + riunione.getOrarioFine().toString() +"' AND partecipante LIKE '"+impiegato.getCF()+"' AND presenza LIKE 'null' GROUP BY idriunione");
    	
    	while(rs.next()) {
    		presente = rs.getInt("count");
    		idRiunione=rs.getInt("idriunione");
    	}
    	rs.close();
    	
    	return presente;
    }
	
    public int UpdatePresenza(Impiegato impiegato, Riunione riunione) throws SQLException{
    	
    	int successoUpdatePresenza;
    	successoUpdatePresenza =  updateImpiegatoPresente.executeUpdate("UPDATE riunioneimpiegato SET presenza = 'presente' WHERE partecipante LIKE '" + impiegato.getCF().toString()+ "' AND idriunione = '" + idRiunione + "'");
    	return successoUpdatePresenza;
    }
    
    public int UpdateAssenza(Impiegato impiegato, Riunione riunione) throws SQLException{
    	
    	int successoUpdateAssenza;
    	successoUpdateAssenza =  updateImpiegatoAssente.executeUpdate("UPDATE riunioneimpiegato SET presenza = 'assente' WHERE partecipante LIKE '" + impiegato.getCF().toString()+ "' AND idriunione = '" + idRiunione + "'");
    	return successoUpdateAssenza;
    }
    
    public int isAssente(Impiegato impiegato, Riunione riunione) throws SQLException {
    	
    	int assente=0;

    	
    	ResultSet rs = isImpiegatoPresente.executeQuery("SELECT COUNT(*), r.idriunione FROM riunione AS r NATURAL JOIN riunioneimpiegato AS ri WHERE titolo LIKE '"+riunione.getTitolo()+"' AND orarioinizio = '"+riunione.getData().toString() + " "+ riunione.getOrarioInizio().toString()+"' AND orariofine = '"+ riunione.getData().toString() + " " + riunione.getOrarioFine().toString() +"' AND partecipante LIKE '"+impiegato.getCF()+"' AND presenza LIKE 'null' GROUP BY idriunione");
    	
    	while(rs.next()) {
    		assente = rs.getInt("count");
    		idRiunione=rs.getInt("idriunione");
    	}
    	rs.close();
    	
    	return assente;
    }
    
    public int GetIdRiunione(Riunione riunione) throws SQLException{
    	
    	ResultSet rs = getId.executeQuery("SELECT r.idriunione FROM riunione AS r WHERE titolo LIKE '"+riunione.getTitolo()+"' AND orarioinizio = '"+riunione.getData().toString() + " "+ riunione.getOrarioInizio().toString()+"' AND orariofine = '"+ riunione.getData().toString() + " " + riunione.getOrarioFine().toString() +"' AND descrizione LIKE '"+riunione.getDescrizione()+"' AND organizzatore LIKE '"+ riunione.getCFOrganizzatore()+"'");
    	
    	while(rs.next()) {
    		idRiunione=rs.getInt("idriunione");
    	}
    	rs.close();
    	
    	return idRiunione;
    	
    }
    
}
