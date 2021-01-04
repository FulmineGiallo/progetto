package view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.PrintWriter;

public class Login
{
    private final PrintWriter writer;

    public Login(PrintWriter writer) {
        this.writer = writer ;
    }
    public void start(Stage window) throws Exception {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("view/fxml/login.fxml"));
        Scene scene =  new Scene(root);
        window.setScene(scene);
        window.show();
    }
}