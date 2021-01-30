package controller.ControllerFinestrePopup;

import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;

public class ControllerFinestraConfermaRegistrazione extends ControllerFinestraPopup implements ControllerFinestraPopupInterface{

	//private Image immagineConferma;
	
	@Override
	public void inizializza(String titoloMessaggio, String messaggio) {
    	//Immagine.setImage(immagineConferma);
    	setBottoneSinistro();
    	setBottoneDestro();
    	
    	setTitoloMessaggio(titoloMessaggio);
    	setMessaggio(messaggio);
	}

	@Override
	public void setBottoneSinistro() {
		SinistraButton.setVisible(false);
	}

	@Override
	public void setBottoneDestro() {
		DestraButton.setText("Ok");
		DestraButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
            	FinestraPopup.getScene().getWindow().hide();
            }
        });
	}

	@Override
	public void setTitoloMessaggio(String titoloMessaggio) {
		TitoloLabel.setStyle("-fx-text-fill: white;");
		TitoloLabel.setText(titoloMessaggio);
	}

	@Override
	public void setMessaggio(String messaggio) {
		MessaggioTA.setVisible(false);
		MessaggioLabel.setVisible(true);
		
		MessaggioLabel.setText(messaggio);
	}

}
