package es.upv.recipes.controller;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.controlsfx.control.Rating;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import es.upv.recipes.model.Recipes;
import es.upv.recipes.controller.mainSceneController;




/**
*
* @author Mario Cordero Sánchez
*/
public class editorSceneController {
	//Botones
	@FXML
	private Button ok;
	@FXML
	private Button cancel;
	@FXML
	private Button imgBut;
	@FXML
	private Button resetBut;
	
	//Texts
	@FXML
	private TextField idDish;
	@FXML
	private TextField numDinners;
	@FXML
	private TextField tCoccion;
	@FXML
	private TextField tPrep;
	
	
	@FXML
	private TextArea textIngred;
	@FXML
	private TextArea textPrep;
	@FXML
	private TextArea textObserv;
	
	@FXML
	private ImageView photoView;
	
	//Contenedores Modificables
	@FXML
	private HBox ratingBox;
	@FXML
	private ComboBox<String> comboDificul;
	
	//RadioButtons
	
    @FXML
    private ToggleGroup primeraCategoria;
	
	@FXML
    private RadioButton Principal;

    @FXML
    private RadioButton Primero;

    @FXML
    private RadioButton Segundo;

    @FXML
    private RadioButton Postre;
    
    @FXML
    private ToggleGroup segundaCategoria;


    @FXML
    private RadioButton Arroces;

    @FXML
    private RadioButton Pasta;

    @FXML
    private RadioButton Carne;

    @FXML
    private RadioButton Pescado;

    @FXML
    private RadioButton Verduras;

    @FXML
    private RadioButton Dulce;
    
    //Label
    @FXML
    private Label typeWindow;
    @FXML
    private Label tlCoccion;
    @FXML
    private Label dish;
    @FXML
    private Label valoration;
    @FXML
    private Label ingred;
    @FXML
    private Label prep;
    @FXML
    private Label dinners;
    @FXML
    private Label categoria;
    @FXML
    private Label observ;
    @FXML
    private Label tlPrep;


	//Creados para seleccionarlos al resetear la receta
	@FXML
	private RadioButton nulll;
	@FXML
	private RadioButton nullll;


	
	private Stage EditorStage;

	private boolean okValue=false;
	private boolean optionOK;

	private Recipes recipe;
	private String directoryApp=System.getProperty("user.home")+"/AppData/Local/Recetario/img/";


	private double desplazX=0;
	private double desplazY=0;
	String atencion="/images/dialog/bullet_error.png";
	String error="/images/dialog/bullet_delete.png";

	String dialog="genericDialog.fxml";

	
	//Variables locales para recetas
    private String dificultad;
    private String photo;
    private String directory;
    private String primCategoria;    
    private String segCategoria;



    //Librerias
    private Rating rating;
    private double stars;      
    final FileChooser fileChooser = new FileChooser();
    
  //Inicializar las fuentes
	  Font botones = 
	            Font.loadFont(getClass()
	                .getResourceAsStream("/Fonts/JANEAUST.TTF"), 20);
	  
	  Font titulo = 
	            Font.loadFont(getClass()
	                .getResourceAsStream("/Fonts/maquinaEscribir.TTF"), 28);
	  Font edit = 
	            Font.loadFont(getClass()
	                .getResourceAsStream("/Fonts/Hand.TTF"), 20);
	  Font editArea = 
	            Font.loadFont(getClass()
	                .getResourceAsStream("/Fonts/Hand.TTF"), 12);
	
	  Font recetas = 
	            Font.loadFont(getClass()
	                .getResourceAsStream("/Fonts/recipesFont.TTF"), 18);
		
