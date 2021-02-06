package view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import controller.ControllerRegistrazioneImpiegato;

public class FormRegistrazioneImpiegato {
	
	private ControllerRegistrazioneImpiegato 	controllerRegistrazioneImpiegato;
	private FinestraPopup						finestraErrore;

    public void start(Stage window, Stage popup){
    	  	 	
    	try {
			FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("view/fxml/registrazioneImpiegato/FormRegistrazioneImpiegato.fxml"));
			Parent root = loader.load();
			Scene scene = new Scene(root);
			
			window.hide();
			window.setScene(scene);

			controllerRegistrazioneImpiegato = loader.getController();
			controllerRegistrazioneImpiegato.setStage(window, popup);
			controllerRegistrazioneImpiegato.inizializza();
			
			window.setTitle("Registrazione di un nuovo impiegato");
			window.setFullScreen(true);
			window.setFullScreenExitHint("");
			window.setMaximized(true);
			window.centerOnScreen();
			window.setMinWidth(850.0);
			window.setMinHeight(650.0);
			
			window.show();
    	} catch (IOException erroreCaricamento) {
			finestraErrore = new FinestraPopup();
			
			try {
				finestraErrore.start(popup, "Impossibile caricare la finestra di registrazione", erroreCaricamento);
			} catch (Exception e) {
				System.err.println("Impossibile caricare la finestra di registrazione");
			}
		} catch (SQLException erroreDatabase) {
			finestraErrore = new FinestraPopup();
			
			try {
				finestraErrore.start(popup, "Errore di connessione al database", erroreDatabase);
			} catch (Exception e) {
				System.err.println("Impossibile caricare la finestra di registrazione");
			}
		}
    }
}
