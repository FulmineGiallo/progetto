package model.Dao;


import model.DaoInterface.SalarioDaoInterface;

import java.sql.Connection;

public class SalarioDao implements SalarioDaoInterface
{
    Connection connection;

    public SalarioDao(Connection connection)
    {
        this.connection = connection;
    }

}
