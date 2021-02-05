package controller;

import java.sql.Connection;
import java.sql.SQLException;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import model.Connection.DBConnection;
import model.Dao.ProgettoDao;
import model.Dao.RiunioneDao;
import model.DaoInterface.ProgettoDaoInterface;
import model.DaoInterface.RiunioneDaoInterface;
import utilities.MetodiComuni;
import model.Ambito;
import model.Impiegato;
import model.Progetto;
import model.Riunione;
import model.RiunioneFisica;
import model.RiunioneTelematica;
import view.FinestraPopup;
import view.FormRegistrazioneProgetto;
import view.HomePageBenvenuto;
import view.HomePageOrganizzatore;
import view.HomePageProjectManager;
import view.HomePageValutazioni;

public class ControllerHomePageImpiegato {

    @FXML  private AnchorPane 			HomePageImpiegato;
    
    @FXML  private VBox 				ImpiegatoBox;
    @FXML  private Label 				NomeImpiegatoLabel;
    @FXML  private Label 				GradoImpiegatoLabel;
    @FXML  private Label 				TitoloSalarioLabel;
    @FXML  private Label 				SalarioLabel;
    
    @FXML  private HBox 				ToolBar;
    @FXML  private Button 				ValutazioniButton;
    @FXML  private Button 				NuovoProgettoButton;
    @FXML  private Button 				LogoutButton;
    
    @FXML  private AnchorPane 			ElencoProgettiRiunioniBox;
    @FXML  private GridPane 			SchedeBox;
    @FXML  private HBox 				SchedaListaProgetti;
    @FXML  private Label 				ListaProgettiLabel;
    @FXML  private HBox 				SchedaListaRiunioni;
    @FXML  private Label 				ListaRiunioniLabel;
    
    @FXML  private AnchorPane 			ListeBox;
    @FXML  private ListView<Progetto> 	ListaProgettiLV;
    @FXML  private ListView<Riunione> 	ListaRiunioniLV;
    
    @FXML  private AnchorPane 			IstruzioniBox;
    @FXML  private Label 				IstruzioniLabel;
    
    @FXML  private AnchorPane 			DescrizioneProgettoBox;
    @FXML  private AnchorPane 			DescrizioneProgettoPane;
    
    @FXML  private Label 				ProjectManagerProgettoLabel;
    @FXML  private TextField 			ProjectManagerTF;
    
    @FXML  private HBox 				TitoloProgettoBox;
    @FXML  private Label 				TitoloProgettoLabel;
    @FXML  private TextField 			TitoloProgettoTF;
    
    @FXML  private Label 				DescrizioneProgettoLabel;
    @FXML  private TextArea 			DescrizioneProgettoTA;
    
    @FXML  private GridPane 			DataBox;
    
    @FXML  private HBox 				DataDiInizioBox;
    @FXML  private Label 				DataDiInizioProgettoLabel;
    @FXML  private TextField 			DataDiInizioProgettoTF;
    
    @FXML  private HBox 				DataDiFineBox;
    @FXML  private Label 				DataDiFineProgettoLabel;
    @FXML  private TextField 			DataDiFineProgettoTF;
    
    @FXML  private HBox 				DataDiScadenzaBox;
    @FXML  private Label 				DataDiScadenzaProgettoLabel;
    @FXML  private TextField 			DataDiScadenzaProgettoTF;
    
    @FXML  private HBox 				TipologiaProgettoBox;
    @FXML  private Label 				TipologiaProgettoLabel;
    @FXML  private TextField 			TipologiaProgettoTF;
    
    @FXML  private HBox 				AmbitiProgettoBox;
    @FXML  private Label 				AmbitiProgettoLabel;
    @FXML  private TextArea 			AmbitiProgettoTA;
    
    @FXML  private VBox 				NoteProgettoBox;
    @FXML  private Label 				NoteProgettoLabel;
    @FXML  private TextArea 			NoteProgettoTA;
    
    @FXML  private AnchorPane			ProjectManagerBox;
    @FXML  private Button 				GestioneProgettoButton;
    @FXML  private Button 				ModificaProgettoButton;
    @FXML  private Button 				SalvaModificheProgetto;
    
    @FXML  private AnchorPane 			DescrizioneRiunioneBox;
    @FXML  private AnchorPane 			DescrizioneRiunionePane;

    @FXML  private Label 				OrganizzatoreRiunioneLabel;
    @FXML  private TextField 			OrganizzatoreRiunioneTF;
    
    @FXML  private HBox 				TitoloRiunioneBox;
    @FXML  private Label 				TitoloRiunione;
    @FXML  private TextField 			TitoloRiunioneTF;
    
