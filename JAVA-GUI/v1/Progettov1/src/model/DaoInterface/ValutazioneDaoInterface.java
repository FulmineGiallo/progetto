package model.DaoInterface;

import javafx.collections.ObservableList;
import model.Dao.ValutazioneDao;
import model.Impiegato;
import model.Progetto;
import model.Riunione;
import model.Valutazione;

import java.sql.SQLException;

public interface ValutazioneDaoInterface
{
    public ObservableList<Valutazione> getValutazioni(Impiegato impiegato) throws SQLException;
    public String insertValutazione(Impiegato recensito, Progetto progetto, Valutazione nuovaValutazione) throws SQLException;
    public String insertValutazione(Impiegato recensito, Riunione riunione, Valutazione nuovaValutazione) throws SQLException;
}
