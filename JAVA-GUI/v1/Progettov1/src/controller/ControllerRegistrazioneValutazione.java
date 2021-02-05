package controller;

import java.sql.Connection;
import java.sql.SQLException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Impiegato;
import model.Progetto;
import model.Riunione;
import model.Connection.DBConnection;
import model.Dao.ValutazioneDao;
import model.DaoInterface.ValutazioneDaoInterface;
import utilities.MetodiComuni;
import view.FinestraPopup;
import view.HomePageOrganizzatore;
import view.HomePageProjectManager;

public class ControllerRegistrazioneValutazione {

    @FXML private AnchorPane 	RegistrazioneRiunione;
    
    @FXML private HBox 			IstruzioniBox;
    @FXML private Label 		IstruzioniLabel;
    @FXML private Label 		IstruzioniLabel2;
    
    @FXML private HBox 			FormBox;
    @FXML private ScrollPane 	FormScrollPane;
    @FXML private VBox 			FormValutazione;
    
    @FXML private HBox 			RecensoreBox;
    @FXML private Label 		RecensoreLabel;
    @FXML private TextField 	RecensoreTF;
    
    @FXML private HBox 			RecensitoBox;
    @FXML private Label 		RecensitoLabel;
    @FXML private TextField 	RecensitoTF;
    
    @FXML private HBox 			StelleBox;
    @FXML private Label 		StelleLabel;
    
    @FXML private HBox 			StelleOkBox;
    @FXML private ImageView 	StellaOk1;
    @FXML private ImageView 	StellaOk2;
    @FXML private ImageView 	StellaOk3;
    @FXML private ImageView 	StellaOk4;
    @FXML private ImageView 	StellaOk5;
    
    @FXML private HBox 			StelleNoBox;
    @FXML private ImageView 	StellaNo1;
    @FXML private ImageView 	StellaNo2;
    @FXML private ImageView 	StellaNo3;
    @FXML private ImageView 	StellaNo4;
    @FXML private ImageView 	StellaNo5;
    
    @FXML private VBox 			TitoloBox;
    @FXML private Label 		TitoloLabel;
    @FXML private TextField 	TitoloTF;
    @FXML private Label 		TitoloErrorLabel;
    
    @FXML private VBox 			RecensioneBox;
    @FXML private Label 		RecensioneLabel;
    @FXML private TextArea 		RecensioneTA;
    
    @FXML private AnchorPane 	ButtonBar;
    @FXML private Button 		AnnullaButton;
    @FXML private Button 		ConfermaButton;
    
    private Stage window;
    private Stage popup;
    
    private Impiegato 	recensito;
    private Progetto 	progetto;
    private Riunione	riunione;
    
    private boolean checkTitolo;
    
    private MetodiComuni utils = new MetodiComuni();
    
    private HomePageOrganizzatore 	homePageOrganizzatore;
    private HomePageProjectManager	homePageProjectManager;
    private FinestraPopup	 		finestraConferma;
    private FinestraPopup	 		finestraDomanda;
    private FinestraPopup	 		finestraErrore;
    
    private Connection connection;
    private DBConnection dbConnection;
    
    private ValutazioneDaoInterface valutazioneDao;
    
    public void setStage(Stage window, Stage popup) {
    	this.window = window;
    	this.popup = popup;
    }
    
    public void inizializza(Impiegato recensito, Progetto progetto) {    	
    	this.progetto	= progetto;
    	this.recensito 	= recensito;
    	
    	if(progetto != null)
    		RecensoreTF.setText(progetto.getProjectManager().toString());
    	
    	if(recensito != null)
    		RecensitoTF.setText(recensito.toString());
    }
    
    public void inizializza(Impiegato recensito, Riunione riunione) {
    	this.riunione	= riunione;
    	this.recensito 	= recensito;
    	
    	if(riunione != null)
    		RecensoreTF.setText(riunione.getOrganizzatore().toString());
    	
    	if(recensito != null)
    		RecensitoTF.setText(recensito.toString());
    }

    @FXML private void annullaOperazione(ActionEvent event) {

    }

    @FXML private void clickStellaOk1(MouseEvent event) {
    	StellaOk1.setVisible(!StellaOk1.isVisible());
    }
    
    @FXML private void clickStellaOk2(MouseEvent event) {
    	
    }
    
    @FXML private void clickStellaOk3(MouseEvent event) {
    	
    }
    
    @FXML private void clickStellaOk4(MouseEvent event) {
    	
    }
    
    @FXML private void clickStellaOk5(MouseEvent event) {
    	
    }
    
    private boolean controlloCampi() {
    	TitoloTF.setText("");
    	checkTitolo = true;
    	
    	switch(utils.controlloStringa(TitoloTF.getText(), "[A-Za-z\s0-9-]+")) {
    		case 1:
    			checkTitolo = false;
    			TitoloTF.setText("Questo campo è obbligatorio");
    			break;
    		case 2:
    			checkTitolo = false;
    			TitoloTF.setText("Il titolo della valutazione può contenere solo caratteri alfanumerici");
    			break;
			default:
				checkTitolo = true;
    	}
    	
    	return checkTitolo;
    }

    @FXML private void confermaOperazione(ActionEvent event) {
    	
    	if (controlloCampi()) {
			try {
				dbConnection = new DBConnection();
				connection = dbConnection.getConnection();
				
				valutazioneDao = new ValutazioneDao(connection);
				
				if(progetto != null && riunione == null) {
					//inserimento tramite valutazioneDao di una nuova valutazione di progetto
					homePageProjectManager = new HomePageProjectManager(recensito, progetto);
				} else if(progetto == null && riunione != null) {
					//inserimento tramite valutazioneDao di una nuova valutazione di riunione
					homePageOrganizzatore  = new HomePageOrganizzatore(recensito, riunione);
				} else {
					throw new SQLException();
				}
				
				finestraConferma = new FinestraPopup();
                try {
                	homePageOrganizzatore.start(window, popup);
					finestraConferma .start(popup, "La riunione è stata registrata correttamente nel database.");
				} catch (Exception e) {
					e.printStackTrace();
				}
			} catch (SQLException throwables) {
				finestraErrore = new FinestraPopup();
				
            	try {
					finestraErrore.start(popup, "Errore durante la registrazione", throwables);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
    }

    @FXML private void visualizzaTitoloLabel(MouseEvent event) {

    }
    
    @FXML private void visualizzaRecensioneLabel(MouseEvent event) {

    }

}

