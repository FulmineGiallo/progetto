package model.Dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.DaoInterface.AmbitoDaoInterface;
import model.DaoInterface.ImpiegatoDaoInterface;
import model.DaoInterface.ProgettoDaoInterface;
import utilities.MetodiComuni;
import model.Ambito;
import model.Impiegato;
import model.Progetto;
import model.Skill;
import model.Tipologia;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

import javax.naming.ReferralException;

public class ProgettoDao implements ProgettoDaoInterface
{
    private final Connection 		connection;
    private final PreparedStatement progettiImpiegato;
    private final PreparedStatement insertNuovoProgetto;
    private final PreparedStatement getIdTipologia;
    private final PreparedStatement insertNuovaTipologia;
    private final PreparedStatement getIdAmbito;
    private final PreparedStatement insertNuovoAmbito;
    private final PreparedStatement insertAmbitiProgetto;
    private final PreparedStatement updateInfo;
    private final PreparedStatement partecipanti;
    private final PreparedStatement getIdProgetto;
    private final PreparedStatement getRuoloImpiegato;
    
    private ImpiegatoDaoInterface 	impiegatoDao;
    private AmbitoDaoInterface 		ambitoDao;

    private MetodiComuni utils = new MetodiComuni();

    public ProgettoDao(Connection connection) throws SQLException {
        this.connection = connection;
        impiegatoDao 	= new ImpiegatoDao(connection);
        ambitoDao 		= new AmbitoDao(connection);
        
        progettiImpiegato = connection.prepareStatement("SELECT * FROM listaprogetti WHERE partecipante = ?");
        updateInfo = connection.prepareStatement("UPDATE progetto SET descrizione = ?, datainizio = ?, datafine = ?, scadenza = ? WHERE projectmanagerprogetto = ? AND titolo = ?");
        partecipanti = connection.prepareStatement("SELECT impiegato.*FROM progetto NATURAL JOIN progettoimpiegato INNER JOIN impiegato ON progettoimpiegato.cf = impiegato.cf WHERE projectmanagerprogetto = ? AND titolo = ?");
        getRuoloImpiegato = connection.prepareStatement("SELECT ruolo.tipoRuolo FROM progettoImpiegato NATURAL JOIN ruolo WHERE cf = ? AND idProgetto = ?");
        
        getIdProgetto 			= connection.prepareStatement("SELECT idprogetto FROM progetto WHERE titolo LIKE ? AND projectmanagerprogetto LIKE ?");
        insertNuovoProgetto 	= connection.prepareStatement("INSERT INTO progetto VALUES (NEXTVAL('id_progetto_seq'), ?, ?, ?, NULL, ?, ?, ?)");
        getIdAmbito				= connection.prepareStatement("SELECT idambito FROM ambito WHERE tipoambito = ?");
        insertNuovoAmbito		= connection.prepareStatement("INSERT INTO ambito VALUES (NEXTVAL('id_ambito_seq'), ?)");
        getIdTipologia			= connection.prepareStatement("SELECT idtipologia FROM tipologia WHERE tipoprogetto = ?");
        insertNuovaTipologia	= connection.prepareStatement("INSERT INTO tipologia VALUES (NEXTVAL('id_tipologia_seq'), ?)");
        insertAmbitiProgetto	= connection.prepareStatement("INSERT INTO compambiti VALUES (?, ?)");
    }
    
    @Override
    public ObservableList<Progetto> getProgettiImpiegato(Impiegato impiegato) throws SQLException
    {
        ObservableList<Progetto> lista = FXCollections.observableArrayList();
        Progetto progetto = null;

        progettiImpiegato.setString(1, impiegato.getCF());
        ResultSet rs = progettiImpiegato.executeQuery();

        while (rs.next()) {
        	Tipologia tipoProgetto = new Tipologia(rs.getString("tipoprogetto"), false);
        	
        	if(rs.getString("ruolo").equals("Project Manager")) {
                progetto = new Progetto(impiegato,
										rs.getString("titolo"),
										rs.getObject("datainizio", LocalDate.class),
										rs.getObject("scadenza", LocalDate.class),
										tipoProgetto);
            } else {
                progetto = new Progetto(impiegatoDao.creaImpiegato(rs.getString("projectmanager")),
										rs.getString("titolo"),
										rs.getObject("datainizio", LocalDate.class),
										rs.getObject("scadenza", LocalDate.class),
										tipoProgetto);
            	
            }
        	
        	progetto.setDataFine(rs.getObject("datafine", LocalDate.class));
        	progetto.setDescrizione(rs.getString("descrizione"));
            
            progetto.setRuolo(rs.getString("ruolo")); //da eliminare
            
            progetto.setListaAmbiti(ambitoDao.getAmbitiProgetto(rs.getInt("idprogetto")));

            lista.add(progetto);
        }
        
        rs.close();
        return lista;
    }

