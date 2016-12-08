package es.upv.recipes.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;
import javafx.stage.Stage;
/**
*
* @author Mario Cordero Sánchez
*/
public class genericDialogController  {
	//Inicializar las fuentes
	  Font botones = 
	            Font.loadFont(getClass()
	                .getResourceAsStream("/Fonts/JANEAUST.TTF"), 20);
	  
	  Font titulo = 
	            Font.loadFont(getClass()
	                .getResourceAsStream("/Fonts/Hand.TTF"), 38);
	
	  Font decripcion = 
	            Font.loadFont(getClass()
	                .getResourceAsStream("/Fonts/CHEDDARJACK.TTF"), 30);
		

	@FXML 
	private Label description;
	@FXML
	private Label title;
	@FXML 
	private ImageView type;
	@FXML
	private Button ok;
	@FXML
	private Button cancel;
	

	
	private boolean okValue=false;
    private Stage dialogStage;

    @FXML
    private void initialize() {
    }	
	
	public void setDialogStage(Stage dialogStage) {
	        this.dialogStage = dialogStage;
	    }
	 public boolean getOkValue() {
	        return okValue;
	    }
	
	public void SettingText(String cabecera,String descrip, String img){
		if(img.endsWith("delete.png") || img.endsWith("deny.png")){
			cancel.setVisible(false);
			ok.setTranslateX(-100);
		}
		
		Image i=new Image(img);
		title.setText(cabecera);
		title.setFont(titulo);//Establece la fuente
		type.setImage(i);
		description.setText(descrip);
		description.setFont(decripcion);
		ok.setFont(botones);
		cancel.setFont(botones);			
		}

	@FXML
	private void okButton()  {  
		okValue=true;
		dialogStage.close();
	}
	
	@FXML
	private void cancelButton()  { 
		dialogStage.close();
	}
}