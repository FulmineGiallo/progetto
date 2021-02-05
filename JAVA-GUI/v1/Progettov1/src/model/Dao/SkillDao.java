package model.Dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.DaoInterface.SkillDaoInterface;
import model.DaoInterface.TitoloDaoInterface;
import model.Impiegato;
import model.Riunione;
import model.Skill;
import model.Titolo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

public class SkillDao implements SkillDaoInterface
{
    Connection connection;
    private PreparedStatement dataCertificazioneImpiegato;
    private Statement descrizioneCertificazioneImpiegato;
    
    private final PreparedStatement queryListaSkill;
    
    private TitoloDaoInterface titoloDao;

    public SkillDao(Connection connection) throws SQLException
    {
        this.connection = connection;
        titoloDao = new TitoloDao(connection);
        
        dataCertificazioneImpiegato = connection.prepareStatement("SELECT datacertificazione FROM skill NATURAL JOIN titolo WHERE impiegato LIKE ? AND tipotitolo LIKE ?");
        descrizioneCertificazioneImpiegato = connection.createStatement();
        
        queryListaSkill = connection.prepareStatement("SELECT * " 			+
        											  "FROM listaskill "	+
        											  "WHERE impiegato = ?");
    }
    
    
    @Override
    public ObservableList<Skill> getListaSkill(Impiegato impiegato) throws SQLException{
    	ObservableList<Skill> 	listaSkill 	= FXCollections.observableArrayList();
    	Skill 					skill		= null;
    	
    	queryListaSkill.setString(1, impiegato.getCF());
    	ResultSet rs = queryListaSkill.executeQuery();
    	
    	while(rs.next()) {
    		if(rs.getString("tiposkill").equals("Soft-Skill")) {
    			skill = new Skill(rs.getString("tiposkill"),
    							  rs.getString("descrizione"));
    		} else {
    			skill = new Skill(rs.getString("tiposkill"),
    							  rs.getObject("datacertificazione", LocalDate.class),
    							  titoloDao.getTitoloById(rs.getInt("idtitolo")),
    							  rs.getString("descrizione"));		  
    		}
    		
    		listaSkill.add(skill);
    	}
    	
    	rs.close();
    	
        return listaSkill;
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
        
    public String getTipologiaSkill(String titolo, Impiegato impiegato) throws SQLException {
    	
    	String descrizione=null;
    	
        
        ResultSet rs = descrizioneCertificazioneImpiegato.executeQuery("SELECT tiposkill FROM skill NATURAL JOIN titolo WHERE impiegato LIKE '" + impiegato.getCF() +"' AND tipotitolo LIKE '" + titolo + "'");
        
        while (rs.next())
        {
        	descrizione=rs.getString("tiposkill");
        }
        
    	
    	return descrizione;
    }
    
}
