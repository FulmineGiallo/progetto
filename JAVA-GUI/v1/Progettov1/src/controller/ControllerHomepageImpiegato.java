package controller;

<<<<<<< HEAD
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import java.io.PrintWriter;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import view.HomePageProgetto;

public class ControllerHomepageImpiegato {

	 HomePageProgetto homeProgetto;
    
	 @FXML
    private Button buttonValutazioni;

    @FXML
    private Button buttonCreaProgetto;

    @FXML
    private Label nomeImpiegato;

    
    
    @FXML
   public void CreateProject(ActionEvent actionEvent) throws Exception {

    	PrintWriter writer = null;
        homeProgetto = new HomePageProgetto(writer);

        Stage stage = (Stage) buttonCreaProgetto.getScene().getWindow();
        homeProgetto.start(stage);
    	
    }
    
    
    
    
=======
public class ControllerHomepageImpiegato
{
    ControllerLogin informazioniLogin;

>>>>>>> 762c38d80038bca86de03aa0182385f02331fdf6
}
