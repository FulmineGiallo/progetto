package view;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import controller.ControllerFinestraErrore;

public class FinestraErrore {
	
	private ControllerFinestraErrore 	controllerFinestraErrore;
	private String						messaggioErrore;
	private Exception					errore;
	
	public FinestraErrore(String messaggioErrore, Exception errore) {
		this.messaggioErrore = messaggioErrore;
		this.errore = errore;
	}

	public void start(Stage window) throws Exception
    {
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("view/fxml/FinestraErrore.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        
        window.hide();
        window.setScene(scene);
        
        controllerFinestraErrore = loader.getController();
        controllerFinestraErrore.inizializza(messaggioErrore, errore);
        
        window.setTitle("Errore!");
        window.setResizable(false);
        window.setMinWidth(400.0);
        window.setMinHeight(200.0);
        window.centerOnScreen();
        
        window.show();
    }
}
