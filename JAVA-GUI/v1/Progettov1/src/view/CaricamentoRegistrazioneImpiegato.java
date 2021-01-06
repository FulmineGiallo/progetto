package view;

import java.io.IOException;
import java.io.PrintWriter;

import controller.ControllerRegistrazioneImpiegato;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class CaricamentoRegistrazioneImpiegato {
	
	ControllerRegistrazioneImpiegato controllerRegistrazioneImpiegato;
	
	private final PrintWriter writer;

    public CaricamentoRegistrazioneImpiegato(PrintWriter writer) {
        this.writer = writer ;
    }

    public void start(Stage window) {
        try {
        	
        	FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/registrazioneImpiegato/CaricamentoRegistrazioneImpiegato.fxml"));
			Parent root = loader.load();
			Scene scene = new Scene(root, 347.0, 176.0);
			
			controllerRegistrazioneImpiegato = loader.getController();
			
			window.setResizable(false);
			window.setTitle("Caricamento in corso...");
			window.centerOnScreen();
			
			window.setScene(scene);
			window.show();
			
			System.out.println("width: "+window.getWidth());
			System.out.println("Height: "+window.getHeight());
		} catch (IOException e) {
			System.err.println("Impossibile caricare la finestra!");
			//e.printStackTrace();
		}
    }
}
