package model.DaoInterface;

import java.sql.SQLException;

import javafx.collections.ObservableList;
import model.Ruolo;

public interface RuoloDaoInterface {

	 public ObservableList<Ruolo> GetAllRuoli() throws SQLException;
	
}
