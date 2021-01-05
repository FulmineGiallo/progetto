package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Connection.DBConnection;
import model.Dao.ImpiegatoDao;
import model.DaoInterface.ImpiegatoDaoInterface;
import model.Impiegato;
import view.HomePageBenvenuto;
import view.HomePageImpiegato;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;


public class ControllerLogin
{
    HomePageBenvenuto benvenuto;
    HomePageImpiegato homeImpiegato;

    @FXML private Button backHomePage;
    @FXML private Button accedi;
    @FXML private TextField emailField;
    @FXML private PasswordField passwordField;
    @FXML private Button okButton;

    int accesso = 0;
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
        if(!emailField.getText().isEmpty() && !passwordField.getText().isEmpty())
        {
            /*Istanzio connessione per vedere se l'impiegato esiste */
            DBConnection connDB;
            connDB = DBConnection.getInstance();
            connection = connDB.getConnection();

            /*Creo impiegatoDao e gli passo la connessione */
            ImpiegatoDaoInterface impiegatoDao = new ImpiegatoDao(connection);

            /*Eseguo query */
            accesso = impiegatoDao.impiegatoExist(emailField.getText(), passwordField.getText());
            Impiegato impiegato = null;


            /* QUERY AVVENUTA CON SUCCESSO UTENTE PRESENTE NEL DB */
            if(accesso == 1)
            {
                impiegato = impiegatoDao.creaImpiegato(impiegatoDao.getCFWithEmail(emailField.getText()));

                PrintWriter writer = null;
                homeImpiegato = new HomePageImpiegato(writer, impiegato);

                Stage stage = (Stage)accedi.getScene().getWindow();
                homeImpiegato.start(stage);
            }
            /* Non ci sono email e password corrispondenti */
            if(accesso == 0)
            {
                Stage window;
                window = new Stage();
                Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("view/fxml/errorelogin.fxml"));
                Scene scene =  new Scene(root, 450,176);
                window.setScene(scene);
                window.setTitle("Errore Impiegato!");
                window.initModality(Modality.APPLICATION_MODAL);
                window.setResizable(false);
                window.show();
            }
        }
        /* Se i campi sono vuoti */
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
