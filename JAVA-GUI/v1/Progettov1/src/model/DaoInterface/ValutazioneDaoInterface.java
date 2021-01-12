package model.DaoInterface;

import javafx.collections.ObservableList;
import model.Dao.ValutazioneDao;
import model.Impiegato;
import model.Valutazione;

import java.sql.SQLException;

public interface ValutazioneDaoInterface
{
    ObservableList<Valutazione> getValutazioni(Impiegato impiegato) throws SQLException;

}
