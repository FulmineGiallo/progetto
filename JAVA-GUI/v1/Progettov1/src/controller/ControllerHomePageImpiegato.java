package controller;


import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import java.io.PrintWriter;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import model.Impiegato;
import view.HomePageProgetto;

public class ControllerHomePageImpiegato
{
	 HomePageProgetto homeProgetto;

	 @FXML
     private Button buttonValutazioni;
     @FXML
     private Button buttonCreaProgetto;
     @FXML
     private Label nomeImpiegato;


    public void inizializza(Impiegato impiegato)
    {
        nomeImpiegato.setText(impiegato.getNome());
    }

    @FXML
    public void CreateProject(ActionEvent actionEvent) throws Exception
    {
    	PrintWriter writer = null;
        homeProgetto = new HomePageProgetto(writer);

        Stage stage = (Stage) buttonCreaProgetto.getScene().getWindow();
        homeProgetto.start(stage);
    	
    }
}
    
  
