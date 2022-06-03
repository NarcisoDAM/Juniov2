package es.ideas.controller;

import es.ideas.model.Rgb;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Slider;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

/**
 * La clase PaletaColoresController es la que implementa la funcionalidad de los
 * elementos de la interfaz.
 *
 * @since 1.0
 * @author Abel y Narciso
 * @see <a href="https://github.com/AbelGarcia09">Github Abel</a>
 * @see <a href="https://github.com/NarcisoDAM">Github Narciso</a>
 * @see
 * <a href="https://github.com/AbelGarcia09/PaletaColores">PaletaColores</a>
 * 
 */
public class PaletaColoresController implements Initializable {

    @FXML
    private Slider sliderRojo;
    @FXML
    private Slider sliderVerde;
    @FXML
    private Slider sliderAzul;
    @FXML
    private Circle circulo;
    @FXML
    private Label textoRojo;
    @FXML
    private Label textoVerde;
    @FXML
    private Label textoAzul;
    @FXML
    private Text hexColor;
    @FXML
    private Button boton;
    @FXML
    private ListView<Rgb> lista;
    /**
     * Elemento que representa nuestra lista donde guardar los colores
     */
    private ObservableList<Rgb> listaRgb = FXCollections.observableArrayList();
    @FXML
    private Button boton2;

    /**
     * Método initialize() que recibe los parámetros relacionados con nuestro
     * FXML para en este caso habilitar o deshabilitar botones.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        cambiaColor();

        //El botón "Añadir" se desactiva cuando un elemento de la lista está seleccionado.
        boton.disableProperty().bind(lista.getSelectionModel().selectedItemProperty().isNotNull());

        //El botón "Eliminar" se desactivar cuando no hay un elemento seleccionado.
        boton2.disableProperty().bind(lista.getSelectionModel().selectedItemProperty().isNull());

    }

    /**
     * Método que utilizamos para modificar el color del círculo de muestra.
     * Modifíca el color del circulo dependiendo de los valores seleccionados en
     * los sliders superiores.
     */
    private void cambiaColor() {
        //Se añaden los elementos del ObservableList
        lista.setItems(listaRgb);

        //Se añaden ChangeListener a los sliders
        sliderRojo.valueProperty().addListener((obs, oldVal, newVal) -> {
            //Se le da color al circulo mediante el método creaColor() que devuelve un color
            circulo.setFill(creaColor());
            //El textoRojo informa del valor del color en todo momento
            textoRojo.setText("Rojo: " + (int) sliderRojo.getValue());
            //Actualiza código Hex
            codigoHex();
        });

        sliderVerde.valueProperty().addListener((obs, oldVal, newVal) -> {
            circulo.setFill(creaColor());
            textoVerde.setText("Verde: " + (int) sliderVerde.getValue());
            codigoHex();
        });

        sliderAzul.valueProperty().addListener((obs, oldVal, newVal) -> {
            circulo.setFill(creaColor());
            textoAzul.setText("Azul: " + (int) sliderAzul.getValue());
            codigoHex();
        });
    }

    /**
     * Método que utilizamos para crear una instancia de clase Color.java con
     * los datos proporcionados por los sliders.
     *
     * @return Devuelve una instancia de la clase Color.java
     */
    private Color creaColor() {
        //Crea objeto de la clase color, pasandole los valores enteros de los sliders.
        int rojo = (int) sliderRojo.getValue();
        int verde = (int) sliderVerde.getValue();
        int azul = (int) sliderAzul.getValue();

        Color color = Color.rgb(rojo, verde, azul);

        return color;
    }

    /**
     * Este método recibe los valores que proporcionan los sliders para generar
     * el código hexadecimal que representan. Dicho de otro modo, genera el
     * código hexadecimal del color que se ve en el círculo.
     */
    private void codigoHex() {
        //se crea el código Hex pasandole los valores de los sliders. Para ello utilizamos String.format
        hexColor.setText(String.format("#%02X%02X%02X",
                (int) sliderRojo.getValue(),
                (int) sliderVerde.getValue(),
                (int) sliderAzul.getValue()));
    }

    /**
     * Este método define el funcionamiento del botón añadir, con el que
     * podremos añadir y guardar los colores que queramos en la lista inferior.
     *
     * @param event Evento que provocará la ejecución del método
     */
    @FXML
    private void accionNuevo(ActionEvent event) {
        Rgb rgb = new Rgb((int) sliderRojo.getValue(),
                (int) sliderVerde.getValue(),
                (int) sliderAzul.getValue(),
                hexColor.getText());

        int contador = 0;

        //Si la lista está vacía, se añade el elemento
        if (listaRgb.isEmpty()) {
            listaRgb.add(rgb);
            contador++;
        } else {
            //Sino está vacía, se recorre la lista en busca de un elemento igual al que voy a añadir
            //Si el elemento ya está añadido, se suma 1 al contador. Para ello comparo los códigos HEX.
            for (int i = 0; i < listaRgb.size(); i++) {
                if (listaRgb.get(i).getHex().equals(rgb.getHex())) {
                    contador++;
                }
            }
        }
        //Si el contador sigue en 0, el elemento es nuevo y se puede añadir. 
        //Si no es nuevo, no se añade porque es repetido.
        if (contador == 0) {
            listaRgb.add(rgb);
        }
    }

    /**
     * Este método define el funcionamiento del botón eliminar, con el que
     * podremos eliminar los colores que hayamos guardado previamente en la
     * lista inferior.
     *
     * @param event Evento que provocará la ejecución del método
     */
    @FXML
    private void accionEliminar(ActionEvent event) {
        //Elimina de la lista el elemento seleccionado
        listaRgb.remove(lista.getSelectionModel().getSelectedItem());
    }

}
