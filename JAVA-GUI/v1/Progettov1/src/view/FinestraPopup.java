package view; // magari effettuare overloading di un unico metodo start()

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
import controller.ControllerFinestrePopup.*;

public class FinestraPopup {
	
	private ControllerFinestraErrore						controllerErrore
															= new ControllerFinestraErrore();
	
	private ControllerFinestraInformazioniSkill 			controllerSkill 
															= new ControllerFinestraInformazioniSkill();
	
	private ControllerFinestraLogout						controllerLogout
															= new ControllerFinestraLogout();
	
	private ControllerFinestraEliminazioneImpiegatoProgetto controllerEliminazioneImpiegatoProgetto
															= new ControllerFinestraEliminazioneImpiegatoProgetto();
	
	private ControllerFinestraEliminazioneImpiegatoRiunione controllerEliminazioneImpiegatoRiunione
															= new ControllerFinestraEliminazioneImpiegatoRiunione();
	
	private ControllerFinestraConfermaRegistrazione 		controllerRegistrazione
															= new ControllerFinestraConfermaRegistrazione();

	public void startPopupErrore(Stage popup,
								 String messaggioErrore,
								 Exception errore) throws Exception
    {
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("view/fxml/FinestraPopup.fxml"));
        loader.setController(controllerErrore);
        Parent root = loader.load();
        Scene scene = new Scene(root);
        
        popup.hide();
        popup.setScene(scene);
        
        controllerErrore.inizializza(messaggioErrore, errore);
        
        popup.setTitle("Errore!");
        popup.setResizable(false);
        popup.setWidth(600.0);
        popup.setMinWidth(600.0);
        popup.setHeight(300.0);
        popup.setMinHeight(300.0);
        popup.centerOnScreen();
        
        popup.show();
    }
	
	public void startDettagliSkill(Stage popup,
								   Skill infoSkill) throws Exception
    {
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("view/fxml/FinestraPopup.fxml"));
        loader.setController(controllerSkill);
        
        Parent root = loader.load();
        Scene scene = new Scene(root);
        
        popup.hide();
        popup.setScene(scene);
        
        if(infoSkill.getTipoSkill().equals("Soft-Skill")) {
        	controllerSkill.inizializza(infoSkill.getTipoSkill(), null,	"Descrizione: " + infoSkill.getDescrizione());
		} else {
			if (infoSkill.getDescrizione() == null) {
				controllerSkill.inizializza(infoSkill.getTipoSkill(), null, "Titolo del certificato: " + infoSkill.getTitolo() 	+
																  			"\nDescrizione: " 			 + "Nessuna descrizione"	+
																  			"\nData di certificazione: " + infoSkill.getDataCertificazione());
			} else {
				controllerSkill.inizializza(infoSkill.getTipoSkill(), null, "Titolo del certificato: " + infoSkill.getTitolo() 	  +
						  										  			"\nDescrizione: " 			 + infoSkill.getDescrizione() +
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
	
	public void startEliminazioneImpiegatoDaProgetto(Stage popup,
													 Impiegato impiegatoDaEliminare,
													 Progetto progettoImpiegatoDaEliminare,
													 ControllerHomePageProgetto controllerHomePageProgetto) throws Exception {
        
		FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("view/fxml/FinestraPopup.fxml"));
        loader.setController(controllerEliminazioneImpiegatoProgetto);
        Parent root = loader.load();
        Scene scene = new Scene(root);
        
        popup.hide();
        popup.setScene(scene);
        
        controllerEliminazioneImpiegatoProgetto.setImpiegato(impiegatoDaEliminare);
        controllerEliminazioneImpiegatoProgetto.setProgetto(progettoImpiegatoDaEliminare);
        controllerEliminazioneImpiegatoProgetto.setControllerHomePageProgetto(controllerHomePageProgetto);
        
        controllerErrore.inizializza(null, "Sei sicuro di voler eliminare " + impiegatoDaEliminare.toString() 		   +
        								   " dal progetto "					+ progettoImpiegatoDaEliminare.getTitolo() +
        								   "?", null);
        
        popup.setTitle("Attenzione");
        popup.setResizable(false);
        popup.setWidth(600.0);
        popup.setMinWidth(600.0);
        popup.setHeight(300.0);
        popup.setMinHeight(300.0);
        popup.centerOnScreen();
        
        popup.show();
    }
	
	public void startEliminazioneImpiegatoDaRiunione(Stage popup,
							  						 Impiegato impiegatoDaEliminare,
							  						 Riunione riunioneImpiegatoDaEliminare,
							  						 ControllerHomePageOrganizzatore controllerHomePageOrganizzatore) throws Exception {
        
		FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("view/fxml/FinestraPopup.fxml"));
        loader.setController(controllerEliminazioneImpiegatoRiunione);
        Parent root = loader.load();
        Scene scene = new Scene(root);
        
        popup.hide();
        popup.setScene(scene);
        
        controllerEliminazioneImpiegatoRiunione.setImpiegato(impiegatoDaEliminare);
        controllerEliminazioneImpiegatoRiunione.setRiunione(riunioneImpiegatoDaEliminare);
        controllerEliminazioneImpiegatoRiunione.setController(controllerHomePageOrganizzatore);
        
        controllerEliminazioneImpiegatoRiunione.inizializza(null, "Sei sicuro di voler eliminare " + impiegatoDaEliminare.toString() 		  +
				   												  " dalla riunione "			   + riunioneImpiegatoDaEliminare.getTitolo() +
				   												  "?", null);
        
        popup.setTitle("Attenzione");
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
        loader.setController(controllerLogout);
        Parent root = loader.load();
        Scene scene = new Scene(root);
        
        popup.hide();
        popup.setScene(scene);
        
        controllerLogout.setStage(window, popup);
        controllerLogout.inizializza(null, "Sei sicuro di voler uscire?", null);
        
        popup.setTitle("Logout");
        popup.setResizable(false);
        popup.setWidth(600.0);
        popup.setMinWidth(600.0);
        popup.setHeight(300.0);
        popup.setMinHeight(300.0);
        popup.centerOnScreen();
        
        popup.show();
    }
	
	public void startConfermaRegistrazione (Stage popup,
											Impiegato nuovoImpiegato) throws Exception {
		
		FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("view/fxml/FinestraPopup.fxml"));
        loader.setController(controllerRegistrazione);
        Parent root = loader.load();
        Scene scene = new Scene(root);
        
        popup.hide();
        popup.setScene(scene);
        
        controllerRegistrazione.inizializza("Perfetto!", "Il nuovo impiegato " + nuovoImpiegato.getCognome()
        												 + " "				   + nuovoImpiegato.getNome()
        												 + " è stato inserito correttamente nel database.", null);
        
        popup.setTitle("Operazione completata con successo!");
        popup.setResizable(false);
        popup.setWidth(600.0);
        popup.setMinWidth(600.0);
        popup.setHeight(300.0);
        popup.setMinHeight(300.0);
        popup.centerOnScreen();
        
        popup.show();
	}
}
