package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import model.Connection.DBConnection;
import model.Dao.ImpiegatoDao;
import model.DaoInterface.ImpiegatoDaoInterface;
import model.Impiegato;
import view.FinestraPopup;
import view.HomePageBenvenuto;
import view.HomePageImpiegato;

import java.sql.Connection;
import java.sql.SQLException;

public class ControllerLogin {

    @FXML private AnchorPane 	Login;
    @FXML private Label 		TitoloLabel;
    @FXML private GridPane 		Form;
    @FXML private Label 		EmailLabel;
    @FXML private ImageView 	EmailIV;
    @FXML private TextField 	EmailTF;
    @FXML private Label 		PasswordLabel;
    @FXML private ImageView 	PasswordIV;
    @FXML private PasswordField PasswordTF;
    @FXML private AnchorPane 	ButtonBar;
    @FXML private Button 		AnnullaButton;
    @FXML private Button 		AccediButton;
    
    private HomePageBenvenuto homePageBenvenuto;
    private HomePageImpiegato homePageImpiegato;
    
    private Stage window;
    private Stage popup;
    
    private FinestraPopup finestraErrore;
    
    int accesso = 0;
    Connection connection;
    
    public void setStage(Stage window, Stage popup) {
    	this.window = window;
    	this.popup = popup;
    }

    public void backHomePageBenvenuto(ActionEvent actionEvent) throws Exception {
        homePageBenvenuto = new HomePageBenvenuto();
        homePageBenvenuto.start(window, popup);
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
                homePageImpiegato = new HomePageImpiegato(impiegato);
                homePageImpiegato.start(window, popup);
            }
            /* Non ci sono email e password corrispondenti */
            if(accesso == 0)
            {            	
    			finestraErrore = new FinestraPopup();
    			finestraErrore.start(popup, "Email o password errati", new Exception());
            }
        }
        /* Se i campi sono vuoti */
        else
        {        	
			finestraErrore = new FinestraPopup();
			finestraErrore.start(popup, "Autenticazione errata", new Exception());
        }
    }
}
