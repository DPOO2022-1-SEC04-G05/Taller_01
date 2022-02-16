package Modelo;

import java.util.ArrayList;

public class ProductoAjustado implements Producto
{
	private ProductoMenu base;
	private ArrayList<Ingrediente> agregados=new ArrayList<Ingrediente>();
	private ArrayList<Ingrediente> eliminados = new ArrayList<Ingrediente>();
	public ProductoAjustado(ProductoMenu base) 
	{
		this.base=base;
	}
	
	public String getNombre()
	{
		return base.getNombre();
	}
	
	public int getPrecio()
	{
		int basePrecio=base.getPrecio();
		for(int i=0;i<agregados.size();i++)
		{	
			Ingrediente ingrediente=agregados.get(i);
			basePrecio+= ingrediente.getCostoAdicional();
		}
	
		return basePrecio;
	}
	public int getPrecioIva()
	{
		return (int)(getPrecio()*0.19);
	}
	
	public int getPrecioTotal()
	{
		return getPrecioIva()+ getPrecio(); 	
	}
	public void agregarIngrediente(Ingrediente ingrediente)
	{
		agregados.add(ingrediente);
	}
	
	public void quitarIngrediente(Ingrediente ingrediente)
	{
		eliminados.add(ingrediente);
	}
	public String generarTextoFactura()
	{
		String cadena=getNombre();
		for(int i=0;i<agregados.size();i++)
		{	
			Ingrediente ingrediente=agregados.get(i);
			cadena+=" con " + ingrediente.getNombre();
		}
		for(int i=0;i<eliminados.size();i++)
		{
			Ingrediente ingrediente=eliminados.get(i);
			cadena+=" sin " + ingrediente.getNombre();
		}
		
		return "\nNombre: " + cadena + ". Precio neto: " + getPrecio() + "$. IVA: " + getPrecioIva() + "$. Precio total: " + getPrecioTotal() +"$.";
	}

	

}
