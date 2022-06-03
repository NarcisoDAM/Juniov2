package es.ideas.controller;

import es.ideas.model.Tiempo;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.util.Duration;

/**
 * Clase controladora que se encarga de dar funcionalidad a los elementos de la
 * vista.
 *
 * @since 1.0
 * @author Abel y Narciso
 * @see <a href="https://github.com/AbelGarcia09">Github Abel</a>
 * @see <a href="https://github.com/NarcisoDAM">Github Narciso</a>
 * @see <a href="https://github.com/AbelGarcia09/Temporizador">Temporizador</a>
 */
public class TemporizadorController implements Initializable {

    @FXML
    private Label horas;
    @FXML
    private Label minutos;
    @FXML
    private Label segundos;
    @FXML
    private Button idPlay;
    @FXML
    private Button idPause;
    @FXML
    private Button idReset;
    @FXML
    private Button idNuevo;
    @FXML
    private Button idSubeHora;
    @FXML
    private Button idSubeMin;
    @FXML
    private Button idSubeSec;
    @FXML
    private Button idBajaHora;
    @FXML
    private Button idBajaMin;
    @FXML
    private Button idBajaSec;
    @FXML
    private Label horaActual;
    @FXML
    private TextField recordatorio;
    @FXML
    private ListView<Tiempo> listView;
    /**
     * Almacena los tiempos que se van a incorporar al ListView.
     */
    private ObservableList<Tiempo> listaTiempo = FXCollections.observableArrayList();
    /**
     * Da formato de hora a una fecha.
     */
    private final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
    /**
     * Línea de tiempo para ir actualizando el temporizador.
     */
    private Timeline temporizador;
    /**
     * Línea de tiempo para ir actualizando la hora.
     */
    private Timeline timelineHora;
    /**
     * Contiene las horas.
     */
    private int hora;
    /**
     * Contiene los minutos.
     */
    private int minuto; 
    /**
     * Contiene los segundos.
     */
    private int segundo;
    /**
     * Diálogo de alerta que aparece al finalizar un temporizador.
     */
    private final Alert fin = new Alert(Alert.AlertType.INFORMATION);
    /**
     * ChangeListener para cambiar la selección de un item de la lista.
     */
    private ChangeListener<Tiempo> tiempoChangeListener;
    /**
     * Objeto del modelo de datos para llevar el valor del temporizador.
     */
    private Tiempo tiempo;

    /**
     * Método initialize() que recibe los parámetros relacionados con nuestro
     * FXML para mostrar información en la lista, establecer el estado de los 
     * botones ( enabled: true or false ) y definir el estado inicial de 
     * la aplicación.
     * @param url
     * @param rb 
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        /*Timeline de la hora actual. Uso DateTimeFormatter para conseguir el
        formato de hora, y LocalDateTime para conseguir la hora actual. 
        Se muestra en un label.*/
        timelineHora = new Timeline(
                new KeyFrame(Duration.millis(1000), (ActionEvent evento) -> {
                    horaActual.setText(dtf.format(LocalDateTime.now()));
                }));
        timelineHora.setCycleCount(Timeline.INDEFINITE);
        timelineHora.play();

        /*Timeline del temporizador. Ejecuta el método comprobarTimer() que se 
        encarga del funcionamiento y reflejar los cambios en los label*/
        temporizador = new Timeline(
                new KeyFrame(Duration.millis(1000), (ActionEvent evento) -> {
                    comprobarTimer();
                }));
        temporizador.setCycleCount(Timeline.INDEFINITE);

        //Deseleccionamos el textField de recordatorio al iniciar el programa.
        recordatorio.setFocusTraversable(false);

        /*Este método muestra los cambios en el Timeline. Lo ejecutamos de 
        inicio para que el contador se muestre a 0*/
        muestraTiempo();
        /*Comprueba si el contador está en 0. En caso de estar en 0, desactiva y
        activa los botones que creemos convenientes*/
        compruebaCero();

