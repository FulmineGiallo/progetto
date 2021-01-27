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
import java.time.LocalDate;

public class RiunioneDao implements RiunioneDaoInterface
{

	private final Connection connection;
    private final PreparedStatement riunioniImpiegato;
    private final PreparedStatement partecipanti;
    

    public RiunioneDao(Connection connection) throws SQLException {
        this.connection = connection;
        partecipanti = connection.prepareStatement("SELECT DISTINCT i.*FROM riunioneimpiegato AS ri NATURAL JOIN riunione JOIN impiegato AS i ON ri.partecipante = i.cf  WHERE organizzatore = ? AND titolo = ?");
        riunioniImpiegato = connection.prepareStatement("SELECT r.orarioinizio, r.organizzatore, r.descrizione, r.orariofine, r.data, r.titolo, i.cognome , i.nome FROM (impiegato as i join riunioneimpiegato as ri ON i.cf = ri.partecipante) JOIN riunione AS r ON ri.idriunione=r.idriunione WHERE CF = ?");
    }
    
    public ObservableList<Impiegato> getPartecipanti(Riunione riunione) throws SQLException
    {

        ObservableList<Impiegato> lista = FXCollections.observableArrayList();
        partecipanti.setString(1,riunione.getCFOrganizzatore());
        partecipanti.setString(2,riunione.getTitolo());

        ResultSet rs = partecipanti.executeQuery();
        Impiegato partecipante;
        while(rs.next())
        {

            partecipante = new Impiegato(rs.getString("cf"));
            partecipante.setNome(rs.getString("nome"));
            partecipante.setCognome(rs.getString("cognome"));
            partecipante.setComuneNascita(rs.getString("comunen"));
            partecipante.setGenere(rs.getString("genere"));
            partecipante.setEmail(rs.getString("email"));
            partecipante.setDataNascita(rs.getObject("datan", LocalDate.class));
            partecipante.setPassword(rs.getString("password"));
            partecipante.setIdImpiegato(rs.getInt("idimpiegato"));
            lista.add(partecipante);
        }
        rs.close();

        return lista;
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
            riunione.setDescrizione(rs.getString("descrizione"));
            riunione.setCFOrganizzatore(rs.getString("organizzatore"));
            riunione.setOrganizzatore(rs.getString("nome"));
            riunione.setOrganizzatore(riunione.getOrganizzatore() + " " + rs.getString("cognome"));
            lista.add(riunione);
        }
        rs.close();

        return lista;
    }
	
	
}
