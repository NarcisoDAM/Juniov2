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
     * L??nea de tiempo para ir actualizando el temporizador.
     */
    private Timeline temporizador;
    /**
     * L??nea de tiempo para ir actualizando la hora.
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
     * Di??logo de alerta que aparece al finalizar un temporizador.
     */
    private final Alert fin = new Alert(Alert.AlertType.INFORMATION);
    /**
     * ChangeListener para cambiar la selecci??n de un item de la lista.
     */
    private ChangeListener<Tiempo> tiempoChangeListener;
    /**
     * Objeto del modelo de datos para llevar el valor del temporizador.
     */
    private Tiempo tiempo;

    /**
     * M??todo initialize() que recibe los par??metros relacionados con nuestro
     * FXML para mostrar informaci??n en la lista, establecer el estado de los 
     * botones ( enabled: true or false ) y definir el estado inicial de 
     * la aplicaci??n.
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

        /*Timeline del temporizador. Ejecuta el m??todo comprobarTimer() que se 
        encarga del funcionamiento y reflejar los cambios en los label*/
        temporizador = new Timeline(
                new KeyFrame(Duration.millis(1000), (ActionEvent evento) -> {
                    comprobarTimer();
                }));
        temporizador.setCycleCount(Timeline.INDEFINITE);

        //Deseleccionamos el textField de recordatorio al iniciar el programa.
        recordatorio.setFocusTraversable(false);

        /*Este m??todo muestra los cambios en el Timeline. Lo ejecutamos de 
        inicio para que el contador se muestre a 0*/
        muestraTiempo();
        /*Comprueba si el contador est?? en 0. En caso de estar en 0, desactiva y
        activa los botones que creemos convenientes*/
        compruebaCero();

        //Se a??aden los elementos del ObservableList al ListView.
        listView.setItems(listaTiempo);
        /*Cuando un elemento de la lista est?? seleccionado, transalamos los 
        datos guardados en este de nuevo al temporizador*/
        listView.getSelectionModel().selectedItemProperty().addListener(
                tiempoChangeListener = (observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        horas.setText(newValue.getHoras());
                        minutos.setText(newValue.getMinutos());
                        segundos.setText(newValue.getSegundos());
                        recordatorio.setText(newValue.getRecordatorio());
                        //M??todo que cambia el estado de los botones
                        botonesStatus(false);
                    }
                });
    }

    /**
     * Este m??todo define la funcionalidad del bot??n Play y las acciones que se 
     * llevan a cabo cuando se interact??a con el bot??n. En este caso, iniciar 
     * el temporizador o reanudar su ejecucion si ha sido previamente pausado.
     * @param event 
     */
    @FXML
    private void play(ActionEvent event) {
        /*Al pulsar Play, guardamos cada tiempo en una clase Tiempo. Un nuevo 
        tiempo solo se crea si el estado del timeline es STOPPED. No vale con 
        que est?? pausado. En caso de estar pausado, se sigue utilizando el mismo
        "Tiempo" cada vez que se pulse el play. Adem??s no puede haber un item 
        seleccionado en la lista. Si no se cumple esta condici??n, "tiempo" 
        pasar?? a ser el item que este seleccionado en la lista*/
        if (temporizador.getStatus().toString().equals("STOPPED")
                && listView.getSelectionModel().getSelectedItem() == null) {
            tiempo = new Tiempo(
                    segundos.getText(),
                    minutos.getText(),
                    horas.getText(),
                    recordatorio.getText());
            //Una vez creado el Tiempo, se a??ade a la lista.
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
     * Este m??todo define la funcionalidad del bot??n Pause y las acciones que se 
     * llevan a cabo cuando se interact??a con el bot??n. En este caso, pausa
     * la ejecuci??n del temporizador, y se puede volver a reanudar desde el 
     * mismo punto en que fue pausado.
     * @param event 
     */
    @FXML
    private void pause(ActionEvent event) {
        //Al pulsar pausa, se pausa el temporizador
        temporizador.pause();
        /*Si hay un tiempo seleccionado en la lista, el tiempo "actual" pasar??
        a ser el que acabamos de seleccionar, y se actualizar??n los label*/
        if (listView.getSelectionModel().getSelectedItem() != null) {
            tiempo = listView.getSelectionModel().getSelectedItem();
        }

        actualizaLista();
        //Cambiamos el estado de los botones al contrario que en el Play.
        botonesStatus(false);
    }

    /**
     * Este m??todo define la funcionalidad del bot??n Reset y las acciones que se 
     * llevan a cabo cuando se interact??a con el bot??n. En este caso resetear 
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
        que hemos a??adido para as?? poder volver a utilizarlo. Entonces cuando
        le damos al bot??n de reset, volvemos a recuperar el primer tiempo*/
        String[] array = tiempo.getPrimer_tiempo();
        segundos.setText(array[0]);
        minutos.setText(array[1]);
        horas.setText(array[2]);

        actualizaLista();
    }

    /**
     * Este m??todo define la funcionalidad del bot??n A??adir y las acciones que se 
     * llevan a cabo cuando se interact??a con el bot??n. En este caso, a??adir un 
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

        //Se ejecuta el m??todo para que se compruebe que est?? todo a 0.
        compruebaCero();
        /*El temporizador se para, no se pausa. Es decir, la proxima vez que le 
        demos al play, se crear?? uno nuevo.*/
        temporizador.stop();
    }

    /*Los 6 siguientes m??todos se encargan de los botones para subir o bajar los
    par??metros del temporizador. Estos llaman al m??todo manipulaTiempo, el cual
    se encarga de realizar la acci??n pasandole el label que queremos modificar
    y la acci??n que queremos realizar, ya sea sumar o restar.*/
    
    /**
     * Este metodo define la funcionalidad del bot??n encargado de aumentar los
     * segundos.
     * @param event 
     */
    @FXML
    private void subeSec(ActionEvent event) {
        manipulaTiempo(segundos, "SUMA");
    }

    /**
     * Este metodo define la funcionalidad del bot??n encargado de aumentar los
     * minutos.
     * @param event 
     */
    @FXML
    private void subeMin(ActionEvent event) {
        manipulaTiempo(minutos, "SUMA");
    }

    /**
     * Este metodo define la funcionalidad del bot??n encargado de aumentar las
     * horas.
     * @param event 
     */
    @FXML
    private void subeHora(ActionEvent event) {
        manipulaTiempo(horas, "SUMA");
    }

    /**
     * Este metodo define la funcionalidad del bot??n encargado de disminuir los
     * segundos.
     * @param event 
     */
    @FXML
    private void bajaSec(ActionEvent event) {
        manipulaTiempo(segundos, "RESTA");
    }

    /**
     * Este metodo define la funcionalidad del bot??n encargado de disminuir los
     * minutos.
     * @param event 
     */
    @FXML
    private void bajaMin(ActionEvent event) {
        manipulaTiempo(minutos, "RESTA");
    }

    /**
     * Este metodo define la funcionalidad del bot??n encargado de disminuir las
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

        /*Se restar?? un segundo mientras que estos sean mayor que 0. Si no, 
        los segundos volver??n a 59 y adem??s, se volver?? a evaluar de la misma 
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

        //Cada segundo tambi??n se comprueba si el temporizador ha llegado a 0.
        if (hora == 0 && minuto == 0 && segundo == 0) {
            //Se para el timeline y se actualiza la lista
            temporizador.stop();
            actualizaLista();

            /*Aparece un mensaje de alarma avisando que el temporizador ha 
            llegado a su fin y ense??ando el recordatorio de ese tiempo*/
            fin.setHeaderText("TEMPORIZADOR FINALIZADO");
            fin.setContentText(tiempo.getRecordatorio());
            fin.show();

            compruebaCero();
        }
    }

    /**
     * Este m??todo se encarga de mostrar los n??meros en los contadores.
     */
    private void muestraTiempo() {
        /*Se encarga de pasar los enteros a Label para mostrarlos. En caso de 
        ser n??meros menores de 10, se les a??ade un 0 antes.*/
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
     * Este m??todo define el estado de los botones seg??n los valores del 
     * temporizador en cada momento. Por ejemplo, que el bot??n play se 
     * deshabilite cuando el contador est?? a cero.
     */
    private void compruebaCero() {
        /*Si el temporizador est?? a 0, se desactivan los botones de play, pause,
        reset, y a??adir. Y se activan los dem??s. En caso contrario, solo se 
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
     * Este m??todo recibe y transfiere informaci??n sobre el estado de los 
     * botones. Cuando su estado es false, se deshabilitan, y cuando es true
     * se habilitan. Esto viene determinado por el estado del temporizador.
     * @param estado 
     */
    private void botonesStatus(boolean estado) {
        /*Si le pasamos un true, se desactivar??n todos los botones menos el de
        pausa. Si le pasamos un false, al contrario. El bot??n de play pasa un
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
     * Este m??todo define la funcionalidad de los botones para aumentar o 
     * disminuir los contadores de horas, minutos y segundos.
     * @param tipo
     * @param accion 
     */
    private void manipulaTiempo(Label tipo, String accion) {
        //M??todo que da funcionalidad a los botones de sumar o bajar n??meros.
        //Pasamos a entero el n??mero recibido en el label
        int total = Integer.parseInt(tipo.getText());

        /*Si la acci??n que recibimos del bot??n es "SUMA", se sumar?? una unidad.
        Si no, restar?? una unidad. Solo funciona mientras que los n??meros est??n
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
     * Este m??todo se encarga de actualizar la lista de contadores de la parte
     * izquierda de la aplicaci??n.
     */
    private void actualizaLista() {
        //M??todo que actualiza la lista. Solo es usado en el bot??n pausa y reset
        tiempo.setTiempo(segundos.getText(),
                minutos.getText(),
                horas.getText(),
                recordatorio.getText());
        listaTiempo.set(tiempo.getPosicion(), tiempo);
    }

}
