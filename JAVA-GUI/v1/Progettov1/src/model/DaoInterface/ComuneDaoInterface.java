package model.DaoInterface;

import javafx.collections.ObservableList;
import model.Comune;
import model.Grado;

import java.sql.SQLException;

public interface ComuneDaoInterface
{
    public ObservableList<Comune> gradoList(String provincia) throws SQLException;

}
