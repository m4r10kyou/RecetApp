package es.upv.recipes.model;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement (name="CookBook")
public class RecipesWrapper {
	//volcamos nuesta Lista de recetas para luego cargar o guardar recetas
	private List<Recipes> recipes;
	
	@XmlElement(name="Recipe")
	public List<Recipes> getRecipes() {
		return recipes;
	}
	
	public void setRecipes(List<Recipes>recipes){
		this.recipes=recipes;
	}
	
	

}
