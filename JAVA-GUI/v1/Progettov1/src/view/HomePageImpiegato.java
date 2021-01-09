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
    private final PrintWriter writer;
    ControllerHomePageImpiegato controllerHomePageImpiegato;
    Impiegato impiegato;


    public HomePageImpiegato(PrintWriter writer, Impiegato impiegato)
    {
        this.writer = writer;
        this.impiegato = impiegato;
    }

    public void start(Stage window) throws Exception
    {
    	FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("view/fxml/Homepages/HomePageImpiegato.fxml"));
        Parent root = loader.load();
        Scene scene =  new Scene(root);
        
        controllerHomePageImpiegato = loader.getController();
        
        controllerHomePageImpiegato.inizializza(impiegato);
        window.setTitle("HomePage Impiegato");
        window.setScene(scene);
        window.show();

    }

}
