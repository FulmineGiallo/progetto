package controller;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Iterator;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Ambito;
import model.Connection.DBConnection;
import model.Dao.AmbitoDao;
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
    
    @FXML private HBox 				  ProjectManagerBox;
    @FXML private Label 			  ProjectManagerLabel;
    @FXML private TextField 		  ProjectManagerTF;
    
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
    
    @FXML private VBox 				  NuovaTipologiaBox;
    @FXML private TextField 		  NuovaTipologiaTF;
    @FXML private Label 			  NuovaTipologiaErrorLabel;
    
    @FXML private GridPane 			  AmbitiBox;
    @FXML private Label 			  AmbitiLabel;
    @FXML private ListView<Ambito> 	  AmbitiLV;
    
    @FXML private VBox 				  NuovoAmbitoBox;
    @FXML private TextField 		  NuovoAmbitoTF;
    @FXML private Label 			  NuovoAmbitoErrorLabel;
    
    @FXML private ComboBox<Ambito> 	  AmbitiComboBox;
    @FXML private Label 			  AmbitiErrorLabel;
    
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
    
    private boolean checkTitolo;
    private boolean checkDataInizio;
    private boolean checkDataScadenza;
    private boolean checkNuovaTipologia;
    private boolean checkAmbito;
    private boolean checkNuovoAmbito;
    
    private MetodiComuni utils = new MetodiComuni();
    
    private TipologiaDao			  tipologiaDao;
    private ObservableList<Tipologia> listaTipologie;
    private Tipologia				  tipologiaAltro = new Tipologia("Altro...", false);
    
    private AmbitoDao 				  ambito;
    private ObservableList<Ambito>	  listaAmbiti;
    private Ambito					  ambitoPrompt	 = new Ambito("-- Scegli qui gli ambiti --", false);
    private Ambito					  ambitoAltro	 = new Ambito("Altro...", false);
    private Ambito					  ambitoIniziale = new Ambito("Non ci sono ambiti di progetto", false);
    
    private Impiegato projectManager;
    
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
            
            AmbitiComboBox.getItems().add(ambitoPrompt);
            AmbitiComboBox.getItems().addAll(listaAmbiti);
            AmbitiComboBox.getItems().add(ambitoAltro);
            
            tipologiaDao = new TipologiaDao(connection);
            listaTipologie = tipologiaDao.getListaTipologie();
            
            TipologiaComboBox.setItems(listaTipologie);
            TipologiaComboBox.getItems().add(tipologiaAltro);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    	
        this.projectManager = projectManager;
        
        if (this.projectManager != null) {
			ProjectManagerTF.setText(this.projectManager.toString());
		}
        
		TipologiaComboBox.getSelectionModel().select(0);
        AmbitiComboBox.getSelectionModel().select(ambitoPrompt);
        
        setTipologiaComboBoxListener();        
        setAmbitiComboBoxListener();
        AmbitiLV.getItems().add(ambitoIniziale);
    }
    
    private void setTipologiaComboBoxListener() {
    	TipologiaComboBox.getSelectionModel().selectedItemProperty().addListener( (options, oldValue, newValue) -> {
    		NuovaTipologiaTF.setText("");
    		NuovaTipologiaErrorLabel.setText("");
    		
        	NuovaTipologiaBox.setVisible(TipologiaComboBox.getSelectionModel().getSelectedItem() == tipologiaAltro);
        });
    }
    
    private void setAmbitiComboBoxListener() {
        AmbitiComboBox.getSelectionModel().selectedItemProperty().addListener( (options, oldValue, newValue) -> {
        	Ambito ambitoSelezionato = AmbitiComboBox.getSelectionModel().getSelectedItem();
        	
        	if(ambitoSelezionato != ambitoPrompt) {
        		NuovoAmbitoTF.setText("");
        		NuovoAmbitoErrorLabel.setText("");
        		
        		AmbitiErrorLabel.setText("");
        		
            	if(ambitoSelezionato == ambitoAltro) {
            		NuovoAmbitoBox.setVisible(true);
                } else {
                	NuovoAmbitoBox.setVisible(false);
                	
                	if(AmbitiLV.getItems().contains(ambitoIniziale)){
                		AmbitiLV.getItems().clear();
                	}
                	
                	if(controlloAmbito(ambitoSelezionato)) {
                    	AmbitiLV.getItems().add(ambitoSelezionato);
                	}
                	
                	AmbitiComboBox.getSelectionModel().select(ambitoPrompt);
            	}
        	}

        });
    }
    
    private Progetto inizializzaNuovoProgetto() {
    	Progetto nuovoProgetto = new Progetto(projectManager,
    										  TitoloTF.getText(),
    										  DataDiInizioDP.getValue(),
    										  DataDiScadenzaDP.getValue(),
    										  TipologiaComboBox.getSelectionModel().getSelectedItem());
    	
    	if(!AmbitiLV.getItems().contains(ambitoIniziale)) {
    		nuovoProgetto.setListaAmbiti(AmbitiLV.getItems());
    	}
    	
    	switch(utils.controlloStringa(DescrizioneTA.getText(), "")) {
	    	case 1:
	    		nuovoProgetto.setDescrizione(null);
	    		break;
    		default:
    			nuovoProgetto.setDescrizione(DescrizioneTA.getText());
    	}
    	
    	return nuovoProgetto;
    }
    
    //CONTROLLO NUOVA TIPOLOGIA (SE OBBLIGATORIA)
    private boolean controlloNuovaTipologia() {
    	checkNuovaTipologia = true;
    	NuovaTipologiaErrorLabel.setText("");
    	
        if(NuovaTipologiaBox.isVisible()) {
        	Iterator<Tipologia> i = listaTipologie.iterator();
    		while(checkNuovaTipologia && i.hasNext()){
    			switch(utils.controlloStringa(NuovaTipologiaTF.getText().toLowerCase(),
    												i.next().toString().toLowerCase()))
    			{
    				case 1:
    					checkNuovaTipologia = false;
    					NuovaTipologiaErrorLabel.setText("Questo campo è obbligatorio");
    					break;
    				case 3:
    					checkNuovaTipologia = false;
    					NuovaTipologiaErrorLabel.setText("Questa tipologia è già presente nella lista");
						break;
					default:
						checkNuovaTipologia = true;
    			}
    		}
        }
    	
    	return checkNuovaTipologia;
    }
    
    //CONTROLLO AMBITO
    private boolean controlloAmbito(Ambito ambitoSelezionato) {
    	checkAmbito = true;
    	AmbitiErrorLabel.setText("");
    	
        Iterator<Ambito> i = AmbitiLV.getItems().iterator();
        while(checkAmbito && i.hasNext()) {
            switch(utils.controlloStringa(ambitoSelezionato.toString(), i.next().toString())) {
            	case 1:
            		checkAmbito = false;
            		AmbitiErrorLabel.setText("Questo campo è obbligatorio");
            		break;
            	case 3:
            		checkAmbito = false;
            		AmbitiErrorLabel.setText("Questo ambito è già presente nella lista di ambiti aggiunti");
            		break;
				default:
					checkAmbito = true;
            }
        }
    	
    	return checkAmbito;
    }
    
    //CONTROLLO NUOVO AMBITO (SE OBBLIGATORIO)
    private boolean controlloNuovoAmbito() {
    	checkNuovoAmbito = true;
    	NuovoAmbitoErrorLabel.setText("");
    	
        if(NuovoAmbitoBox.isVisible()) {
        	Iterator<Ambito> i = listaAmbiti.iterator();
    		while(checkNuovoAmbito && i.hasNext()){
    			switch(utils.controlloStringa(NuovoAmbitoTF.getText().toLowerCase(),
    										  i.next().toString().toLowerCase()))
    			{
    				case 1:
    					checkNuovoAmbito = false;
    					NuovoAmbitoErrorLabel.setText("Questo campo è obbligatorio");
    					break;
    				case 3:
    					checkNuovoAmbito = false;
    					NuovoAmbitoErrorLabel.setText("Questo ambito è già presente nella lista di ambiti forniti");
						break;
					default:
						checkNuovoAmbito = true;
    			}
    		}
			
			i = AmbitiLV.getItems().iterator();
    		while(checkNuovoAmbito && i.hasNext()) {
				switch (utils.controlloStringa(NuovoAmbitoTF.getText().toLowerCase(),
													 i.next().toString().toLowerCase()))
				{
					case 3:
						checkNuovoAmbito = false;
    					NuovoAmbitoErrorLabel.setText("Questo ambito è già presente nella lista di ambiti aggiunti");
    					break;
    				default:
    					checkNuovoAmbito = true;
				}
    		}
        }
        
    	return checkNuovoAmbito;
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
	    		DataDiScadenzaDP.setDisable(true);	
	        	break;
	    	case 3:
	    		checkDataInizio = false;
	    		DataDiInizioErrorLabel.setText("La data di inizio non può essere precedente a quella di oggi");
	    		DataDiScadenzaDP.setDisable(true);
	    		break;
			default:
				checkDataInizio = true;
				DataDiScadenzaDP.setDisable(false);	
        }
        
        return checkDataInizio;
    }
    
    //CONTROLLO DATA DI SCADENZA
    @FXML private boolean controlloDataDiScadenza() {
        LocalDate dataSupportata = null;
        checkDataScadenza = true;
        DataDiScadenzaErrorLabel.setText("");
        
        if(checkDataInizio) {
            dataSupportata = DataDiInizioDP.getValue();
            
            switch(utils.controlloData(DataDiScadenzaDP.getValue(), dataSupportata)) {
		    	case 1:
		    		checkDataScadenza = false;
		    		DataDiScadenzaErrorLabel.setText("Questo campo è obbligatorio");
		        	break;
		    	case 3:
		    		checkDataScadenza = false;
		    		DataDiScadenzaErrorLabel.setText("La data di scadenza non può essere precedente a quella di inizio");
		    		break;
				default:
					checkDataScadenza = true;
            }
        } else {
        	checkDataScadenza = false;
        	DataDiScadenzaErrorLabel.setText("Prima di inserire una data di scadenza, inserisci una data di inizio corretta");
        }
        
        return checkDataScadenza;
    }
    
    public boolean controlloCampi() {
    	TitoloErrorLabel		.setText("");
    	DataDiInizioErrorLabel	.setText("");
    	DataDiScadenzaErrorLabel.setText("");
    	NuovaTipologiaErrorLabel.setText("");
    	AmbitiErrorLabel		.setText("");
    	NuovoAmbitoErrorLabel	.setText("");
    	
    	checkTitolo			= true;
    	checkDataInizio		= controlloDataDiInizio();
    	checkDataScadenza	= controlloDataDiScadenza();
        checkNuovaTipologia = controlloNuovaTipologia();
        checkNuovoAmbito	= controlloNuovoAmbito();
        
        //CONTROLLO TITOLO
        switch(utils.controlloStringa(TitoloTF.getText(), "[a-zA-Z0-9\s]+")) {
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
        
        return checkTitolo		   &&
        	   checkDataInizio	   &&
        	   checkDataScadenza   &&
        	   checkNuovaTipologia &&
        	   checkNuovoAmbito;

    }
    
    @FXML private void annullaOperazione(ActionEvent event) throws Exception{
    	//aggiungere finestra di domanda conferma
    	homePageImpiegato = new HomePageImpiegato(projectManager);
    	homePageImpiegato.start(window, popup);
    }

    @FXML private void confermaOperazione(ActionEvent event) {
    	if(controlloCampi()) {
            try {
            	ProgettoDaoInterface progettoDao = new ProgettoDao(connection);
                progettoDao.creaProgetto(inizializzaNuovoProgetto());
                
				homePageImpiegato = new HomePageImpiegato(projectManager);
				finestraConferma = new FinestraPopup();
                try {
					homePageImpiegato.start(window, popup);
					finestraConferma .start(popup, "Il progetto è stato registrato correttamente nel database.");
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
    
    @FXML private void inserisciNuovaTipologia(KeyEvent invio) {    	
    	if (invio.getCode().equals(KeyCode.ENTER)) {
        	checkNuovaTipologia = controlloNuovaTipologia();
            if(checkNuovaTipologia) {
            	NuovaTipologiaBox.setVisible(false);
            	
            	Tipologia nuovaTipologia = new Tipologia(NuovaTipologiaTF.getText(), true);
            	
            	TipologiaComboBox.getItems().remove(tipologiaAltro);
            	TipologiaComboBox.getItems().add(nuovaTipologia);
            	TipologiaComboBox.getItems().add(tipologiaAltro);
            	
            	TipologiaComboBox.getSelectionModel().select(nuovaTipologia);
            	
            	NuovaTipologiaTF.setText("");
            	NuovaTipologiaErrorLabel.setText("");
            }
        }
    }

    @FXML private void inserisciNuovoAmbito(KeyEvent invio) {    	
    	if (invio.getCode().equals(KeyCode.ENTER)) {
        	checkNuovoAmbito = controlloNuovoAmbito();
            if(checkNuovoAmbito) {
            	NuovoAmbitoBox.setVisible(false);
            	
            	if(AmbitiLV.getItems().contains(ambitoIniziale)){
            		AmbitiLV.getItems().clear();
            	}
            	
            	AmbitiLV.getItems().add(new Ambito(NuovoAmbitoTF.getText(), true));
            	AmbitiComboBox.getSelectionModel().select(ambitoPrompt);
            	
            	NuovoAmbitoTF		 .setText("");
            	NuovoAmbitoErrorLabel.setText("");
            }
        }
    }
    
    @FXML private void rimuoviAmbito(MouseEvent event) {
    	Ambito ambitoSelezionato = AmbitiLV.getSelectionModel().getSelectedItem();
    	
    	if(!AmbitiLV.getItems().contains(ambitoIniziale)){
			AmbitiLV.getItems().remove(ambitoSelezionato);
    	}
    	
    	if(AmbitiLV.getItems().isEmpty()) {
    		AmbitiLV.getItems().add(ambitoIniziale);
    	}
    	
    	AmbitiComboBox.getSelectionModel().select(ambitoPrompt);
    	NuovoAmbitoBox.setVisible(false);
    	NuovoAmbitoTF.setText("");
    	NuovoAmbitoErrorLabel.setText("");
    }

    @FXML
    void visualizzaTitoloLabel(MouseEvent event) {

    }
    
    @FXML
    void visualizzaDescrizioneLabel(MouseEvent event) {

    }
}