    private String errorCSS="-fx-text-inner-color: red; "
			+"-fx-background-color:Transparent;"
			+"-fx-background-image:url(/images/dialog/bullet_deny.png);"
			+"-fx-background-size: 32 32;"
			+"-fx-background-repeat: no-repeat;"
		  	+"-fx-background-position: right center;";
    String noerrorCSS="-fx-text-inner-color: black; "
			+"-fx-background-color:Transparent;"
			+"-fx-background-image:null;"
			+"-fx-background-size: 32 32;"
			+"-fx-background-repeat: no-repeat;"
		  	+"-fx-background-position: right center;";
    
    
    @FXML
    private void initialize() {
    	

  //Vamos a rellenar los contenedores
  	  //Asignamos valores al ComboBox
	   	comboDificul.getItems().addAll(
	   		    "Baja",
	   		    "Media",
	   		    "Alta"
	   		);
	
	   	
	   	//Establecemos asignar el valor del combo a una variable
	   	comboDificul.setOnAction((event) -> {
	   		dificultad=comboDificul.getSelectionModel().getSelectedItem();
	   	});
	   	/* Con esto vamos a crear el sistema de Valoración inicialmente a 0
    	 * y que se el valor se fije únicamente con hover
    	 */
    	
   	 	 rating = new Rating();
   	 	 rating.setRating(0.0);
	   	 rating.setPartialRating(false);
	   	 rating.setUpdateOnHover(true);
	   	 ratingBox.getChildren().add(rating);
	   	 
	   	 //Añadimos un Listener para ir actualizando el valor de las estrellas
	   	rating.ratingProperty().addListener(new ChangeListener<Number>() {
	   	    public void changed(ObservableValue<? extends Number> observable,
	   	                Number oldValue, Number newValue) {
	   	        stars=(double) newValue; 
	   	    }
	   	});
	   	
		//Evento para los RadioButtons
	   	
	   	
	   	primeraCategoria.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
	        @Override
	        public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
	        	RadioButton chkPrim = (RadioButton)newValue.getToggleGroup().getSelectedToggle(); // Cast object to radio button
	            primCategoria=chkPrim.getText();
	            System.out.println("Primera Categ "+primCategoria);
	        }
	    });		
		
			
		segundaCategoria.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
	        @Override
	        public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
	        	RadioButton chkSeg = (RadioButton)newValue.getToggleGroup().getSelectedToggle(); // Cast object to radio button
	            segCategoria=chkSeg.getText();
	            System.out.println(segCategoria);

	        }
	    });	
		
		//Cargamos las fuentes
		
		typeWindow.setFont(titulo);
		dish.setFont(recetas);
		dinners.setFont(recetas);
		tlCoccion.setFont(recetas);
		valoration.setFont(recetas);
		ingred.setFont(recetas);
		prep.setFont(recetas);
		categoria.setFont(recetas);
		observ.setFont(recetas);
		tlPrep.setFont(recetas);
		
		
		idDish.setFont(edit);
		numDinners.setFont(edit);
		tCoccion.setFont(edit);
		tPrep.setFont(edit);
		
		textIngred.setFont(editArea);
		textPrep.setFont(editArea);
		textObserv.setFont(editArea);

		
		imgBut.setFont(botones);
		ok.setFont(botones);
		cancel.setFont(botones);
		resetBut.setFont(botones);
		
	//Bloqueamos la itroducción de letras en los TextFields numéricos          
		numDinners.addEventFilter(KeyEvent.KEY_TYPED, new EventHandler<KeyEvent>() {
	        @Override  public void handle(KeyEvent inputevent) {
	              if (!inputevent.getCharacter().matches("\\d")) {              
	                           inputevent.consume();
	        }
	            }
	        });
		tPrep.addEventFilter(KeyEvent.KEY_TYPED, new EventHandler<KeyEvent>() {
	        @Override  public void handle(KeyEvent inputevent) {
	              if (!inputevent.getCharacter().matches("\\d")) {              
	                           inputevent.consume();
	        }
	            }
	        });
		
		tCoccion.addEventFilter(KeyEvent.KEY_TYPED, new EventHandler<KeyEvent>() {
	        @Override  public void handle(KeyEvent inputevent) {
	              if (!inputevent.getCharacter().matches("\\d")) {              
	                           inputevent.consume();
	        }
	            }
	        });
    	
    }
    
    public void setTypeWindow(String type) {
    	typeWindow.setText(type + " de Recetas");
	}


	public boolean getOkValue() {
		return okValue;
	}



	public void setEditorStage(Stage popUpStage) {
		// TODO Auto-generated method stub
		this.EditorStage = popUpStage;
	}
	


	public void SettingDetails(Recipes recipe) {
		this.recipe=recipe; //Para que modifique la receta local que enviará a MainScene
		// TODO Auto-generated method stub
		photo=recipe.getFoto();
		File file=new File(directoryApp + photo);
		Image img = new Image(file.toURI().toString());
		
		photoView.setImage(img);
		
		idDish.setText(recipe.getNombre());
		numDinners.setText(recipe.getComensal().toString());
		tCoccion.setText(recipe.getTimeCoccion().toString());
		tPrep.setText(recipe.getTimePrep().toString());
		
		textIngred.setText(recipe.getIngred());
		textPrep.setText(recipe.getPrep());
		textObserv.setText(recipe.getObserv());

		dificultad=recipe.getDificultad();
		rating.setRating(recipe.getValoracion());
		comboDificul.setValue(dificultad);
		
		primCatgRadioButton(recipe.getCategPrim());
		segCatgRadioButton(recipe.getCategSeg());
		
	}
	
	@FXML
	private void okButton(ActionEvent event) throws Exception  { 
		if(datosValidos(event)){
			okValue=true;
			updateRecipe();	

			EditorStage.close();
		}
	}
	
	@FXML
	private void cancelButton()  { 
		EditorStage.close();
	}
	
	@FXML
	private void reset(ActionEvent event)  { 
		String title="ATENCIÓN";
		String desc="¿Seguro que quiere borrar\ntodos los campos?";
		
		optionOK=dialog(title, desc, atencion, event);
		if(optionOK){	
		//Ponemos a null
			resetAction();
		}
	}
	
	
	
	private void resetAction(){
		File file=new File(directoryApp + "vacio.png");
		Image img = new Image(file.toURI().toString());

		idDish.setText("");
		textIngred.setText("");
		numDinners.setText("");
		
		photoView.setImage(img);
		
		rating.setRating(0);
    	
		primCatgRadioButton("nada");
    	segCatgRadioButton("nada");
    	

		tCoccion.setText("");
		tPrep.setText("");

		textPrep.setText("");
		textObserv.setText("");


		comboDificul.setValue(null);
			
	}
	//TODO Actualizar datos receta
	private void updateRecipe(){
		//Código que repetimos ya que si se daba Ok sin hacer cambios reseteaba
		File file=new File(directoryApp + photo);
		Image img = new Image(file.toURI().toString());
		photoView.setImage(img);
		
		dificultad=comboDificul.getValue();
		//comboDificul.setValue(dificultad);
		//Fin de codigo repetido
		
		
		recipe.setValoracion((int)stars);
		recipe.setComensal(Integer.parseInt(numDinners.getText()));
		recipe.setFoto(photo);
		
		recipe.setCategPrim(primCategoria);
		recipe.setCategSeg(segCategoria);
		recipe.setDificultad(dificultad);
		
		recipe.setIngred(textIngred.getText());
		recipe.setNombre(idDish.getText());
		recipe.setObserv(textObserv.getText());
		
		recipe.setPrep(textPrep.getText());
		recipe.setTimeCoccion(Integer.parseInt(tCoccion.getText()));
		recipe.setTimePrep(Integer.parseInt(tPrep.getText()));
		}	
	

	
	
	
	//Por temas de comodida hemos decidido if encadenados, ya que el metodo de hacer
    //casting al String devuelto presentaba problemas de creación
	 private void primCatgRadioButton (String eleccionA){
	    	if (eleccionA.equalsIgnoreCase("Principal"))
	    		primeraCategoria.selectToggle(Principal);
	    	
	    	else if (eleccionA.equals(Primero.getId().toString()))
	    		primeraCategoria.selectToggle(Primero);
	    	
	    	else if (eleccionA.equals(Segundo.getId()))
	    		primeraCategoria.selectToggle(Segundo);
	    	
	    	else if (eleccionA.equals(Postre.getId()))
	    		primeraCategoria.selectToggle(Postre);
	  
	    	else
	    		primeraCategoria.selectToggle(nullll);
	    		
	    }
    
    
    private void segCatgRadioButton (String eleccion){
    	if (eleccion.equalsIgnoreCase("Arroces"))
    		segundaCategoria.selectToggle(Arroces);
    	
    	else if (eleccion.equals(Pasta.getId().toString()))
    		segundaCategoria.selectToggle(Pasta);
    	
    	else if (eleccion.equals(Carne.getId()))
    		segundaCategoria.selectToggle(Carne);
    	
    	else if (eleccion.equals(Pescado.getId()))
    		segundaCategoria.selectToggle(Pescado);
    	else if (eleccion.equals(Verduras.getId()))
    		segundaCategoria.selectToggle(Verduras);
    	
    	else if (eleccion.equals(Dulce.getId()))
    		segundaCategoria.selectToggle(Dulce);
    	else
    		segundaCategoria.selectToggle(nulll);
    		
    }

	//Accion para seleccionar photo
	

    @FXML
    private void selPhoto(ActionEvent event){
    	
    	 configureFileChooser(fileChooser);
         File file = fileChooser.showOpenDialog(null);
         if (file != null) {
             //openFile(file);
        	 //Guardamos en la variable directory el Path del fichero
        	 Image img = new Image(file.toURI().toString());
        	 directory=file.getAbsolutePath();
        	 photo=file.getName();
        	 photoView.setImage(img);
     		copyFile(directory,directoryApp);
        	 
         }
    }
    
    private static void configureFileChooser(
             FileChooser fileChooser) { 
    	//Si dabas varias veces al boton "Buscar" duplicaba extensiones, así lo evitamos
    			fileChooser.getExtensionFilters().clear();
                fileChooser.setTitle("Selecciona Imagen para la Receta");
                fileChooser.setInitialDirectory(
                    new File(System.getProperty("user.home"))
                );     
                fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                    new FileChooser.ExtensionFilter("PNG", "*.png")
                );
        }
    
	//Repetimos la funcion crear dialog
    
    public boolean dialog(String title,String des,String type, ActionEvent event) {

		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(mainSceneController.class.getResource(dialog));
			AnchorPane page = (AnchorPane) loader.load();
			genericDialogController controller = loader.getController();
			controller.SettingText(title, des, type);            
			Stage PopUpStage = new Stage();
			PopUpStage.initStyle(StageStyle.TRANSPARENT);
			PopUpStage.initModality(Modality.WINDOW_MODAL);
			PopUpStage.centerOnScreen();
			PopUpStage.setResizable(false);
			Scene scene = new Scene(page);
			PopUpStage.setScene(scene);
			scene.setFill(null);
			PopUpStage.initOwner( (Stage) ((Node) event.getSource()).getScene().getWindow());
			controller.setDialogStage(PopUpStage);

			/*Para hacerla arrastrable*/

			//Tomamos la posición actual al pulsar con el ratón
			page.setOnMousePressed((evento)	->	{	
				desplazX = evento.getSceneX();
				desplazY = evento.getSceneY();
			});

			//Actualizamos la posición al desplazar
			//mientras está pulsado (arrastramos) (drag)

			page.setOnMouseDragged((evento)	->	{	
				PopUpStage.setX(evento.getScreenX() - desplazX);
				PopUpStage.setY(evento.getScreenY() - desplazY);
			});
			/*Fin código para hacerla arrastrable*/

			PopUpStage.showAndWait();  

			return controller.getOkValue();


		} catch (Exception ex) {
			System.out.println("Internal error: " + ex.getMessage());
		}
		return false;

	}
    
    private void copyFile(String source, String target){
    	
    	File f = new File(directory);// Establecemos directory como Archivo para comprobar que existe y no es un directorio
    								//En principio directory es el PathAbsoluto (directory + fichero + extension)
    	
    	if(f.exists() && !f.isDirectory()){

	    	Path copy_from_1 = Paths.get(directory);
	
	        Path copy_to_1 = Paths.get(directoryApp, copy_from_1
	            .getFileName().toString());
	        try {
	          Files.copy(copy_from_1, copy_to_1,REPLACE_EXISTING);
	        } catch (IOException e) {
	            System.err.println(e);
	        }
    	}
    }
    //TODO Validacion
    private boolean datosValidos(ActionEvent event)throws Exception{  	
    	String logError = "";
    	
    	if(idDish.getText()== null || idDish.getText().length()==0){
    		logError +="Nombre de Plato no válido.\n";
    		idDish.setStyle(errorCSS);
    	}else{
    		idDish.setStyle(noerrorCSS);
    	
    	}
    	if(textIngred.getText()== null || textIngred.getText().length()==0){
    		logError +="El campo ingredientes no puede estar vacío\n";
    		textIngred.setStyle(errorCSS);
    		textIngred.setPromptText("El campo ingredientes no puede estar vacío\n");
    	}else{
    		textIngred.setStyle(noerrorCSS);
    	}
    	if(primCategoria==null || nullll.isSelected()){
    		logError+="Tienes que elegir almenos un tipo de categoria\n";
    	}
    	
    	if(textPrep.getText()== null || textPrep.getText().length()==0){
    		logError +="No has introducido forma de preparar la receta\n";
    		textPrep.setStyle(errorCSS);
    		textPrep.setPromptText("No has introducido forma de preparar la receta\n");
    	}else{
    		textPrep.setStyle(noerrorCSS);
    	}
    	if(textObserv.getText()== null || textObserv.getText().length()==0){
    		textObserv.setText("Ninguna");
    	} //No nos interesa mucho controlar que no se metan observaciones
    	if(tPrep.getLength()<1 ||(Integer.parseInt(tPrep.getText())<1 || Integer.parseInt(tPrep.getText())>=400)){
    		logError +="Tiempo de Preparación no válido\n";
    		tPrep.setStyle(errorCSS);
    	}else{
    		tPrep.setStyle(noerrorCSS);
    	}
    	//No nos interesa el tiempo de cocción ya que puede ser nulo
    	if(tCoccion.getLength()<1){
    		tCoccion.setText("0");
    	} 
    	if(numDinners.getLength()<1 ||(Integer.parseInt(numDinners.getText())<1 || Integer.parseInt(numDinners.getText())>=20 )){
    		logError +="No puede haber 0 comensales\nO más de 20\n";
    		numDinners.setStyle(errorCSS);
    	}else{
    		numDinners.setStyle(noerrorCSS);
    	}
    	
    	if(stars == 0.0){
    		logError +="Falta la valoración\n";
    	}
    	
    	if(comboDificul.getValue()==null){
    		logError +="No selecccionaste dificultad\n";
    	}
    	
    	//Retorno en función de si existe error
        if (logError.length() == 0) {
            return true;
        } else {
        	System.err.println(logError);
        	//Creamos la ventana de error si existe
        	String title="Error";
    		String desc="Existen errores\n"
    				+ "revise los campos marcados";
        	
        	dialog(title, desc, error, event);
    		
        	return false;
        }

    	
    }
}
