
package controller;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import view.Registrazione;

import java.io.IOException;
import java.io.PrintWriter;

public class Controller
{
    @FXML
    Button invia;
    @FXML
    Label cambia;

    Registrazione frame;
    public void initialize() throws Exception
    {
        PrintWriter writer = null;
        frame = new Registrazione(writer);
    }

    public void inviaCurriculum(javafx.event.ActionEvent actionEvent) throws Exception {
        Stage stage = (Stage)invia.getScene().getWindow();
        frame.start(stage);
    }
}