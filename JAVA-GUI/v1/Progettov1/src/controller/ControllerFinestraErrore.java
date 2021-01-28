package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import model.Impiegato;
import model.Progetto;
import model.Connection.DBConnection;
import model.Dao.ProgettoDao;
import model.Dao.progettoImpiegatoDao;
import model.DaoInterface.ProgettoDaoInterface;
import model.DaoInterface.ProgettoImpiegatoDaoInterface;

import java.sql.Connection;
import java.sql.SQLException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class ControllerFinestraErrore {

    @FXML private AnchorPane 	FinestraErrore;
    @FXML private GridPane 		MessaggioBox;
    @FXML private VBox 			TestoBox;
    @FXML private Label 		AttenzioneLabel;
    @FXML private TextArea 		MessaggioErroreTA;
    @FXML private Label 		MessaggioErroreLabel;
    @FXML private VBox 			ImmagineBox;
    @FXML private ImageView 	AttenzioneIV;
    @FXML private GridPane 		ButtonBar;
    @FXML private Button 		DettagliButton;
    @FXML private Button 		OkButton;
    
    private String dettagliErrore;
    
	private ProgettoDaoInterface progettoDao;
	private ProgettoImpiegatoDaoInterface progettoImpiegatoDao;
	
	private Impiegato impiegato;
	private Progetto progetto;
	
    Connection connection;
    DBConnection dbConnection;
    ObservableList<Impiegato> lista = FXCollections.observableArrayList();
  

    {
        try {
            dbConnection = new DBConnection();
            connection = dbConnection.getConnection();
            progettoDao = new ProgettoDao(connection);
        	progettoImpiegatoDao = new progettoImpiegatoDao(connection);


        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
	
	
    
    public void inizializza(String messaggioErrore, Exception errore) {
    	
    	OkButton.setText("Ok");
    	
    	MessaggioErroreLabel.setText(messaggioErrore);
    	if(errore != null) {    		
    		dettagliErrore = errore.toString() + "\n";
			for(StackTraceElement STE: errore.getStackTrace()) {
				dettagliErrore = dettagliErrore + "at " + STE.toString() + "\n";
			}
			
        	MessaggioErroreTA.setText(dettagliErrore);
    	} else {
    		MessaggioErroreTA.setText("Nessun dettaglio da mostrare");
    	}
    	
    	MostraNascondiDettagli();
    	ChiudiFinestraErrore();
    }


    
    
    
    public void inizializzaEliminazioneImpiegato(Impiegato impiegato, Progetto progetto) {
    	
    	this.impiegato=impiegato;
    	this.progetto=progetto;
    	MessaggioErroreLabel.setText("Sei sicuro di voler eliminare questo impiegato dal progetto?");
    	OkButton.setText("Elimina");
    	DettagliButton.setText("annulla");
    	EliminaImpiegatoDalProgetto();
    	ChiudiFinestraEliminazioneImpiegato();
    }
    
    
    public void EliminaImpiegatoDalProgetto()
    {
        OkButton.setOnMouseClicked(new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent mouseEvent) {

            	int idProgetto=0;
            	int eliminato=0;
            	
            	try {
					idProgetto=progettoDao.GetIdProgetto(progetto);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

            	
            	try {
					eliminato = progettoImpiegatoDao.EliminaImpiegatoDalProgetto(impiegato, idProgetto);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            	
            	
            	if(eliminato !=0) {
            		System.out.println("impiegato eliminato");
            		ChiudiFinestra(mouseEvent);
            	}
                
            }
        });
    }
    
    public void MostraNascondiDettagli()
    {
        OkButton.setOnMouseClicked(new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent mouseEvent) {

            	if (MessaggioErroreLabel.isVisible()) {
            		
            		MessaggioErroreLabel.setVisible(false);
            		MessaggioErroreTA.setVisible(true);
            		
            	} else if (MessaggioErroreTA.isVisible()) {
            		
            		MessaggioErroreLabel.setVisible(true);
            		MessaggioErroreTA.setVisible(false);
            		
            	}
                
            }
        });
    }
    
    
    
    void ChiudiFinestra(MouseEvent event) {
    	FinestraErrore.getScene().getWindow().hide();
    }
    
    
    public void ChiudiFinestraEliminazioneImpiegato()
    {
        DettagliButton.setOnMouseClicked(new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent mouseEvent) {

            	FinestraErrore.getScene().getWindow().hide();
                
            }
        });
    }
    
    public void ChiudiFinestraErrore()
    {
        OkButton.setOnMouseClicked(new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent mouseEvent) {

            	FinestraErrore.getScene().getWindow().hide();
                
            }
        });
    }
}
