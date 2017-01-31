package accesoDatos;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Properties;

import logicaRefrescos.Deposito;
import logicaRefrescos.Dispensador;

public class AlmacenadoFicheros implements Datos {

	File fichero = null;
	FileReader fr = null;
	BufferedReader br = null;
	FileWriter archivo = null;
	PrintWriter pw = null;
	Properties propiedades = new Properties();
	InputStream entrada = null;

	
	@Override
	public HashMap<Integer, Deposito> obtenerDepositos() {
		
		HashMap<Integer, Deposito> depositosCreados = new HashMap<Integer, Deposito>();
		Deposito auxDeposito;

		try {
			entrada = new FileInputStream("Ficheros/config/configFichero.properties");
			propiedades.load(entrada);
			fichero = new File(propiedades.getProperty("rutaDep"));
			fr = new FileReader(fichero);
			br = new BufferedReader(fr);
			String lineaActual;

			while ((lineaActual = br.readLine()) != null) {

				String[] partes = lineaActual.split(";");
				auxDeposito = new Deposito(partes[0], Integer.parseInt(partes[1]), Integer.parseInt(partes[2]));
				depositosCreados.put(Integer.parseInt(partes[1]), auxDeposito);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {

				if (null != fr)
					br.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return depositosCreados;

	}
	
	
	

	@Override
	public HashMap<String, Dispensador> obtenerDispensadores() {
		
		HashMap<String,Dispensador> dispensadoresCreado = new HashMap<String,Dispensador>();
		String clave;
		Dispensador dispensador;

		
		try {
			entrada = new FileInputStream("Ficheros/config/configFichero.properties");
			propiedades.load(entrada);
			fichero = new File(propiedades.getProperty("rutaDisp"));
			fr = new FileReader(fichero);
			br = new BufferedReader(fr);
			String lineaActual;

			while
			 ((lineaActual = br.readLine()) != null) {
				
				String[]partes =lineaActual.split(";");
				
				clave = partes[0];
				dispensador = new Dispensador(clave, partes[1], Integer.parseInt(partes[2]), Integer.parseInt(partes[3]));
				dispensadoresCreado.put(clave, dispensador);
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {

				if (null != fr)
					br.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		
		return dispensadoresCreado;
	}

	@Override
	public boolean guardarDepositos(HashMap<Integer, Deposito> depositos) {
		
		boolean guardadoCorrecto = true;
		
			try {
				entrada = new FileInputStream("Ficheros/config/configFichero.properties");
			} catch (FileNotFoundException e1) {
				System.err.println("Fallo al guardar");
				guardadoCorrecto = false;
				e1.printStackTrace();
			}
			try {
				propiedades.load(entrada);
			} catch (IOException e1) {
				System.err.println("Fallo al guardar");
				guardadoCorrecto = false;
				e1.printStackTrace();
			}
		

		try {
			archivo = new FileWriter(propiedades.getProperty("rutaDep"));
			pw = new PrintWriter(archivo);
			
			for (Entry<Integer, Deposito> entry : depositos.entrySet()) {
				archivo.write(entry.getValue().getNombreMoneda()+";"+entry.getKey()+";"+entry.getValue().getCantidad()+"\n");
			}

		} catch (Exception e) {
			System.err.println("Fallo al guardar");
			guardadoCorrecto = false;
			e.printStackTrace();
		} finally {
			try {

				if (null != archivo)
					archivo.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		System.out.println("Se han guardado los depositos correctamente.");
		return guardadoCorrecto;
	}

	@Override
	public boolean guardarDispensadores(HashMap<String, Dispensador> dispensadores) {
		
		boolean guardadoCorrecto = true;
		
		try {
			entrada = new FileInputStream("Ficheros/config/configFichero.properties");
		} catch (FileNotFoundException e1) {
			System.err.println("Fallo al guardar");
			guardadoCorrecto = false;
			e1.printStackTrace();
		}
		try {
			propiedades.load(entrada);
		} catch (IOException e1) {
			System.err.println("Fallo al guardar");
			guardadoCorrecto = false;
			e1.printStackTrace();
		}
	

	try {
		archivo = new FileWriter(propiedades.getProperty("rutaDisp"));
		pw = new PrintWriter(archivo);
		
		for (Entry<String, Dispensador> entry : dispensadores.entrySet()) {
			archivo.write(entry.getKey()+";"+entry.getValue().getNombreProducto()+";"+entry.getValue().getPrecio()+";"+entry.getValue().getCantidad()+"\n");
		}

	} catch (Exception e) {
		e.printStackTrace();
		System.err.println("Fallo al guardar");
		guardadoCorrecto = false;
	} finally {
		try {

			if (null != archivo)
				archivo.close();
		} catch (Exception e2) {
			e2.printStackTrace();
		}
	}
	
	System.out.println("Se han guardado los dispensadores correctamente.");
	return guardadoCorrecto;
	
	}

}
