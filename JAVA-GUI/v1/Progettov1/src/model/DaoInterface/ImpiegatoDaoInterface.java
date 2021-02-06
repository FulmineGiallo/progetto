package model.DaoInterface;

import model.Impiegato;
import model.Progetto;
import model.Riunione;

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
    ObservableList<Impiegato> getAllImpiegati(Progetto progetto) throws SQLException;
    ObservableList<Impiegato> getAllImpiegati(Riunione riunione) throws SQLException;
    ObservableList<Impiegato> getAllImpiegatiOrdinati(float salarioMedio, String nomeInserito, String cognomeInserito, String ordinamentoSelezionato, ObservableList<String> skillSelezionate, int numeroDiSkill, Progetto progetto, double valutazioneMedia) throws SQLException;
    ObservableList<Impiegato> getAllImpiegatiOrdinati(float salarioMedio, String nomeInserito, String cognomeInserito, String ordinamentoSelezionato, ObservableList<String> skillSelezionate, int numeroDiSkill, Riunione riunione, double valutazioneMedia) throws SQLException;
    ObservableList<Impiegato> getAllImpiegatiSenzaCampi(float salarioMedio, String nomeInserito, String cognomeInserito, String ordinamentoSelezionato, ObservableList<String> skillSelezionate, int numeroDiSkill, Progetto progetto) throws SQLException;
    ObservableList<Impiegato> getAllImpiegatiSenzaCampi(float salarioMedio, String nomeInserito, String cognomeInserito, String ordinamentoSelezionato, ObservableList<String> skillSelezionate, int numeroDiSkill, Riunione riunione) throws SQLException;	
}
