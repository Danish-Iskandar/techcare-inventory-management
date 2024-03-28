package org.example.inventorymanagement;

import Models.Orders;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class AddStatementController {
    public Button btnConfirm;
    public Button btnClose;
    public TextArea txtDesc;
    public TextField txtRemarks;
    public TextField txtOut;
    public TextField txtIn;
    Connection connection = null ;
    LocalDateTime currentDateTime = LocalDateTime.now();
    private void savestatement() {
        connection = DBConnect.getConnect();


        String description = txtDesc.getText();
        String outMoney = txtOut.getText();
        String inMoney = txtIn.getText();
        String formattedInMoney = String.format("%.2f", inMoney);
        String formattedOutMoney = String.format("%.2f", outMoney);
        String remarks = txtRemarks.getText();
        if (txtRemarks.getText().isBlank()) {
            remarks = "";
        } else {
            remarks = txtRemarks.getText();
        }
        String datetime = String.valueOf(currentDateTime);
        datetime = datetime.substring(0, datetime.length() - 10);
        try (PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO statement (StatementDescription, OutMoney, InMoney, DateTime, StatementRemarks) VALUES (?,?,?,?,?)")) {
            preparedStatement.setString(1, description);
            preparedStatement.setString(2, formattedOutMoney);
            preparedStatement.setString(3, formattedInMoney);
            preparedStatement.setString(4, datetime);
            preparedStatement.setString(5, remarks);


            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {

            } else {
                System.out.println("Failed to submit statement. Please try again.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


        public void setBtnConfirm (ActionEvent event){
            if (txtDesc.getText().isBlank()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("ERROR");
                alert.setHeaderText("Add Proper Detailed Description!");
                alert.showAndWait();
            } else {
                if (txtOut.getText().isBlank() || txtIn.getText().isBlank()) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("ERROR");
                    alert.setHeaderText("Specify the amount of money coming out or into account!");
                    alert.showAndWait();
                } else {
                    savestatement();
                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    stage.close();
                }
            }
        }


    public void setBtnClose(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }
}
