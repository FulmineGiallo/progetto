package controller; // >> completare gestione slider orari

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Impiegato;
import model.Progetto;
import model.Riunione;
import model.Connection.DBConnection;
import model.Dao.AmbitoDao;
import model.Dao.ProgettoDao;
import model.Dao.RiunioneDao;
import model.Dao.TipologiaDao;
import model.DaoInterface.ProgettoDaoInterface;
import model.DaoInterface.RiunioneDaoInterface;
import utilities.MetodiComuni;
import view.FinestraPopup;
import view.HomePageImpiegato;
import view.HomePageProjectManager;

public class ControllerRegistrazioneRiunione {

    @FXML private AnchorPane 		RegistrazioneRiunione;
    
    @FXML private HBox 				IstruzioniBox;
    @FXML private Label 			IstruzioniLabel;
    @FXML private Label 			IstruzioniLabel2;
    
    @FXML private HBox 				FormBox;
    @FXML private ScrollPane 		FormScrollPane;
    @FXML private VBox 				FormRiunione;
    
    @FXML private HBox 				OrganizzatoreBox;
    @FXML private Label 			OrganizzatoreLabel;
    @FXML private TextField 		OrganizzatoreTF;
    
    @FXML private VBox 				TitoloBox;
    @FXML private Label 			TitoloLabel;
    @FXML private TextField 		TitoloTF;
    @FXML private Label 			TitoloErrorLabel;
    
    @FXML private VBox 				DescrizioneBox;
    @FXML private Label 			DescrizioneLabel;
    @FXML private TextArea 			DescrizioneTA;
    
    @FXML private VBox 				OrarioDiInizioBox;
    @FXML private Label 			OrarioDiInizioLabel;
    @FXML private TextField 		OrarioDiInizioTF;
    @FXML private Label 			OrarioDiInizioErrorLabel;
    
    @FXML private VBox 				OrarioDiFineBox;
    @FXML private Label 			OrarioDiFineLabel;
    @FXML private TextField 		OrarioDiFineTF;
    @FXML private Label 			OrarioDiFineErrorLabel;
    
    @FXML private HBox 				ModalitaRiunioneBox;
    @FXML private ToggleGroup 		ModalitaRiunioneGroup;
    @FXML private Label 			ModalitaRiunioneLabel;
    @FXML private RadioButton 		ModalitaRiunioneRB1;
    @FXML private RadioButton 		ModalitaRiunioneRB2;
    
    @FXML private VBox 				FormRiunioneFisica;
    
    @FXML private VBox 				SedeBox;
    @FXML private Label 			SedeLabel;
    @FXML private TextField 		SedeTF;
    @FXML private Label 			SedeErrorLabel;
    
    @FXML private GridPane 			StanzaBox;
    @FXML private VBox 				NomeStanzaBox;
    @FXML private Label 			NomeStanzaLabel;
    @FXML private TextField 		NomeStanzaTF;
    @FXML private Label 			NomeStanzaErrorLabel;
    
    @FXML private VBox 				PianoStanzaBox;
    @FXML private Label 			PianoStanzaLabel;
    @FXML private TextField 		PianoStanzaTF;
    @FXML private Label 			PianoStanzaErrorLabel;
    
    @FXML private GridPane 			FormRiunioneTelematica;
    
    @FXML private VBox 				NomePiattaformaBox;
    @FXML private Label 			NomePiattaformaLabel;
    @FXML private TextField 		NomePiattaformaTF;
    @FXML private Label 			NomePiattaformaErrorLabel;
    
    @FXML private VBox 				CodiceAccessoBox;
    @FXML private Label 			CodiceAccessoLabel;
    @FXML private PasswordField 	CodiceAccessoPF;
    @FXML private Label 			CodiceAccessoErrorLabel;
    
    @FXML private AnchorPane 		ButtonBar;
    @FXML private Button 			AnnullaButton;
    @FXML private Button 			ConfermaButton;
    
    private Stage window;
    private Stage popup;
    
    private Impiegato organizzatore;
    private Progetto  progetto;
    
    private boolean checkTitolo;
    private boolean checkOrarioDiInizio;
    private boolean checkOrarioDiFine;
    private boolean checkFormRiunioneFisica;
    private boolean checkSede;
    private boolean checkNomeStanza;
    private boolean checkPianoStanza;
    private boolean checkFormRiunioneTelematica;
    private boolean checkNomePiattaforma;
    private boolean checkCodiceAccesso;
    
    private LocalTime orarioDiInizio;
    private LocalTime orarioDiFine;
    
    private MetodiComuni utils = new MetodiComuni();
    
    private HomePageProjectManager homePageProjectManager;
    private FinestraPopup	 finestraConferma;
    private FinestraPopup	 finestraDomanda;
    private FinestraPopup	 finestraErrore;
    
