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
import model.Dao.RiunioneDao;
import model.Dao.RiunioneImpiegatoDao;
import model.Dao.SkillDao;
import model.Dao.TitoloDAO;
import model.Dao.progettoImpiegatoDao;
import model.DaoInterface.RiunioneDaoInterface;
import view.FinestraPopup;
import view.HomePageImpiegato;
import view.HomePageOrganizzatore;

public class ControllerHomePageOrganizzatore {

    @FXML
    private AnchorPane HomePageOrganizzatore;

    @FXML
    private VBox OrganizzatoreBox;

    @FXML
    private Label NomeOrganizzatoreLabel;

    @FXML
    private Label NomeRiunioneLabel;

    @FXML
    private HBox ToolBar;

    @FXML
    private Button AggiungiImpiegatoButton;

    @FXML
    private Button HomePageImpiegatoButton;

    @FXML
    private AnchorPane ListaPartecipantiBox;

    @FXML
    private Label ListaPartecipantiLabel;

    @FXML
    private ScrollPane ListaPartecipantiScrollPane;

    @FXML
    private ListView<Impiegato> ListaPartecipantiLV;

    @FXML
    private AnchorPane IstruzioniBox;

    @FXML
    private Label IstruzioniLabel;

    @FXML
    private AnchorPane DescrizioneRiunioneImpiegatoBox;

    @FXML
    private AnchorPane DescrizioneRiunioneImpiegatoPane;

    @FXML
    private Label NomePartecipanteLabel;

    @FXML
    private Label SkillComboBoxLabel;

    @FXML
    private ComboBox<Titolo> SkillComboBox;

    @FXML
    private VBox SkillBox;

    @FXML
    private Label TitoloSkillLabel;

    @FXML
    private Label DataCertificazioneSkillLabel;

    @FXML
    private TextArea DescrizioneSkillTA;

    @FXML
    private Button RimuoviImpiegatoButton;

    private HomePageImpiegato homePageImpiegato;
    
    private Stage window;
    private Stage popup;
    
    private TitoloDAO titoloDAO;
    private SkillDao SkillDAO;
    private RiunioneImpiegatoDao riunioneImpiegatoDao;
    private int idriunione;
    private FinestraPopup finestraRimuoviImpiegatoDallaRiunione;
    
    
    Riunione riunione;
    Impiegato Organizzatore;

    public void setStage(Stage window, Stage popup)
    {
    	this.window = window;
    	this.popup = popup;
    }


    Connection connection;
    DBConnection dbConnection;
    ObservableList<Impiegato> lista = FXCollections.observableArrayList();
    RiunioneDaoInterface riunioneDao;

    {
        try {
            dbConnection = new DBConnection();
            connection = dbConnection.getConnection();
            riunioneDao = new RiunioneDao(connection);



        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }



    public void inizializza(Impiegato Organizzatore, Riunione riunione) throws SQLException {
    	this.riunione = riunione;
    	this.Organizzatore = Organizzatore;
        NomeOrganizzatoreLabel.setText((Organizzatore.getNome()+ " " + Organizzatore.getCognome()).toUpperCase(Locale.ROOT));
        NomeRiunioneLabel.setText((riunione.getTitolo()));
        idriunione = riunioneDao.GetIdRiunione(riunione);
        lista = riunioneDao.getPartecipanti(idriunione);
        ListaPartecipantiLV.setItems(lista);
        
        updateInfoImpiegato();
        updateInfoBox();
    }
    
    @FXML
    private void backHomePageImpiegato(ActionEvent event) throws Exception
    {
    	homePageImpiegato = new HomePageImpiegato(Organizzatore);
    	homePageImpiegato.start(window, popup);
    }
    
    public void updateInfoImpiegato()
    {
        ListaPartecipantiLV.setOnMouseClicked(new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent mouseEvent) {
                IstruzioniBox.setVisible(false);
                
                DataCertificazioneSkillLabel.setVisible(false);
                DescrizioneSkillTA.setVisible(false);
                DescrizioneRiunioneImpiegatoBox.setVisible(true);
                NomeRiunioneLabel.setVisible(true);
                NomePartecipanteLabel.setText(ListaPartecipantiLV.getSelectionModel().getSelectedItem().getNome() + " " + ListaPartecipantiLV.getSelectionModel().getSelectedItem().getCognome());

                
                
            	try
                {
                    titoloDAO = new TitoloDAO(connection);
                    SkillDAO = new SkillDao(connection);
                    RimuoviImpiegatoButton.setVisible(true);
                    
                    SkillComboBox.setItems(titoloDAO.titoliListImpiegato(ListaPartecipantiLV.getSelectionModel().getSelectedItem()));

                    if(ListaPartecipantiLV.getSelectionModel().getSelectedItem().getCF().equals( riunione.getCFOrganizzatore())) {
                    	RimuoviImpiegatoButton.setVisible(false);
                    }
                    SkillComboBox.getSelectionModel().selectedItemProperty().addListener( (options, oldValue, newValue) -> {

                    	SkillBox.setVisible(true);

                    if(SkillComboBox.getSelectionModel().getSelectedItem() != null) {	
                    	TitoloSkillLabel.setVisible(true);
                    	TitoloSkillLabel.setText(SkillComboBox.getSelectionModel().getSelectedItem().toString());
                    	
                    	DataCertificazioneSkillLabel.setVisible(true);
                    	DescrizioneSkillTA.setVisible(true);
                    	try {
							DescrizioneSkillTA.setText(SkillDAO.descrizioneCertificazione(SkillComboBox.getSelectionModel().getSelectedItem().toString(), ListaPartecipantiLV.getSelectionModel().getSelectedItem()));
                    		DataCertificazioneSkillLabel.setText(SkillDAO.dataCertificazione(SkillComboBox.getSelectionModel().getSelectedItem().toString(), ListaPartecipantiLV.getSelectionModel().getSelectedItem()));
						} catch (SQLException e) {
							e.printStackTrace();
						}
                    	
                    }else {
                    	TitoloSkillLabel.setVisible(false);
                    	DataCertificazioneSkillLabel.setVisible(false);
                    	DescrizioneSkillTA.setVisible(false);
                    }
                    
                   });
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
                
                
            }
        });
    }
    
    public void updateInfoBox()
    {
        RimuoviImpiegatoButton.setOnMouseClicked(new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent mouseEvent) {
            	DescrizioneRiunioneImpiegatoBox.setVisible(false);
                IstruzioniBox.setVisible(true);
            }
        });
    }
    
    public void RimuoviImpiegato(ActionEvent event) throws Exception {
    	   
    	finestraRimuoviImpiegatoDallaRiunione = new FinestraPopup();
		finestraRimuoviImpiegatoDallaRiunione.start(popup, ListaPartecipantiLV.getSelectionModel().getSelectedItem(), riunione, this);

        idriunione = riunioneDao.GetIdRiunione(riunione);
        lista = riunioneDao.getPartecipanti(idriunione);
        ListaPartecipantiLV.setItems(lista);

    }
    
    public void AggiornaLista() throws SQLException {
    	
        idriunione = riunioneDao.GetIdRiunione(riunione);
        lista = riunioneDao.getPartecipanti(idriunione);
        ListaPartecipantiLV.setItems(lista);
    }
    
    public void DaDescrizioneRiunioneAdIstruzioniBox() throws SQLException {
    	DescrizioneRiunioneImpiegatoBox.setVisible(false);
    	IstruzioniBox.setVisible(true);
    }
    
}
