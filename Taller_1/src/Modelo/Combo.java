package Modelo;

import java.util.ArrayList;

public class Combo implements Producto
{	private double descuento;
	private String nombre;
	private ArrayList<Producto> productos;
	private ArrayList<Ingrediente> agregados;
	private ArrayList<Ingrediente> eliminados;

	public Combo(String nombre, double descuento) 
	{
		this.nombre=nombre;
		this.descuento=descuento;
		productos=new ArrayList<Producto>();
		agregados=new ArrayList<Ingrediente>();
		eliminados=new ArrayList<Ingrediente>();
	}
	
	public void agregarItemACombo(Producto itemCombo)
	{
		productos.add(itemCombo);
		
	}
	public void agregarIngredienteCombo(Ingrediente ingrediente)
	{
		agregados.add(ingrediente);
	}
	public void eliminarIngredienteCombo(Ingrediente ingrediente)
	{
		eliminados.add(ingrediente);
	}
	public String getNombre()
	{
		return nombre;
	}
	public double getDescuento()
	{
		return descuento;
	}
	public int getPrecio()
	{
		int precio=0;
		for(int i=0;i<productos.size();i++)
		{	
			Producto producto=productos.get(i);
			precio+=producto.getPrecio();
		}
		precio=(int)(precio*(1-descuento));
		
		for(int i=0;i<agregados.size();i++)
		{
			Ingrediente ingrediente=agregados.get(i);
			precio+=ingrediente.getCostoAdicional();
		}
		
		return precio;
		
	}
	
	public int getPrecioIva()
	{
		return (int)(getPrecio()*0.19);
	}
	public int getPrecioTotal()
	{
		return getPrecio()+ getPrecioIva();
	}
	
	
	public ArrayList<Producto> getProductos()
	{
		return productos;
	}
	public String generarTextoFactura()
	{
		String cadena= getNombre();
		for(int i=0;i<agregados.size();i++)
		{	Ingrediente ingrediente=agregados.get(i);
			cadena+= " con " + ingrediente.getNombre();
		}
		for(int i=0;i<eliminados.size();i++)
		{
			Ingrediente ingrediente=eliminados.get(i);
			cadena+=" sin " + ingrediente.getNombre();
			
		}
		
		return "\nNombre: " + cadena + ". Precio neto: " + getPrecio() + ". IVA: " + getPrecioIva() +  ". Descuento: " + descuento + ". Precio total: " + getPrecioTotal() + ".";
	}

}
