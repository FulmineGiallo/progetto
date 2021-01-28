package model.DaoInterface;

import model.Impiegato;

import java.sql.SQLException;
import java.util.Date;

public interface ImpiegatoDaoInterface
{
    int impiegatoExist(String email, String password) throws SQLException;
    Impiegato creaImpiegato(String CF) throws SQLException;
    String getCFWithEmail(String email) throws SQLException;
    String getNomeCognomeConCF(String CF) throws SQLException;
    String getGrado(String email) throws SQLException;
    int insertRegistrazione(String nome, String cognome, String genere, Date datan, String comunen, String provincia);
    String getDirettoreRisorseUmane() throws SQLException;

}
