package model.DaoInterface;

import java.sql.SQLException;

import javafx.collections.ObservableList;
import model.Impiegato;
import model.Progetto;
import model.Riunione;
import model.RiunioneFisica;
import model.RiunioneTelematica;

public interface RiunioneDaoInterface {

	ObservableList<Riunione> getRiunioniImpiegato(Impiegato impiegato) throws SQLException;
	ObservableList<Impiegato> getPartecipanti(int idriunione) throws SQLException;
	boolean isInvitato(Impiegato impiegato, Riunione riunione) throws SQLException;
	int UpdatePresenza(Impiegato impiegato, Riunione riunione) throws SQLException;
	int UpdateAssenza(Impiegato impiegato, Riunione riunione) throws SQLException;
	int getIdRiunione(Riunione riunione) throws SQLException;
	public int updateRiunione(Riunione riunione) throws SQLException;
	public String creaRiunione(RiunioneFisica nuovaRiunioneFisica) throws SQLException;
	public String creaRiunione(RiunioneTelematica nuovaRiunioneTelematica) throws SQLException;
	
}
