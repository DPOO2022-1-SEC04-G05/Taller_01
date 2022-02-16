package Modelo;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Pedido 
{
	private static int numeroPedidos;
	private int idPedido;
	private String nombreCliente;
	private String direccionCliente;
	private ArrayList<Producto> itemsPedido;
	
	public Pedido(String nombreCliente,String direccionCliente) 
	{
		this.nombreCliente=nombreCliente;
		this.direccionCliente=direccionCliente;
		itemsPedido=new ArrayList<Producto>();
		idPedido=getNumeroPedidos();
	}
	
	public void agregarProducto(Producto nuevoItem)
	{
		itemsPedido.add(nuevoItem);
	}
	
	public int getIdPedido()
	{
		return idPedido;
	}
	public static int getNumeroPedidos() {
		return numeroPedidos;
	}

	public static void setNumeroPedidos(int numeroPedidos) {
		Pedido.numeroPedidos = numeroPedidos;
	}

	private  int getPrecioNetoPedido()
	{
		int cantidad=0;
		for(int i=0;i<itemsPedido.size();i++)
		{	
			Producto item=itemsPedido.get(i);
			cantidad+=item.getPrecio();
		}
		
		return cantidad;
	}
	private int getPrecioIVAPedido()
	{
		int cantidad=0;
		for(int i=0;i<itemsPedido.size();i++)
		{	
			Producto item=itemsPedido.get(i);
			cantidad+=item.getPrecioIva();
		}
		
		return cantidad;
	}
	private int getPrecioTotalPedido()
	{
		int cantidad=0;
		for(int i=0;i<itemsPedido.size();i++)
		{	
			Producto item=itemsPedido.get(i);
			cantidad+=item.getPrecioTotal();
		}
		
		return cantidad;
	}
	public String generarTextoFactura()
	{
		String cadena ="";
		
		cadena+="\nNombre Cliente: " + nombreCliente + ". Dirección cliente: " + direccionCliente + ". ID: " + idPedido + ".";
		
		for(int i=0;i<itemsPedido.size();i++)
		{	
			Producto item=itemsPedido.get(i);
			cadena+=item.generarTextoFactura();
		}
		cadena+= "\nPrecio neto: " + getPrecioNetoPedido() + "$. Precio iva: " + getPrecioIVAPedido() + "$. Precio total: " + getPrecioTotalPedido() + "$.";
		
		return cadena;
	}
	public void guardarFactura(File archivo)
	{
		
        try {
        	FileWriter fw = new FileWriter(archivo);
            BufferedWriter bw = new BufferedWriter(fw);
			bw.write(generarTextoFactura());
			bw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
       
	}

	
}
