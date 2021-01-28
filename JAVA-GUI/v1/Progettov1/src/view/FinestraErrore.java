package view;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Impiegato;
import model.Progetto;
import controller.ControllerFinestraErrore;
import controller.ControllerHomePageProgetto;

public class FinestraErrore {
	
	private ControllerFinestraErrore 	controllerFinestraErrore;
	private String						messaggioErrore;
	private Exception					errore;
	private ControllerHomePageProgetto homePageProgetto;

	
	private Impiegato impiegato;
	private Progetto progetto;
	
	private String dettagliSkill;
	
	public FinestraErrore(String messaggioErrore, Exception errore) {
		this.messaggioErrore = messaggioErrore;
		this.errore = errore;
	}
	
	public FinestraErrore(Impiegato impiegato, Progetto progetto, ControllerHomePageProgetto homePageProgetto) {
		this.impiegato = impiegato;
		this.progetto = progetto;
		this.homePageProgetto=homePageProgetto;
	}
	
	public FinestraErrore(String dettagliSkill) {
		this.dettagliSkill = dettagliSkill;
	}
	
	public void startPopupErrore(Stage popup) throws Exception
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
	
	public void startDettagliSkill(Stage popup) throws Exception
    {
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("view/fxml/FinestraErrore.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        
        popup.hide();
        popup.setScene(scene);
        
        controllerFinestraErrore = loader.getController();
        controllerFinestraErrore.inizializza(dettagliSkill);
        
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
        controllerFinestraErrore.inizializza(impiegato, progetto, homePageProgetto);
        
        //window.setTitle("Errore!");
        popup.setTitle(popup.toString());
        popup.setResizable(false);
        popup.setMinWidth(400.0);
        popup.setMinHeight(200.0);
        popup.centerOnScreen();
        
        popup.show();
    }
	
	
}
