package org.example.inventorymanagement;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;

public class CashierController {
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
    public String selectedTopping;
    public String topping1;
    public String topping2;
    public String topping3;
    private static final int max_selection = 3;
    private int selectedCount = 0;

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
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
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
    static void selectedToppings(CheckBox ch, String topping1, String topping2, String topping3, String selectedTopping) {
        if (ch.isSelected()) {
            System.out.println("Test");
            if (topping1 == null) {
                topping1 = selectedTopping;
            } else if (topping2 == null) {
                topping2 = selectedTopping;
            } else if (topping3 == null) {
                topping3 = selectedTopping;
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("TOPPINGS");
                alert.setHeaderText("You can only select three (3) toppings only.");
                alert.showAndWait();
            }
        }
    }
    public void toppings(ActionEvent event) {
        if (chWaffle.isSelected()) {
            selectedTopping = "Waffle";
            selectedToppings(chWaffle, topping1, topping2, topping3, selectedTopping);
        }
        if (chIceCream.isSelected()) {
            selectedTopping = "Ice Cream";
            selectedToppings(chWaffle, topping1, topping2, topping3, selectedTopping);
        }
        if (chJelly.isSelected()) {
            selectedTopping = "Jelly";
            selectedToppings(chWaffle, topping1, topping2, topping3, selectedTopping);
        }
        if (chChocChip.isSelected()) {
            selectedTopping = "Chocolate Chip";
            selectedToppings(chWaffle, topping1, topping2, topping3, selectedTopping);
        }
    }
    public void resetButton(ActionEvent e) {
        chWaffle.setSelected(false);
        chIceCream.setSelected(false);
        chJelly.setSelected(false);
        chChocChip.setSelected(false);
        chCaramelSyrup.setSelected(false);
        chChocSyrup.setSelected(false);
    }
}
