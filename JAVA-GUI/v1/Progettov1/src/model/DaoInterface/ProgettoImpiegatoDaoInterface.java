package model.DaoInterface;

import java.sql.SQLException;

import model.Impiegato;
import model.Progetto;
import model.Ruolo;

public interface ProgettoImpiegatoDaoInterface {

	int EliminaImpiegatoDalProgetto(Impiegato impiegato, int idProgetto)throws SQLException;
	int InserisciImpiegatoNelProgetto(Impiegato impiegato, Progetto progetto, int idRuolo)throws SQLException;
	Ruolo getRuoloImpiegato(Impiegato impiegato, int idProgetto)throws SQLException;
}
