package model.Dao;

import javafx.collections.ObservableList;
import model.DaoInterface.SkillDaoInterface;
import model.Impiegato;
import model.Riunione;
import model.Skill;
import model.Titolo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SkillDao implements SkillDaoInterface
{
    Connection connection;
    private PreparedStatement dataCertificazioneImpiegato;
    private Statement descrizioneCertificazioneImpiegato;

    public SkillDao(Connection connection) throws SQLException
    {
        this.connection = connection;
        dataCertificazioneImpiegato = connection.prepareStatement("SELECT datacertificazione FROM skill NATURAL JOIN titolo WHERE impiegato LIKE ? AND tipotitolo LIKE ?");
        descrizioneCertificazioneImpiegato = connection.createStatement();
    
    }
    
    
    @Override
    public ObservableList<Skill> skillList()
    {
        return null;
    }
    
    public String dataCertificazione(String titolo, Impiegato impiegato) throws SQLException {
    	
    	String data=null;
    	
    	
        dataCertificazioneImpiegato.setString(1, impiegato.getCF());
        dataCertificazioneImpiegato.setString(2, titolo);
        
        ResultSet rs = dataCertificazioneImpiegato.executeQuery();
        
        while (rs.next())
        {
        	data=rs.getDate("datacertificazione").toString();
        }
        
    	
    	return data;
    }
    
    public String descrizioneCertificazione(String titolo, Impiegato impiegato) throws SQLException {
    	
    	String descrizione=null;
    	
        
        ResultSet rs = descrizioneCertificazioneImpiegato.executeQuery("SELECT descrizione FROM skill NATURAL JOIN titolo WHERE impiegato LIKE '" + impiegato.getCF() +"' AND tipotitolo LIKE '" + titolo + "'");
        
        while (rs.next())
        {
        	descrizione=rs.getString("descrizione");
        }
        
    	
    	return descrizione;
    }
        
    
    
}
