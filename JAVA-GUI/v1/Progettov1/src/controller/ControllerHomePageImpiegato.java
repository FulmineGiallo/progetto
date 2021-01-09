package controller;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

import java.awt.event.MouseEvent;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Locale;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import model.Connection.DBConnection;
import model.Dao.ProgettoDao;
import model.DaoInterface.ProgettoDaoInterface;
import model.Impiegato;
import model.Progetto;
import view.HomePageBenvenuto;
import view.HomePageProgetto;
import view.HomePageValutazioni;

public class ControllerHomePageImpiegato
{
	 HomePageProgetto homeProgetto;
	 HomePageValutazioni homeValutazioni;
	 HomePageBenvenuto homePageBenvenuto;
	 Impiegato impiegato = null;
	 @FXML
     private Button buttonValutazioni;
     @FXML
     private Button buttonCreaProgetto;
    @FXML
    private AnchorPane HomePageImpiegato;

    @FXML
    private GridPane ToolBar;

    @FXML
    private Label NomeImpiegatoLabel;

    @FXML
    private Label GradoImpiegatoLabel;

    @FXML
    private Button ValutazioniButton;

    @FXML
    private Button NuovoProgettoButton;

    @FXML
    private Button LogoutButton;

    @FXML
    private GridPane ProgettiRiunioniBox;

    @FXML
    private GridPane ProgettiBox;

    @FXML
    private AnchorPane ListaProgettiBox;

    @FXML
    private Label ListaProgettiLabel;

    @FXML
    private ScrollPane ListaProgettiScrollPane;

    @FXML
    private ListView<Progetto> ListaProgettiLV;

    @FXML
    private AnchorPane DescrizioneProgettoBox;

    @FXML
    private Label TitoloProgettoLabel;

    @FXML
    private TextArea DescrizioneProgettoTA;

    @FXML
    private Label DataDiInizioLabel;

    @FXML
    private Label DataDiFineLabel;

    @FXML
    private Label DataDiScadenzaLabel;

    @FXML
    private Label NoteLabel;

    @FXML
    private Button GestioneProgettoButton;

    @FXML
    private Label ListaRiunioniLabel;

    @FXML
    private ScrollPane ListaRiunioniScrollPane;

    @FXML
    private ListView<?> RiunioniLV;
     ObservableList<Progetto> listaProgetti = FXCollections.observableArrayList();
     Connection connection;
     DBConnection dbConnection;
    {
        try {
            dbConnection = new DBConnection();
            connection = dbConnection.getConnection();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    ProgettoDaoInterface progetti;
    {
        try {
            progetti = new ProgettoDao(connection);


        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void inizializza(Impiegato impiegato) throws SQLException {
    	this.impiegato = impiegato;

    	NomeImpiegatoLabel.setText((impiegato.getNome() +" "+ impiegato.getCognome()).toUpperCase(Locale.ROOT));
        GradoImpiegatoLabel.setText(impiegato.getGrado());
        listaProgetti.addAll(progetti.getProgettiImpiegato(impiegato));
        ListaProgettiLV.setItems(listaProgetti);
        updateInfoProgetto();
    }

    public void updateInfoProgetto()
    {
        ListaProgettiLV.setOnMouseClicked(new EventHandler<javafx.scene.input.MouseEvent>()
        {
            @Override
            public void handle(javafx.scene.input.MouseEvent mouseEvent)
            {
                DescrizioneProgettoTA.setText(ListaProgettiLV.getSelectionModel().getSelectedItem().getDescrizione());
                DataDiInizioLabel.setText(String.valueOf(ListaProgettiLV.getSelectionModel().getSelectedItem().getDataInizio()));
                DataDiFineLabel.setText(String.valueOf(ListaProgettiLV.getSelectionModel().getSelectedItem().getDataFine()));
                DataDiScadenzaLabel.setText(String.valueOf(ListaProgettiLV.getSelectionModel().getSelectedItem().getScadenza()));
            }
        });
    }

    @FXML
    public void CreateProject(ActionEvent actionEvent) throws Exception
    {
    	PrintWriter writer = null;
        homeProgetto = new HomePageProgetto(writer);

        Stage stage = (Stage) buttonCreaProgetto.getScene().getWindow();
        homeProgetto.start(stage);
    	
    }
    
    
    @FXML
    public void VisualizzaValutazioni(ActionEvent event) throws Exception {

    	PrintWriter writer = null;
        homeValutazioni = new HomePageValutazioni(writer, impiegato);

        Stage stage = (Stage) buttonValutazioni.getScene().getWindow();
        homeValutazioni.start(stage);
    	
    }
    
    @FXML
    public void Logout(ActionEvent event) throws Exception
    {
        PrintWriter writer = null;
        homePageBenvenuto = new HomePageBenvenuto(writer);
        Stage stage = (Stage) LogoutButton.getScene().getWindow();
        homePageBenvenuto.start(stage);
    }
}
    
  
