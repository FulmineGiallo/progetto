package controller;

import java.io.PrintWriter;
import java.util.Locale;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import model.Impiegato;
import view.HomePageBenvenuto;
import view.HomePageImpiegato;
import view.HomePageValutazioni;

public class ControllerValutazioni {
	
	HomePageImpiegato homeImpiegato;
	

	Impiegato impiegato = new Impiegato(null);
	
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
    
    
    public void inizializza(Impiegato impiegato)
    {
    	this.impiegato=impiegato;
    }
    
   public void backhomeimpiegato(ActionEvent event) throws Exception {
	   
	   	PrintWriter writer = null;
   		homeImpiegato = new HomePageImpiegato(writer, impiegato);

   		Stage stage = (Stage)TornaAllaHomeButton.getScene().getWindow();
   		homeImpiegato.start(stage);
    }

}