    @Override
    public String creaProgetto(Progetto nuovoProgetto) throws SQLException {
    	
    	ResultSet rs;
    	
    	int progettiInseriti = 0;
    	int nuovaTipologiaInserita = 0;
    	int nuoviAmbitiInseriti = 0;
    	int ambitiProgettoInseriti = 0;
    	
    	int idTipologiaNuovoProgetto = 1;
    	Tipologia tipologiaNuovoProgetto = nuovoProgetto.getTipoProgetto();
    	
    	if(tipologiaNuovoProgetto.isNuovo()) {
    		insertNuovaTipologia.setString(1, tipologiaNuovoProgetto.toString());
    		nuovaTipologiaInserita = insertNuovaTipologia.executeUpdate();
    	}
    	
    	getIdTipologia.setString(1, tipologiaNuovoProgetto.toString());
    	rs = getIdTipologia.executeQuery();
    	
    	while(rs.next()) {
    		idTipologiaNuovoProgetto = rs.getInt("idtipologia");
    	}
    	
    	rs.close();
    	
    	insertNuovoProgetto.setString(1, nuovoProgetto.getTitolo());
    	
    	if(nuovoProgetto.getDescrizione() != null) {
    		insertNuovoProgetto.setString(2, nuovoProgetto.getDescrizione());
    	} else {
    		insertNuovoProgetto.setNull(2, java.sql.Types.NULL);
    	}
    	
    	insertNuovoProgetto.setObject(3, nuovoProgetto.getDataInizio());
    	insertNuovoProgetto.setObject(4, nuovoProgetto.getScadenza());
    	insertNuovoProgetto.setInt(5, idTipologiaNuovoProgetto);
    	insertNuovoProgetto.setString(6, nuovoProgetto.getProjectManager().getCF());
    	
    	progettiInseriti = insertNuovoProgetto.executeUpdate();
    	
    	int idNuovoProgetto = 1;
    	idNuovoProgetto = getIdProgetto(nuovoProgetto);
    	
    	int idAmbitoNuovoProgetto = 2;
		if (nuovoProgetto.getListaAmbiti() != null) {
			for (Ambito a : nuovoProgetto.getListaAmbiti()) {
				if (a.isNuovo()) {
					insertNuovoAmbito.setString(1, a.toString());
					nuoviAmbitiInseriti = nuoviAmbitiInseriti + insertNuovoAmbito.executeUpdate();
				}

				getIdAmbito.setString(1, a.toString());
				rs = getIdAmbito.executeQuery();

				while (rs.next()) {
					idAmbitoNuovoProgetto = rs.getInt("idambito");
				}

				rs.close();

				insertAmbitiProgetto.setInt(1, idNuovoProgetto);
				insertAmbitiProgetto.setInt(2, idAmbitoNuovoProgetto);

				ambitiProgettoInseriti = ambitiProgettoInseriti + insertAmbitiProgetto.executeUpdate();
			}
		}
		
		return "Progetti inseriti: "					+ String.valueOf(progettiInseriti)			+
         	   "\nNuove tipologie inserite: " 			+ String.valueOf(nuovaTipologiaInserita)	+
         	   "\nNuovi ambiti inseriti: "				+ String.valueOf(nuoviAmbitiInseriti)		+
			   "\nAmbiti per il progetto inseriti: " 	+ String.valueOf(ambitiProgettoInseriti);
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
        updateInfo.setObject(2, progetto.getDataInizio());
        updateInfo.setObject(3, progetto.getDataFine());
        updateInfo.setObject(4, progetto.getScadenza());
        updateInfo.setString(5, progetto.getProjectManager().getCF());
        updateInfo.setString(6, progetto.getTitolo());
        int result = updateInfo.executeUpdate();

        System.out.println("Data inizio Update " + progetto.getDataInizio());

        return result;
    }
    
    public int getIdProgetto(Progetto progetto) throws SQLException {
    	
    	int id = 0;
    	
    	getIdProgetto.setString(1, progetto.getTitolo());
    	getIdProgetto.setString(2, progetto.getProjectManager().getCF());
    	
    	ResultSet rs = getIdProgetto.executeQuery();
    	
    	while(rs.next()) {
    		id = rs.getInt("idprogetto");
    	}
    	
    	rs.close();
    	
    	return id;
    }
}
