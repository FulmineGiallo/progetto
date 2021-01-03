package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import view.HomePageBenvenuto;
import view.HomePageImpiegato;

import java.io.IOException;
import java.io.PrintWriter;


public class ControllerLogin
{
    HomePageBenvenuto benvenuto;
    HomePageImpiegato homeImpiegato;

    @FXML
    private Button backHomePage;
    @FXML
    private Button accedi;
    @FXML
    private TextField emailField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Button okButton;

    boolean accesso = false;
    String email = emailField.getText();
    String password = passwordField.getText();

    public void backHomePageBenvenuto(ActionEvent actionEvent) throws Exception
    {
        PrintWriter writer = null;
        benvenuto = new HomePageBenvenuto(writer);

        Stage stage = (Stage) backHomePage.getScene().getWindow();
        benvenuto.start(stage);
    }
    @FXML
    public void closeButton(ActionEvent event) {
        Stage stage = (Stage)okButton.getScene().getWindow();
        stage.close();
    }

    public void checkLogin(ActionEvent actionEvent) throws Exception
    {
        if(emailField.getText().equals("carminefb@live.it") && passwordField.getText().equals("ciao"))
        {
            /* QUERY AVVENUTA CON SUCCESSO UTENTE PRESENTE NEL DB */
            System.out.println("Valido");
            System.out.println("Email" + emailField.getText() +" Password" + passwordField.getText());
            PrintWriter writer = null;


            homeImpiegato = new HomePageImpiegato(writer);

            Stage stage = (Stage)accedi.getScene().getWindow();
            homeImpiegato.start(stage);
        }
        else
        {
            Stage window;
            window = new Stage();
            Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("view/fxml/erroreautenticazione.fxml"));
            Scene scene =  new Scene(root, 350,176);
            window.setScene(scene);
            window.setTitle("Errore Autenticazione!");
            window.initModality(Modality.APPLICATION_MODAL);
            window.setResizable(false);
            window.show();

        }
    }
}