    @FXML  private Label 				DescrizioneRiunioneLabel;
    @FXML  private TextArea 			DescrizioneRiunioneTA;
    @FXML  private GridPane 			OrarioRiunioneBox;
    
    @FXML  private HBox 				OrarioDiInizioRiunioneBox;
    @FXML  private Label 				OrarioDiInizioRiunioneLabel;
    @FXML  private TextField 			OrarioDiInizioRiunioneTF;
    
    @FXML  private HBox 				OrarioDiFineRiunioneBox;
    @FXML  private Label 				OrarioDiFineRiunioneLabel;
    @FXML  private TextField 			OrarioDiFineRiunioneTF;
    
    @FXML  private HBox 				TipologiaRiunioneBox;
    @FXML  private Label 				TipologiaRiunioneLabel;
    @FXML  private TextField 			TipologiaRiunioneTF;
    
    @FXML  private VBox 				CampiRiunioneFisica;
    
    @FXML  private HBox 				SedeBox;
    @FXML  private Label 				SedeLabel;
    @FXML  private TextField 			SedeTF;
    
    @FXML  private HBox 				NomeStanzaBox;
    @FXML  private Label 				NomeStanzaLabel;
    @FXML  private TextField 			NomeStanzaTF;
    
    @FXML  private HBox 				PianoStanzaBox;
    @FXML  private Label 				PianoStanzaLabel;
    @FXML  private TextField 			PianoStanzaTF;
    
    @FXML  private VBox 				CampiRiunioneTelematica;
    
    @FXML  private HBox 				NomePiattaformaBox;
    @FXML  private Label 				NomePiattaformaLabel;
    @FXML  private TextField 			NomePiattaformaTF;
    
    @FXML  private HBox 				CodiceAccessoBox;
    @FXML  private Label 				CodiceAccessoLabel;
    @FXML  private TextField 			CodiceAccessoTF;
    
    @FXML  private VBox 				NoteRiunioneBox;
    @FXML  private Label 				NoteRiunioneLabel;
    @FXML  private TextArea 			NoteRiunioneTA;
    
    @FXML  private AnchorPane 			PartecipanteBox;
    @FXML  private Button 				PresenzaButton;
    @FXML  private Button 				AssenzaButton;
    
    @FXML  private AnchorPane 			OrganizzatoreBox;
    @FXML  private Button 				GestioneRiunioneButton;
    @FXML  private Button 				ModificaRiunioneButton;
    @FXML  private Button 				SalvaModificheRiunioneButton;
    
    private final String istruzioniProgetto = "Clicca un progetto\r\n" + "per visualizzarne le informazioni";
    private Progetto progettoIniziale = new Progetto("Non ci sono ancora progetti");
    
    private final String istruzioniRiunione = "Clicca una riunione\r\n" + "per visualizzarne le informazioni";
    private RiunioneFisica riunioneIniziale = new RiunioneFisica(null, "Non ci sono ancora riunioni", null, null, "", "", "");
	
    private FinestraPopup finestraDomanda;
	private FinestraPopup finestraConferma;
	private FinestraPopup finestraErrore;
	
	private HomePageBenvenuto 					homePageBenvenuto;
	private HomePageValutazioni 				homePageValutazioni;
	private FormRegistrazioneProgetto 			registrazioneProgetto;
	private Stage 								window;
	private Stage								popup;
	
	private int updateEffettuato;

	private Impiegato impiegato;
	private MetodiComuni utils = new MetodiComuni();

    ObservableList<Progetto> listaProgetti = FXCollections.observableArrayList();
    ObservableList<Riunione> listaRiunioni = FXCollections.observableArrayList();
    
    Connection connection;
    DBConnection dbConnection;
    ProgettoDaoInterface progettoDao;
    RiunioneDaoInterface riunioneDao;
    
    public void setStage(Stage window, Stage popup)
    {
    	this.window = window;
    	this.popup = popup;
    }
    
