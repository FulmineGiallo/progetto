package view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.PrintWriter;

import controller.ControllerRegistrazioneProgetto;
import model.Impiegato;

public class FormRegistrazioneProgetto
{
	
	ControllerRegistrazioneProgetto controllerRegistrazioneProgetto;
    private final PrintWriter writer;
    Impiegato impiegato;

    public FormRegistrazioneProgetto(PrintWriter writer, Impiegato impiegato)
    {
        this.impiegato = impiegato;
        this.writer = writer ;
    }

    public void start(Stage window) throws Exception {
    	
    	FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("view/fxml/registrazioneProgetto/FormRegistrazioneProgetto.fxml"));
        Parent root = loader.load();
        Scene scene =  new Scene(root);
        
		window.hide();
		window.setScene(scene);

        controllerRegistrazioneProgetto = loader.getController();
        controllerRegistrazioneProgetto.inizializzaRegistrazioneProgetto(impiegato);
        
        window.setTitle("Registrazione");
        window.setMaximized(true);
        window.centerOnScreen();
        window.setMinWidth(850.0);
        window.setMinHeight(450.0);
        
        window.show();
    }
}
