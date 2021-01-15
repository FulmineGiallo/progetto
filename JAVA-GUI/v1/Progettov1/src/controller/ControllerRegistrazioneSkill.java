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
import javafx.stage.Stage;
import model.Comune;
import model.Grado;
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
    @FXML private ToggleGroup 		TipoSkill;
    @FXML private RadioButton 		TipoSkillRB2;

    @FXML private Label 			DataCertificazioneLabel;
    @FXML private DatePicker 		DataCertificazioneDP;
    @FXML private Label 			DataCertificazioneErrorLabel;

    @FXML private Button 			AnnullaButton;
    @FXML private Button 			ConfermaButton;
    
    private Stage popup;
    private TitoloDAO titoloDAO;
    
    private String descrizione;
    Skill skill;

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
    
    public void inizializza() {
    	try
        {
            titoloDAO = new TitoloDAO(connection);
            TitoloComboBox.setItems(titoloDAO.titoliList());
            TitoloComboBox.getSelectionModel().select(7);
        } catch (SQLException ex)
        {
            ex.printStackTrace();
        }
    }
    
    public boolean controllocampi() {
    	//controllo data
    	return true;
    }

	@FXML private void annullaOperazione(ActionEvent event) {

    }

    @FXML private void confermaOperazione(ActionEvent event) {
    	if(controllocampi()) {
    		descrizione = DescrizioneTA.getText();
    		
    		skill = new Skill(TipoSkill.getSelectedToggle().toString(), DataCertificazioneDP.getValue(), TitoloComboBox.getSelectionModel().getSelectedItem().toString());
    		
    		if(!(descrizione.isBlank())) {
    			skill.setDescrizione(descrizione);
    		}
    	}
    }

}
