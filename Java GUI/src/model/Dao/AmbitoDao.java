package model.Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Ambito;
import model.Comune;
import model.Tipologia;
import model.DaoInterface.AmbitoDaoInterface;
import model.DaoInterface.ComuneDaoInterface;

public class AmbitoDao implements AmbitoDaoInterface {
	
    Connection connection;
    private final PreparedStatement getAmbiti;
    private final PreparedStatement queryAmbitiProgetto;
    
    public AmbitoDao(Connection connection) throws SQLException
    {
        this.connection = connection;
        this.getAmbiti = connection.prepareStatement("SELECT tipoambito FROM ambito ORDER BY tipoambito ASC");
        queryAmbitiProgetto = connection.prepareStatement("SELECT tipoambito FROM compambiti NATURAL JOIN ambito WHERE idprogetto = ?");
    }

    @Override
    public ObservableList<Ambito> AmbitoList() throws SQLException {
        
        ObservableList<Ambito> lista = FXCollections.observableArrayList();
        ResultSet rs = getAmbiti.executeQuery();

        while (rs.next())
            lista.add(new Ambito(rs.getString("tipoambito"), false));
        
        rs.close();

        return lista;
    }
    
    @Override
    public ObservableList<Ambito> getAmbitiProgetto(int idProgetto) throws SQLException {
    	
    	ObservableList<Ambito> lista = FXCollections.observableArrayList();
    	queryAmbitiProgetto.setInt(1, idProgetto);
        ResultSet rs = queryAmbitiProgetto.executeQuery();

        while (rs.next())
            lista.add(new Ambito(rs.getString("tipoambito"), false));
        
        rs.close();

        return lista;
    }
}
