package model.DaoInterface;

import model.Impiegato;
import model.Progetto;

import java.sql.SQLException;

public interface ProgettoDaoInterface
{
    Progetto getProgettiImpiegato(Impiegato impiegato) throws SQLException;

}
