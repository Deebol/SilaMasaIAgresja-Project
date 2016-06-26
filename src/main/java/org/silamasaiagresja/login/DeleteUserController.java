package org.silamasaiagresja.login;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class DeleteUserController implements Initializable{

    @FXML
    private ListView<String> usersListView;

    @FXML
    private Label loginLabel;

    @FXML
    private TextField loginTextField;

    @FXML
    private TextField passwordTextField;

    @FXML
    private Label passwordLabel;

    @FXML
    private Button cancelButton;

    @FXML
    private Button deleteButton;

    @FXML
    private Text deleteStatusText;
    /**
     * Key is User login and Value is User password  
     */
    private Map<String, String> usersData = new Hashtable<String, String>();

    @FXML
    void closeWindow(ActionEvent event) {
    	Stage stage = (Stage) cancelButton.getScene().getWindow();
		stage.close();
    }

    @FXML
    void deleteUser(ActionEvent event) throws SQLException {
        if(loginTextFieldIsEmpty()) {
        	deleteStatusText.setText("Nie podano Nazwy Użytkownika");
			deleteStatusText.setStyle("-fx-fill: red;");
        } else if(wrongUserLogin()) {
        	deleteStatusText.setText("Nie ma takiego Uzytkownika");
			deleteStatusText.setStyle("-fx-fill: red;");
        } else if(passwordIsValid()) {
        	Connection conn = null;
        	Statement myStatement = null;
			try {
				conn = DatabaseConnector.DATA_SOURCE.getConnection();
				myStatement = conn.createStatement();
	        	myStatement.execute("DELETE FROM Uzytkownicy WHERE login='" + loginTextField.getText() 
	        	+ "'" );
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				myStatement.close();
			}
        	ObservableList<String> listItems = usersListView.getItems();
        	listItems.remove(loginTextField.getText());
        	usersListView.setItems(listItems);
        	deleteStatusText.setText("Usunięto użytkownika " + loginTextField.getText());
			deleteStatusText.setStyle("-fx-fill: green;");
        } else {
        	deleteStatusText.setText("Hasło jest nieprawidłowe");
			deleteStatusText.setStyle("-fx-fill: red;");
        }
    }

	private boolean wrongUserLogin() {
		List<String> list = usersListView.getItems();
		String currentLoginTextField = loginTextField.getText();
		for (String l : list) {
			if(l.equals(currentLoginTextField)) return false;
		}
		return true;
	}

	private boolean passwordIsValid() {
		return passwordTextField.getText().equals(usersData.get(loginTextField.getText()));
	}

	
    @FXML
    void mouseClicked(MouseEvent click) {
    	if(click.getClickCount() == 2) {
			String currentItemSelected = usersListView.getSelectionModel()
                    .getSelectedItem();
			loginTextField.setText(currentItemSelected);
			passwordTextField.clear();
		}
    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		showUsersInUsersList();
	}

	private void showUsersInUsersList() {
		try {
			Connection conn = DatabaseConnector.DATA_SOURCE.getConnection();
			Statement myStatement = conn.createStatement();
			ResultSet result = myStatement.executeQuery("SELECT login, haslo FROM Uzytkownicy");
			while (result.next()) {
				usersListView.getItems().add(result.getString(1));
				usersData.put(result.getString(1), result.getString(2));
			}
			result.close();
			myStatement.close();
			conn.close();
			
		} catch (SQLException e) {
			deleteStatusText.setText("Nie udało się połączyć z bazą danych");
			deleteStatusText.setStyle("-fx-fill: red;");
			e.printStackTrace();
		}
	}
	
	private boolean loginTextFieldIsEmpty() {
		return loginTextField.getText().equals("");
	}
    

}
