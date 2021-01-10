package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import javafx.event.ActionEvent;

public class ControllerFinestraErrore {

    @FXML private AnchorPane 	FinestraErrore;
    @FXML private GridPane 		MessaggioBox;
    @FXML private VBox 			TestoBox;
    @FXML private Label 		AttenzioneLabel;
    @FXML private TextArea 		MessaggioErroreTA;
    @FXML private Label 		MessaggioErroreLabel;
    @FXML private VBox 			ImmagineBox;
    @FXML private ImageView 	AttenzioneIV;
    @FXML private GridPane 		ButtonBar;
    @FXML private Button 		DettagliButton;
    @FXML private Button 		OkButton;

    @FXML
    void MostraNascondiDettagli(ActionEvent event) {

    }

}
