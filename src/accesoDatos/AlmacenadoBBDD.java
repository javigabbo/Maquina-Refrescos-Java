package accesoDatos;

import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Properties;
import java.util.Scanner;

import logicaRefrescos.Deposito;
import logicaRefrescos.Dispensador;

public class AlmacenadoBBDD implements Datos {

	private Connection conexion;
	private Statement st;
	private ResultSet rs;
	Scanner scan = new Scanner(System.in);
	Properties propiedades = new Properties();
	InputStream entrada = null;

	public AlmacenadoBBDD() {

		try {

			Class.forName("com.mysql.jdbc.Driver").newInstance();
			entrada = new FileInputStream("Ficheros/config/configBBDD.properties");
			propiedades.load(entrada);
			conexion = DriverManager.getConnection(propiedades.getProperty("url"), propiedades.getProperty("login"),
					"");
			System.out.println("Conectado con exito.");
			entrada.close();
		} catch (Exception ex) {
			System.out.println("Error: " + ex);
		}

	}

	@Override
	public HashMap<Integer, Deposito> obtenerDepositos() {

		HashMap<Integer, Deposito> depositosCreados = new HashMap<Integer, Deposito>();
		Deposito auxDeposito;

		try {
			String query = "SELECT * FROM maquinaRefrescos.depositos;";
			st = conexion.createStatement();
			rs = st.executeQuery(query);

			while (rs.next()) {	
				auxDeposito = new Deposito(rs.getString("nombreMoneda"), Integer.parseInt(rs.getString("valor")), Integer.parseInt(rs.getString("cantidad")));
				depositosCreados.put(Integer.parseInt(rs.getString("valor")), auxDeposito);
			}

			rs.close();
			st.close();
		} catch (SQLException s) {
			s.printStackTrace();
		}
		return depositosCreados;
	}

	
	
	@Override
	public HashMap<String, Dispensador> obtenerDispensadores() {
		HashMap<String,Dispensador> dispensadoresCreado = new HashMap<String,Dispensador>();
		String clave;
		Dispensador dispensador;
		
		try {
			String query = "SELECT * FROM maquinaRefrescos.dispensadores;";
			st = conexion.createStatement();
			rs = st.executeQuery(query);
			
			while (rs.next()) {	
				clave = rs.getString("clave");
				dispensador = new Dispensador(clave, rs.getString("nombre"), Integer.parseInt(rs.getString("precio")), Integer.parseInt(rs.getString("cantidad")));
				dispensadoresCreado.put(clave, dispensador);
			}
			rs.close();
			st.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		return dispensadoresCreado;
	}

	
	
	
	
	@Override
	public boolean guardarDepositos(HashMap<Integer, Deposito> depositos) {
		boolean guardadoCorrecto = true;
		int valor, cantidad;
		String nombreMoneda;
		
		try {
			for (HashMap.Entry<Integer, Deposito> entry : depositos.entrySet()) {
				valor = entry.getKey();
				cantidad = entry.getValue().getCantidad();
				nombreMoneda = entry.getValue().getNombreMoneda();
				String query = "UPDATE depositos SET valor='"+valor+"', cantidad='"+cantidad+"', nombreMoneda='"+nombreMoneda+"' WHERE valor='"+valor+"'";
				
				PreparedStatement ps = conexion.prepareStatement(query);
				ps.executeUpdate();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	
		return guardadoCorrecto;
	}

	@Override
	public boolean guardarDispensadores(HashMap<String, Dispensador> dispensadores) {
		boolean guardadoCorrecto = true;
		String clave, nombre;
		int precio, cantidad;
		
	try {
		for (HashMap.Entry<String, Dispensador> entry : dispensadores.entrySet()) {
			
			clave = entry.getKey();
			nombre = entry.getValue().getNombreProducto();
			precio = entry.getValue().getPrecio();
			cantidad = entry.getValue().getCantidad();
			
			String query = "UPDATE dispensadores SET clave='"+clave+"', nombre='"+nombre+"', precio='"+precio+"', cantidad='"+cantidad+"' WHERE clave='"+clave+"'";
			
			PreparedStatement ps = conexion.prepareStatement(query);
			ps.executeUpdate();
		}
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		return guardadoCorrecto;
	}

}
