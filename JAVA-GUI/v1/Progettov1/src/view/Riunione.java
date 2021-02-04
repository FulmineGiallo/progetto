package view;

import controller.ControllerHomePageProjectManager;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.PrintWriter;

public class Riunione
    {
        ControllerHomePageProjectManager controllerRiunione;
        private final PrintWriter writer;

        public Riunione(PrintWriter writer)
        {
            this.writer = writer;
        }

        public void start(Stage window) throws Exception {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("view/fxml/riunone.fxml"));
            Parent root = loader.load();

            controllerRiunione = loader.getController();

            Scene scene =  new Scene(root);
            window.setScene(scene);
            window.show();
        }
    }


