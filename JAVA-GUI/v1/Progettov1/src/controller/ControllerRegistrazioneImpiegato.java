package controller;

import java.io.PrintWriter;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

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
    @FXML private ListView<?> 	ComuneLV;
    @FXML private TextField 	ProvinciaTF;
    @FXML private ListView<?> 	SkillLV;
    @FXML private ListView<?> 	GradoLV;
    @FXML private Button 		AnnullaButton;
    @FXML private Button 		ConfermaButton;
    
    HomePageBenvenuto homePageBenvenuto;
    CaricamentoRegistrazioneImpiegato caricamentoRegistrazioneImpiegato;
    
    
    public void annullaOperazione (ActionEvent actionEvent) throws Exception{
        
    	PrintWriter writer = null;
    	homePageBenvenuto = new HomePageBenvenuto(writer);

        Stage stage = (Stage)AnnullaButton.getScene().getWindow();
        homePageBenvenuto.start(stage);
    }
    
    public void confermaOperazione (ActionEvent actionEvent) throws Exception{
    	
    	PrintWriter writer = null;
    	caricamentoRegistrazioneImpiegato = new CaricamentoRegistrazioneImpiegato(writer);

        Stage stage = (Stage)ConfermaButton.getScene().getWindow();
        caricamentoRegistrazioneImpiegato.start(stage);
    }
}