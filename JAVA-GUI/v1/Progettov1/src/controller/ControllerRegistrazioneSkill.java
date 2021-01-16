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
    
    @FXML private Label 			TitoloLabel;
    @FXML private ComboBox<Titolo> 	TitoloComboBox;
    @FXML private TextField 		NuovoTitoloTF;
    @FXML private Label 			NuovoTitoloErrorLabel;
    
    @FXML private Label 			DescrizioneLabel;
    @FXML private TextArea 			DescrizioneTA;

    @FXML private Label 			TipoSkillLabel;
    @FXML private RadioButton 		TipoSkillRB1;
    @FXML private ToggleGroup 		TipoSkillGroup;
    @FXML private RadioButton 		TipoSkillRB2;

    @FXML private Label 			DataCertificazioneLabel;
    @FXML private DatePicker 		DataCertificazioneDP;
    @FXML private Label 			DataCertificazioneErrorLabel;

    @FXML private Button 			AnnullaButton;
    @FXML private Button 			ConfermaButton;
    
    private Stage popup;
    private TitoloDAO titoloDAO;
    
    private String descrizione;
    private String tipoSkill;
    private Date dataCertificazioneDate;
    private Date dataDiOggi = Calendar.getInstance().getTime();
    
    private Impiegato impiegato = null;
    private Skill skill = null;
    
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
    	
    	try
        {
            titoloDAO = new TitoloDAO(connection);
            TitoloComboBox.setItems(titoloDAO.titoliList());
            TitoloComboBox.getSelectionModel().select(7);
            
            TitoloComboBox.getSelectionModel().selectedItemProperty().addListener( (options, oldValue, newValue) -> {

                  
                	if(TitoloComboBox.getSelectionModel().getSelectedItem().toString().equals("Altro") )
                    {
                		NuovoTitoloTF.setVisible(true);	
                    }
                	else {
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
    	DescrizioneLabel.setText("");
    	NuovoTitoloErrorLabel.setText("");
    
    	boolean checkDataCertificazione=true;
    	boolean checkNuovoTitolo=true;
    	
    	if(DataCertificazioneDP.getValue() != null)
    		dataCertificazioneDate = java.sql.Date.valueOf(DataCertificazioneDP.getValue());
    	
    	if(dataCertificazioneDate == null || dataCertificazioneDate.after(dataDiOggi)) {
    		checkDataCertificazione = false;
    		
    		if(dataCertificazioneDate == null)
    			DataCertificazioneErrorLabel.setText("Inserire la data");
    		else
    			DataCertificazioneErrorLabel.setText("Inserire una data corretta");
    	}
    	
    	if(NuovoTitoloTF.isVisible() && NuovoTitoloTF.getText().isBlank()) {
    		checkNuovoTitolo = false;
    		NuovoTitoloErrorLabel.setVisible(true);
    		NuovoTitoloErrorLabel.setText("Inseisci il titolo");
    	}
    	
    	return checkDataCertificazione && checkNuovoTitolo;
    }

	@FXML private void annullaOperazione(ActionEvent event) {

    }

    @FXML private void confermaOperazione(ActionEvent event) {
    	if(controllocampi()) {
    		descrizione = DescrizioneTA.getText();
    		tipoSkill = ((RadioButton)TipoSkillGroup.getSelectedToggle()).getText();
    		
    		skill = new Skill(tipoSkill, DataCertificazioneDP.getValue(), TitoloComboBox.getSelectionModel().getSelectedItem().toString());
    		
    		if(!(descrizione.isBlank())) {
    			skill.setDescrizione(descrizione);
    		}
    	    		
    		impiegato.getListaSkill().add(skill);
    		controllerRegistrazioneImpiegato.setNuovaSkillPerImpiegato(impiegato);
    		
    		popup.hide();
    	}
    }

}
