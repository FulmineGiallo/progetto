package model.Dao;


import model.DaoInterface.SalarioDaoInterface;
import model.Impiegato;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SalarioDao implements SalarioDaoInterface
{
    Connection connection;
    PreparedStatement salarioMedio;

    public SalarioDao(Connection connection) throws SQLException
    {
        this.connection = connection;
        salarioMedio = connection.prepareStatement("SELECT avg FROM salariomedio WHERE impiegato = ?");
    }

    @Override
    public int salarioMedioImpiegato(Impiegato impiegato) throws SQLException
    {
        int salarioImpiegato = 0;
        salarioMedio.setString(1, impiegato.getCF());
        ResultSet r = salarioMedio.executeQuery();
        while(r.next()) {
            salarioImpiegato = r.getInt("avg");
        }
        return salarioImpiegato;
    }



}
