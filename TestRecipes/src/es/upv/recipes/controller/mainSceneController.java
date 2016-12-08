package es.upv.recipes.controller;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.io.File;
import java.io.IOException;
import java.util.prefs.Preferences;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.controlsfx.control.Notifications;
import org.controlsfx.control.Rating;

import es.upv.recipes.model.Recipes;
import es.upv.recipes.model.RecipesWrapper;
import es.upv.recipes.controller.mainSceneController;

/**
 * FXML Controller class
 *
 * @author Mario Cordero Sánchez
 */
public class mainSceneController{
	String css="es/upv/recipes/view/application.css";

	String dialog="genericDialog.fxml";//Ya que si cambias del directorio del controller no lo carga
	String editor="editorSceneController.fxml";
	String view="recipeViewer.fxml";

	String alert="/images/dialog/bullet_info.png";
	String atencion="/images/dialog/bullet_error.png";
	String prohibido="/images/dialog/bullet_delete.png";
	String error="/images/dialog/bullet_deny.png";
	
	String icon="images/logo.ico";
	String title="Recetario de "+ System.getProperty("user.name");


	private double desplazX=0;
	private double desplazY=0;

	private boolean optionOK;

	private String directoryApp=System.getProperty("user.home")+"/AppData/Local/Recetario/";

	@FXML
	private TableView<Recipes> listTable;
	@FXML
	private TableColumn<Recipes,String> categTable;
	@FXML
	private TableColumn<Recipes, String> dishTable;

	@FXML
	private Label dish;
	@FXML
	private Label dinners;
	@FXML
	private Label dificult;
	@FXML
	private Label vista;
	
	
	@FXML
	private HBox rating;
	@FXML
	private ImageView photoView;
	
	
	@FXML
	private Button delete;
	@FXML
	private Button edit;
	@FXML
	private Button newRecipe;
	@FXML
	private Button consultBut;

	private ObservableList<Recipes> recipesData=FXCollections.observableArrayList();

	final Rating rate_recipe = new Rating();
	
	  Font botones = 
	            Font.loadFont(getClass()
	                .getResourceAsStream("/Fonts/JANEAUST.TTF"), 20);
	
	  Font titulo = 
	            Font.loadFont(getClass()
	                .getResourceAsStream("/Fonts/maquinaEscribir.TTF"), 28);
	
	  Font recetas = 
	            Font.loadFont(getClass()
	                .getResourceAsStream("/Fonts/recipesFont.TTF"), 22);

	public mainSceneController(){
		
	}

	public ObservableList<Recipes> getRecipesData() {
		return recipesData;
	}

	/**
	 * Initializes the controller class.
	 * @throws Exception 
	 */
	@FXML
	public void initialize() throws Exception {
		//ExampleRecipes();
		//Cargamos el fichero al iniciar.
		initialLoad();

		dishTable.setCellValueFactory(cellData -> cellData.getValue().nombreProperty());
		categTable.setCellValueFactory(cellData -> cellData.getValue(). primCatProperty());			
	        
		listTable.setItems(getRecipesData());
	
		quickViewDetails(null);

		listTable.getSelectionModel().selectedItemProperty().addListener(
				(observable, oldValue, newValue) -> quickViewDetails(newValue));

		//Mensaje para cuando la tabla esté vacía
		listTable.setPlaceholder(new Label("Por favor, introduzca una receta"));
		

		rate_recipe.getStyleClass().add(css);
		rating.getChildren().add(rate_recipe);
		
		
		//Establecemos estilos de las fuentes
		
		vista.setFont(titulo);
		dish.setFont(recetas);
		dinners.setFont(recetas);
		dificult.setFont(recetas);
		
		newRecipe.setFont(botones);
		edit.setFont(botones);
		delete.setFont(botones);
		consultBut.setFont(botones);			
		
	}
	

	@FXML
	private void salir(ActionEvent event) {  
		String title="ATENCION";
		String description=System.getProperty("user.name")+", ¿Seguro que desea\n salir?";	       
		optionOK=dialog(title,description,alert,event);
		
		File file=new File(directoryApp+"recipes.xml");

		if(optionOK==true)
		{
			saveRecipesDataToFile(file);
			Platform.exit();
		}
	}
	/**
	 * @La escena por defecto es de 462 * 285
	 */
	private boolean dialog(String title,String des,String type, ActionEvent event) {

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

			//  PopUpStage.setWidth(462);
			// PopUpStage.setHeight(285);
			PopUpStage.showAndWait();  

			return controller.getOkValue();


		} catch (Exception ex) {
			System.out.println("Internal error: " + ex.getMessage());
		}
		return false;

	}
