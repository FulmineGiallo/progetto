package controller;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import model.Connection.DBConnection;
import model.Dao.GradoDao;
import model.DaoInterface.GradoDaoInterface;
import model.Grado;
import view.HomePageBenvenuto;
import view.CaricamentoRegistrazioneImpiegato;

public class ControllerRegistrazioneImpiegato {

	//FormRegistrazioneImpiegato
    @FXML private TextField 	NomeTF;
    @FXML private TextField 	CognomeTF;
    @FXML private RadioButton 	GenereRB1;
    @FXML private RadioButton 	GenereRB2;
    @FXML private ToggleGroup 	Genere;
    @FXML private DatePicker 	DataDiNascitaDP;
    @FXML private ListView<?> 	ComuneLV;
    @FXML private TextField 	ProvinciaTF;
    @FXML private ListView<?> 	SkillLV;
    @FXML private ComboBox<Grado> GradoComboBox;
    @FXML private Button 		AnnullaButton;
    @FXML private Button 		ConfermaButton;
    
    HomePageBenvenuto homePageBenvenuto;
    CaricamentoRegistrazioneImpiegato caricamentoRegistrazioneImpiegato;

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

    ObservableList<Grado> gradiList = FXCollections.observableArrayList();
    GradoDaoInterface gradi = null;
        {
            try
            {
                gradi = new GradoDao(connection);
                gradiList = gradi.gradoList();
            } catch (SQLException throwables)
            {
                throwables.printStackTrace();
            }
        }

    public void inizializza()
    {
        GradoComboBox.getItems().addAll(gradiList);
    }



    public void annullaOperazione (ActionEvent actionEvent) throws Exception
    {
    	PrintWriter writer = null;
    	homePageBenvenuto = new HomePageBenvenuto(writer);

        Stage stage = (Stage)AnnullaButton.getScene().getWindow();
        homePageBenvenuto.start(stage);
    }
    
    public void confermaOperazione (ActionEvent actionEvent) throws Exception
    {


    	PrintWriter writer = null;
    	caricamentoRegistrazioneImpiegato = new CaricamentoRegistrazioneImpiegato(writer);

        Stage stage = (Stage)ConfermaButton.getScene().getWindow();
        caricamentoRegistrazioneImpiegato.start(stage);
    }

    public void visualizzaNomeLabel(MouseEvent mouseEvent) {
    }

    public void visualizzaCognomeLabel(MouseEvent mouseEvent) {
    }

    public void coloraGenereLabel(MouseEvent mouseEvent) {
    }
}