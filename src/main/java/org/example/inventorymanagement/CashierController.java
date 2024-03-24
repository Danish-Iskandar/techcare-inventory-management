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
import java.text.DecimalFormat;
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
    public Label lblAmount;
    public Button btnClearAll;
    public Label lbFlavour;
    public double totalAmount = 0, toppingPrice = 0;
    private static final DecimalFormat df = new DecimalFormat("0.00");
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
        Scene scene = new Scene(fxmlLoader.load(), 750, 400);
        stage.setScene(scene);
        stage.show();
    }
    public String bingsuFlavour="";
    public double mainFlavor;
    public void Chocolate(ActionEvent event) {
        bingsuFlavour="Chocolate";
        lbFlavour.setText("Chocolate");
        mainFlavor = 4;
        toppingPrice = 0;
        chWaffle.setSelected(false);
        chIceCream.setSelected(false);
        chJelly.setSelected(false);
        chChocChip.setSelected(false);
        chCaramelSyrup.setSelected(false);
        chChocSyrup.setSelected(false);
        lblAmount.setText(String.valueOf(df.format(mainFlavor)));
    }
    public void Mango(ActionEvent event) {
        bingsuFlavour="Mango";
        lbFlavour.setText("Mango");
        mainFlavor = 4;
        toppingPrice = 0;
        chWaffle.setSelected(false);
        chIceCream.setSelected(false);
        chJelly.setSelected(false);
        chChocChip.setSelected(false);
        chCaramelSyrup.setSelected(false);
        chChocSyrup.setSelected(false);
        lblAmount.setText(String.valueOf(df.format(mainFlavor)));
    }
    public void Bandung(ActionEvent event) {
        bingsuFlavour="Bandung";
        lbFlavour.setText("Bandung");
        toppingPrice = 0;
        mainFlavor = 4;
        chWaffle.setSelected(false);
        chIceCream.setSelected(false);
        chJelly.setSelected(false);
        chChocChip.setSelected(false);
        chCaramelSyrup.setSelected(false);
        chChocSyrup.setSelected(false);
        lblAmount.setText(String.valueOf(df.format(mainFlavor)));
    }
    public void Honeydew(ActionEvent event) {
        bingsuFlavour = "Honeydew";
        lbFlavour.setText("Honeydew");
        mainFlavor = 4;
        toppingPrice = 0;
        chWaffle.setSelected(false);
        chIceCream.setSelected(false);
        chJelly.setSelected(false);
        chChocChip.setSelected(false);
        chCaramelSyrup.setSelected(false);
        chChocSyrup.setSelected(false);
        lblAmount.setText(String.valueOf(df.format(mainFlavor)));
    }
    public void resetButton(ActionEvent event) {
        mainFlavor = 4;
        toppingPrice = 0;
        totalAmount = 4;
        lblAmount.setText(String.valueOf(df.format(totalAmount)));
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
        String buyerPhone = "+6"+txtBuyerPhone.getText();
        String toppings = toppingsTotal;
        String ActualbingsuFlavour= bingsuFlavour;
        if (rbPayNow.isSelected()) {
            Status = "Paid";
        } else {
            Status = "Pending";
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
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("ORDER SUCCESS");
                alert.setHeaderText("Order submitted successfully!");
                alert.showAndWait();
                clearAll();
                System.out.println("Order submitted successfully!");
            } else {
                System.out.println("Failed to submit order. Please try again.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void monetaryProceed(ActionEvent event) {
        String description = txtBuyerName.getText() + " " + "+6" + txtBuyerPhone.getText() + "\n"+ bingsuFlavour + "\n"+ toppingsTotal;
        String outMoney = "0.00";
        String inMoney = String.valueOf(totalAmount);
        if (rbPayNow.isSelected()) {
            Status = "Paid";
        } else {
            Status = "Pending";
        }
        String datetime= String.valueOf(currentDateTime);
        datetime = datetime.substring(0, datetime.length()-10);
        try (PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO statement (StatementDescription, OutMoney, InMoney, DateTime) VALUES (?,?,?,?)")) {
            preparedStatement.setString(1, description);
            preparedStatement.setString(2, outMoney);
            preparedStatement.setString(3, inMoney);
            preparedStatement.setString(4, datetime);


            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {

            } else {
                System.out.println("Failed to submit statement. Please try again.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public void calcWaffle (ActionEvent e) {
        if (chWaffle.isSelected()) {
            toppingPrice = toppingPrice + 0.5;
        } else {
            toppingPrice = toppingPrice - 0.5;
        }
        totalAmount = mainFlavor + toppingPrice;
        lblAmount.setText(String.valueOf(df.format(totalAmount)));
    }
    public void calcIceCream (ActionEvent e) {
        if (chIceCream.isSelected()) {
            toppingPrice = toppingPrice + 0.5;
        } else {
            toppingPrice = toppingPrice - 0.5;
        }
        totalAmount = mainFlavor + toppingPrice;
        lblAmount.setText(String.valueOf(df.format(totalAmount)));
    }
    public void calcJelly (ActionEvent e) {
        if (chJelly.isSelected()) {
            toppingPrice = toppingPrice + 0.5;
        } else {
            toppingPrice = toppingPrice - 0.5;
        }
        totalAmount = mainFlavor + toppingPrice;
        lblAmount.setText(String.valueOf(df.format(totalAmount)));
    }
    public void calcChocChip (ActionEvent e) {
        if (chChocChip.isSelected()) {
            toppingPrice = toppingPrice + 0.5;
        } else {
            toppingPrice = toppingPrice - 0.5;
        }
        totalAmount = mainFlavor + toppingPrice;
        lblAmount.setText(String.valueOf(df.format(totalAmount)));
    }
    public void calcCaramelSyrup (ActionEvent e) {
        if (chCaramelSyrup.isSelected()) {
            toppingPrice = toppingPrice + 0.5;
        } else {
            toppingPrice = toppingPrice - 0.5;
        }
        totalAmount = mainFlavor + toppingPrice;
        lblAmount.setText(String.valueOf(df.format(totalAmount)));
    }
    public void calcChocSyrup (ActionEvent e) {
        if (chChocSyrup.isSelected()) {
            toppingPrice = toppingPrice + 0.5;
        } else {
            toppingPrice = toppingPrice - 0.5;
        }
        totalAmount = mainFlavor + toppingPrice;
        lblAmount.setText(String.valueOf(df.format(totalAmount)));
    }
    public void clearAll() {
        bingsuFlavour = "";
        lbFlavour.setText("");
        toppingPrice = 0;
        totalAmount = 0;
        txtBuyerName.setText("");
        txtBuyerPhone.setText("");
        lblAmount.setText(String.valueOf(df.format(totalAmount)));
        rbPayNow.setSelected(false);
        rbPayLater.setSelected(false);
        chWaffle.setSelected(false);
        chIceCream.setSelected(false);
        chJelly.setSelected(false);
        chChocChip.setSelected(false);
        chCaramelSyrup.setSelected(false);
        chChocSyrup.setSelected(false);
    }
    public void setBtnClearAll (ActionEvent e) {
        clearAll();
    }
    public void submitOrder (ActionEvent event){
        if (!rbPayLater.isSelected() && !rbPayNow.isSelected()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setHeaderText("Select Payment Option!");
            alert.showAndWait();
        } else {
            if (txtBuyerPhone.getText().isBlank() && txtBuyerName.getText().isBlank()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("ERROR");
                alert.setHeaderText("Fill in Buyer Name and Phone Number!");
                alert.showAndWait();
            } else {
                if (bingsuFlavour.isBlank()) {
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
                    monetaryProceed(event);
                    proceed(event);


                }
            }
        }
    }
}
