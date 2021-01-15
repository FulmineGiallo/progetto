package model.Dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.DaoInterface.TitoloDaoInterface;
import model.Titolo;

public class TitoloDAO implements TitoloDaoInterface {
	
	Connection connection;
    private Statement getTitoli;

    public TitoloDAO(Connection connection) throws SQLException
    {
        this.connection = connection;
        getTitoli = connection.createStatement();
    }

	@Override
	public ObservableList<Titolo> titoliList() throws SQLException {
        ObservableList<Titolo> lista = FXCollections.observableArrayList();

        ResultSet rs = getTitoli.executeQuery("SELECT tipotitolo FROM titolo");

        while (rs.next()) {
            lista.add(new Titolo(rs.getString("tipotitolo")));
        }

        rs.close();
        return lista;
	}

}
