/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.upv.recipes;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author Mario Cordero Sánchez
 */
public class MainApp extends Application {
	
	String icon="images/logo.ico";
	String title="Recetario de "+ System.getProperty("user.name");
	private String directoryApp=System.getProperty("user.home")+"\\AppData"+"\\Local"+"\\Recetario\\";
	
	@Override
    public void start(Stage stage) throws Exception {
		createDirApp();
        Parent root = FXMLLoader.load(getClass().getResource("SplashScreen.fxml"));
        stage.initStyle(StageStyle.TRANSPARENT);
        
        Scene scene = new Scene(root);
        stage.getIcons().add(new Image(icon));
        stage.setScene(scene);
        stage.setTitle(title);
        
        scene.setFill(null);
 
        stage.show();
      
    }
	/*Creamos este método así, ya que al no encontrar el directorio,
	 * es casi seguro, que no exista ningun fichero
	 */
	private void createDirApp() throws Exception{
		File dir=new File(directoryApp);
		File dirImages=new File(directoryApp+"/img/"); 
	   	if(dir.exists()) {
	   		System.out.println("ya exite el directorio!!");
	   		}
	   		else{
	   		dir.mkdir();
	   		dirImages.mkdir();
	   		//Los ficheros los carga del jar para arrancarlos del HDD en ejecución
	   		copy("es/upv/recipes/model/recipes.xml",directoryApp+"recipes.xml");
	   		copy("images/recipes/vacio.png",
	   				directoryApp+"img/vacio.png");
	   		copy("images/recipes/macarrones-bolonesa.jpg",
	   				directoryApp+"img/macarrones-bolonesa.jpg");
	   		copy("images/recipes/karl5044-arroz-con-leche-xl-668x400x80xX.jpg",
	   				directoryApp+"img/karl5044-arroz-con-leche-xl-668x400x80xX.jpg");
	   		}
	}
	//Copiamos los archivos necesarios al HDD desde el jar
	private static void copy(String resource, String destination) {
        InputStream resStreamIn = MainApp.class.getClassLoader().getResourceAsStream(resource);
        File resDestFile = new File(destination);
        try {
            OutputStream resStreamOut = new FileOutputStream(resDestFile);
            int readBytes;
            byte[] buffer = new byte[4096];
            while ((readBytes = resStreamIn.read(buffer)) != -1){ 
 				resStreamOut.write (buffer, 0, readBytes); 
	                }
            resStreamOut.close();
        	resStreamIn.close();

        } catch (Exception ex) {
        	System.out.println(ex);
        }

    }
	
	
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }  
    
}

