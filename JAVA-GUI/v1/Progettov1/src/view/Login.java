package view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.PrintWriter;

import controller.ControllerLogin;

public class Login
{
	private ControllerLogin controllerLogin;
	private FinestraPopup	finestraErrore;
    
    public void start(Stage window, Stage popup) {
    	try {
    		    		
			FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("view/fxml/Login.fxml"));
			Parent root = loader.load();
			Scene scene = new Scene(root);
			
			window.hide();			
			window.setScene(scene);
					
			controllerLogin = loader.getController();
			controllerLogin.setStage(window, popup);
			
			window.setTitle("Login");
			window.setFullScreen(true);
			window.setMaximized(true);
			window.centerOnScreen();
			window.setMinHeight(550.0);
			window.setMinWidth(850.0);
			
			window.show();
    	} catch (IOException erroreCaricamento) {
			finestraErrore = new FinestraPopup();
			
			try {
				finestraErrore.start(popup, "Impossibile caricare la finestra di login", erroreCaricamento);
			} catch (Exception e) {
				System.err.println("Impossibile caricare la finestra di login");
			}
		}
        
    }
}