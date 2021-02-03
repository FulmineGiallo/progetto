package model.DaoInterface;

import model.Impiegato;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javafx.collections.ObservableList;

public interface ImpiegatoDaoInterface
{
    int impiegatoExist(String email, String password) throws SQLException;
    Impiegato creaImpiegato(String CF) throws SQLException;
    String getCFWithEmail(String email) throws SQLException;
    String getNomeCognomeConCF(String CF) throws SQLException;
    String getGrado(String email) throws SQLException;
    String insertRegistrazione(Impiegato nuovoImpiegato) throws SQLException;
    String getDirettoreRisorseUmane() throws SQLException;
    ObservableList<Impiegato> getAllImpiegati() throws SQLException;
    ObservableList<Impiegato> getAllImpiegatiOrdinatiPerNome(float salarioMedio, String nomeInserito, String cognomeInserito, String ordinamentoSelezionato, ObservableList<String> skillSelezionate, int numeroDiSkill) throws SQLException;

    	
}
