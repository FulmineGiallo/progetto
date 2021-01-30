package view; // >> stesso motivo del commento in ControllerFinestraErrore

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Impiegato;
import model.Progetto;
import model.Riunione;
import model.Skill;
import controller.ControllerHomePageOrganizzatore;
import controller.ControllerHomePageProgetto;
import controller.ControllerFinestrePopup.ControllerFinestraErrore;
import controller.ControllerFinestrePopup.ControllerFinestraInformazioniSkill;

public class FinestraPopup {
	
	private ControllerFinestraErrore 		controllerErrore = new ControllerFinestraErrore();
	private String							messaggioErrore;
	private Exception						errore;
	
	private ControllerFinestraInformazioniSkill controllerSkill = new ControllerFinestraInformazioniSkill();
	private Skill								infoSkill;
	
	private ControllerHomePageProgetto		controllerProgetto;
	private ControllerHomePageOrganizzatore controllerRiunione;
	
	private Riunione riunione;
	
	private Impiegato impiegato;
	private Impiegato impiegatoRiunione;
	
	private Progetto progetto;
	
	public FinestraPopup(String messaggioErrore, Exception errore) {
		this.messaggioErrore = messaggioErrore;
		this.errore = errore;
	}
	
	public FinestraPopup(Impiegato impiegato, Progetto progetto, ControllerHomePageProgetto homePageProgetto) {
		this.impiegato = impiegato;
		this.progetto = progetto;
		this.controllerProgetto=homePageProgetto;
	}

	public FinestraPopup(Impiegato impiegatoRiunione, Riunione riunione, ControllerHomePageOrganizzatore homePageOrganizzatore) {
		this.impiegatoRiunione = impiegatoRiunione;
		this.riunione = riunione;
		this.controllerRiunione = homePageOrganizzatore;
	}

	public FinestraPopup(Skill infoSkill) {
		this.infoSkill = infoSkill;
	}
	
	public void startPopupErrore(Stage popup) throws Exception
    {
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("view/fxml/FinestraPopup.fxml"));
        loader.setController(controllerErrore);
        Parent root = loader.load();
        Scene scene = new Scene(root);
        
        popup.hide();
        popup.setScene(scene);
        
        controllerErrore.inizializza(messaggioErrore, errore);
        
        //window.setTitle("Errore!");
        popup.setTitle(popup.toString());
        popup.setResizable(false);
        popup.setWidth(600.0);
        popup.setMinWidth(600.0);
        popup.setHeight(300.0);
        popup.setMinHeight(300.0);
        popup.centerOnScreen();
        
        popup.show();
    }
	
	public void startDettagliSkill(Stage popup) throws Exception
    {
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("view/fxml/FinestraPopup.fxml"));
        loader.setController(controllerSkill);
        
        Parent root = loader.load();
        Scene scene = new Scene(root);
        
        popup.hide();
        popup.setScene(scene);
        
        if(infoSkill.getTipoSkill().equals("Soft-Skill")) {
        	controllerSkill.inizializza(infoSkill.toString(), "Tipologia: "		+ infoSkill.getTipoSkill() +
															  "\nDescrizione: " + infoSkill.getDescrizione());
		} else {
			if (infoSkill.getDescrizione() == null) {
				controllerSkill.inizializza(infoSkill.toString(), "Tipologia: " 			   + infoSkill.getTipoSkill() 	+
																  "\nTitolo del certificato: " + infoSkill.getTitolo() 		+
																  "\nDescrizione: " 		   + "Nessuna descrizione"		+
																  "\nData di certificazione: " + infoSkill.getDataCertificazione());
			} else {
				controllerSkill.inizializza(infoSkill.toString(), "Tipologia: " 			   + infoSkill.getTipoSkill() 	+
						  										  "\nTitolo del certificato: " + infoSkill.getTitolo() 		+
						  										  "\nDescrizione: " 		   + infoSkill.getDescrizione() +
						  										  "\nData di certificazione: " + infoSkill.getDataCertificazione());
			}
		}
        
        popup.setTitle(popup.toString());
        popup.setResizable(false);
        popup.setWidth(600.0);
        popup.setMinWidth(600.0);
        popup.setHeight(300.0);
        popup.setMinHeight(300.0);
        popup.centerOnScreen();
        
        popup.show();
    }
	
	public void startProgetto(Stage popup) throws Exception
    {
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("view/fxml/FinestraPopup.fxml"));
        loader.setController(controllerErrore);
        Parent root = loader.load();
        Scene scene = new Scene(root);
        
        popup.hide();
        popup.setScene(scene);
        
        controllerErrore.inizializza(impiegato, progetto, controllerProgetto);
        
        //window.setTitle("Errore!");
        popup.setTitle(popup.toString());
        popup.setResizable(false);
        popup.setWidth(600.0);
        popup.setMinWidth(600.0);
        popup.setHeight(300.0);
        popup.setMinHeight(300.0);
        popup.centerOnScreen();
        
        popup.show();
    }
	
	public void startRiunione(Stage popup) throws Exception
    {
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("view/fxml/FinestraPopup.fxml"));
        loader.setController(controllerErrore);
        Parent root = loader.load();
        Scene scene = new Scene(root);
        
        popup.hide();
        popup.setScene(scene);
        
        controllerErrore.inizializza(impiegatoRiunione, riunione, controllerRiunione);
        
        //window.setTitle("Errore!");
        popup.setTitle(popup.toString());
        popup.setResizable(false);
        popup.setWidth(600.0);
        popup.setMinWidth(600.0);
        popup.setHeight(300.0);
        popup.setMinHeight(300.0);
        popup.centerOnScreen();
        
        popup.show();
    }
	
	public void startLogout(Stage window, Stage popup) throws Exception
    {
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("view/fxml/FinestraPopup.fxml"));
        loader.setController(controllerErrore);
        Parent root = loader.load();
        Scene scene = new Scene(root);
        
        popup.hide();
        popup.setScene(scene);
        
        controllerErrore.inizializza(messaggioErrore, window, popup);
        
        //window.setTitle("Errore!");
        popup.setTitle(popup.toString());
        popup.setResizable(false);
        popup.setWidth(600.0);
        popup.setMinWidth(600.0);
        popup.setHeight(300.0);
        popup.setMinHeight(300.0);
        popup.centerOnScreen();
        
        popup.show();
    }
}
