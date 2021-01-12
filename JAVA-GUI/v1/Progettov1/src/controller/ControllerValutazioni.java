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
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.Connection.DBConnection;
import model.Dao.ValutazioneDao;
import model.DaoInterface.ValutazioneDaoInterface;
import model.Impiegato;
import model.Valutazione;
import view.HomePageImpiegato;

public class ControllerValutazioni 
{
	
	HomePageImpiegato homeImpiegato;
	

	Impiegato impiegato = new Impiegato(null);
	
    @FXML
    private ListView<Valutazione> ListaValutazioniLV;

    @FXML
    private ListView<?> RecensiononeListView;

    @FXML
    private Label RecensoreLabel;

    @FXML
    private Label StelleLabel;

    @FXML
    private Label dataValutazioneLabel;

    @FXML
    private Button HomePageImpiegatoButton;

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
    public void inizializza(Impiegato impiegato) throws SQLException
    {
        this.impiegato=impiegato;
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
   public void backhomeimpiegato(ActionEvent event) throws Exception {
	   
	   	PrintWriter writer = null;
   		homeImpiegato = new HomePageImpiegato(writer, impiegato);

   		Stage stage = (Stage)HomePageImpiegatoButton.getScene().getWindow();
   		homeImpiegato.start(stage);
    }
}
