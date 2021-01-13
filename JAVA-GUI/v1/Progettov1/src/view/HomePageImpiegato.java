package view;

import controller.ControllerHomePageImpiegato;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.DaoInterface.ProgettoDaoInterface;
import model.Impiegato;
import model.Progetto;

import java.io.PrintWriter;

public class HomePageImpiegato
{
    ControllerHomePageImpiegato controllerHomePageImpiegato;
    Impiegato impiegato;

    public HomePageImpiegato(Impiegato impiegato)
    {
        this.impiegato = impiegato;
    }

    public void start(Stage window) throws Exception
    {
    	FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("view/fxml/Homepages/HomePageImpiegato.fxml"));
        Parent root = loader.load();
        Scene scene =  new Scene(root);

		window.hide();
        window.setScene(scene);
				
		controllerHomePageImpiegato = loader.getController();
		controllerHomePageImpiegato.inizializza(impiegato, window);
		
		//window.setTitle("La tua homepage");
		window.setTitle(window.toString());
		window.setMaximized(true);
		window.centerOnScreen();
		window.setMinHeight(630.0);
		window.setMinWidth(900.0);
		
		window.show();
    }

}
