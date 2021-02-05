package model.Dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.DaoInterface.ImpiegatoDaoInterface;
import model.DaoInterface.ValutazioneDaoInterface;
import model.Impiegato;
import model.Progetto;
import model.Valutazione;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class ValutazioneDao implements ValutazioneDaoInterface
{
    Connection connection;
    PreparedStatement getValutazioni;
    ImpiegatoDaoInterface impiegatoDao;
    public ValutazioneDao(Connection connection) throws SQLException
    {
        this.connection = connection;
        impiegatoDao = new ImpiegatoDao(connection);
        getValutazioni = connection.prepareStatement("SELECT * FROM listavalutazioni WHERE cfrecensito = ?");
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
}
