package es.ideas.model;

/**
 * La clase Tiempo contiene los segundos, minutos, horas, recordatorios, y
 * posiciones de cada tiempo. Además, guarda el primer tiempo.
 *
 * @since 1.0
 * @author Abel y Narciso
 * @see <a href="https://github.com/AbelGarcia09">Github Abel</a>
 * @see <a href="https://github.com/NarcisoDAM">Github Narciso</a>
 * @see <a href="https://github.com/AbelGarcia09/Temporizador">Temporizador</a>
 */
public class Tiempo {

    /*En la clase tiempo tenemos segundos, minutos, horas, el recordatorio, la 
    posición asignada, y la lista de tiempos*/

    /**
     * Segundos del tiempo
     */
    String segundos;
    /**
     * Minutos del tiempo
     */
    String minutos;
    /**
     * Horas del tiempo
     */
    String horas;
    /**
     * Recordatorio del tiempo asignado por el usuario
     */
    String recordatorio;
    /**
     * Se almacenan los segundos, minutos y horas del primer tiempo.
     */
    String[] primer_tiempo = new String[3];
    /**
     * Posición del tiempo, el cual se asigna por orden de creación
     */
    int posicion;
    /**
     * Posición posterior del tiempo
     */
    static int posicion_siguiente = 0;

    /**
     * Crea un nuevo tiempo recibiendo utilizando los valores recibidos como
     * parámetros. Se guarda el primer tiempo en el array, y se asigna la
     * posición.
     * @param segundos
     * @param minutos
     * @param horas
     * @param recordatorio 
     */
    public Tiempo(String segundos, String minutos, String horas,
            String recordatorio) {
        this.segundos = segundos;
        this.minutos = minutos;
        this.horas = horas;
        this.recordatorio = recordatorio;
        //En el array guardamos el primer tiempo.
        primer_tiempo[0] = segundos;
        primer_tiempo[1] = minutos;
        primer_tiempo[2] = horas;
        /*La variable estática posicion_siguiente suma 1 posición por cada 
        instancia creada. La variable posicion es la que realmente le asignamos
        a cada instancia.*/
        posicion = posicion_siguiente;
        posicion_siguiente++;
    }

    /**
     * Permite modificar el tiempo
     * @param segundos
     * @param minutos
     * @param horas
     * @param recordatorio 
     */
    public void setTiempo(String segundos, String minutos, String horas,
            String recordatorio) {
        this.segundos = segundos;
        this.minutos = minutos;
        this.horas = horas;
        this.recordatorio = recordatorio;
    }

    /**
     * Devuelve los segundos
     * @return 
     */
    public String getSegundos() {
        return segundos;
    }
    
    /**
     * Modifica los segundos
     * @param segundos 
     */
    public void setSegundos(String segundos) {
        this.segundos = segundos;
    }

    /**
     * Devuelve los minutos
     * @return 
     */
    public String getMinutos() {
        return minutos;
    }

    /**
     * Modifica los minutos
     * @param minutos 
     */
    public void setMinutos(String minutos) {
        this.minutos = minutos;
    }

    /**
     * Devuelve las horas
     * @return 
     */
    public String getHoras() {
        return horas;
    }

    /**
     * Modifica las horas
     * @param horas 
     */
    public void setHoras(String horas) {
        this.horas = horas;
    }

    /**
     * Devuelve el recordatorio
     * @return 
     */
    public String getRecordatorio() {
        return recordatorio;
    }

    /**
     * Modifica el recordatorio
     * @param recordatorio 
     */
    public void setRecordatorio(String recordatorio) {
        this.recordatorio = recordatorio;
    }

    /**
     * Devuelve la posición
     * @return 
     */
    public int getPosicion() {
        return posicion;
    }

    /**
     * Modifica la posición
     * @param posicion 
     */
    public void setPosicion(int posicion) {
        this.posicion = posicion;
    }

    /**
     * Devuelve el array con los valores del primer tiempo
     * @return 
     */
    public String[] getPrimer_tiempo() {
        return primer_tiempo;
    }

    /**
     * Modifica el array con el array recibido como parámetro
     * @param ultimo_tiempo 
     */
    public void setPrimer_tiempo(String[] ultimo_tiempo) {
        this.primer_tiempo = ultimo_tiempo;
    }

    /**
     * Devuelve la posición siguiente
     * @return 
     */
    public static int getPosicion_siguiente() {
        return posicion_siguiente;
    }

    /**
     * Modifica la posición siguiente
     * @param posicion_siguiente 
     */
    public static void setPosicion_siguiente(int posicion_siguiente) {
        Tiempo.posicion_siguiente = posicion_siguiente;
    }

    /**
     * Sobreescribe el método toString
     * @return 
     */
    //Los items de la lista se guardan en el siguiente formato.
    @Override
    public String toString() {
        return horas + ":" + minutos + ":" + segundos;
    }

}
