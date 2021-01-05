package view;

import controller.ControllerHomePageImpiegato;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Impiegato;

import java.io.PrintWriter;

public class HomePageImpiegato
{
    private final PrintWriter writer;
    ControllerHomePageImpiegato controller = new ControllerHomePageImpiegato();

    Impiegato impiegato;
    public HomePageImpiegato(PrintWriter writer, Impiegato impiegato)
    {
        this.writer = writer;
        this.impiegato = impiegato;

    }

    public void start(Stage window) throws Exception
    {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("view/fxml/homepageimpiegato.fxml"));
        Scene scene =  new Scene(root);
        controller.inizializza(impiegato);
        window.setScene(scene);
        window.show();

    }

}
