package controller;

import java.sql.Connection;
import java.sql.SQLException;

import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.Impiegato;
import model.Connection.DBConnection;
import model.Dao.ImpiegatoDao;
import model.DaoInterface.ImpiegatoDaoInterface;
import view.FinestraPopup;
import view.HomePageBenvenuto;

public class ControllerCaricamentoRegistrazione {

    @FXML private AnchorPane  CaricamentoRegistrazione;
    @FXML private Label		  CaricamentoLabel;
    @FXML private ProgressBar CaricamentoPB;
    
    private ImpiegatoDaoInterface impiegatoConnection;
    private String direttoreRisorseUmane;
    
    private Stage window;
    private Stage popup;
    
    private HomePageBenvenuto homePageBenvenuto;
    private FinestraPopup finestraConfermaRegistrazione;
    
    private Connection connection;
    private DBConnection dbConnection;
    
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
    
    public void setStage(Stage window, Stage popup) {
    	this.window = window;
    	this.popup = popup;
    }
    
    public void inizializza(Impiegato nuovoImpiegato) {
	    try {
	    	
	    	impiegatoConnection = new ImpiegatoDao(connection);
	    	direttoreRisorseUmane = impiegatoConnection.getDirettoreRisorseUmane();
	    	
	    	if(direttoreRisorseUmane.isBlank()) {
	    		CaricamentoLabel.setText("Stiamo valutando la tua richiesta...");
	    	} else {
	    		CaricamentoLabel.setText("Il direttore alle risorse umane " + direttoreRisorseUmane + "sta valutando la tua richiesta...");
	    	}
	    	
	    	impiegatoConnection.insertRegistrazione(nuovoImpiegato);
	    	
			PauseTransition pausa = new PauseTransition(Duration.seconds(5));
			pausa.setOnFinished(event -> {
		    	homePageBenvenuto = new HomePageBenvenuto();
		    	finestraConfermaRegistrazione = new FinestraPopup();
		    	
				try {
					homePageBenvenuto.start(window, popup);
			    	finestraConfermaRegistrazione.start(popup, nuovoImpiegato);
				} catch (Exception e) {
					e.printStackTrace();
				}
			});
			
			pausa.play();
	    	
	    } catch (SQLException sqlEx){
	    	sqlEx.printStackTrace();
	    }
	    
    }

}