    private Connection connection;
    private DBConnection dbConnection;
    
    public void setStage(Stage window, Stage popup) {
    	this.window = window;
    	this.popup = popup;
    }
    
    public void inizializza(Impiegato organizzatore, Progetto progetto) {
    	
    	try {
            dbConnection = new DBConnection();
            connection = dbConnection.getConnection();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    	
    	this.organizzatore = organizzatore;
    	this.progetto	   = progetto;
    	
    	if(organizzatore != null)
    		OrganizzatoreTF.setText(organizzatore.toString());
    	
    	OrarioDiInizioTF.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                if(OrarioDiInizioTF.getText().length() > 5)
                {
                    s = OrarioDiInizioTF.getText().substring(0, 5);
                    OrarioDiInizioTF.setText(s);
                }
            }
        });
    	
    	OrarioDiFineTF.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                if(OrarioDiFineTF.getText().length() > 5)
                {
                    s = OrarioDiFineTF.getText().substring(0, 5);
                    OrarioDiFineTF.setText(s);
                }
            }
        });
    }
    
    private Riunione inizializzaNuovaRiunione() { // >> da creare
    	Riunione nuovaRiunione = new Riunione(null);
    	
    	return nuovaRiunione;
    }
    
    @FXML void visualizzaFormRiunioneFisica(ActionEvent event) {
    	FormRiunioneFisica.setVisible(true);
    	FormRiunioneTelematica.setVisible(false);
    	
    	SedeTF					.setText("");
    	SedeErrorLabel			.setText("");
    	
    	NomeStanzaTF			.setText("");
    	NomeStanzaErrorLabel	.setText("");
    	
    	PianoStanzaTF			.setText("");
    	PianoStanzaErrorLabel	.setText("");
    }

    @FXML void visualizzaFormRiunioneTelematica(ActionEvent event) {
    	FormRiunioneFisica.setVisible(false);
    	FormRiunioneTelematica.setVisible(true);
    	
    	NomePiattaformaTF			.setText("");
    	NomePiattaformaErrorLabel	.setText("");
    	
    	CodiceAccessoPF				.setText("");
    	CodiceAccessoErrorLabel		.setText("");
    }
    
    private boolean controlloCampiRiunioneFisica() {
    	SedeErrorLabel				.setText("");
    	NomeStanzaErrorLabel		.setText("");
    	PianoStanzaErrorLabel		.setText("");
    	
    	checkSede 			= true;
    	checkNomeStanza 	= true;
    	checkPianoStanza 	= true;
    	
    	switch(utils.controlloStringa(SedeTF.getText(), "")) {
    		case 1:
    			checkSede = false;
    			SedeErrorLabel.setText("Questo campo è obbligatorio");
    			break;
			default:
				checkSede = true;
    	}
    	
    	switch(utils.controlloStringa(NomeStanzaTF.getText(), "")) {
			case 1:
				checkNomeStanza = false;
				NomeStanzaErrorLabel.setText("Questo campo è obbligatorio");
				break;
			default:
				checkNomeStanza = true;
    	}
    	
    	switch(utils.controlloStringa(PianoStanzaTF.getText(), "[-]{0,1}[0-9]+")) {
	    	case 1:
	    		checkPianoStanza = false;
	    		PianoStanzaErrorLabel.setText("Questo campo è obbligatorio");
	    		break;
	    	case 2:
	    		checkPianoStanza = false;
	    		PianoStanzaErrorLabel.setText("Il piano può essere composto al massimo da due cifre.\n" +
	    							  		  "Per indicare un piano sotterraneo, usa il -");
	    		break;
	    	default:
	    		checkPianoStanza = true;
    	}
    	
    	return 	checkSede 		&&
    			checkNomeStanza &&
    			checkPianoStanza;
    }
    
    private boolean controlloCampiRiunioneTelematica() {
    	NomePiattaformaErrorLabel	.setText("");
    	CodiceAccessoErrorLabel		.setText("");
    	
    	checkNomePiattaforma = true;
    	checkCodiceAccesso = true;
    	
    	switch(utils.controlloStringa(NomePiattaformaTF.getText(), "")) {
	    	case 1:
	    		checkNomePiattaforma = false;
	    		NomePiattaformaErrorLabel.setText("Questo campo è obbligatorio");
	    		break;
	    	default:
	    		checkNomePiattaforma = true;
    	}
    	
    	switch(utils.controlloPassword(CodiceAccessoPF.getText(), 6)) {
    		case 1:
    			checkCodiceAccesso = false;
    			CodiceAccessoErrorLabel.setText("Questo campo è obbligatorio");
    			break;
    		case 2:
    			checkCodiceAccesso = false;
    			CodiceAccessoErrorLabel.setText("Il codice di accesso deve contenere almeno 6 caratteri");
    			break;
			default:
				checkCodiceAccesso = true;
    	}
    	
    	return 	checkNomePiattaforma &&
    			checkCodiceAccesso;
    }
    
    private boolean controlloCampi() {
    	TitoloErrorLabel			.setText("");
    	OrarioDiInizioErrorLabel	.setText("");
    	OrarioDiFineErrorLabel		.setText("");
    	
    	checkTitolo 				= true;
    	checkOrarioDiInizio 		= true;
    	checkOrarioDiFine 			= true;
    	checkFormRiunioneFisica 	= true;
    	checkFormRiunioneTelematica = true;
    	
    	if(FormRiunioneFisica.isVisible())
    		checkFormRiunioneFisica = controlloCampiRiunioneFisica();
    	else if(FormRiunioneTelematica.isVisible())
    		checkFormRiunioneTelematica = controlloCampiRiunioneTelematica();
    	
    	switch(utils.controlloStringa(TitoloTF.getText(), "[A-Za-z0-9\s-]+")) {
    		case 1:
    			checkTitolo = false;
    			TitoloErrorLabel.setText("Questo campo è obbligatorio");
    			break;
    		case 2:
    			checkTitolo = false;
    			TitoloErrorLabel.setText("Il titolo può contenere solo caratteri alfanumerici");
				break;
    		default:
    			checkTitolo = true;
    	}
    	
    	switch(utils.controlloStringa(OrarioDiInizioTF.getText(), "[0-2][0-9][:][0-5][0-9]")) {
			case 1:
				checkOrarioDiInizio = false;
				OrarioDiInizioErrorLabel.setText("Questo campo è obbligatorio");
				break;
			case 2:
				checkOrarioDiInizio = false;
				OrarioDiInizioErrorLabel.setText("La sintassi dell'orario di inizio inserito non è corretta");
				break;
			default:
				try {
					orarioDiInizio = LocalTime.parse(OrarioDiInizioTF.getText() + ":00");
					checkOrarioDiInizio = true;
				} catch (DateTimeParseException e) {
					checkOrarioDiInizio = false;
					OrarioDiInizioErrorLabel.setText("Il valore delle ore inserito nell'orario di inizio non è corretto");
				}
    	}
    	
    	switch(utils.controlloStringa(OrarioDiFineTF.getText(), "[0-2][0-9][:][0-5][0-9]")) {
			case 1:
				checkOrarioDiFine = false;
				OrarioDiFineErrorLabel.setText("Questo campo è obbligatorio");
				break;
			case 2:
				checkOrarioDiFine = false;
				OrarioDiFineErrorLabel.setText("La sintassi dell'orario di fine inserito non è corretta");
				break;
			default:
				try {
					orarioDiFine = LocalTime.parse(OrarioDiInizioTF.getText() + ":00");
					checkOrarioDiFine = true;
				} catch (DateTimeParseException e) {
					checkOrarioDiFine = false;
					OrarioDiFineErrorLabel.setText("Il valore delle ore inserito nell'orario di fine non è corretto");
				}
    	}
    	
    	return 	checkTitolo				&&
    			checkOrarioDiInizio 	&&
    			checkOrarioDiFine		&&
    			checkFormRiunioneFisica &&
    			checkFormRiunioneTelematica;
    	
    }

    @FXML private void confermaOperazione(ActionEvent event) {
    	if(controlloCampi()) {
            try {
            	RiunioneDaoInterface riunioneDao = new RiunioneDao(connection);
                //riunioneDao.creaRiunione(inizializzaNuovaRiunione()); >> da creare
                
				homePageProjectManager = new HomePageProjectManager(organizzatore, progetto);
				finestraConferma = new FinestraPopup();
                try {
                	homePageProjectManager.start(window, popup);
					finestraConferma .start(popup, "La riunione è stato registrata correttamente nel database.");
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
    
    @FXML void annullaOperazione(ActionEvent event) {
    	homePageProjectManager = new HomePageProjectManager(organizzatore, progetto);
    	
    	try {
			homePageProjectManager.start(window, popup);
		} catch (Exception erroreCaricamento) {
			erroreCaricamento.printStackTrace();
		}
    }
    
    @FXML void visualizzaTitoloLabel(MouseEvent event) {
    	
    }
    
    @FXML void visualizzaDescrizioneLabel(MouseEvent event) {
    	
    }

    @FXML void visualizzaOrarioDiFineLabel(MouseEvent event) {
    	
    }

    @FXML void visualizzaOrarioDiInizioLabel(MouseEvent event) {
    	
    }

}
