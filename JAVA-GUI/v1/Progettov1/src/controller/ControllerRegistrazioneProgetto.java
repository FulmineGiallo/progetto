package controller;


import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.Ambito;
import model.Comune;
import model.Connection.DBConnection;
import model.Dao.AmbitoDao;
import model.Dao.ComuneDao;
import model.Dao.ProgettoDao;
import model.DaoInterface.ProgettoDaoInterface;
import view.HomePageImpiegato;
import model.Impiegato;
import model.Progetto;

public class ControllerRegistrazioneProgetto {

    @FXML private AnchorPane RegistrazioneProgetto;
    @FXML private HBox IstruzioniBox;
    @FXML private Label IstruzioniLabel;
    @FXML private Label IstruzioniLabel2;
    @FXML private HBox FormBox;
    @FXML private ScrollPane FormScrollPane;
    @FXML private VBox Form;
    @FXML private VBox TitoloBox;
    @FXML private Label TitoloLabel;
    @FXML private TextField TitoloTF;
    @FXML private Label TitoloErrorLabel;
    @FXML private VBox DescrizioneBox;
    @FXML private Label DescrizioneLabel;
    @FXML private TextArea DescrizioneTA;
    @FXML private VBox DataDiInizioBox;
    @FXML private Label DataDiInizioLabel;
    @FXML private DatePicker DataDiInizioDP;
    @FXML private Label DataDiInizioErrorLabel;
    @FXML private VBox DataDiScadenzaBox;
    @FXML private Label DataDiScadenzaLabel;
    @FXML private DatePicker DataDiScadenzaDP;
    @FXML private Label DataDiScadenzaErrorLabel;
    @FXML private HBox TipologiaBox;
    @FXML private Label TipologiaLabel;
    @FXML private ComboBox<?> TipologiaComboBox;
    @FXML private HBox AmbitiBox;
    @FXML private Label AmbitiLabel;
    @FXML private MenuButton AmbitiMenuButton;
    @FXML private AnchorPane ButtonBar;
    @FXML private Button AnnullaButton;
    @FXML private Button ConfermaButton;
    
    private Stage window;
    private Stage popup;
    
    private HomePageImpiegato homePageImpiegato;
    
    private Date dataInizioDate = null;
    private Date dataScadenzaDate = null;
    private Date dataSupportata = Calendar.getInstance().getTime();
    
    Connection connection;
    DBConnection dbConnection;
    {
        try
        {
            dbConnection = new DBConnection();
            connection = dbConnection.getConnection();
        } catch (SQLException throwables)
        {
            throwables.printStackTrace();
        }
    }
    
    ObservableList<Ambito> ambitoList = FXCollections.observableArrayList();
    AmbitoDao ambito = null;
    {
        try
        {
            ambito= new AmbitoDao(connection);
            ambitoList = ambito.AmbitoList();
        } catch (SQLException throwables)
        {
            throwables.printStackTrace();
        }
    }
    
    public void setStage(Stage window, Stage popup) {
    	this.window = window;
    	this.popup = popup;
    }

    Impiegato impiegato;
    
    public void inizializzaRegistrazioneProgetto(Impiegato impiegato) {
        this.impiegato = impiegato;
    	int i = 0;

    	while(i<ambitoList.size()) {
    		CheckMenuItem a = new CheckMenuItem(ambitoList.get(i).toString());
    		AmbitiMenuButton.getItems().add(a);
    		i++;
    	}
    }
    
    @FXML
    void annullaOperazione(ActionEvent event) throws Exception{
    	homePageImpiegato = new HomePageImpiegato(impiegato);
    	homePageImpiegato.start(window, popup);
    }

    @FXML
    void confermaOperazione(ActionEvent event)
    {
    	

        int risultato;
    	if(controlloCampi()) {
            try {
                ProgettoDaoInterface progetto = new ProgettoDao(connection);
                Progetto registrazione = new Progetto(TitoloTF.getText());
                registrazione.setProjectManager(impiegato);
                registrazione.setDescrizione(DescrizioneTA.getText());
                registrazione.setDataInizio(java.sql.Date.valueOf(DataDiInizioDP.getValue()));
                registrazione.setScadenza(java.sql.Date.valueOf(DataDiScadenzaDP.getValue()));
                risultato = progetto.creaProgetto(registrazione);
                if(risultato == 1) {
                    System.out.println("Dati inseriti");
                }
                else
                    System.out.println("Errore nella query");

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }

    }

    @FXML
    void visualizzaNomeLabel(MouseEvent event) {

    }
    
    public boolean controlloCampi(){
    	
    	boolean checkTitolo = true;
    	boolean checkDescrizione = true;
    	boolean checkDataInizio = true;
    	boolean checkDataScadenza = true;
    	boolean checkDataFine = true;
    	
    	TitoloErrorLabel.setText("");
    	DataDiInizioErrorLabel.setText("");
    	DataDiScadenzaErrorLabel.setText("");

    	
    	
    	if(DataDiInizioDP.getValue() != null)
    		dataInizioDate = java.sql.Date.valueOf(DataDiInizioDP.getValue());
    	
    	if(DataDiScadenzaDP.getValue() != null)
        	dataScadenzaDate = java.sql.Date.valueOf(DataDiScadenzaDP.getValue()); 

    	
    	if (!(TitoloTF.getText().matches("[a-zA-Z0-9]+")) || ( TitoloTF.getText().isBlank()) ) {
    		checkTitolo = false;
    		if(TitoloTF.getText().isBlank())	
    			TitoloErrorLabel.setText("Il titolo non puo essere vuoto");
    		else
    			TitoloErrorLabel.setText("Il titolo puo contenere solo lettere e numeri");
    	}
    	
    	if(dataInizioDate == null || dataInizioDate.before(dataSupportata)) {
    		checkDataInizio = false;
    		DataDiInizioErrorLabel.setTextFill(Color.RED);
    		
    		if(dataInizioDate == null)
    			DataDiInizioErrorLabel.setText("inserire la data di inizio del progetto");
    		else
    			DataDiInizioErrorLabel.setText("il progetto non puo iniziare prima della data di oggi");
    	}
    	
    	if(dataScadenzaDate == null) {
    		checkDataScadenza = false;
    		DataDiScadenzaErrorLabel.setTextFill(Color.RED);
    		DataDiScadenzaErrorLabel.setText("inserire la data di scadenza del progetto");
    	}else if(dataInizioDate != null) {
    				if(dataScadenzaDate.before(dataInizioDate)) {
    					checkDataScadenza = false;
    					DataDiScadenzaErrorLabel.setTextFill(Color.RED);
    					DataDiScadenzaErrorLabel.setText("il progetto non puo scadere prima del suo inizio");
    				}
    			}
    		else {
    				checkDataScadenza = false;
    				DataDiScadenzaErrorLabel.setTextFill(Color.RED);
    				DataDiScadenzaErrorLabel.setText("Inserire prima la data di inizio");
    			}
    		
    	return checkTitolo && checkDataFine && checkDataInizio && checkDataScadenza && checkDescrizione;
    	
    }

}