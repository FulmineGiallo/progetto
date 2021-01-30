package controller;

import java.sql.Connection;
import java.sql.SQLException;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.AnchorPane;
import model.Impiegato;
import model.Connection.DBConnection;
import model.Dao.ImpiegatoDao;
import model.DaoInterface.ImpiegatoDaoInterface;

public class ControllerCaricamentoRegistrazione {

    @FXML private AnchorPane  CaricamentoRegistrazione;
    @FXML private Label		  CaricamentoLabel;
    @FXML private ProgressBar CaricamentoPB;
    
    private ImpiegatoDaoInterface impiegatoConnection;
    private String direttoreRisorseUmane;
    
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
    
    public void inizializza(Impiegato nuovoImpiegato) {
	    try {
	    	impiegatoConnection = new ImpiegatoDao(connection);
	    	direttoreRisorseUmane = impiegatoConnection.getDirettoreRisorseUmane();
	    	
	    	if(direttoreRisorseUmane.isBlank()) {
	    		CaricamentoLabel.setText("Stiamo valutando la tua richiesta...");
	    	} else {
	    		CaricamentoLabel.setText("Il direttore alle risorse umane " + direttoreRisorseUmane + "sta valutando la tua richiesta...");
	    	}
	    	
	    	System.out.println(impiegatoConnection.insertRegistrazione(nuovoImpiegato));
	    } catch(SQLException sqlEx){
	    	sqlEx.printStackTrace();
	    }
	    
    }

}