package accesoDatos;

import java.sql.PreparedStatement;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import logicaRefrescos.Deposito;
import logicaRefrescos.Dispensador;

public class AccesoHibernate implements Datos {

	Scanner sc;
	Deposito deposito;
	Dispensador dispensador;
	SessionFactory sf;
	Session s;

	public AccesoHibernate() {
		sc = new Scanner(System.in);
		deposito = new Deposito();
		dispensador = new Dispensador();
		sf = new Configuration().configure().buildSessionFactory();
	}

	@Override
	public HashMap<Integer, Deposito> obtenerDepositos() {
		HashMap<Integer, Deposito> depositosCreados = new HashMap<Integer, Deposito>();

		s = sf.openSession();
		s.beginTransaction();
		Query q = s.createQuery("select dep from Deposito dep");
		List resultados = q.list();
		Iterator iterador = resultados.iterator();
		System.out.println("\n");
		while (iterador.hasNext()) {

			Deposito deposito = (Deposito) iterador.next();

			deposito.setValor(deposito.getValor());
			deposito.setCantidad(deposito.getCantidad());
			deposito.setNombreMoneda(deposito.getNombreMoneda());

			depositosCreados.put(deposito.getValor(), deposito);

		}

		s.close();
		return depositosCreados;
	}

	@Override
	public HashMap<String, Dispensador> obtenerDispensadores() {
		HashMap<String, Dispensador> dispensadoresCreados = new HashMap<String, Dispensador>();
		
		
		
		s = sf.openSession();
		s.beginTransaction();
		Query q = s.createQuery("select disp from Dispensador disp");
		List resultados = q.list();
		Iterator iterador = resultados.iterator();
		while (iterador.hasNext()) {

			Dispensador dispensador = (Dispensador) iterador.next();

			dispensador.setClave(dispensador.getClave());
			dispensador.setNombreProducto(dispensador.getNombreProducto());
			dispensador.setCantidad(dispensador.getCantidad());
			dispensador.setPrecio(dispensador.getPrecio());

			dispensadoresCreados.put(dispensador.getClave(), dispensador);

		}

		s.close();
		
		
		
		return dispensadoresCreados;
	}

	@Override
	public boolean guardarDepositos(HashMap<Integer, Deposito> depositos) {
		boolean guardado = true;
		s = sf.openSession();
		s.beginTransaction();
		
		for (HashMap.Entry<Integer, Deposito> entry : depositos.entrySet()) {
			
			s.update(entry.getValue());
		}
		
		s.getTransaction().commit();
		s.close();
		return guardado;
	}

	@Override
	public boolean guardarDispensadores(HashMap<String, Dispensador> dispensadores) {
		boolean guardado = true;
		s = sf.openSession();
		s.beginTransaction();
		
		for(HashMap.Entry<String, Dispensador> entry : dispensadores.entrySet()){

			s.update(entry.getValue());

		}
		s.getTransaction().commit();
		s.close();
		return guardado;
	}

}
