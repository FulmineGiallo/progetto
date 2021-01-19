package view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Impiegato;
import model.Progetto;

import java.io.PrintWriter;

import controller.ControllerHomePageProgetto;

public class HomePageProgetto
{
	ControllerHomePageProgetto controllerHomePageProgetto;


    Progetto progetto;
    Impiegato projectManager;

    public HomePageProgetto(Impiegato projectManager, Progetto progetto)
    {
        this.progetto = progetto;
        this.projectManager = projectManager;
    }

    public void start(Stage window, Stage popup) throws Exception {
    	
    	FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("view/fxml/Homepages/homepageprojectmanager.fxml"));
    	Parent root = loader.load();
        Scene scene = new Scene(root);

    	controllerHomePageProgetto = loader.getController();
    	controllerHomePageProgetto.setStage(window, popup);
    	controllerHomePageProgetto.inizializza(projectManager, progetto);

        window.setScene(scene);
        
        window.setTitle(window.toString());
        window.show();
    }
}
