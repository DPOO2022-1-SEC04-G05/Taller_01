package Modelo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Restaurante {
		
		private ArrayList<Ingrediente> ingredientes;
		private ArrayList<Producto> menuBase;
		private ArrayList<Combo> combos;
		private ArrayList<Pedido> pedidos;
		private Pedido pedidoEnCurso;
		
		public Restaurante() 
		{	
			ingredientes=new ArrayList<Ingrediente>();
			menuBase=new ArrayList<Producto>();
			combos = new ArrayList<Combo>();
			pedidos=new ArrayList<Pedido>();
		}
		
		public void iniciarPedido(String nombreCliente, String direccionCliente) 
		{
			pedidoEnCurso=new Pedido(nombreCliente,direccionCliente);
			System.out.println("\nSus datos se han guardado correctamente.");
		}
		
		public void cerraryGuardarPedido()
		{	
			String ruta="./facturas/Factura #" + pedidoEnCurso.getIdPedido() + ".txt";
			
			pedidos.add(pedidoEnCurso);
			
			File factura =new File(ruta);
			
			pedidoEnCurso.guardarFactura(factura);
			
			pedidoEnCurso=null;
			
			Pedido.setNumeroPedidos(Pedido.getNumeroPedidos() + 1);
			
			System.out.println("\nSu pedido ha sido guardado correctamente.");	
		}
		
		public String  imprimirFactura(int index)
		{
			if(index<pedidos.size())
			{
				Pedido pedido=pedidos.get(index);
				return pedido.generarTextoFactura();
			}
			else
			{
				return null;
			}
		}
		public void agregarProductoMenu(ProductoMenu producto ,ArrayList<Integer> agregados, ArrayList<Integer>eliminados)
		{ 
			if (agregados.size()!=0 || eliminados.size()!=0)
			{
				ProductoAjustado productoAjustado=new ProductoAjustado(producto);
				
				for(int i=0;i<agregados.size();i++)
				{	
					int index=agregados.get(i);
					Ingrediente ingrediente=ingredientes.get(index);
					productoAjustado.agregarIngrediente(ingrediente);
				}
				
				for(int i=0;i<eliminados.size();i++)
				{
					int index=agregados.get(i);
					Ingrediente ingrediente =ingredientes.get(index);
					productoAjustado.quitarIngrediente(ingrediente);
				}
				pedidoEnCurso.agregarProducto(productoAjustado);
				
			}
			
			else
			{
				pedidoEnCurso.agregarProducto( producto);
			}
				
			System.out.println("\nSu pedido fue modificado correctamente.");
		}
		
		public void agregarProductoCombo(Combo combo,ArrayList<Integer> agregados, ArrayList<Integer>eliminados)
		{	
			if (agregados.size()!=0 || eliminados.size()!=0)
			{
				
				for(int i=0;i<agregados.size();i++)
				{	
					int index=agregados.get(i);
					Ingrediente ingrediente=ingredientes.get(index);
					combo.agregarIngredienteCombo(ingrediente);
				}
				
				for(int i=0;i<eliminados.size();i++)
				{
					int index=eliminados.get(i);
					Ingrediente ingrediente =ingredientes.get(index);
					combo.eliminarIngredienteCombo(ingrediente);
					
				}
				pedidoEnCurso.agregarProducto(combo);
			}
			else
			{
				pedidoEnCurso.agregarProducto(combo);
			}
			System.out.println("\nSu pedido fue modificado correctamente.");
		}
		public Pedido getPedidoenCurso()
		{
			return pedidoEnCurso;
		}
		public ArrayList<Combo> getCombos()
		{
			return combos;
		}
		public ArrayList<Producto> getMenuBase()
		{
			return menuBase;
		}
		
		public ArrayList<Ingrediente> getIngredientes()
		{
			return ingredientes;
		}
		
		public void cargarInformacionRestaurante(File archivoIngredientes,File archivoMenu, File archivoCombos) throws FileNotFoundException,IOException
		{	
			cargarIngredientes(archivoIngredientes);
			cargarMenu(archivoMenu);
			cargarCombos(archivoCombos);
		}
		
		public Producto buscarProducto(String nombre)
		{	
			boolean condicion= true;
			int i=0;
			while (condicion && i<menuBase.size() )
			{	
				Producto temp=menuBase.get(i);
				if (temp.getNombre().equals(nombre))
				{
					return temp;
					
				}
				i+=1;
				
				
			}
			return null;
		}
		private void cargarIngredientes(File archivoIngredientes)
		{	
			try {
				BufferedReader br = new BufferedReader(new FileReader(archivoIngredientes));
				String linea;
				linea = br.readLine();
				while (linea!= null)
				{	
					String[] partes=linea.split(";");
					
					int valor=Integer.valueOf(partes[1]);
					
					ingredientes.add(new Ingrediente(partes[0],valor));
					linea=br.readLine();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		}
		private void cargarMenu(File archivoMenu) 
		{	
			
			
			try {
				BufferedReader br = new BufferedReader(new FileReader(archivoMenu));
				String linea;
				linea = br.readLine();
			
			while (linea!= null)
			{	
				
				String[] partes=linea.split(";");
				int valor=Integer.valueOf(partes[1]);
				menuBase.add(new ProductoMenu(partes[0],valor));
				linea=br.readLine();
			}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		private void cargarCombos(File archivoCombos)
		{
			
			try {
				BufferedReader br = new BufferedReader(new FileReader(archivoCombos));
				String linea;
				linea = br.readLine();
		 
			while (linea!= null)
			{	
				
				String[] partes=linea.split(";");
				double valor=Integer.valueOf(partes[1].substring(0, partes[1].length()-1));
				
				
				
				Combo combo=new Combo(partes[0],valor/100);
				
				for(int i=2;i<partes.length;i++)
				{	
					String nombre=partes[i];
					combo.agregarItemACombo(buscarProducto(nombre));
				}
				
				
				combos.add(combo);
				linea=br.readLine();
			}
			}
				catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
	
		

