package controller;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Locale;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Impiegato;
import model.Riunione;
import model.Connection.DBConnection;
import model.Dao.RiunioneDao;
import model.DaoInterface.RiunioneDaoInterface;
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
    private ComboBox<?> SkillComboBox;

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
        lista = riunioneDao.getPartecipanti(riunione);
        ListaPartecipantiLV.setItems(lista);
    }
    
    @FXML
    private void backHomePageImpiegato(ActionEvent event) throws Exception
    {
    	homePageImpiegato = new HomePageImpiegato(Organizzatore);
    	homePageImpiegato.start(window, popup);
    }
    
}
