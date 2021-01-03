package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import model.Connection.DBConnection;
import model.Dao.ImpiegatoDao;
import model.Impiegato;

import java.sql.Connection;
import java.sql.SQLException;

public class ControllerHomepageImpiegato
{
    @FXML
    private Label nomeImpiegato;
    

    public void setNomeImpiegato(MouseEvent mouseEvent)
    {
        nomeImpiegato.setText("");
    }
}
