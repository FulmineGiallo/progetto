package controller.ControllerFinestrePopup;

import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import view.HomePageBenvenuto;

public class ControllerFinestraLogout extends ControllerFinestraPopup {

	private Image immagineDomanda = new Image(getClass().getClassLoader().getResourceAsStream("view/resources/img/question.png"));
    private HomePageBenvenuto homePageBenvenuto;
    
    private Stage window;
    private Stage popup;
    
    public void setStage(Stage window, Stage popup) {
    	this.window = window;
    	this.popup = popup;
    }
	
	@Override
    public void inizializza(String titoloMessaggio, String messaggioLabel, String messaggioTextArea) {
		
		Immagine.setImage(immagineDomanda);
		
		setBottoneSinistro();
		setBottoneDestro();
		
		//setTitoloMessaggio(titoloMessaggio);
		setMessaggioLabel(messaggioLabel);
		//setMessaggioTextArea(messaggioTextArea);
	}
	
	@Override
	protected void setBottoneSinistro() {
		SinistraButton.setText("Annulla");
		
		SinistraButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
            	FinestraPopup.getScene().getWindow().hide();
            }
        });
	}
	
	@Override
	protected void setBottoneDestro() {
		DestraButton.setText("Conferma");
		
		DestraButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
            public void handle(MouseEvent mouseEvent) {
                FinestraPopup.getScene().getWindow().hide();
                
                homePageBenvenuto = new HomePageBenvenuto();
                homePageBenvenuto.start(window, popup);
			}
        });
	}
	
	/*@Override >> non utilizzato
	protected void setTitoloMessaggio(String titoloMessaggio) {
		if(titoloMessaggio != null) {
			
		}
	}*/
	
	@Override
	protected void setMessaggioLabel(String messaggioLabel) {
		if(messaggioLabel != null) {
			MessaggioTA.setVisible(false);
			MessaggioLabel.setVisible(true);
			
			MessaggioLabel.setText(messaggioLabel);
		}
	}
	
	/*@Override >> non utilizzato
	protected void setMessaggioTextArea(String messaggioTextArea) {
		if(messaggioTextArea != null) {
			
		}
	}*/
}
