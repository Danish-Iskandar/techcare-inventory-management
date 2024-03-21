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
    public RadioButton rbPayLater;
    public RadioButton rbPayNow;



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
    public void submitOrder(ActionEvent event) {
        if (!rbPayLater.isSelected() && !rbPayNow.isSelected()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setHeaderText("Select Payment Option!");
            alert.showAndWait();
        }else{
            if (txtBuyerPhone.getText()=="" && txtBuyerName.getText()==""){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("ERROR");
                alert.setHeaderText("Fill in Buyer Name and Phone Number!");
                alert.showAndWait();
            }else{
                if (bingsuFlavour==""){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("ERROR");
                    alert.setHeaderText("Select a Flavour!");
                    alert.showAndWait();
                }else{

                }
            }
        }

    }
}
