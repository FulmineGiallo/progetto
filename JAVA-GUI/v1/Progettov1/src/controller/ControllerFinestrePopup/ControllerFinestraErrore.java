package controller.ControllerFinestrePopup; // completare gestione

import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.Impiegato;
import model.Progetto;
import model.Riunione;
import model.Connection.DBConnection;
import model.Dao.ProgettoDao;
import model.Dao.RiunioneDao;
import model.Dao.RiunioneImpiegatoDao;
import model.Dao.progettoImpiegatoDao;
import model.DaoInterface.ProgettoDaoInterface;
import model.DaoInterface.ProgettoImpiegatoDaoInterface;
import model.DaoInterface.RiunioneDaoInterface;
import view.HomePageBenvenuto;

import java.sql.Connection;
import java.sql.SQLException;

import controller.ControllerHomePageOrganizzatore;
import controller.ControllerHomePageProgetto;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;

public class ControllerFinestraErrore extends ControllerFinestraPopup{

    private Image immagineAttenzione = new Image(getClass().getClassLoader().getResourceAsStream("view/resources/img/warning.png"));
    
    private String dettagliErrore;
    
    
    ObservableList<Impiegato> lista = FXCollections.observableArrayList();
	
    public void inizializza(String messaggioErrore, Exception errore) {

    	Immagine.setImage(immagineAttenzione);
    	
    	DestraButton.setText("Ok");
    	
    	MessaggioLabel.setText(messaggioErrore);
    	if(errore != null) {    		
    		dettagliErrore = errore.toString() + "\n";
			for(StackTraceElement STE: errore.getStackTrace()) {
				dettagliErrore = dettagliErrore + "at " + STE.toString() + "\n";
			}
			
        	MessaggioTA.setText(dettagliErrore);
    	} else {
    		MessaggioTA.setText("Nessun dettaglio da mostrare");
    	}
    	
    	MostraNascondiDettagli();
    	ChiudiFinestraErrore();
    }

    
    public void MostraNascondiDettagli() {
        DestraButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
            	
            	if (MessaggioLabel.isVisible()) {
            		
            		MessaggioLabel.setVisible(false);
            		MessaggioTA.setVisible(true);
            		
            	} else if (MessaggioTA.isVisible()) {
            		
            		MessaggioLabel.setVisible(true);
            		MessaggioTA.setVisible(false);
            		
            	}
            }
        });
    }
    
    public void ChiudiFinestraErrore() {
        DestraButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
            	FinestraPopup.getScene().getWindow().hide(); 
            }
        });
    }

    
	@Override
    public void inizializza(String titoloMessaggio, String messaggioLabel, String messaggioTextArea) {
		
	}
	
	@Override
	protected void setBottoneSinistro() {
		
	}
	
	@Override
	protected void setBottoneDestro() {
		
	}
	
	@Override
	protected void setTitoloMessaggio(String titoloMessaggio) {
		
	}
	
	@Override
	protected void setMessaggioLabel(String messaggioLabel) {
		
	}
	
	@Override
	protected void setMessaggioTextArea(String messaggioTextArea) {
		
	} 
}
