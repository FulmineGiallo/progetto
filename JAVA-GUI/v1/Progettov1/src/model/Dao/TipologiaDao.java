package model.Dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Tipologia;
import model.DaoInterface.TipologiaDaoInterface;

public class TipologiaDao implements TipologiaDaoInterface{
	
	private 	  Connection connection;
	private final Statement  queryTipologie;
	
	public TipologiaDao(Connection connection) throws SQLException{
		this.connection = connection;
		queryTipologie = connection.createStatement();
	}

	@Override
	public ObservableList<Tipologia> getListaTipologie() throws SQLException {
		ObservableList<Tipologia> listaTipologie = FXCollections.observableArrayList();
		ResultSet rs = queryTipologie.executeQuery("SELECT tipoprogetto FROM tipologia");
		
		while(rs.next()) {
			listaTipologie.add(new Tipologia(rs.getString("tipoprogetto"), false));
		}
		
		rs.close();
		
		listaTipologie.add(new Tipologia("Altro...", false));
		return listaTipologie;
		
	}

}
