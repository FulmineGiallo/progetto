package model.Dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Comune;
import model.DaoInterface.ProgettoDaoInterface;
import model.Impiegato;
import model.Progetto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProgettoDao implements ProgettoDaoInterface
{
    private final Connection connection;
    private final PreparedStatement progettiImpiegato;

    public ProgettoDao(Connection connection) throws SQLException {
        this.connection = connection;
        progettiImpiegato = connection.prepareStatement("SELECT progetto.titolo, progetto.descrizione, progetto.datainizio, progetto.datafine, progetto.scadenza FROM impiegato NATURAL JOIN progettoimpiegato NATURAL JOIN progetto WHERE CF = ?");
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

            lista.add(progetto);
        }
        rs.close();

        return lista;
    }
}
