package es.upv.recipes.controller;

import java.io.File;

import org.controlsfx.control.Rating;

import es.upv.recipes.model.Recipes;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
/**
*
* @author Mario Cordero Sánchez
*/
public class recipeViewerController  {
	//Inicializar las fuentes
	  Font botones = 
	            Font.loadFont(getClass()
	                .getResourceAsStream("/Fonts/JANEAUST.TTF"), 20);
	  
	  Font recetaDatos = 
	            Font.loadFont(getClass()
	                .getResourceAsStream("/Fonts/Hand.TTF"), 14);
	  Font tituloRec = 
	            Font.loadFont(getClass()
	                .getResourceAsStream("/Fonts/CHEDDARJACK.TTF"), 35);
		

	  @FXML
	    private ImageView photoView;

	    @FXML
	    private Label dish;

	    @FXML
	    private HBox rating;

	    @FXML
	    private Label dificult;

	    @FXML
	    private Button ok;

	    @FXML
	    private Label dinners;	    

	    @FXML
	    private Label categoria;
	    @FXML
	    private Label coccion;
	    
	    @FXML
	    private Label preparacionTime;
	    @FXML
	    private Label ingred;
	    @FXML
	    private Label prep;
	    
	    @FXML
	    private TextFlow ingredFlow;
	    @FXML
	    private TextFlow prepaFlow;
	    
	    @FXML
	    private TextFlow observ;
	    
	
	private boolean okValue=false;
    private Stage viewerStage;
    @SuppressWarnings("unused")
	private Recipes recipe;
	private String directoryApp=System.getProperty("user.home")+"/AppData/Local/Recetario/img/";
	private String photo;
	private Rating rating_recipe;

    @FXML
    private void initialize() {
    	//Ponemos las fuentes
    	ok.setFont(botones);
    	dish.setFont(tituloRec);
    	ingred.setFont(tituloRec);
    	ingred.setText("Ingredientes:");
    	prep.setFont(tituloRec);
    	prep.setText("Preparación");
    	dinners.setFont(recetaDatos);
    	dificult.setFont(recetaDatos);
    	preparacionTime.setFont(recetaDatos);
    	coccion.setFont(recetaDatos);
    	categoria.setFont(recetaDatos);

    	rating_recipe = new Rating();
    	rating_recipe.setRating(0.0);
    	rating_recipe.setPartialRating(false);
    	rating_recipe.setUpdateOnHover(true);
	   	rating.getChildren().add(rating_recipe);
    	
    }	
	
	public void setViewerStage(Stage thisStage) {
	        this.viewerStage = thisStage;
	    }
	 public boolean getOkValue() {
	        return okValue;
	    }
	
	public void SettingRecipe(Recipes recipe){
		this.recipe=recipe;
		
		photo=recipe.getFoto();
		File file=new File(directoryApp + photo);
		Image img = new Image(file.toURI().toString());		
		photoView.setImage(img);
		
		dish.setText(recipe.getNombre());
		
		rating_recipe.setRating(recipe.getValoracion());

		dinners.setText(recipe.getComensal().toString()+" personas");
		dificult.setText("Receta de dificultad "+recipe.getDificultad());
		categoria.setText(recipe.getCategPrim() +" > " + recipe.getCategSeg());
		
		coccion.setText("Cocción: "+recipe.getTimeCoccion().toString()+" min.");
		preparacionTime.setText("Preparación: "+recipe.getTimePrep().toString()+" min.");
		
		Text ingredientes= new Text(recipe.getIngred());
		ingredientes.setFont(recetaDatos);
		ingredFlow.getChildren().add(ingredientes);

		
		Text preparacion = new Text(recipe.getPrep());
		preparacion.setFont(recetaDatos);
		prepaFlow.getChildren().add(preparacion);

		Text observaciones=new Text("\nObservaciones:\n"+recipe.getObserv());
		observaciones.setFont(recetaDatos);
		observ.getChildren().add(observaciones);	

		
		}

	@FXML
	private void okButton()  {  
		okValue=true;
		viewerStage.close();
	}
	

}