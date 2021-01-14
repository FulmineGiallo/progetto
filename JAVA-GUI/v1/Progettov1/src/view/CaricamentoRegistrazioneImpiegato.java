package view;

import java.io.IOException;

import javafx.animation.PauseTransition;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;

import controller.ControllerRegistrazioneImpiegato;

public class CaricamentoRegistrazioneImpiegato {
	
	ControllerRegistrazioneImpiegato controllerRegistrazioneImpiegato;
	HomePageBenvenuto homePageBenvenuto;

    public CaricamentoRegistrazioneImpiegato() {
    	
    }

    public void start(Stage popup) {
        try
		{
        	FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/registrazioneImpiegato/CaricamentoRegistrazioneImpiegato.fxml"));
			Parent root = loader.load();
			Scene scene = new Scene(root, 400.0, 200.0);
			
			controllerRegistrazioneImpiegato = loader.getController();
			
			popup.setResizable(false);
			popup.setTitle("Caricamento in corso...");
			popup.centerOnScreen();
			
			popup.setScene(scene);
			popup.show();
			
			PauseTransition delay = new PauseTransition(Duration.seconds(3));
			delay.setOnFinished( event -> popup.close());
			delay.play();

		} catch (IOException e)
		{
			System.err.println("Impossibile caricare la finestra!");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
