
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
    @FXML
    Label cambia;

    RegistrazioneImpiegato frame;
    public void initialize() throws Exception
    {
        PrintWriter writer = null;
        frame = new RegistrazioneImpiegato(writer);
    }

    public void inviaCurriculum(javafx.event.ActionEvent actionEvent) throws Exception {
        Stage stage = (Stage)invia.getScene().getWindow();
        frame.start(stage);
    }
}