package view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import controller.ControllerRegistrazioneProgetto;
import model.Impiegato;

public class FormRegistrazioneProgetto
{
	ControllerRegistrazioneProgetto controllerRegistrazioneProgetto;

    public void start(Stage window, Stage popup, Impiegato projectManager) throws Exception {
    	
    	FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("view/fxml/registrazioneProgetto/FormRegistrazioneProgetto.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        
		window.hide();
		window.setScene(scene);

        controllerRegistrazioneProgetto = loader.getController();
        controllerRegistrazioneProgetto.setStage(window, popup);
        controllerRegistrazioneProgetto.inizializza(projectManager);
        
        window.setTitle("Registrazione nuovo progetto");
        window.setFullScreen(true);
		window.setFullScreenExitHint("");
        window.setMinWidth(850.0);
        window.setMinHeight(650.0);
        window.setMaximized(true);
        window.centerOnScreen();
        
        window.show();
    }
}
