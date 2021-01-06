package controller;


import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import java.io.PrintWriter;
import java.util.Locale;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import model.Impiegato;
import model.Progetto;
import view.HomePageProgetto;
import view.HomePageValutazioni;

public class ControllerHomePageImpiegato
{
	 HomePageProgetto homeProgetto;
	 HomePageValutazioni homeValutazioni;

	 @FXML
     private Button buttonValutazioni;
     @FXML
     private Button buttonCreaProgetto;
     @FXML
     private Label nomeImpiegato;
     @FXML
     private Label gradoLabel;

    public void inizializza(Impiegato impiegato)
    {
    	nomeImpiegato.setText((impiegato.getNome() +" "+ impiegato.getCognome()).toUpperCase(Locale.ROOT));
        gradoLabel.setText(impiegato.getGrado());
    }

    @FXML
    public void CreateProject(ActionEvent actionEvent) throws Exception
    {
    	PrintWriter writer = null;
        homeProgetto = new HomePageProgetto(writer);

        Stage stage = (Stage) buttonCreaProgetto.getScene().getWindow();
        homeProgetto.start(stage);
    	
    }
    
    
    @FXML
    public void VisualizzaValutazioni(ActionEvent event) throws Exception {

    	PrintWriter writer = null;
        homeValutazioni = new HomePageValutazioni(writer);

        Stage stage = (Stage) buttonValutazioni.getScene().getWindow();
        homeValutazioni.start(stage);
    	
    }
    
    
}
    
  
