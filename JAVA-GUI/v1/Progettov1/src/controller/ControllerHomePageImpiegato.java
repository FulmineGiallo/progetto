package controller;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;

import java.time.LocalDate;
import java.util.Locale;

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
import model.Impiegato;
import model.Progetto;
import model.Riunione;
import view.FinestraPopup;
import view.FormRegistrazioneProgetto;
import view.HomePageBenvenuto;
import view.HomePageOrganizzatore;
import view.HomePageProgetto;
import view.HomePageValutazioni;

public class ControllerHomePageImpiegato {
	@FXML private AnchorPane 			HomePageImpiegato;
	
	@FXML private VBox					ImpiegatoBox;
	@FXML private Label 				NomeImpiegatoLabel;
	@FXML private Label 				GradoImpiegatoLabel;
	@FXML private Label					TitoloSalarioLabel;
	@FXML private Label                 SalarioLabel;
	
	@FXML private HBox					ToolBar;
	@FXML private Button 				ValutazioniButton;
	@FXML private Button 				NuovoProgettoButton;
	@FXML private Button 				LogoutButton;
	
	@FXML private GridPane				SchedeBox;
	@FXML private HBox					SchedaListaProgetti;			
	@FXML private Label 				ListaProgettiLabel;
	@FXML private HBox					SchedaListaRiunioni;
	@FXML private Label					ListaRiunioniLabel;
	
	@FXML private AnchorPane			ListeBox;
	@FXML private ListView<Progetto>	ListaProgettiLV;
	@FXML private ListView<Riunione> 	ListaRiunioniLV;
	
	@FXML private AnchorPane			IstruzioniBox;
	@FXML private Label					IstruzioniLabel;
	
	@FXML private AnchorPane			DescrizioneProgettoBox;
	@FXML private AnchorPane 			DescrizioneProgettoPane;
	@FXML private Label 				ProjectManagerProgettoLabel;
	@FXML private TextField				ProjectManagerTF;
	@FXML private Label					DescrizioneProgettoLabel;
	@FXML private TextArea 				DescrizioneProgettoTA;
	@FXML private Label 				DataDiInizioProgettoLabel;
	@FXML private TextField				DataDiInizioProgettoTF;
	@FXML private Label 				DataDiFineProgettoLabel;
	@FXML private TextField				DataDiFineProgettoTF;
	@FXML private Label 				DataDiScadenzaProgettoLabel;
	@FXML private TextField				DataDiScadenzaProgettoTF;
	@FXML private Label 				NoteProgettoLabel;
	@FXML private TextArea				NoteProgettoTA;

	@FXML private AnchorPane			ProjectManagerBox;
	@FXML private Button 				GestioneProgettoButton;
	@FXML private Button 				ModificaProgettoButton;
	@FXML private Button                SalvaModificheProgetto;

	@FXML private AnchorPane			DescrizioneRiunioneBox;
	@FXML private AnchorPane 			DescrizioneRiunionePane;
	@FXML private Label 				OrganizzatoreRiunioneLabel;
	@FXML private TextField				OrganizzatoreRiunioneTF;
	@FXML private Label 				DescrizioneRiunioneLabel;
	@FXML private TextArea 				DescrizioneRiunioneTA;
	@FXML private Label 				OrarioDiInizioRiunioneLabel;
	@FXML private TextField				OrarioDiInizioRiunioneTF;
	@FXML private Label 				OrarioDiFineRiunioneLabel;
	@FXML private TextField				OrarioDiFineRiunioneTF;
	@FXML private Label 				NoteRiunioneLabel;
	@FXML private TextArea				NoteRiunioneTA;
	
	@FXML private AnchorPane			OrganizzatoreBox;
	@FXML private Button 				GestioneRiunioneButton;
	@FXML private Button 				ModificaRiunioneButton;
	@FXML private Button                SalvaModificheRiunioneButton;

