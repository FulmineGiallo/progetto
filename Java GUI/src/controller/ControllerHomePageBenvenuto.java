package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import java.io.PrintWriter;

import view.Login;
import view.FinestraPopup;
import view.FormRegistrazioneImpiegato;

public class ControllerHomePageBenvenuto
{
    @FXML private Button RegistrazioneButton;
    @FXML private Button LoginButton;

    FormRegistrazioneImpiegato 	registrazione;
    Login 						loginPage;
    FinestraPopup				finestraErrore;
    
    private Stage window;
    private Stage popup;
    
    public void setStage(Stage window, Stage popup) {
    	this.window = window;
    	this.popup = popup;
    }

    public void inviaCurriculum(ActionEvent actionEvent){
        try {
			registrazione = new FormRegistrazioneImpiegato();
			registrazione.start(window, popup);
		} catch (Exception e) {
			e.printStackTrace();
			//inserire inizializzazione della finestraErrore
		}
    }

    public void effettua(ActionEvent actionEvent){
        try {
			loginPage = new Login();
			loginPage.start(window, popup);
		} catch (Exception e) {
			e.printStackTrace();
			//inserire inizializzazione della finestraErrore
		}
    }

}