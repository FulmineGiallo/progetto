package controller.ControllerFinestrePopup;

import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.event.EventHandler;

public class ControllerFinestraErrore extends ControllerFinestraPopup{

    private Image immagineAttenzione = new Image(getClass().getClassLoader().getResourceAsStream("view/resources/img/warning.png"));    
	
    @Override
    public void inizializza(String titoloMessaggio, String messaggioLabel, String messaggioTextArea) {
		Immagine.setImage(immagineAttenzione);
		
		setBottoneSinistro();
		setBottoneDestro();
		
		MessaggioLabel	.setVisible(true);
		MessaggioTA		.setVisible(false);
		
		setTitoloMessaggio(titoloMessaggio);
		setMessaggioLabel(messaggioLabel);
		setMessaggioTextArea(messaggioTextArea);
	}
	
	@Override
	protected void setBottoneSinistro() {
		SinistraButton.setText("Mostra dettagli");
	    SinistraButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
	    	@Override
	        public void handle(MouseEvent mouseEvent) {
	            	
            	if (MessaggioLabel.isVisible()) {
            		
            		MessaggioLabel	.setVisible(false);
            		MessaggioTA		.setVisible(true);
            		
            	} else if (MessaggioTA.isVisible()) {
            		
            		MessaggioLabel	.setVisible(true);
            		MessaggioTA		.setVisible(false);
            	}
	        }
	    });
	}
	
	@Override
	protected void setBottoneDestro() {
		DestraButton.setText("Ok");
		
        DestraButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
            	FinestraPopup.getScene().getWindow().hide(); 
            }
        });
	}
	
	@Override
	protected void setTitoloMessaggio(String titoloMessaggio) {
		if(titoloMessaggio != null) {
			TitoloLabel.setStyle("-fx-text-fill: red;");
			TitoloLabel.setText(titoloMessaggio);
		}
	}
	
	@Override
	protected void setMessaggioLabel(String messaggioLabel) {
		if(messaggioLabel != null) {
			MessaggioLabel.setStyle("-fx-text-fill: red;");
			MessaggioLabel.setText(messaggioLabel);
		}
	}
	
	@Override
	protected void setMessaggioTextArea(String messaggioTextArea) {
		if(messaggioTextArea != null) {
			MessaggioTA.setText(messaggioTextArea);
		}
	} 
}