    @FXML private AnchorPane 			PartecipanteBox;
    @FXML private Button 				PresenzaButton;
    @FXML private Button 				AssenzaButton;
    
    private final String istruzioniProgetto = "Clicca un progetto\r\n" + "per visualizzarne le informazioni";
    private final String istruzioniRiunione = "Clicca una riunione\r\n" + "per visualizzarne le informazioni";
	
	private FinestraPopup finestraConferma;
	private FinestraPopup finestraErrore;
	private Exception error;
	
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
    ProgettoDaoInterface progetti;
    RiunioneDaoInterface riunioni;
    {
        try {
            dbConnection = new DBConnection();
            connection = dbConnection.getConnection();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    
    {
        try {
            progetti = new ProgettoDao(connection);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    
    {
        try {
            riunioni = new RiunioneDao(connection);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    
    public void setStage(Stage window, Stage popup)
    {
    	this.window = window;
    	this.popup = popup;
    }
    
    public void inizializza(Impiegato impiegato) throws SQLException
	{
    	this.impiegato = impiegato;
    	NomeImpiegatoLabel.setText((impiegato.getNome() +" "+ impiegato.getCognome()).toUpperCase(Locale.ROOT));
        GradoImpiegatoLabel.setText(impiegato.getGrado());
        listaProgetti.addAll(progetti.getProgettiImpiegato(impiegato));
        if (listaProgetti.isEmpty())
        {
			ListaProgettiLV.getItems().add(new Progetto("Non ci sono ancora progetti"));
		}
        else {
			ListaProgettiLV.setItems(listaProgetti);
		}
        
		listaRiunioni.addAll(riunioni.getRiunioniImpiegato(impiegato));
		
        if (listaRiunioni.isEmpty()) {
			ListaRiunioniLV.getItems().add(new Riunione("Non ci sono ancora riunioni"));
		} else
			{
			ListaRiunioniLV.setItems(listaRiunioni);
		}
        
		IstruzioniBox.setVisible(true);
        DescrizioneProgettoBox.setVisible(false);
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
    	finestraErrore = new FinestraPopup();
    	finestraErrore.start(window, popup);
		connection.close();
    }

    @FXML void accettaInvito(ActionEvent event) {
    	try {
			if(riunioni.isPresente(impiegato, ListaRiunioniLV.getSelectionModel().getSelectedItem()) != 0) {
				
				int update;

				
				update=riunioni.UpdatePresenza(impiegato, ListaRiunioniLV.getSelectionModel().getSelectedItem());
				
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
					finestraErrore.start(popup, "Impossibile registrare la presenza", error);
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
			if(riunioni.isAssente(impiegato, ListaRiunioniLV.getSelectionModel().getSelectedItem()) != 0) {
				
			int update;

				
				update=riunioni.UpdateAssenza(impiegato, ListaRiunioniLV.getSelectionModel().getSelectedItem());
				
				if(update!=0)
					System.out.println("assenza salvata");
				finestraConferma = new FinestraPopup();
				try {
					finestraErrore.start(popup, "Assenza registrata correttamente");
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}else {
				System.out.println("errore assenza");
				finestraErrore = new FinestraPopup();
				try {
					finestraErrore.start(popup, "Impossibile registrare l'assenza", error);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }

    //Se il GestioneProgettoButton viene cliccato
    @FXML void gestisciProgetto(ActionEvent event) {
        HomePageProgetto homeProjectManager = new HomePageProgetto(impiegato, ListaProgettiLV.getSelectionModel().getSelectedItem());
        try {
            homeProjectManager.start(window, popup);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML void gestisciRiunione(ActionEvent event) {
        //Se il bottone gestione riunione viene cliccato
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

    @FXML void modificaInformazioniRiunione(ActionEvent event)
	{ // >> DA FARE

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


            updateEffettuato = progetti.updateInfoProgetto(ListaProgettiLV.getSelectionModel().getSelectedItem());
			SalvaModificheProgetto.setVisible(false); //una volta premuoto salva, il bottone scompare.

        }
        catch (SQLException throwables)
		{
            throwables.printStackTrace();
        }

    }

    @FXML private void salvaModificheRiunione(ActionEvent event)
	{ // >> DA FARE

    }
    
    @FXML private void visualizzaProgetti(MouseEvent event) {
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

    @FXML void visualizzaInformazioniProgetto(MouseEvent event) {
        if (!ListaProgettiLV.getItems().get(0).getTitolo().equals("Non ci sono ancora progetti")) {
        	
			IstruzioniBox		  .setVisible(false);
			DescrizioneRiunioneBox.setVisible(false);
			DescrizioneProgettoBox.setVisible(true);
			
			gestisciProgettoBox(false);
			
			Progetto progettoSelezionato = ListaProgettiLV.getSelectionModel().getSelectedItem();
			
			ProjectManagerTF		.setText(progettoSelezionato.getProjectManager().toString());
			DescrizioneProgettoTA	.setText(progettoSelezionato.getDescrizione());
			DataDiInizioProgettoTF	.setText(String.valueOf(progettoSelezionato.getDataInizio()));
			
			if (progettoSelezionato.getDataFine() != null) {
				DataDiFineProgettoTF.setText(String.valueOf(progettoSelezionato.getDataFine()));
			} else {
				DataDiFineProgettoTF.setText("Ancora da consegnare");
			}
			
			switch(utils.controlloStringa(progettoSelezionato.getDescrizione(), "")) {
	    		case 1:
	    			DescrizioneProgettoTA.setText("Nessuna descrizione");
	    			break;
	    		default:
	    			DescrizioneProgettoTA.setText(progettoSelezionato.getDescrizione());
			}
			
			DataDiScadenzaProgettoTF.setText(String.valueOf(progettoSelezionato.getScadenza()));
			
			if (progettoSelezionato.getProjectManager() == impiegato) {
				ProjectManagerBox.setVisible(true);
				SalvaModificheProgetto.setVisible(false);
			} else
				ProjectManagerBox.setVisible(false);
		}
    }
    
    @FXML private void visualizzaRiunioni(MouseEvent event){		
    	if (!ListaRiunioniLV.isVisible()) {
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
        if (!ListaRiunioniLV.getItems().get(0).getTitolo().equals("Non ci sono ancora riunioni")) {
        	
			IstruzioniBox		   .setVisible(false);
			DescrizioneProgettoBox .setVisible(false);
			DescrizioneRiunioneBox .setVisible(true);
			OrganizzatoreBox	   .setVisible(false);
			
			Riunione riunioneSelezionata = ListaRiunioniLV.getSelectionModel().getSelectedItem();
			
			OrganizzatoreRiunioneTF	.setText(String.valueOf(riunioneSelezionata.getOrganizzatore().toString()));
			DescrizioneRiunioneTA	.setText(riunioneSelezionata.getDescrizione());
			//DataDiInizioRiunioneTF		.setText(String.valueOf(riunioneSelezionata.getData()));
			//TitoloRiunioneLabel			.setText(String.valueOf(riunioneSelezionata.getTitolo()));
			OrarioDiInizioRiunioneTF.setText(String.valueOf(riunioneSelezionata.getOrarioInizio()));
			OrarioDiFineRiunioneTF	.setText(String.valueOf(riunioneSelezionata.getOrarioFine()));
			
			if (riunioneSelezionata.getCFOrganizzatore().equals(impiegato.getCF())) {
				PartecipanteBox.setVisible(false);
				OrganizzatoreBox.setVisible(true);
				//SalvaModificheProgetto.setVisible(false);

			} else {
				PartecipanteBox.setVisible(true);
				PresenzaButton.setVisible(true);
				AssenzaButton.setVisible(true);
				ProjectManagerBox.setVisible(false);
			} 
		}
    }
}