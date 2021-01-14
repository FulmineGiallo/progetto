package view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.PrintWriter;

import controller.ControllerRegistrazioneImpiegato;

public class FormRegistrazioneImpiegato
{
	
	ControllerRegistrazioneImpiegato controllerRegistrazioneImpiegato;

    public FormRegistrazioneImpiegato() {
    	
    }

    public void start(Stage window, Stage popup) throws Exception {
    	  	 	
    	FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("view/fxml/registrazioneImpiegato/FormRegistrazioneImpiegato.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        
		window.hide();
		window.setScene(scene);

        controllerRegistrazioneImpiegato = loader.getController();
        controllerRegistrazioneImpiegato.setStage(window, popup);
        controllerRegistrazioneImpiegato.inizializza();
        
        //window.setTitle("Registrazione");
        window.setTitle(window.toString());
        window.setMaximized(true);
        window.centerOnScreen();
        window.setMinWidth(850.0);
        window.setMinHeight(450.0);
        
        window.show();
    }
}
