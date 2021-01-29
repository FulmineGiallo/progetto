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
import model.Dao.ImpiegatoDao;
import model.DaoInterface.GradoDaoInterface;
import model.DaoInterface.ImpiegatoDaoInterface;
import utilities.MetodiComuni;
import model.Grado;
import model.Impiegato;
import model.Skill;
import model.calcoloCF;

import view.HomePageBenvenuto;
import view.CaricamentoRegistrazioneImpiegato;
import view.FinestraPopup;
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
    
    @FXML private Label 			CaricamentoLabel;
    
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
    private FinestraPopup		   			  finestraInformazioniSkill;
    
    private Skill ultimaSkillInserita;
    private Skill infoSkill;
    
    private ObservableList<Skill> listaSkillImpiegato = FXCollections.observableArrayList();
    
	private Impiegato nuovoImpiegato;
    
    private boolean checkEmail;
    private boolean checkPassword;
    private boolean checkNome;
    private boolean checkCognome;
    private boolean checkData;
    private boolean checkProvincia;
    private boolean checkComune;
    private boolean checkAnagrafica;
    private boolean checkCF;
    
    private MetodiComuni utils = new MetodiComuni();

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
    
    public void setSkillImpiegato(Impiegato nuovoImpiegato) {
    	this.nuovoImpiegato = nuovoImpiegato;
    	
    	listaSkillImpiegato.clear();
		listaSkillImpiegato.addAll(nuovoImpiegato.getListaSkill());
		SkillLV.setItems(listaSkillImpiegato);
    }
    
    public void setStage(Stage window, Stage popup) {
    	this.window = window;
    	this.popup = popup;
    }

    public void inizializza() throws SQLException {
    	nuovoImpiegato = new Impiegato();
    	
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
    
    public boolean controlloProvincia() {
    	ProvinciaErrorLabel.setText("");
    	
    	switch(utils.controlloStringa(ProvinciaTF.getText(), "[a-zA-Z]+")) {
			case 1:
				ProvinciaErrorLabel.setText("Questo campo è obbligatorio");
				return false;
			case 2:
				ProvinciaErrorLabel.setText("La provincia può contenere solo lettere");
				return false;
			default:
				return true;
    	}
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
    	switch(utils.controlloStringa(NomeTF.getText(), "[a-zA-Z\s]+")) {
			case 1:
				checkNome = false;
				NomeErrorLabel.setText("Questo campo è obbligatorio");
				break;
			case 2:
				checkNome = false;
				NomeErrorLabel.setText("Il nome può contenere solo lettere");
				break;
			default:
				checkNome = true;
    	}
    	
    	//CONTROLLO COGNOME
    	switch(utils.controlloStringa(CognomeTF.getText(), "[a-zA-Z\s]+")) {
			case 1:
				checkCognome = false;
				CognomeErrorLabel.setText("Questo campo è obbligatorio");
				break;
			case 2:
				checkCognome = false;
				CognomeErrorLabel.setText("Il cognome può contenere solo lettere");
				break;
			default:
				checkCognome = true;
    	}
       
    	//CONTROLLO DATA DI NASCITA
        dataSupportata = LocalDate.of(OggiAnno - 18, OggiMese, OggiGiorno);
        dataDiOggi = LocalDate.of(OggiAnno, OggiMese, OggiGiorno);
        
        switch(utils.controlloData(DataDiNascitaDP.getValue(), dataDiOggi, dataSupportata)) {
        	case 1:
        		checkData = false;
            	DataDiNascitaErrorLabel.setText("Questo campo è obbligatorio");
            	break;
        	case 2:
        		checkData = false;
        		DataDiNascitaErrorLabel.setText("Inserisci una data di nascita corretta");
        		break;
        	case 3:
        		checkData = false;
    			DataDiNascitaErrorLabel.setText("L'impiegato deve avere almeno 18 anni");
    			break;
			default:
				checkData = true;
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
    	switch(utils.controlloStringa(EmailTF.getText(), "[a-zA-Z0-9._%-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}")) {
    		case 1:
    			checkEmail = false;
    			EmailErrorLabel.setText("Questo campo è obbligatorio");
    			break;
    		case 2:
    			checkEmail = false;
    			EmailErrorLabel.setText("La sintassi dell'email non è valida");
    			break;
    		default:
    			checkEmail = true;
    	}
    	
    	//CONTROLLO PASSWORD
    	switch(utils.controlloPassword(PasswordTF.getText())) {
			case 1:
				checkPassword = false;
	    		PasswordErrorLabel.setText("Questo campo è obbligatorio");
				break;
			case 2:
				checkPassword = false;
				PasswordErrorLabel.setText("La password deve contenere almeno 4 caratteri");
				break;
			default:
				checkPassword = true;
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
    
    @FXML private void cercaComuni(ActionEvent event) throws SQLException{
    	
    	checkProvincia = true;
    	checkComune = true;
    	
    	ComuneComboBox.setPromptText("");
    	ComuneErrorLabel.setText("");
    	ProvinciaErrorLabel.setText("");
    	
    	CodiceFiscaleTF.setText("");
    	
    	updateComune();
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
    
    @FXML public void visualizzaInformazioniSkill(MouseEvent event) {    	
    	infoSkill = SkillLV.getSelectionModel().getSelectedItem();
    	
    	if (infoSkill != null) {
			finestraInformazioniSkill = new FinestraPopup(infoSkill);
			
			try {
				finestraInformazioniSkill.startDettagliSkill(popup);
			} catch (Exception e) {
				e.printStackTrace();
			} 
		}
    }
    
    @FXML private void nuovaSkill(ActionEvent event) throws Exception{
    	formRegistrazioneSkill = new FormRegistrazioneSkill(this, nuovoImpiegato);
    	formRegistrazioneSkill.start(popup);
    }

    @FXML private void annullaOperazione (ActionEvent actionEvent) throws Exception {
    	homePageBenvenuto = new HomePageBenvenuto();
        homePageBenvenuto.start(window, popup);
    }
    
    @FXML private void confermaOperazione (ActionEvent actionEvent) throws Exception {
    	if(controlloCampi()) {
			connection.close();
			caricamentoRegistrazioneImpiegato = new CaricamentoRegistrazioneImpiegato(nuovoImpiegato);
		    caricamentoRegistrazioneImpiegato.start(popup);
		}
    }

    public void visualizzaNomeLabel(MouseEvent mouseEvent) {
    	
    }

    public void visualizzaCognomeLabel(MouseEvent mouseEvent) {
    	
    }

    public void coloraGenereLabel(MouseEvent mouseEvent) {
    	
    }
}