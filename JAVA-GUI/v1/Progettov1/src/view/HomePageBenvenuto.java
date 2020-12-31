package view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.PrintWriter;

public class HomePageBenvenuto
{


    private final PrintWriter writer;

    public HomePageBenvenuto
            (PrintWriter writer) {
        this.writer = writer ;
    }

    public void start(Stage window) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("fxml/homepagebenvenuto.fxml"));
        Scene scene =  new Scene(root);
        window.setScene(scene);
        window.show();
    }


}
