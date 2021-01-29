package model.Dao;

import model.DaoInterface.ImpiegatoDaoInterface;
import model.Impiegato;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
    private final PreparedStatement insertImpiegato;
    private final PreparedStatement insertListaTitoli;
    private final PreparedStatement insertListaSkill;
    
    private 	  String 			direttoreRisorseUmane = "";
    private		  int				idGradoNuovoImpiegato;

    public ImpiegatoDao(Connection connection) throws SQLException
    {
        this.connection = connection;
        getImpiegati = connection.prepareStatement("SELECT * FROM impiegato");
        getNome = connection.prepareStatement("SELECT nome FROM impiegato WHERE email = ?");
        getCognome = connection.prepareStatement("SELECT cognome FROM impiegato WHERE email = ?");
        loginImpiegato = connection.prepareStatement("SELECT COUNT(*) FROM impiegatoaccount WHERE email = ? AND password = ?");
        getImpiegato = connection.prepareStatement("SELECT * FROM impiegato WHERE cf = ?");
        getCF = connection.prepareStatement("SELECT CF FROM impiegato WHERE email = ?");
        getTipoGrado = connection.prepareStatement("SELECT tipogrado FROM impiegatoazienda WHERE cf = ?");
        getIdGrado = connection.prepareStatement("SELECT idgrado FROM grado WHERE tipoGrado = ?");
        insertImpiegato = connection.prepareStatement("INSERT INTO Impiegato VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, NEXTVAL('id_impiegato_seq');");
        insertListaTitoli = connection.prepareStatement("INSERT INTO Titolo VALUES (NEXTVAL('id_titolo_seq'), ?);");
        insertListaSkill = connection.prepareStatement("INSERT INTO Skill VALUES (NEXTVAL('id_skill_seq'), ?, ?, ?, ?, ?)"); //da fare
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
    public int insertRegistrazione(Impiegato nuovoImpiegato) throws SQLException{ // >> DA FARE
    	
        /*getIdGrado.setString(1, nuovoImpiegato.getGrado());
        ResultSet rs = getIdGrado.executeQuery();
        
        while(rs.next()) {
        	idGradoNuovoImpiegato = rs.getInt("idgrado");
        }
        
        rs.close();*/
    	
    	//inserimento del nuovo impiegato
    	insertImpiegato.setString(1, nuovoImpiegato.getNome());
    	insertImpiegato.setString(2, nuovoImpiegato.getCognome());
    	insertImpiegato.setString(3, nuovoImpiegato.getCF());
    	insertImpiegato.setObject(4, nuovoImpiegato.getDataNascita());
    	insertImpiegato.setString(5, nuovoImpiegato.getComuneNascita());
    	insertImpiegato.setString(6, nuovoImpiegato.getEmail());
    	insertImpiegato.setString(7, nuovoImpiegato.getGenere());
    	//insertImpiegato.setInt	 (8, nuovoImpiegato.getGrado()); >> creare trigger in cui recupera l'id del grado e l'id del comune a partire dalle stringhe
    	insertImpiegato.setString(9, nuovoImpiegato.getPassword());
    	
    	//inserimento dei nuovi titoli >> DA FARE
    	//insertListaTitoli
    	
    	//inserimento delle skill >> DA FARE
    	//insertListaSkill
    	
        return 0;
    }

    public List<Impiegato> getAllImpiegati() throws SQLException
    {
        ResultSet rs = getImpiegati.executeQuery();
        List<Impiegato> list = new ArrayList<Impiegato>();

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
}
