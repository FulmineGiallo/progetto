package controller;

import java.awt.Desktop.Action;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Observable;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Impiegato;
import model.Progetto;
import model.Ruolo;
import model.Skill;
import model.Titolo;
import model.Connection.DBConnection;
import model.Dao.ImpiegatoDao;
import model.Dao.ProgettoDao;
import model.Dao.RuoloDao;
import model.Dao.SkillDao;
import model.Dao.TitoloDAO;
import model.DaoInterface.ImpiegatoDaoInterface;
import model.DaoInterface.ProgettoDaoInterface;
import model.DaoInterface.RuoloDaoInterface;
import model.DaoInterface.SkillDaoInterface;
import model.DaoInterface.TitoloDaoInterface;

public class ControllerRicercaImpiegati {

    @FXML private AnchorPane 			RicercaImpiegati;
    @FXML private HBox 					IstruzioniBox;
    @FXML private Label 				IstruzioniLabel;
    @FXML private AnchorPane 			FormRicercaImpiegati;
    @FXML private AnchorPane 			FormAP;
    @FXML private VBox 					RicercaSkillBox;
    @FXML private Label 				RicercaSkillLabel;
    @FXML private ComboBox<Titolo> 		RicercaSkillComboBox;
    @FXML private VBox 					SkillAggiunteBox;
    @FXML private Label 				SkillAggiunteLabel;
    @FXML private ListView<String> 		SkillAggiunteLV;
    @FXML private HBox 					SalarioMedioBox;
    @FXML private Label 				SalarioMedioLabel;
    @FXML private TextField 			SalarioMedioTF;
    @FXML private HBox 					NomeBox;
    @FXML private Label 				NomeLabel;
    @FXML private TextField 			NomeTF;
    @FXML private HBox 					CognomeBox;
    @FXML private Label 				CognomeLabel;
    @FXML private TextField 			CognomeTF;
    @FXML private GridPane 				RIcercaImpiegatiButtonBar;
    @FXML private VBox 					OrdinamentoBox;
    @FXML private Label 				OrdinamentoLabel;
    @FXML private ComboBox<String> 		OrdinamentoComboBox;
    @FXML private Button 				RicercaImpiegatiButton;
    @FXML private AnchorPane 			ListaRicercaImpiegatiBox;
    @FXML private ListView<Impiegato> 	ListaRicercaImpiegatiLV;
    @FXML private AnchorPane 			ConfermaBox;
    @FXML private AnchorPane 			InformazioniImpiegatoBox;
    @FXML private Label 				NomeImpiegatoLabel;
    @FXML private TextField 			NomeImpiegatoTF;
    @FXML private HBox 					EmailBox;
    @FXML private Label 				EmailLabel;
    @FXML private TextField 			EmailTF;
    @FXML private HBox 					ComuneDiNascitaBox;
    @FXML private Label 				ComuneDiNascitaLabel;
    @FXML private TextField 			ComuneDiNascitaTF;
    @FXML private HBox 					DataDiNascitaBox;
    @FXML private Label 				DataDiNascitaLabel;
    @FXML private TextField 			DataDiNascitaTF;
    @FXML private HBox 					SelezionaSkillBox;
    @FXML private Label 				SkillComboBoxLabel;
    @FXML private ComboBox<Skill> 		SkillComboBox;
    @FXML private VBox 					SkillBox;
    @FXML private HBox 					TipologiaSkillBox;
    @FXML private Label 				TipologiaSkillLabel;
    @FXML private TextField 			TipologiaSkillTF;
    @FXML private HBox 					TitoloSkillBox;
    @FXML private Label 				TitoloSkillLabel;
    @FXML private TextField 			TitoloSkillTF;
    @FXML private HBox 					DataDiCertificazioneBox;
    @FXML private Label 				DataCertificazioneSkillLabel;
    @FXML private TextField 			DataCertificazioneTF;
    @FXML private VBox 					DescrizioneSkillBox;
    @FXML private Label 				DescrizioneLabel;
    @FXML private TextArea 				DescrizioneSkillTA;
    @FXML private AnchorPane 			AggiungiImpiegatoBox;
    @FXML private VBox 					RuoloImpiegatoBox;
    @FXML private Label 				RuoloImpiegatoLabel;
    @FXML private ComboBox<Ruolo> 		RuoloImpiegatoComboBox;
    @FXML private Button 				AggiungiImpiegatoButton;
    @FXML private AnchorPane 			IstruzioniBox2;
    @FXML private Label 				IstruzioniLabel2;
    
