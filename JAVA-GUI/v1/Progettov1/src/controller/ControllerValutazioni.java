package controller;

import java.io.PrintWriter;
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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Connection.DBConnection;
import model.Dao.ValutazioneDao;
import model.DaoInterface.ValutazioneDaoInterface;
import model.Impiegato;
import model.Valutazione;
import view.HomePageImpiegato;

public class ControllerValutazioni {
	Impiegato impiegato = new Impiegato(null);
	
    @FXML
    private ListView<?> RecensiononeListView;

    @FXML
    private Label RecensoreLabel;

    @FXML
    private Label StelleLabel;

    @FXML
    private Label dataValutazioneLabel;
    
    @FXML private AnchorPane 			HomePageValutazioni;
    
    @FXML private VBox 					ImpiegatoBox;
    @FXML private Label 				NomeImpiegatoLabel;
    @FXML private Label 				GradoImpiegatoLabel;
    
    @FXML private HBox 					ToolBar;
    @FXML private Button 				HomePageImpiegatoButton;
    
    @FXML private AnchorPane 			ListaValutazioniBox;
    @FXML private Label 				ListaValutazioniLabel;
    @FXML private ScrollPane 			ListaValutazioniScrollPane;
    @FXML private ListView<Valutazione> ListaValutazioniLV;
    
    @FXML private AnchorPane 			IstruzioniBox;
    @FXML private Label 				IstruzioniLabel;
    
    @FXML private AnchorPane 			DescrizioneValutazioneBox;
    @FXML private AnchorPane 			DescrizioneValutazionePane;
    
	HomePageImpiegato homePageImpiegato;
    Stage window;

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
    ObservableList<Valutazione> lista = FXCollections.observableArrayList();
    ValutazioneDaoInterface valutazioni;
    {
        try {
            valutazioni = new ValutazioneDao(connection);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    public void inizializza(Impiegato impiegato, Stage window) throws SQLException {
    	this.window = window;
        this.impiegato = impiegato;
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


            }
        });
    }
   public void backHomePageImpiegato(ActionEvent event) throws Exception {
   		homePageImpiegato = new HomePageImpiegato(impiegato);
   		homePageImpiegato.start(window);
    }
}
