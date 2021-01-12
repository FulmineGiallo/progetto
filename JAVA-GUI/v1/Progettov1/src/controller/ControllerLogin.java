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
import view.FinestraErrore;
import view.FormRegistrazioneImpiegato;
import view.HomePageBenvenuto;
import view.HomePageImpiegato;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;


public class ControllerLogin
{
    HomePageBenvenuto benvenuto;
    HomePageImpiegato homeImpiegato;
    FinestraErrore popup;

    @FXML private Button AnnullaButton;
    @FXML private Button AccediButton;
    @FXML private PasswordField PasswordTF;
    @FXML private TextField EmailTF;


    int accesso = 0;
    Connection connection;


    public void backHomePageBenvenuto(ActionEvent actionEvent) throws Exception {
        benvenuto = new HomePageBenvenuto();
        benvenuto.start(new Stage());
    }

    public void checkLogin(ActionEvent actionEvent) throws SQLException, Exception {
        if(!EmailTF.getText().isEmpty() && !PasswordTF.getText().isEmpty())
        {
            /*Istanzio connessione per vedere se l'impiegato esiste */
            DBConnection connDB;
            connDB = DBConnection.getInstance();
            connection = connDB.getConnection();

            /*Creo impiegatoDao e gli passo la connessione */
            ImpiegatoDaoInterface impiegatoDao = new ImpiegatoDao(connection);

            /*Eseguo query */
            accesso = impiegatoDao.impiegatoExist(EmailTF.getText(), PasswordTF.getText());
            Impiegato impiegato = null;


            /* QUERY AVVENUTA CON SUCCESSO UTENTE PRESENTE NEL DB */
            if(accesso == 1)
            {
                impiegato = impiegatoDao.creaImpiegato(impiegatoDao.getCFWithEmail(EmailTF.getText()));

                PrintWriter writer = null;
                homeImpiegato = new HomePageImpiegato(writer, impiegato);

                Stage stage = (Stage)AccediButton.getScene().getWindow();
                homeImpiegato.start(stage);
            }
            /* Non ci sono email e password corrispondenti */
            if(accesso == 0)
            {            	
    			//PrintWriter writer = null;
    			popup = new FinestraErrore("Email o password errati", null);
    			popup.start(new Stage());
            }
        }
        /* Se i campi sono vuoti */
        else
        {        	
			//PrintWriter writer = null;
			popup = new FinestraErrore("Autenticazione errata", null);
			popup.start(new Stage());
        }
    }
}
