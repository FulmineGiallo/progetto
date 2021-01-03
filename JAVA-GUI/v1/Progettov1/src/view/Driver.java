package view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Connection.DBConnection;
import model.Dao.ImpiegatoDao;
import model.Impiegato;

import java.sql.Connection;
import java.sql.SQLException;


public class Driver extends Application
{
    @Override
    public void start(Stage primaryStage) throws Exception
    {
        Parent root = FXMLLoader.load(getClass().getResource("fxml/homepagebenvenuto.fxml"));
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    public static void main(String[] args) throws SQLException {
        DBConnection dbconn = null;
        Connection connection = null;

        dbconn = DBConnection.getInstance();
        connection = dbconn.getConnection();


        launch(args);
    }
}
