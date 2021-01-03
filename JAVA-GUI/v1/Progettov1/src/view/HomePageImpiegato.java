package view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.PrintWriter;

public class HomePageImpiegato
{
    private final PrintWriter writer;

    public HomePageImpiegato(PrintWriter writer)
    {
        this.writer = writer;
    }

    public void start(Stage window) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("fxml/homepageimpiegato.fxml"));
        Scene scene =  new Scene(root);
        window.setScene(scene);
        window.show();
    }
}
