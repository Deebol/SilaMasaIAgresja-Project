package org.silamasaiagresja.login;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import org.silamasaiagresja.dialogs.AlertBox;
import org.sqlite.core.DB;

import com.mchange.v2.c3p0.impl.DbAuth;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.BorderPane;
import javafx.fxml.FXMLLoader;

public class Main extends Application {
	private Connection connection;
	private Stage window;
	public static final String appTitle = "Siła Masa i Agrejsa"; 

	@Override
	public void start(Stage primaryStage) {
		try  {
			connection = DatabaseConnector.checkConnectionWithDB();
			connection.prepareStatement("SELECT login, haslo FROM Uzytkownicy");
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try { connection.close(); } catch (Exception e) { /*ignored*/}
			//try { DatabaseConnector.DATA_SOURCE.close(); } catch (Exception e) { /*ignored*/ }
		}
		createLoginWindow(primaryStage);
		
	}

	private void createLoginWindow(Stage primaryStage) {
		try {
			BorderPane root = (BorderPane) FXMLLoader.load(getClass().getResource("Login.fxml"));
			window = primaryStage;
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			window.setScene(scene);
			window.setTitle(appTitle);	
			window.sizeToScene();
			window.setResizable(false);
			window.setOnCloseRequest(e -> {
				e.consume();
				closeProgram();
			});
			window.show();
		} catch (IOException e1) {
			AlertBox.displayError("Nie udało się załadować pliku Login.fxml", "Błąd!");
			e1.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
	
	public static void closeProgram() {
		ButtonType buttonYes = new ButtonType("Tak");
		ButtonType buttonNo = new ButtonType("Nie");
		if(AlertBox.displayConfirmation("Czy na pewno chcesz wyjsc?", "Zamykanie programu", 
				buttonYes, buttonNo).equals(buttonYes)) {
			System.exit(0);
		}
	}
}
