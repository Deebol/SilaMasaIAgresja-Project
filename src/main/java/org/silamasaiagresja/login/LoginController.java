package org.silamasaiagresja.login;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.silamasaiagresja.dialogs.AlertBox;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class LoginController implements Initializable{

    @FXML
    private Text dbConnectionStatusText;

    @FXML
    private Label loginLabel;

    @FXML
    private Label passwordLabel;

    @FXML
    private TextField loginTextField;

    @FXML
    private Button loginButton;

    @FXML
    private PasswordField passwordTextField;

    @FXML
    private MenuItem newUserMenuItem;

    @FXML
    private MenuItem changeUserMenuItem;

    @FXML
    private MenuItem deleteUserMenuItem;

    @FXML
    private MenuItem closeAppMenuItem;

    @FXML
    private MenuItem cutMenuItem;

    @FXML
    private MenuItem copyMenuItem;

    @FXML
    private MenuItem pasteMenuItem;

    @FXML
    private MenuItem aboutMenuItem;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		dbConnectionStatusChange(DatabaseConnector.DB_CONNECTION_STATUS);
	}
	
	public void closeApp(ActionEvent e) {
		Main.closeProgram();
	}
	
	public void dbConnectionStatusChange(Text status) {
		dbConnectionStatusText.setText(status.getText());
		dbConnectionStatusText.setStyle(status.getStyle());
	}
	
	public void createNewUser(ActionEvent event) {
		try {
			Pane root = (Pane) FXMLLoader.load(getClass().getResource("NewUser.fxml"));
			Stage stage = new Stage();
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			stage.setScene(scene);
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setTitle("SMiA - Tworzenie nowego użytkownika");
			stage.setResizable(false);
			stage.showAndWait();
		} catch (IOException e) {
			AlertBox.displayError("Nie udało się załadować pliku NewUser.fxml", "Błąd!");
		}
	}
	
	@FXML
	void deleteUser(ActionEvent event) {
		try {
			Pane root = (Pane) FXMLLoader.load(getClass().getResource("DeleteUser.fxml"));
			Stage stage = new Stage();
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			stage.setScene(scene);
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setTitle("SMiA - Usuwanie użytkownika");
			stage.setMinHeight(300);
			stage.setMinWidth(500);
			stage.showAndWait();
		} catch (IOException e) {
			AlertBox.displayError("Nie udało się załadować pliku DeleteUser.fxml", "Błąd!");
		}
	}
	
}
