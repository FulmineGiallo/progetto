package model.DaoInterface;

import java.sql.SQLException;

public interface ImpiegatoDaoInterface
{
    String getNome(String email, boolean accesso) throws SQLException;
        
}
