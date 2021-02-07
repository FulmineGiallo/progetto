package model.DaoInterface;

import java.sql.SQLException;

import model.Impiegato;
import model.Riunione;

public interface RiunioneImpiegatoDaoInterface {

	int EliminaImpiegatoDallaRiunione(Impiegato impiegato, int idRiunione)throws SQLException;
	int inserisciImpiegato(Impiegato impiegato, Riunione riunione) throws SQLException;
	
}
