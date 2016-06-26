package org.silamasaiagresja.login;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javax.print.attribute.standard.DialogTypeSelection;

import org.silamasaiagresja.dialogs.AlertBox;

import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Duration;

public class NewUserController implements Initializable {

	@FXML
	private TextField loginTextField;

	@FXML
	private TextField passwordTextField;

	@FXML
	private TextField confirmPassTextField;

	@FXML
	private Label passwordLabel;

	@FXML
	private Label loginLabel;

	@FXML
	private Label confirmPassLabel;

	@FXML
	private Button cancelButton;

	@FXML
	private Button createButton;

	@FXML
	private ImageView passwordFailIcon;

	@FXML
	private ImageView confirmPassFailIcon;

	@FXML
	private ImageView confimPassSuccessIcon;

	@FXML
	private ImageView passwordSuccessIcon;

	@FXML
	private ImageView successIcon;

	@FXML
	void closeWindow(ActionEvent event) {
		Stage stage = (Stage) cancelButton.getScene().getWindow();
		stage.close();
	}

	@FXML
	void createUser(ActionEvent event) throws SQLException  {
		PauseTransition visiblePause = new PauseTransition(Duration.seconds(3));
		if (textFieldsValuesAreCorrect()) {
			Connection conn = null;
			PreparedStatement myStatement = null;
			try {
				conn = DatabaseConnector.DATA_SOURCE.getConnection();
				myStatement = conn
						.prepareStatement("INSERT INTO Uzytkownicy (login, haslo)" + " VALUES (?, ?)");
				myStatement.setString(1, loginTextField.getText());
				myStatement.setString(2, passwordTextField.getText());
				myStatement.executeUpdate();
				successIcon.setVisible(true);
				visiblePause.setOnFinished(e -> successIcon.setVisible(false));
				visiblePause.play();
				passwordFailIcon.setVisible(false);
				confirmPassFailIcon.setVisible(false);
			} catch (SQLException e) {
				AlertBox.displayError("Login zajęty", "Błąd!");
				e.printStackTrace();
			} finally {
				myStatement.close();
				conn.close();
			}
		} else if (passwordDontMatch()) {
			successIcon.setVisible(false);
			passwordFailIcon.setVisible(true);
			confirmPassFailIcon.setVisible(true);
			visiblePause.setOnFinished(e -> {
				passwordFailIcon.setVisible(false);
				confirmPassFailIcon.setVisible(false);
			});
			visiblePause.play();
		} else {
			passwordFailIcon.setVisible(false);
			confirmPassFailIcon.setVisible(false);
			successIcon.setVisible(false);
		}
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub

	}

	public boolean textFieldsValuesAreCorrect() {
		return (passwordTextField.getText()).equals(confirmPassTextField.getText())
				&& !(passwordTextField.getText().isEmpty()) && !(loginTextField.getText().isEmpty());
	}

	public boolean passwordDontMatch() {
		return !(passwordTextField.getText().equals(confirmPassTextField.getText()));
	}

}
