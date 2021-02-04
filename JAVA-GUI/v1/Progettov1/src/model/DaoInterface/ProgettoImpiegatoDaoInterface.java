package model.DaoInterface;

import java.sql.SQLException;

import model.Impiegato;
import model.Progetto;
import model.Ruolo;

public interface ProgettoImpiegatoDaoInterface {

	int EliminaImpiegatoDalProgetto(Impiegato impiegato, int idProgetto)throws SQLException;
	int InserisciImpiegatoNelProgetto(Impiegato impiegato, int idProgetto, int idRuolo)throws SQLException;
}
