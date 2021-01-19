package model.DaoInterface;

import javafx.collections.ObservableList;
import model.Impiegato;
import model.Progetto;

import java.sql.SQLException;

public interface ProgettoDaoInterface
{
    ObservableList<Progetto> getProgettiImpiegato(Impiegato impiegato) throws SQLException;
    int creaProgetto(Progetto progetto) throws SQLException;
    int updateInfoProgetto(Progetto progetto) throws SQLException;
    ObservableList<Impiegato> getPartecipanti(Progetto progetto) throws SQLException;

}
