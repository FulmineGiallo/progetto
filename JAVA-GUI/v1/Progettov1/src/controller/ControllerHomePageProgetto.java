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
import model.Titolo;
import view.HomePageImpiegato;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Locale;

public class ControllerHomePageProgetto {

	HomePageImpiegato homeImpiegato;
	
    @FXML private AnchorPane 			HomePageProjectManager;
    
    @FXML private VBox 					ImpiegatoBox;
    @FXML private Label 				NomeProjectManagerLabel;
    @FXML private Label 				NomeProgettoLabel;
    
    @FXML private HBox 					ToolBar;
    @FXML private Button 				NuovaRiunioneButton;
    @FXML private Button 				AggiungiImpiegatoButton;
    @FXML private Button 				HomePageImpiegatoButton;
    
    @FXML private AnchorPane 			ListaPartecipantiBox;
    @FXML private Label 				ListaPartecipantiLabel;
    @FXML private ScrollPane 			ListaPartecipantiScrollPane;
    @FXML private ListView<Impiegato> 	ListaPartecipantiLV;
    
    @FXML private AnchorPane 			IstruzioniBox;
    @FXML private Label 				IstruzioniLabel;
    
    @FXML private AnchorPane 			DescrizioneProgettoImpiegatoBox;
    @FXML private AnchorPane 			DescrizioneProgettoImpiegatoPane;
    
    @FXML
    private VBox ProjectManagerBox;

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
    private progettoImpiegatoDao progettoImpiegatoDao;
    
    Progetto progetto;
    Impiegato impiegato;

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
    	this.impiegato = projectManager;
        NomeProjectManagerLabel.setText((impiegato.getNome() + " " + impiegato.getCognome()).toUpperCase(Locale.ROOT));
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
                IstruzioniBox.setVisible(false);
                
                DescrizioneProgettoImpiegatoBox.setVisible(true);
                NomeProgettoLabel.setVisible(true);
                
                DataCertificazioneSkillLabel.setVisible(false);
                DescrizioneSkillTA.setVisible(false);
                NomePartecipanteLabel.setText(ListaPartecipantiLV.getSelectionModel().getSelectedItem().getNome() + " " + ListaPartecipantiLV.getSelectionModel().getSelectedItem().getCognome());

                
                
            	try
                {
                    titoloDAO = new TitoloDAO(connection);
                    SkillDAO = new SkillDao(connection);
                    
                    SkillComboBox.setItems(titoloDAO.titoliListImpiegato(ListaPartecipantiLV.getSelectionModel().getSelectedItem()));

                    
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
    
    
    public void RimuoviImpiegato(ActionEvent event) throws Exception {
   
    	int idProgetto=0;
    	int eliminato=0;
    	
    	idProgetto=progettoDao.GetIdProgetto(progetto);
    	progettoImpiegatoDao = new progettoImpiegatoDao(connection);
    	
    	eliminato = progettoImpiegatoDao.EliminaImpiegatoDalProgetto(ListaPartecipantiLV.getSelectionModel().getSelectedItem(), idProgetto);
    	
        lista = progettoDao.getPartecipanti(progetto);
        ListaPartecipantiLV.setItems(lista);
    	
    	if(eliminato !=0)
    		System.out.println("impiegato eliminato");
    	
    	
    	
    }
    
    @FXML
    private void backHomePageImpiegato(ActionEvent event) throws Exception
    {
    	homePageImpiegato = new HomePageImpiegato(impiegato);
    	homePageImpiegato.start(window, popup);
    }
}
