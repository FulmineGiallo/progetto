package model.Dao;

import model.Impiegato;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ImpiegatoDao
{

    private Connection connection;
    private PreparedStatement getImpiegati;
    private PreparedStatement getNome;


    public ImpiegatoDao(Connection connection, String email) throws SQLException
    {
        this.connection = connection;
        getImpiegati = connection.prepareStatement("SELECT * FROM impiegato");
        getNome = connection.prepareStatement("SELECT nome FROM impiegato WHERE email ='" + email + "'");
    }
    public String getNome() throws SQLException
    {
        ResultSet rs = getNome.executeQuery();
        return rs.getString("nome");
    }
    public List<Impiegato> getAllImpiegati() throws SQLException
    {
        ResultSet rs = getImpiegati.executeQuery();
        List<Impiegato> list = new ArrayList<Impiegato>();

        while(rs.next())
        {
            Impiegato impiegato = new Impiegato(rs.getString("cf"));
            impiegato.setCognome(rs.getString("cognome"));
            impiegato.setNome(rs.getString("nome"));
            impiegato.setComuneNascita(rs.getString("comunen"));
            impiegato.setGenere(rs.getString("genere"));
            impiegato.setEmail(rs.getString("email"));
            impiegato.setDataNascita(rs.getDate("datan"));
            impiegato.setPassowrd(rs.getString("password"));
        }
        rs.close();
        return list;
    }
}
