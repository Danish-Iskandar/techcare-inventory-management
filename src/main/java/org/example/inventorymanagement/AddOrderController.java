package org.example.inventorymanagement;

import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import Models.Orders;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;

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
    private JFXTextField ordernameFld;
    @FXML
    private JFXTextField orderphoneFld;
    @FXML
    private JFXTextField flavourFld;
    @FXML
    private JFXTextField toppingsFld;

    String query = null;
    Connection connection= null;
    ResultSet resultSet = null;
    PreparedStatement preparedStatement;
    Orders orders = null;
    private boolean update;
    int orderID;

@Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
    @FXML
    private void save(MouseEvent event) {

        connection = DBConnect.getConnect();
        String OrderName = ordernameFld.getText();
        String OrderPhone = orderphoneFld.getText();
        String Flavour = flavourFld.getText();
        String Toppings = toppingsFld.getText();

        if (OrderName.isEmpty() || OrderPhone.isEmpty() || Flavour.isEmpty() || Toppings.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Please Fill All DATA");
            alert.showAndWait();

        } else {
            getQuery();
            insert();
            clean();

        }

    }

    @FXML
    private void clean() {
        ordernameFld.setText(null);
        orderphoneFld.setText(null);
        flavourFld.setText(null);
        toppingsFld.setText(null);

    }

    private void getQuery() {

        if (update == false) {

            query = "INSERT INTO `orderlist`( `OrderName`, `OrderPhone`, `Flavour`, `Toppings`) VALUES (?,?,?,?)";

        }else{
            query = "UPDATE `student` SET "
                    + "`OrderName`=?,"
                    + "`OrderPhone`=?,"
                    + "`Flavour`=?,"
                    + "`Toppings`= ? WHERE OrderID = '"+orderID+"'";
        }

    }

    private void insert() {

        try {

            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, orderphoneFld.getText());
            preparedStatement.setString(2, orderphoneFld.getText());
            preparedStatement.setString(3, flavourFld.getText());
            preparedStatement.setString(4, toppingsFld.getText());
            preparedStatement.execute();

        } catch (SQLException ex) {
            Logger.getLogger(AddOrderController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    void setTextField(int OrderID, String OrderName,String OrderPhone, String Flavour, String Toppings) {

        orderID = OrderID;
        ordernameFld.setText(OrderName);
        orderphoneFld.setText(OrderPhone);
        flavourFld.setText(Flavour);
        toppingsFld.setText(Toppings);

    }

    void setUpdate(boolean b) {
        this.update = b;

    }
}

