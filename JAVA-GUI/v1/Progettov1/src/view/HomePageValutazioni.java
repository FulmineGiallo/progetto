package view;

import java.io.IOException;
import java.sql.SQLException;

import controller.ControllerValutazioni;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Impiegato;

public class HomePageValutazioni {

	private ControllerValutazioni 	controllerValutazioni;
	private FinestraPopup			finestraErrore;
	
	private Impiegato impiegato;
	    
	public HomePageValutazioni(Impiegato impiegato) {
		this.impiegato = impiegato;
	}
	
	public void start(Stage window, Stage popup) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("view/fxml/Homepages/homepagevalutazioni.fxml"));
			Parent root = loader.load();
			Scene scene =  new Scene(root);
			
			window.hide();
			window.setScene(scene);
			
			controllerValutazioni = loader.getController();
			controllerValutazioni.setStage(window, popup);
			controllerValutazioni.inizializza(impiegato);
			
			window.setTitle("Le tue valutazioni");
			window.setFullScreen(true);
			window.setMaximized(true);
			window.centerOnScreen();
			window.setMinWidth(1100.0);
			window.setMinHeight(500.0);
			
			window.show();
		} catch (IOException erroreCaricamento) {
			finestraErrore = new FinestraPopup();
			
			try {
				finestraErrore.start(popup, "Impossibile caricare la homepage delle valutazioni", erroreCaricamento);
			} catch (Exception e) {
				System.err.println("Impossibile caricare la homepage delle valutazioni");
			}
		} catch (SQLException erroreDatabase) {
			finestraErrore = new FinestraPopup();
			
			try {
				finestraErrore.start(popup, "Errore di connessione al database", erroreDatabase);
			} catch (Exception e) {
				System.err.println("Impossibile caricare la homepage delle valutazioni");
			}
		}
	}
}
