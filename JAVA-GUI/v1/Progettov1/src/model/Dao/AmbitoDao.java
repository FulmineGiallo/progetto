package model.Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Ambito;
import model.Comune;
import model.DaoInterface.AmbitoDaoInterface;
import model.DaoInterface.ComuneDaoInterface;

public class AmbitoDao implements AmbitoDaoInterface {
    Connection connection;
    private final PreparedStatement getAmbiti;
    public AmbitoDao(Connection connection) throws SQLException
    {
        this.connection = connection;
        this.getAmbiti = connection.prepareStatement("SELECT tipoambito FROM ambito");
    }

    @Override
    public ObservableList<Ambito> AmbitoList() throws SQLException
    {
        
        Ambito ambito;
        ObservableList<Ambito> lista = FXCollections.observableArrayList();
        ResultSet rs = getAmbiti.executeQuery();


        while ( rs.next() )
        {
            ambito = new Ambito(rs.getString("tipoambito"));
            lista.add(ambito);
        }
        rs.close();

        return lista;
    }
}
