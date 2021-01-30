package model.Dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.DaoInterface.ProgettoDaoInterface;
import model.Impiegato;
import model.Progetto;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

import javax.naming.ReferralException;

public class ProgettoDao implements ProgettoDaoInterface
{
    private final Connection connection;
    private final PreparedStatement progettiImpiegato;
    private final PreparedStatement insertProgetto;
    private final PreparedStatement updateInfo;
    private final PreparedStatement partecipanti;
    private final PreparedStatement getId;
    private final PreparedStatement getRuoloImpiegato;
    private final PreparedStatement getIdProgetto;


    public ProgettoDao(Connection connection) throws SQLException {
        this.connection = connection;
        getIdProgetto = connection.prepareStatement("SELECT DISTINCT idprogetto FROM listaprogetti WHERE partecipante = ? OR projectmanager = ?");
        progettiImpiegato = connection.prepareStatement("SELECT * FROM listaprogetti  WHERE partecipante = ?");
        insertProgetto = connection.prepareStatement("INSERT INTO progetto VALUES (NEXTVAL('id_progetto_seq'), ?, ?, current_date, ?, ?,?, ?)");
        updateInfo = connection.prepareStatement("UPDATE progetto SET descrizione = ?, datainizio = ?, datafine = ?, scadenza = ? WHERE projectmanagerprogetto = ? AND titolo = ?");
        partecipanti = connection.prepareStatement("SELECT impiegato.*FROM progetto NATURAL JOIN progettoimpiegato INNER JOIN impiegato ON progettoimpiegato.cf = impiegato.cf WHERE projectmanagerprogetto = ? AND titolo = ?");
        getId = connection.prepareStatement("SELECT idprogetto FROM progetto WHERE titolo LIKE ? AND descrizione LIKE ? AND datainizio = ? AND datafine = ? AND scadenza = ? AND projectmanagerprogetto LIKE ?");
        getRuoloImpiegato = connection.prepareStatement("SELECT ruolo.tipoRuolo FROM progettoImpiegato NATURAL JOIN ruolo WHERE cf = ? AND idProgetto = ?");
    }


    @Override
    public ObservableList<Progetto> getProgettiImpiegato(Impiegato impiegato) throws SQLException
    {
        ObservableList<Progetto> lista = FXCollections.observableArrayList();
        Progetto progetto;

        progettiImpiegato.setString(1,impiegato.getCF());
        ResultSet rs = progettiImpiegato.executeQuery();

        while (rs.next())
        {
            progetto = new Progetto(rs.getString("titolo"));
            progetto.setDataInizio(rs.getDate("datainizio"));
            progetto.setScadenza(rs.getDate("scadenza"));
            progetto.setDataFine(rs.getDate("datafine"));
            progetto.setDescrizione(rs.getString("descrizione"));
            progetto.setRuolo(rs.getString("ruolo"));
            if(impiegato.getCF().equals(rs.getString("projectmanager")))
            {
                progetto.setProjectManager(impiegato);
                progetto.setRuolo("Project Manager");
            }

            lista.add(progetto);
        }
        rs.close();
        return lista;
    }

    @Override
    public int creaProgetto(Progetto progetto) throws SQLException
    {
        int row = 0;
        insertProgetto.setString(1, progetto.getTitolo());
        insertProgetto.setString(2,progetto.getDescrizione());
        insertProgetto.setDate(3, progetto.getDataFine());
        insertProgetto.setDate(4, progetto.getScadenza());
        insertProgetto.setString(5,progetto.getProjectManager().getCF());
        return row;
    }

    @Override
    public ObservableList<Impiegato> getPartecipanti(Progetto progetto) throws SQLException
    {

        ObservableList<Impiegato> lista = FXCollections.observableArrayList();
        partecipanti.setString(1,progetto.getProjectManager().getCF());
        partecipanti.setString(2,progetto.getTitolo());

        ResultSet rs = partecipanti.executeQuery();
        Impiegato impiegato;
        while(rs.next())
        {

            impiegato = new Impiegato(rs.getString("cf"));
            impiegato.setNome(rs.getString("nome"));
            impiegato.setCognome(rs.getString("cognome"));
            impiegato.setComuneNascita(rs.getString("comunen"));
            impiegato.setGenere(rs.getString("genere"));
            impiegato.setEmail(rs.getString("email"));
            impiegato.setDataNascita(rs.getObject("datan", LocalDate.class));
            impiegato.setPassword(rs.getString("password"));
            impiegato.setIdImpiegato(rs.getInt("idimpiegato"));
            lista.add(impiegato);
        }
        rs.close();

        return lista;
    }

    @Override
    public int updateInfoProgetto(Progetto progetto) throws SQLException
    {
        updateInfo.setString(1,progetto.getDescrizione());
        updateInfo.setDate(2, progetto.getDataInizio());
        updateInfo.setDate(3, progetto.getDataFine());
        updateInfo.setDate(4, progetto.getScadenza());
        updateInfo.setString(5, progetto.getProjectManager().getCF());
        updateInfo.setString(6, progetto.getTitolo());
        int result = updateInfo.executeUpdate();

        System.out.println("Data inizio Update " + progetto.getDataInizio());

        return result;
    }
    
    public int GetIdProgetto(Progetto progetto)throws SQLException {
    	
    	int id=0;
    	
    	getId.setString(1, progetto.getTitolo());
    	getId.setString(2, progetto.getDescrizione());
    	getId.setDate(3, progetto.getDataInizio());
    	getId.setDate(4, progetto.getDataFine());
    	getId.setDate(5, progetto.getScadenza());
    	getId.setString(6, progetto.getProjectManager().getCF());
    	
    	ResultSet rs = getId.executeQuery();
    	
    	while(rs.next()) {
    		id=rs.getInt("idprogetto");
    	}
    	
    	return id;
    }
}
