package model.DaoInterface;

import javafx.collections.ObservableList;
import model.Impiegato;
import model.Progetto;

import java.sql.SQLException;

public interface ProgettoDaoInterface
{
    public ObservableList<Progetto> getProgettiImpiegato(Impiegato impiegato) throws SQLException;
    public String creaProgetto(Progetto progetto) throws SQLException;
    public int updateInfoProgetto(Progetto progetto) throws SQLException;
    public ObservableList<Impiegato> getPartecipanti(Progetto progetto) throws SQLException;
    public int getIdProgetto(Progetto progetto) throws SQLException;

}
