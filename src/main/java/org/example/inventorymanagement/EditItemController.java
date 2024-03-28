package org.example.inventorymanagement;

import Models.ItemStateSingleton;
import Models.Items;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EditItemController implements Initializable {

    ItemStateSingleton itemStateSingleton = ItemStateSingleton.getInstance();

    // Retrieve the itemState
    String itemState = itemStateSingleton.getItemState();
    @FXML
    private TextField itemFld;
    @FXML
    private Spinner quantityFld;
    @FXML
    private TextField remarksFld;
    @FXML
    private Button btnSave;



    String query = null;
    Connection connection= DBConnect.getConnect();
    ResultSet resultSet = null;
    PreparedStatement preparedStatement;
    Items items = null;
    private boolean update;
    int itemsID;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        quantityFld.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 0, 1));

        quantityFld.getEditor().textProperty().addListener((obs, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                quantityFld.getEditor().setText(newValue.replaceAll("[^\\d]", ""));
            }
        });

        // Set up a StringConverter to convert text to integer
        quantityFld.getValueFactory().setConverter(new StringConverter<Integer>() {
            @Override
            public String toString(Integer object) {
                if (object == null) {
                    return "";
                } else {
                    return object.toString();
                }
            }

            @Override
            public Integer fromString(String string) {
                try {
                    // Parse the input text as an integer
                    return Integer.parseInt(string);
                } catch (NumberFormatException e) {
                    // Return null if the input cannot be parsed as an integer
                    return null;
                }
            }
        }); // This is where the missing parenthesis was added
    }

    public void setBtnCancel(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("INVENTORY.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void updateStatus(ActionEvent event) throws IOException {

        String ItemName = itemFld.getText();
        int ItemQuantity = (int) quantityFld.getValue();
        String ItemRemarks = remarksFld.getText();


        getQuery();
        insert();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("STATUS UPDATED");
        alert.setHeaderText("Updated! Please reload the page to update the table.");
        alert.showAndWait();
        stage.close();
    }
    private void getQuery() {

        if (update == false) {

            System.out.println("What");

        }else{
            if (itemState == "Utensil"){
                query = "UPDATE `utensillist` SET "
                        + "`UtensilName`=?,"
                        + "`UtensilQuantity`=?,"
                        + "`UtensilRemarks`= ? WHERE UtensilID = '"+itemsID+"'";
            }else{
                query = "UPDATE `ingredientlist` SET "
                        + "`IngredientName`=?,"
                        + "`IngredientQuantity`=?,"
                        + "`IngredientRemarks`= ? WHERE IngredientID = '"+itemsID+"'";
            }

        }

    }

    private void insert() {

        try {
            String ItemName = itemFld.getText();
            int ItemQuantity = (int) quantityFld.getValue();
            String ItemRemarks = remarksFld.getText();

            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, ItemName);
            preparedStatement.setInt(2,ItemQuantity);
            preparedStatement.setString(3,ItemRemarks);
            preparedStatement.execute();

        } catch (SQLException ex) {
            Logger.getLogger(EditItemController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    void setTextField(int ItemsID, String ItemName,int ItemQuantity, String ItemRemarks) {

        itemsID = ItemsID;
        itemFld.setText(ItemName);
        quantityFld.setPromptText(String.valueOf(ItemQuantity));
        remarksFld.setText(ItemRemarks);


    }

    void setUpdate(boolean b) {
        this.update = b;

    }
}
