package controller;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import model.Comune;
import model.Connection.DBConnection;
import model.Dao.ComuneDao;
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
    @FXML private ComboBox<Comune> 	ComuneComboBox;
    @FXML private TextField ProvinciaTextField;
    @FXML private ListView<?> 	SkillLV;
    @FXML private ComboBox<Grado> GradoComboBox;
    @FXML private Button 		AnnullaButton;
    @FXML private Button 		ConfermaButton;
    @FXML private Button        CercaComuniButton;

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
    ObservableList<Comune> comuneList = FXCollections.observableArrayList();
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
    ComuneDao comuni = null;
    {
        try
        {
            comuni = new ComuneDao(connection);
        } catch (SQLException throwables)
        {
            throwables.printStackTrace();
        }
    }

    public void inizializza() throws SQLException
    {
        GradoComboBox.getItems().addAll(gradiList);
        comuneList = comuni.gradoList(ProvinciaTextField.getText());
    }
    public void updateComune() throws SQLException
    {
        comuneList = comuni.gradoList(ProvinciaTextField.getText());
        ComuneComboBox.setItems(comuneList);
        System.out.println(comuneList.size());
    }
    @FXML
    void cercaComuni(ActionEvent event) throws SQLException
    {
        updateComune();
    }

    String CFRegistrazione()
    {
        return null;
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