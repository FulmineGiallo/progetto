package view;

import javafx.application.Application;
import javafx.stage.Stage;
import java.io.PrintWriter;
import java.sql.SQLException;


public class Driver extends Application
{
	
	HomePageBenvenuto homePageBenvenuto;
	
    @Override
    public void start(Stage primaryStage) {
        try
        {
        	homePageBenvenuto = new HomePageBenvenuto();
            homePageBenvenuto.start(primaryStage);
        } catch (Exception e) {
            System.err.println("Impossibile caricare la finestra!");
        }

    }
    
    public static void main(String[] args) {
        launch(args);
    }
}
