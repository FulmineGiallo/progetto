package model.DaoInterface;

import java.sql.SQLException;

import javafx.collections.ObservableList;
import model.Impiegato;
import model.Progetto;
import model.Riunione;

public interface RiunioneDaoInterface {

	 ObservableList<Riunione> getRiunioniImpiegato(Impiegato impiegato) throws SQLException;
	 ObservableList<Impiegato> getPartecipanti(Riunione riunione) throws SQLException;
	 int isPresente(Impiegato impiegato, Riunione riunione) throws SQLException;
	 int UpdatePresenza(Impiegato impiegato, Riunione riunione) throws SQLException;
	 int isAssente(Impiegato impiegato, Riunione riunione) throws SQLException;
	 int UpdateAssenza(Impiegato impiegato, Riunione riunione) throws SQLException;
	 
}
