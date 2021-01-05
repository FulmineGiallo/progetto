package controller;

import java.io.PrintWriter;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import view.HomePageBenvenuto;
import view.HomePageImpiegato;

public class ControllerHomePageProgetto {

	HomePageImpiegato homeImpiegato;
	
    @FXML
    private Button buttonComunicaRiunione;

    @FXML
    private Button buttonRimuovi;

    @FXML
    private Button buttonAggiungi;

    @FXML
    private Button buttonHome;

    @FXML
    void backHomePageImpiegato(ActionEvent event) throws Exception {

            PrintWriter writer = null;
            homeImpiegato = new HomePageImpiegato(writer);

            Stage stage = (Stage) buttonHome.getScene().getWindow();
            homeImpiegato.start(stage);
       
    	
    }

}
