package org.example.inventorymanagement;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import Models.Orders;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AddOrderController implements Initializable {

    @FXML
    private TextField ordernameFld;
    @FXML
    private TextField orderphoneFld;
    @FXML
    private TextField flavourFld;
    @FXML
    private TextField toppingsFld;
    @FXML
    private TextField statusfld;
    @FXML
    private Button btnSave;



    String query = null;
    Connection connection= null;
    ResultSet resultSet = null;
    PreparedStatement preparedStatement;
    Orders orders = null;
    private boolean update;
    int orderID;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }
    @FXML
    private void updateStatus(ActionEvent event) throws IOException {

        connection = DBConnect.getConnect();
        String OrderName = ordernameFld.getText();
        String OrderPhone = orderphoneFld.getText();
        String Flavour = flavourFld.getText();
        String Toppings = toppingsFld.getText();
        String Status = "Completed";

        getQuery();
        insert();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("STATUS UPDATED");
        alert.setHeaderText("Status updated! Please reload the page to update the table.");
        alert.showAndWait();
        stage.close();
    }
    private void getQuery() {

        if (update == false) {

            query = "INSERT INTO `orderlist`(`Status`) VALUES (?)";

        }else{
            query = "UPDATE `orderlist` SET "
                    + "`Status`= ? WHERE OrderID = '"+orderID+"'";
        }

    }

    private void insert() {

        try {

            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, "Completed");
            preparedStatement.execute();

        } catch (SQLException ex) {
            Logger.getLogger(AddOrderController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    void setTextField(int OrderID, String OrderName,String OrderPhone, String Flavour, String Toppings, String Status) {

        orderID = OrderID;
        ordernameFld.setText(OrderName);
        orderphoneFld.setText(OrderPhone);
        flavourFld.setText(Flavour);
        toppingsFld.setText(Toppings);
        statusfld.setText(Status);

    }

    void setUpdate(boolean b) {
        this.update = b;

    }
}

