package accesoDatos;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;
import org.json.simple.*;
import org.json.simple.parser.JSONParser;

public class Intermediario {

	ApiRequests encargadoPeticiones;
	Scanner teclado;
	JSONObject jsonObject;
	JSONParser jsonParser;
	HashMap<Long, String> mapaPreguntas;

	public Intermediario() {
		mapaPreguntas = new HashMap<Long, String>();
		teclado = new Scanner(System.in); // Para leer las opciones de teclado
		encargadoPeticiones = new ApiRequests();
		jsonParser = new JSONParser();
	}

	public void ejecucion() {
		int op = 0; // Opcion
		boolean salir = false;

		while (!salir) { // Estructura que repite el algoritmo del menu
							// principal hasta que se la condicion sea falsa
			// Se muestra el menu principal
			System.out.println(".......................... \n" + ".  0 Salir \n" + ".  1 Leer consultas abiertas  \n"
					+ ".  2 Iniciar consulta \n" + ".  3 Cerrar consulta \n"

					+ "..........................");
			try {
				op = teclado.nextInt(); // Se le da a la variable op el valor
										// del teclado
				System.out.println("OPCION SELECCIONADA:" + op);
				switch (op) {
				case 0:
					System.out.println("Adios");
					System.exit(0);
				case 1://
					leerConsultas();
					break;
				case 2://
					insertarPregunta();
					break;
				case 3://
					cerrarConsulta();
					break;
				default:// No valido
					System.out.println("Opcion invalida: marque un numero de 1 a 3");
					break;
				}
			} catch (Exception e) {
				System.out.println("Excepcion por opcion invalida: marque un numero de 1 a 3");
				// flushing scanner
				// e.printStackTrace();
				teclado.next();
			}
		}

		// teclado.close();

	}

	private void leerConsultas() {

		try {
			System.out.println("Lanzamos peticion JSON para consultas abiertas");

			String url = "http://localhost/JAVI/listaEspera/consulta.php";

			System.out.println("La url a la que lanzamos la petición es " + url);

			String response = encargadoPeticiones.getRequest(url);

			JSONArray jsonArray = (JSONArray) jsonParser.parse(response);

			for (int i = 0; i < jsonArray.size(); i++) {

				jsonObject = (JSONObject) jsonArray.get(i);

				System.out.println("....");
				System.out.println("ID Peticion: " + jsonObject.get("idPeticion"));
				System.out.println("Direccion IP: " + jsonObject.get("direccionIP"));
				System.out.println("Usuario: " + jsonObject.get("usuario"));
				System.out.println("Pregunta: " + jsonObject.get("texto"));
				System.out.println("Abierta: " + jsonObject.get("abierta"));
				System.out.println("Fecha Inicio: " + jsonObject.get("fechaInicio"));
				System.out.println("Fecha Fin: " + jsonObject.get("fechaFin"));
				System.out.println("....\n");
			}

		} catch (Exception e) {
			System.out.println("Ha ocurrido un error en la busqueda de datos " + e.getMessage());
			System.exit(-1);
		}

	}

	private void cerrarConsulta() {
		try {

			
			System.out.println("TUS PETICIONES:");
			

			for(Map.Entry<Long, String> entry : mapaPreguntas.entrySet()){
				System.out.println("___________________");
				System.out.println("ID pregunta: " + entry.getKey());
				System.out.println("Texto: " + entry.getValue());
				System.out.println("___________________");
			}
			
			

			System.out.println("Escribe el número de la petición que quieres cerrar:");
			int idPeticion = teclado.nextInt();
			cerrar(idPeticion);

		} catch (Exception e) {
			System.out.println("Ha ocurrido un error en la actualización de datos." + e.getMessage());
		}
	}

	
	
	@SuppressWarnings("unchecked")
	private void cerrar(int idPeticion) {

		try {

			String url = "http://localhost/JAVI/listaEspera/eliminarPregunta.php";

			JSONObject obj = new JSONObject();
			obj.put("idPeticion", idPeticion);

			String json = obj.toJSONString();

			String response = encargadoPeticiones.postRequestWithParams(url, json);

			System.out.println(response);

		} catch (Exception e) {
			System.out.println("Ha ocurrido un error en la inserción de datos." + e.getMessage());
		}

	}
	
	
	
	
	

	@SuppressWarnings("unchecked")
	private void insertarPregunta() {
		try {

			String url = "http://localhost/JAVI/listaEspera/insert.php";

			System.out.println("Nombre de usuario: ");
			String usuario = teclado.next();
			teclado.nextLine();
			System.out.println("Texto de la pregunta: ");
			String pregunta = teclado.next();
			teclado.nextLine();

			JSONObject obj = new JSONObject();
			obj.put("usuario", usuario);
			obj.put("pregunta", pregunta);

			String json = obj.toJSONString();

			String response = encargadoPeticiones.postRequestWithParams(url, json);

			JSONObject jsonObject = (JSONObject) jsonParser.parse(response);

			Long resp = (Long) jsonObject.get("respuesta");
			System.out.println("ID de tu pregunta: "+ resp);
			
			mapaPreguntas.put(resp, pregunta);
			

		} catch (Exception e) {
			System.out.println("¡Error!: " + e.getMessage());
		}

	}

}