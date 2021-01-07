package model.DaoInterface;

import javafx.collections.ObservableList;
import model.Grado;

import java.sql.SQLException;


public interface GradoDaoInterface
{
    public ObservableList<Grado> gradoList() throws SQLException;

}
