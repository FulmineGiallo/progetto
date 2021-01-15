package model.DaoInterface;

import java.sql.SQLException;
import javafx.collections.ObservableList;

import model.Titolo;

public interface TitoloDaoInterface {

    public ObservableList<Titolo> titoliList() throws SQLException;
}
