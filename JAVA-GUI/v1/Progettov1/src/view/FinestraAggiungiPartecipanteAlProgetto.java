package view;

import controller.ControllerRicercaImpiegati;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Progetto;

public class FinestraAggiungiPartecipanteAlProgetto {

	ControllerRicercaImpiegati controllerRicercaImpiegati;

    public void start(Stage window, Stage popup, Progetto progetto) throws Exception {
    	
    	FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("view/fxml/FormRicercaImpiegati.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        
		window.hide();
		window.setScene(scene);

        controllerRicercaImpiegati = loader.getController();
        controllerRicercaImpiegati.setStage(window, popup);
        controllerRicercaImpiegati.inizializza(progetto);
        
        window.setTitle("Registrazione nuovo progetto");
        window.setWidth(850.0);
        window.setMinWidth(850.0);
        window.setHeight(450.0);
        window.setMinHeight(450.0);
        window.setMaximized(true);
        window.centerOnScreen();
        
        window.show();
    }
	
	
}
