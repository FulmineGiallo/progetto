package controller.ControllerFinestrePopup;

import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;

public class ControllerFinestraInformazioniSkill extends ControllerFinestraPopup implements ControllerFinestraPopupInterface{
    
    private Image immagineSkill = new Image(getClass().getClassLoader().getResourceAsStream("view/resources/img/skill.png"));
    
    public void inizializza(String titoloMessaggio, String messaggio) {
    	
    	Immagine.setImage(immagineSkill);
    	setBottoneSinistro();
    	setBottoneDestro();
    	
    	setTitoloMessaggio(titoloMessaggio);
    	setMessaggio(messaggio);
    }
	
	public void setBottoneSinistro() {
		DestraButton.setText("Ok");
		DestraButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
            	FinestraPopup.getScene().getWindow().hide();
            }
        });
    }
	
	public void setBottoneDestro() {
		SinistraButton.setVisible(false);
	}
	
	public void setTitoloMessaggio(String titoloMessaggio) {
		TitoloLabel.setStyle("-fx-text-fill: white;");
		TitoloLabel.setText(titoloMessaggio);
	}
	
	public void setMessaggio(String messaggio) {
		MessaggioTA.setVisible(true);
		MessaggioLabel.setVisible(false);
		
		MessaggioTA.setText(messaggio);
	}
}
