package controller.ControllerFinestrePopup;

import java.sql.Connection;
import java.sql.SQLException;

import controller.ControllerHomePageOrganizzatore;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import model.Impiegato;
import model.Riunione;
import model.Connection.DBConnection;
import model.Dao.RiunioneDao;
import model.Dao.RiunioneImpiegatoDao;
import model.DaoInterface.RiunioneDaoInterface;

public class ControllerFinestraEliminazioneImpiegatoRiunione extends ControllerFinestraPopup {

	private Image immagineDomanda = new Image(getClass().getClassLoader().getResourceAsStream("view/resources/img/question.png"));
	
	private Connection 						connection;
    private DBConnection 					dbConnection;
	
    private RiunioneDaoInterface			riunioneDao;
    private RiunioneImpiegatoDao			riunioneImpiegatoDao;
    private int								idriunione;
    
	private Impiegato 						impiegatoDaEliminare;
	private Riunione  						riunioneImpiegatoDaEliminare;
	private ControllerHomePageOrganizzatore	controllerHomePageOrganizzatore;
	
	{
        try {
            dbConnection = new DBConnection();
            connection = dbConnection.getConnection();
            riunioneDao = new RiunioneDao(connection);
            riunioneImpiegatoDao = new RiunioneImpiegatoDao(connection);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
	
	public void setImpiegato(Impiegato impiegatoDaEliminare) {
		this.impiegatoDaEliminare = impiegatoDaEliminare;
	}

	public void setRiunione(Riunione riunioneImpiegatoDaEliminare) {
		this.riunioneImpiegatoDaEliminare = riunioneImpiegatoDaEliminare;
	}

	public void setController(ControllerHomePageOrganizzatore controllerHomePageOrganizzatore) {
		this.controllerHomePageOrganizzatore = controllerHomePageOrganizzatore;
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
            	int eliminato = 0;

            	try {
	            	riunioneImpiegatoDao = new RiunioneImpiegatoDao(connection);
	            	idriunione = riunioneDao.getIdRiunione(riunioneImpiegatoDaEliminare);
	            	
	            	eliminato = riunioneImpiegatoDao.EliminaImpiegatoDallaRiunione(impiegatoDaEliminare, idriunione);
	
	            	if(eliminato!=0) {
	            		FinestraPopup.getScene().getWindow().hide();	
	            		controllerHomePageOrganizzatore.AggiornaLista();
	            		controllerHomePageOrganizzatore.DaDescrizioneRiunioneAdIstruzioniBox();
	            	}
            	} catch (SQLException e) {
					e.printStackTrace();
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
			
		}
	}
	
	/*@Override >> non utilizzato
	protected void setMessaggioTextArea(String messaggioTextArea) {
		if (messaggioTextArea != null) {
			
		}
	}*/
}
