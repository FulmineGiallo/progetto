package controller;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Locale;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Impiegato;
import model.Riunione;
import model.Skill;
import model.Titolo;
import model.Connection.DBConnection;
import model.Dao.ComuneDao;
import model.Dao.RiunioneDao;
import model.Dao.RiunioneImpiegatoDao;
import model.Dao.SkillDao;
import model.Dao.TitoloDao;
import model.Dao.progettoImpiegatoDao;
import model.DaoInterface.RiunioneDaoInterface;
import utilities.MetodiComuni;
import view.FinestraPopup;
import view.FormRegistrazioneValutazione;
import view.HomePageImpiegato;
import view.HomePageOrganizzatore;

public class ControllerHomePageOrganizzatore {

    @FXML private AnchorPane 		  HomePageOrganizzatore;
    
    @FXML private VBox   			  OrganizzatoreBox;
    @FXML private Label 			  NomeOrganizzatoreLabel;
    @FXML private Label 			  NomeRiunioneLabel;
    
    @FXML private HBox 				  ToolBar;
    @FXML private Button 			  HomePageImpiegatoButton;
    @FXML private Button 			  AggiungiImpiegatoButton;
    
    @FXML private AnchorPane 		  ListaPartecipantiBox;
    @FXML private Label 			  ListaPartecipantiLabel;
    @FXML private ListView<Impiegato> ListaPartecipantiLV;
    
    @FXML private AnchorPane 		  IstruzioniBox;
    @FXML private Label 			  IstruzioniLabel;
    
    @FXML private AnchorPane 		  DescrizioneRiunioneImpiegatoBox;
    @FXML private AnchorPane 		  DescrizioneRiunioneImpiegatoPane;
    
    @FXML private AnchorPane 		  InformazioniImpiegatoBox;
    
    @FXML private HBox 				  NomeImpiegatoBox;
    @FXML private Label 			  NomeImpiegatoLabel;
    @FXML private TextField 		  NomeImpiegatoTF;
    
    @FXML private HBox 				  EmailBox;
    @FXML private Label 			  EmailLabel;
    @FXML private TextField 		  EmailTF;
    
    @FXML private HBox 				  ComuneDiNascitaBox;
    @FXML private Label 			  ComuneDiNascitaLabel;
    @FXML private TextField 		  ComuneDiNascitaTF;
    
    @FXML private HBox 				  DataDiNascitaBox;
    @FXML private Label 			  DataDiNascitaLabel;
    @FXML private TextField 		  DataDiNascitaTF;
    
    @FXML private HBox 			  	  SelezionaSkillBox;
    @FXML private Label 		  	  SkillComboBoxLabel;
    @FXML private ComboBox<Skill> 	  SkillComboBox;
    
    @FXML private HBox 				  TipologiaSkillBox;
    @FXML private Label 			  TipologiaSkillLabel;
    @FXML private TextField 		  TipologiaSkillTF;
    
    @FXML private VBox 				  SkillBox;
    
    @FXML private HBox 				  TitoloSkillBox;
    @FXML private Label 			  TitoloSkillLabel;
    @FXML private TextField 		  TitoloSkillTF;
    
    @FXML private HBox 				  DataDiCertificazioneBox;
    @FXML private Label 			  DataCertificazioneSkillLabel;
    @FXML private TextField 		  DataCertificazioneTF;
    
    @FXML private VBox 				  DescrizioneSkillBox;
    @FXML private Label 			  DescrizioneLabel;
    @FXML private TextArea 			  DescrizioneSkillTA;
    
    @FXML private HBox				  ButtonBox;
    @FXML private Button 			  RimuoviImpiegatoButton;
    @FXML private Button			  NuovaValutazioneButton;
    
    private HomePageImpiegato homePageImpiegato;
    
    private Stage window;
    private Stage popup;
    
    private SkillDao skillDao;
    private RiunioneImpiegatoDao riunioneImpiegatoDao;
    private RiunioneDaoInterface riunioneDao;
    private ComuneDao comuneDao;
    
    private int idriunione;
    private FinestraPopup finestraRimuoviImpiegatoDallaRiunione;
    private FormRegistrazioneValutazione formRegistrazioneValutazione;
    
    private Riunione riunione;
    private Impiegato Organizzatore;
    
    private Connection connection;
    private DBConnection dbConnection;
    
    ObservableList<Impiegato> lista = FXCollections.observableArrayList();
    private ObservableList<Skill> listaSkill;
    
    private MetodiComuni utils = new MetodiComuni();

    public void setStage(Stage window, Stage popup)
    {
    	this.window = window;
    	this.popup = popup;
    }

