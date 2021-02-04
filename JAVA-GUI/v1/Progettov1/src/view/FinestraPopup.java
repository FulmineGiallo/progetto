package view; // magari effettuare overloading di un unico metodo start()

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Impiegato;
import model.Progetto;
import model.Riunione;
import model.Ruolo;
import model.Skill;
import utilities.MetodiComuni;
import controller.ControllerHomePageOrganizzatore;

import controller.ControllerHomePageProjectManager;

import controller.ControllerRicercaImpiegati;

import controller.ControllerFinestrePopup.*;

public class FinestraPopup {
	
	private MetodiComuni utils = new MetodiComuni();
	
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
	
	private ControllerFinestraConferma 						controllerFinestraConferma
															= new ControllerFinestraConferma();
	
	private ControllerFinestraAggiungiImpiegatoAlProgetto controllerAggiuntaImpiegatoProgetto
															= new ControllerFinestraAggiungiImpiegatoAlProgetto();
	
	private void caricaStage(Stage popup, ControllerFinestraPopup controller, String titoloFinestra) throws Exception{
		
		FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("view/fxml/FinestraPopup.fxml"));
        loader.setController(controller);
        Parent root = loader.load();
        Scene scene = new Scene(root);
        
        popup.hide();
        popup.setScene(scene);

        popup.setTitle(titoloFinestra);
        popup.setResizable(false);
        popup.setWidth(600.0);
        popup.setMinWidth(600.0);
        popup.setHeight(300.0);
        popup.setMinHeight(300.0);
        popup.centerOnScreen();
        
        popup.show();
	}

	//Inizializzazione della finestra popup di errore
	public void start(Stage popup, String messaggioErrore, Exception errore) throws Exception {
        caricaStage(popup, controllerErrore, "Errore!");
        controllerErrore.inizializza("ATTENZIONE!", messaggioErrore, utils.getMessaggioErrore(errore));
    }
	
	//Inizializzazione della finestra popup per la visualizzazione delle informazioni di una Skill
	public void start(Stage popup, Skill infoSkill) throws Exception {
        caricaStage(popup, controllerSkill, "Informazioni skill");
        
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
    }
	
	//Inizializzazione della finestra popup per confermare l'eliminazione di un impiegato dalla lista di partecipanti ad un progetto
	public void start(Stage popup, Impiegato impiegatoDaEliminare, Progetto progettoImpiegatoDaEliminare, ControllerHomePageProjectManager controllerHomePageProgetto) throws Exception {
        
		caricaStage(popup, controllerEliminazioneImpiegatoProgetto, "Attenzione");
		
        controllerEliminazioneImpiegatoProgetto.setImpiegato(impiegatoDaEliminare);
        controllerEliminazioneImpiegatoProgetto.setProgetto(progettoImpiegatoDaEliminare);
        controllerEliminazioneImpiegatoProgetto.setControllerHomePageProgetto(controllerHomePageProgetto);
        
        controllerEliminazioneImpiegatoProgetto.inizializza(null, "Sei sicuro di voler eliminare " + impiegatoDaEliminare.toString() 		   +
        								   " dal progetto "					+ progettoImpiegatoDaEliminare.getTitolo() +
        								   "?", null);
    }
	
	//Inizializzazione della finestra popup per confermare l'eliminazione di un impiegato dalla lista di partecipanti ad una riunione
	public void start(Stage popup, Impiegato impiegatoDaEliminare, Riunione riunioneImpiegatoDaEliminare, ControllerHomePageOrganizzatore controllerHomePageOrganizzatore) throws Exception {
		
		caricaStage(popup, controllerEliminazioneImpiegatoRiunione, "Attenzione");
		
        controllerEliminazioneImpiegatoRiunione.setImpiegato(impiegatoDaEliminare);
        controllerEliminazioneImpiegatoRiunione.setRiunione(riunioneImpiegatoDaEliminare);
        controllerEliminazioneImpiegatoRiunione.setController(controllerHomePageOrganizzatore);
        
        controllerEliminazioneImpiegatoRiunione.inizializza(null, "Sei sicuro di voler eliminare " + impiegatoDaEliminare.toString() 		  +
				   												  " dalla riunione "			   + riunioneImpiegatoDaEliminare.getTitolo() +
				   												  "?", null);
    }
	
	//Inizializzazione della finestra popup per confermare l'uscita dall'area personale
	public void start(Stage window, Stage popup) throws Exception {
		
		caricaStage(popup, controllerLogout, "Attenzione");
                
        controllerLogout.setStage(window, popup);
        controllerLogout.inizializza(null, "Sei sicuro di voler uscire?", null);
    }
	
	//Inizializzazione della finestra popup che conferma l'inserimento di un nuovo impiegato nel database
	public void start(Stage popup, Impiegato nuovoImpiegato) throws Exception {
		
		caricaStage(popup, controllerFinestraConferma, "Operazione completata con successo!");
		
        controllerFinestraConferma.inizializza("Perfetto!", "Il nuovo impiegato " + nuovoImpiegato.getCognome() +
        													" "					  + nuovoImpiegato.getNome()	+
        													" è stato inserito correttamente nel database.", null);

	}
	
	//Inizializzazione della finestra popup che conferma di aver eseguito correttamente un'operazione specificata in messaggioLabel
	public void start(Stage popup, String messaggioLabel) throws Exception {
		caricaStage(popup, controllerFinestraConferma, "Operazione completata con successo!");
		controllerFinestraConferma.inizializza("Perfetto!", messaggioLabel, null);
	}
	
	
	//Inizializzazione della finestra popup per confermare l'aggiunta di un impiegato in un progetto
	public void start(Stage popup, Impiegato impiegatoDaAggiungere, int idProgetto, ControllerRicercaImpiegati controllerRicercaImpiegati, Ruolo ruoloImpiegatoDaAggiungere) throws Exception {
		
		caricaStage(popup, controllerAggiuntaImpiegatoProgetto, "Attenzione");
		
		controllerAggiuntaImpiegatoProgetto.setImpiegato(impiegatoDaAggiungere);
		controllerAggiuntaImpiegatoProgetto.setIdProgetto(idProgetto);;
		controllerAggiuntaImpiegatoProgetto.setControllerRicercaImpiegati(controllerRicercaImpiegati);
		controllerAggiuntaImpiegatoProgetto.setRuolo(ruoloImpiegatoDaAggiungere);
        
        controllerAggiuntaImpiegatoProgetto.inizializza(null, "Sei sicuro di voler aggiungere " + impiegatoDaAggiungere.toString() 		  +
				   												  " nel progetto? ", null);
    }
}
