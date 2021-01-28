package controller;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.ListIterator;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.layout.FlowPane;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import model.Comune;
import model.Connection.DBConnection;
import model.Dao.ComuneDao;
import model.Dao.GradoDao;
import model.DaoInterface.GradoDaoInterface;
import model.Grado;
import model.Impiegato;
import model.Skill;
import model.calcoloCF;

import view.HomePageBenvenuto;
import view.CaricamentoRegistrazioneImpiegato;
import view.FinestraErrore;
import view.FormRegistrazioneSkill;

public class ControllerRegistrazioneImpiegato {

    @FXML private Label 			IstruzioniLabel;
    @FXML private Label 			IstruzioniLabel2;
    
    @FXML private Label 			EmailLabel;
    @FXML private TextField 		EmailTF;
    @FXML private Label 			EmailErrorLabel;
    
    @FXML private Label 			PasswordLabel;
    @FXML private PasswordField 	PasswordTF;
    @FXML private Label 			PasswordErrorLabel;
    
    @FXML private Label 			NomeLabel;
	@FXML private TextField 		NomeTF;
    @FXML private Label 			NomeErrorLabel;
    
    @FXML private Label				CognomeLabel;
    @FXML private TextField 		CognomeTF;
    @FXML private Label 			CognomeErrorLabel;
    
    @FXML private Label 			GenereLabel;
    @FXML private ToggleGroup 		GenereGroup;
    @FXML private RadioButton 		GenereRB1;
    @FXML private RadioButton 		GenereRB2;

    @FXML private Label 			DataDiNascitaLabel;
    @FXML private DatePicker 		DataDiNascitaDP;
    @FXML private Label 			DataDiNascitaErrorLabel;
    
    @FXML private Label 			ComuneLabel;
    @FXML private ComboBox<Comune> 	ComuneComboBox;
    @FXML private Label				ComuneErrorLabel;
    
    @FXML private Label 			ProvinciaLabel;
    @FXML private TextField 		ProvinciaTF;
    @FXML private Label				ProvinciaErrorLabel;
    @FXML private Button        	CercaComuniButton;
    
    @FXML private Label 			CodiceFiscaleLabel;
    @FXML private TextField 		CodiceFiscaleTF;
    @FXML private Label 			CodiceFiscaleErrorLabel;
    
    @FXML private Label 			SkillLabel;
    @FXML private ListView<Skill>	SkillLV;
	@FXML private Button			NuovaSkillButton;
    
    @FXML private Label				GradoLabel;
    @FXML private ComboBox<Grado> 	GradoComboBox;
    
    @FXML private Button 			AnnullaButton;
    @FXML private Button 			ConfermaButton;
    
    @FXML private Label				CaricamentoLabel;
    
    private Stage window;
    private Stage popup;

    private Calendar Oggi = Calendar.getInstance();
    private int OggiGiorno = Oggi.get(Calendar.DAY_OF_MONTH);
    private int OggiMese = Oggi.get(Calendar.MONTH) + 1;
    private int OggiAnno = Oggi.get(Calendar.YEAR);
    
    private LocalDate dataDiNascita = null;
    private LocalDate dataSupportata = null;
    private LocalDate dataDiOggi = null;
    
    /*private String email;
    private String password;*/
    private String nome;
    private String cognome;
    private String comune;
    private String genere;
    private String codiceFiscale;
    private calcoloCF cf = new calcoloCF();
    
    private HomePageBenvenuto 				  homePageBenvenuto;
    private CaricamentoRegistrazioneImpiegato caricamentoRegistrazioneImpiegato;
    private FormRegistrazioneSkill 			  formRegistrazioneSkill;
    private FinestraErrore		   			  informazioniSkill;
    
    private Skill		ultimaSkillInserita;
    private Skill 		infoSkill;
    
    private ObservableList<Skill> listaSkillImpiegato = FXCollections.observableArrayList();
    
    private boolean checkEmail;
    private boolean checkPassword;
    private boolean checkNome;
    private boolean checkCognome;
    private boolean checkData;
    private boolean checkProvincia;
    private boolean checkComune;
    private boolean checkAnagrafica;
    private boolean checkCF;

    private Connection connection;
    private DBConnection dbConnection;
    
    {
        try
        {
            dbConnection = new DBConnection();
            connection = dbConnection.getConnection();
        } catch (SQLException throwables)
        {
            throwables.printStackTrace();
        }
    }

    private ObservableList<Grado> gradiList = FXCollections.observableArrayList();
    private ObservableList<Comune> comuneList = FXCollections.observableArrayList();
    private GradoDaoInterface gradi = null;
    
    {
        try
        {
            gradi = new GradoDao(connection);
            gradiList = gradi.gradoList();
        } catch (SQLException throwables)
        {
            throwables.printStackTrace();
        }
    }
        
