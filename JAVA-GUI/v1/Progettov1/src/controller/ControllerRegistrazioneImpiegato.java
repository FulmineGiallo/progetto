package controller;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart.Data;
import javafx.scene.control.*;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.converter.LocalDateStringConverter;
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

	//FormRegistrazioneImpiegato
    @FXML private TextField 	NomeTF;
    @FXML private TextField 	CognomeTF;
    @FXML private RadioButton 	GenereRB1;
    @FXML private RadioButton 	GenereRB2;
    @FXML private ToggleGroup 	Genere;
    @FXML private DatePicker 	DataDiNascitaDP;
    @FXML private ComboBox<Comune> 	ComuneComboBox;
    @FXML private TextField ProvinciaTextField;
    @FXML private ListView<?> 	SkillLV;
    @FXML private ComboBox<Grado> GradoComboBox;
    @FXML private Button 		AnnullaButton;
    @FXML private Button 		ConfermaButton;
    @FXML private Button        CercaComuniButton;
    @FXML private TextField CodiceFiscaleTF;
    @FXML private Label IstruzioniLabel;
    @FXML private Label IstruzioniLabel2;
    @FXML private ScrollPane FormScrollPane;
    @FXML private Label EmailLabel;
    @FXML private TextField EmailTF;
    @FXML private Label EmailErrorLabel;
    @FXML private Label PasswordLabel;
    @FXML private TextField PasswordTF;
    @FXML private Label PasswordErrorLabel;
    @FXML private Label NomeLabel;
    @FXML private Label NomeErrorLabel;
    @FXML private Label CognomeErrorLabel;
    @FXML private Label GenereLabel;
    @FXML private Label GenereErrorLabel;
    @FXML private Label DataDiNascitaLabel;
    @FXML private Label DataDiNascitaErrorLabel;
    @FXML private Label ComuneLabel11;
    @FXML private Label ProvinciaLabel;
    @FXML private Label CodiceFiscaleLabel;
    @FXML private Label SkillLabel;
    @FXML private MenuButton SkillMenuButton;
    @FXML private Label GradoLabel;

 

    HomePageBenvenuto homePageBenvenuto;
    CaricamentoRegistrazioneImpiegato caricamentoRegistrazioneImpiegato;

    Connection connection;
    DBConnection dbConnection;
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

    ObservableList<Grado> gradiList = FXCollections.observableArrayList();
    ObservableList<Comune> comuneList = FXCollections.observableArrayList();
    GradoDaoInterface gradi = null;
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
    ComuneDao comuni = null;
    {
        try
        {
            comuni = new ComuneDao(connection);
        } catch (SQLException throwables)
        {
            throwables.printStackTrace();
        }
    }

    public void inizializza() throws SQLException
    {
        GradoComboBox.getItems().addAll(gradiList);
        GradoComboBox.getSelectionModel().select(2);
        
        ProvinciaTextField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                if(ProvinciaTextField.getText().length() > 2 )
                {
                    s = ProvinciaTextField.getText().substring(0, 2).toUpperCase();
                    ProvinciaTextField.setText(s);
                }
            }
        });
        comuneList = comuni.gradoList(ProvinciaTextField.getText());
    }
    public void updateComune() throws SQLException
    {
        comuneList = comuni.gradoList(ProvinciaTextField.getText());
        ComuneComboBox.setItems(comuneList);
      //  System.out.println(comuneList.size());
    }
    @FXML
    void cercaComuni(ActionEvent event) throws SQLException
    {
        updateComune();
    }

    public void CFRegistrazione()
    {
 
    	try {
    	String comune = ComuneComboBox.getValue().toString().substring(0,4);
    	calcoloCF cf = null;
    	
    	String data = DataDiNascitaDP.getValue().toString();
    	
    	String year = data.substring(0,4);
    	int month = Integer.parseInt(data.substring(5,7));
    	int day = Integer.parseInt(data.substring(8,10));   	
    	
    	
    	if(GenereRB1.isSelected())
    		cf = new calcoloCF(CognomeTF.getText(), NomeTF.getText(), 'M', day, month, year, comune);
    	else
    		cf = new calcoloCF(CognomeTF.getText(), NomeTF.getText(), 'F', day, month, year, comune);
        
    	CodiceFiscaleTF.setStyle("-fx-text-fill: white");
    	CodiceFiscaleTF.setText(cf.toString());
    	
    	}catch(Exception e) {
    		CodiceFiscaleTF.setStyle("-fx-text-fill: red");
    		CodiceFiscaleTF.setText("Inserire tutti i campi prima di calcolare il codice fiscale, poi ricliccare qui");
    	}
    	
    }
    
    
    public void annullaOperazione (ActionEvent actionEvent) throws Exception
    {
    	PrintWriter writer = null;
    	homePageBenvenuto = new HomePageBenvenuto(writer);

        Stage stage = (Stage)AnnullaButton.getScene().getWindow();
        homePageBenvenuto.start(stage);
    }
    
    public void confermaOperazione (ActionEvent actionEvent) throws Exception
    {
    	
    	NomeErrorLabel.setText("");
    	CognomeErrorLabel.setText("");
    	CognomeErrorLabel.setText("");
    	EmailErrorLabel.setText("");
    	PasswordErrorLabel.setText("");
    	
    	boolean checkNome = true;
    	boolean checkCognome = true;
    	boolean checkEmail = true;
    	boolean checkPassword = true;
    	
    	
    	
    	if (!(NomeTF.getText().matches("[a-zA-Z\s]+")) || ( NomeTF.getText().isBlank()) ) {
    		checkNome = false;
    		if(NomeTF.getText().isBlank())	
    			NomeErrorLabel.setText("Il nome non puo essere vuoto");
    		else
    			NomeErrorLabel.setText("Il nome puo contenere solo lettere");
    	}
        
    	
    	if (!(CognomeTF.getText().matches("[a-zA-Z\s]+")) || ( CognomeTF.getText().isBlank()) ) {
    		checkCognome=false;
    		if(CognomeTF.getText().isBlank())	
    			CognomeErrorLabel.setText("Il cognome non puo essere vuoto");
    		else
    			CognomeErrorLabel.setText("Il Cognome puo contenere solo lettere");
    	}
    	
    	if (!(EmailTF.getText().matches("[a-zA-Z0-9._%-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}")) || ( EmailTF.getText().isBlank()) ) {
        		checkEmail=false;
        		if(EmailTF.getText().isBlank())	
        			EmailErrorLabel.setText("L' email non puo essere vuoto");
        		else
        			EmailErrorLabel.setText("L'email non rispetta la sintassi");
    	}
         if (( !(PasswordTF.getText().matches("[a-zA-Z0-9._%-]{4,}")) || ( EmailTF.getText().isBlank()))) {
            		checkPassword=false;
            		if(PasswordTF.getText().isBlank())	
            			PasswordErrorLabel.setText("La password non puo essere vuota");
            		else
            			PasswordErrorLabel.setText("La password deve contenere almeno 4 caratteri");
         }
         
  
        
       //  System.out.print(checkNome + " " + checkCognome + " "  + checkEmail + " " + checkPassword );
         
         
         if(checkNome && checkCognome && checkEmail && checkPassword) {
         PrintWriter writer = null;
         caricamentoRegistrazioneImpiegato = new CaricamentoRegistrazioneImpiegato(writer);

         Stage stage = (Stage)ConfermaButton.getScene().getWindow();
         caricamentoRegistrazioneImpiegato.start(stage);
         }   	
    }

    public void visualizzaNomeLabel(MouseEvent mouseEvent) {
    }

    public void visualizzaCognomeLabel(MouseEvent mouseEvent) {
    }

    public void coloraGenereLabel(MouseEvent mouseEvent) {
    }


}