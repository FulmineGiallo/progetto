package view;


import controller.ControllerFinestraRimuoviImpiegatoDalProgetto;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Impiegato;
import model.Progetto;

public class FinestraRimuoviImpiegatoDalProgetto {

	
	private ControllerFinestraRimuoviImpiegatoDalProgetto 	controllerImpiegatoProgetto;
	private Impiegato impiegato;
	private Progetto progetto;
	
	
	public FinestraRimuoviImpiegatoDalProgetto(Impiegato impiegato, Progetto progetto) {
		this.impiegato=impiegato;
		this.progetto=progetto;
	}

	public void start(Stage popup) throws Exception
    {
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("view/fxml/confermaEliminazioneImpiegato.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        
        popup.hide();
        popup.setScene(scene);
        
        controllerImpiegatoProgetto = loader.getController();
        controllerImpiegatoProgetto.inizializza(impiegato, progetto);
        

        popup.setTitle(popup.toString());
        popup.setResizable(false);
        popup.setMinWidth(400.0);
        popup.setMinHeight(200.0);
        popup.centerOnScreen();
        
        popup.show();
    }
}
