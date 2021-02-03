package view;

import javafx.application.Application;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Impiegato;

public class Driver extends Application
{
	HomePageBenvenuto homePageBenvenuto = new HomePageBenvenuto();
	
    @Override
    public void start(Stage window) {
    	Stage popup = new Stage();
        popup.initModality(Modality.APPLICATION_MODAL);
        try {
            homePageBenvenuto.start(window, popup);
        } catch (Exception e) {
        	e.printStackTrace();
        }
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}
