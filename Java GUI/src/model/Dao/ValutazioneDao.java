package model.Dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.DaoInterface.ImpiegatoDaoInterface;
import model.DaoInterface.ProgettoDaoInterface;
import model.DaoInterface.RiunioneDaoInterface;
import model.DaoInterface.ValutazioneDaoInterface;
import model.Impiegato;
import model.Progetto;
import model.Riunione;
import model.Valutazione;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class ValutazioneDao implements ValutazioneDaoInterface
{
    private Connection connection;
    
    private PreparedStatement getValutazioni;
    private PreparedStatement insertNuovaValutazioneProgetto;
    private PreparedStatement insertNuovaValutazioneRiunione;
    
    private ImpiegatoDaoInterface	impiegatoDao;
    private ProgettoDaoInterface	progettoDao;
    private RiunioneDaoInterface	riunioneDao;
    
    public ValutazioneDao(Connection connection) throws SQLException
    {
        this.connection = connection;
        
        impiegatoDao	= new ImpiegatoDao(connection);
        progettoDao		= new ProgettoDao(connection);
        riunioneDao		= new RiunioneDao(connection);
        
        getValutazioni	= connection.prepareStatement("SELECT * FROM listavalutazioni WHERE cfrecensito = ?");
        
        insertNuovaValutazioneProgetto = connection.prepareStatement("INSERT INTO valutazioneprogettoimpiegato "			+
        															 "VALUES (NEXTVAL('id_valutazione_progetto_seq'), "	+
        															 "?, ? , null, ?, ?, ?)");
        
        insertNuovaValutazioneRiunione = connection.prepareStatement("INSERT INTO valutazioneriunioneimpiegato "			+
																	 "VALUES (NEXTVAL('id_valutazione_riunione_seq'), "	+
																	 "?, ? , null, ?, ?, ?)");
    }

    @Override
    public ObservableList<Valutazione> getValutazioni(Impiegato impiegato) throws SQLException {
        ObservableList<Valutazione> lista = FXCollections.observableArrayList();
        Valutazione valutazione;
        
        getValutazioni.setString(1, impiegato.getCF());
        ResultSet rs = getValutazioni.executeQuery();

        while (rs.next()) {
            valutazione = new Valutazione(impiegatoDao.creaImpiegato(rs.getString("cfrecensore")), impiegato,
            							  rs.getString("titolo"), rs.getInt("stelle"), false);
            
            valutazione.setDataValutazione(rs.getObject("datavalutazione", LocalDate.class));
            valutazione.setRecensione(rs.getString("recensione"));
            
            lista.add(valutazione);
        }
        
        rs.close();
        return lista;
    }

	@Override
	public String insertValutazione(Impiegato recensito, Progetto progetto, Valutazione nuovaValutazione) throws SQLException {
		
		int valutazioniInserite;
		
		if(nuovaValutazione.getRecensione() != null) {
    		insertNuovaValutazioneProgetto.setString(1, nuovaValutazione.getRecensione());
    	} else {
    		insertNuovaValutazioneProgetto.setNull(1, java.sql.Types.NULL);
    	}
		
		insertNuovaValutazioneProgetto.setInt	(2, nuovaValutazione.getStelle());
		insertNuovaValutazioneProgetto.setString(3, recensito.getCF());
		insertNuovaValutazioneProgetto.setInt	(4, progettoDao.getIdProgetto(progetto));
		insertNuovaValutazioneProgetto.setString(5, nuovaValutazione.getTitolo());
		
		valutazioniInserite = insertNuovaValutazioneProgetto.executeUpdate();
		
		return "Valutazioni inserite: " + valutazioniInserite;
	}

	@Override
	public String insertValutazione(Impiegato recensito, Riunione riunione, Valutazione nuovaValutazione) throws SQLException {
		
		int valutazioniInserite;
		
		if(nuovaValutazione.getRecensione() != null) {
    		insertNuovaValutazioneRiunione.setString(1, nuovaValutazione.getRecensione());
    	} else {
    		insertNuovaValutazioneRiunione.setNull(1, java.sql.Types.NULL);
    	}
		
		insertNuovaValutazioneRiunione.setInt	(2, nuovaValutazione.getStelle());
		insertNuovaValutazioneRiunione.setString(3, recensito.getCF());
		insertNuovaValutazioneRiunione.setInt	(4, riunioneDao.getIdRiunione(riunione));
		insertNuovaValutazioneRiunione.setString(5, nuovaValutazione.getTitolo());
		
		valutazioniInserite = insertNuovaValutazioneRiunione.executeUpdate();
		
		return "Valutazioni inserite: " + valutazioniInserite;
	}
}
