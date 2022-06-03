package es.ideas.controller;

import es.ideas.App;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class PrimaryController {

    @FXML
    private Button btnhojas;
    @FXML
    private Button btnAnimaciones;
    @FXML
    private Button btnListeners;
    @FXML
    private Button btnInternacionalizacion;
    @FXML
    private Button btnJavadoc;

    @FXML
    private void openHojasEstilos(ActionEvent event) {
    }

    @FXML
    private void openAnimacionesYEstilos(ActionEvent event) throws IOException {
        App.setRoot("view/TemporizadorFXML");
    }

    @FXML
    private void openListenersYBlinders(ActionEvent event) throws IOException {
        App.setRoot("view/PaletaColoresFXML");
    }

    @FXML
    private void openi18n(ActionEvent event) {
    }

    @FXML
    private void openJavadoc(ActionEvent event) {
    }

}
