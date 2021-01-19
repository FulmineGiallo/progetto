package model.Dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.DaoInterface.ProgettoDaoInterface;
import model.Impiegato;
import model.Progetto;

import java.sql.*;
import java.time.LocalDate;

public class ProgettoDao implements ProgettoDaoInterface
{
    private final Connection connection;
    private final PreparedStatement progettiImpiegato;
    private final PreparedStatement insertProgetto;
    private final PreparedStatement updateInfo;

    public ProgettoDao(Connection connection) throws SQLException {
        this.connection = connection;
        progettiImpiegato = connection.prepareStatement("SELECT * FROM listaprogetti  WHERE partecipante = ?");
        insertProgetto = connection.prepareStatement("INSERT INTO progetto VALUES (NEXTVAL('id_progetto_seq'), ?, ?, current_date, ?, ?,?, ?)");
        updateInfo = connection.prepareStatement("UPDATE progetto SET descrizione = ?, datainizio = ?, datafine = ?, scadenza = ? WHERE projectmanagerprogetto = ? AND titolo = ?");

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
            if(impiegato.getCF().equals(rs.getString("projectmanager")))
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
        insertProgetto.setDate(3, progetto.getDataFine());
        insertProgetto.setDate(4, progetto.getScadenza());
        insertProgetto.setString(5,progetto.getProjectManager().getCF());
        return row;
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
}
