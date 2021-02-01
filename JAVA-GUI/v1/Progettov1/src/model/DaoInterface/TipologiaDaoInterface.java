package model.DaoInterface;

import java.sql.SQLException;

import javafx.collections.ObservableList;
import model.Tipologia;

public interface TipologiaDaoInterface {

	public ObservableList<Tipologia> getListaTipologie() throws SQLException;
}
