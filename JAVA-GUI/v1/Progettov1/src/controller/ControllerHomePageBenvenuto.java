package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import view.Login;
import view.RegistrazioneImpiegato;

import java.io.PrintWriter;

public class ControllerHomePageBenvenuto
{
    @FXML
    private Button RegistrazioneButton;
    @FXML
    public Button LoginButton;

    RegistrazioneImpiegato registrazione;
    Login loginPage;


    public void inviaCurriculum(javafx.event.ActionEvent actionEvent) throws Exception
    {
        PrintWriter writer = null;
        registrazione = new RegistrazioneImpiegato(writer);

        Stage stage = (Stage)RegistrazioneButton.getScene().getWindow();
        registrazione.start(stage);
    }

    public void effettua(javafx.event.ActionEvent actionEvent) throws Exception
    {
        PrintWriter writer = null;
        loginPage = new Login(writer);

        Stage stage = (Stage)LoginButton.getScene().getWindow();
        loginPage.start(stage);
    }

}