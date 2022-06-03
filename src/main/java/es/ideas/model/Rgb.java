package es.ideas.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * La clase RGB representa los valores de un color RGB y su código hexadecimal.
 *
 * @since 1.0
 * @author Abel y Narciso
 * @see <a href="https://github.com/AbelGarcia09">Github Abel</a>
 * @see <a href="https://github.com/NarcisoDAM">Github Narciso</a>
 * @see <a href="https://github.com/AbelGarcia09/PaletaColores">PaletaColores</a>
 */
public class Rgb {

    /**
     * Valor del slider rojo
     */
    private final IntegerProperty red = new SimpleIntegerProperty(this, "red", 0);
    /**
     * Valor del slider verde
     */
    private final IntegerProperty green = new SimpleIntegerProperty(this, "green", 0);
    /**
     * Valor del slider azul
     */
    private final IntegerProperty blue = new SimpleIntegerProperty(this, "blue", 0);
    /**
     * Valor del código hexadecimal
     */
    private final StringProperty hex = new SimpleStringProperty(this, "hex", "");

    /**
     * Crea un objeto RGB recibiendo los valores de cada color y su código hex.
     *
     * @param red
     * @param green
     * @param blue
     * @param hex
     */
    public Rgb(int red, int green, int blue, String hex) {
        this.red.set(red);
        this.green.set(green);
        this.blue.set(blue);
        this.hex.set(hex);
    }

    /**
     * Devuelve el valor rojo del objeto
     *
     * @return
     */
    public int getRed() {
        return red.getValue();
    }

    /**
     * Devuelve el valor verde del objeto
     *
     * @return
     */
    public int getGreen() {
        return green.getValue();
    }

    /**
     * Devuelve el valor azul del objeto
     *
     * @return
     */
    public int getBlue() {
        return blue.getValue();
    }

    /**
     * Devuelve el código hexadecimal del objeto
     *
     * @return
     */
    public String getHex() {
        return hex.getValue();
    }

    /**
     * Devuelve la propiedad rojo del objeto
     *
     * @return
     */
    public IntegerProperty redProperty() {
        return red;
    }

    /**
     * Devuelve la propiedad verde del objeto
     *
     * @return
     */
    public IntegerProperty greenProperty() {
        return green;
    }

    /**
     * Devuelve la propiedad azul del objeto
     *
     * @return
     */
    public IntegerProperty blueProperty() {
        return blue;
    }

    /**
     * Devuelve la propiedad hexadecimal del objeto
     *
     * @return
     */
    public StringProperty hexProperty() {
        return hex;
    }

    /**
     * Da un nuevo valor a la propiedad rojo
     *
     * @param nuevoValor
     */
    public void setRed(int nuevoValor) {
        this.red.setValue(nuevoValor);
    }

    /**
     * Da un nuevo valor a la propiedad verde
     *
     * @param nuevoValor
     */
    public void setGreen(int nuevoValor) {
        this.green.setValue(nuevoValor);
    }

    /**
     * Da un nuevo valor a la propiedad azul
     *
     * @param nuevoValor
     */
    public void setBlue(int nuevoValor) {
        this.blue.setValue(nuevoValor);
    }

    /**
     * Da un nuevo valor a la propiedad hexadecimal
     *
     * @param nuevoValor
     */
    public void setHex(String nuevoValor) {
        this.hex.setValue(nuevoValor);
    }

    /**
     * Sobreescribe el método toString para tener uno personalizado
     *
     * @return
     */
    @Override
    public String toString() {
        //Se sobreescribe el método toString() para que escriba en la lista lo que queramos.
        return "RGB: " + red.get() + " " + green.get() + " " + blue.get() + " - HEX: " + hex.get();
    }
}
