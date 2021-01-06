package view;


import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.PrintWriter;

import controller.ControllerHomePageBenvenuto;

public class HomePageBenvenuto
{
	
	
	ControllerHomePageBenvenuto controllerHomePageBenvenuto;
    private final PrintWriter writer;

    public HomePageBenvenuto(PrintWriter writer)
    {
        this.writer = writer;
    }

    public void start(Stage window) throws Exception {
        
    	
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("view/fxml/Homepages/homepagebenvenuto.fxml"));
        Parent root = loader.load();
        Scene scene =  new Scene(root);
		
        
        controllerHomePageBenvenuto = loader.getController();
        window.setScene(scene);
        window.setMinWidth(700.0);
        window.setMinHeight(450.0);
        window.setTitle("Home Page");
        window.centerOnScreen();
        window.show();
    }


}
