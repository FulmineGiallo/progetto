package view;

import java.io.IOException;
import java.io.PrintWriter;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class CaricamentoRegistrazione {
	
	private final PrintWriter writer;

    public CaricamentoRegistrazione(PrintWriter writer) {
        this.writer = writer ;
    }

    public void start(Stage window){
        try {
			Parent root = FXMLLoader.load(getClass().getResource("@../../src/view/fxml/CaricamentoRegistrazione.fxml"));
			Scene scene = new Scene(root);
			
			window.setResizable(false);
			window.setTitle("Caricamento in corso...");
			window.centerOnScreen();
			
			window.setScene(scene);
			window.show();
		} catch (IOException e) {
			System.err.println("Impossibile caricare la finestra!");
			//e.printStackTrace();
		}
    }
}
