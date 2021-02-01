package controller;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.Ambito;
import model.Comune;
import model.Connection.DBConnection;
import model.Dao.AmbitoDao;
import model.Dao.ComuneDao;
import model.Dao.ProgettoDao;
import model.Dao.TipologiaDao;
import model.DaoInterface.ProgettoDaoInterface;
import utilities.MetodiComuni;
import view.FinestraPopup;
import view.HomePageImpiegato;
import model.Impiegato;
import model.Progetto;
import model.Tipologia;

public class ControllerRegistrazioneProgetto {

    @FXML private AnchorPane 		  RegistrazioneProgetto;
    
    @FXML private HBox 				  IstruzioniBox;
    @FXML private Label 			  IstruzioniLabel;
    @FXML private Label 			  IstruzioniLabel2;
    
    @FXML private HBox 				  FormBox;
    @FXML private ScrollPane 		  FormScrollPane;
    @FXML private VBox 				  Form;
    
    @FXML private HBox				  ProjectManagerBox;
    @FXML private Label				  ProjectManagerLabel;
    @FXML private TextField			  ProjectManagerTF;
    
    @FXML private VBox 				  TitoloBox;
    @FXML private Label 			  TitoloLabel;
    @FXML private TextField 		  TitoloTF;
    @FXML private Label 			  TitoloErrorLabel;
    
    @FXML private VBox 				  DescrizioneBox;
    @FXML private Label 			  DescrizioneLabel;
    @FXML private TextArea 			  DescrizioneTA;
    
    @FXML private VBox 				  DataDiInizioBox;
    @FXML private Label 			  DataDiInizioLabel;
    @FXML private DatePicker 		  DataDiInizioDP;
    @FXML private Label 			  DataDiInizioErrorLabel;
    
    @FXML private VBox 				  DataDiScadenzaBox;
    @FXML private Label 			  DataDiScadenzaLabel;
    @FXML private DatePicker 		  DataDiScadenzaDP;
    @FXML private Label 			  DataDiScadenzaErrorLabel;
    
    @FXML private GridPane 			  TipologiaBox;
    @FXML private Label 			  TipologiaLabel;
    @FXML private ComboBox<Tipologia> TipologiaComboBox;
    
    @FXML private VBox				  NuovaTipologiaBox;
    @FXML private TextField			  NuovaTipologiaTF;
    @FXML private Label				  NuovaTipologiaErrorLabel;
    
    @FXML private GridPane			  AmbitiBox;
    @FXML private Label				  AmbitiLabel;
    @FXML private ListView<Ambito> 	  AmbitiLV;
    @FXML private ComboBox<Ambito> 	  AmbitiComboBox;
    @FXML private Label				  AmbitiErrorLabel;
    
    @FXML private VBox				  NuovoAmbitoBox;
    @FXML private TextField			  NuovoAmbitoTF;
    @FXML private Label				  NuovoAmbitoErrorLabel;
    
    @FXML private AnchorPane 		  ButtonBar;
    @FXML private Button 			  AnnullaButton;
    @FXML private Button 			  ConfermaButton;
    
    private Stage window;
    private Stage popup;
    
    private Calendar Oggi = Calendar.getInstance();
    private int OggiGiorno = Oggi.get(Calendar.DAY_OF_MONTH);
    private int OggiMese = Oggi.get(Calendar.MONTH) + 1;
    private int OggiAnno = Oggi.get(Calendar.YEAR);
    
    private HomePageImpiegato homePageImpiegato;
    private FinestraPopup	  finestraConferma;
    private FinestraPopup	  finestraErrore;
    
    private String titolo;
    private String descrizione;
    private LocalDate dataInizio;
    private LocalDate dataScadenza;
    
    private boolean checkTitolo;
    private boolean checkDataInizio;
    private boolean checkDataScadenza;
    private boolean checkNuovaTipologia;
    private boolean checkAmbito;
    private boolean checkNuovoAmbito;
    
    private MetodiComuni utils = new MetodiComuni();
    
    private TipologiaDao			  tipologiaDao;
    private ObservableList<Tipologia> listaTipologie;
    
    private AmbitoDao 				  ambito;
    private ObservableList<Ambito>	  listaAmbiti;
    private Ambito					  ambitoPrompt;
    private Ambito					  ambitoIniziale = new Ambito("Non ci sono ambiti di progetto", false);
    
    private Impiegato projectManager;
    private Progetto  nuovoProgetto;
    
    private Connection connection;
    private DBConnection dbConnection;
    
    public void setStage(Stage window, Stage popup) {
    	this.window = window;
    	this.popup = popup;
    }
    
