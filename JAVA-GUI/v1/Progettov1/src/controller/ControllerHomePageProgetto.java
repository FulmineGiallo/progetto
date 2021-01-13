package controller;

import java.io.PrintWriter;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Impiegato;
import view.HomePageBenvenuto;
import view.HomePageImpiegato;

public class ControllerHomePageProgetto {

	HomePageImpiegato homeImpiegato;
	
    @FXML private AnchorPane 			HomePageProjectManager;
    
    @FXML private VBox 					ImpiegatoBox;
    @FXML private Label 				NomeImpiegatoLabel;
    @FXML private Label 				GradoImpiegatoLabel;
    
    @FXML private HBox 					ToolBar;
    @FXML private Button 				NuovaRiunioneButton;
    @FXML private Button 				AggiungiImpiegatoButton;
    @FXML private Button 				HomePageImpiegatoButton;
    
    @FXML private AnchorPane 			ListaPartecipantiBox;
    @FXML private Label 				ListaPartecipantiLabel;
    @FXML private ScrollPane 			ListaPartecipantiScrollPane;
    @FXML private ListView<Impiegato> 	ListaPartecipantiLV;
    
    @FXML private AnchorPane 			IstruzioniBox;
    @FXML private Label 				IstruzioniLabel;
    
    @FXML private AnchorPane 			DescrizioneProgettoImpiegatoBox;
    @FXML private AnchorPane 			DescrizioneProgettoImpiegatoPane;

    /*void backHomePageImpiegato(ActionEvent event) throws Exception {

            PrintWriter writer = null;
            homeImpiegato = new HomePageImpiegato(writer);

            Stage stage = (Stage) buttonHome.getScene().getWindow();
            homeImpiegato.start(stage);

    }
    */
}
