package org.example.inventorymanagement;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class InventoryController implements Initializable {
    public Button btnDashboard;
    public Button btnCashier;
    public Button btnOrderList;
    public Button btnInventory;
    public Button btnMoney;
    public Button btnAddUtensil;
    public Button btnRemoveUtensil;
    public Button btnAddIngredient;
    public Button btnRemoveIngredient;
    @FXML
    private ChoiceBox<String> chbInventory;
    private String[] inventoryChoice = {"Utensil", "Ingredient"};
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        chbInventory.getItems().addAll(inventoryChoice);
        chbInventory.setOnAction(this::getInventoryChoice);
    }
    public void getInventoryChoice (ActionEvent event) {
        String invChoice = chbInventory.getValue();
        //Code for the ChoiceBox
        if (Objects.equals(invChoice, "Utensil")) {
            System.out.println(invChoice);
        } else if (Objects.equals(invChoice, "Ingredient")){
            System.out.println(invChoice);
        } else {
            System.out.println("DEYYYYYY");
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
    public void openAddUtensil(ActionEvent e) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("ADD_UTENSIL.fxml"));
        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setScene(scene);
        stage.show();
    }
    public void openAddIngredient(ActionEvent e) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("ADD_INGREDIENT.fxml"));
        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setScene(scene);
        stage.show();
    }
    public void openRemoveUtensil(ActionEvent e) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("REMOVE_UTENSIL.fxml"));
        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setScene(scene);
        stage.show();
    }
    public void openRemoveIngredient(ActionEvent e) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("REMOVE_INGREDIENT.fxml"));
        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setScene(scene);
        stage.show();
    }
}
