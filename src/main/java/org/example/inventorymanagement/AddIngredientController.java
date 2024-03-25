package org.example.inventorymanagement;

import javafx.event.ActionEvent;
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
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AddIngredientController implements Initializable {
    public Button btnOk;
    public Button btnCancel;
    public TextField txtName;
    public TextField txtRemarks;
    public Spinner numberPicker;
    Connection connection = null ;

    public void setBtnCancel(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("INVENTORY.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setScene(scene);
        stage.show();
    }


    public void addIngredient(ActionEvent event) throws IOException{

        if (txtName.getText().isBlank() || numberPicker.getValue().toString().isBlank()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setHeaderText("Fill in the information!");
            alert.showAndWait();
        }else{
            saveIngredient();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("STATUS UPDATED");
            alert.setHeaderText("Status updated! Please reload the page to update the table.");
            alert.showAndWait();
            setBtnCancel(event);
        }
    }

    private void saveIngredient(){
        connection = DBConnect.getConnect();

        String IngredientName = txtName.getText();
        Integer IngredientQuantity = (int) numberPicker.getValue();
        String IngredientRemarks;
        if (txtRemarks.getText().isBlank()){
            IngredientRemarks = "";
        }else{
            IngredientRemarks=  txtRemarks.getText();
        }
        try (PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO ingredientlist (IngredientName, IngredientQuantity, IngredientRemarks) VALUES (?,?,?)")) {
            preparedStatement.setString(1, IngredientName);
            preparedStatement.setInt(2, IngredientQuantity);
            preparedStatement.setString(3, IngredientRemarks);


            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {

            } else {
                System.out.println("Failed to submit statement. Please try again.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void initialize(URL url, ResourceBundle resourceBundle) {
        numberPicker.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 0, 1));

        numberPicker.getEditor().textProperty().addListener((obs, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                numberPicker.getEditor().setText(newValue.replaceAll("[^\\d]", ""));
            }
        });

        // Set up a StringConverter to convert text to integer
        numberPicker.getValueFactory().setConverter(new StringConverter<Integer>() {
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
}
