package model.DaoInterface;

import java.sql.SQLException;

import model.Impiegato;
import model.Progetto;

public interface ProgettoImpiegatoDaoInterface {

	int EliminaImpiegatoDalProgetto(Impiegato impiegato, int idProgetto)throws SQLException;
	
}
