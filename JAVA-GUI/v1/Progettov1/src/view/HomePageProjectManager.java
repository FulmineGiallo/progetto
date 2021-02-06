package view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Impiegato;
import model.Progetto;

import java.io.IOException;
import java.sql.SQLException;

import controller.ControllerHomePageProjectManager;

public class HomePageProjectManager
{
	private ControllerHomePageProjectManager controllerHomePageProjectManager;
	private FinestraPopup					 finestraErrore;
	
    private Progetto progetto;
    private Impiegato projectManager;

    public HomePageProjectManager(Impiegato projectManager, Progetto progetto)
    {
        this.progetto = progetto;
        this.projectManager = projectManager;
    }

    public void start(Stage window, Stage popup){
        try {
			FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("view/fxml/Homepages/homepageprojectmanager.fxml"));
			Parent root = loader.load();
			Scene scene = new Scene(root);

			controllerHomePageProjectManager = loader.getController();
			controllerHomePageProjectManager.setStage(window, popup);
			controllerHomePageProjectManager.inizializza(projectManager, progetto);
			
			window.hide();
			window.setScene(scene);
			
			window.setTitle("Home page \"" + progetto.getTitolo() + "\"");
			
			window.setMaximized(true);
			window.setFullScreen(true);
			window.setMinWidth(850.0);
			window.setMinHeight(650.0);
			window.centerOnScreen();
			
			window.show();
        } catch (IOException erroreCaricamento) {
        	finestraErrore = new FinestraPopup();
        	
        	try {
        		finestraErrore.start(popup, "Impossibile caricare la home page di \"" + progetto.getTitolo() + "\"", erroreCaricamento);
        	} catch (Exception e) {
        		System.err.println("Impossibile caricare la home page di \"" + progetto.getTitolo() + "\"");
        	}
        } catch (SQLException erroreDatabase) {
        	finestraErrore = new FinestraPopup();
        	
        	try {
        		finestraErrore.start(popup, "Errore di connessione al database", erroreDatabase);
        	} catch (Exception e) {
        		System.err.println("Impossibile caricare la home page di \"" + progetto.getTitolo() + "\"");
        	}
        }
    }
}
