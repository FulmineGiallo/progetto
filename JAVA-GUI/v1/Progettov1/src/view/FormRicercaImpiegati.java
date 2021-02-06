package view;

import java.io.IOException;
import java.sql.SQLException;

import controller.ControllerRicercaImpiegati;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Progetto;
import model.Riunione;

public class FormRicercaImpiegati {

	private ControllerRicercaImpiegati	controllerRicercaImpiegati;
	private FinestraPopup				finestraErrore;
	
	private void caricaStage(Stage window, Stage popup, String titoloFinestra) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("view/fxml/FormRicercaImpiegati.fxml"));
			Parent root = loader.load();
			Scene scene = new Scene(root);
			
			window.hide();
			window.setScene(scene);

			controllerRicercaImpiegati = loader.getController();
			controllerRicercaImpiegati.setStage(window, popup);
			
			window.setTitle(titoloFinestra);
			window.setFullScreen(true);
			window.setFullScreenExitHint("");
			window.setWidth(1300.0);
			window.setMinWidth(1300.0);
			window.setHeight(800.0);
			window.setMinHeight(800.0);
			window.setMaximized(true);
			window.centerOnScreen();
			
			window.show();
		} catch (IOException erroreCaricamento) {
			finestraErrore = new FinestraPopup();
			
			try {
				finestraErrore.start(popup, "Impossibile caricare la finestra di ricerca degli impiegati", erroreCaricamento);
			} catch (Exception e) {
				System.err.println("Impossibile caricare la finestra di ricerca degli impiegati");
			}
		}
	}

    public void start(Stage window, Stage popup, Progetto progetto){
    	caricaStage(window, popup, "Aggiungi un impiegato al progetto \"" + progetto.getTitolo() + "\"");
    	try {
			controllerRicercaImpiegati.inizializza(progetto);
    	} catch (SQLException erroreDatabase) {
    		finestraErrore = new FinestraPopup();
    		
    		try {
    			finestraErrore.start(popup, "Errore di connessione al database", erroreDatabase);
    		} catch (Exception e) {
    			System.err.println("Impossibile caricare la finestra di ricerca degli impiegati");
    		}
    	}
    }
	
	public void start(Stage window, Stage popup, Riunione riunione) {
		caricaStage(window, popup, "Invita un impiegato alla riunione \"" + riunione.getTitolo() + "\"");
    	try {
			controllerRicercaImpiegati.inizializza(riunione);
    	} catch (SQLException erroreDatabase) {
    		finestraErrore = new FinestraPopup();
    		
    		try {
    			finestraErrore.start(popup, "Errore di connessione al database", erroreDatabase);
    		} catch (Exception e) {
    			System.err.println("Impossibile caricare la finestra di ricerca degli impiegati");
    		}
    	}
	}
}
