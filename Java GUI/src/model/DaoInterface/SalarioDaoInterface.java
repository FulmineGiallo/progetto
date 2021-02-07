package model.DaoInterface;

import model.Impiegato;

import java.sql.SQLException;

public interface SalarioDaoInterface
{
    public int salarioMedioImpiegato(Impiegato impiegato) throws SQLException;

}