    public void inizializza(Impiegato projectManager) {
    	
    	try {
            dbConnection = new DBConnection();
            connection = dbConnection.getConnection();
            
            ambito = new AmbitoDao(connection);
            listaAmbiti = ambito.AmbitoList();
            AmbitiComboBox.setItems(listaAmbiti);
            
            tipologiaDao = new TipologiaDao(connection);
            listaTipologie = tipologiaDao.getListaTipologie();
            TipologiaComboBox.setItems(listaTipologie);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    	
        this.projectManager = projectManager;
        
        if (projectManager != null) {
			ProjectManagerTF.setText(projectManager.toString());
		}
        
		TipologiaComboBox.getSelectionModel().select(0);
        AmbitiComboBox.getSelectionModel().select(0);
        ambitoPrompt = AmbitiComboBox.getSelectionModel().getSelectedItem();
        
        setTipologiaComboBoxListener();        
        setAmbitiComboBoxListener();
        AmbitiLV.getItems().add(ambitoIniziale);
        
    	/*int i = 0;

    	while(i<ambitoList.size()) {
    		CheckMenuItem a = new CheckMenuItem(ambitoList.get(i).toString());
    		AmbitiMenuButton.getItems().add(a);
    		i++;
    	}*/
    }
    
    private void setTipologiaComboBoxListener() {
    	TipologiaComboBox.getSelectionModel().selectedItemProperty().addListener( (options, oldValue, newValue) -> {
        	if(TipologiaComboBox.getSelectionModel().getSelectedItem().toString().equals("Altro...")) {
        		NuovaTipologiaBox.setVisible(true);
        		NuovaTipologiaTF.setText("");
        		NuovaTipologiaErrorLabel.setText("");
            } else {
            	NuovaTipologiaBox.setVisible(false);
        	}
        });
    }
    
    private void setAmbitiComboBoxListener() {
        AmbitiComboBox.getSelectionModel().selectedItemProperty().addListener( (options, oldValue, newValue) -> {
        	Ambito ambitoSelezionato = AmbitiComboBox.getSelectionModel().getSelectedItem();
        	
        	if(ambitoSelezionato != ambitoPrompt) {
            	if(ambitoSelezionato.toString().equals("Altro...")) {
            		NuovoAmbitoBox.setVisible(true);	
            		NuovoAmbitoTF.setText("");
            		NuovoAmbitoErrorLabel.setText("");
                } else { // >> gestione del passaggio da combobox a listview
                	NuovoAmbitoBox.setVisible(false);
                	
                	if(AmbitiLV.getItems().contains(ambitoIniziale)){
                		AmbitiLV.getItems().clear();
                	}
                	
                	AmbitiLV.getItems().add(ambitoSelezionato);
                	AmbitiComboBox.getSelectionModel().select(ambitoPrompt);
            	}
        	}

        });
    }
    
    public boolean controlloCampi() {
    	TitoloErrorLabel		.setText("");
    	DataDiInizioErrorLabel	.setText("");
    	DataDiScadenzaErrorLabel.setText("");
    	NuovaTipologiaErrorLabel.setText("");
    	AmbitiErrorLabel		.setText("");
    	NuovoAmbitoErrorLabel	.setText("");
    	
    	checkTitolo			= true;
        checkDataInizio		= true;
        checkDataScadenza	= true;
        checkNuovaTipologia = true;
        checkAmbito			= true;
        checkNuovoAmbito	= true;
        
        //CONTROLLO TITOLO
        switch(utils.controlloStringaPattern(TitoloTF.getText(), "a-zA-Z0-9\s")) {
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
        
        //CONTROLLO DATA DI INIZIO
        LocalDate dataDiOggi = LocalDate.of(OggiAnno, OggiMese, OggiGiorno);
        
        switch(utils.controlloData(DataDiInizioDP.getValue(), dataDiOggi, null)) {
	    	case 1:
	    		checkDataInizio = false;
	    		DataDiInizioErrorLabel.setText("Questo campo è obbligatorio");
	        	break;
	    	case 3:
	    		checkDataInizio = false;
	    		DataDiInizioErrorLabel.setText("Inserisci una data di inizio corretta");
	    		break;
			default:
				checkDataInizio = true;
        }
        
        //CONTROLLO DATA DI SCADENZA
        LocalDate dataSupportata = null;
        
        if(checkDataInizio) {
            dataSupportata = DataDiInizioDP.getValue();
            
            switch(utils.controlloData(DataDiScadenzaDP.getValue(), dataSupportata, null)) {
		    	case 1:
		    		checkDataScadenza = false;
		    		DataDiScadenzaErrorLabel.setText("Questo campo è obbligatorio");
		        	break;
		    	case 3:
		    		checkDataScadenza = false;
		    		DataDiScadenzaErrorLabel.setText("Inserisci una data di scadenza corretta");
		    		break;
				default:
					checkDataScadenza = true;
            }
        } else {
        	checkDataScadenza = false;
        	DataDiScadenzaErrorLabel.setText("Prima di inserire una data di scadenza, inserisci una data di inizio corretta");
        }
        
        //CONTROLLO NUOVA TIPOLOGIA (SE OBBLIGATORIA)
        
        //CONTROLLO AMBITO
        
        //CONTROLLO NUOVO AMBITO (SE OBBLIGATORIO)
        
        return checkTitolo		   &&
        	   checkDataInizio	   &&
        	   checkDataScadenza   &&
        	   checkNuovaTipologia &&
        	   checkAmbito		   &&
        	   checkNuovoAmbito;

    }
    
    /*public boolean controlloCampi(){ //Questo elemento è già presente nella lista di ambiti
    	
    	boolean checkTitolo = true;
    	boolean checkDescrizione = true;
    	boolean checkDataInizio = true;
    	boolean checkDataScadenza = true;
    	boolean checkDataFine = true;
    	
    	TitoloErrorLabel.setText("");
    	DataDiInizioErrorLabel.setText("");
    	DataDiScadenzaErrorLabel.setText("");

    	
    	
    	if(DataDiInizioDP.getValue() != null)
    		dataInizio = java.sql.Date.valueOf(DataDiInizioDP.getValue());
    	
    	if(DataDiScadenzaDP.getValue() != null)
        	dataScadenza = java.sql.Date.valueOf(DataDiScadenzaDP.getValue()); 

    	
    	if (!(TitoloTF.getText().matches("[a-zA-Z0-9]+")) || ( TitoloTF.getText().isBlank()) ) {
    		checkTitolo = false;
    		if(TitoloTF.getText().isBlank())	
    			TitoloErrorLabel.setText("Il titolo non puo essere vuoto");
    		else
    			TitoloErrorLabel.setText("Il titolo puo contenere solo lettere e numeri");
    	}
    	
    	if(dataInizio == null || dataInizio.before(dataSupportata)) {
    		checkDataInizio = false;
    		DataDiInizioErrorLabel.setTextFill(Color.RED);
    		
    		if(dataInizio == null)
    			DataDiInizioErrorLabel.setText("inserire la data di inizio del progetto");
    		else
    			DataDiInizioErrorLabel.setText("il progetto non puo iniziare prima della data di oggi");
    	}
    	
    	if(dataScadenza == null) {
    		checkDataScadenza = false;
    		DataDiScadenzaErrorLabel.setTextFill(Color.RED);
    		DataDiScadenzaErrorLabel.setText("inserire la data di scadenza del progetto");
    	}else if(dataInizio != null) {
    				if(dataScadenza.before(dataInizio)) {
    					checkDataScadenza = false;
    					DataDiScadenzaErrorLabel.setTextFill(Color.RED);
    					DataDiScadenzaErrorLabel.setText("il progetto non puo scadere prima del suo inizio");
    				}
    			}
    		else {
    				checkDataScadenza = false;
    				DataDiScadenzaErrorLabel.setTextFill(Color.RED);
    				DataDiScadenzaErrorLabel.setText("Inserire prima la data di inizio");
    			}
    		
    	return checkTitolo && checkDataFine && checkDataInizio && checkDataScadenza && checkDescrizione;
     	
    	return true;
    }*/
    
    @FXML private void annullaOperazione(ActionEvent event) throws Exception{
    	homePageImpiegato = new HomePageImpiegato(projectManager);
    	homePageImpiegato.start(window, popup);
    }

    @FXML void confermaOperazione(ActionEvent event) {/*
        int risultato;
    	if(controlloCampi()) {
            try {
                ProgettoDaoInterface progetto = new ProgettoDao(connection);
                Progetto registrazione = new Progetto(TitoloTF.getText());
                registrazione.setProjectManager(projectManager);
                registrazione.setDescrizione(DescrizioneTA.getText());
                registrazione.setDataInizio(java.sql.Date.valueOf(DataDiInizioDP.getValue()));
                registrazione.setScadenza(java.sql.Date.valueOf(DataDiScadenzaDP.getValue()));
                risultato = progetto.creaProgetto(registrazione);
                if(risultato == 1) {
                    System.out.println("Dati inseriti");
                }
                else
                    System.out.println("Errore nella query");

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
	*/
    }
    
    @FXML
    void inserisciNuovaTipologia(KeyEvent invio) {
    	/* se preme 'invio' • il textfield non è più modificabile
    	 * 					• quando clicca 'altro...' nella combobox, tf torna modificabile e si elimina il testo
    	 * */
    }

    @FXML
    void inserisciNuovoAmbito(KeyEvent invio) {
    	/* if (invio.getCode().equals(KeyCode.ENTER)) {
                
           }
         
         * se preme 'invio' • il textfield non è più modificabile
    	 * 					• se clicca nella listview e l'ambito da eliminare è nuovo, 
    	 * 					  allora elimina solo dalla lista, senza rimetterlo nella combobox
    	 * 					• se l'ambito da eliminare proviene dal DB,
    	 * 					  allora elimina l'ambito dalla lista e rimettilo nella combobox
    	 * */
    }
    
    @FXML
    void rimuoviAmbito(MouseEvent event) {
    	Ambito ambitoSelezionato = AmbitiLV.getSelectionModel().getSelectedItem();
    	
    	if(!AmbitiLV.getItems().contains(ambitoIniziale)){
			AmbitiLV.getItems().remove(ambitoSelezionato);
			
    	}
    	
    	if(AmbitiLV.getItems().isEmpty()) {
    		AmbitiLV.getItems().add(ambitoIniziale);
    	}
    	
    	AmbitiComboBox.getSelectionModel().select(ambitoPrompt);
    }

    @FXML
    void visualizzaTitoloLabel(MouseEvent event) {

    }
    
    @FXML
    void visualizzaDescrizioneLabel(MouseEvent event) {

    }
}