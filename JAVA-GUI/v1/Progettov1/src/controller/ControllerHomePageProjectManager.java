package controller;

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
import model.Connection.DBConnection;
import model.Dao.ComuneDao;
import model.Dao.ProgettoDao;
import model.Dao.SkillDao;
import model.Dao.TitoloDao;
import model.Dao.progettoImpiegatoDao;
import model.DaoInterface.ComuneDaoInterface;
import model.DaoInterface.ProgettoDaoInterface;
import model.DaoInterface.SkillDaoInterface;
import model.DaoInterface.TitoloDaoInterface;
import utilities.MetodiComuni;
import model.Impiegato;
import model.Progetto;
import model.Skill;
import model.Titolo;
import view.FinestraAggiungiPartecipanteAlProgetto;
import view.FinestraPopup;
import view.FinestraRimuoviImpiegatoDalProgetto;
import view.FormRegistrazioneRiunione;
import view.FormRegistrazioneValutazione;
import view.HomePageImpiegato;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Locale;

public class ControllerHomePageProjectManager
{

    @FXML private AnchorPane 		  HomePageProjectManager;
    
    @FXML private VBox 				  ProjectManagerBox;
    @FXML private Label 			  NomeProjectManagerLabel;
    @FXML private Label 			  NomeProgettoLabel;
    
    @FXML private HBox 				  ToolBar;
    @FXML private Button 			  NuovaRiunioneButton;
    @FXML private Button 			  AggiungiImpiegatoButton;
    @FXML private Button 			  HomePageImpiegatoButton;
    
    @FXML private AnchorPane 		  ListaPartecipantiBox;
    @FXML private Label 			  ListaPartecipantiLabel;
    @FXML private ListView<Impiegato> ListaPartecipantiLV;
    
    @FXML private AnchorPane 		  IstruzioniBox;
    @FXML private Label 			  IstruzioniLabel;
    
    @FXML private AnchorPane 		  DescrizioneProgettoImpiegatoBox;
    @FXML private AnchorPane 		  DescrizioneProgettoImpiegatoPane;
    
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
    
    @FXML private HBox 				  RuoloImpiegatoBox;
    @FXML private Label 			  RuoloImpiegatoLabel;
    @FXML private TextField 		  RuoloImpiegatoTF;
    
    @FXML private HBox 				  SelezionaSkillBox;
    @FXML private Label 			  SkillComboBoxLabel;
    @FXML private ComboBox<Skill> 	  SkillComboBox;
    
    @FXML private VBox 				  SkillBox;
    
    @FXML private HBox 				  TipologiaSkillBox;
    @FXML private Label 			  TipologiaSkillLabel;
    @FXML private TextField 		  TipologiaSkillTF;
    
    @FXML private HBox 				  TitoloSkillBox;
    @FXML private Label 			  TitoloSkillLabel;
    @FXML private TextField 		  TitoloSkillTF;
    
    @FXML private HBox 				  DataDiCertificazioneBox;
    @FXML private Label 			  DataCertificazioneSkillLabel;
    @FXML private TextField 		  DataCertificazioneTF;
    
    @FXML private VBox 				  DescrizioneSkillBox;
    @FXML private Label 			  DescrizioneLabel;
    @FXML private TextArea 			  DescrizioneSkillTA;
    
    @FXML private Button 			  RimuoviImpiegatoButton;
    @FXML private Button			  NuovaValutazioneButton;
    
    @FXML private HBox                ButtonBox;
    @FXML private VBox                BoxInfo;

    private HomePageImpiegato homePageImpiegato;
    private Stage window;
    private Stage popup;
    
    private TitoloDaoInterface titoloDAO;
    private SkillDaoInterface SkillDAO;
    private progettoImpiegatoDao progettoImpiegatoDao;
    private ComuneDaoInterface comuneDao;
    
    private FinestraPopup 							finestraRimuoviImpiegatoDalProgetto;
    private FinestraAggiungiPartecipanteAlProgetto 	finestraAggiungiImpiegatoAlProgetto;
    private FormRegistrazioneRiunione			   	formRegistrazioneRiunione;
    
	private FormRegistrazioneValutazione formRegistrazioneValutazione;
    
    Progetto progetto;
    Impiegato projectManager;
    Impiegato impiegatoIniziale = new Impiegato("!", "Lista Vuota");

    public void setStage(Stage window, Stage popup)
    {
    	this.window = window;
    	this.popup = popup;
    }

