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
    private final PrintWriter writer;

    public FormRegistrazioneImpiegato(PrintWriter writer) {
        this.writer = writer ;
    }

    public void start(Stage window) throws Exception {
    	
    	FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("view/fxml/registrazioneImpiegato/FormRegistrazioneImpiegato.fxml"));
        Parent root = loader.load();
        Scene scene =  new Scene(root);

        controllerRegistrazioneImpiegato = loader.getController();
        controllerRegistrazioneImpiegato.inizializza();
        window.setScene(scene);
        
        window.setMinWidth(850.0);
        window.setMinHeight(450.0);
        window.setTitle("Registrazione");
        window.centerOnScreen();
        
        window.show();
    }
}