    public void inizializza(Impiegato impiegato) throws SQLException {
    	
    	try {
            dbConnection = new DBConnection();
            connection = dbConnection.getConnection();
            
            progettoDao = new ProgettoDao(connection);
            listaProgetti.addAll(progettoDao.getProgettiImpiegato(impiegato));
            
            riunioneDao = new RiunioneDao(connection);
    		listaRiunioni.addAll(riunioneDao.getRiunioniImpiegato(impiegato));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    	
    	this.impiegato = impiegato;
    	NomeImpiegatoLabel.setText(impiegato.toString().toUpperCase());
        GradoImpiegatoLabel.setText(impiegato.getGrado());
        
        if (listaProgetti.isEmpty())
			ListaProgettiLV.getItems().add(progettoIniziale);
		else
			ListaProgettiLV.setItems(listaProgetti);
		
        if (listaRiunioni.isEmpty())
			ListaRiunioniLV.getItems().add(riunioneIniziale);
		else
			ListaRiunioniLV.setItems(listaRiunioni);
        
		IstruzioniBox.setVisible(true);
        DescrizioneProgettoBox.setVisible(false);
        SalvaModificheRiunioneButton.setVisible(false);
        SalarioLabel.setText("Calcolare");
        /*Richiamare Dao Salario */
        
        ListaRiunioniLabel.setStyle("-fx-text-fill: derive(white, -50%);");
    }

    /*Metodo che gestisce le modifiche di DescrizioneProgettoBox */
    private void gestisciProgettoBox(boolean state)
    {
        DescrizioneProgettoTA.setEditable(state);
        DataDiInizioProgettoTF.setEditable(state);
        DataDiFineProgettoTF.setEditable(state);
        DataDiScadenzaProgettoTF.setEditable(state);
    }

    @FXML
    public void CreaProgetto(ActionEvent actionEvent) throws Exception {
        registrazioneProgetto = new FormRegistrazioneProgetto();
        registrazioneProgetto.start(window, popup, impiegato);
    }
    
    @FXML
    public void VisualizzaValutazioni(ActionEvent event) throws Exception {
        homePageValutazioni = new HomePageValutazioni(impiegato);
        homePageValutazioni.start(window, popup);
    }
    
    @FXML
    public void EffettuaLogout(ActionEvent event) throws Exception {
    	finestraDomanda = new FinestraPopup();
    	finestraDomanda.start(window, popup, connection);
    }

    @FXML void accettaInvito(ActionEvent event) {
    	try {
			if(riunioneDao.isInvitato(impiegato, ListaRiunioniLV.getSelectionModel().getSelectedItem())) {
				
				int update;
				update=riunioneDao.UpdatePresenza(impiegato, ListaRiunioniLV.getSelectionModel().getSelectedItem());
				
				if(update!=0)
					System.out.println("presenza salvata");
					finestraConferma = new FinestraPopup();
					try {
						finestraConferma.start(popup, "Presenza registrata correttamente");
					} catch (Exception e) {
						e.printStackTrace();
					}
					
			}else {
				System.out.println("errore presenza");
				finestraErrore = new FinestraPopup();
				try {
					finestraErrore.start(popup, "Impossibile registrare la presenza", new Exception());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }
    
    @FXML void rifutaInvito(ActionEvent event) {
    	try {
			if(riunioneDao.isInvitato(impiegato, ListaRiunioniLV.getSelectionModel().getSelectedItem())) {
				
				int update;
				update=riunioneDao.UpdateAssenza(impiegato, ListaRiunioniLV.getSelectionModel().getSelectedItem());
				
				if(update!=0)
					System.out.println("assenza salvata");
				finestraConferma = new FinestraPopup();
				try {
					finestraConferma.start(popup, "Assenza registrata correttamente");
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			} else {
				System.out.println("errore assenza");
				finestraErrore = new FinestraPopup();
				try {
					finestraErrore.start(popup, "Impossibile registrare l'assenza", new Exception());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }

    //Se GestioneProgettoButton viene cliccato
    @FXML void gestisciProgetto(ActionEvent event) {
        HomePageProjectManager homeProjectManager = new HomePageProjectManager(impiegato, ListaProgettiLV.getSelectionModel().getSelectedItem());
        try {
            homeProjectManager.start(window, popup);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Se GestioneRiunioneButton viene cliccato
    @FXML void gestisciRiunione(ActionEvent event) {
    	HomePageOrganizzatore homeOrganizzatore = new HomePageOrganizzatore(impiegato, ListaRiunioniLV.getSelectionModel().getSelectedItem());
        try {
            homeOrganizzatore.start(window, popup);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Se il project manager vuole modificare le informazioni del progetto
    @FXML
	void modificaInformazioniProgetto(ActionEvent event)
	{
        gestisciProgettoBox(true);
        SalvaModificheProgetto.setVisible(true);
    }

    @FXML void salvaModificheProgetto(ActionEvent event)
	{
    	gestisciProgettoBox(false);
        LocalDate datainizio;
        LocalDate dataFine;
        LocalDate dataScadenza;
        
        try
		{
            ListaProgettiLV.getSelectionModel().getSelectedItem().setDescrizione(DescrizioneProgettoTA.getText());
            datainizio = LocalDate.parse(DataDiInizioProgettoTF.getText());
            dataFine = LocalDate.parse(DataDiFineProgettoTF.getText());
            dataScadenza = LocalDate.parse(DataDiScadenzaProgettoTF.getText());

            ListaProgettiLV.getSelectionModel().getSelectedItem().setDataInizio(datainizio);
            ListaProgettiLV.getSelectionModel().getSelectedItem().setDataFine(dataFine);
            ListaProgettiLV.getSelectionModel().getSelectedItem().setScadenza(dataScadenza);


            updateEffettuato = progettoDao.updateInfoProgetto(ListaProgettiLV.getSelectionModel().getSelectedItem());
			SalvaModificheProgetto.setVisible(false); //una volta premuoto salva, il bottone scompare.

        }
        catch (SQLException throwables)
		{
            throwables.printStackTrace();
        }

    }
    void gestioneRiunioneBox(boolean state)
    {
        DescrizioneRiunioneTA.setEditable(state);
        OrarioDiInizioRiunioneTF.setEditable(state);
        OrarioDiFineRiunioneTF.setEditable(state);
        TitoloRiunioneTF.setEditable(state);
        TipologiaRiunioneTF.setEditable(state);
        SedeTF.setEditable(state);
        NomeStanzaTF.setEditable(state);
        PianoStanzaTF.setEditable(state);
        NoteRiunioneTA.setEditable(state);
    }

    @FXML void modificaInformazioniRiunione(ActionEvent event)
    {
        gestioneRiunioneBox(true);
        SalvaModificheRiunioneButton.setVisible(true);
    }
    
    @FXML private void salvaModificheRiunione(ActionEvent event) throws SQLException
    {
        gestioneRiunioneBox(false);
        int update = 0;
        Riunione riunioneSelezionata = ListaRiunioniLV.getSelectionModel().getSelectedItem();
        riunioneSelezionata.setDescrizione(DescrizioneRiunioneTA.getText());
        riunioneSelezionata.setTitolo(TitoloRiunioneTF.getText());
        riunioneSelezionata.setNote(NoteRiunioneTA.getText());
        try
        {
            update = riunioneDao.updateRiunione(riunioneSelezionata);
            System.out.println(update);
        }catch (SQLException error)
        {
            error.printStackTrace();
        }
        SalvaModificheRiunioneButton.setVisible(false);
    }
    
    @FXML private void visualizzaProgetti(MouseEvent event)
    {
    	if (!ListaProgettiLV.isVisible()) {
			ListaRiunioniLabel.setStyle("-fx-text-fill: derive(white, -50%);");
			SchedaListaRiunioni.setStyle("-fx-border-width: 0 0 5 0;");
			
			ListaProgettiLabel.setStyle("-fx-text-fill: white;");
			SchedaListaProgetti.setStyle("-fx-border-width: 5 5 1 5");
			
			ListaRiunioniLV.setVisible(false);
			ListaProgettiLV.setVisible(true);
			
			DescrizioneRiunioneBox.setVisible(false);
			DescrizioneProgettoBox.setVisible(false);
			IstruzioniBox.setVisible(true);
			
			IstruzioniLabel.setText(istruzioniProgetto);
		}
    }

    @FXML void visualizzaInformazioniProgetto(MouseEvent event)
    {
        if (!ListaProgettiLV.getItems().contains(progettoIniziale)) {
        	
			IstruzioniBox		  .setVisible(false);
			DescrizioneRiunioneBox.setVisible(false);
			DescrizioneProgettoBox.setVisible(true);
			
			gestisciProgettoBox(false);
			
			Progetto progettoSelezionato = ListaProgettiLV.getSelectionModel().getSelectedItem();
			
			ProjectManagerTF		.setText(progettoSelezionato.getProjectManager().toString());
			TitoloProgettoTF		.setText(progettoSelezionato.getTitolo());
			
			switch(utils.controlloStringa(progettoSelezionato.getDescrizione(), "")) {
				case 1:
					DescrizioneProgettoTA.setText("Nessuna descrizione");
					break;
				default:
					DescrizioneProgettoTA.setText(progettoSelezionato.getDescrizione());
			}
			
			DataDiInizioProgettoTF	.setText(String.valueOf(progettoSelezionato.getDataInizio()));
			
			if (progettoSelezionato.getDataFine() != null) {
				DataDiFineProgettoTF.setText(String.valueOf(progettoSelezionato.getDataFine()));
			} else {
				DataDiFineProgettoTF.setText("Ancora da consegnare");
			}
			
			DataDiScadenzaProgettoTF.setText(String.valueOf(progettoSelezionato.getScadenza()));
			TipologiaProgettoTF		.setText(progettoSelezionato.getTipoProgetto().toString());			
			AmbitiProgettoTA		.setText(utils.ambitiToString(progettoSelezionato.getListaAmbiti()));
			
			if (progettoSelezionato.getProjectManager() == impiegato) {
				ProjectManagerBox.setVisible(true);
				SalvaModificheProgetto.setVisible(false);
			} else
				ProjectManagerBox.setVisible(false);
		}
    }
    
    @FXML private void visualizzaRiunioni(MouseEvent event)
	{
    	if (!ListaRiunioniLV.isVisible())
    	{
			ListaProgettiLabel.setStyle("-fx-text-fill: derive(white, -50%);");
			SchedaListaProgetti.setStyle("-fx-border-width: 0 0 5 0;");
			
			ListaRiunioniLabel.setStyle("-fx-text-fill: white;");
			SchedaListaRiunioni.setStyle("-fx-border-width: 5 5 1 5");
			
			ListaProgettiLV.setVisible(false);
			ListaRiunioniLV.setVisible(true);
			
			DescrizioneRiunioneBox.setVisible(false);
			DescrizioneProgettoBox.setVisible(false);
			IstruzioniBox.setVisible(true);
			
			IstruzioniLabel.setText(istruzioniRiunione);
		}
    }

    @FXML void visualizzaInformazioniRiunione(MouseEvent event) {
        //gestisciBox(false);
        if (!ListaRiunioniLV.getItems().contains(riunioneIniziale)) {
        	
			if(ListaRiunioniLV.getSelectionModel().getSelectedItem().getTipologia().equals("Riunione in sede fisica")) {
				RiunioneFisica infoRiunione = (RiunioneFisica)ListaRiunioniLV.getSelectionModel().getSelectedItem();
				setCampiRiunione(infoRiunione);
				
				CampiRiunioneFisica.setVisible(true);
				CampiRiunioneTelematica.setVisible(false);
				
				SedeTF.setText(infoRiunione.getSede());
				NomeStanzaTF.setText(infoRiunione.getNomeStanza());
				PianoStanzaTF.setText(infoRiunione.getPiano());
			} else if(ListaRiunioniLV.getSelectionModel().getSelectedItem().getTipologia().equals("Riunione in modalit� telematica")){
				
				RiunioneTelematica infoRiunione = (RiunioneTelematica)ListaRiunioniLV.getSelectionModel().getSelectedItem();
				setCampiRiunione(infoRiunione);
				
				CampiRiunioneFisica.setVisible(false);
				CampiRiunioneTelematica.setVisible(true);
				
				NomePiattaformaTF.setText(infoRiunione.getPiattaforma());
				CodiceAccessoTF.setText(infoRiunione.getCodiceAccesso());
			}
		}
    }
    
    private void setCampiRiunione(Riunione riunioneSelezionata) {
    	IstruzioniBox		   .setVisible(false);
		DescrizioneProgettoBox .setVisible(false);
		DescrizioneRiunioneBox .setVisible(true);
		
		OrganizzatoreBox.setVisible(riunioneSelezionata.getOrganizzatore().getCF().equals(impiegato.getCF()));
		PartecipanteBox.setVisible(!(riunioneSelezionata.getOrganizzatore().getCF().equals(impiegato.getCF())));
		
		OrganizzatoreRiunioneTF	.setText(riunioneSelezionata.getOrganizzatore().toString());
		TitoloRiunioneTF		.setText(riunioneSelezionata.getTitolo());
		
		switch(utils.controlloStringa(riunioneSelezionata.getDescrizione(), "")) {
			case 1:
				DescrizioneRiunioneTA.setText("Nessuna descrizione");
				break;
			default:
				DescrizioneRiunioneTA	.setText(riunioneSelezionata.getDescrizione());
		}
		
		OrarioDiInizioRiunioneTF.setText(utils.orarioToString(null, riunioneSelezionata.getOrarioDiInizio().toLocalDate(),
															  riunioneSelezionata.getOrarioDiInizio().toLocalTime()));
		OrarioDiFineRiunioneTF	.setText(utils.orarioToString(null, riunioneSelezionata.getOrarioDiFine().toLocalDate(),
				  											  riunioneSelezionata.getOrarioDiFine().toLocalTime()));
		
		TipologiaRiunioneTF		.setText(riunioneSelezionata.getTipologia());
    }
}