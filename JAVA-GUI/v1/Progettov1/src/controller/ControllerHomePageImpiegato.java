package controller;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;

import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.Locale;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
	@FXML private Label                 SalarioLabel;
	
	@FXML private HBox					ToolBar;
	@FXML private Button 				ValutazioniButton;
	@FXML private Button 				NuovoProgettoButton;
	@FXML private Button 				LogoutButton;
	
	@FXML private AnchorPane 			ListaProgettiBox;
	@FXML private Label 				ListaProgettiLabel;
	@FXML private ScrollPane 			ListaProgettiScrollPane;
	@FXML private ListView<Progetto>	ListaProgettiLV;
	
	@FXML private AnchorPane			IstruzioniBox;
	@FXML private Label					IstruzioniLabel;
	
	@FXML private AnchorPane			DescrizioneProgettoBox;
	@FXML private AnchorPane 			DescrizioneProgettoPane;
	@FXML private Label 				ProjectManagerProgettoLabel;
	@FXML private Label 				TitoloProgettoLabel;
	@FXML private TextArea 				DescrizioneProgettoTA;
	@FXML private TextArea 				DataDiInizioProgettoLabel;
	@FXML private TextArea 				DataDiFineProgettoLabel;
	@FXML private TextArea 				DataDiScadenzaProgettoLabel;
	@FXML private Label 				NoteProgettoLabel;
	@FXML private Label                 DescrizioneProgettoLabel;

	@FXML private AnchorPane			ProjectManagerBox;
	@FXML private Button 				GestioneProgettoButton;
	@FXML private Button 				ModificaProgettoButton;
	@FXML private Button                SalvaModificheProgetto;

	@FXML private AnchorPane			DescrizioneRiunioneBox;
	@FXML private AnchorPane 			DescrizioneRiunionePane;
	@FXML private Label 				OrganizzatoreRiunioneLabel;
	@FXML private Label 				TitoloRiunioneLabel;
	@FXML private TextArea 				DescrizioneRiunioneTA;
	@FXML private Label 				DataDiInizioRiunioneLabel;
	@FXML private Label 				OrarioDiInizioRiunioneLabel;
	@FXML private Label 				OrarioDiFineRiunioneLabel;
	@FXML private Label 				NoteRiunioneLabel;
	
	@FXML private AnchorPane			OrganizzatoreBox;
	@FXML private Button 				GestioneRiunioneButton;
	@FXML private Button 				ModificaRiunioneButton;
	@FXML private Button                SalvaModificheRiunioneButton;

    @FXML private AnchorPane 			PartecipanteBox;
    @FXML private Button 				PresenzaButton;
    @FXML private Button 				AssenzaButton;
	
	@FXML private Label 				ListaRiunioniLabel;
	@FXML private ScrollPane 			ListaRiunioniScrollPane;
	@FXML private ListView<Riunione> 	ListaRiunioniLV;
	
	private FinestraPopup finestraErrore;
	private Exception error;
	
	HomePageBenvenuto 					homePageBenvenuto;
	HomePageValutazioni 				homePageValutazioni;
	FormRegistrazioneProgetto 			registrazioneProgetto;
	Stage 								window;
	Stage								popup;
	
	int updateEffettuato;

	Impiegato impiegato = null;

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
    
    public void inizializza(Impiegato impiegato) throws SQLException {
    	this.impiegato = impiegato;
    	
    	NomeImpiegatoLabel.setText((impiegato.getNome() +" "+ impiegato.getCognome()).toUpperCase(Locale.ROOT));
        GradoImpiegatoLabel.setText(impiegato.getGrado());
        
        listaProgetti.addAll(progetti.getProgettiImpiegato(impiegato));
        if (listaProgetti.isEmpty()) {
			ListaProgettiLV.getItems().add(new Progetto("Non ci sono ancora progetti"));
		} else {
			ListaProgettiLV.setItems(listaProgetti);
		}
        
		listaRiunioni.addAll(riunioni.getRiunioniImpiegato(impiegato));
		
        if (listaRiunioni.isEmpty()) {
			ListaRiunioniLV.getItems().add(new Riunione("Non ci sono ancora riunioni"));
		} else {
			ListaRiunioniLV.setItems(listaRiunioni);
		}
        
		IstruzioniBox.setVisible(true);
        DescrizioneProgettoBox.setVisible(false);
        SalarioLabel.setText("Calcolare");
        /*Richiamare Dao Salario */
    }

    /*Metodo che gestisce le modifiche di DescrizioneProgettoBox */
    private void gestisciProgettoBox(boolean state)
    {
        DescrizioneProgettoTA.setEditable(state);
        DataDiInizioProgettoLabel.setEditable(state);
        DataDiFineProgettoLabel.setEditable(state);
        DataDiScadenzaProgettoLabel.setEditable(state);
    }

    @FXML
    public void CreaProgetto(ActionEvent actionEvent) throws Exception {
        registrazioneProgetto = new FormRegistrazioneProgetto(impiegato);
        registrazioneProgetto.start(window, popup);
    }
    
    @FXML
    public void VisualizzaValutazioni(ActionEvent event) throws Exception {
        homePageValutazioni = new HomePageValutazioni(impiegato);
        homePageValutazioni.start(window, popup);
    }
    
    @FXML
    public void EffettuaLogout(ActionEvent event) throws Exception {
    	finestraErrore = new FinestraPopup("Sei sicuro di voler uscire?", error);
    	finestraErrore.startLogout(window, popup);

    }

    @FXML void accettaInvito(ActionEvent event) {
    	try {
			if(riunioni.isPresente(impiegato, ListaRiunioniLV.getSelectionModel().getSelectedItem()) != 0) {
				
				int update;

				
				update=riunioni.UpdatePresenza(impiegato, ListaRiunioniLV.getSelectionModel().getSelectedItem());
				
				if(update!=0)
					System.out.println("presenza salvata");
					finestraErrore=new FinestraPopup("presenza salvata", error);
					try {
						finestraErrore.startPopupErrore(popup);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
			}else {
				System.out.println("errore presenza");
				finestraErrore=new FinestraPopup("impossibile salvare la presenza", error);
				try {
					finestraErrore.startPopupErrore(popup);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
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
				finestraErrore=new FinestraPopup("assenza salvata", error);
				try {
					finestraErrore.startPopupErrore(popup);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}else {
				System.out.println("errore assenza");
				finestraErrore=new FinestraPopup("impossibile salvare l'assenza", error);
				try {
					finestraErrore.startPopupErrore(popup);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    //Se il GestioneProgettoButton viene cliccato
    @FXML void gestisciProgetto(ActionEvent event) {
        HomePageProgetto homeProjectManger = new HomePageProgetto(impiegato, ListaProgettiLV.getSelectionModel().getSelectedItem());
        try {
            homeProjectManger.start(window, popup);
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
    @FXML void modificaInformazioniProgetto(ActionEvent event) {
        gestisciProgettoBox(true);
        SalvaModificheProgetto.setVisible(true);
    }

    @FXML void modificaInformazioniRiunione(ActionEvent event) { // >> DA FARE

    }

    @FXML void salvaModificheProgetto(ActionEvent event) {
    	gestisciProgettoBox(false);
        
        LocalDate datainizio;
        LocalDate dataFine;
        LocalDate dataScadenza;
        
        try {
            ListaProgettiLV.getSelectionModel().getSelectedItem().setDescrizione(DescrizioneProgettoTA.getText());
            datainizio = LocalDate.parse(DataDiInizioProgettoLabel.getText());
            dataFine = LocalDate.parse(DataDiFineProgettoLabel.getText());
            dataScadenza = LocalDate.parse(DataDiScadenzaProgettoLabel.getText());

            ListaProgettiLV.getSelectionModel().getSelectedItem().setDataInizio(Date.valueOf(datainizio));
            ListaProgettiLV.getSelectionModel().getSelectedItem().setDataFine(Date.valueOf(dataFine));
            ListaProgettiLV.getSelectionModel().getSelectedItem().setScadenza(Date.valueOf(dataScadenza));


            updateEffettuato = progetti.updateInfoProgetto(ListaProgettiLV.getSelectionModel().getSelectedItem());
            SalvaModificheProgetto.setVisible(false);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    @FXML void salvaModificheRiunione(ActionEvent event) { // >> DA FARE

    }

    @FXML void visualizzaInformazioniProgetto(MouseEvent event) {
        IstruzioniBox.setVisible(false);
        DescrizioneRiunioneBox.setVisible(false);
        DescrizioneProgettoBox.setVisible(true);
        
        gestisciProgettoBox(false);

        DescrizioneProgettoTA.setText(ListaProgettiLV.getSelectionModel().getSelectedItem().getDescrizione());
        DataDiInizioProgettoLabel.setText(String.valueOf(ListaProgettiLV.getSelectionModel().getSelectedItem().getDataInizio()));
        DataDiFineProgettoLabel.setText(String.valueOf(ListaProgettiLV.getSelectionModel().getSelectedItem().getDataFine()));
        DataDiScadenzaProgettoLabel.setText(String.valueOf(ListaProgettiLV.getSelectionModel().getSelectedItem().getScadenza()));

        if(ListaProgettiLV.getSelectionModel().getSelectedItem().getProjectManager() == impiegato) {
            ProjectManagerBox.setVisible(true);
            SalvaModificheProgetto.setVisible(false);
        } else
            ProjectManagerBox.setVisible(false);
    }

    @FXML void visualizzaInformazioniRiunione(MouseEvent event) {
        IstruzioniBox.setVisible(false);
        DescrizioneProgettoBox.setVisible(false);
        DescrizioneRiunioneBox.setVisible(true);
        OrganizzatoreBox.setVisible(false);
        
        //gestisciBox(false);

        DescrizioneRiunioneTA.setText(ListaRiunioniLV.getSelectionModel().getSelectedItem().getDescrizione());
        DataDiInizioRiunioneLabel.setText(String.valueOf(ListaRiunioniLV.getSelectionModel().getSelectedItem().getData()));
        OrganizzatoreRiunioneLabel.setText(String.valueOf(ListaRiunioniLV.getSelectionModel().getSelectedItem().getOrganizzatore()));
        TitoloRiunioneLabel.setText(String.valueOf(ListaRiunioniLV.getSelectionModel().getSelectedItem().getTitolo()));
        OrarioDiInizioRiunioneLabel.setText(String.valueOf(ListaRiunioniLV.getSelectionModel().getSelectedItem().getOrarioInizio()));
        OrarioDiFineRiunioneLabel.setText(String.valueOf(ListaRiunioniLV.getSelectionModel().getSelectedItem().getOrarioFine()));

        
        if(ListaRiunioniLV.getSelectionModel().getSelectedItem().getCFOrganizzatore().equals(impiegato.getCF()))
        {
        	PartecipanteBox.setVisible(false);
            OrganizzatoreBox.setVisible(true);
            //SalvaModificheProgetto.setVisible(false);

        }
        else {
            PartecipanteBox.setVisible(true);
            PresenzaButton.setVisible(true);
            AssenzaButton.setVisible(true);
            ProjectManagerBox.setVisible(false);
        }
    }
}