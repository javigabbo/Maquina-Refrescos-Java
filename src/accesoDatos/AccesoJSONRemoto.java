package accesoDatos;

import java.sql.PreparedStatement;
import java.util.HashMap;
import java.util.Scanner;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import logicaRefrescos.Deposito;
import logicaRefrescos.Dispensador;

public class AccesoJSONRemoto implements Datos {

	ApiRequests encargadoPeticiones;
	JSONObject jsonObject;
	JSONParser jsonParser;
	HashMap<Long, String> mapaPreguntas;

	public AccesoJSONRemoto() {
		mapaPreguntas = new HashMap<Long, String>();
		encargadoPeticiones = new ApiRequests();
		jsonParser = new JSONParser();
	}

	@Override
	public HashMap<Integer, Deposito> obtenerDepositos() {
		
		HashMap<Integer, Deposito> depositosCreados = new HashMap<Integer, Deposito>();
		Deposito auxDeposito;
		int valor = 0;
		int cantidad = 0; 
		 String nombreMoneda = "";
		String url = "http://localhost/JAVI/PHP/verDepositosJSON.php";
		try {
			String response = encargadoPeticiones.getRequest(url);
			JSONArray jsonArray = (JSONArray) jsonParser.parse(response);
			System.out.println("DEPOSITOS");
			for (int i = 0; i < jsonArray.size(); i++) {

				jsonObject = (JSONObject) jsonArray.get(i);

				System.out.println(".........................");
				System.out.println("Valor de la moneda: " + jsonObject.get("clave"));
				System.out.println("Cantidad: " + jsonObject.get("cantidad"));
				System.out.println("Nombre de la moneda: " + jsonObject.get("nombre"));
				System.out.println(".........................\n");

				valor = Integer.parseInt((String)jsonObject.get("clave"));
				cantidad = Integer.parseInt((String)jsonObject.get("cantidad"));
				nombreMoneda = (String) jsonObject.get("nombreMoneda");
				auxDeposito = new Deposito(nombreMoneda, valor, cantidad);
				depositosCreados.put(valor, auxDeposito);

			}
			
		} catch (Exception e) {
			System.out.println("Error en la devolucion de depositos por JSON.");
			e.printStackTrace();
		}
		
		return depositosCreados;
	}

	@Override
	public HashMap<String, Dispensador> obtenerDispensadores() {

		HashMap<String, Dispensador> dispensadoresCreado = new HashMap<String, Dispensador>();
		Dispensador dispensador;
		String clave = "";
		String nombre = "";
		int precio = 0;
		int cantidad = 0;
		String url = "http://localhost/JAVI/PHP/verProductosJSON.php";
		try {
			String response = encargadoPeticiones.getRequest(url);
			JSONArray jsonArray = (JSONArray) jsonParser.parse(response);
			System.out.println("DISPENSADORES");
			for (int i = 0; i < jsonArray.size(); i++) {

				jsonObject = (JSONObject) jsonArray.get(i);

				System.out.println(".........................");
				System.out.println("Clave: " + jsonObject.get("clave"));
				System.out.println("Nombre: " + jsonObject.get("nombre"));
				System.out.println("Precio: " + jsonObject.get("precio"));
				System.out.println("Cantidad: " + jsonObject.get("cantidad"));
				System.out.println(".........................\n");

				clave = (String) jsonObject.get("clave");
				nombre = (String) jsonObject.get("nombre");
				precio = Integer.parseInt((String) jsonObject.get("precio"));
				cantidad = Integer.parseInt((String)jsonObject.get("cantidad"));
				dispensador = new Dispensador(clave, nombre, precio, cantidad);
				dispensadoresCreado.put(clave, dispensador);

			}

		} catch (Exception e) {
			System.out.println("Error en la devolucion de dispensadores por JSON.");
		}

		return dispensadoresCreado;
	}

	@Override
	public boolean guardarDepositos(HashMap<Integer, Deposito> depositos) {
		
		boolean guardadoCorrecto = true;
		int valor, cantidad;
		String nombreMoneda;
		String url = "http://localhost/JAVI/PHP/guardaDepositosJSON.php";
		JSONObject obj = new JSONObject();
		
		try {
			for (HashMap.Entry<Integer, Deposito> entry : depositos.entrySet()) {
				valor = entry.getKey();
				cantidad = entry.getValue().getCantidad();
				nombreMoneda = entry.getValue().getNombreMoneda();
				obj.putAll(depositos);
				String json = obj.toJSONString();
				String response = encargadoPeticiones.postRequestWithParams(url, json);
				System.out.println(response);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	
		return guardadoCorrecto;
		
	}

	@Override
	public boolean guardarDispensadores(HashMap<String, Dispensador> dispensadores) {
		
		
		
		System.out.println("GUARDAR DISPENSADORES NO IMPLEMENTADO");
		return false;
	}

}
