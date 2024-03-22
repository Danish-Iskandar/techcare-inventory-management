package org.example.inventorymanagement;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.sql.*;

import java.io.IOException;
import java.sql.DriverManager;
import java.time.LocalDateTime;

public class CashierController {
    private Connection connection;
    public Button btnDashboard;
    public Button btnCashier;
    public Button btnOrderList;
    public Button btnInventory;
    public Button btnMoney;
    public Button btnReset;
    public TextField txtBuyerName;
    public TextField txtBuyerPhone;
    public Button btnProceedCheckout;
    public CheckBox chWaffle;
    public CheckBox chIceCream;
    public CheckBox chJelly;
    public CheckBox chChocChip;
    public CheckBox chCaramelSyrup;
    public CheckBox chChocSyrup;
    public RadioButton rbPayLater;
    public RadioButton rbPayNow;
    public Button btnChoc;
    public Button btnMango;
    public Button btnBandung;
    public Button btnHoneydew;

    public CashierController() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://techcaredb.cbg6264eke46.ap-southeast-2.rds.amazonaws.com :3306/mytechcare", "admin", "shukri1234");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }



    public void switchToDashboard(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("DASHBOARD.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setScene(scene);
        stage.show();
    }
    public void switchToCashier(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("CASHIER.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setScene(scene);
        stage.show();
    }
    public void switchToOrderList(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("ORDER-LIST.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load(), 750, 400);
        stage.setScene(scene);
        stage.show();
    }
    public void switchToInventory(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("INVENTORY.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setScene(scene);
        stage.show();
    }
    public void switchToMonetary(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("MONETARY.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setScene(scene);
        stage.show();
    }
    public String bingsuFlavour="";

    public void Chocolate(ActionEvent event) {
        bingsuFlavour="Chocolate";
    }
    public void Mango(ActionEvent event) {
        bingsuFlavour="Mango";
    }
    public void Bandung(ActionEvent event) {
        bingsuFlavour="Bandung";
    }
    public void Honeydew(ActionEvent event) {
        bingsuFlavour="Honeydew";
    }
    public void resetButton(ActionEvent event) {
        chWaffle.setSelected(false);
        chIceCream.setSelected(false);
        chJelly.setSelected(false);
        chChocChip.setSelected(false);
        chCaramelSyrup.setSelected(false);
        chChocSyrup.setSelected(false);
    }

    public String toppingsTotal="";
    public String Status;
    LocalDateTime currentDateTime = LocalDateTime.now();

    private void proceed(ActionEvent event) {
        String buyerName = txtBuyerName.getText();
        String buyerPhone = txtBuyerPhone.getText();
        String toppings = toppingsTotal;
        String ActualbingsuFlavour= bingsuFlavour;
        if (rbPayNow.isSelected()) {
            Status = "Paid";
        } else {
            Status = "Not Paid";
        }
        String datetime= String.valueOf(currentDateTime);
        datetime = datetime.substring(0, datetime.length()-9);
        try (PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO orderlist (OrderName, OrderPhone, Flavour, Toppings, Status, DateTime) VALUES (?,?,?,?,?,?)")) {
            preparedStatement.setString(1, buyerName);
            preparedStatement.setString(2, buyerPhone);
            preparedStatement.setString(3, ActualbingsuFlavour);
            preparedStatement.setString(4, toppings);
            preparedStatement.setString(5, Status);
            preparedStatement.setString(6, datetime);

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Order submitted successfully!");
            } else {
                System.out.println("Failed to submit order. Please try again.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


        public void submitOrder (ActionEvent event){
            if (!rbPayLater.isSelected() && !rbPayNow.isSelected()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("ERROR");
                alert.setHeaderText("Select Payment Option!");
                alert.showAndWait();
            } else {
                if (txtBuyerPhone.getText() == "" && txtBuyerName.getText() == "") {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("ERROR");
                    alert.setHeaderText("Fill in Buyer Name and Phone Number!");
                    alert.showAndWait();
                } else {
                    if (bingsuFlavour == "") {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("ERROR");
                        alert.setHeaderText("Select a flavour!");
                        alert.showAndWait();
                    } else {
                        toppingsTotal = ""; // Initialize toppings string as empty
                        if (chWaffle.isSelected()) toppingsTotal += "Waffle, ";
                        if (chIceCream.isSelected()) toppingsTotal += "Ice Cream, ";
                        if (chJelly.isSelected()) toppingsTotal += "Jelly, ";
                        if (chChocChip.isSelected()) toppingsTotal += "ChocChip, ";
                        if (chCaramelSyrup.isSelected()) toppingsTotal += "Caramel Syrup, ";
                        if (chChocSyrup.isSelected()) toppingsTotal += "Chocolate Syrup, ";

                        // Remove trailing comma and space if no toppings selected
                        if (toppingsTotal.endsWith(", ")) {
                            toppingsTotal = toppingsTotal.substring(0, toppingsTotal.length() - 2);
                        }
                        proceed(event);

                    }
                }

            }
        }
    }
