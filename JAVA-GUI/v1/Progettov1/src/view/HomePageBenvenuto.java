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
    private final PrintWriter writer;

    public HomePageBenvenuto(PrintWriter writer)
    {
        this.writer = writer;
    }

    public void start(Stage window) {
        try {
			FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("view/fxml/Homepages/homepagebenvenuto.fxml"));
			Parent root = loader.load();
			Scene scene =  new Scene(root);
			window.setScene(scene);
			
			controllerHomePageBenvenuto = loader.getController();
			
			window.setTitle("Home Page");
			window.setMaximized(true);
			window.setMinWidth(700.0);
			window.setMinHeight(450.0);
			
			window.show();
		} catch (IOException e)
		{
			e.printStackTrace();
			//inserire inizializzazione della finestraErrore
		}
    }
}
