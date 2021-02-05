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
import javafx.scene.control.TextField;
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
import utilities.MetodiComuni;
import model.Grado;
import model.Impiegato;
import model.Valutazione;
import view.HomePageImpiegato;

public class ControllerValutazioni {

    @FXML private AnchorPane 			HomePageValutazioni;
    
    @FXML private VBox 					ImpiegatoBox;
    @FXML private Label 				NomeImpiegatoLabel;
    @FXML private Label 				NumeroValutazioniLabel;
    
    @FXML private HBox 					ToolBar;
    @FXML private Button 				HomePageImpiegatoButton;
    
    @FXML private AnchorPane 			ListaValutazioniBox;
    @FXML private Label 				ListaValutazioniLabel;
    @FXML private ListView<Valutazione> ListaValutazioniLV;
    
    @FXML private AnchorPane 			IstruzioniBox;
    @FXML private Label 				IstruzioniLabel;
    
    @FXML private AnchorPane 			DescrizioneValutazioneBox;
    @FXML private ScrollPane 			DescrizioneValutazioneScrollPane;
    
    @FXML private HBox					TitoloValutazioneBox;
    @FXML private Label 				TitoloValutazioneLabel;
    @FXML private TextField				TitoloValutazioneTF;
    
    @FXML private HBox					RecensoreValutazioneBox;
    @FXML private Label 				RecensoreValutazioneLabel;
    @FXML private TextField				RecensoreValutazioneTF;
    
    @FXML private HBox 					StelleOkBox;
    @FXML private ImageView 			StellaOk1;
    @FXML private ImageView 			StellaOk2;
    @FXML private ImageView 			StellaOk3;
    @FXML private ImageView 			StellaOk4;
    @FXML private ImageView 			StellaOk5;
    
    @FXML private HBox 					StelleNoBox;
    @FXML private ImageView 			StellaNo1;
    @FXML private ImageView 			StellaNo2;
    @FXML private ImageView 			StellaNo3;
    @FXML private ImageView 			StellaNo4;
    @FXML private ImageView 			StellaNo5;
    
    @FXML private Label 				DataValutazioneLabel;
    
    @FXML private TextArea 				DescrizioneValutazioneTA;
    
    private MetodiComuni utils = new MetodiComuni();
        
    private HomePageImpiegato homePageImpiegato;
    
    private Stage window;
    private Stage popup;
    
    private Impiegato	recensito;
    private Valutazione infoValutazione;
    
    private ValutazioneDaoInterface valutazioniDao;
	
	private Valutazione valutazioneIniziale = new Valutazione(null, null, "Non ci sono ancora valutazioni", -1, false);

    Connection connection;
    DBConnection dbConnection;    
    
    ObservableList<Valutazione> listaValutazioni = FXCollections.observableArrayList();
    
    public void setStage(Stage window, Stage popup) {
    	this.window = window;
    	this.popup = popup;
    }
    
    public void inizializza(Impiegato recensito) throws SQLException {
    	this.recensito = recensito;
    	
        try {
            dbConnection = new DBConnection();
            connection = dbConnection.getConnection();
            
            valutazioniDao = new ValutazioneDao(connection);
            listaValutazioni.addAll(valutazioniDao.getValutazioni(recensito));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        
		NomeImpiegatoLabel.setText(recensito.toString().toUpperCase());
        
        if (listaValutazioni.isEmpty()) {
			ListaValutazioniLV.getItems().add(valutazioneIniziale);
			NumeroValutazioniLabel.setText("Nessuna valutazione");
		} else {
			ListaValutazioniLV.setItems(listaValutazioni);
			NumeroValutazioniLabel.setText(listaValutazioni.size() + " valutazioni");
		}
    }
    
    @FXML private void visualizzaInformazioniValutazione(MouseEvent event) {
    	if (!ListaValutazioniLV.getItems().contains(valutazioneIniziale)) {
    		
    		infoValutazione = ListaValutazioniLV.getSelectionModel().getSelectedItem();
    		
			StellaOk1.setVisible(false);
			StellaOk2.setVisible(false);
			StellaOk3.setVisible(false);
			StellaOk4.setVisible(false);
			StellaOk5.setVisible(false);
			
			IstruzioniBox.setVisible(false);
			DescrizioneValutazioneBox.setVisible(true);
			
			TitoloValutazioneTF.setText(infoValutazione.getTitolo());
			RecensoreValutazioneTF.setText(infoValutazione.getRecensore().toString());
			
			DataValutazioneLabel.setText("Data recensione: " + infoValutazione.getDataValutazione());
			switch(utils.controlloStringa(infoValutazione.getRecensione(), "")) {
				case 1:
					DescrizioneValutazioneTA.setText("Nessun commento");
					break;
				default:
					DescrizioneValutazioneTA.setText(infoValutazione.getRecensione());
			}
			
			switch (infoValutazione.getStelle()) {
				case 5:
					StellaOk1.setVisible(true);
					StellaOk2.setVisible(true);
					StellaOk3.setVisible(true);
					StellaOk4.setVisible(true);
					StellaOk5.setVisible(true);
					break;
				case 4:
					StellaOk1.setVisible(true);
					StellaOk2.setVisible(true);
					StellaOk3.setVisible(true);
					StellaOk4.setVisible(true);
					break;
				case 3:
					StellaOk1.setVisible(true);
					StellaOk2.setVisible(true);
					StellaOk3.setVisible(true);
					break;
				case 2:
					StellaOk1.setVisible(true);
					StellaOk2.setVisible(true);
					break;
				case 1:
					StellaOk1.setVisible(true);
					break;
			}
		}
    }
    
    @FXML private void backHomePageImpiegato(ActionEvent event) throws Exception {
   		homePageImpiegato = new HomePageImpiegato(recensito);
   		homePageImpiegato.start(window, popup);
    }
}
