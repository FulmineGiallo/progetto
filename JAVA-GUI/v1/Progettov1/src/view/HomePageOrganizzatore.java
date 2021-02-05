package view;

import controller.ControllerHomePageOrganizzatore;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Impiegato;
import model.Riunione;

public class HomePageOrganizzatore {
	
	private ControllerHomePageOrganizzatore controllerHomePageOrganizzatore;
    private Riunione riunione;
    private Impiegato organizzatore;

    public HomePageOrganizzatore(Impiegato organizzatore, Riunione riunione)
    {
        this.riunione = riunione;
        this.organizzatore = organizzatore;
    }

    public void start(Stage window, Stage popup) throws Exception {
    	
    	FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("view/fxml/Homepages/homepageorganizzatore.fxml"));
    	Parent root = loader.load();
        Scene scene = new Scene(root);
        
    	controllerHomePageOrganizzatore = loader.getController();
    	controllerHomePageOrganizzatore.setStage(window, popup);
    	controllerHomePageOrganizzatore.inizializza(organizzatore, riunione);
        
    	window.hide();
    	window.setScene(scene);
        
        window.setTitle("Home page \"" + riunione.getTitolo() + "\"");
        
        window.setMaximized(true);
        window.setMinWidth(850.0);
        window.setMinHeight(650.0);
        window.centerOnScreen();
        
        window.show();
    }
}
