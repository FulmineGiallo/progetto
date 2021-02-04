package controller.ControllerFinestrePopup;

import java.sql.Connection;
import java.sql.SQLException;


import controller.ControllerRicercaImpiegati;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import model.Impiegato;
import model.Progetto;
import model.Ruolo;
import model.Connection.DBConnection;
import model.Dao.ProgettoDao;
import model.Dao.RuoloDao;
import model.Dao.progettoImpiegatoDao;
import model.DaoInterface.ProgettoDaoInterface;
import model.DaoInterface.ProgettoImpiegatoDaoInterface;
import model.DaoInterface.RuoloDaoInterface;

public class ControllerFinestraAggiungiImpiegatoAlProgetto extends ControllerFinestraPopup{
	
	
	private Image immagineDomanda = new Image(getClass().getClassLoader().getResourceAsStream("view/resources/img/question.png"));

	private Connection 					  connection;
    private DBConnection 				  dbConnection;
    
	private ProgettoDaoInterface 		  	progettoDao;
	private RuoloDaoInterface 		  		ruoloDao;
	private ProgettoImpiegatoDaoInterface progettoImpiegatoDao;
	
	private Ruolo ruoloImpiegatoDaAggiungere;
	
	
	private Impiegato 					  impiegatoDaAggiungere;
	private int  					  	idProgettoImpiegatoDaAggiungere;
	private ControllerRicercaImpiegati	  controllerRicercaImpiegati;

	{
        try {
            dbConnection = new DBConnection();
            connection = dbConnection.getConnection();
            progettoDao = new ProgettoDao(connection);
        	progettoImpiegatoDao = new progettoImpiegatoDao(connection);
        	ruoloDao = new RuoloDao(connection);
        	
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
	
	public void setImpiegato(Impiegato impiegatoDaAggiungere) {
		this.impiegatoDaAggiungere = impiegatoDaAggiungere;
	}
	
	public void setRuolo(Ruolo ruoloImpiegatoDaAggiungere) {
		this.ruoloImpiegatoDaAggiungere = ruoloImpiegatoDaAggiungere;
	}
		
	
	public void setIdProgetto(int idProgettoImpiegatoDaAggiungere) {
		this.idProgettoImpiegatoDaAggiungere = idProgettoImpiegatoDaAggiungere;
	}
	
    public void setControllerRicercaImpiegati(ControllerRicercaImpiegati controllerRicercaImpiegati) {
		this.controllerRicercaImpiegati = controllerRicercaImpiegati;
	}
	
	@Override
    public void inizializza(String titoloMessaggio, String messaggioLabel, String messaggioTextArea) {
		
		Immagine.setImage(immagineDomanda);
		
		setBottoneSinistro();
		setBottoneDestro();
		
    	//setTitoloMessaggio(titoloMessaggio);
    	setMessaggioLabel(messaggioLabel);
    	//setMessaggioTextArea(messaggioTextArea);
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
            	
            	int idRuolo;
            	int inserito = -1;
            	
            	try {
					idRuolo = ruoloDao.getIdRuolo(ruoloImpiegatoDaAggiungere);
	            	inserito = progettoImpiegatoDao.InserisciImpiegatoNelProgetto(impiegatoDaAggiungere, idProgettoImpiegatoDaAggiungere, idRuolo);
				} catch (SQLException e) {
					e.printStackTrace();
				}
            
            	
            	if (inserito != 0)
            		System.out.print("Impiegato inserito");
            	else
            		System.out.print("Impiegato non inserito");


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
