package view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.PrintWriter;

import controller.ControllerLogin;

public class Login
{
	ControllerLogin controllerLogin;
    private final PrintWriter writer;

    public Login(PrintWriter writer) {
        this.writer = writer ;
    }
    
    
    public void start(Stage window) throws Exception {
    	
    	FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("view/fxml/Login.fxml"));
    	Parent root = loader.load();
    	Scene scene = new Scene(root);		
    			
    	controllerLogin = loader.getController();
    	window.setScene(scene);
        window.show();
    }
}