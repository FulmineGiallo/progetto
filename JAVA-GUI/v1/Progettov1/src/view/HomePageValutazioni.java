package view;

import controller.ControllerValutazioni;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Impiegato;

public class HomePageValutazioni {

	ControllerValutazioni controllerValutazioni;
	Impiegato impiegato;
	    
	public HomePageValutazioni(Impiegato impiegato) {
		this.impiegato = impiegato;
	}
	
	public void start(Stage window) throws Exception
	{
		FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("view/fxml/Homepages/homepagevalutazioni.fxml"));
	    Parent root = loader.load();
	    Scene scene =  new Scene(root); 
	    
		controllerValutazioni = loader.getController();
	    controllerValutazioni.inizializza(impiegato, window);
	
	    //window.setTitle("Homepage Valutazioni");
	    window.setTitle(window.toString());
	    window.setScene(scene);
	    window.show();
	}
}
