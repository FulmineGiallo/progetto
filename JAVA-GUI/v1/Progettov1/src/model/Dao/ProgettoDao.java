package model.Dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Comune;
import model.DaoInterface.ProgettoDaoInterface;
import model.Impiegato;
import model.Progetto;

import java.sql.*;

public class ProgettoDao implements ProgettoDaoInterface
{
    private final Connection connection;
    private final PreparedStatement progettiImpiegato;
    private final PreparedStatement insertProgetto;
    public ProgettoDao(Connection connection) throws SQLException {
        this.connection = connection;
        progettiImpiegato = connection.prepareStatement("SELECT progetto.titolo, progetto.descrizione, progetto.datainizio, progetto.datafine, progetto.scadenza, progetto.projectmanagerprogetto FROM progetto  WHERE projectmanagerprogetto = ?");
        insertProgetto = connection.prepareStatement("INSERT INTO progetto VALUES (NEXTVAL('id_progetto_seq'), ?, ?, current_date, ?, ?,?, ?)");
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
            if(impiegato.getCF().equals(rs.getString("projectmanagerprogetto")))
            {
                progetto.setProjectManager(impiegato);
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
        insertProgetto.setDate(3, (Date) progetto.getDataFine());
        insertProgetto.setDate(4, (Date) progetto.getScadenza());
        insertProgetto.setString(5,progetto.getProjectManager().getCF());
        return row;
    }
}
