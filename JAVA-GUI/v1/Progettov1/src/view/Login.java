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
	ControllerLogin controllerLogin;
    private final PrintWriter writer;

    public Login(PrintWriter writer) {
        this.writer = writer ;
    }
    
    
    public void start(Stage window) {
    	try {
			FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("view/fxml/Login.fxml"));
			Parent root = loader.load();
			Scene scene = new Scene(root);
			
			window.hide();			
			window.setScene(scene);
					
			controllerLogin = loader.getController();
			
			window.setTitle("Login");
			window.setMaximized(true);
			window.centerOnScreen();
			window.setMinHeight(550.0);
			window.setMinWidth(850.0);
			
			window.show();
		} catch (IOException e) {
			e.printStackTrace();
			//inserire inizializzazione della finestraErrore
		}
        
    }
}