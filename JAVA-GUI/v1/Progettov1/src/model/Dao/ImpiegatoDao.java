package model.Dao;

import model.DaoInterface.ImpiegatoDaoInterface;
import model.Impiegato;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ImpiegatoDao implements ImpiegatoDaoInterface
{

    private final Connection connection;

    private final PreparedStatement getImpiegati;
    private final PreparedStatement getNome;
    private final PreparedStatement loginImpiegato;
    private final PreparedStatement getImpiegato;
    private final PreparedStatement getCF;
    private final PreparedStatement getGrado;
    private final PreparedStatement insertImpiegato;

    public ImpiegatoDao(Connection connection) throws SQLException
    {
        this.connection = connection;
        getImpiegati = connection.prepareStatement("SELECT * FROM impiegato");
        getNome = connection.prepareStatement("SELECT nome FROM impiegato WHERE email = ?");
        loginImpiegato = connection.prepareStatement("SELECT COUNT(*) FROM impiegatoaccount WHERE email = ? AND password = ?");
        getImpiegato = connection.prepareStatement("SELECT * FROM impiegato WHERE cf = ?");
        getCF = connection.prepareStatement("SELECT CF FROM impiegato WHERE email = ?");
        getGrado = connection.prepareStatement("SELECT tipogrado FROM impiegatoazienda WHERE cf = ?");
        insertImpiegato = connection.prepareStatement("");
    }

    @Override
    public int impiegatoExist(String email, String password) throws SQLException
    {
        int accesso = 0;
        loginImpiegato.setString(1,email);
        loginImpiegato.setString(2,password);
        ResultSet rs = loginImpiegato.executeQuery();
        while(rs.next())
        {
            accesso = rs.getInt("count");
        }
        rs.close();
        //Se ritorna 0, l'impiegato non esiste, altrimenti 1.
        return accesso;
    }

    @Override
    public Impiegato creaImpiegato(String CF) throws SQLException
    {
        getImpiegato.setString(1,CF);
        ResultSet rs = getImpiegato.executeQuery();
        Impiegato impiegato = null;
        while (rs.next())
        {
            impiegato = new Impiegato(rs.getString("cf"));
            impiegato.setNome(rs.getString("nome"));
            impiegato.setCognome(rs.getString("cognome"));
            impiegato.setComuneNascita(rs.getString("comunen"));
            impiegato.setGenere(rs.getString("genere"));
            impiegato.setEmail(rs.getString("email"));
            impiegato.setDataNascita(rs.getDate("datan"));
            impiegato.setPassword(rs.getString("password"));
            //impiegato.setIdgrado(rs.getInt("idgrado")); vedere il perché del commento in Impiegato.java
            impiegato.setIdImpiegato(rs.getInt("idimpiegato"));
            impiegato.setGrado(getGrado(impiegato.getCF()));
        }
        rs.close();
        return impiegato;
    }

    @Override
    public String getCFWithEmail(String email) throws SQLException
    {
        String CF = null;
        getCF.setString(1,email);
        ResultSet rs = getCF.executeQuery();

        while(rs.next())
        {
            CF = rs.getString("CF");
        }
        rs.close();
        return CF;
    }
    @Override
    public String getGrado(String cf) throws SQLException
    {
        String grado = null;
        getGrado.setString(1, cf);
        ResultSet rs = getGrado.executeQuery();

        while(rs.next()) {
            grado = rs.getString("tipogrado");
        }
        
        rs.close();
        return grado;
    }

    @Override
    public int insertRegistrazione(String nome, String cognome, String genere, Date datan, String comunen, String provincia) {
        return 0;
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
            impiegato.setPassword(rs.getString("password"));
            impiegato.setGrado(getGrado(impiegato.getCF()));
        }
        rs.close();
        return list;
    }
}
