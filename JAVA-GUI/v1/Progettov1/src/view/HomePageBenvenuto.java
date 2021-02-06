package view;


import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.PrintWriter;

import controller.ControllerHomePageBenvenuto;

public class HomePageBenvenuto
{
	private ControllerHomePageBenvenuto controllerHomePageBenvenuto;
	private FinestraPopup				finestraErrore;

    public void start(Stage window, Stage popup) {
        try {
        		    	
			FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("view/fxml/Homepages/homepagebenvenuto.fxml"));
			Parent root = loader.load();
			Scene scene = new Scene(root);
			
			window.hide();
			window.setScene(scene);
			
			controllerHomePageBenvenuto = loader.getController();
			controllerHomePageBenvenuto.setStage(window, popup);
			
			window.setTitle("Home Page");
			window.setFullScreen(true);
			window.setMaximized(true);
			window.centerOnScreen();
			window.setMinWidth(1100.0);
			window.setMinHeight(500.0);
			
			window.show();			
		} catch (IOException erroreCaricamento) {
			finestraErrore = new FinestraPopup();
			
			try {
				finestraErrore.start(popup, "Impossibile caricare la finestra di benvenuto", erroreCaricamento);
			} catch (Exception e) {
				System.err.println("Impossibile caricare la finestra di benvenuto");
			}
		}
    }
}
