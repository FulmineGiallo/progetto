package view;

import java.io.IOException;

import controller.ControllerRegistrazioneRiunione;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Impiegato;
import model.Progetto;

public class FormRegistrazioneRiunione {

	ControllerRegistrazioneRiunione controllerRegistrazioneRiunione;
	
    public void start(Stage window, Stage popup, Impiegato organizzatore, Progetto progetto) {
    	
    	FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("view/fxml/registrazioneRiunione/FormRegistrazioneRiunione.fxml"));
        Parent root;
		try {
			root = loader.load();
	        Scene scene = new Scene(root);
	        
			window.hide();
			window.setScene(scene);

			controllerRegistrazioneRiunione = loader.getController();
			controllerRegistrazioneRiunione.setStage(window, popup);
			controllerRegistrazioneRiunione.inizializza(organizzatore, progetto);
	        
	        window.setTitle("Registrazione nuova riunione");
	        window.setWidth(850.0);
	        window.setMinWidth(850.0);
	        window.setHeight(450.0);
	        window.setMinHeight(450.0);
	        window.setMaximized(true);
	        window.centerOnScreen();
	        
	        window.show();
		} catch (IOException erroreCaricamento) {
			erroreCaricamento.printStackTrace();
			/*FinestraPopup finestraErrore = new FinestraPopup();
			
			try {
				finestraErrore.start(popup, "Impossibile caricare la finestra", erroreCaricamento);
			} catch (Exception ex) {
				ex.printStackTrace();
			}*/
		}
    }
}
