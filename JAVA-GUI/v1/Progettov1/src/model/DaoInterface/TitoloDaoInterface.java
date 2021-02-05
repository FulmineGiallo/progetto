package model.DaoInterface;

import java.sql.SQLException;
import javafx.collections.ObservableList;
import model.Impiegato;
import model.Titolo;

public interface TitoloDaoInterface {

    public ObservableList<Titolo> titoliList() throws SQLException;
	public ObservableList<Titolo> titoliListImpiegato(Impiegato impiegato) throws SQLException;
	public Titolo getTitoloById(int idTitolo) throws SQLException;
}
