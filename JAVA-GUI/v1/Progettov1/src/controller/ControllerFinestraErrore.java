package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
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
import view.HomePageBenvenuto;

import java.sql.Connection;
import java.sql.SQLException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;

public class ControllerFinestraErrore {

    @FXML private AnchorPane 	FinestraPopup;
    
    @FXML private GridPane 		MessaggioBox;
    @FXML private VBox 			TestoBox;
    @FXML private Label 		TitoloLabel;
    @FXML private TextArea 		MessaggioTA;
    @FXML private Label 		MessaggioLabel;
    
    @FXML private VBox 			ImmagineBox;
    @FXML private ImageView 	Immagine;
    
    @FXML private GridPane 		ButtonBar;
    @FXML private Button 		SinistraButton;
    @FXML private Button 		DestraButton;
    
    private Image immagineAttenzione = new Image(getClass().getClassLoader().getResourceAsStream("view/resources/img/warning.png"));
    private Image immagineSkill = new Image(getClass().getClassLoader().getResourceAsStream("view/resources/img/skill.png"));
    private Image immagineDomanda = new Image(getClass().getClassLoader().getResourceAsStream("view/resources/img/question.png"));
    
    private String dettagliErrore;
    
    private Impiegato impiegatoRiunione;
    private Riunione riunione;
    private ControllerHomePageOrganizzatore homePageOrganizzatore;
    
	private ProgettoDaoInterface 		  progettoDao;
	private ProgettoImpiegatoDaoInterface progettoImpiegatoDao;
	private ControllerHomePageProgetto	  homePageProgetto;
    private RiunioneDaoInterface		  riunioneDao;
    private RiunioneImpiegatoDao		  riunioneImpiegatoDao;
    private HomePageBenvenuto			  homePageBenvenuto;
    private int							  idriunione;
	
    private Stage window;
    private Stage popup;
    
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

    	Immagine.setImage(immagineAttenzione);
    	
    	DestraButton.setText("Ok");
    	
    	MessaggioLabel.setText(messaggioErrore);
    	if(errore != null) {    		
    		dettagliErrore = errore.toString() + "\n";
			for(StackTraceElement STE: errore.getStackTrace()) {
				dettagliErrore = dettagliErrore + "at " + STE.toString() + "\n";
			}
			
        	MessaggioTA.setText(dettagliErrore);
    	} else {
    		MessaggioTA.setText("Nessun dettaglio da mostrare");
    	}
    	
    	MostraNascondiDettagli();
    	ChiudiFinestraErrore();
    }
    
    public void inizializza(String dettagliSkill) {
    	
    	Immagine.setImage(immagineSkill);
    	
    	MessaggioTA.setVisible(true);
    	MessaggioLabel.setVisible(false);
    	
    	MessaggioTA.setText(dettagliSkill);
    	//altre cose da fare
    }
    
    public void inizializza(Impiegato impiegato, Progetto progetto, ControllerHomePageProgetto homePageProgetto) {
    	
    	this.impiegato=impiegato;
    	this.progetto=progetto;
    	this.homePageProgetto = homePageProgetto;
    	MessaggioLabel.setText("Sei sicuro di voler eliminare questo impiegato dal progetto?");
    	DestraButton.setText("Conferma");
    	SinistraButton.setText("Annulla");

    	
    	EliminaImpiegatoDalProgetto();
    	
    	ChiudiFinestraEliminazioneImpiegato();
    }
    
    public void inizializza(Impiegato impiegatoRiunione, Riunione riunione, ControllerHomePageOrganizzatore homePageOrganizzatore) {
    	

    	this.impiegatoRiunione=impiegatoRiunione;
    	this.riunione=riunione;
    	this.homePageOrganizzatore=homePageOrganizzatore;
    	
    	MessaggioLabel.setText("Sei sicuro di voler eliminare questo impiegato dalla riunione?");
    	DestraButton.setText("Conferma");
    	SinistraButton.setText("Annulla");
    	EliminaImpiegatoDallaRiunione();
    	ChiudiFinestraEliminazioneImpiegato();
    }
    
    public void inizializza(String messaggioErrore, Stage window, Stage popup) {
    	
    	this.window=window;
    	this.popup=popup;
    	
    	DestraButton.setText("Ok");
    	SinistraButton.setText("Annulla");
    	
    	MessaggioLabel.setText(messaggioErrore);
    	
    	EffettuaLogout();
    	ChiudiFinestraLogout();
    }
    
    public void EliminaImpiegatoDalProgetto()
    {
        DestraButton.setOnMouseClicked(new EventHandler<MouseEvent>()
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
            		FinestraPopup.getScene().getWindow().hide();
            		try {
						homePageProgetto.aggiornaLista();
						homePageProgetto.DaDescrizioneProgettoAdIstruzioniBox();
					} catch (SQLException e) {
						e.printStackTrace();
					}
            	}
                
            }
        });
    }
    
    public void EffettuaLogout()
    {
        DestraButton.setOnMouseClicked(new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent mouseEvent) {
              homePageBenvenuto = new HomePageBenvenuto();
              homePageBenvenuto.start(window, popup);
              FinestraPopup.getScene().getWindow().hide();
            }
        });
    }
    
    public void MostraNascondiDettagli() {
        DestraButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
            	
            	if (MessaggioLabel.isVisible()) {
            		
            		MessaggioLabel.setVisible(false);
            		MessaggioTA.setVisible(true);
            		
            	} else if (MessaggioTA.isVisible()) {
            		
            		MessaggioLabel.setVisible(true);
            		MessaggioTA.setVisible(false);
            		
            	}
            }
        });
    }    
    
    public void ChiudiFinestraEliminazioneImpiegato() {
        SinistraButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
            	FinestraPopup.getScene().getWindow().hide();

            }
        });
    }
    
    public void ChiudiFinestraErrore() {
        DestraButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
            	FinestraPopup.getScene().getWindow().hide(); 
            }
        });
    }
    
    public void ChiudiFinestraLogout() {
        SinistraButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
            	FinestraPopup.getScene().getWindow().hide(); 
            }
        });
    }
    
    public void EliminaImpiegatoDallaRiunione()
    {
        DestraButton.setOnMouseClicked(new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent mouseEvent) {

            	int eliminato=0;
            	
            	
            	try {
            	riunioneImpiegatoDao = new RiunioneImpiegatoDao(connection);
            	idriunione = riunioneDao.GetIdRiunione(riunione);
            	
            	eliminato = riunioneImpiegatoDao.EliminaImpiegatoDallaRiunione(impiegatoRiunione, idriunione);

            	if(eliminato!=0) {
            		FinestraPopup.getScene().getWindow().hide();	
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
