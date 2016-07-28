package org.silamasaiagresja.login;

import java.beans.PropertyVetoException;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.silamasaiagresja.dialogs.AlertBox;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import javafx.scene.text.Text;

public class DatabaseConnector {
	public static final Text DB_CONNECTION_STATUS = new Text();
	private static final String dataBasePath = DatabaseConnector.class.getResource("SilaMasaIAgresja.db").getPath();
	public static final ComboPooledDataSource DATA_SOURCE = getDataSource();
	
	public static ComboPooledDataSource getDataSource() {
		ComboPooledDataSource cpds = null; 
		try {
			cpds = new ComboPooledDataSource();
			cpds.setDriverClass("org.sqlite.JDBC");
			cpds.setJdbcUrl("jdbc:sqlite:" + dataBasePath);
			System.out.println(dataBasePath);
		} catch (PropertyVetoException e) {
			e.printStackTrace();
		}
		return cpds;	
	}

	public static Connection checkConnectionWithDB() throws SQLException  {
		//System.out.println(DATA_SOURCE.toString());
		if (new File(dataBasePath).exists() && DATA_SOURCE != null) {
			Connection conn = DATA_SOURCE.getConnection();
			DB_CONNECTION_STATUS.setText("Połączono z bazą danych");
			DB_CONNECTION_STATUS.setStyle("-fx-fill: green;");
			return conn;
		} else {
			DB_CONNECTION_STATUS.setText("Nie udało się połączyć z bazą danych");
			DB_CONNECTION_STATUS.setStyle("-fx-fill: red;");
			return null;
		}
	}
	
}
