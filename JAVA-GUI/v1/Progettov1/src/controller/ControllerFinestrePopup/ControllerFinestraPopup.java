package controller.ControllerFinestrePopup;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class ControllerFinestraPopup {

@FXML protected AnchorPane 	FinestraPopup;
    
    @FXML protected GridPane 	MessaggioBox;
    @FXML protected VBox 		TestoBox;
    @FXML protected Label 		TitoloLabel;
    @FXML protected TextArea 	MessaggioTA;
    @FXML protected Label 		MessaggioLabel;
    
    @FXML protected VBox 		ImmagineBox;
    @FXML protected ImageView 	Immagine;
    
    @FXML protected GridPane 	ButtonBar;
    @FXML protected Button 		SinistraButton;
    @FXML protected Button 		DestraButton;
}