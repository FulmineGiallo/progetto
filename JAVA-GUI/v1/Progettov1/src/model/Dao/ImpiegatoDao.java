package model.Dao;

import model.DaoInterface.ImpiegatoDaoInterface;
import model.Impiegato;
import model.Skill;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ImpiegatoDao implements ImpiegatoDaoInterface
{

    private final Connection connection;

    private final PreparedStatement getImpiegati;

    private final PreparedStatement getNome;
    private final PreparedStatement getCognome;    
    private final PreparedStatement loginImpiegato;
    private final PreparedStatement getImpiegato;
    private final PreparedStatement getCF;
    private final PreparedStatement getTipoGrado;
    private final PreparedStatement getIdGrado;
    private final PreparedStatement getIdTitolo;
    private final PreparedStatement insertImpiegato;
    private final PreparedStatement insertTitolo;
    private final PreparedStatement insertSkill;
    private final Statement getImpiegatiConSalarioMedio;
    private final Statement getImpiegatiSenzaSalarioMedio;
    
    
    
    private 	  String 			direttoreRisorseUmane = "";
    private		  int				idGradoNuovoImpiegato;
    private		  int				idTitoloSkill;

    public ImpiegatoDao(Connection connection) throws SQLException
    {
        this.connection = connection;
        getImpiegati = connection.prepareStatement("SELECT * FROM impiegato AS i WHERE i.cf NOT IN(SELECT cf FROM progettoimpiegato AS pi WHERE pi.idprogetto = ?) ORDER BY cognome");
  
        getImpiegatiConSalarioMedio = connection.createStatement();
        getImpiegatiSenzaSalarioMedio = connection.createStatement();
        
        getNome = connection.prepareStatement("SELECT nome FROM impiegato WHERE email = ?");
        getCognome = connection.prepareStatement("SELECT cognome FROM impiegato WHERE email = ?");
        loginImpiegato = connection.prepareStatement("SELECT COUNT(*) FROM impiegatoaccount WHERE email = ? AND password = ?");
        getImpiegato = connection.prepareStatement("SELECT * FROM impiegato WHERE cf = ?");
        getCF = connection.prepareStatement("SELECT CF FROM impiegato WHERE email = ?");
        getTipoGrado = connection.prepareStatement("SELECT tipogrado FROM impiegatoazienda WHERE cf = ?");
        getIdGrado = connection.prepareStatement("SELECT idgrado FROM grado WHERE tipoGrado = ?");
        getIdTitolo = connection.prepareStatement("SELECT idtitolo FROM titolo WHERE tipoTitolo = ?");
        insertImpiegato = connection.prepareStatement("INSERT INTO Impiegato VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, NEXTVAL('id_impiegato_seq'))");
        insertTitolo = connection.prepareStatement("INSERT INTO Titolo VALUES (NEXTVAL('id_titolo_seq'), ?)");
        insertSkill = connection.prepareStatement("INSERT INTO Skill VALUES (NEXTVAL('id_skill_seq'), ?, ?, ?, ?, ?)");
    }

    @Override
    public int impiegatoExist(String email, String password) throws SQLException
    {
        int accesso = 0;
        loginImpiegato.setString(1,email);
        loginImpiegato.setString(2,password);
        ResultSet rs = loginImpiegato.executeQuery();
        while(rs.next())
        {
            accesso = rs.getInt("count");
        }
        rs.close();
        //Se ritorna 0, l'impiegato non esiste, altrimenti 1.
        return accesso;
    }

    @Override
    public Impiegato creaImpiegato(String CF) throws SQLException
    {
        getImpiegato.setString(1,CF);
        ResultSet rs = getImpiegato.executeQuery();
        Impiegato impiegato = null;
        while (rs.next())
        {
            impiegato = new Impiegato(rs.getString("cf"));
            impiegato.setNome(rs.getString("nome"));
            impiegato.setCognome(rs.getString("cognome"));
            impiegato.setComuneNascita(rs.getString("comunen"));
            impiegato.setGenere(rs.getString("genere"));
            impiegato.setEmail(rs.getString("email"));
            impiegato.setDataNascita(rs.getObject("datan", LocalDate.class));
            impiegato.setPassword(rs.getString("password"));
            //impiegato.setIdgrado(rs.getInt("idgrado")); vedere il perché del commento in Impiegato.java
            impiegato.setIdImpiegato(rs.getInt("idimpiegato"));
            impiegato.setGrado(getGrado(impiegato.getCF()));
        }
        rs.close();
        return impiegato;
    }

    @Override
    public String getCFWithEmail(String email) throws SQLException
    {
        String CF = null;
        getCF.setString(1,email);
        ResultSet rs = getCF.executeQuery();

        while(rs.next())
        {
            CF = rs.getString("CF");
        }
        rs.close();
        return CF;
    }
    
    @Override
    public String getNomeCognomeConCF(String CF) throws SQLException
    {
    	String nome = "";
    	String cognome = "";
    	
        getImpiegato.setString(1,CF);
        ResultSet rs = getImpiegato.executeQuery();

        while(rs.next())
        {
            nome = rs.getString("nome");
            cognome = rs.getString("cognome");
        }
        rs.close();
        String nomeCognome = nome + " " + cognome;
        return nomeCognome;
    }
    
    @Override
    public String getGrado(String cf) throws SQLException
    {
        String grado = null;
        getTipoGrado.setString(1, cf);
        ResultSet rs = getTipoGrado.executeQuery();

        while(rs.next()) {
            grado = rs.getString("tipogrado");
        }
        
        rs.close();
        return grado;
    }

    @Override
    public String insertRegistrazione(Impiegato nuovoImpiegato) throws SQLException{
    	
    	int impiegatiInseriti = 0;
    	int titoliInseriti = 0;
    	int skillInserite = 0;
    	
        getIdGrado.setString(1, nuovoImpiegato.getGrado());
        ResultSet rs = getIdGrado.executeQuery();
        
        while(rs.next()) {
        	idGradoNuovoImpiegato = rs.getInt("idgrado");
        }
        
        rs.close();
    	
    	insertImpiegato.setString(1, nuovoImpiegato.getNome());
    	insertImpiegato.setString(2, nuovoImpiegato.getCognome());
    	insertImpiegato.setString(3, nuovoImpiegato.getCF());
    	insertImpiegato.setObject(4, nuovoImpiegato.getDataNascita());
    	insertImpiegato.setString(5, nuovoImpiegato.getComuneNascita());
    	insertImpiegato.setString(6, nuovoImpiegato.getEmail());
    	insertImpiegato.setString(7, nuovoImpiegato.getGenere());
    	insertImpiegato.setInt   (8, idGradoNuovoImpiegato);
    	insertImpiegato.setString(9, nuovoImpiegato.getPassword());
    	
    	impiegatiInseriti = insertImpiegato.executeUpdate();

    	for(Skill s: nuovoImpiegato.getListaSkill()) {
    		
			if (s.getDescrizione() != null) {
				insertSkill.setString(1, s.getDescrizione());
			} else {
				insertSkill.setNull(1, java.sql.Types.NULL);
			}
			
			insertSkill.setString(2, s.getTipoSkill());
			insertSkill.setObject(3, s.getDataCertificazione());
			
    		if (s.getTitolo() != null) {
				if (s.getTitolo().isNuovo()) {
					insertTitolo.setString(1, s.getTitolo().getTipoTitolo());
					titoliInseriti = titoliInseriti + insertTitolo.executeUpdate();
				}
				
				getIdTitolo.setString(1, s.getTitolo().getTipoTitolo());
				rs = getIdTitolo.executeQuery();
				
				while (rs.next()) {
					idTitoloSkill = rs.getInt("idtitolo");
				}
				
				rs.close();
				
				insertSkill.setInt(4, idTitoloSkill);
			} else {
				insertSkill.setNull(4, java.sql.Types.NULL);
			}
    		
			insertSkill.setString(5, nuovoImpiegato.getCF());
			skillInserite = skillInserite + insertSkill.executeUpdate();
    	}
    	
        return "Impiegati inseriti: " + String.valueOf(impiegatiInseriti) +
        	   "\nTitoli inseriti: "  + String.valueOf(titoliInseriti)	  +
        	   "\nSkill inserite: "	  + String.valueOf(skillInserite);
    }

    public ObservableList<Impiegato> getAllImpiegati(int idProgetto) throws SQLException
    {
    	getImpiegati.setInt(1, idProgetto);
        ResultSet rs = getImpiegati.executeQuery();
        ObservableList<Impiegato> list = FXCollections.observableArrayList();

        while(rs.next())
        {
            Impiegato impiegato = new Impiegato(rs.getString("cf"));
            impiegato.setCognome(rs.getString("cognome"));
            impiegato.setNome(rs.getString("nome"));
            impiegato.setComuneNascita(rs.getString("comunen"));
            impiegato.setGenere(rs.getString("genere"));
            impiegato.setEmail(rs.getString("email"));
            impiegato.setDataNascita(rs.getObject("datan", LocalDate.class));
            impiegato.setPassword(rs.getString("password"));
            impiegato.setGrado(getGrado(impiegato.getCF()));
            list.add(impiegato);
        }
        rs.close();
        return list;
    }
    
    public String getDirettoreRisorseUmane() throws SQLException{
    	ResultSet rs = connection.createStatement().executeQuery("SELECT I.nome, I.cognome FROM Impiegato I WHERE I.idGrado = 10;");
    	
    	while (rs.next()) {
			if (rs.getString("nome").isBlank() || rs.getString("cognome").isBlank()) {
				direttoreRisorseUmane = "";
			} else {
				direttoreRisorseUmane = " " + rs.getString("nome") + " " + rs.getString("cognome") + " ";
			}
		}
    	
		rs.close();
    	return direttoreRisorseUmane;
    }
    
    public ObservableList<Impiegato> getAllImpiegatiOrdinati(float salarioMedio, String nomeInserito, String cognomeInserito, String ordinamentoSelezionato, ObservableList<String> skillSelezionate, int numeroDiSkill, int idProgetto) throws SQLException{
    	
       
    if(salarioMedio != -1) {	

        ObservableList<Impiegato> impiegati = FXCollections.observableArrayList();
        Impiegato impiegato = new Impiegato();
    
        String daEseguire = "SELECT DISTINCT nome, cognome, cf, comunen, email, datan, AVG(quantita) AS salarioMedio FROM (((impiegato AS i LEFT OUTER JOIN salario AS s ON i.cf = s.impiegato) LEFT OUTER JOIN skill AS sk ON i.cf=sk.impiegato) LEFT OUTER JOIN titolo AS t ON sk.idtitolo=t.idtitolo) WHERE nome LIKE '"+nomeInserito+"' AND cognome LIKE '"+cognomeInserito+"' AND (false ";
        
        for (String s:skillSelezionate) {
        	daEseguire = daEseguire + "OR t.tipotitolo LIKE '" + s + "'";
        }
        
        daEseguire = daEseguire + ") AND i.cf NOT IN(SELECT cf FROM progettoimpiegato AS pi WHERE pi.idprogetto = "+idProgetto+") GROUP BY cf HAVING AVG(quantita) BETWEEN "+(salarioMedio-200)+" AND "+(salarioMedio+200) + " ORDER BY " + ordinamentoSelezionato;
        
        ResultSet rs = getImpiegatiConSalarioMedio.executeQuery(daEseguire);
        
  
        
        
     	while(rs.next())
        	{
     			impiegato=new Impiegato();
        		impiegato.setNome(rs.getString("nome"));
        		impiegato.setCognome(rs.getString("cognome"));
        		impiegato.setCF(rs.getString("cf"));
        		impiegato.setComuneNascita(rs.getString("comunen"));
        		impiegato.setEmail(rs.getString("email"));
        		impiegato.setDataNascita(rs.getDate("datan").toLocalDate());
        		impiegati.add(impiegato);
        	}
        	rs.close();
        	return impiegati;
        }
        else {
            ObservableList<Impiegato> impiegati = FXCollections.observableArrayList();
            Impiegato impiegato = new Impiegato();
        
            String daEseguire = "SELECT DISTINCT nome, cognome, cf, comunen, email, datan FROM((impiegato AS i LEFT OUTER JOIN skill AS sk ON i.cf=sk.impiegato) LEFT OUTER JOIN titolo AS t ON sk.idtitolo=t.idtitolo) WHERE nome LIKE '"+nomeInserito+"' AND cognome LIKE '"+cognomeInserito+"' AND (false ";
            
            for (String s:skillSelezionate) {
            	daEseguire = daEseguire + "OR t.tipotitolo LIKE '" + s + "'";
            }
            
            daEseguire = daEseguire + ")AND i.cf NOT IN(SELECT cf FROM progettoimpiegato AS pi WHERE pi.idprogetto = "+idProgetto+")GROUP BY cf ORDER BY " + ordinamentoSelezionato;
            
            ResultSet rs = getImpiegatiSenzaSalarioMedio.executeQuery(daEseguire);

            while(rs.next())
            	{
         			impiegato=new Impiegato();
            		impiegato.setNome(rs.getString("nome"));
            		impiegato.setCognome(rs.getString("cognome"));
            		impiegato.setCF(rs.getString("cf"));
            		impiegato.setComuneNascita(rs.getString("comunen"));
            		impiegato.setEmail(rs.getString("email"));
            		impiegato.setDataNascita(rs.getDate("datan").toLocalDate());
            		impiegati.add(impiegato);
            	}
            	rs.close();
            	return impiegati;
        }
    }
    
    
    
    
    
    public ObservableList<Impiegato> getAllImpiegatiSenzaCampi(float salarioMedio, String nomeInserito, String cognomeInserito, String ordinamentoSelezionato, ObservableList<String> skillSelezionate, int numeroDiSkill, int idProgetto) throws SQLException{
    	
        
    if(salarioMedio != -1) {	

        ObservableList<Impiegato> impiegati = FXCollections.observableArrayList();
        Impiegato impiegato = new Impiegato();
    
        String daEseguire = "SELECT DISTINCT nome, cognome, cf, comunen, email, datan, AVG(quantita) AS salarioMedio FROM (((impiegato AS i LEFT OUTER JOIN salario AS s ON i.cf = s.impiegato) LEFT OUTER JOIN skill AS sk ON i.cf=sk.impiegato) LEFT OUTER JOIN titolo AS t ON sk.idtitolo=t.idtitolo) WHERE nome LIKE '"+nomeInserito+"' AND cognome LIKE '"+cognomeInserito+"' AND (true ";
        
        for (String s:skillSelezionate) {
        	daEseguire = daEseguire + "OR t.tipotitolo LIKE '" + s + "'";
        }
        
        daEseguire = daEseguire + ") AND i.cf NOT IN(SELECT cf FROM progettoimpiegato AS pi WHERE pi.idprogetto = "+idProgetto+") GROUP BY cf HAVING AVG(quantita) BETWEEN "+(salarioMedio-200)+" AND "+(salarioMedio+200) + " ORDER BY " + ordinamentoSelezionato;
        
        ResultSet rs = getImpiegatiConSalarioMedio.executeQuery(daEseguire);
        
  
        
        
     	while(rs.next())
        	{
     			impiegato=new Impiegato();
        		impiegato.setNome(rs.getString("nome"));
        		impiegato.setCognome(rs.getString("cognome"));
        		impiegato.setCF(rs.getString("cf"));
        		impiegato.setComuneNascita(rs.getString("comunen"));
        		impiegato.setEmail(rs.getString("email"));
        		impiegato.setDataNascita(rs.getDate("datan").toLocalDate());
        		impiegati.add(impiegato);
        	}
        	rs.close();
        	return impiegati;
        }
        else {
            ObservableList<Impiegato> impiegati = FXCollections.observableArrayList();
            Impiegato impiegato = new Impiegato();
        
            String daEseguire = "SELECT DISTINCT nome, cognome, cf, comunen, email, datan FROM((impiegato AS i LEFT OUTER JOIN skill AS sk ON i.cf=sk.impiegato) LEFT OUTER JOIN titolo AS t ON sk.idtitolo=t.idtitolo) WHERE nome LIKE '"+nomeInserito+"' AND cognome LIKE '"+cognomeInserito+"' AND (true ";
            
            for (String s:skillSelezionate) {
            	daEseguire = daEseguire + "OR t.tipotitolo LIKE '" + s + "'";
            }
            
            daEseguire = daEseguire + ")AND i.cf NOT IN(SELECT cf FROM progettoimpiegato AS pi WHERE pi.idprogetto = "+idProgetto+")GROUP BY cf ORDER BY " + ordinamentoSelezionato;
            
            ResultSet rs = getImpiegatiSenzaSalarioMedio.executeQuery(daEseguire);

            while(rs.next())
            	{
         			impiegato=new Impiegato();
            		impiegato.setNome(rs.getString("nome"));
            		impiegato.setCognome(rs.getString("cognome"));
            		impiegato.setCF(rs.getString("cf"));
            		impiegato.setComuneNascita(rs.getString("comunen"));
            		impiegato.setEmail(rs.getString("email"));
            		impiegato.setDataNascita(rs.getDate("datan").toLocalDate());
            		impiegati.add(impiegato);
            	}
            	rs.close();
            	return impiegati;
        }
    }
}
