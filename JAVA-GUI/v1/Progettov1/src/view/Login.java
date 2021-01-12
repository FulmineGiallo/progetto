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

    public Login() {

    }
    
    public void start(Stage window) {
    	try {
    		    		
			FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("view/fxml/Login.fxml"));
			Parent root = loader.load();
			Scene scene = new Scene(root);
			
			window.hide();			
			window.setScene(scene);
					
			controllerLogin = loader.getController();
			controllerLogin.recuperaStage(window);
			
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