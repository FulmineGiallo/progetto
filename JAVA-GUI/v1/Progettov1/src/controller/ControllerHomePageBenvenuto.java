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

    public void inviaCurriculum(ActionEvent actionEvent){
        try {
			PrintWriter writer = null;
			registrazione = new FormRegistrazioneImpiegato(writer);

			Stage stage = (Stage)RegistrazioneButton.getScene().getWindow();
			registrazione.start(stage);
		} catch (Exception e) {
			e.printStackTrace();
			//inserire inizializzazione della finestraErrore
		}
    }

    public void effettua(ActionEvent actionEvent) throws Exception {
        try {
			PrintWriter writer = null;
			loginPage = new Login(writer);

			Stage stage = (Stage)LoginButton.getScene().getWindow();
			loginPage.start(stage);
		} catch (Exception e) {
			e.printStackTrace();
			//inserire inizializzazione della finestraErrore
		}
    }

}