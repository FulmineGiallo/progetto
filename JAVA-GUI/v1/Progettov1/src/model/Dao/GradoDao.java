package model.Dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;
import model.DaoInterface.GradoDaoInterface;
import model.Grado;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GradoDao implements GradoDaoInterface
{
    Connection connection;
    private PreparedStatement getGradi;

    public GradoDao(Connection connection) throws SQLException
    {
        this.connection = connection;
        getGradi = connection.prepareStatement("SELECT tipogrado FROM grado");
    }
    @Override

    public ObservableList<Grado> gradoList() throws SQLException
    {
        ObservableList<Grado> lista = FXCollections.observableArrayList();
        Grado grado;

        ResultSet rs = getGradi.executeQuery();
        int i = 0;

        while (rs.next())
        {
            grado = new Grado(rs.getString("tipogrado"));
            lista.addAll(grado);
            i++;
        }

        rs.close();
        return lista;
    }
}
