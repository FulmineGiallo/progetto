package model.DaoInterface;

import java.sql.SQLException;

import model.Impiegato;

public interface RiunioneImpiegatoDaoInterface {

	int EliminaImpiegatoDallaRiunione(Impiegato impiegato, int idRiunione)throws SQLException;
	
}
