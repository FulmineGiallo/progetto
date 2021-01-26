package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Connection.DBConnection;
import model.Dao.ProgettoDao;
import model.DaoInterface.ProgettoDaoInterface;
import model.Impiegato;
import model.Progetto;
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
    
    private HomePageImpiegato homePageImpiegato;
    private Stage window;
    private Stage popup;
    
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
    }
    
    @FXML
    private void backHomePageImpiegato(ActionEvent event) throws Exception
    {
    	homePageImpiegato = new HomePageImpiegato(impiegato);
    	homePageImpiegato.start(window, popup);
    }
}
