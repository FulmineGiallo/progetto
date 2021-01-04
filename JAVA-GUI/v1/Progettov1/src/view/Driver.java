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
import java.sql.Connection;
import java.sql.SQLException;


public class Driver extends Application
{
    @Override
    public void start(Stage primaryStage) throws Exception
    {
        try
        {
            Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("view/fxml/homepagebenvenuto.fxml"));
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
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
