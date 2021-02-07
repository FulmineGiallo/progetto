package view;

import java.io.IOException;
import java.sql.SQLException;

import controller.ControllerHomePageImpiegato;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Impiegato;

public class HomePageImpiegato
{
    private ControllerHomePageImpiegato controllerHomePageImpiegato;
    private FinestraPopup				finestraErrore;
    
    private Impiegato impiegato;

    public HomePageImpiegato(Impiegato impiegato) {
        this.impiegato = impiegato;
    }

    public void start(Stage window, Stage popup) {
    	try {
			FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("view/fxml/Homepages/HomePageImpiegato.fxml"));
			Parent root = loader.load();
			Scene scene =  new Scene(root);

			window.hide();
			window.setScene(scene);
					
			controllerHomePageImpiegato = loader.getController();
			controllerHomePageImpiegato.setStage(window, popup);
			controllerHomePageImpiegato.inizializza(impiegato);
			
			window.setTitle("La tua homepage");
			window.setFullScreen(true);
			window.setFullScreenExitHint("");
			window.setMaximized(true);
			window.centerOnScreen();
			window.setMinHeight(650.0);
			window.setMinWidth(950.0);
			
			window.show();
    	} catch (IOException erroreCaricamento) {
			finestraErrore = new FinestraPopup();
			
			try {
				finestraErrore.start(popup, "Impossibile caricare la tua homepage", erroreCaricamento);
			} catch (Exception e) {
				System.err.println("Impossibile caricare la tua homepage");
			}
    	} catch (SQLException erroreDatabase) {
			finestraErrore = new FinestraPopup();
			
			try {
				finestraErrore.start(popup, "Errore di connessione al database", erroreDatabase);
			} catch (Exception e) {
				System.err.println("Impossibile caricare la tua homepage");
			}
		}
    }

}
