package model.DaoInterface;

import model.Impiegato;

import java.sql.SQLException;
import java.util.Date;

public interface ImpiegatoDaoInterface
{
    int impiegatoExist(String email, String password) throws SQLException;
    Impiegato creaImpiegato(String CF) throws SQLException;
    String getCFWithEmail(String email) throws SQLException;
    String getGrado(String email) throws SQLException;

}
