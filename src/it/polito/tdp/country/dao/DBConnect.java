package it.polito.tdp.country.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.sql.DataSource;

import com.mchange.v2.c3p0.DataSources;


public class DBConnect {

	private static String jdbcURL = "jdbc:mysql://localhost/countries?user=root";
	private static DataSource ds;   // variabile statica perche ce ne e` una sola

	public static Connection getConnection() {
		// verifico se DataSource sia gia` stato creata in passato
		
		// se la connessione (DataSource) non e` mai stata creata: 
		// creo una connection pool e la salvo in una variabile ds cosi che possa essere chiamata alle chiamate successive
		if (ds == null) {
			// crea il DataSource
			try {
				ds = DataSources.pooledDataSource( DataSources.unpooledDataSource(jdbcURL));  
							//metodo 2					//metodo 1
			
			} catch (SQLException e) {
				e.printStackTrace();
				System.exit(1);     //faccio terminare il programma perche se non sono riuscito a creare una connessione e` un errore non recuperabile
			}
		}

		// se DataSource e` gia' stato creata e quindi ds != null 
		// --> faccio ds.getConnection()  ovvero prendo in prestito la connessione dal pool
		try {
			Connection c = ds.getConnection();  // non serve piu` l'url passato come parametro
			return c;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

	}

}
