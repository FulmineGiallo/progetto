package view;

import java.io.PrintWriter;

import controller.ControllerHomePageImpiegato;
import controller.ControllerValutazioni;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Impiegato;

public class HomePageValutazioni {

	   ControllerValutazioni controllerValutazioni;
	   Impiegato impiegato;
	    
	    
	 private final PrintWriter writer;
	 
	 public HomePageValutazioni(PrintWriter writer, Impiegato impiegato)
	    {
	        this.writer = writer;
	        this.impiegato = impiegato;
	    }
	 


	    public void start(Stage window) throws Exception
	    {
	    	FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("view/fxml/Homepages/homepagevalutazioni.fxml"));
	        Parent root = loader.load();
	        Scene scene =  new Scene(root); 
	        
	    	controllerValutazioni = loader.getController();
	        controllerValutazioni.inizializza(impiegato);
	    
	        window.setTitle("Home page valutazioni");
	        window.setScene(scene);
	        window.show();

	    }
}
