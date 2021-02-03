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
import model.Dao.ProgettoDao;
import model.Dao.SkillDao;
import model.Dao.TitoloDAO;
import model.Dao.progettoImpiegatoDao;
import model.DaoInterface.ProgettoDaoInterface;
import model.Impiegato;
import model.Progetto;
import model.Skill;
import model.Titolo;
import view.FinestraAggiungiPartecipanteAlProgetto;
import view.FinestraPopup;
import view.FinestraRimuoviImpiegatoDalProgetto;
import view.FormRegistrazioneRiunione;
import view.HomePageImpiegato;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Locale;

public class ControllerHomePageProgetto {

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
    @FXML private ComboBox<Titolo> 	  SkillComboBox;
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
        
    private HomePageImpiegato homePageImpiegato;
    private Stage window;
    private Stage popup;
    
    private TitoloDAO titoloDAO;
    private SkillDao SkillDAO;
    private progettoImpiegatoDao progettoImpiegatoDao;
    
    FinestraPopup 						   finestraRimuoviImpiegatoDalProgetto;
    FinestraAggiungiPartecipanteAlProgetto finestraAggiungiImpiegatoAlProgetto;
    FormRegistrazioneRiunione			   formRegistrazioneRiunione;
    
    Progetto progetto;
    Impiegato projectManager;

    public void setStage(Stage window, Stage popup)
    {
    	this.window = window;
    	this.popup = popup;
    }


    Connection connection;
    DBConnection dbConnection;
    ObservableList<Impiegato> lista = FXCollections.observableArrayList();
    ProgettoDaoInterface progettoDao;

    {
        try {
            dbConnection = new DBConnection();
            connection = dbConnection.getConnection();
            progettoDao = new ProgettoDao(connection);



        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }



    public void inizializza(Impiegato projectManager, Progetto progetto) throws SQLException {
    	this.progetto = progetto;
    	this.projectManager = projectManager;
        NomeProjectManagerLabel.setText(projectManager.toString().toUpperCase());
        NomeProgettoLabel.setText(progetto.getTitolo());
        lista = progettoDao.getPartecipanti(progetto);
        ListaPartecipantiLV.setItems(lista);
        updateInfoImpiegato();
    }
    
    public void updateInfoImpiegato()
    {
        ListaPartecipantiLV.setOnMouseClicked(new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent mouseEvent) {
            	
            	Impiegato infoImpiegato = ListaPartecipantiLV.getSelectionModel().getSelectedItem();
            	
                IstruzioniBox.setVisible(false);
                DescrizioneProgettoImpiegatoBox.setVisible(true);
                
                SkillBox.setVisible(false);
                NomeImpiegatoTF.setText(infoImpiegato.toString());
                EmailTF.setText(infoImpiegato.getEmail());
                ComuneDiNascitaTF.setText(infoImpiegato.getComuneNascita());

            	try
                {
                    titoloDAO = new TitoloDAO(connection);
                    SkillDAO = new SkillDao(connection);
                    RimuoviImpiegatoButton.setVisible(true);
                    
                    SkillComboBox.setItems(titoloDAO.titoliListImpiegato(infoImpiegato));

                    if(infoImpiegato.getCF().equals( progetto.getProjectManager().getCF())) {
                    	RimuoviImpiegatoButton.setVisible(false);
                    }
                    
                    SkillComboBox.getSelectionModel().selectedItemProperty().addListener( (options, oldValue, newValue) -> {

                    	SkillBox.setVisible(true);

                    if(SkillComboBox.getSelectionModel().getSelectedItem() != null) {	
                    	TitoloSkillLabel.setVisible(true);
                    	TitoloSkillTF.setVisible(true);
                    	TitoloSkillTF.setText(SkillComboBox.getSelectionModel().getSelectedItem().toString());
                    	
                    	DataCertificazioneSkillLabel.setVisible(true);
                    	DescrizioneSkillTA.setVisible(true);
                    	try {
							DescrizioneSkillTA.setText(SkillDAO.descrizioneCertificazione(SkillComboBox.getSelectionModel().getSelectedItem().toString(), infoImpiegato));
                    		DataCertificazioneTF.setText(SkillDAO.dataCertificazione(SkillComboBox.getSelectionModel().getSelectedItem().toString(), infoImpiegato));
						} catch (SQLException e) {
							e.printStackTrace();
						}
                    	
                    }else {
                    	TitoloSkillLabel.setVisible(false);
                    	DataCertificazioneSkillLabel.setVisible(false);
                    	DescrizioneSkillTA.setVisible(false);
                    	TitoloSkillTF.setVisible(false);
                    	DataCertificazioneTF.setVisible(false);
                    }
                    
                   });
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
                
                
            }
        });
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
      
}
