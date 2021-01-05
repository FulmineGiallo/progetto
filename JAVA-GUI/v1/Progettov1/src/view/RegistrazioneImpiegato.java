package view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.PrintWriter;

public class RegistrazioneImpiegato
{
    private final PrintWriter writer;

    public RegistrazioneImpiegato(PrintWriter writer) {
        this.writer = writer ;
    }

    public void start(Stage window) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("fxml/registrazioneimpiegato.fxml"));
        Scene scene =  new Scene(root);
		window.setScene(scene);
        
        window.setMinWidth(850.0);
        window.setMinHeight(450.0);
        window.setTitle("Registrazione");
        window.centerOnScreen();
        
        window.show();
    }
}
