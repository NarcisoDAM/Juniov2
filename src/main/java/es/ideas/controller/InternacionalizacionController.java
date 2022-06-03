package es.ideas.controller;

import es.ideas.MultiLenguajeUI;
import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;

public class InternacionalizacionController implements Initializable {

    @FXML
    private ChoiceBox<String> cbSemana;
    @FXML
    private ToggleGroup languageGroup;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        //Inicialización del ComboBox con los días de la semana
        //También tiene que traducirse
        //La traduccion la hemos generado a traves del metodo getString de un Resource Bundle
        //Simplemente cogemos la cadena contenida en el array y realizamos la traduccion mediante codigo
        
        String dias_semana[] = {rb.getString("lunes"), rb.getString("martes"), rb.getString("miercoles"), rb.getString("jueves"),
            rb.getString("viernes"), rb.getString("sabado"), rb.getString("domingo")};
        cbSemana.setItems(FXCollections.observableArrayList(dias_semana));

        languageGroup.selectedToggleProperty().addListener((obs,oldValue,newValue)->{
            if (newValue != null ){
               ToggleButton tb = (ToggleButton) newValue.getToggleGroup()
                       .getSelectedToggle();               
               //comprobamos el valor del texto y definimos segun la seleccion mediante un switch      
               switch (tb.getText()){
                   case "Inglés":
                       Locale.setDefault(Locale.ENGLISH);
                       System.out.println("Has seleccionado idioma INGLÉS");
                       
                       break;  
                   case "Francés":
                       Locale.setDefault(Locale.FRENCH);
                       System.out.println("Has seleccionado idioma FRANCÉS");
                       break;
                   case "EEUU":
                       Locale.setDefault(Locale.US);
                       System.out.println("Has seleccionado Inglés de EEUU");
                       break;
                   default:
                       Locale.setDefault(new Locale("es"));
                       System.out.println("Has seleccionado idioma ESPAÑOL");                       
               }
               try{
                //Dentro de este bloque try-catch "recargamos" nuestra escena.
                Parent pane = getFXMLLoader().load();
                MultiLenguajeUI.getPrimaryStage().getScene().setRoot(pane);
               }catch(IOException ieo){                   
               }               
               MultiLenguajeUI.getPrimaryStage().show();
            }
               
        });
        
    }
    private FXMLLoader getFXMLLoader(){
        FXMLLoader loader = new FXMLLoader();
        //Cargamos las traducciones.
        loader.setResources(ResourceBundle.getBundle("es.ideas.i18n/traduccion",
                Locale.getDefault()));
        loader.setLocation(getClass().getResource("primary.fxml"));
        return loader;
    }
    
}
