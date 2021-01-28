package controller;

import java.sql.Connection;
import java.sql.SQLException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import model.Impiegato;
import model.Progetto;
import model.Connection.DBConnection;
import model.Dao.ProgettoDao;
import model.Dao.progettoImpiegatoDao;
import model.DaoInterface.ProgettoDaoInterface;
import model.DaoInterface.ProgettoImpiegatoDaoInterface;
import view.FinestraRimuoviImpiegatoDalProgetto;


import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

public class ControllerFinestraRimuoviImpiegatoDalProgetto {
	
	private Impiegato impiegato;
	private Progetto progetto;
	
	private ProgettoDaoInterface progettoDao;
	private ProgettoImpiegatoDaoInterface progettoImpiegatoDao;
	
    @FXML
    private AnchorPane finestraEliminazione;

    @FXML
    private Button ConfermaButton;

    @FXML
    private Button AnnullaButton;
	
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
	
    public void inizializza(Impiegato impiegato, Progetto progetto) {

    	this.impiegato=impiegato;
    	this.progetto=progetto;
    	
    }
    
    
    
    public void RimuoviImpiegato(ActionEvent event) throws Exception {
    	
   
    	int idProgetto=0;
    	int eliminato=0;
    	
    	idProgetto=progettoDao.GetIdProgetto(progetto);

    	
    	eliminato = progettoImpiegatoDao.EliminaImpiegatoDalProgetto(impiegato, idProgetto);
    	
    	
    	if(eliminato !=0) {
    		System.out.println("impiegato eliminato");
    		ChiudiFinestra(event);
    	}
    	
    	
    }
    
    
    public void ChiudiFinestra(ActionEvent event) {
    	finestraEliminazione.getScene().getWindow().hide();
    }
    
}
