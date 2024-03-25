package org.example.inventorymanagement;


import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.sql.*;
import javafx.stage.Stage;
import javafx.scene.*;
import javafx.scene.control.Alert;

public class LoginController extends Component {
    public javafx.scene.control.Button btnLogin;
    public javafx.scene.control.TextField txtUsername;
    public javafx.scene.control.TextField txtPassword;
    private Connection connection;

    public LoginController() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://techcaredb.cbg6264eke46.ap-southeast-2.rds.amazonaws.com :3306/mytechcare", "admin", "shukri1234");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void loginButton(ActionEvent event) throws IOException {
        login(event);
    }

    private void login(ActionEvent event) {
        String username = txtUsername.getText();
        String password = String.valueOf(txtPassword.getText());

        try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM userinfo WHERE BINARY Username=? AND BINARY Password=?")) {
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("DASHBOARD.fxml"));
                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    Scene scene = new Scene(fxmlLoader.load(), 600, 400);
                    stage.setScene(scene);
                    stage.show();
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("ERROR");
                    alert.setHeaderText("User not found. Please try again...");
                    alert.showAndWait();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "An error occurred while processing your request. Please try again later.");
        }
    }
}