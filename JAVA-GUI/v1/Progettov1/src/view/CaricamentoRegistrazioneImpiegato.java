package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Timer;

import controller.ControllerRegistrazioneImpiegato;
import javafx.animation.PauseTransition;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;

public class CaricamentoRegistrazioneImpiegato {
	
	ControllerRegistrazioneImpiegato controllerRegistrazioneImpiegato;
	HomePageBenvenuto homePageBenvenuto;

	private final PrintWriter writer;

    public CaricamentoRegistrazioneImpiegato(PrintWriter writer) {
        this.writer = writer ;
    }

    public void start(Stage window) {
        try
		{
        	FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/registrazioneImpiegato/CaricamentoRegistrazioneImpiegato.fxml"));
			Parent root = loader.load();
			Scene scene = new Scene(root, 347.0, 176.0);
			
			controllerRegistrazioneImpiegato = loader.getController();
			
			window.setResizable(false);
			window.setTitle("Caricamento in corso...");
			window.centerOnScreen();
			
			window.setScene(scene);
			window.show();
			PauseTransition delay = new PauseTransition(Duration.seconds(3));
			delay.setOnFinished( event -> window.close() );
			delay.play();

		} catch (IOException e)
		{
			System.err.println("Impossibile caricare la finestra!");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
