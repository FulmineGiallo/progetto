package controller;

import java.sql.Connection;
import java.sql.SQLException;

import java.io.PrintWriter;
import java.util.Locale;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
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
import view.FormRegistrazioneProgetto;
import view.HomePageBenvenuto;
import view.HomePageProgetto;
import view.HomePageValutazioni;

public class ControllerHomePageImpiegato
{
	FormRegistrazioneProgetto registrazioneProgetto;
	HomePageValutazioni homeValutazioni;
	HomePageBenvenuto homePageBenvenuto;
	Impiegato impiegato = null;
	
	@FXML private AnchorPane 			HomePageImpiegato;
	
	@FXML private GridPane 				ToolBar;
	@FXML private Label 				NomeImpiegatoLabel;
	@FXML private Label 				GradoImpiegatoLabel;
	@FXML private Button 				ValutazioniButton;
	@FXML private Button 				NuovoProgettoButton;
	@FXML private Button 				LogoutButton;
	
	@FXML private GridPane 				ProgettiRiunioniBox;
	
	@FXML private GridPane 				ProgettiBox;
	@FXML private AnchorPane 			ListaProgettiBox;
	@FXML private Label 				ListaProgettiLabel;
	@FXML private ScrollPane 			ListaProgettiScrollPane;
	@FXML private ListView<Progetto>	ListaProgettiLV;
	
	@FXML private AnchorPane			IstruzioniBox;
	@FXML private Label					IstruzioniLabel;
	
	@FXML private AnchorPane			DescrizioneProgettoBox;
	@FXML private Label 				TitoloProgettoLabel;
	@FXML private AnchorPane 			DescrizioneProgettoPane;
	@FXML private TextArea 				DescrizioneProgettoTA;
	@FXML private Label 				DataDiInizioLabel;
	@FXML private Label 				DataDiFineLabel;
	@FXML private Label 				DataDiScadenzaLabel;
	@FXML private Label 				NoteLabel;
	
	@FXML private AnchorPane			ProjectManagerBox;
	@FXML private Button 				GestioneProgettoButton;
	@FXML private Button 				ModificaButton;
	@FXML private Label 				ListaRiunioniLabel;
	@FXML private ScrollPane 			ListaRiunioniScrollPane;
	@FXML private ListView<Riunione> 	RiunioniLV;
    
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
    
    public void inizializza(Impiegato impiegato) throws SQLException {
    	this.impiegato = impiegato;
    	
    	NomeImpiegatoLabel.setText((impiegato.getNome() +" "+ impiegato.getCognome()).toUpperCase(Locale.ROOT));
        GradoImpiegatoLabel.setText(impiegato.getGrado());
        
        listaProgetti.addAll(progetti.getProgettiImpiegato(impiegato));
        ListaProgettiLV.setItems(listaProgetti);
        
        listaRiunioni.addAll(riunioni.getRiunioniImpiegato(impiegato));
        RiunioniLV.setItems(listaRiunioni);
        
        IstruzioniBox.setVisible(true);
        DescrizioneProgettoBox.setVisible(false);
        
        updateInfoProgetto();
    }

    public void updateInfoProgetto()
    {
        ListaProgettiLV.setOnMouseClicked(new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent mouseEvent) {
                IstruzioniBox.setVisible(false);
                DescrizioneProgettoBox.setVisible(true);
                DescrizioneProgettoTA.setText(ListaProgettiLV.getSelectionModel().getSelectedItem().getDescrizione());
                DataDiInizioLabel.setText(String.valueOf(ListaProgettiLV.getSelectionModel().getSelectedItem().getDataInizio()));
                DataDiFineLabel.setText(String.valueOf(ListaProgettiLV.getSelectionModel().getSelectedItem().getDataFine()));
                DataDiScadenzaLabel.setText(String.valueOf(ListaProgettiLV.getSelectionModel().getSelectedItem().getScadenza()));

                if(ListaProgettiLV.getSelectionModel().getSelectedItem().getProjectManager() == impiegato)
                {
                    ProjectManagerBox.setVisible(true);
                    /* Se il bottone gestione progetto viene cliccato */

                }
                else
                    ProjectManagerBox.setVisible(false);
            }
        });
    }

    @FXML
    public void CreaProgetto(ActionEvent actionEvent) throws Exception {
    	PrintWriter writer = null;
        registrazioneProgetto = new FormRegistrazioneProgetto(writer, impiegato);

        Stage stage = (Stage) NuovoProgettoButton.getScene().getWindow();
        registrazioneProgetto.start(stage);
    }
    
    @FXML
    public void VisualizzaValutazioni(ActionEvent event) throws Exception {

    	PrintWriter writer = null;
        homeValutazioni = new HomePageValutazioni(writer, impiegato);

        Stage stage = (Stage) ValutazioniButton.getScene().getWindow();
        homeValutazioni.start(stage);
    }
    
    @FXML
    public void EffettuaLogout(ActionEvent event) throws Exception {
        homePageBenvenuto = new HomePageBenvenuto();
        homePageBenvenuto.start(new Stage());
    }
}