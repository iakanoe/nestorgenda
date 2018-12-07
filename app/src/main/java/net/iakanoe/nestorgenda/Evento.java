package net.iakanoe.nestorgenda;

public class Evento {
	private String fecha;
	private String nombre;
	private String descripcion;
	
	Evento(String nombre, String fecha, String descripcion){
		this.nombre = nombre;
		this.fecha = fecha;
		this.descripcion = descripcion;
	}
	
	public String getFecha(){
		return fecha;
	}
	
	public void setFecha(String fecha){
		this.fecha = fecha;
	}
	
	public String getNombre(){
		return nombre;
	}
	
	public void setNombre(String nombre){
		this.nombre = nombre;
	}
	
	public String getDescripcion(){
		return descripcion;
	}
	
	public void setDescripcion(String descripcion){
		this.descripcion = descripcion;
	}
}
