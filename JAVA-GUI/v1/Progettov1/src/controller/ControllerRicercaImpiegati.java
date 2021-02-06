package controller;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Impiegato;
import model.Progetto;
import model.Ruolo;
import model.Titolo;
import model.Connection.DBConnection;
import model.Dao.ComuneDao;
import model.Dao.ImpiegatoDao;
import model.Dao.ProgettoDao;
import model.Dao.RuoloDao;
import model.Dao.SkillDao;
import model.Dao.TitoloDao;
import model.DaoInterface.ComuneDaoInterface;
import model.DaoInterface.ImpiegatoDaoInterface;
import model.DaoInterface.ProgettoDaoInterface;
import model.DaoInterface.ProgettoImpiegatoDaoInterface;
import model.DaoInterface.RuoloDaoInterface;
import model.DaoInterface.SkillDaoInterface;
import model.DaoInterface.TitoloDaoInterface;
import view.FinestraPopup;
import view.HomePageProjectManager;

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
    @FXML private ComboBox<Titolo> 		SkillComboBox;
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
    @FXML private HBox 					NomeImpiegatoBox;
    @FXML private Button 				AnnullaButton;
    @FXML private Slider 				ValutazioneMediaSlider;
    @FXML private Button 				ValutazioneMediaButton;
    
    private Stage window;
    private Stage popup;
    FinestraPopup finestraAggiungiImpiegatoAlProgetto;
    
    
    private int idProgetto;
    private Progetto progetto;
    private float salarioMedioInserito;
    private String nomeInserito;
    private String cognomeInserito;
    private String ordinamentoSelezionato;
    ImpiegatoDaoInterface impiegatoDao;
    RuoloDaoInterface ruoliDao;
    TitoloDaoInterface titoloDao;
    ProgettoDaoInterface progettoDao;
    SkillDaoInterface SkillDAO;
	ComuneDaoInterface comuneNacitaDao;
	ProgettoImpiegatoDaoInterface progettoImpiegatoDao;
    
    private ObservableList<Impiegato> listaImpiegati = FXCollections.observableArrayList();
    private ObservableList<Ruolo> listaRuoli = FXCollections.observableArrayList();
    private ObservableList<Titolo> listaTitoli = FXCollections.observableArrayList();
    private ObservableList<String> listaOridinaPer = FXCollections.observableArrayList();

    HomePageProjectManager homePageProgetto;
    
    
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
            titoloDao = new TitoloDao(connection);
            progettoDao = new ProgettoDao(connection);
            comuneNacitaDao = new ComuneDao(connection);
            progettoImpiegatoDao = new model.Dao.progettoImpiegatoDao(connection);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    
    
    public void inizializza(Progetto progetto) throws SQLException {
    	this.progetto = progetto;
    	idProgetto = progettoDao.getIdProgetto(progetto);
    	listaImpiegati = impiegatoDao.getAllImpiegati(idProgetto);
    	listaRuoli = ruoliDao.GetAllRuoli();
    	listaTitoli = titoloDao.titoliList();
    	ListaRicercaImpiegatiLV.setItems(listaImpiegati);
    	RicercaSkillComboBox.setItems(listaTitoli);
    	RuoloImpiegatoComboBox.setItems(listaRuoli);
    	RicercaImpiegatiButton.setDisable(false);
    	RuoloImpiegatoComboBox.getSelectionModel().select(1);
    	
    	listaOridinaPer.add("Nome (Alfabetico)");
    	listaOridinaPer.add("Cognome (Alfabetico)");
    	listaOridinaPer.add("Salario (Crescente)");
    	listaOridinaPer.add("Salario (Descrescente)");
    	
    	OrdinamentoComboBox.setItems(listaOridinaPer);
    	OrdinamentoComboBox.getSelectionModel().select(1);
    	
    	ValutazioneMediaSlider.setBlockIncrement(1);
    	
    	InserisciSkill();
    	updateInfoImpiegato();
    	
    }
    
    
    public void InserisciSkill(){
    RicercaSkillComboBox.getSelectionModel().selectedItemProperty().addListener( (options, oldValue, newValue) -> {
    	SkillAggiunteLV.getItems().add(RicercaSkillComboBox.getSelectionModel().getSelectedItem().toString());
    	});
    
    }
    
    public void avviaRicerca(ActionEvent event) {
    	
    	String ordinamento = null;
    	ObservableList<String> skillAggiunte = FXCollections.observableArrayList();
    	skillAggiunte = SkillAggiunteLV.getItems();
    	double valutazioneMediaInserita = ValutazioneMediaSlider.getValue();
    	
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

    	if(nomeInserito.length()!=0) {
    		nomeInserito = nomeInserito.substring(0,1).toUpperCase() + nomeInserito.substring(1);
    	}
    	
    	if(cognomeInserito.length() != 0) {
    		cognomeInserito = cognomeInserito.substring(0,1).toUpperCase() + cognomeInserito.substring(1);
    	}
    	
    	
    	nomeInserito="%" + nomeInserito + "%";
    	cognomeInserito="%" + cognomeInserito + "%";
    	
    	if(SkillAggiunteLV.getItems().isEmpty()) {
    		ObservableList<String> listaVuota = FXCollections.observableArrayList();
    		skillAggiunte.add("%%");
    		SkillAggiunteLV.setItems(listaVuota);
    	}
    	
    	if(ValutazioneMediaSlider.isDisable()) {
    		valutazioneMediaInserita = 6;
    	}
    	
   
    	
    	if(SalarioMedioTF.getText().isBlank() && NomeTF.getText().isBlank() && CognomeTF.getText().isBlank() && SkillAggiunteLV.getItems().isEmpty() && ValutazioneMediaSlider.isDisable()) {
        	try {
    			listaImpiegati = impiegatoDao.getAllImpiegatiSenzaCampi(salarioMedioInserito, nomeInserito, cognomeInserito, ordinamento, skillAggiunte, skillAggiunte.size(), idProgetto);
    			ListaRicercaImpiegatiLV.setItems(listaImpiegati);
    			updateInfoImpiegato();
    		} catch (SQLException e) {
    			e.printStackTrace();
    		}
    	}
    	else {
    	
    		try {
				listaImpiegati = impiegatoDao.getAllImpiegatiOrdinati(salarioMedioInserito, nomeInserito, cognomeInserito, ordinamento, skillAggiunte, skillAggiunte.size(), idProgetto, valutazioneMediaInserita);
				ListaRicercaImpiegatiLV.setItems(listaImpiegati);
				updateInfoImpiegato();
			}catch (SQLException e) {
				e.printStackTrace();
			}
    	}
    	
    	updateInfoImpiegato();
    }
    
    public void annullaOperazione() throws Exception {
    	homePageProgetto = new HomePageProjectManager(progetto.getProjectManager(), progetto);
    	homePageProgetto.start(window, popup);
//    	System.out.print(ValutazioneMediaSlider.getValue());

    }
    
    
    public void updateInfoImpiegato()
    {
        ListaRicercaImpiegatiLV.setOnMouseClicked(new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent mouseEvent) {
            	
            	Impiegato infoImpiegato = ListaRicercaImpiegatiLV.getSelectionModel().getSelectedItem();
            	
                IstruzioniBox2.setVisible(false);
                ConfermaBox.setVisible(true);
                InformazioniImpiegatoBox.setVisible(true);
                
                SkillBox.setVisible(false);
                NomeImpiegatoTF.setText(infoImpiegato.toString());
                EmailTF.setText(infoImpiegato.getEmail());
//                ComuneDiNascitaTF.setText(infoImpiegato.getComuneNascita());
                
                try {
					ComuneDiNascitaTF.setText(comuneNacitaDao.getComuneBySigla(infoImpiegato.getComuneNascita().toString()).toString().substring(8));
				} catch (SQLException e2) {
					e2.printStackTrace();
				}
               
                DataDiNascitaTF.setText(infoImpiegato.getDataNascita().toString());
                
                
                
            	try
                {
                    titoloDao = new TitoloDao(connection);
                    SkillDAO = new SkillDao(connection);
                    
                    SkillComboBox.setItems(titoloDao.titoliListImpiegato(infoImpiegato));


                    
                    SkillComboBox.getSelectionModel().selectedItemProperty().addListener( (options, oldValue, newValue) -> {

                    	SkillBox.setVisible(true);

                    if(SkillComboBox.getSelectionModel().getSelectedItem() != null) {	
                    	TitoloSkillTF.setVisible(true);
                    	TitoloSkillTF.setText(SkillComboBox.getSelectionModel().getSelectedItem().toString());
                    	
                        try {
							TipologiaSkillTF.setText(SkillDAO.getTipologiaSkill(SkillComboBox.getSelectionModel().getSelectedItem().toString(), infoImpiegato));
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
                    	
                    	DataCertificazioneSkillLabel.setVisible(true);
                    	DescrizioneSkillTA.setVisible(true);
                    	try {
							if((SkillDAO.descrizioneCertificazione(SkillComboBox.getSelectionModel().getSelectedItem().toString(), infoImpiegato) == null)){
								DescrizioneSkillTA.setText("Nessuna descrizione");
							}else {
								DescrizioneSkillTA.setText(SkillDAO.descrizioneCertificazione(SkillComboBox.getSelectionModel().getSelectedItem().toString(), infoImpiegato));
							}
							
                    		DataCertificazioneTF.setText(SkillDAO.dataCertificazione(SkillComboBox.getSelectionModel().getSelectedItem().toString(), infoImpiegato));
						} catch (SQLException e) {
							e.printStackTrace();
						}
                    	
                    }else {
                    	TitoloSkillTF.setVisible(false);
                    	DataCertificazioneTF.setVisible(false);
                    	DescrizioneSkillTA.setVisible(false);
                    }
                    
                   });
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
                
                
            }
        });
    }
    
    
    @FXML private void rimuoviSkill(MouseEvent event) {

    	String skillSelezionata = SkillAggiunteLV.getSelectionModel().getSelectedItem();
    	
			SkillAggiunteLV.getItems().remove(skillSelezionata);
    	
    }
    
    public void AggiungiImpiegato() throws Exception
    {
    	finestraAggiungiImpiegatoAlProgetto = new FinestraPopup();
    	finestraAggiungiImpiegatoAlProgetto.start(popup, ListaRicercaImpiegatiLV.getSelectionModel().getSelectedItem(), idProgetto, this, RuoloImpiegatoComboBox.getSelectionModel().getSelectedItem());
    	Impiegato impiegatoSalario = ListaRicercaImpiegatiLV.getSelectionModel().getSelectedItem();
        /* Procedure del database che fa un insert in Salario */
        String CF = impiegatoSalario.getCF();
        CallableStatement aggiungiSalarioProgetto = connection.prepareCall("CALL insalarioprogetto(?)");
        aggiungiSalarioProgetto.setString(1, impiegatoSalario.getCF());
        aggiungiSalarioProgetto.execute();

    }
    
    public void NascondiInfoImpiegato()
    {
        ConfermaBox.setVisible(false);
        InformazioniImpiegatoBox.setVisible(false);
        IstruzioniBox2.setVisible(true);
        SkillBox.setVisible(true);
        updateInfoImpiegato();
    }
    
    public void abilitaRicercaValutazione() {
    
    	if(ValutazioneMediaSlider.isDisable()) {
    		ValutazioneMediaSlider.setDisable(false);
    		ValutazioneMediaButton.setText("Disabilita ricerca per valutazione");
    	}
    	else {
    		ValutazioneMediaSlider.setDisable(true);
    		ValutazioneMediaButton.setText("Abilita ricerca per valutazione");
    	}
    }
    
}