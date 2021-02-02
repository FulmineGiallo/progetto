package controller;

 

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Locale;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Impiegato;
import model.Progetto;
import model.Ruolo;
import model.Skill;
import model.Titolo;
import model.Connection.DBConnection;
import model.Dao.ImpiegatoDao;
import model.Dao.ProgettoDao;
import model.Dao.RuoloDao;
import model.Dao.SkillDao;
import model.Dao.TitoloDAO;
import model.DaoInterface.ImpiegatoDaoInterface;
import model.DaoInterface.ProgettoDaoInterface;
import model.DaoInterface.RuoloDaoInterface;
import model.DaoInterface.SkillDaoInterface;
import model.DaoInterface.TitoloDaoInterface;

 

public class ControllerRicercaImpiegati {

	@FXML
    private AnchorPane RicercaImpiegati;
   @FXML
    private HBox IstruzioniBox;
    @FXML
    private Label IstruzioniLabel;
    @FXML
    private VBox Form;
    @FXML
    private ScrollPane FormScrollPane;
    @FXML
    private VBox RicercaSkillBox;
    @FXML
    private Label RicercaSkillLabel;
    @FXML
    private ComboBox<Titolo> RicercaSkillComboBox;
    @FXML
    private VBox SkillAggiunteBox;
    @FXML
    private Label SkillAggiunteLabel;
    @FXML
    private ListView<String> SkillAggiunteLV;
    @FXML
    private HBox SalarioMedioBox;
    @FXML
    private Label SalarioMedioLabel;
    @FXML
    private TextField SalarioMedioTF;
    @FXML
    private HBox NomeBox;
    @FXML
    private Label NomeLabel;
   	@FXML
    private TextField NomeTF;
    @FXML
    private HBox CognomeBox;
    @FXML
    private Label CognomeLabel;
    @FXML
    private TextField CognomeTF;
    @FXML
    private GridPane RIcercaImpiegatiButtonBar;
    @FXML
    private VBox OrdinamentoBox;
    @FXML
    private Label OrdinamentoLabel;
    @FXML
    private ComboBox<String> OrdinamentoComboBox;
    @FXML
    private Button RicercaImpiegatiButton;
    @FXML
    private AnchorPane ListaRicercaImpiegatiBox;
    @FXML
    private ListView<Impiegato> ListaRicercaImpiegatiLV;
    @FXML
    private AnchorPane ListaImpiegatiDaInserireBox;
    @FXML
    private VBox RuoloImpiegatoBox;
    @FXML
    private Label RuoloImpiegatoLabel;
    @FXML
    private ComboBox<Ruolo> RuoloImpiegatoComboBox;
    @FXML
    private Button AggiungiImpiegatoButton;

    private Stage window;
    private Stage popup;
    
    private Progetto progetto;
    ImpiegatoDaoInterface impiegatoDao;
    RuoloDaoInterface ruoliDao;
    TitoloDaoInterface titoloDao;
    
    private ObservableList<Impiegato> listaImpiegati = FXCollections.observableArrayList();
    private ObservableList<Ruolo> listaRuoli = FXCollections.observableArrayList();
    private ObservableList<Titolo> listaTitoli = FXCollections.observableArrayList();
    
    
    public void setStage(Stage window, Stage popup)
    {
    	this.window = window;
    	this.popup = popup;
    }
 
    
    Connection connection;
    DBConnection dbConnection;

    {
        try {
            dbConnection = new DBConnection();
            connection = dbConnection.getConnection();
            impiegatoDao = new ImpiegatoDao(connection);
            ruoliDao = new RuoloDao(connection);
            titoloDao = new TitoloDAO(connection);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    
    
    public void inizializza(Progetto progetto) throws SQLException {
    	this.progetto = progetto;
    	listaImpiegati = impiegatoDao.getAllImpiegati();
    	listaRuoli = ruoliDao.GetAllRuoli();
    	listaTitoli = titoloDao.titoliList();
    	ListaRicercaImpiegatiLV.setItems(listaImpiegati);
    	RicercaSkillComboBox.setItems(listaTitoli);
    	RuoloImpiegatoComboBox.setItems(listaRuoli);
//        lista = progettoDao.getPartecipanti(progetto);
//        ListaPartecipantiLV.setItems(lista);
//        updateInfoImpiegato();
    }

}