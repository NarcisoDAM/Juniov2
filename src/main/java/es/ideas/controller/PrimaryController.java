package es.ideas.controller;

import es.ideas.App;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class PrimaryController {

    @FXML
    private Button btnprueba;
    @FXML
    private Button btnTemporizador;

    @FXML
    private void pruebabtn(ActionEvent event) throws IOException{
        App.setRoot("view/PaletaColoresFXML");
    }

    @FXML
    private void btnTemporizador(ActionEvent event) throws IOException{
        App.setRoot("view/TemporizadorFXML");
    }

}
