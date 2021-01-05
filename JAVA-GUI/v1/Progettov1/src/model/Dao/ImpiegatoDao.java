package model.Dao;

import model.DaoInterface.ImpiegatoDaoInterface;
import model.Impiegato;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ImpiegatoDao implements ImpiegatoDaoInterface
{

    private final Connection connection;

    private final PreparedStatement getImpiegati;
    private final PreparedStatement getNome;


    public ImpiegatoDao(Connection connection) throws SQLException
    {
        this.connection = connection;
        getImpiegati = connection.prepareStatement("SELECT * FROM impiegato");
        getNome = connection.prepareStatement("SELECT nome FROM impiegato WHERE email = ?");
    }

    public String getNome(String email, boolean accesso) throws SQLException
    {
        String nome = null;
        getNome.setString(1,email);
        ResultSet rs = getNome.executeQuery();
        while(rs.next())
        {
            nome = rs.getString("nome");
        }

        rs.close();
        return nome;
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
