
package controller;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import view.RegistrazioneImpiegato;

import java.io.PrintWriter;

public class Controller
{
    @FXML
    Button invia;

    RegistrazioneImpiegato registrazione;
    public void initialize() throws Exception
    {
        PrintWriter writer = null;
        registrazione = new RegistrazioneImpiegato(writer);
    }

    public void inviaCurriculum(javafx.event.ActionEvent actionEvent) throws Exception {
        Stage stage = (Stage)invia.getScene().getWindow();
        registrazione.start(stage);
    }
}