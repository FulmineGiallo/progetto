package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import view.HomePageBenvenuto;
import view.Login;
import view.RegistrazioneImpiegato;

import java.io.PrintWriter;

public class Controller {
    @FXML
    private Button invia;
    @FXML
    public Button login;


    /* Page Registrazione */
    RegistrazioneImpiegato registrazionePage;
    /*Page Login*/
    Login loginPage;



    public void inviaCurriculum(javafx.event.ActionEvent actionEvent) throws Exception
    {
        PrintWriter writer = null;
        registrazionePage = new RegistrazioneImpiegato(writer);

        Stage stage = (Stage) invia.getScene().getWindow();
        registrazionePage.start(stage);
    }

    public void effettua(javafx.event.ActionEvent actionEvent) throws Exception
    {
        PrintWriter writer = null ;
        loginPage = new Login(writer);

        Stage stage = (Stage) login.getScene().getWindow();
        loginPage.start(stage);
    }


}