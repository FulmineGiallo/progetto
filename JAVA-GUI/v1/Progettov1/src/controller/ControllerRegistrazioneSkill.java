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
import javafx.event.ActionEvent;

public class ControllerRegistrazioneSkill {

    @FXML private AnchorPane 	RegistrazioneSkill;
    @FXML private HBox 			IstruzioniBox;
    @FXML private Label 		IstruzioniLabel;
    @FXML private Label 		IstruzioniLabel2;
    @FXML private HBox 			FormBox;
    @FXML private ScrollPane 	FormScrollPane;
    @FXML private VBox 			Form;
    @FXML private GridPane 		TitoloBox;
    @FXML private Label 		TitoloLabel;
    @FXML private ComboBox<?> 	TitoloComboBox;
    @FXML private TextField 	NuovoTitoloTF;
    @FXML private Label 		NuovoTitoloErrorLabel;
    @FXML private VBox 			DescrizioneBox;
    @FXML private Label 		DescrizioneLabel;
    @FXML private TextArea 		DescrizioneTA;
    @FXML private HBox 			TipoSkillBox;
    @FXML private Label 		TipoSkillLabel;
    @FXML private HBox 			TipoSkillRBBox;
    @FXML private RadioButton 	TipoSkillRB1;
    @FXML private ToggleGroup 	TipoSkill;
    @FXML private RadioButton 	TipoSkillRB2;
    @FXML private GridPane 		DataCertificazioneBox;
    @FXML private Label 		DataCertificazioneLabel;
    @FXML private DatePicker 	DataCertificazioneDP;
    @FXML private Label 		DataCertificazioneErrorLabel;
    @FXML private AnchorPane 	ButtonBar;
    @FXML private Button 		AnnullaButton;
    @FXML private Button 		ConfermaButton;

    @FXML
    void annullaOperazione(ActionEvent event) {

    }

    @FXML
    void confermaOperazione(ActionEvent event) {

    }

}
