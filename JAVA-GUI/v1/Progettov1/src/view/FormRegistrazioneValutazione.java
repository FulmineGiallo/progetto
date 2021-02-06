package view;

import java.io.IOException;

import controller.ControllerRegistrazioneValutazione;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Impiegato;
import model.Progetto;
import model.Riunione;

public class FormRegistrazioneValutazione {

	ControllerRegistrazioneValutazione controllerRegistrazioneValutazione;
	
	private void caricaStage(Stage window, String titoloFinestra) {
		FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("view/fxml/registrazioneValutazione/FormRegistrazioneValutazione.fxml"));
        Parent root;
		try {
			root = loader.load();
	        Scene scene = new Scene(root);
	        
			window.hide();
			window.setScene(scene);
			
			controllerRegistrazioneValutazione = loader.getController();
	        
	        window.setTitle(titoloFinestra);
	        window.setFullScreen(true);
			window.setFullScreenExitHint("");
	        window.setWidth(850.0);
	        window.setMinWidth(850.0);
	        window.setHeight(450.0);
	        window.setMinHeight(450.0);
	        window.setMaximized(true);
	        window.centerOnScreen();
	        
	        window.show();
		} catch (IOException erroreCaricamento) {}
	}
	
	//Caricamento della finestra per l'inserimento di una valutazione di progetto
    public void start(Stage window, Stage popup, Impiegato recensito, Progetto progetto) {
    	caricaStage(window, "Nuova valutazione per " + recensito.toString());
		controllerRegistrazioneValutazione.setStage(window, popup);
		controllerRegistrazioneValutazione.inizializza(recensito, progetto);
    }
    
    //Caricamento della finestra per l'inserimento di una valutazione di riunione
    public void start(Stage window, Stage popup, Impiegato recensito, Riunione riunione) {
    	caricaStage(window, "Nuova valutazione per " + recensito.toString());
		controllerRegistrazioneValutazione.setStage(window, popup);
		controllerRegistrazioneValutazione.inizializza(recensito, riunione);
    }
}
