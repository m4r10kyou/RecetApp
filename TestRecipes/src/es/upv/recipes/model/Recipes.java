package es.upv.recipes.model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.property.IntegerProperty;


public class Recipes {
	
	//Establecemos todos los parametros de nuestro modelo
	private SimpleStringProperty nombre = new SimpleStringProperty();
	private SimpleStringProperty foto = new SimpleStringProperty();
	private SimpleStringProperty categoriaPrimera = new SimpleStringProperty();
	private SimpleStringProperty categoriaSegunda = new SimpleStringProperty();
	private IntegerProperty comensales = new SimpleIntegerProperty();
	private SimpleStringProperty dificultad = new SimpleStringProperty();
	private IntegerProperty TimePrep = new SimpleIntegerProperty();
	private IntegerProperty TimeCoccion = new SimpleIntegerProperty();
	private IntegerProperty valoracion = new SimpleIntegerProperty();
	private StringProperty Ingred = new SimpleStringProperty();
	private StringProperty Prep = new SimpleStringProperty();
	private StringProperty Observ = new SimpleStringProperty();
	
	//@param Constructor vacío
	public Recipes(){
		
		this(null, "vacio.png", "nada", "nada", 0, null, 0, 0, 0, null, null, null);
	}
	
	//@param Constructor del Modelo
	
	public Recipes(String nombre,String foto,String categoriaPrimera,String categoriaSegunda,
			Integer comensales,String dificultad,Integer TimePrep,
			Integer TimeCoccion,Integer valoracion,String Ingred,String Prep,String Observ) {
		
		this.nombre 			= new SimpleStringProperty(nombre);
		this.categoriaPrimera 	= new SimpleStringProperty(categoriaPrimera);
		this.categoriaSegunda  	= new SimpleStringProperty(categoriaSegunda);
		this.foto	 			= new SimpleStringProperty(foto);
		this.dificultad 		= new SimpleStringProperty(dificultad);
		this.Ingred 			= new SimpleStringProperty(Ingred);
		this.Prep	 			= new SimpleStringProperty(Prep);
		this.Observ	 			= new SimpleStringProperty(Observ);
		this.comensales			= new SimpleIntegerProperty(comensales);
		this.TimePrep			= new SimpleIntegerProperty(TimePrep);
		this.TimeCoccion		= new SimpleIntegerProperty(TimeCoccion);
		this.valoracion			= new SimpleIntegerProperty(valoracion);		
		
	}
	
	
	//Setters para la edición de las recetas	
	
	public void setNombre(String nombre)
	{
		this.nombre.set(nombre);
	}
	
	public void setCategPrim(String categoriaPrim)
	{
		this.categoriaPrimera.set(categoriaPrim);
	}
	
	public void setCategSeg(String categoriaSeg)
	{
		this.categoriaSegunda.set(categoriaSeg);
	}
	
	public void setFoto(String foto)
	{
		this.foto.set(foto);
	}
	
	public void setDificultad(String dificultad)
	{
		this.dificultad.set(dificultad);
	}
	
	public void setIngred(String Ingred)
	{
		this.Ingred.set(Ingred);
	}
	
	public void setPrep(String Prep)
	{
		this.Prep.set(Prep);
	}
	
	public void setObserv(String Observ)
	{
		this.Observ.set(Observ);
	}
	
	public void setTimePrep(Integer TimePrep)
	{
		this.TimePrep.set(TimePrep);
	}
	
	public void setTimeCoccion(Integer TimeCoccion)
	{
		this.TimeCoccion.set(TimeCoccion);
	}
	
	public void setValoracion(Integer valoracion)
	{
		this.valoracion.set(valoracion);
	}
	
	public void setComensal(Integer comensales)
	{
		this.comensales.set(comensales);
	}

	
	//Getters	
	
	public String getNombre()
	{
		return this.nombre.get();
	}
	
	public String getCategPrim() {
		return this.categoriaPrimera.get();
	}
	
	public String getCategSeg()
	{
		return this.categoriaSegunda.get();
	}
	
	public String getFoto()
	{
		return this.foto.get();
	}
	
	public String getDificultad()
	{
		return this.dificultad.get();
	}
	
	public String getIngred()
	{
		return this.Ingred.get();
	}
	
	public String getPrep()
	{
		return this.Prep.get();
	}
	
	public String getObserv()
	{
		return this.Observ.get();
	}
	
	public Integer getTimePrep()
	{
		return this.TimePrep.get();
	}
	
	public Integer getTimeCoccion()
	{
		return this.TimeCoccion.get();
	}
	
	public Integer getValoracion()
	{
		return this.valoracion.get();
	}
	
	public Integer getComensal()
	{
		return this.comensales.get();
	}
	
	// Observables
	
	public StringProperty nombreProperty() {
		return nombre;
	}
	
	public StringProperty primCatProperty() {
		return categoriaPrimera;
	}


}
