package controller;


import java.sql.Connection;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
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
import model.Ambito;
import model.Comune;
import model.Connection.DBConnection;
import model.Dao.AmbitoDao;
import model.Dao.ComuneDao;

public class ControllerRegistrazioneProgetto {

    @FXML
    private AnchorPane RegistrazioneProgetto;

    @FXML
    private HBox IstruzioniBox;

    @FXML
    private Label IstruzioniLabel;

    @FXML
    private Label IstruzioniLabel2;

    @FXML
    private HBox FormBox;

    @FXML
    private ScrollPane FormScrollPane;

    @FXML
    private VBox Form;

    @FXML
    private VBox TitoloBox;

    @FXML
    private Label TitoloLabel;

    @FXML
    private TextField TitoloTF;

    @FXML
    private Label TitoloErrorLabel;

    @FXML
    private VBox DescrizioneBox;

    @FXML
    private Label DescrizioneLabel;

    @FXML
    private TextArea DescrizioneTA;

    @FXML
    private VBox DataDiInizioBox;

    @FXML
    private Label DataDiInizioLabel;

    @FXML
    private DatePicker DataDiInizioDP;

    @FXML
    private Label DataDiInizioErrorLabel;

    @FXML
    private VBox DataDiScadenzaBox;

    @FXML
    private Label DataDiScadenzaLabel;

    @FXML
    private DatePicker DataDiScadenzaDP;

    @FXML
    private Label DataDiScadenzaErrorLabel;

    @FXML
    private HBox TipologiaBox;

    @FXML
    private Label TipologiaLabel;

    @FXML
    private ComboBox<?> TipologiaComboBox;

    @FXML
    private HBox AmbitiBox;

    @FXML
    private Label AmbitiLabel;

    @FXML
    private MenuButton AmbitiMenuButton;

    @FXML
    private AnchorPane ButtonBar;

    @FXML
    private Button AnnullaButton;

    @FXML
    private Button ConfermaButton;
    
    
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
    
    public void inizializzaRegistrazioneProgetto() {
    	
    	int i = 0;
    	
    	while(i<ambitoList.size()) {
    		MenuItem a = new MenuItem(ambitoList.get(i).toString());
    		AmbitiMenuButton.getItems().add(a);
    		i++;
    	}
    	
    	AmbitiMenuButton.setTextFill(Color.BLACK);
    }
    
    
    @FXML
    void annullaOperazione(ActionEvent event) {

    }

    @FXML
    void confermaOperazione(ActionEvent event) {
    	
    	boolean checkTitolo = true;
    	boolean checkDescrizione = true;
    	boolean checkDataInizio = true;
    	boolean checkDataScadenza = true;


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
    	
//    	if (( DescrizioneTA.getText().isBlank()) ) {
//    		checkDescrizione = false;
//    		DescrizioneLabel.setText("La descrizione non puo essere vuota");
//    	}
    	
    	if(dataInizioDate == null || dataInizioDate.before(dataSupportata)) {
    		checkDataInizio = false;
    		DataDiInizioErrorLabel.setTextFill(Color.RED);
    		
    		if(dataInizioDate == null)
    			DataDiInizioErrorLabel.setText("inserire la data di inizio del progetto");
    		else
    			DataDiInizioErrorLabel.setText("il progetto non puo iniziare prima della data di oggi");
    	}
    	
    	if(dataScadenzaDate == null || dataScadenzaDate.before(dataInizioDate)) {
    		checkDataScadenza = false;
    		DataDiScadenzaErrorLabel.setTextFill(Color.RED);
    		if(dataScadenzaDate == null)
    			DataDiScadenzaErrorLabel.setText("inserire la data di scadenza del progetto");
    		else
    			DataDiScadenzaErrorLabel.setText("il progetto non puo scadere prima del suo inizio");
    	}
    	
    	

    }

    @FXML
    void visualizzaNomeLabel(MouseEvent event) {

    }

}