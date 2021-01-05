package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

import model.Connection.DBBuilder;
import model.Connection.DBConnection;
import model.Dao.ImpiegatoDao;
import model.DaoInterface.ImpiegatoDaoInterface;
import model.Impiegato;
import view.HomePageBenvenuto;
import view.HomePageImpiegato;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;


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
    String email;
    String password;
    Connection connection;

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

    public void checkLogin(ActionEvent actionEvent) throws SQLException, Exception
    {

        if(emailField.getText().equals("m.carofano@studenti.unina.it") && passwordField.getText().equals("ciao"))
        {
            /* QUERY AVVENUTA CON SUCCESSO UTENTE PRESENTE NEL DB */

            DBConnection connDB;


            connDB = DBConnection.getInstance();
            connection = connDB.getConnection();

            ImpiegatoDaoInterface impiegato = new ImpiegatoDao(connection);
            System.out.println("Valido" + impiegato.getNome(emailField.getText(), accesso));


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
