package controller.ControllerFinestrePopup;

import java.sql.Connection;
import java.sql.SQLException;


import controller.ControllerRicercaImpiegati;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import model.Impiegato;
import model.Progetto;
import model.Riunione;
import model.Ruolo;
import model.Connection.DBConnection;
import model.Dao.ProgettoDao;
import model.Dao.RiunioneDao;
import model.Dao.RiunioneImpiegatoDao;
import model.Dao.RuoloDao;
import model.Dao.progettoImpiegatoDao;
import model.DaoInterface.ProgettoDaoInterface;
import model.DaoInterface.ProgettoImpiegatoDaoInterface;
import model.DaoInterface.RiunioneDaoInterface;
import model.DaoInterface.RiunioneImpiegatoDaoInterface;
import model.DaoInterface.RuoloDaoInterface;

public class ControllerFinestraAggiungiImpiegato extends ControllerFinestraPopup{
	
	private Image immagineDomanda = new Image(getClass().getClassLoader().getResourceAsStream("view/resources/img/question.png"));

	private Connection 					  	connection;
    private DBConnection 				  	dbConnection;
    
	private ProgettoImpiegatoDaoInterface 	progettoImpiegatoDao;
	private RiunioneImpiegatoDaoInterface 	riunioneImpiegatoDao;
	private RuoloDaoInterface 		  		ruoloDao;
	
	private Ruolo ruoloImpiegatoDaAggiungere;
	
	private Impiegato 	impiegatoDaAggiungere;
	private Progetto	progetto = null;
	private Riunione	riunione = null;
	
	private ControllerRicercaImpiegati	  controllerRicercaImpiegati;

	{
        try {
            dbConnection = new DBConnection();
            connection = dbConnection.getConnection();
        	progettoImpiegatoDao = new progettoImpiegatoDao(connection);
        	ruoloDao = new RuoloDao(connection);
        	
        	riunioneImpiegatoDao = new RiunioneImpiegatoDao(connection);
        } catch (SQLException erroreDatabase) {
        	System.err.println(erroreDatabase.toString());
        }
    }
	
	public void setImpiegato(Impiegato impiegatoDaAggiungere) {
		this.impiegatoDaAggiungere = impiegatoDaAggiungere;
	}
	
	public void setRuolo(Ruolo ruoloImpiegatoDaAggiungere) {
		this.ruoloImpiegatoDaAggiungere = ruoloImpiegatoDaAggiungere;
	}
	
	public void setProgetto(Progetto progetto) {
		this.progetto = progetto;
	}
	
	public void setRiunione(Riunione riunione) {
		this.riunione = riunione;
	}
	
    public void setControllerRicercaImpiegati(ControllerRicercaImpiegati controllerRicercaImpiegati) {
		this.controllerRicercaImpiegati = controllerRicercaImpiegati;
	}
	
	@Override
    public void inizializza(String titoloMessaggio, String messaggioLabel, String messaggioTextArea) {
		
		Immagine.setImage(immagineDomanda);
		
		setBottoneSinistro();
		setBottoneDestro();
		
    	setMessaggioLabel(messaggioLabel);
	}
	
	@Override
	protected void setBottoneSinistro() {
		SinistraButton.setText("Annulla");
		
		SinistraButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
            	FinestraPopup.getScene().getWindow().hide();
            }
        });
	}
	
	@Override
	protected void setBottoneDestro() {
		
		DestraButton.setText("Conferma");
		
		DestraButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
            	
            	try {
	            	if (progetto != null && riunione == null)
						progettoImpiegatoDao.InserisciImpiegatoNelProgetto(impiegatoDaAggiungere, progetto,
																		   ruoloDao.getIdRuolo(ruoloImpiegatoDaAggiungere));
	            	else if (progetto == null && riunione != null)
	            		riunioneImpiegatoDao.inserisciImpiegato(impiegatoDaAggiungere, riunione);
	            	else
	            		throw new SQLException();
				} catch (SQLException e) {
					e.printStackTrace();
				}
            	
               	FinestraPopup.getScene().getWindow().hide();
               	controllerRicercaImpiegati.NascondiInfoImpiegato();
               	controllerRicercaImpiegati.avviaRicerca(null);
            	
            }
        });
	}
	
	/*@Override >> non utilizzato
	protected void setTitoloMessaggio(String titoloMessaggio) {
		if (titoloMessaggio != null) {
			
		}
	}*/
	
	@Override
	protected void setMessaggioLabel(String messaggioLabel) {
		if (messaggioLabel != null) {
			MessaggioTA.setVisible(false);
			MessaggioLabel.setVisible(true);
			
			MessaggioLabel.setText(messaggioLabel);
		}
	}
	
	/*@Override >> non utilizzato
	protected void setMessaggioTextArea(String messaggioTextArea) {
		if (messaggioTextArea != null) {
			
		}
	}*/

}
