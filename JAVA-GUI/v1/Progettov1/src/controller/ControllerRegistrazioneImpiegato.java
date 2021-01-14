package controller;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Calendar;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import model.Comune;
import model.Connection.DBConnection;
import model.Dao.ComuneDao;
import model.Dao.GradoDao;
import model.DaoInterface.GradoDaoInterface;
import model.Grado;
import model.calcoloCF;

import view.HomePageBenvenuto;
import view.CaricamentoRegistrazioneImpiegato;

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
    @FXML private ToggleGroup 		Genere;
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
    @FXML private MenuButton 		SkillMenuButton;
    
    @FXML private Label				GradoLabel;
    @FXML private ComboBox<Grado> 	GradoComboBox;
    
    @FXML private Button 			AnnullaButton;
    @FXML private Button 			ConfermaButton;
    
    private Stage window;
    private Stage popup;

    private Calendar Oggi = Calendar.getInstance();
    private int OggiGiorno = Oggi.get(Calendar.DAY_OF_MONTH);
    private int OggiMese = Oggi.get(Calendar.MONTH) + 1;
    private int OggiAnno = Oggi.get(Calendar.YEAR);
    
    private LocalDate DataDiNascita = null;
    private LocalDate dataSupportata = null;
   
    private HomePageBenvenuto homePageBenvenuto;
    private CaricamentoRegistrazioneImpiegato caricamentoRegistrazioneImpiegato;
    
    private boolean checkEmail = true;
    private boolean checkPassword = true;
    private boolean checkNome = true;
    private boolean checkCognome = true;
    private boolean checkData = true;
    private boolean checkProvincia = true;

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

        comuneList = comuni.gradoList(ProvinciaTF.getText().toUpperCase());
    }
    
    public void updateComune() throws SQLException {
        comuneList = comuni.gradoList(ProvinciaTF.getText().toUpperCase());
        if(comuneList.isEmpty()) {
        	ComuneComboBox.setPromptText("Nessun risultato trovato!");
        } else {
        	ComuneComboBox.setPromptText(String.valueOf(comuneList.size()) + " comuni trovati");
            ComuneComboBox.setItems(comuneList);
        }
    }
    
    @FXML private void cercaComuni(ActionEvent event) throws SQLException{
    	ComuneComboBox.setPromptText("");
    	updateComune();
    }

    @FXML private void CFRegistrazione() {
    	CodiceFiscaleErrorLabel.setText("");
			try {
				String comune = ComuneComboBox.getValue().getCodiceComune();
				calcoloCF cf = null;

				DataDiNascita = DataDiNascitaDP.getValue();

				int AnnoNascita = DataDiNascita.getYear();
				int MeseNascita = DataDiNascita.getMonthValue();
				int GiornoNascita = DataDiNascita.getDayOfMonth();

				if (GenereRB1.isSelected())
					cf = new calcoloCF(CognomeTF.getText(), NomeTF.getText(), 'M', GiornoNascita, MeseNascita,
							AnnoNascita, comune);
				else
					cf = new calcoloCF(CognomeTF.getText(), NomeTF.getText(), 'F', GiornoNascita, MeseNascita,
							AnnoNascita, comune);

				CodiceFiscaleTF.setText(cf.toString());

			} catch (Exception e) {
				CodiceFiscaleErrorLabel.setText("Inserisci tutti i campi prima di calcolare il codice fiscale");
			}
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
    
    public boolean controlloCampi() {
    	EmailErrorLabel.setText("");
    	PasswordErrorLabel.setText("");
    	NomeErrorLabel.setText("");
    	CognomeErrorLabel.setText("");
    	DataDiNascitaErrorLabel.setText("");        
    	ProvinciaErrorLabel.setText("");
    	
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
    	
    	//CONTROLLO NOME
    	if(NomeTF.getText().isBlank()) {
    		checkNome = false;
    		NomeErrorLabel.setText("Questo campo è obbligatorio");
    	} else {
    		if(!(NomeTF.getText().matches("[a-zA-Z]+"))) {
    			checkNome = false;
    			NomeErrorLabel.setText("Il nome può contenere solo lettere");
    		}
    	}
    	
    	//CONTROLLO COGNOME
    	if(CognomeTF.getText().isBlank()) {
    		checkCognome = false;
    		CognomeErrorLabel.setText("Questo campo è obbligatorio");
    	} else {
    		if(!(CognomeTF.getText().matches("[a-zA-Z]+"))) {
    			checkCognome = false;
    			CognomeErrorLabel.setText("Il cognome può contenere solo lettere");
    		}
    	}
       
    	//CONTROLLO DATA DI NASCITA
        dataSupportata = LocalDate.of(OggiAnno - 18, OggiMese, OggiGiorno);

        if(DataDiNascita != null) {
        	if(DataDiNascita.isAfter(dataSupportata)) {
    			checkData=false;
    			DataDiNascitaErrorLabel.setText("L'impiegato deve avere almeno 18 anni");
    		}
        	
        	if(DataDiNascita.isAfter(LocalDate.of(OggiAnno, OggiMese, OggiGiorno))){
        		checkData = false;
        		DataDiNascitaErrorLabel.setText("Inserisci una data di nascita corretta");
        	}
        } else {
        	checkData = false;
        	DataDiNascitaErrorLabel.setText("Questo campo è obbligatorio");
        }
        
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
    	
    	return checkNome && checkCognome && checkEmail && checkPassword && checkData && checkProvincia;
    }

    public void visualizzaNomeLabel(MouseEvent mouseEvent) {
    	
    }

    public void visualizzaCognomeLabel(MouseEvent mouseEvent) {
    	
    }

    public void coloraGenereLabel(MouseEvent mouseEvent) {
    	
    }
}