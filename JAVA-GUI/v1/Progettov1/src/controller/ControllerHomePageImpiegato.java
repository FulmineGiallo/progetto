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
	 Impiegato impiegato = null;
	 

	 @FXML
     private Button buttonValutazioni;
     @FXML
     private Button buttonCreaProgetto;
     @FXML
     private Label NomeImpiegatoLabel;
     @FXML
     private Label GradoImpiegatoLabel;

    public void inizializza(Impiegato impiegato)
    {
    	this.impiegato = impiegato;
    	NomeImpiegatoLabel.setText((impiegato.getNome() +" "+ impiegato.getCognome()).toUpperCase(Locale.ROOT));
        GradoImpiegatoLabel.setText(impiegato.getGrado());
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
        homeValutazioni = new HomePageValutazioni(writer, impiegato);

        Stage stage = (Stage) buttonValutazioni.getScene().getWindow();
        homeValutazioni.start(stage);
    	
    }
    
    
}
    
  
