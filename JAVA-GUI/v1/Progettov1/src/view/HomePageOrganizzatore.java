package view;

import java.io.IOException;
import java.sql.SQLException;

import controller.ControllerHomePageOrganizzatore;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Impiegato;
import model.Riunione;

public class HomePageOrganizzatore {
	
	private ControllerHomePageOrganizzatore controllerHomePageOrganizzatore;
	private FinestraPopup					finestraErrore;
	
    private Riunione riunione;
    private Impiegato organizzatore;

    public HomePageOrganizzatore(Impiegato organizzatore, Riunione riunione) {
        this.riunione = riunione;
        this.organizzatore = organizzatore;
    }

    public void start(Stage window, Stage popup){
    	try {
			FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("view/fxml/Homepages/homepageorganizzatore.fxml"));
			Parent root = loader.load();
			Scene scene = new Scene(root);
			
			controllerHomePageOrganizzatore = loader.getController();
			controllerHomePageOrganizzatore.setStage(window, popup);
			controllerHomePageOrganizzatore.inizializza(organizzatore, riunione);
			
			window.hide();
			window.setScene(scene);
			
			window.setTitle("Home page \"" + riunione.getTitolo() + "\"");
			
			window.setFullScreen(true);
			window.setFullScreenExitHint("");
			window.setMaximized(true);
			window.setMinWidth(850.0);
			window.setMinHeight(650.0);
			window.centerOnScreen();
			
			window.show();
    	} catch (IOException erroreCaricamento) {
    		finestraErrore = new FinestraPopup();
    		
    		try {
    			finestraErrore.start(popup, "Impossibile caricare la homepage di \"" + riunione.getTitolo() + "\"", erroreCaricamento);
    		} catch (Exception e) {
    			System.err.println("Impossibile caricare la homepage di \"" + riunione.getTitolo() + "\"");
    		}
    	} catch (SQLException erroreDatabase) {
    		finestraErrore = new FinestraPopup();
    		
    		try {
    			finestraErrore.start(popup, "Errore di connessione al database", erroreDatabase);
    		} catch (Exception e) {
    			System.err.println("Impossibile caricare la homepage di \"" + riunione.getTitolo() + "\"");
    		}
    	}
    }
}
