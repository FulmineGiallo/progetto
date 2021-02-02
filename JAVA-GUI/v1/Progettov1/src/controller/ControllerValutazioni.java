package controller;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Connection.DBConnection;
import model.Dao.ImpiegatoDao;
import model.Dao.ProgettoDao;
import model.Dao.ValutazioneDao;
import model.DaoInterface.ImpiegatoDaoInterface;
import model.DaoInterface.ValutazioneDaoInterface;
import model.Grado;
import model.Impiegato;
import model.Valutazione;
import view.HomePageImpiegato;

public class ControllerValutazioni {

    @FXML private AnchorPane 			HomePageValutazioni;
    @FXML private VBox 					ImpiegatoBox;
    @FXML private Label 				NomeImpiegatoLabel;
    @FXML private Label 				GradoImpiegatoLabel;
    @FXML private HBox 					ToolBar;
    @FXML private Button 				HomePageImpiegatoButton;
    @FXML private AnchorPane 			ListaValutazioniBox;
    @FXML private Label 				ListaValutazioniLabel;
    @FXML private ListView<Valutazione> ListaValutazioniLV;
    @FXML private AnchorPane 			IstruzioniBox;
    @FXML private Label 				IstruzioniLabel;
    @FXML private AnchorPane 			DescrizioneValutazioneBox;
    @FXML private AnchorPane 			DescrizioneValutazionePane;
    @FXML private Label 				TitoloValutazioneLabel;
    @FXML private Label 				RecensoreValutazioneLabel;
    @FXML private HBox 					StelleOkBox;
    @FXML private ImageView 			StellaOk1;
    @FXML private ImageView 			StellaOk2;
    @FXML private ImageView 			StellaOk3;
    @FXML private ImageView 			StellaOk4;
    @FXML private ImageView 			StellaOk5;
    @FXML private HBox 					StelleNoBox;
    @FXML private ImageView 			PrimaStellaNo;
    @FXML private ImageView 			SecondaStellaNo;
    @FXML private ImageView 			TerzaStellaNo;
    @FXML private ImageView 			QuartaStellaNo;
    @FXML private ImageView 			QuintaStellaNo;
    @FXML private Label 				DataValutazioneLabel;
    @FXML private TextArea 				DescrizioneValutazioneTA;
        
    private HomePageImpiegato homePageImpiegato;
    private Stage window;
    private Stage popup;
    
    private Impiegato impiegato;
	private ImpiegatoDaoInterface Recensore;

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
    
    
    {
        try {
            Recensore = new ImpiegatoDao(connection);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    
    
    ObservableList<Valutazione> lista = FXCollections.observableArrayList();
    ValutazioneDaoInterface valutazioni;
    {
        try {
            valutazioni = new ValutazioneDao(connection);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    
    public void setStage(Stage window, Stage popup) {
    	this.window = window;
    	this.popup = popup;
    }
    
    public void inizializza(Impiegato impiegato) throws SQLException {
        this.impiegato = impiegato;
        
        NomeImpiegatoLabel.setText(impiegato.toString().toUpperCase());
        GradoImpiegatoLabel.setText(impiegato.getGrado());
        lista.addAll(valutazioni.getValutazioni(impiegato));
        ListaValutazioniLV.setItems(lista);
        updateValutazioni();
    }

    public void updateValutazioni()
    {
        ListaValutazioniLV.setOnMouseClicked(new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent mouseEvent)
            {
            	int NumeroStelle;
            	StellaOk1.setVisible(false);
            	StellaOk2.setVisible(false);
            	StellaOk3.setVisible(false);
            	StellaOk4.setVisible(false);
            	StellaOk5.setVisible(false);
            	
                IstruzioniBox.setVisible(false);
                DescrizioneValutazioneBox.setVisible(true);
                TitoloValutazioneLabel.setText("Titolo: " + ListaValutazioniLV.getSelectionModel().getSelectedItem().getTitolo());
                String CFrecensore = ListaValutazioniLV.getSelectionModel().getSelectedItem().getCFrecensore();
                
                try {
                	RecensoreValutazioneLabel.setText("Recensore: " + Recensore.getNomeCognomeConCF(CFrecensore).toUpperCase());
				} catch (SQLException e) {
					e.printStackTrace();
				}
                
                DataValutazioneLabel.setText("Data recensione: " + ListaValutazioniLV.getSelectionModel().getSelectedItem().getDataV().toString());
                DescrizioneValutazioneTA.setText("Descrizione valutazione: " + ListaValutazioniLV.getSelectionModel().getSelectedItem().getRecensione());
                
                NumeroStelle = ListaValutazioniLV.getSelectionModel().getSelectedItem().getStelle();
                
                switch(NumeroStelle) {
                case 5:
                	StellaOk1.setVisible(true);
                	StellaOk2.setVisible(true);
                	StellaOk3.setVisible(true);
                	StellaOk4.setVisible(true);
                	StellaOk5.setVisible(true);
                case 4:
                	StellaOk1.setVisible(true);
                	StellaOk2.setVisible(true);
                	StellaOk3.setVisible(true);
                	StellaOk4.setVisible(true);
                case 3:
                	StellaOk1.setVisible(true);
                	StellaOk2.setVisible(true);
                	StellaOk3.setVisible(true);
                case 2:
                	StellaOk1.setVisible(true);
                	StellaOk2.setVisible(true);
                case 1:
                	StellaOk1.setVisible(true);
                }
                
                	
                StellaOk1.setVisible(true);
                

            }
        });
    }
   public void backHomePageImpiegato(ActionEvent event) throws Exception {
   		homePageImpiegato = new HomePageImpiegato(impiegato);
   		homePageImpiegato.start(window, popup);
    }
}
