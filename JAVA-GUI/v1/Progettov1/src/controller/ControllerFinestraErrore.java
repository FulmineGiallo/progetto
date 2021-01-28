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
//import jdk.internal.org.objectweb.asm.Handle;
import model.Impiegato;
import model.Progetto;
import model.Riunione;
import model.Connection.DBConnection;
import model.Dao.ProgettoDao;
import model.Dao.RiunioneDao;
import model.Dao.RiunioneImpiegatoDao;
import model.Dao.progettoImpiegatoDao;
import model.DaoInterface.ProgettoDaoInterface;
import model.DaoInterface.ProgettoImpiegatoDaoInterface;
import model.DaoInterface.RiunioneDaoInterface;

import java.sql.Connection;
import java.sql.SQLException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
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
    
    private Impiegato impiegatoRiunione;
    private Riunione riunione;
    private ControllerHomePageOrganizzatore homePageOrganizzatore;
    
    
    private EventHandler<MouseEvent> progettoEvent;
    private EventHandler<MouseEvent> riunioneEvent;
	private ProgettoDaoInterface progettoDao;
	private ProgettoImpiegatoDaoInterface progettoImpiegatoDao;
	private ControllerHomePageProgetto homePageProgetto;
    private RiunioneDaoInterface riunioneDao;
    private RiunioneImpiegatoDao riunioneImpiegatoDao;
    private int idriunione;
	
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
        	riunioneDao = new RiunioneDao(connection);
            

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
    
    public void inizializza(String dettagliSkill) {
    	MessaggioErroreTA.setVisible(true);
    	MessaggioErroreLabel.setVisible(false);
    	
    	MessaggioErroreTA.setText(dettagliSkill);
    	//altre cose da fare
    }
    
    public void inizializza(Impiegato impiegato, Progetto progetto, ControllerHomePageProgetto homePageProgetto) {
    	
    	this.impiegato=impiegato;
    	this.progetto=progetto;
    	this.homePageProgetto = homePageProgetto;
    	MessaggioErroreLabel.setText("Sei sicuro di voler eliminare questo impiegato dal progetto?");
    	OkButton.setText("Conferma");
    	DettagliButton.setText("Annulla");

    	
    	EliminaImpiegatoDalProgetto();
    	
    	ChiudiFinestraEliminazioneImpiegato();
    }
    
    public void inizializza(Impiegato impiegatoRiunione, Riunione riunione, ControllerHomePageOrganizzatore homePageOrganizzatore) {
    	

    	this.impiegatoRiunione=impiegatoRiunione;
    	this.riunione=riunione;
    	this.homePageOrganizzatore=homePageOrganizzatore;
    	
    	MessaggioErroreLabel.setText("Sei sicuro di voler eliminare questo impiegato dalla riunione?");
    	OkButton.setText("Conferma");
    	DettagliButton.setText("Annulla");
    	EliminaImpiegatoDallaRiunione();
    	ChiudiFinestraEliminazioneImpiegato();
    }
    
    
    public void EliminaImpiegatoDalProgetto()
    {
        OkButton.setOnMouseClicked( progettoEvent =new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent mouseEvent) {

            	int idProgetto=0;
            	int eliminato=0;
            	
            	try {
					idProgetto = progettoDao.GetIdProgetto(progetto);
					eliminato = progettoImpiegatoDao.EliminaImpiegatoDalProgetto(impiegato, idProgetto);
				} catch (SQLException e) {
					e.printStackTrace();
				}
            	
            	if(eliminato !=0) {
            		//System.out.println("impiegato eliminato");
            		FinestraErrore.getScene().getWindow().hide();
            		try {
						homePageProgetto.aggiornaLista();
						homePageProgetto.DaDescrizioneProgettoAdIstruzioniBox();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
            	}
                
            }
        });
    }
    
    public void MostraNascondiDettagli() {
        OkButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
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
    
    public void ChiudiFinestraEliminazioneImpiegato() {
        DettagliButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
            	FinestraErrore.getScene().getWindow().hide();

            }
        });
    }
    
    public void ChiudiFinestraErrore() {
        OkButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
            	FinestraErrore.getScene().getWindow().hide(); 
            }
        });
    }
    
    public void EliminaImpiegatoDallaRiunione()
    {
        OkButton.setOnMouseClicked(riunioneEvent=new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent mouseEvent) {

            	int eliminato=0;
            	
            	
            	try {
            	riunioneImpiegatoDao = new RiunioneImpiegatoDao(connection);
            	idriunione = riunioneDao.GetIdRiunione(riunione);
            	
            	eliminato = riunioneImpiegatoDao.EliminaImpiegatoDallaRiunione(impiegatoRiunione, idriunione);

            	if(eliminato!=0) {
            		FinestraErrore.getScene().getWindow().hide();	
            		homePageOrganizzatore.AggiornaLista();
            		homePageOrganizzatore.DaDescrizioneRiunioneAdIstruzioniBox();
            	}
            	
            	}catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

                
            }
        });
    }
    
    
}