        //Se añaden los elementos del ObservableList al ListView.
        listView.setItems(listaTiempo);
        /*Cuando un elemento de la lista está seleccionado, transalamos los 
        datos guardados en este de nuevo al temporizador*/
        listView.getSelectionModel().selectedItemProperty().addListener(
                tiempoChangeListener = (observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        horas.setText(newValue.getHoras());
                        minutos.setText(newValue.getMinutos());
                        segundos.setText(newValue.getSegundos());
                        recordatorio.setText(newValue.getRecordatorio());
                        //Método que cambia el estado de los botones
                        botonesStatus(false);
                    }
                });
    }

    /**
     * Este método define la funcionalidad del botón Play y las acciones que se 
     * llevan a cabo cuando se interactúa con el botón. En este caso, iniciar 
     * el temporizador o reanudar su ejecucion si ha sido previamente pausado.
     * @param event 
     */
    @FXML
    private void play(ActionEvent event) {
        /*Al pulsar Play, guardamos cada tiempo en una clase Tiempo. Un nuevo 
        tiempo solo se crea si el estado del timeline es STOPPED. No vale con 
        que esté pausado. En caso de estar pausado, se sigue utilizando el mismo
        "Tiempo" cada vez que se pulse el play. Además no puede haber un item 
        seleccionado en la lista. Si no se cumple esta condición, "tiempo" 
        pasará a ser el item que este seleccionado en la lista*/
        if (temporizador.getStatus().toString().equals("STOPPED")
                && listView.getSelectionModel().getSelectedItem() == null) {
            tiempo = new Tiempo(
                    segundos.getText(),
                    minutos.getText(),
                    horas.getText(),
                    recordatorio.getText());
            //Una vez creado el Tiempo, se añade a la lista.
            listaTiempo.add(tiempo);
        } else {
            for (int i = 0; i < listaTiempo.size(); i++) {
                if (listView.getSelectionModel().isSelected(i)) {
                    tiempo = listView.getSelectionModel().getSelectedItem();
                }
            }
        }
        //Se actualiza la lista
        actualizaLista();
        //Ejecutamos el temporizador y cambiamos de estado los botones.
        temporizador.play();
        botonesStatus(true);
    }

    /**
     * Este método define la funcionalidad del botón Pause y las acciones que se 
     * llevan a cabo cuando se interactúa con el botón. En este caso, pausa
     * la ejecución del temporizador, y se puede volver a reanudar desde el 
     * mismo punto en que fue pausado.
     * @param event 
     */
    @FXML
    private void pause(ActionEvent event) {
        //Al pulsar pausa, se pausa el temporizador
        temporizador.pause();
        /*Si hay un tiempo seleccionado en la lista, el tiempo "actual" pasará
        a ser el que acabamos de seleccionar, y se actualizarán los label*/
        if (listView.getSelectionModel().getSelectedItem() != null) {
            tiempo = listView.getSelectionModel().getSelectedItem();
        }

        actualizaLista();
        //Cambiamos el estado de los botones al contrario que en el Play.
        botonesStatus(false);
    }

    /**
     * Este método define la funcionalidad del botón Reset y las acciones que se 
     * llevan a cabo cuando se interactúa con el botón. En este caso resetear 
     * el temporizador al primer valor guardado, es decir, al valor inicial
     * desde el que ha empezado a contar el temporizador.
     * @param event 
     */
    @FXML
    private void reset(ActionEvent event) {
        //Cogemos el tiempo del item seleccionado
        if (listView.getSelectionModel().getSelectedItem() != null) {
            tiempo = listView.getSelectionModel().getSelectedItem();
        }

        /*En la clase Tiempo, siempre se guarda en un array el primer tiempo
        que hemos añadido para así poder volver a utilizarlo. Entonces cuando
        le damos al botón de reset, volvemos a recuperar el primer tiempo*/
        String[] array = tiempo.getPrimer_tiempo();
        segundos.setText(array[0]);
        minutos.setText(array[1]);
        horas.setText(array[2]);

        actualizaLista();
    }

    /**
     * Este método define la funcionalidad del botón Añadir y las acciones que se 
     * llevan a cabo cuando se interactúa con el botón. En este caso, añadir un 
     * elemento a la lista y poner el contador a cero para uno nuevo
     * @param event 
     */
    @FXML
    private void nuevo(ActionEvent event) {
        //Crea un nuevo temporizador. 
        //Para ello deselecciona los item de la lista.
        listView.getSelectionModel().clearSelection();

        //Lo pone todo a 0. Como recien abierto el programa.
        segundos.setText("00");
        minutos.setText("00");
        horas.setText("00");
        recordatorio.setText("");

        //Se ejecuta el método para que se compruebe que está todo a 0.
        compruebaCero();
        /*El temporizador se para, no se pausa. Es decir, la proxima vez que le 
        demos al play, se creará uno nuevo.*/
        temporizador.stop();
    }

    /*Los 6 siguientes métodos se encargan de los botones para subir o bajar los
    parámetros del temporizador. Estos llaman al método manipulaTiempo, el cual
    se encarga de realizar la acción pasandole el label que queremos modificar
    y la acción que queremos realizar, ya sea sumar o restar.*/
    
    /**
     * Este metodo define la funcionalidad del botón encargado de aumentar los
     * segundos.
     * @param event 
     */
    @FXML
    private void subeSec(ActionEvent event) {
        manipulaTiempo(segundos, "SUMA");
    }

    /**
     * Este metodo define la funcionalidad del botón encargado de aumentar los
     * minutos.
     * @param event 
     */
    @FXML
    private void subeMin(ActionEvent event) {
        manipulaTiempo(minutos, "SUMA");
    }

    /**
     * Este metodo define la funcionalidad del botón encargado de aumentar las
     * horas.
     * @param event 
     */
    @FXML
    private void subeHora(ActionEvent event) {
        manipulaTiempo(horas, "SUMA");
    }

    /**
     * Este metodo define la funcionalidad del botón encargado de disminuir los
     * segundos.
     * @param event 
     */
    @FXML
    private void bajaSec(ActionEvent event) {
        manipulaTiempo(segundos, "RESTA");
    }

    /**
     * Este metodo define la funcionalidad del botón encargado de disminuir los
     * minutos.
     * @param event 
     */
    @FXML
    private void bajaMin(ActionEvent event) {
        manipulaTiempo(minutos, "RESTA");
    }

    /**
     * Este metodo define la funcionalidad del botón encargado de disminuir las
     * horas.
     * @param event 
     */
    @FXML
    private void bajaHora(ActionEvent event) {
        manipulaTiempo(horas, "RESTA");
    }

    /**
     * Controla el tiempo.
     */
    private void comprobarTimer() {
        //Pasamos a enteros el texto de los label para poder manipularlos mejor.
        hora = Integer.parseInt(horas.getText());
        minuto = Integer.parseInt(minutos.getText());
        segundo = Integer.parseInt(segundos.getText());

        /*Se restará un segundo mientras que estos sean mayor que 0. Si no, 
        los segundos volverán a 59 y además, se volverá a evaluar de la misma 
        manera los minutos, y en caso de ser necesario, las horas*/
        if (segundo > 0) {
            segundo--;
        } else {
            segundo = 59;
            if (minuto > 0) {
                minuto--;
            } else {
                minuto = 59;
                if (hora > 0) {
                    hora--;
                }
            }
        }

        //Volvemos a reflejar los cambios en los label
        muestraTiempo();

        //Cada segundo también se comprueba si el temporizador ha llegado a 0.
        if (hora == 0 && minuto == 0 && segundo == 0) {
            //Se para el timeline y se actualiza la lista
            temporizador.stop();
            actualizaLista();

            /*Aparece un mensaje de alarma avisando que el temporizador ha 
            llegado a su fin y enseñando el recordatorio de ese tiempo*/
            fin.setHeaderText("TEMPORIZADOR FINALIZADO");
            fin.setContentText(tiempo.getRecordatorio());
            fin.show();

            compruebaCero();
        }
    }

    /**
     * Este método se encarga de mostrar los números en los contadores.
     */
    private void muestraTiempo() {
        /*Se encarga de pasar los enteros a Label para mostrarlos. En caso de 
        ser números menores de 10, se les añade un 0 antes.*/
        if (hora < 10) {
            horas.setText("0" + hora);
        } else {
            horas.setText("" + hora);
        }

        if (minuto < 10) {
            minutos.setText("0" + minuto);
        } else {
            minutos.setText("" + minuto);
        }

        if (segundo < 10) {
            segundos.setText("0" + segundo);
        } else {
            segundos.setText("" + segundo);
        }
    }

    /**
     * Este método define el estado de los botones según los valores del 
     * temporizador en cada momento. Por ejemplo, que el botón play se 
     * deshabilite cuando el contador está a cero.
     */
    private void compruebaCero() {
        /*Si el temporizador está a 0, se desactivan los botones de play, pause,
        reset, y añadir. Y se activan los demás. En caso contrario, solo se 
        activa el de play.*/
        if (horas.getText().equals("00")
                && minutos.getText().equals("00")
                && segundos.getText().equals("00")) {
            idPlay.setDisable(true);
            idPause.setDisable(true);
            idReset.setDisable(true);
            idNuevo.setDisable(true);
            idSubeHora.setDisable(false);
            idSubeMin.setDisable(false);
            idSubeSec.setDisable(false);
            idBajaHora.setDisable(false);
            idBajaMin.setDisable(false);
            idBajaSec.setDisable(false);
            listView.setDisable(false);
            recordatorio.setDisable(false);
        } else {
            idPlay.setDisable(false);
        }
    }

    /**
     * Este método recibe y transfiere información sobre el estado de los 
     * botones. Cuando su estado es false, se deshabilitan, y cuando es true
     * se habilitan. Esto viene determinado por el estado del temporizador.
     * @param estado 
     */
    private void botonesStatus(boolean estado) {
        /*Si le pasamos un true, se desactivarán todos los botones menos el de
        pausa. Si le pasamos un false, al contrario. El botón de play pasa un
        true, y el de pausa pasa un false.*/
        idPlay.setDisable(estado);
        idPause.setDisable(!estado);
        idReset.setDisable(estado);
        idNuevo.setDisable(estado);
        idSubeHora.setDisable(estado);
        idSubeMin.setDisable(estado);
        idSubeSec.setDisable(estado);
        idBajaHora.setDisable(estado);
        idBajaMin.setDisable(estado);
        idBajaSec.setDisable(estado);
        listView.setDisable(estado);
        recordatorio.setDisable(estado);
    }

    /**
     * Este método define la funcionalidad de los botones para aumentar o 
     * disminuir los contadores de horas, minutos y segundos.
     * @param tipo
     * @param accion 
     */
    private void manipulaTiempo(Label tipo, String accion) {
        //Método que da funcionalidad a los botones de sumar o bajar números.
        //Pasamos a entero el número recibido en el label
        int total = Integer.parseInt(tipo.getText());

        /*Si la acción que recibimos del botón es "SUMA", se sumará una unidad.
        Si no, restará una unidad. Solo funciona mientras que los números estén
        entre 0 y 59*/
        if (accion.equals("SUMA")) {
            if (total < 59 && total >= 0) {
                if (total < 9) {
                    tipo.setText("0" + (total + 1));
                } else {
                    tipo.setText("" + (total + 1));
                }
            }
        }

        if (accion.equals("RESTA")) {
            if (total <= 59 && total > 0) {
                if (total <= 10) {
                    tipo.setText("0" + (total - 1));
                } else {
                    tipo.setText("" + (total - 1));
                }
            }
        }

        compruebaCero();
    }

    /**
     * Este método se encarga de actualizar la lista de contadores de la parte
     * izquierda de la aplicación.
     */
    private void actualizaLista() {
        //Método que actualiza la lista. Solo es usado en el botón pausa y reset
        tiempo.setTiempo(segundos.getText(),
                minutos.getText(),
                horas.getText(),
                recordatorio.getText());
        listaTiempo.set(tiempo.getPosicion(), tiempo);
    }

}
