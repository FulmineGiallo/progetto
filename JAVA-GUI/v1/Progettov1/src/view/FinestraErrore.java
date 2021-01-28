package view;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Impiegato;
import model.Progetto;
import controller.ControllerFinestraErrore;

public class FinestraErrore {
	
	private ControllerFinestraErrore 	controllerFinestraErrore;
	private String						messaggioErrore;
	private Exception					errore;
	
	private Impiegato impiegato;
	private Progetto progetto;
	
	public FinestraErrore(String messaggioErrore, Exception errore) {
		this.messaggioErrore = messaggioErrore;
		this.errore = errore;
	}
	
	public FinestraErrore(Impiegato impiegato, Progetto progetto) {
		this.impiegato = impiegato;
		this.progetto = progetto;
	}
	
	

	public void start(Stage popup) throws Exception
    {
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("view/fxml/FinestraErrore.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        
        popup.hide();
        popup.setScene(scene);
        
        controllerFinestraErrore = loader.getController();
        controllerFinestraErrore.inizializza(messaggioErrore, errore);
        
        //window.setTitle("Errore!");
        popup.setTitle(popup.toString());
        popup.setResizable(false);
        popup.setMinWidth(400.0);
        popup.setMinHeight(200.0);
        popup.centerOnScreen();
        
        popup.show();
    }
	
	
	
	public void startProgetto(Stage popup) throws Exception
    {
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("view/fxml/FinestraErrore.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        
        popup.hide();
        popup.setScene(scene);
        
        controllerFinestraErrore = loader.getController();
        controllerFinestraErrore.inizializzaEliminazioneImpiegato(impiegato, progetto);
        
        //window.setTitle("Errore!");
        popup.setTitle(popup.toString());
        popup.setResizable(false);
        popup.setMinWidth(400.0);
        popup.setMinHeight(200.0);
        popup.centerOnScreen();
        
        popup.show();
    }
	
	
}
