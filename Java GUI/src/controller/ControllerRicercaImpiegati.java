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
import model.Riunione;
import model.Ruolo;
import model.Titolo;
import model.Connection.DBConnection;
import model.Dao.ComuneDao;
import model.Dao.ImpiegatoDao;
import model.Dao.ProgettoDao;
import model.Dao.RiunioneDao;
import model.Dao.RuoloDao;
import model.Dao.SkillDao;
import model.Dao.TitoloDao;
import model.DaoInterface.ComuneDaoInterface;
import model.DaoInterface.ImpiegatoDaoInterface;
import model.DaoInterface.ProgettoDaoInterface;
import model.DaoInterface.ProgettoImpiegatoDaoInterface;
import model.DaoInterface.RiunioneDaoInterface;
import model.DaoInterface.RuoloDaoInterface;
import model.DaoInterface.SkillDaoInterface;
import model.DaoInterface.TitoloDaoInterface;
import view.FinestraPopup;
import view.HomePageOrganizzatore;
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
    
    @FXML private Slider 				ValutazioneMediaSlider;
    @FXML private Button 				ValutazioneMediaButton;
    
    @FXML private GridPane 				RicercaImpiegatiButtonBar;
    
    @FXML private VBox 					OrdinamentoBox;
    @FXML private Label 				OrdinamentoLabel;
    @FXML private ComboBox<String> 		OrdinamentoComboBox;
    
    @FXML private Button 				RicercaImpiegatiButton;
    
    @FXML private AnchorPane 			ListaRicercaImpiegatiBox;
    @FXML private ListView<Impiegato> 	ListaRicercaImpiegatiLV;
    
    @FXML private AnchorPane 			ConfermaBox;
    @FXML private AnchorPane 			InformazioniImpiegatoBox;
    
    @FXML private HBox 					NomeImpiegatoBox;
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
    
    @FXML private Button 				AnnullaButton;
    
    private Stage 			window;
    private Stage 			popup;
    private FinestraPopup 	finestraAggiungiImpiegato;
    private FinestraPopup	finestraErrore;
    
    private int idProgetto;
    private Progetto progetto = null;
    
    private Riunione riunione = null;
    
    private float 							salarioMedioInserito;
    private String 							nomeInserito;
    private String 							cognomeInserito;
    private String 							ordinamentoSelezionato;
    
    private ImpiegatoDaoInterface 			impiegatoDao;
    private RuoloDaoInterface 				ruoliDao;
    private TitoloDaoInterface 				titoloDao;
    private SkillDaoInterface 				skillDao;
    private ComuneDaoInterface 				comuneNacitaDao;
    
    private ObservableList<Impiegato> listaImpiegati = FXCollections.observableArrayList();
    private ObservableList<Ruolo> listaRuoli = FXCollections.observableArrayList();
    private ObservableList<Titolo> listaTitoli = FXCollections.observableArrayList();
    private ObservableList<String> listaOrdinaPer = FXCollections.observableArrayList();

    private HomePageProjectManager 	homePageProjectManager;
    private HomePageOrganizzatore	homePageOrganizzatore;
    
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
            comuneNacitaDao = new ComuneDao(connection);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    
    public void inizializza(Progetto progetto) throws SQLException {
    	this.progetto = progetto;
    	
    	listaImpiegati = impiegatoDao.getAllImpiegati(progetto);
    	ListaRicercaImpiegatiLV.setItems(listaImpiegati);
    	
    	RuoloImpiegatoLabel.setVisible(true);
    	RuoloImpiegatoComboBox.setVisible(true);
    	listaRuoli = ruoliDao.GetAllRuoli();
    	RuoloImpiegatoComboBox.setItems(listaRuoli);
    	
    	listaTitoli = titoloDao.titoliList();
    	RicercaSkillComboBox.setItems(listaTitoli);
    	
    	RicercaImpiegatiButton.setDisable(false);
    	RuoloImpiegatoComboBox.getSelectionModel().select(1);
    	
    	listaOrdinaPer.add("Nome (Crescente)");
    	listaOrdinaPer.add("Cognome (Crescente)");
    	listaOrdinaPer.add("Salario (Crescente)");
    	listaOrdinaPer.add("Salario (Descrescente)");
    	
    	OrdinamentoComboBox.setItems(listaOrdinaPer);
    	OrdinamentoComboBox.getSelectionModel().select(1);
    	
    	ValutazioneMediaSlider.setBlockIncrement(1);
    	
    	InserisciSkill();
    	updateInfoImpiegato();
    	
    }
    
    public void inizializza(Riunione riunione) throws SQLException {
    	this.riunione = riunione;
    	
    	listaImpiegati = impiegatoDao.getAllImpiegati(riunione);    	
    	ListaRicercaImpiegatiLV.setItems(listaImpiegati);
    	
    	RuoloImpiegatoLabel.setVisible(false);
    	RuoloImpiegatoComboBox.setVisible(false);
    	
    	listaTitoli = titoloDao.titoliList();
    	RicercaSkillComboBox.setItems(listaTitoli);
    	
    	RicercaImpiegatiButton.setDisable(false);
    	
    	listaOrdinaPer.add("Nome (Crescente)");
    	listaOrdinaPer.add("Cognome (Crescente)");
    	listaOrdinaPer.add("Salario (Crescente)");
    	listaOrdinaPer.add("Salario (Descrescente)");
    	
    	OrdinamentoComboBox.setItems(listaOrdinaPer);
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
    	
    	if(SalarioMedioTF.getText().isBlank()) {
    		salarioMedioInserito = -1;
    	}
    	else {
    		salarioMedioInserito = Float.parseFloat(SalarioMedioTF.getText());
    	}
    	
    	nomeInserito = NomeTF.getText();
    	cognomeInserito = CognomeTF.getText();
    	ordinamentoSelezionato = OrdinamentoComboBox.getSelectionModel().getSelectedItem();
    	
    	switch (ordinamentoSelezionato) {
    	case "Nome (Crescente)":
    		ordinamento = "Nome ASC";
    		break;	
    	case "Cognome (Crescente)":
    		ordinamento = "Cognome ASC";
    		break;
    	case "Salario (Crescente)":
    		ordinamento = "salariomedio ASC";
    		break;
    	case "Salario (Descrescente)":
    		ordinamento = "salariomedio DESC";
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
    	
    	
    	
    	
    	
    	
    		
    		listaImpiegati.clear();
    		
			if (SalarioMedioTF.getText().isBlank() && NomeTF.getText().isBlank() && CognomeTF.getText().isBlank()
					&& SkillAggiunteLV.getItems().isEmpty() && ValutazioneMediaSlider.isDisable()) {
				try {
					if (progetto != null && riunione == null)
						listaImpiegati = impiegatoDao.getAllImpiegatiSenzaCampi(salarioMedioInserito, nomeInserito,
								cognomeInserito, ordinamento, skillAggiunte, skillAggiunte.size(), progetto);
					else if (progetto == null && riunione != null)
						listaImpiegati = impiegatoDao.getAllImpiegatiSenzaCampi(salarioMedioInserito, nomeInserito,
								cognomeInserito, ordinamento, skillAggiunte, skillAggiunte.size(), riunione);
					else
						throw new SQLException();
				} catch (SQLException erroreDatabase) {
					finestraErrore = new FinestraPopup();

					try {
						finestraErrore.start(popup, "Errore di connessione al database", erroreDatabase);
					} catch (Exception e) {
						System.err.println("Errore di connessione al database");
					}
				}
			} else {
				try {
					if (progetto != null && riunione == null)
						listaImpiegati = impiegatoDao.getAllImpiegatiOrdinati(salarioMedioInserito, nomeInserito,
								cognomeInserito, ordinamento, skillAggiunte, skillAggiunte.size(), progetto,
								valutazioneMediaInserita);
					else if (progetto == null && riunione != null)
						listaImpiegati = impiegatoDao.getAllImpiegatiOrdinati(salarioMedioInserito, nomeInserito,
								cognomeInserito, ordinamento, skillAggiunte, skillAggiunte.size(), riunione,
								valutazioneMediaInserita);
					else
						throw new SQLException();
				} catch (SQLException erroreDatabase) {
					finestraErrore = new FinestraPopup();

					try {
						finestraErrore.start(popup, "Errore di connessione al database", erroreDatabase);
					} catch (Exception e) {
						System.err.println("Errore di connessione al database");
					}
				}
			}
			
			ListaRicercaImpiegatiLV.setItems(listaImpiegati);
			updateInfoImpiegato();
		
    }
    
    public void annullaOperazione() throws Exception {
    	if(progetto == null && riunione != null) {
        	homePageOrganizzatore = new HomePageOrganizzatore(riunione.getOrganizzatore(), riunione);
        	homePageOrganizzatore.start(window, popup);
    	} else if (progetto != null && riunione == null) {
    		homePageProjectManager = new HomePageProjectManager(progetto.getProjectManager(), progetto);
        	homePageProjectManager.start(window, popup);
    	} else {
    		throw new Exception();
    	}
    }
    
    public void updateInfoImpiegato()
    {
        ListaRicercaImpiegatiLV.setOnMouseClicked(new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (!ListaRicercaImpiegatiLV.getItems().isEmpty()) {
                	Impiegato infoImpiegato = ListaRicercaImpiegatiLV.getSelectionModel().getSelectedItem();
                	
					IstruzioniBox2.setVisible(false);
					ConfermaBox.setVisible(true);
					InformazioniImpiegatoBox.setVisible(true);
					SkillBox.setVisible(false);
					NomeImpiegatoTF.setText(infoImpiegato.toString());
					EmailTF.setText(infoImpiegato.getEmail());
					try {
						ComuneDiNascitaTF.setText(comuneNacitaDao
								.getComuneBySigla(infoImpiegato.getComuneNascita().toString()).toString().substring(8));
					} catch (SQLException e2) {
						e2.printStackTrace();
					}
					DataDiNascitaTF.setText(infoImpiegato.getDataNascita().toString());
					try {
						titoloDao = new TitoloDao(connection);
						skillDao = new SkillDao(connection);

						SkillComboBox.setItems(titoloDao.titoliListImpiegato(infoImpiegato));

						SkillComboBox.getSelectionModel().selectedItemProperty()
								.addListener((options, oldValue, newValue) -> {

									SkillBox.setVisible(true);

									if (SkillComboBox.getSelectionModel().getSelectedItem() != null) {
										TitoloSkillTF.setVisible(true);
										TitoloSkillTF.setText(
												SkillComboBox.getSelectionModel().getSelectedItem().toString());

										try {
											TipologiaSkillTF.setText(skillDao.getTipologiaSkill(
													SkillComboBox.getSelectionModel().getSelectedItem().toString(),
													infoImpiegato));
										} catch (SQLException e1) {
											e1.printStackTrace();
										}

										DataCertificazioneSkillLabel.setVisible(true);
										DescrizioneSkillTA.setVisible(true);
										try {
											if ((skillDao.descrizioneCertificazione(
													SkillComboBox.getSelectionModel().getSelectedItem().toString(),
													infoImpiegato) == null)) {
												DescrizioneSkillTA.setText("Nessuna descrizione");
											} else {
												DescrizioneSkillTA.setText(skillDao.descrizioneCertificazione(
														SkillComboBox.getSelectionModel().getSelectedItem().toString(),
														infoImpiegato));
											}

											DataCertificazioneTF.setText(skillDao.dataCertificazione(
													SkillComboBox.getSelectionModel().getSelectedItem().toString(),
													infoImpiegato));
										} catch (SQLException e) {
											e.printStackTrace();
										}

									} else {
										TitoloSkillTF.setVisible(false);
										DataCertificazioneTF.setVisible(false);
										DescrizioneSkillTA.setVisible(false);
									}

								});
					} catch (SQLException ex) {
						ex.printStackTrace();
					} 
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
    	Impiegato impiegatoDaAggiungere = ListaRicercaImpiegatiLV.getSelectionModel().getSelectedItem();
    	finestraAggiungiImpiegato = new FinestraPopup();
    	
    	if(progetto != null && riunione == null)
    		finestraAggiungiImpiegato.start(popup, impiegatoDaAggiungere, progetto,
    										this, RuoloImpiegatoComboBox.getSelectionModel().getSelectedItem());
    	else if (progetto == null && riunione != null)
    		finestraAggiungiImpiegato.start(popup, impiegatoDaAggiungere, riunione, this);
    	else
    		throw new Exception();
    	
        /* Procedure del database che fa un insert in Salario */
        CallableStatement aggiungiSalarioProgetto = connection.prepareCall("CALL insalarioprogetto(?)");
        aggiungiSalarioProgetto.setString(1, impiegatoDaAggiungere.getCF());
        aggiungiSalarioProgetto.execute();
    }
    
    public void NascondiInfoImpiegato() {
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