package model.DaoInterface;

import java.sql.SQLException;

import javafx.collections.ObservableList;
import model.Impiegato;
import model.Skill;

public interface SkillDaoInterface {
	public ObservableList<Skill> getListaSkill(Impiegato impiegato) throws SQLException;
    String descrizioneCertificazione(String titolo, Impiegato impiegato) throws SQLException;
    String dataCertificazione(String titolo, Impiegato impiegato) throws SQLException;
    String getTipologiaSkill(String titolo, Impiegato impiegato) throws SQLException;
}
