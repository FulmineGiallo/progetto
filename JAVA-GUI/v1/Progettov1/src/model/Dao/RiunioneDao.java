package model.Dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.DaoInterface.RiunioneDaoInterface;
import model.Impiegato;
import model.Riunione;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RiunioneDao implements RiunioneDaoInterface
{

	private final Connection connection;
    private final PreparedStatement riunioniImpiegato;

    public RiunioneDao(Connection connection) throws SQLException {
        this.connection = connection;
        riunioniImpiegato = connection.prepareStatement("SELECT r.orarioinizio, r.orariofine, r.data, r.titolo, i.cognome , i.nome FROM riunione as r JOIN impiegato as i ON r.organizzatore = i.cf WHERE CF = ?");
    }

    @Override
    public ObservableList<Riunione> getRiunioniImpiegato(Impiegato impiegato) throws SQLException
    {
        ObservableList<Riunione> lista = FXCollections.observableArrayList();
        Riunione riunione;
        riunioniImpiegato.setString(1,impiegato.getCF());
        ResultSet rs = riunioniImpiegato.executeQuery();

        while (rs.next())
        {
            riunione = new Riunione(rs.getString("titolo"));
            riunione.setOrarioInizio(rs.getTime("orarioinizio"));
            riunione.setOrarioFine(rs.getTime("orariofine"));
            riunione.setData(rs.getDate("data"));
            riunione.setOrganizzatore(rs.getString("nome"));
            riunione.setOrganizzatore(riunione.getOrganizzatore() + " " + rs.getString("cognome"));
            lista.add(riunione);
            
        }
        rs.close();

        return lista;
    }
	
	
}
