/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.upv.recipes;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


/**
 *
 * @author Mario Cordero Sánchez
 */
public class SplashScreenController implements Initializable {
    String css="es/upv/recipes/view/application.css";
    String FXMLMain="view/mainScene.fxml";
    
      
    @FXML
    private void handleButtonAction(ActionEvent event) throws IOException {
    	Parent home_page_parent = FXMLLoader.load(getClass().getResource(FXMLMain));
        Scene home_page_scene = new Scene(home_page_parent);
        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
           
        app_stage.hide(); //para eliminar el SplashScreen
        home_page_scene.getStylesheets().add(css);
        home_page_parent.setId("fondo");
        app_stage.setScene(home_page_scene);
        home_page_scene.setFill(null);
        app_stage.show();  

    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
