package view;

import controller.ControllerHomePageImpiegato;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Impiegato;


public class HomePageImpiegato
{
    ControllerHomePageImpiegato controllerHomePageImpiegato;
    Impiegato impiegato;

    public HomePageImpiegato(Impiegato impiegato)
    {
        this.impiegato = impiegato;
    }

    public void start(Stage window, Stage popup) throws Exception
    {
    	FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("view/fxml/Homepages/HomePageImpiegato.fxml"));
        Parent root = loader.load();
        Scene scene =  new Scene(root);

		window.hide();
        window.setScene(scene);
				
		controllerHomePageImpiegato = loader.getController();
		controllerHomePageImpiegato.setStage(window, popup);
		controllerHomePageImpiegato.inizializza(impiegato);
		
		window.setTitle("La tua homepage");
		window.setMaximized(true);
		window.centerOnScreen();
		window.setMinHeight(650.0);
		window.setMinWidth(950.0);
		
		window.show();
    }

}
