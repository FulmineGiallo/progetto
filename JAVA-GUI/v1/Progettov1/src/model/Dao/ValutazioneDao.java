package model.Dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.DaoInterface.ValutazioneDaoInterface;
import model.Impiegato;
import model.Progetto;
import model.Valutazione;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ValutazioneDao implements ValutazioneDaoInterface
{
    Connection connection;
    PreparedStatement getValutazioni;
    ImpiegatoDao impiegatoDao;
    public ValutazioneDao(Connection connection) throws SQLException
    {
        this.connection = connection;
        getValutazioni = connection.prepareStatement("SELECT * FROM valutazioni WHERE cfrecensito = ?");
    }

    @Override
    public ObservableList<Valutazione> getValutazioni(Impiegato impiegato) throws SQLException {
        ObservableList<Valutazione> lista = FXCollections.observableArrayList();
        Valutazione valutazione;
        getValutazioni.setString(1, impiegato.getCF());
        ResultSet rs = getValutazioni.executeQuery();

        while (rs.next())
        {
            valutazione = new Valutazione();
            valutazione.setTitolo(rs.getString("titolo"));
            valutazione.setRecensito(impiegato);
            valutazione.setCFrecensore(rs.getString("cfrecensore"));
            valutazione.setDataV(rs.getDate("datavalutazione"));
            valutazione.setStelle(rs.getInt("stelle"));
            valutazione.setRecensione(rs.getString("recensione"));
            lista.add(valutazione);
        }
        rs.close();
        return lista;
    }
}
