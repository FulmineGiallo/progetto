package view;

import java.io.IOException;

import controller.ControllerRegistrazioneImpiegato;
import controller.ControllerRegistrazioneSkill;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Impiegato;

public class FormRegistrazioneSkill {
	
	private ControllerRegistrazioneSkill 		controllerRegistrazioneSkill;
	private ControllerRegistrazioneImpiegato 	controllerRegistrazioneImpiegato = null;
	private FinestraPopup						finestraErrore;
	
	private Impiegato nuovoImpiegato;
	
	public FormRegistrazioneSkill(ControllerRegistrazioneImpiegato controllerRegistrazioneImpiegato, Impiegato nuovoImpiegato) {
		this.nuovoImpiegato = nuovoImpiegato;
		this.controllerRegistrazioneImpiegato = controllerRegistrazioneImpiegato;
	}
	
	public void start(Stage popup){
    	
    	try {
			FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("view/fxml/registrazioneImpiegato/InserimentoSkill/FormRegistrazioneSkill.fxml"));
			Parent root = loader.load();
			Scene scene = new Scene(root);
			
			popup.hide();
			popup.setScene(scene);

			controllerRegistrazioneSkill = loader.getController();
			controllerRegistrazioneSkill.setStage(popup);
			controllerRegistrazioneSkill.inizializza(controllerRegistrazioneImpiegato, nuovoImpiegato);
			
			popup.setTitle("Registrazione di una nuova skill");
			popup.setResizable(true);
			popup.setWidth(850.0);
			popup.setMinWidth(850.0);
			popup.setHeight(600.0);
			popup.setMinHeight(600.0);
			
			popup.show();
			popup.centerOnScreen();
			
    	} catch (IOException erroreCaricamento) {
			finestraErrore = new FinestraPopup();
			
			try {
				finestraErrore.start(popup, "Impossibile caricare la finestra di registrazione di una nuova skill", erroreCaricamento);
			} catch (Exception e) {
				System.err.println("Impossibile caricare la finestra di registrazione di una nuova skill");
			}
		}
	}
}
