package view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Impiegato;
import model.Progetto;

import controller.ControllerHomePageProjectManager;

public class HomePageProjectManager
{
	ControllerHomePageProjectManager controllerHomePageProjectManager;


    Progetto progetto;
    Impiegato projectManager;

    public HomePageProjectManager(Impiegato projectManager, Progetto progetto)
    {
        this.progetto = progetto;
        this.projectManager = projectManager;
    }

    public void start(Stage window, Stage popup) throws Exception {
        
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
        window.setMinWidth(850.0);
        window.setMinHeight(650.0);
        window.centerOnScreen();
        
        window.show();
    }
}
