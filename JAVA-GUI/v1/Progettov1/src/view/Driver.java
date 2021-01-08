package view;

import javafx.application.Application;
import javafx.stage.Stage;
import java.io.PrintWriter;
import java.sql.SQLException;


public class Driver extends Application
{
	
	HomePageBenvenuto homePageBenvenuto;
    @Override
    public void start(Stage primaryStage)
    {
        try
        {
        	PrintWriter writer = null;
        	homePageBenvenuto = new HomePageBenvenuto(writer);
            homePageBenvenuto.start(primaryStage);
        } catch (Exception e) {
            System.err.println("Impossibile caricare la finestra!");
        }

    }
    public static void main(String[] args) throws SQLException
    {
        launch(args);
    }
}
