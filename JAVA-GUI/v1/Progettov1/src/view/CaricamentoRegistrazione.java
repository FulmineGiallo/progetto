package view;

import java.io.IOException;
import java.io.PrintWriter;

import controller.ControllerRegistrazioneImpiegato;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class CaricamentoRegistrazione {
	
	ControllerRegistrazioneImpiegato controllerRegistrazioneImpiegato;
	
	private final PrintWriter writer;

    public CaricamentoRegistrazione(PrintWriter writer) {
        this.writer = writer ;
    }

    public void start(Stage window){
        try {
        	FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/CaricamentoRegistrazione.fxml"));
        	
			Parent root = loader.load();
			Scene scene = new Scene(root);
			
			controllerRegistrazioneImpiegato = loader.getController();
			
			window.setResizable(false);
			window.setTitle("Caricamento in corso...");
			window.centerOnScreen();
			
			window.setScene(scene);
			window.show();
		} catch (IOException e) {
			System.err.println("Impossibile caricare la finestra!");
			//e.printStackTrace();
		}
    }
}
