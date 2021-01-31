package controller.ControllerFinestrePopup;

import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;

public class ControllerFinestraInformazioniSkill extends ControllerFinestraPopup{
    
    private Image immagineSkill = new Image(getClass().getClassLoader().getResourceAsStream("view/resources/img/skill.png"));
    
    @Override
    public void inizializza(String titoloMessaggio, String messaggioLabel, String messaggioTextArea) {
    	
    	Immagine.setImage(immagineSkill);
    	
    	setBottoneSinistro();
    	setBottoneDestro();
    	
    	setTitoloMessaggio(titoloMessaggio);
    	//setMessaggioLabel(messaggioLabel);
    	setMessaggioTextArea(messaggioTextArea);
    }
	
    @Override
	protected void setBottoneSinistro() {
		SinistraButton.setVisible(false);
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
		if (titoloMessaggio != null) {
			TitoloLabel.setStyle("-fx-text-fill: white;");
			TitoloLabel.setText(titoloMessaggio);
		}
    }
	
    /*@Override >> non utilizzato
    protected void setMessaggioLabel(String messaggioLabel) {
    	if(messaggioLabel != null){
    		
    	}
	}*/
	
    @Override
    protected void setMessaggioTextArea(String messaggioTextArea) {
		if (messaggioTextArea != null) {
			MessaggioTA.setVisible(true);
			MessaggioLabel.setVisible(false);
			MessaggioTA.setText(messaggioTextArea);
		}
	}
}
