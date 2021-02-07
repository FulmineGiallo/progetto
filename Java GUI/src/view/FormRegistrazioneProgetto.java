package view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

import controller.ControllerRegistrazioneProgetto;
import model.Impiegato;

public class FormRegistrazioneProgetto
{
	private ControllerRegistrazioneProgetto controllerRegistrazioneProgetto;
	private FinestraPopup					finestraErrore;

    public void start(Stage window, Stage popup, Impiegato projectManager){
    	try {
			FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("view/fxml/registrazioneProgetto/FormRegistrazioneProgetto.fxml"));
			Parent root = loader.load();
			Scene scene = new Scene(root);
			
			window.hide();
			window.setScene(scene);

			controllerRegistrazioneProgetto = loader.getController();
			controllerRegistrazioneProgetto.setStage(window, popup);
			controllerRegistrazioneProgetto.inizializza(projectManager);
			
			window.setTitle("Registrazione nuovo progetto");
			window.setFullScreen(true);
			window.setFullScreenExitHint("");
			window.setMinWidth(850.0);
			window.setMinHeight(650.0);
			window.setMaximized(true);
			window.centerOnScreen();
			
			window.show();
    	} catch (IOException erroreCaricamento) {
    		finestraErrore = new FinestraPopup();
    		
    		try {
    			finestraErrore.start(popup, "Impossibile caricare la finestra di registrazione di un nuovo progetto", erroreCaricamento);
    		} catch (Exception e) {
    			System.err.println("Impossibile caricare la finestra di registrazione di un nuovo progetto");
    		}
    	}
    }
}
