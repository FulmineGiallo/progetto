package view;

import controller.ControllerRegistrazioneSkill;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class FormRegistrazioneSkill {
	
	ControllerRegistrazioneSkill controllerRegistrazioneSkill;
	
	public FormRegistrazioneSkill() {
		
	}
	
	public void start(Stage popup) throws Exception{
    	
    	FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("view/fxml/registrazioneImpiegato/InserimentoSkill/FormRegistrazioneSkill.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        
		popup.hide();
		popup.setScene(scene);

		controllerRegistrazioneSkill = loader.getController();
		controllerRegistrazioneSkill.setStage(popup);
		controllerRegistrazioneSkill.inizializza();
        
        //popup.setTitle("Registrazione");
        popup.setTitle(popup.toString());
        //popup.setMaximized(true);
        popup.setMinWidth(850.0);
        popup.setMinHeight(450.0);
        
        popup.show();
        popup.centerOnScreen();
	}
}
