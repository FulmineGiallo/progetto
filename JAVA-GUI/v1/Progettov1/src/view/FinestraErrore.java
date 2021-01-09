package view;

import java.io.PrintWriter;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import controller.ControllerFinestraErrore;

public class FinestraErrore {
	
	private ControllerFinestraErrore controllerFinestraErrore;
    private final PrintWriter writer;

	public FinestraErrore(PrintWriter writer)
    {
        this.writer = writer;
    }
	
    public void start(Stage window) throws Exception
    {
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("view/fxml/FinestraErrore.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        window.setScene(scene);
        
        controllerFinestraErrore = loader.getController();
        
        window.setTitle("Errore!");
        window.setResizable(true);
        window.setMinWidth(400.0);
        window.setMinHeight(200.0);
        window.centerOnScreen();
        
        window.show();
    }
}