//@param vista rápida receta
	private void quickViewDetails(Recipes recipe){
//TODO 
		rate_recipe.setMouseTransparent(true);// Para que no detecte eventos de ratón

		if(recipe !=null){
			//Crea el fichero para enlazar al Imgview
			File file=new File(directoryApp+"img/" + recipe.getFoto());
			Image img = new Image(file.toURI().toString());
			photoView.setImage(img);
			//Fin fichero

			dish.setText(recipe.getNombre());
			dinners.setText(recipe.getComensal()+" Comensales");
			dificult.setText("Dificultad "+recipe.getDificultad());

			rate_recipe.setRating(recipe.getValoracion());	

		} else {

			dish.setText("Sin Receta");
			dinners.setText("");
			dificult.setText("");


			File file=new File(directoryApp + "img/vacio.png");
			Image img = new Image(file.toURI().toString());
			photoView.setImage(img);

			rate_recipe.setRating(0.0);
		}


	}


	@FXML
	private void delBut(ActionEvent event) {
		int selectedIndex = listTable.getSelectionModel().getSelectedIndex();
		
		if(selectedIndex >=0)
		{
			String title="ATENCION";
			String description="¿Desea borrar la receta \nseleccionada?";	       
			optionOK=dialog(title,description,atencion,event);

			if(optionOK==true){				
				listTable.getItems().remove(selectedIndex);
				Notif("Se ha borrado la receta");

			}
		}else{
			String title="No Permitido";
			String description="No seleccionaste receta";	       
			optionOK=dialog(title,description,prohibido,event);			
		}
	}
		

	private void Notif(String text){
		Image i = new Image(icon);
		ImageView graphic = new ImageView();
		graphic.setImage(i);
		graphic.setFitWidth(60);
		graphic.setFitHeight(60);


		Notifications.create()
		.title(title)
		.text(text)
		.graphic(graphic)
		.hideCloseButton()
		.show();
	}

	@FXML
	private void newBut(ActionEvent event) {

		Recipes tempRecip = new Recipes();
		
			optionOK=editorWindow(tempRecip,event,"Creador");
			if (optionOK==true) {
				recipesData.add(tempRecip);
				Notif("Se ha creado la receta");
			}
		}

	
	@FXML
	private void editBut(ActionEvent event) {

		Recipes selectedRecipe = listTable.getSelectionModel().getSelectedItem();
		if (selectedRecipe != null) {
			optionOK=editorWindow(selectedRecipe,event,"Editor");
			if (optionOK==true) {
				quickViewDetails(selectedRecipe);
				Notif("Se ha actualizado la receta");
			}

		} else {
			// Sin recetas Seleccionad
			String title="Error";
			String description="No seleccionó ninguna receta";	       
			dialog(title,description,error,event);
		}
	}

	private boolean editorWindow( Recipes recipe, ActionEvent event, String Label) {

		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(mainSceneController.class.getResource(editor));
			AnchorPane page = (AnchorPane) loader.load();
			editorSceneController controller = loader.getController();
			controller.SettingDetails(recipe); 
			controller.setTypeWindow(Label);
			Stage PopUpStage = new Stage();
			PopUpStage.initStyle(StageStyle.TRANSPARENT);
			PopUpStage.initModality(Modality.WINDOW_MODAL);
			PopUpStage.centerOnScreen();
			PopUpStage.setResizable(false);
			Scene scene = new Scene(page);
			PopUpStage.setScene(scene);
			scene.setFill(null);
			PopUpStage.initOwner( (Stage) ((Node) event.getSource()).getScene().getWindow());
			controller.setEditorStage(PopUpStage);
		

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
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;

	}
	
	
	
	@FXML
	private void consultRecipe(ActionEvent event) {
		
		Recipes selectedRecipe = listTable.getSelectionModel().getSelectedItem();
		//Integer numberRecipe=listTable.getSelectionModel().getSelectedIndex();
		if (selectedRecipe != null) {
			
			optionOK=viewWindow(selectedRecipe,event);
			if (optionOK==true) {
				quickViewDetails(selectedRecipe);
				Notif("Bon Apetit con "+ selectedRecipe.getNombre());
			}

		} else {
			// Sin recetas Seleccionad
			String title="Error";
			String description="No seleccionó ninguna receta";	       
			dialog(title,description,error,event);
		}
	}
	
	
	private boolean viewWindow( Recipes recipe, ActionEvent event) {

		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(mainSceneController.class.getResource(view));
			AnchorPane page = (AnchorPane) loader.load();
			recipeViewerController controller = loader.getController();
			controller.SettingRecipe(recipe); 
			Stage PopUpStage = new Stage();
			PopUpStage.initStyle(StageStyle.TRANSPARENT);
			PopUpStage.initModality(Modality.WINDOW_MODAL);
			PopUpStage.centerOnScreen();
			PopUpStage.setResizable(false);
			Scene scene = new Scene(page);
			PopUpStage.setScene(scene);
			scene.setFill(null);
			PopUpStage.initOwner( (Stage) ((Node) event.getSource()).getScene().getWindow());
			controller.setViewerStage(PopUpStage);
		

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
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;

	}
	

	private void initialLoad() throws Exception {
		File file=new File(directoryApp+"recipes.xml");	
		if (file.exists() && !file.isDirectory())
			loadRecipe(file);
		else{
			ExampleRecipes();
		}
	}

	private void saveRecipesDataToFile(File file) {
		 try {
		 JAXBContext context = JAXBContext
		 .newInstance(RecipesWrapper.class);
		 Marshaller m = context.createMarshaller();
		 m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		 RecipesWrapper wrapper = new RecipesWrapper();
		 wrapper.setRecipes(recipesData);
		 m.marshal(wrapper, file);
		 setRecipeFilePath(file);
		 } catch (Exception e) {
		 Alert alert = new Alert(AlertType.ERROR);
		 alert.setTitle("Error"); alert.setHeaderText(null);
		 alert.setContentText("Could not save data to file:\n" +file.getPath());
		 alert.showAndWait();
		}
		 
	}
	
	public void setRecipeFilePath(File file) {
		 Preferences prefs = Preferences.userNodeForPackage(mainSceneController.class);
		 if (file != null) {
		 prefs.put("filePath", file.getPath());
		 } else {
		 prefs.remove("filePath");
		 }
	}
	
	public File getRecipeFilePath() {
		 Preferences prefs = Preferences.userNodeForPackage(mainSceneController.class);
		 String filePath = prefs.get("filePath", null);
		 if (filePath != null) {
		 return new File(filePath);
		 } else {
		 return null;
		 }
		}
	
	public void loadRecipe(File file) throws JAXBException {
		 JAXBContext context = JAXBContext
		 .newInstance(RecipesWrapper.class);
		 Unmarshaller um = context.createUnmarshaller();
		 RecipesWrapper wrapper = (RecipesWrapper) um.unmarshal(file);
		 recipesData.clear();
		 recipesData.addAll(wrapper.getRecipes());
		 setRecipeFilePath(file);
	}

	/*@param Estas son las recetas iniciales de nuestro recetario
	 * 		 incluidas en el xml que copiamos al iniciar
	 */
	private void ExampleRecipes(){
		recipesData.add(new Recipes("Macarrones", "macarrones-bolonesa.jpg","Principal",
				"Pasta", 6,"Media",60 ,10 ,4,"500 gr. de macarrones\n"
						+ "400 gr. de carne picada\n600 gr. de salsa de tomate\n2 cdas de margarina\n"
						+ "1 pimiento verde\n1 cebolla\n1 cubito de caldo de carne\n2 dientes de ajo\n"
						+ "1 huevo sancochado\n100 gr. de queso rallado","En una olla grande, poner a hervir"
								+ " el agua y echar un cubito de caldo de pollo, sal y dos cucharadas soperas de "
								+ "margarina. Cocinar los macarrones hasta que estén en su punto. Una vez listos y "
								+ "escurrirlos, dejarlos enfriar y colocarlos en una fuente.\nEn otro recipiente, "
								+ "sancochar un huevo durante 8 minutos, pelarlo y picarlo.\nLuego, en una sartén, "
								+ "freír el pimiento, la cebolla y el ajo. Agregar la carne picada, el huevo picado"
								+ " y el tomate natural. Echar la salsa sobre la fuente de los macarrones y esparcir"
								+ " el queso por encima.Llevar al horno hasta que esté gratinado. Servir.","ninguna")
				);

		recipesData.add(new Recipes("Arroz con leche cremoso","karl5044-arroz-con-leche-xl-668x400x80xX.jpg","Postre",
				"Dulce",4,"Fácil",75,60,1,"1 litro de leche\n100 gr. de arroz\n70 gr. de azúcar\n1 limón\n"
						+"1 rama de canela\n1 cucharada de canela en polvo\nhojas de menta (para decorar)",
						"Pon la leche en una cazuela. Añade el arroz, la rama de canela y un poco de cáscara de limón."
								+"Cuécelo a fuego suave durante unos 50 minutos. Remueve a menudo.\n"
								+"Agrega el azúcar y cuécelo durante 10 minutos más. Remueve para que se deshaga el azúcar.\n"
								+"Retira la canela, deja templar y reparte el arroz con leche en 4 recipientes."
								+"Deja templar y espolvorea con un poco de canela en polvo. Decora con unas hojas"
								+"de menta y si quieres con la rama de canela.","Mientras se esté enfriando el arroz es"
										+" conveniente seguir removiendo para que no se quede encima una capa de nata y el arroz"
										+" quede más sabroso.")
				);
		
	}
}