    public void inizializza(Impiegato Organizzatore, Riunione riunione) throws SQLException {
    	this.riunione = riunione;
    	this.Organizzatore = Organizzatore;
    	
        try {
            dbConnection = new DBConnection();
            connection = dbConnection.getConnection();
            
            riunioneDao = new RiunioneDao(connection);
            idriunione = riunioneDao.getIdRiunione(riunione);
            lista = riunioneDao.getPartecipanti(idriunione);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    	
        NomeOrganizzatoreLabel.setText((Organizzatore.toString()).toUpperCase());
        NomeRiunioneLabel.setText((riunione.getTitolo()));
        ListaPartecipantiLV.setItems(lista);
        
        if(lista.size() == 1) {
        	IstruzioniLabel.setText("Non ci sono ancora partecipanti a questa riunione");
        }
    }
    
    @FXML
    private void backHomePageImpiegato(ActionEvent event) throws Exception
    {
    	homePageImpiegato = new HomePageImpiegato(Organizzatore);
    	homePageImpiegato.start(window, popup);
    }
    
    @FXML private void updateInfoImpiegato() {
    	Impiegato infoImpiegato = ListaPartecipantiLV.getSelectionModel().getSelectedItem();
    	
        if (infoImpiegato != null) {
        	
			IstruzioniBox.setVisible(false);
			DescrizioneRiunioneImpiegatoBox.setVisible(true);
			SkillBox.setVisible(false);
			ButtonBox.setVisible(!infoImpiegato.getCF().equals(riunione.getOrganizzatore().getCF()));
			
			NomeImpiegatoTF	.setText(infoImpiegato.toString());
			EmailTF			.setText(infoImpiegato.getEmail());

			try {
				comuneDao = new ComuneDao(connection);
				ComuneDiNascitaTF.setText(comuneDao.getComuneBySigla(infoImpiegato.getComuneNascita()).getNomeComune());
			} catch (SQLException e) {
				ComuneDiNascitaTF.setText(infoImpiegato.getComuneNascita());
			}
			
			DataDiNascitaTF.setText(infoImpiegato.getDataNascita().toString());
			
			try {
				skillDao = new SkillDao(connection);
				listaSkill = skillDao.getListaSkill(infoImpiegato);				
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
			
			if (listaSkill.isEmpty()) {
				SkillComboBox.setPromptText("Nessuna skill specificata");
				SkillComboBox.setDisable(true);
			} else {
				SkillComboBox.setPromptText("Seleziona una skill");
				SkillComboBox.setDisable(false);
				SkillComboBox.setItems(listaSkill);
				
				SkillComboBox.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
					Skill infoSkill = SkillComboBox.getSelectionModel().getSelectedItem();

					if (infoSkill != null) {
						SkillBox.setVisible(true);
						
						TipologiaSkillTF.setText(infoSkill.getTipoSkill());
						
						if(infoSkill.getTipoSkill().equals("Soft-Skill")) {
							TitoloSkillBox.setVisible(false);
							DataDiCertificazioneBox.setVisible(false);
						} else {
							TitoloSkillBox.setVisible(true);
							DataDiCertificazioneBox.setVisible(true);
							
							TitoloSkillTF.setText(infoSkill.getTitolo().toString());
							DataCertificazioneTF.setText(infoSkill.getDataCertificazione().toString());
						}
						
						switch(utils.controlloStringa(infoSkill.getDescrizione(), "")) {
							case 1:
								DescrizioneSkillTA.setText("Nessuna descrizione");
								break;
							default:
								DescrizioneSkillTA.setText(infoSkill.getDescrizione());
						}
						
					} else {
						SkillBox.setVisible(false);
					}
				});
			}
		}
    }
    
    @FXML private void updateInfoBox(){
    	DescrizioneRiunioneImpiegatoBox.setVisible(false);
        IstruzioniBox.setVisible(true);
    }
    
    public void RimuoviImpiegato(ActionEvent event) throws Exception {
    	   
    	finestraRimuoviImpiegatoDallaRiunione = new FinestraPopup();
		finestraRimuoviImpiegatoDallaRiunione.start(popup, ListaPartecipantiLV.getSelectionModel().getSelectedItem(), riunione, this);

        idriunione = riunioneDao.getIdRiunione(riunione);
        lista = riunioneDao.getPartecipanti(idriunione);
        ListaPartecipantiLV.setItems(lista);

    }
    
    public void AggiornaLista() throws SQLException {
    	
        idriunione = riunioneDao.getIdRiunione(riunione);
        lista = riunioneDao.getPartecipanti(idriunione);
        ListaPartecipantiLV.setItems(lista);
    }
    
    public void DaDescrizioneRiunioneAdIstruzioniBox() throws SQLException {
    	DescrizioneRiunioneImpiegatoBox.setVisible(false);
    	IstruzioniBox.setVisible(true);
    }
    
    @FXML private void aggiungiValutazione() {
    	formRegistrazioneValutazione = new FormRegistrazioneValutazione();
    	formRegistrazioneValutazione.start(window, popup, ListaPartecipantiLV.getSelectionModel().getSelectedItem(), riunione);
    }
    
}