    private ComuneDao comuni = null;
    
    {
        try
        {
            comuni = new ComuneDao(connection);
        } catch (SQLException throwables)
        {
            throwables.printStackTrace();
        }
    }
    
    public void setSkillImpiegato(Impiegato impiegato) {
		listaSkillImpiegato.addAll(impiegato.getListaSkill());
		SkillLV.setItems(listaSkillImpiegato);
    }
    
    public void setStage(Stage window, Stage popup) {
    	this.window = window;
    	this.popup = popup;
    }

    public void inizializza() throws SQLException {
        GradoComboBox.getItems().addAll(gradiList);
        GradoComboBox.getSelectionModel().select(2);
        
        ProvinciaTF.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                if(ProvinciaTF.getText().length() > 2)
                {
                    s = ProvinciaTF.getText().substring(0, 2);
                    ProvinciaTF.setText(s);
                }
            }
        });
    }
    
    public void updateComune() throws SQLException {
    	
    	if(controlloProvincia()) {
    		comuneList.clear();
    		comuneList = comuni.gradoList(ProvinciaTF.getText().toUpperCase());
    		
            if(comuneList.isEmpty()) {
            	checkComune = false;
            	ComuneComboBox.setPromptText("Nessun risultato trovato!");
    			ComuneErrorLabel.setText("Inserisci una provincia italiana esistente");
            } else {
            	ComuneComboBox.setPromptText(String.valueOf(comuneList.size()) + " comuni trovati");
                ComuneComboBox.setItems(comuneList);
            }
    	}
    }
    
    @FXML private void cercaComuni(ActionEvent event) throws SQLException{
    	
    	checkProvincia = true;
    	checkComune = true;
    	
    	ComuneComboBox.setPromptText("");
    	ComuneErrorLabel.setText("");
    	ProvinciaErrorLabel.setText("");
    	
    	CodiceFiscaleTF.setText("");
    	
    	updateComune();
    }

    @FXML private void CFRegistrazione() {
    	CodiceFiscaleErrorLabel.setText("");
    	
    	if(controlloCampiAnagrafica()) {
    		
 			nome 		  = NomeTF.getText();
    		cognome 	  = CognomeTF.getText();
    		comune 		  = ComuneComboBox.getValue().getCodiceComune();
    		genere 		  = ((RadioButton)GenereGroup.getSelectedToggle()).getText();
    		dataDiNascita = DataDiNascitaDP.getValue();
    		
    		codiceFiscale = cf.toString(cognome, nome, genere, dataDiNascita.getDayOfMonth(),
    										 dataDiNascita.getMonthValue(), dataDiNascita.getYear(), comune);
    		
    		CodiceFiscaleTF.setText(codiceFiscale);
    	} else {
    		
    		CodiceFiscaleErrorLabel.setText("Inserisci tutti i campi prima di calcolare il codice fiscale");
    	}
    }
    
    @FXML private void nuovaSkill(ActionEvent event) throws Exception{
    	formRegistrazioneSkill = new FormRegistrazioneSkill(this);
    	formRegistrazioneSkill.start(popup);
    }

    @FXML private void annullaOperazione (ActionEvent actionEvent) throws Exception {
    	homePageBenvenuto = new HomePageBenvenuto();
        homePageBenvenuto.start(window, popup);
    }
    
    @FXML private void confermaOperazione (ActionEvent actionEvent) throws Exception {    	
		if(controlloCampi()) {
		    caricamentoRegistrazioneImpiegato = new CaricamentoRegistrazioneImpiegato();
		    caricamentoRegistrazioneImpiegato.start(popup);
		}	
    }
    
    public boolean controlloProvincia() {
    	ProvinciaErrorLabel.setText("");
    	
    	checkProvincia = true;
    	
        //CONTROLLO PROVINCIA DI NASCITA
    	if(ProvinciaTF.getText().isBlank()) {
    		checkProvincia = false;
    		ProvinciaErrorLabel.setText("Questo campo è obbligatorio");
    	} else {
    		if(!(ProvinciaTF.getText().matches("[a-zA-Z]+"))) {
    			checkProvincia = false;
    			ProvinciaErrorLabel.setText("La provincia può contenere solo lettere");
    		}
    	}
    	
    	return checkProvincia;
    }
    
    public boolean controlloCampiAnagrafica() {
    	NomeErrorLabel.setText("");
    	CognomeErrorLabel.setText("");
    	DataDiNascitaErrorLabel.setText("");     
    	ComuneErrorLabel.setText("");
    	
    	checkNome = true;
    	checkCognome = true;
    	checkData = true;
    	checkProvincia = controlloProvincia();
    	checkComune = true;
    	
    	//CONTROLLO NOME
    	if(NomeTF.getText().isBlank()) {
    		checkNome = false;
    		NomeErrorLabel.setText("Questo campo è obbligatorio");
    	} else {
    		if(!(NomeTF.getText().matches("[a-zA-Z\s]+"))) {
    			checkNome = false;
    			NomeErrorLabel.setText("Il nome può contenere solo lettere");
    		}
    	}
    	
    	//CONTROLLO COGNOME
    	if(CognomeTF.getText().isBlank()) {
    		checkCognome = false;
    		CognomeErrorLabel.setText("Questo campo è obbligatorio");
    	} else {
    		if(!(CognomeTF.getText().matches("[a-zA-Z\s]+"))) {
    			checkCognome = false;
    			CognomeErrorLabel.setText("Il cognome può contenere solo lettere");
    		}
    	}
       
    	//CONTROLLO DATA DI NASCITA
        dataSupportata = LocalDate.of(OggiAnno - 18, OggiMese, OggiGiorno);
        dataDiOggi = LocalDate.of(OggiAnno, OggiMese, OggiGiorno);
        
        if(DataDiNascitaDP.getValue() != null) {            	
            	if(DataDiNascitaDP.getValue().isAfter(dataSupportata)) {
        			checkData = false;
        			DataDiNascitaErrorLabel.setText("L'impiegato deve avere almeno 18 anni");
        		}

            	if(DataDiNascitaDP.getValue().isAfter(dataDiOggi)){
            		checkData = false;
            		DataDiNascitaErrorLabel.setText("Inserisci una data di nascita corretta");
            	}
            } else {
            	checkData = false;
            	DataDiNascitaErrorLabel.setText("Questo campo è obbligatorio");
        }
    	
    	//CONTROLLO COMUNE DI NASCITA
        if(ComuneComboBox.getSelectionModel().isEmpty() && checkProvincia) {
    		checkComune = false;
    		ComuneErrorLabel.setText("Questo campo è obbligatorio");
        }
    	
    	return checkNome && checkCognome && checkData && checkProvincia && checkComune;
    }
    
    public boolean controlloCampi() {
    	EmailErrorLabel.setText("");
    	PasswordErrorLabel.setText("");
    	CodiceFiscaleErrorLabel.setText("");
   
    	checkEmail = true;
    	checkPassword = true;
    	checkAnagrafica = controlloCampiAnagrafica();
    	checkCF = true;
    	
    	//CONTROLLO EMAIL
    	if(EmailTF.getText().isBlank()) {
    		checkEmail = false;
    		EmailErrorLabel.setText("Questo campo è obbligatorio");
    	} else {
    		if(!(EmailTF.getText().matches("[a-zA-Z0-9._%-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}"))) {
    			checkEmail = false;
    			EmailErrorLabel.setText("La sintassi dell'email non è valida");
    		}
    	}
    	
    	//CONTROLLO PASSWORD
    	if(PasswordTF.getText().isBlank()) {
    		checkPassword = false;
    		PasswordErrorLabel.setText("Questo campo è obbligatorio");
    	} else {
    		if(!(PasswordTF.getText().length() >= 4)) {
    			checkPassword = false;
    			PasswordErrorLabel.setText("La password deve contenere almeno 4 caratteri");
    		}
    	}
    	
    	//CONTROLLO CODICE FISCALE
    	if(CodiceFiscaleTF == null || codiceFiscale == null) {
    		checkCF = false;
    		CodiceFiscaleErrorLabel.setText("Questo campo è obbligatorio, inserisci tutti i campi e poi clicca qui per calcolare il codice fiscale");
    	} else {
        	if(codiceFiscale.isBlank()) {
        		checkCF = false;
        		CodiceFiscaleErrorLabel.setText("Questo campo è obbligatorio, inserisci tutti i campi e poi clicca qui per calcolare il codice fiscale");
        	}
    	}
    	
    	return checkEmail && checkPassword && checkAnagrafica && checkCF;
    }
    
    @FXML public void visualizzaInformazioniSkill(MouseEvent event) {    	
    	infoSkill = SkillLV.getSelectionModel().getSelectedItem();
		
		informazioniSkill = new FinestraErrore(infoSkill.toString() 	  	+
											   "\n\nTipologia: " 		  	+ infoSkill.getTipoSkill() 		+
											   "\nTitolo del certificato: " + infoSkill.getTitolo()    		+
											   "\nDescrizione: " 		  	+ infoSkill.getDescrizione()	+
											   "\nData di certificazione: " + infoSkill.getDataCertificazione());
		
		try { informazioniSkill.startDettagliSkill(popup);} catch (Exception e) {}
    }

    public void visualizzaNomeLabel(MouseEvent mouseEvent) {
    	
    }

    public void visualizzaCognomeLabel(MouseEvent mouseEvent) {
    	
    }

    public void coloraGenereLabel(MouseEvent mouseEvent) {
    	
    }
}