    private Stage window;
    private Stage popup;
    
    private Progetto progetto;
    private float salarioMedioInserito;
    private String nomeInserito;
    private String cognomeInserito;
    private String ordinamentoSelezionato;
    ImpiegatoDaoInterface impiegatoDao;
    RuoloDaoInterface ruoliDao;
    TitoloDaoInterface titoloDao;
    
    private ObservableList<Impiegato> listaImpiegati = FXCollections.observableArrayList();
    private ObservableList<Ruolo> listaRuoli = FXCollections.observableArrayList();
    private ObservableList<Titolo> listaTitoli = FXCollections.observableArrayList();
    private ObservableList<String> listaOridinaPer = FXCollections.observableArrayList();

    public void setStage(Stage window, Stage popup)
    {
    	this.window = window;
    	this.popup = popup;
    }
 
    
    Connection connection;
    DBConnection dbConnection;

    {
        try {
            dbConnection = new DBConnection();
            connection = dbConnection.getConnection();
            impiegatoDao = new ImpiegatoDao(connection);
            ruoliDao = new RuoloDao(connection);
            titoloDao = new TitoloDAO(connection);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    
    
    public void inizializza(Progetto progetto) throws SQLException {
    	this.progetto = progetto;
    	listaImpiegati = impiegatoDao.getAllImpiegati();
    	listaRuoli = ruoliDao.GetAllRuoli();
    	listaTitoli = titoloDao.titoliList();
    	ListaRicercaImpiegatiLV.setItems(listaImpiegati);
    	RicercaSkillComboBox.setItems(listaTitoli);
    	RuoloImpiegatoComboBox.setItems(listaRuoli);
    	
    	
    	listaOridinaPer.add("Nome (Alfabetico)");
    	listaOridinaPer.add("Cognome (Alfabetico)");
    	listaOridinaPer.add("Salario (Crescente)");
    	listaOridinaPer.add("Salario (Descrescente)");
    	
    	OrdinamentoComboBox.setItems(listaOridinaPer);
    	OrdinamentoComboBox.getSelectionModel().select(1);
    	
    	InserisciSkill();
    	
//        lista = progettoDao.getPartecipanti(progetto);
//        ListaPartecipantiLV.setItems(lista);
//        updateInfoImpiegato();
    }
    
    
    public void InserisciSkill(){
    RicercaSkillComboBox.getSelectionModel().selectedItemProperty().addListener( (options, oldValue, newValue) -> {
    	SkillAggiunteLV.getItems().add(RicercaSkillComboBox.getSelectionModel().getSelectedItem().toString());
    	});
    
    }
    
    public void avviaRicerca(ActionEvent event) {
    	
    	String ordinamento = null;
    	
    	if(SalarioMedioTF.getText().equals("")) {
    		salarioMedioInserito = -1;
    	}
    	else {
    		salarioMedioInserito = Float.parseFloat(SalarioMedioTF.getText());
    	}
    	
    	nomeInserito = NomeTF.getText();
    	cognomeInserito = CognomeTF.getText();
    	ordinamentoSelezionato = OrdinamentoComboBox.getSelectionModel().getSelectedItem();
    	
    	switch (ordinamentoSelezionato) {
    	case "Nome (Alfabetico)":
    		ordinamento = "Nome ASC";
    		break;	
    	case "Cognome (Alfabetico)":
    		ordinamento = "Cognome ASC";
    		break;
    	case "Salario (Crescente)":
    		ordinamento = "salarioMedio ASC";
    		break;
    	case "Salario (Descrescente)":
    		ordinamento = "salarioMedio DESC";
    		break;
    	}

    	
    	nomeInserito="%" + nomeInserito + "%";
    	cognomeInserito="%" + cognomeInserito + "%";

    	
    	
    	try {
			listaImpiegati = impiegatoDao.getAllImpiegatiByResearch(salarioMedioInserito, nomeInserito, cognomeInserito, ordinamento);
			ListaRicercaImpiegatiLV.setItems(listaImpiegati);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    }
}