package controller.ControllerFinestrePopup;

import java.sql.Connection;
import java.sql.SQLException;

import controller.ControllerHomePageProgetto;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import model.Impiegato;
import model.Progetto;
import model.Connection.DBConnection;
import model.Dao.ProgettoDao;
import model.Dao.progettoImpiegatoDao;
import model.DaoInterface.ProgettoDaoInterface;
import model.DaoInterface.ProgettoImpiegatoDaoInterface;

public class ControllerFinestraEliminazioneImpiegatoProgetto extends ControllerFinestraPopup {
	
	private Image immagineDomanda = new Image(getClass().getClassLoader().getResourceAsStream("view/resources/img/question.png"));

	private Connection 					  connection;
    private DBConnection 				  dbConnection;
    
	private ProgettoDaoInterface 		  progettoDao;
	private ProgettoImpiegatoDaoInterface progettoImpiegatoDao;
	
	private Impiegato 					  impiegatoDaEliminare;
	private Progetto  					  progettoImpiegatoDaEliminare;
	private ControllerHomePageProgetto	  controllerHomePageProgetto;

	{
        try {
            dbConnection = new DBConnection();
            connection = dbConnection.getConnection();
            progettoDao = new ProgettoDao(connection);
        	progettoImpiegatoDao = new progettoImpiegatoDao(connection);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
	
	public void setImpiegato(Impiegato impiegatoDaEliminare) {
		this.impiegatoDaEliminare = impiegatoDaEliminare;
	}
	
	public void setProgetto(Progetto progettoImpiegatoDaEliminare) {
		this.progettoImpiegatoDaEliminare = progettoImpiegatoDaEliminare;
	}
	
    public void setControllerHomePageProgetto(ControllerHomePageProgetto controllerHomePageProgetto) {
		this.controllerHomePageProgetto = controllerHomePageProgetto;
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
		
		SinistraButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
            	
            	int idProgetto = 0;
            	int eliminato = 0;
            	
            	try {
					idProgetto = progettoDao.GetIdProgetto(progettoImpiegatoDaEliminare);
					eliminato = progettoImpiegatoDao.EliminaImpiegatoDalProgetto(impiegatoDaEliminare, idProgetto);
				} catch (SQLException e) {
					e.printStackTrace();
				}
            	
            	if(eliminato != 0) {
            		FinestraPopup.getScene().getWindow().hide();
            		try {
						controllerHomePageProgetto.aggiornaLista();
						controllerHomePageProgetto.DaDescrizioneProgettoAdIstruzioniBox();
					} catch (SQLException e) {
						e.printStackTrace();
					}
            	}
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
