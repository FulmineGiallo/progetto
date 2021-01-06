package view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Connection.DBConnection;
import model.Dao.ImpiegatoDao;
import model.Impiegato;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
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
    public static void main(String[] args) throws SQLException {
        /*
        DBConnection dbconn = null;
        Connection connection = null;

        dbconn = DBConnection.getInstance();
        connection = dbconn.getConnection();
        */
        launch(args);
    }
}
