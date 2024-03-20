package org.example.inventorymanagement;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javax.swing.*;
import javax.swing.plaf.nimbus.State;
import javax.xml.transform.Result;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.sql.*;
import java.sql.PreparedStatement;
import java.sql.Connection;


import java.util.Optional;

public class LoginController extends Component {
    public Button btnLogin;
    public TextField txtUsername;
    public TextField txtPassword;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private Connection connection;
    private PreparedStatement preparedStatement;

    public void loginButton(ActionEvent event) throws IOException {

        /* Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Conformation");
        alert.setHeaderText("Look, a Confirmation Dialog");

        Optional<ButtonType> result = alert.showAndWait();
        //noinspection OptionalGetWithoutIsPresent
        if (result.get() == ButtonType.OK){
            FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("DASHBOARD.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(fxmlLoader.load(), 600, 400);
            stage.setScene(scene);
            stage.show();*/

        String username = txtUsername.getText();
        String password = txtPassword.getText();

        user = getAuthenticatedUser(username, password, event);



    }
    public User user;
    private User getAuthenticatedUser(String username, String password, ActionEvent event) {
        User user = null;

        final String DBLink= "jdbc:mysql://localhost:3306/mysql";
        final String USERNAME= "root";
        final String PASSWORD= "0000";

        try {
            Connection conn = DriverManager.getConnection(DBLink, USERNAME, PASSWORD);

            Statement stnt= conn.createStatement();
            String sql = "SELECT * FROM userinfo WHERE Username=? AND Password=?";
            PreparedStatement preparedStatement= conn.prepareStatement(sql);
            preparedStatement.setString(1,username);
            preparedStatement.setString(2,password);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()){
                user= new User();
                user.username = resultSet.getString("Username");
                user.password = resultSet.getString("Password");
                user.accesslevel = resultSet.getString("Admin Access");
                FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("DASHBOARD.fxml"));
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                Scene scene = new Scene(fxmlLoader.load(), 600, 400);
                stage.setScene(scene);
                stage.show();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("ERROR");
                alert.setHeaderText("User not found. Please try again...");
            }

        }catch(Exception e){
            e.printStackTrace();
        }

        return user;
    }
}