package view;

import controller.ControllerRegistrazioneImpiegato;
import controller.ControllerRegistrazioneSkill;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Impiegato;

public class FormRegistrazioneSkill {
	
	ControllerRegistrazioneSkill controllerRegistrazioneSkill;
	ControllerRegistrazioneImpiegato controllerRegistrazioneImpiegato = null;
	
	public FormRegistrazioneSkill(ControllerRegistrazioneImpiegato controllerRegistrazioneImpiegato) {
		this.controllerRegistrazioneImpiegato = controllerRegistrazioneImpiegato;
	}
	
	public void start(Stage popup) throws Exception{
    	
    	FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("view/fxml/registrazioneImpiegato/InserimentoSkill/FormRegistrazioneSkill.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        
		popup.hide();
		popup.setScene(scene);

		controllerRegistrazioneSkill = loader.getController();
		controllerRegistrazioneSkill.setStage(popup);
		controllerRegistrazioneSkill.inizializza(controllerRegistrazioneImpiegato);
        
        //popup.setTitle("Registrazione");
        popup.setTitle(popup.toString());
        //popup.setMaximized(true);
        popup.setMinWidth(850.0);
        popup.setMinHeight(450.0);
        
        popup.show();
        popup.centerOnScreen();
	}
}
