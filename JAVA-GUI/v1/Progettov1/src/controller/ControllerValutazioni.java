package controller;

import java.io.PrintWriter;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import view.HomePageBenvenuto;
import view.HomePageImpiegato;
import view.HomePageValutazioni;

public class ControllerValutazioni {
	
	HomePageImpiegato homeImpiegato;
	

    @FXML
    private ListView<?> ValutazioniListView;

    @FXML
    private ListView<?> RecensiononeListView;

    @FXML
    private Label RecensoreLabel;

    @FXML
    private Label StelleLabel;

    @FXML
    private Label dataValutazioneLabel;

    @FXML
    private Button TornaAllaHomeButton;
    
   public void backhomeimpiegato(ActionEvent event) throws Exception {
	   
	   	PrintWriter writer = null;
   		homeImpiegato = new HomePageImpiegato(writer, null);

   		Stage stage = (Stage)TornaAllaHomeButton.getScene().getWindow();
   		homeImpiegato.start(stage);
    }

}
