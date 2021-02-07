package model.DaoInterface;

import java.sql.SQLException;

import javafx.collections.ObservableList;
import model.Ambito;

public interface AmbitoDaoInterface {	
	public ObservableList<Ambito> AmbitoList() throws SQLException;
	public ObservableList<Ambito> getAmbitiProgetto(int idProgetto) throws SQLException;
}
