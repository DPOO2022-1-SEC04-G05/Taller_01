package Consola;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Scanner;

import Modelo.Combo;
import Modelo.Ingrediente;
import Modelo.Producto;
import Modelo.ProductoMenu;
import Modelo.Restaurante;

public class Aplicacion {
	Restaurante restaurante;

	public static void main(String[] args)  {
		Aplicacion aplicacion=new Aplicacion();
		
	    
		System.out.println("¡Bienvenido!\n");
		aplicacion.cargarInformacion();
		aplicacion.mostrarMenu();
		

	}
	
	
	public void mostrarMenu()  
	{	
		System.out.println("");
		System.out.println("1.) Iniciar nuevo pedido.");
		System.out.println("2.) Agregar elemento a pedido.");
		System.out.println("3.) Cerrar pedido y guardar factura.");
		System.out.println("4.) Consultar informacion segun id.");
		System.out.println("5.) Salir.");
		int numero=inputNumero("\nSeleccione una opción");
		
		
		
		if(numero<=5)
		{
			ejecutarOpcion(numero);
		}
		else
		{
			System.out.println("Opción inválida. Por favor ingrese una nueva opción.");
			mostrarMenu();
		}
		
	}
	public void ejecutarOpcion(int opcionDeseada)  
	{
		if(opcionDeseada==1)
		{	
			String nombreCliente=input("\nIngrese su nombre");
			String direccionCliente=input("\nIngrese su dirección");
			
			restaurante.iniciarPedido(nombreCliente, direccionCliente);
			
		}
		else if(opcionDeseada==2)
		{
			
			System.out.println("\n1.) Combo.");
			System.out.println("2.) Producto Menu.");
			
			
			int numero=inputNumero("\nSeleccione opción deseada");
			
			
			if(numero<=2)
			{	
				agregarItem(numero);
			}
			else
			{
				System.out.println("Opción inválida. Por favor ingrese una nueva opción.");
				ejecutarOpcion(2);
			}
			
			
		}
		else if(opcionDeseada==3)
		{
			restaurante.cerraryGuardarPedido();
		}
		else if(opcionDeseada==4)
		{
			int id=inputNumero("\nSeleccione algun id para revisar informacion de la factura");
			
			String cadena=restaurante.imprimirFactura(id);
			if (cadena!=null)
			{
				System.out.println(cadena);
			}
			else
			{
				System.out.println("ID no encontrado.");
			}
		}
		else if(opcionDeseada==5)
		{
			System.exit(0);
		}
		else
		{
			System.out.println("Opción no disponible.");
		}
		mostrarMenu();
	}
	
public void agregarItem(int numero)
	{
		if(numero==1)
		{
			ejecutarCombos();
		}
		else if(numero==2)
		{
			ejecutarProductos();
		}
	
	}
public void ejecutarCombos()
{	ArrayList<Integer> eliminados=new ArrayList<Integer>();
	ArrayList<Integer> agregados=new ArrayList<Integer>();
	ArrayList<Combo> combos = restaurante.getCombos();
	System.out.println("");
	for (int i=0;i<combos.size();i++)
	{	Combo combo=combos.get(i);
		System.out.println( (i+1) + ".) Nombre: "+ combo.getNombre()+ ". Descuento: " + combo.getDescuento() + ".");	
	}
	int numero=inputNumero("\nSeleccione opción deseada");
	
	if (numero-1<combos.size())
	{
		
		Combo combo=combos.get(numero-1);
		ArrayList<Producto> productos= combo.getProductos();
		
		for(int i=0;i<productos.size();i++)
		{	
			System.out.println("\nCompuesto por: ");
			Producto producto=productos.get(i);
			System.out.println((i+1) + "  " + producto.getNombre() + ": " + producto.getPrecio() + "$.");
				
		}
		
		System.out.println("\n¿Desea añadir o eliminar algún ingrediente al combo?");
		System.out.println("1.) Si.");
		System.out.println("2.) No.");
		int opcion=inputNumero("\nSeleccione opción deseada");
		
		
		if (opcion==1) 
		{
			ejecutarControlIngredientesCombo(combo,agregados,eliminados);
		}			
		else if(opcion==2)
		{
			restaurante.agregarProductoCombo(combo,agregados,eliminados);
		}
	
	}
	else
	{
		System.out.println("Ejecute una opción válida.");
		ejecutarCombos();
	}
	
	
}
public void ejecutarControlIngredientesCombo(Combo combo,ArrayList<Integer> agregados,ArrayList<Integer> eliminados)
{ 
	System.out.println("\n1.) Agregar ingrediente.");
	System.out.println("2.) Eliminar ingrediente.");
	System.out.println("3.) No agregar ni eliminar más ingredientes.");
	
	int numero=inputNumero("\nSeleccione opción deseada");
	
	System.out.println("");
	mostrarIngredientes();
	
	
	
	if(numero==1)
	{	numero=inputNumero("\nSeleccione el ingrediente");
		agregados.add(numero-1);
		ejecutarControlIngredientesCombo(combo,agregados,eliminados);
	}
	else if(numero==2)
	{
		numero=inputNumero("\nSeleccione el ingrediente");
		eliminados.add(numero-1);
		ejecutarControlIngredientesCombo(combo,agregados,eliminados);
	}
	else
	{
		restaurante.agregarProductoCombo(combo, agregados, eliminados);
	}
}
public void ejecutarProductos()
{	
	ArrayList<Integer> eliminados=new ArrayList<Integer>();
	ArrayList<Integer> agregados=new ArrayList<Integer>();
	ArrayList<Producto> menuBase = restaurante.getMenuBase();
	System.out.println("");
	
	for (int i=0;i<menuBase.size();i++)
	{
		Producto producto=menuBase.get(i);
		System.out.println((i+1) + ".) " + producto.generarTextoFactura());
	}
	int opcion=inputNumero("\nIngrese el producto deseado");
	
	if (opcion-1<menuBase.size())
		{
			ProductoMenu producto= (ProductoMenu) menuBase.get(opcion-1);
			
			System.out.println("\n1.) Agregar o eliminar ingrediente.");
			System.out.println("2.) No agregar ingredientes.");
			opcion=inputNumero("\nSeleccione opción deseada.");
			
			if (opcion==1) 
			{
				ejecutarControlIngredientesMenu(producto,agregados,eliminados);
			}			
			else if(opcion==2)
			{
				restaurante.agregarProductoMenu(producto,agregados,eliminados);
			}
		}
	else
	{
	System.out.println("Producto no encontrado.");
	}
}

public void ejecutarControlIngredientesMenu(ProductoMenu producto,ArrayList<Integer> agregados,ArrayList<Integer> eliminados)
{
	System.out.println("\n1.) Agregar ingrediente.");
	System.out.println("2.) Eliminar ingrediente.");
	System.out.println("3.) No agregar ni eliminar ingredientes.");
	
	int numero=inputNumero("Seleccione alguna de las anterior opciones");
	
	mostrarIngredientes();
	
	
	
	if(numero==1)
	{	numero=inputNumero("Seleccione el ingrediente");
		agregados.add(numero-1);
		ejecutarControlIngredientesMenu(producto,agregados,eliminados);
	}
	else if(numero==2)
	{
		numero=inputNumero("Seleccione el ingrediente");
		eliminados.add(numero-1);
		ejecutarControlIngredientesMenu(producto,agregados,eliminados);
	}
	else
	{
		restaurante.agregarProductoMenu(producto, agregados, eliminados);
	}
}
public void mostrarIngredientes()
{	ArrayList<Ingrediente> ingredientes= new ArrayList<Ingrediente>();
	ingredientes=restaurante.getIngredientes();
	for (int i=0;i<ingredientes.size();i++)
	{	
		Ingrediente ingrediente=ingredientes.get(i);
		System.out.println((i+1) + ".) Nombre: "+ ingrediente.getNombre()+ ". Precio: " + ingrediente.getCostoAdicional() + "$."); 
	}
}
public void cargarInformacion() 
{	
	System.out.println("Cargando datos...");
	restaurante=new Restaurante();
	File archivoIngredientes= new File("./Data/ingredientes.txt");
	File archivoMenu=new File("./Data/menu.txt");
	File archivoCombos=new File("./Data/combos.txt");
	
	try {
		restaurante.cargarInformacionRestaurante(archivoIngredientes, archivoMenu, archivoCombos);
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	System.out.println("Datos cargados correctamente.");
}
	
	public int inputNumero(String mensaje)  // Esta función recibe como parámetro un string
										 // y lo imprime en pantalla. Almacena la cadena escrita por el usuario.
	{
		System.out.print(mensaje + ": ");
		Scanner reader = new Scanner(System.in);
		return reader.nextInt();
	}
	
	public String input(String mensaje)
	{
		try
		{
			System.out.print(mensaje + ": ");
			BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
			return reader.readLine();
		}
		catch (IOException e)
		{
			System.out.println("Error leyendo de la consola.");
			e.printStackTrace();
		}
		return null;
	}
}
