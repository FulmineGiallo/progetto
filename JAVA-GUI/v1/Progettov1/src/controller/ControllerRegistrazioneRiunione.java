package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
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

public class ControllerRegistrazioneRiunione {

    @FXML private AnchorPane RegistrazioneRiunione;
    @FXML private HBox IstruzioniBox;
    @FXML private Label IstruzioniLabel;
    @FXML private Label IstruzioniLabel2;
    @FXML private HBox FormBox;
    @FXML private ScrollPane FormScrollPane;
    @FXML private VBox FormRiunione;
    @FXML private HBox OrganizzatoreBox;
    @FXML private Label OrganizzatoreLabel;
    @FXML private TextField OrganizzatoreTF;
    @FXML private VBox TitoloBox;
    @FXML private Label TitoloLabel;
    @FXML private TextField TitoloTF;
    @FXML private Label TitoloErrorLabel;
    @FXML private VBox DescrizioneBox;
    @FXML private Label DescrizioneLabel;
    @FXML private TextArea DescrizioneTA;
    @FXML private VBox OrarioDiInizioBox;
    @FXML private Label OrarioDiInizioLabel;
    @FXML private TextField OrarioDiInizioTF;
    @FXML private Label OrarioDiInizioErrorLabel;
    @FXML private VBox OrarioDiFineBox;
    @FXML private Label OrarioDiFineLabel;
    @FXML private TextField OrarioDiFineTF;
    @FXML private Label OrarioDiFineErrorLabel;
    @FXML private HBox ModalitaRiunioneBox;
    @FXML private Label ModalitaRiunioneLabel;
    @FXML private RadioButton ModalitaRiunioneRB1;
    @FXML private ToggleGroup ModalitaRiunioneGroup;
    @FXML private RadioButton ModalitaRiunioneRB2;
    @FXML private VBox FormRiunioneFisica;
    @FXML private VBox SedeBox;
    @FXML private Label SedeLabel;
    @FXML private TextField SedeTF;
    @FXML private Label SedeErrorLabel;
    @FXML private GridPane StanzaBox;
    @FXML private VBox NomeStanzaBox;
    @FXML private Label NomeStanzaLabel;
    @FXML private TextField NomeStanzaTF;
    @FXML private Label NomeStanzaErrorLabel;
    @FXML private VBox PianoStanzaBox;
    @FXML private Label PianoStanzaLabel;
    @FXML private TextField PianoStanzaTF;
    @FXML private Label PianoStanzaErrorLabel;
    @FXML private GridPane FormRiunioneTelematica;
    @FXML private VBox NomePiattaformaBox;
    @FXML private Label NomePiattaformaLabel;
    @FXML private TextField NomePiattaformaTF;
    @FXML private Label NomePiattaformaErrorLabel;
    @FXML private VBox CodiceAccessoBox;
    @FXML private Label CodiceAccessoLabel;
    @FXML private PasswordField CodiceAccessoPF;
    @FXML private Label CodiceAccessoErrorLabel;
    @FXML private AnchorPane ButtonBar;
    @FXML private Button AnnullaButton;
    @FXML private Button ConfermaButton;
    
    @FXML void annullaOperazione(ActionEvent event) {
    	
    }

    @FXML void confermaOperazione(ActionEvent event) {
    	
    }

    @FXML void visualizzaDescrizioneLabel(MouseEvent event) {
    	
    }

    @FXML void visualizzaFormRiunioneFisica(ActionEvent event) {
    	
    }

    @FXML void visualizzaFormRiunioneTelematica(ActionEvent event) {
    	
    }

    @FXML void visualizzaOrarioDiFineLabel(MouseEvent event) {
    	
    }

    @FXML void visualizzaOrarioDiInizioLabel(MouseEvent event) {
    	
    }

    @FXML void visualizzaTitoloLabel(MouseEvent event) {
    	
    }

}
