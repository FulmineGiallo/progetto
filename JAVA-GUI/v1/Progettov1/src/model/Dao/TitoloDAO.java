package model.Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.DaoInterface.TitoloDaoInterface;
import model.Impiegato;
import model.Titolo;

public class TitoloDao implements TitoloDaoInterface {
	
	Connection connection;
    private Statement getTitoli;
    private Statement getTitoliImpiegato;
    
    private final PreparedStatement queryIdTitolo;
    
    public TitoloDao(Connection connection) throws SQLException
    {
        this.connection = connection;
        getTitoli = connection.createStatement();
        getTitoliImpiegato = connection.createStatement();
        
        queryIdTitolo = connection.prepareStatement("SELECT tipotitolo FROM titolo WHERE idtitolo = ?");
    }

	@Override
	public ObservableList<Titolo> titoliList() throws SQLException {
        ObservableList<Titolo> lista = FXCollections.observableArrayList();

        ResultSet rs = getTitoli.executeQuery("SELECT tipotitolo FROM titolo ORDER BY tipotitolo ASC");

        while (rs.next()) {
            lista.add(new Titolo(rs.getString("tipotitolo"), false));
        }
        
        Titolo titoloAltro = new Titolo("Altro...", false);
        lista.add(titoloAltro);
        
        rs.close();
        return lista;
	}

	public ObservableList<Titolo> titoliListImpiegato(Impiegato impiegato) throws SQLException {
        ObservableList<Titolo> lista = FXCollections.observableArrayList();

        ResultSet rs = getTitoliImpiegato.executeQuery("SELECT tipotitolo FROM titolo NATURAL JOIN skill WHERE impiegato LIKE '"+ impiegato.getCF() + "'");

        
        while (rs.next()) {
            lista.add(new Titolo(rs.getString("tipotitolo"), false));
        }
        
        
        rs.close();
        return lista;
	}
	
	public Titolo getTitoloById(int idTitolo) throws SQLException{
		Titolo titolo = null;
		
		queryIdTitolo.setInt(1, idTitolo);
		ResultSet rs = queryIdTitolo.executeQuery();
		
		while(rs.next())
			titolo = new Titolo(rs.getString("tipotitolo"), false);
		
		return titolo;
	}
}
