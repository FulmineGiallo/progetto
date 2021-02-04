package controller;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Calendar;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
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
import model.Dao.RiunioneDao;
import model.DaoInterface.RiunioneDaoInterface;
import utilities.MetodiComuni;
import view.FinestraPopup;
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
    
    @FXML private HBox 				DataDiInizioBox;
    @FXML private Label 			OrarioDiInizioLabel;
    @FXML private DatePicker 		DataDiInizioDP;
    @FXML private Label 			DataDiInizioErrorLabel;
    @FXML private VBox 				OrarioDiInizioBox;
    @FXML private Slider 			OrarioDiInizioOreSlider;
    @FXML private Slider 			OrarioDiInizioMinutiSlider;
    
    @FXML private HBox 				DataDiFineBox;
    @FXML private Label 			OrarioDiFineLabel;
    @FXML private DatePicker 		DataDiFineDP;
    @FXML private Label 			DataDiFineErrorLabel;
    @FXML private VBox 				OrarioDiFineBox;
    @FXML private Slider 			OrarioDiFineOreSlider;
    @FXML private Slider 			OrarioDiFineMinutiSlider;
    
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
    
    private Calendar Oggi = Calendar.getInstance();
    private int OggiGiorno = Oggi.get(Calendar.DAY_OF_MONTH);
    private int OggiMese = Oggi.get(Calendar.MONTH) + 1;
    private int OggiAnno = Oggi.get(Calendar.YEAR);
    
    private boolean checkTitolo;
    private boolean checkDataInizio;
    private boolean checkDataFine;
    private boolean checkOrarioDiFine;
    private boolean checkFormRiunioneFisica;
    private boolean checkSede;
    private boolean checkNomeStanza;
    private boolean checkPianoStanza;
    private boolean checkFormRiunioneTelematica;
    private boolean checkNomePiattaforma;
    private boolean checkCodiceAccesso;
    
    private LocalDateTime orarioDiInizio;
    private LocalDateTime orarioDiFine;
    
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
    	
    	setOrarioDiInizioLabel();    	
    	setOrarioDiFineLabel();
    }
    
    private Riunione inizializzaNuovaRiunione() { // >> da creare
    	Riunione nuovaRiunione = new Riunione(null);
    	
    	return nuovaRiunione;
    }
    
    @FXML void setOrarioDiInizioLabel() {
    	OrarioDiInizioLabel.setText(utils.getOrario((int)OrarioDiInizioOreSlider	.getValue(),
													(int)OrarioDiInizioMinutiSlider	.getValue()));
    }
    
    @FXML void setOrarioDiFineLabel() {
    	OrarioDiFineLabel.setText(utils.getOrario  ((int)OrarioDiFineOreSlider		.getValue(),
													(int)OrarioDiFineMinutiSlider	.getValue()));
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
    
    //CONTROLLO DATA DI INIZIO
    @FXML private boolean controlloDataDiInizio() {
        LocalDate dataDiOggi = LocalDate.of(OggiAnno, OggiMese, OggiGiorno);
        checkDataInizio = true;
        DataDiInizioErrorLabel.setText("");
        
        switch(utils.controlloData(DataDiInizioDP.getValue(), dataDiOggi)) {
	    	case 1:
	    		checkDataInizio = false;
	    		DataDiInizioErrorLabel.setText("Questo campo è obbligatorio");
	    		DataDiFineDP.setDisable(true);
	        	break;
	    	case 3:
	    		checkDataInizio = false;
	    		DataDiInizioErrorLabel.setText("La data di inizio non può essere precedente a quella di oggi");
	    		DataDiFineDP.setValue(null);
	    		DataDiFineDP.setDisable(true);
	    		break;
			default:
				checkDataInizio = true;
				DataDiFineDP.setDisable(false);
				if (DataDiFineDP.getValue() == null)
					DataDiFineDP.setValue(DataDiInizioDP.getValue());
        }
        
        return checkDataInizio;
    }
    
    //CONTROLLO DATA DI FINE
    @FXML private boolean controlloDataDiFine() {
        LocalDate dataSupportata = null;
        checkDataFine = true;
        DataDiFineErrorLabel.setText("");
        
        if(checkDataInizio) {
            dataSupportata = DataDiInizioDP.getValue().plusDays(1);
            
            switch(utils.controlloData(DataDiFineDP.getValue(), dataSupportata)) {
		    	case 1:
		    		checkDataFine = false;
		    		DataDiFineErrorLabel.setText("Questo campo è obbligatorio");
		        	break;
		    	case 2:
		    		checkDataFine = false;
		    		DataDiFineErrorLabel.setText("La riunione non può durare più di un giorno");
		    		break;
				default:
					if(DataDiFineDP.getValue().isBefore(DataDiInizioDP.getValue())){
			    		checkDataFine = false;
			    		DataDiFineErrorLabel.setText("La data di fine non può essere\nprecedente a quella di inizio");
					} else
						checkDataFine = true;
            }
        } else {
        	checkDataFine = false;
        	DataDiFineErrorLabel.setText("Prima di inserire una data di fine,\ninserisci una data di inizio corretta");
        }
        
        return checkDataFine;
    }
    
    //CONTROLLO ORARIO DI FINE
    private boolean controlloOrarioDiFine() {
    	checkOrarioDiFine = true;
    	checkDataFine = controlloDataDiFine();
    	
    	if(checkDataFine) {
    		if(DataDiInizioDP.getValue().isEqual(DataDiFineDP.getValue())) {
    			switch(utils.controlloOrario((int)OrarioDiInizioOreSlider	.getValue(),
    										 (int)OrarioDiInizioMinutiSlider.getValue(),
    										 (int)OrarioDiFineOreSlider		.getValue(),
    										 (int)OrarioDiFineMinutiSlider	.getValue()))
    			{
    				case 1:
    					checkOrarioDiFine = false;
    					DataDiFineErrorLabel.setText("Il valore delle ore inserito non è corretto");
    					break;
    				case 2:
    					checkOrarioDiFine = false;
    					DataDiFineErrorLabel.setText("Il valore dei minuti inserito non è corretto");
    					break;
    				case 3:
    					checkOrarioDiFine = false;
    					DataDiFineErrorLabel.setText("L'orario di inizio e di fine della riunione\nnon possono coincidere");
    					break;
					default:
						checkOrarioDiFine = true;
    			}
    		} else {
    			switch(utils.controlloOrario((int)OrarioDiInizioOreSlider	.getValue(),
						 					 (int)OrarioDiInizioMinutiSlider.getValue(),
						 					 (int)OrarioDiFineOreSlider		.getValue(),
						 					 (int)OrarioDiFineMinutiSlider	.getValue()))
    			{
					case 1:
						checkOrarioDiFine = true;
						break;
					case 2:
						checkOrarioDiFine = true;
						break;
					case 3:
						checkOrarioDiFine = true;
						break;
					default:
						checkOrarioDiFine = false;
						DataDiFineErrorLabel.setText("La riunione non può durare più di un giorno");
    			}
    		}
    	} else {
    		checkOrarioDiFine = false;
    	}
    	
    	return checkOrarioDiFine;
    	
    }
    
    //CONTROLLO CAMPI DI FormRiunioneFisicaBox
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
    
    //CONTROLLO CAMPI DI FormRiunioneTelematicaBox
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
    
    //CONTROLLO TUTTI I CAMPI PRIMA DELLA CONFERMA
    private boolean controlloCampi() {
    	TitoloErrorLabel			.setText("");
    	
    	checkTitolo 				= true;
    	checkDataInizio				= controlloDataDiInizio();
    	checkOrarioDiFine 			= controlloOrarioDiFine();
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
    	
    	return 	checkTitolo				&&
    			checkDataInizio			&&
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
