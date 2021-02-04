package model.Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Ruolo;
import model.DaoInterface.RuoloDaoInterface;

public class RuoloDao implements RuoloDaoInterface{

    Connection connection;
    private Statement getRuoli;
    private PreparedStatement getIdRuoloStatement;
    
	
    public RuoloDao(Connection connection) throws SQLException
    {
        this.connection = connection;
        getRuoli = connection.createStatement();
        getIdRuoloStatement = connection.prepareStatement("SELECT idruolo FROM ruolo WHERE tiporuolo LIKE ?");
    }
	
	public ObservableList<Ruolo> GetAllRuoli() throws SQLException{
   
		ObservableList<Ruolo> lista = FXCollections.observableArrayList();
        Ruolo ruolo;

        ResultSet rs = getRuoli.executeQuery("SELECT tipoRuolo FROM Ruolo");

        while (rs.next())
        {
            ruolo = new Ruolo(rs.getString("tipoRuolo"));
            lista.addAll(ruolo);
        }

        rs.close();
        return lista;
	}
	
	
	 public int getIdRuolo(Ruolo ruolo) throws SQLException{
		 
		 int idRuolo=-1;
		 
		 getIdRuoloStatement.setString(1, ruolo.getTipoRuolo());
		 
		 ResultSet rs = getIdRuoloStatement.executeQuery();
		 
		 while(rs.next()) {
			 idRuolo = rs.getInt("idRuolo");
		 }
		 
		 return idRuolo;
	 }
}
