package model.Dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Comune;
import model.DaoInterface.ComuneDaoInterface;
import model.Grado;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ComuneDao implements ComuneDaoInterface
{
    Connection connection;
    private final PreparedStatement getComuni;
    public ComuneDao(Connection connection) throws SQLException
    {
        this.connection = connection;
        this.getComuni = connection.prepareStatement("SELECT nomecomune, sigla FROM comune WHERE codprovincia = ?");
    }

    @Override
    public ObservableList<Comune> gradoList(String provincia) throws SQLException
    {
        getComuni.setString(1, provincia);
        Comune comune;
        ObservableList<Comune> lista = FXCollections.observableArrayList();
        ResultSet rs = getComuni.executeQuery();


        while ( rs.next() )
        {
            comune = new Comune(rs.getString("nomecomune"),rs.getString("sigla"));
            lista.add(comune);
        }
        rs.close();

        return lista;
    }
}
