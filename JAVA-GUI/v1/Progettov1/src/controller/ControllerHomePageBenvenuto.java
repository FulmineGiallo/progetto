package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import java.io.PrintWriter;

import view.Login;
import view.FormRegistrazioneImpiegato;
import view.FinestraErrore;

public class ControllerHomePageBenvenuto
{
    @FXML private Button RegistrazioneButton;
    @FXML private Button LoginButton;

    FormRegistrazioneImpiegato 	registrazione;
    Login 						loginPage;
    FinestraErrore				finestraErrore;
    
    Stage window = null;
    
    public void recuperaStage(Stage window) {
    	this.window = window;
    }

    public void inviaCurriculum(ActionEvent actionEvent){
        try {
			registrazione = new FormRegistrazioneImpiegato();
			registrazione.start(window);
		} catch (Exception e) {
			e.printStackTrace();
			//inserire inizializzazione della finestraErrore
		}
    }

    public void effettua(ActionEvent actionEvent){
        try {
			loginPage = new Login();
			loginPage.start(window);
		} catch (Exception e) {
			e.printStackTrace();
			//inserire inizializzazione della finestraErrore
		}
    }

}