    Connection connection;
    DBConnection dbConnection;
    ObservableList<Impiegato> lista = FXCollections.observableArrayList();
    ProgettoDaoInterface progettoDao;


	private SkillDao skillDao;

	private ObservableList<Skill> listaSkill;
	
	private MetodiComuni utils = new MetodiComuni();

    {
        try {
            dbConnection = new DBConnection();
            connection = dbConnection.getConnection();
            progettoDao = new ProgettoDao(connection);
            progettoImpiegatoDao = new progettoImpiegatoDao(connection);
            comuneDao = new ComuneDao(connection);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }


    public void inizializza(Impiegato projectManager, Progetto progetto) throws SQLException
    {
    	this.progetto = progetto;
    	this.projectManager = projectManager;
    	
        NomeProjectManagerLabel.setText(projectManager.toString().toUpperCase());
        NomeProgettoLabel.setText(progetto.getTitolo());
        
        lista = progettoDao.getPartecipanti(progetto);
        ListaPartecipantiLV.setItems(lista);
        
        if(lista.size() == 1)
            IstruzioniLabel.setText("Non ci sono partecipanti a questo progetto");
    }
    
    @FXML private void updateInfoImpiegato() {
    	Impiegato infoImpiegato = ListaPartecipantiLV.getSelectionModel().getSelectedItem();
    	
        if (infoImpiegato != null) {
			IstruzioniBox.setVisible(false);
			DescrizioneProgettoImpiegatoBox.setVisible(true);
			
			SkillBox.setVisible(false);
			
			NomeImpiegatoTF	.setText(infoImpiegato.toString());
			EmailTF			.setText(infoImpiegato.getEmail());
			
			try {
				ComuneDiNascitaTF.setText(comuneDao.getComuneBySigla(infoImpiegato.getComuneNascita()).getNomeComune());
			} catch (SQLException e) {
				ComuneDiNascitaTF.setText(infoImpiegato.getComuneNascita());
			}
			
			DataDiNascitaTF.setText(infoImpiegato.getDataNascita().toString());
			
			if (infoImpiegato.getCF().equals(progetto.getProjectManager().getCF())) {
				RuoloImpiegatoTF.setText("Project Manager");
				ButtonBox.setVisible(false);
			} else {
				ButtonBox.setVisible(true);
				try {
					RuoloImpiegatoTF.setText(progettoDao.getRuoloByImpiegatoProgetto(infoImpiegato, progetto));
				} catch (SQLException e) {
					RuoloImpiegatoTF.setText("");
				} 
			}
			
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
    
    public void RimuoviImpiegato(ActionEvent event) throws Exception {
		finestraRimuoviImpiegatoDalProgetto = new FinestraPopup();
		finestraRimuoviImpiegatoDalProgetto.start(popup, ListaPartecipantiLV.getSelectionModel().getSelectedItem(), progetto, this);

        lista = progettoDao.getPartecipanti(progetto);
        ListaPartecipantiLV.setItems(lista);
    }
    
    @FXML
    private void backHomePageImpiegato(ActionEvent event) throws Exception
    {
    	homePageImpiegato = new HomePageImpiegato(projectManager);
    	homePageImpiegato.start(window, popup);
    }
    
    public void aggiornaLista() throws SQLException {
        lista = progettoDao.getPartecipanti(progetto);
        ListaPartecipantiLV.setItems(lista);
    }
    
    public void DaDescrizioneProgettoAdIstruzioniBox() throws SQLException {
    	DescrizioneProgettoImpiegatoBox.setVisible(false);
    	IstruzioniBox.setVisible(true);
    }
    
    public void AggiungiImpiegatoAlProgetto(ActionEvent event) throws Exception {
		finestraAggiungiImpiegatoAlProgetto = new FinestraAggiungiPartecipanteAlProgetto();
		finestraAggiungiImpiegatoAlProgetto.start(window, popup, progetto);

    }
    
    @FXML private void programmaRiunione(ActionEvent event) {
    	formRegistrazioneRiunione = new FormRegistrazioneRiunione();
    	formRegistrazioneRiunione.start(window, popup, projectManager, progetto);
    }
    
    @FXML private void aggiungiValutazione() {
    	formRegistrazioneValutazione = new FormRegistrazioneValutazione();
    	formRegistrazioneValutazione.start(window, popup, ListaPartecipantiLV.getSelectionModel().getSelectedItem(), progetto);
    }
      
}
