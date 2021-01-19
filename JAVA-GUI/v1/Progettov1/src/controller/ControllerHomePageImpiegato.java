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
import view.FormRegistrazioneProgetto;
import view.HomePageBenvenuto;
import view.HomePageProgetto;
import view.HomePageValutazioni;

public class ControllerHomePageImpiegato {
	@FXML private AnchorPane 			HomePageImpiegato;
	
	@FXML private VBox					ImpiegatoBox;
	@FXML private Label 				NomeImpiegatoLabel;
	@FXML private Label 				GradoImpiegatoLabel;
	
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
	@FXML private Button                salvaModifiche;

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
	
	@FXML private Label 				ListaRiunioniLabel;
	@FXML private ScrollPane 			ListaRiunioniScrollPane;
	@FXML private ListView<Riunione> 	RiunioniLV;
	@FXML private Label                 salario;

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
        ListaProgettiLV.setItems(listaProgetti);
        
        listaRiunioni.addAll(riunioni.getRiunioniImpiegato(impiegato));
        RiunioniLV.setItems(listaRiunioni);
        
        IstruzioniBox.setVisible(true);
        DescrizioneProgettoBox.setVisible(false);
        salario.setText("Calcolare");
        /*RIchiamare Dao Salario */

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
                gestisciBox(false);

                DescrizioneProgettoTA.setText(ListaProgettiLV.getSelectionModel().getSelectedItem().getDescrizione());
                DataDiInizioProgettoLabel.setText(String.valueOf(ListaProgettiLV.getSelectionModel().getSelectedItem().getDataInizio()));
                DataDiFineProgettoLabel.setText(String.valueOf(ListaProgettiLV.getSelectionModel().getSelectedItem().getDataFine()));
                DataDiScadenzaProgettoLabel.setText(String.valueOf(ListaProgettiLV.getSelectionModel().getSelectedItem().getScadenza()));

                if(ListaProgettiLV.getSelectionModel().getSelectedItem().getProjectManager() == impiegato)
                {
                    ProjectManagerBox.setVisible(true);
                    salvaModifiche.setVisible(false);
                    /* Se il bottone gestione progetto viene cliccato */

                    /* Se il project manager vuole modificare le informazioni del progetto */
                    ModificaProgettoButton.setOnAction(new EventHandler<ActionEvent>()
                    {
                        @Override
                        public void handle(ActionEvent event)
                        {
                            gestisciBox(true);
                            salvaModifiche.setVisible(true);
                            salvaModifiche.setOnAction(new EventHandler<ActionEvent>()
                            {
                                @Override
                                public void handle(ActionEvent event)
                                {
                                    gestisciBox(false);
                                    LocalDate datainizio;
                                    LocalDate dataFine;
                                    LocalDate dataScadenza;
                                    try
                                    {
                                        ListaProgettiLV.getSelectionModel().getSelectedItem().setDescrizione(DescrizioneProgettoTA.getText());
                                        datainizio = LocalDate.parse(DataDiInizioProgettoLabel.getText());
                                        dataFine = LocalDate.parse(DataDiFineProgettoLabel.getText());
                                        dataScadenza = LocalDate.parse(DataDiScadenzaProgettoLabel.getText());

                                        ListaProgettiLV.getSelectionModel().getSelectedItem().setDataInizio(Date.valueOf(datainizio));
                                        ListaProgettiLV.getSelectionModel().getSelectedItem().setDataFine(Date.valueOf(dataFine));
                                        ListaProgettiLV.getSelectionModel().getSelectedItem().setScadenza(Date.valueOf(dataScadenza));


                                        updateEffettuato = progetti.updateInfoProgetto(ListaProgettiLV.getSelectionModel().getSelectedItem());
                                        salvaModifiche.setVisible(false);

                                    } catch (SQLException throwables)
                                    {
                                        throwables.printStackTrace();
                                    }

                                }
                            });

                        }
                    });

                }
                else
                    ProjectManagerBox.setVisible(false);
            }
        });
    }

    /*Metodo che gestisce le modifice della box */
    private void gestisciBox(boolean state)
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
    	//inserire conferma di esecuzione logout
        homePageBenvenuto = new HomePageBenvenuto();
        homePageBenvenuto.start(window, popup);
    }
}