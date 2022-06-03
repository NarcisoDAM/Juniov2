package es.ideas;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;


public class MultiLenguajeUI extends Application {

    private static Stage escena;

    @Override
    public void start(Stage stage) throws IOException {

        MultiLenguajeUI.escena = stage;

        FXMLLoader loader = new FXMLLoader();

        loader.setLocation(getClass().getResource("Internacionalizacion.fxml"));

        //cargamos el paquete de idioma por defecto
        loader.setResources(ResourceBundle.getBundle("es.ideas.i18n/traduccion",
                Locale.getDefault()));
        Parent raiz = loader.load();

        Scene scene = new Scene(raiz);
        stage.setScene(scene);
        stage.show();

    }

    // creamos el metodo estatico para poder acceder a sus metodos desde el controlador.
    public static Stage getPrimaryStage(){
        return escena;
    }



}