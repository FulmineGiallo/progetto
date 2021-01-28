package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.Window;
import model.Comune;
import model.Grado;
import model.Impiegato;
import model.Skill;
import model.Titolo;
import model.Connection.DBConnection;
import model.Dao.ComuneDao;
import model.Dao.GradoDao;
import model.Dao.TitoloDAO;
import model.DaoInterface.GradoDaoInterface;
import model.DaoInterface.TitoloDaoInterface;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

public class ControllerRegistrazioneSkill {

    @FXML private Label 			IstruzioniLabel;
    @FXML private Label 			IstruzioniLabel2;
    
    @FXML private Label 			TipoSkillLabel;
    @FXML private RadioButton 		TipoSkillRB1;
    @FXML private ToggleGroup 		TipoSkillGroup;
    @FXML private RadioButton 		TipoSkillRB2;
    
    @FXML private Label 			DescrizioneLabel;
    @FXML private TextArea 			DescrizioneTA;
    @FXML private Label				DescrizioneErrorLabel;
    
    @FXML private Label 			DataCertificazioneLabel;
    @FXML private DatePicker 		DataCertificazioneDP;
    @FXML private Label 			DataCertificazioneErrorLabel;
    
    @FXML private GridPane			TitoloBox;    
    @FXML private Label 			TitoloLabel;
    @FXML private ComboBox<Titolo> 	TitoloComboBox;
    @FXML private TextField 		NuovoTitoloTF;
    @FXML private Label 			NuovoTitoloErrorLabel;

    @FXML private Button 			AnnullaButton;
    @FXML private Button 			ConfermaButton;
    
    private Stage popup;
    private TitoloDAO titoloDAO;
    
    private String tipoSkill;
    private String descrizioneSkill;
    private LocalDate dataCertificazione;
    private Titolo titoloSkill;
    private final String DescrizioneTAPrompt = "Inserisci una descrizione";
    
    private Calendar  Oggi 		 = Calendar.getInstance();
    private int 	  OggiGiorno = Oggi.get(Calendar.DAY_OF_MONTH);
    private int 	  OggiMese 	 = Oggi.get(Calendar.MONTH) + 1;
    private int 	  OggiAnno   = Oggi.get(Calendar.YEAR);
    private LocalDate dataDiOggi;
    
    private Impiegato impiegato = null;
    private Skill skill = null;
    
    private boolean checkDescrizione		= true;
    private boolean checkDataCertificazione = true;
    private boolean checkNuovoTitolo 		= true;
    
    ControllerRegistrazioneImpiegato controllerRegistrazioneImpiegato;
    
    public void setStage(Stage popup) {
		this.popup = popup;
	}
    
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
    
    public void inizializza(ControllerRegistrazioneImpiegato controllerRegistrazioneImpiegato) {
		this.controllerRegistrazioneImpiegato = controllerRegistrazioneImpiegato;
    	impiegato = new Impiegato();
    	
    	DescrizioneTA.setPromptText("* " + DescrizioneTAPrompt);
    	
    	try {
            titoloDAO = new TitoloDAO(connection);
            TitoloComboBox.setItems(titoloDAO.titoliList());
            TitoloComboBox.getSelectionModel().select(7);
            
            TitoloComboBox.getSelectionModel().selectedItemProperty().addListener( (options, oldValue, newValue) -> {
            	if(TitoloComboBox.getSelectionModel().getSelectedItem().toString().equals("Altro...")) {
            		NuovoTitoloTF.setVisible(true);	
                } else {
            		NuovoTitoloTF.setVisible(false);
            		NuovoTitoloErrorLabel.setText("");
            	}
            });
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    public boolean controllocampi() {
    	DataCertificazioneErrorLabel.setText("");
    	DescrizioneErrorLabel.setText("");
    	NuovoTitoloErrorLabel.setText("");
    	
    	checkDescrizione 		= true;
    	checkDataCertificazione = true;
    	checkNuovoTitolo 		= true;
    	
    	//CONTROLLO DESCRIZIONE (SE OBBLIGATORIA)
    	if(!TitoloBox.isVisible()) {
    		if(DescrizioneTA.getText().isBlank()) {
    			checkDescrizione = false;
    			DescrizioneErrorLabel.setText("Questo campo è obbligatorio");
    		}
    	}
    	
    	//CONTROLLO DATA DI NASCITA
        dataDiOggi = LocalDate.of(OggiAnno, OggiMese, OggiGiorno);
        
        if(DataCertificazioneDP.getValue() != null) {
        	if(DataCertificazioneDP.getValue().isAfter(dataDiOggi)) {
        		checkDataCertificazione = false;
        		DataCertificazioneErrorLabel.setText("Inserisci una data di certificazione corretta");
        	}
        } else {
        	checkDataCertificazione = false;
        	DataCertificazioneErrorLabel.setText("Questo campo è obbligatorio");
        }
    	
        //CONTROLLO NUOVO TITOLO (SE OBBLIGATORIO)
    	if (TitoloBox.isVisible()) {
			if (NuovoTitoloTF.isVisible()) {
				if (NuovoTitoloTF.getText().isBlank()) {
					checkNuovoTitolo = false;
					NuovoTitoloErrorLabel.setVisible(true);
					NuovoTitoloErrorLabel.setText("Questo campo è obbligatorio");
				}
			}
		}
    	
		return checkDataCertificazione && checkNuovoTitolo && checkDescrizione;
    }

	@FXML private void annullaOperazione(ActionEvent event) {
		//aggiungere conferma con FinestraErrore
		popup.hide();
    }

    @FXML private void confermaOperazione(ActionEvent event) {
    	if(controllocampi()) {
    		descrizioneSkill = DescrizioneTA.getText();
    		tipoSkill = ((RadioButton)TipoSkillGroup.getSelectedToggle()).getText();
    		
    		
    		if (tipoSkill.equals("Hard-Skill")) {
				if (NuovoTitoloTF.isVisible()) {
					titoloSkill = new Titolo(NuovoTitoloTF.getText());
				} else {
					titoloSkill = new Titolo(TitoloComboBox.getSelectionModel().getSelectedItem().toString());
				}
			} else {
				titoloSkill = new Titolo(tipoSkill);
			}
    		
			if(!descrizioneSkill.isBlank()) {
    			impiegato.getListaSkill().add(new Skill(tipoSkill, DataCertificazioneDP.getValue(), titoloSkill.getTipoTitolo(), descrizioneSkill));
    		} else {
    			impiegato.getListaSkill().add(new Skill(tipoSkill, DataCertificazioneDP.getValue(), titoloSkill.getTipoTitolo()));
    		}
    		
    		controllerRegistrazioneImpiegato.setSkillImpiegato(impiegato);
    		popup.hide();
    	}
    }
    
    @FXML
    void mostraTitolo(MouseEvent event) {
    	TitoloBox.setVisible(true);
    	DescrizioneTA.setPromptText(DescrizioneTAPrompt);
    }

    @FXML
    void nascondiTitolo(MouseEvent event) {
    	TitoloBox.setVisible(false);
    	DescrizioneTA.setPromptText("* " + DescrizioneTAPrompt);
    }

}
