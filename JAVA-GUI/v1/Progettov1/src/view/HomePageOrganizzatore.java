package view;

import controller.ControllerHomePageOrganizzatore;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Impiegato;
import model.Riunione;

public class HomePageOrganizzatore {
	
	ControllerHomePageOrganizzatore controllerHomePageOrganizzatore;


    Riunione riunione;
    Impiegato Organizzatore;

    public HomePageOrganizzatore(Impiegato Organizzatore, Riunione riunione)
    {
        this.riunione = riunione;
        this.Organizzatore = Organizzatore;
    }

    public void start(Stage window, Stage popup) throws Exception {
    	
    	FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("view/fxml/Homepages/homepageorganizzatore.fxml"));
    	Parent root = loader.load();
        Scene scene = new Scene(root);

        window.setScene(scene);
        
    	controllerHomePageOrganizzatore = loader.getController();
    	controllerHomePageOrganizzatore.setStage(window, popup);
    	controllerHomePageOrganizzatore.inizializza(Organizzatore, riunione);
        
        window.setTitle(window.toString());
        window.show();
        
        
        
    }
}
