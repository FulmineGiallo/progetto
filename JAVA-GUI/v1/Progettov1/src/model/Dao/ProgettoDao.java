package model.Dao;

import model.DaoInterface.ProgettoDaoInterface;
import model.Impiegato;
import model.Progetto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ProgettoDao implements ProgettoDaoInterface {
    private final Connection connection;
    private final PreparedStatement progettiImpiegato;

    public ProgettoDao(Connection connection) throws SQLException {
        this.connection = connection;
        progettiImpiegato = connection.prepareStatement("");
    }

    @Override
    public Progetto getProgettiImpiegato(Impiegato impiegato) throws SQLException
    {

        return null;
    }
}
