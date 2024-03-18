package org.example.inventorymanagement;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

public class LoginController {
    public Button btnLogin;
    public TextField txtUsername;
    public TextField txtPassword;

    public void loginButton(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Conformation");
        alert.setHeaderText("Look, a Confirmation Dialog");

        Optional<ButtonType> result = alert.showAndWait();
        //noinspection OptionalGetWithoutIsPresent
        if (result.get() == ButtonType.OK){
            FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("DASHBOARD.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(fxmlLoader.load(), 600, 400);
            stage.setScene(scene);
            stage.show();
        }
    }
}
