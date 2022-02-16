package Modelo;

public interface Producto 
{
	public String getNombre();

	public int getPrecio();
	
	public int getPrecioIva();
	
	public int getPrecioTotal();
	
	public String generarTextoFactura();
	
}
