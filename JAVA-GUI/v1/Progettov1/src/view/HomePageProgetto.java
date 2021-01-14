package view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Progetto;

import java.io.PrintWriter;

import controller.ControllerHomePageProgetto;

public class HomePageProgetto
{
	ControllerHomePageProgetto controllerHomePageProgetto;
    private final PrintWriter writer;

    public HomePageProgetto(PrintWriter writer)
    {
        this.writer = writer;
    }

    public void start(Stage window, Stage popup) throws Exception {
    	
    	FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("view/fxml/Homepages/homepageprojectmanager.fxml"));
    	Parent root = loader.load();
    	
    	controllerHomePageProgetto = loader.getController();
    	controllerHomePageProgetto.setStage(window, popup);
    	controllerHomePageProgetto.inizializza(new Progetto(null)); //non deve essere nuovo ma deve essere recuperato
    																//dalla HomePageImpiegato
    	
    	Scene scene = new Scene(root);
        window.setScene(scene);
        
        window.setTitle(window.toString());
        window.show();
    }
}
