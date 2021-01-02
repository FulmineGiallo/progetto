package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import view.HomePageBenvenuto;

import java.io.PrintWriter;

public class ControllerLogin
{
    HomePageBenvenuto benvenuto;
    @FXML
    public Button backHomePage;

    public void backHomePageBenvenuto(ActionEvent actionEvent) throws Exception
    {
        PrintWriter writer = null;
        benvenuto = new HomePageBenvenuto(writer);

        Stage stage = (Stage) backHomePage.getScene().getWindow();
        benvenuto.start(stage);
    }
}
