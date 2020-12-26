package sample;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.PrintWriter;

public class Registrazione
{
    private final PrintWriter writer ;

    public Registrazione(PrintWriter writer) {
        this.writer = writer ;
    }

    public void start(Stage window) throws Exception
    {
        Parent root = FXMLLoader.load(getClass().getResource("registrazione.fxml"));
        Scene scene = new Scene(root);
        window.setScene(scene);
        window.show();
    }
}
