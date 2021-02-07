package model.Dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;
import model.DaoInterface.GradoDaoInterface;
import model.Grado;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class GradoDao implements GradoDaoInterface
{
    Connection connection;
    private Statement getGradi;

    public GradoDao(Connection connection) throws SQLException
    {
        this.connection = connection;
        getGradi = connection.createStatement();
    }
    
    @Override
    public ObservableList<Grado> gradoList() throws SQLException
    {
        ObservableList<Grado> lista = FXCollections.observableArrayList();
        Grado grado;

        ResultSet rs = getGradi.executeQuery("SELECT tipogrado FROM gradidisponibili");

        while (rs.next())
        {
            grado = new Grado(rs.getString("tipogrado"));
            lista.addAll(grado);
        }

        rs.close();
        return lista;
    }
}
