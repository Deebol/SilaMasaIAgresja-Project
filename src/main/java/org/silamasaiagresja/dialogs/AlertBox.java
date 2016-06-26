package org.silamasaiagresja.dialogs;

import org.silamasaiagresja.login.Main;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;

public class AlertBox {

	public static void displayError(String contentText, String headerText) {
		Alert alertBox = new Alert(AlertType.ERROR);
		alertBox.setContentText(contentText);
		alertBox.setHeaderText(headerText);
		alertBox.setTitle(Main.appTitle);
		alertBox.showAndWait();
	}
    /**
     * 
     * @param contentText
     * @param headerText
     * @param types - types of Button that will be shown in Dialog
     * @return Method returns ButtonType of clicked Button
     */
	public static ButtonType displayConfirmation(String contentText, String headerText,
			ButtonType... types) {
		Alert confirmBox = new Alert(AlertType.CONFIRMATION);
		confirmBox.getButtonTypes().setAll(types);
		confirmBox.setContentText(contentText);
		confirmBox.setHeaderText(headerText);
		confirmBox.setTitle(Main.appTitle);
		confirmBox.showAndWait();
		return confirmBox.getResult();
	}
}
