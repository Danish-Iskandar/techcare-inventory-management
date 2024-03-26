package org.example.inventorymanagement;

import javafx.application.Platform;
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
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;

public class AddUtensilController implements Initializable {
    public Button btnOk;
    public Button btnCancel;
    public TextField txtName;
    public TextField txtRemarks;
    public Spinner numberPicker;
    @FXML private Label currentTime;
    Connection connection = null ;

    public void setBtnCancel(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("INVENTORY.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load(), 854, 480);
        stage.setScene(scene);
        stage.show();
    }
    private void timeNow() {
        Thread thread = new Thread(() -> {
            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
            try {
                while (true) { // Infinite loop
                    Thread.sleep(1000);
                    final String timeNow = timeFormat.format(new java.util.Date());
                    Platform.runLater(() -> {
                        currentTime.setText(timeNow);
                    });
                }
            } catch (InterruptedException e) {
                // Thread interrupted, do nothing
            }
        });
        thread.start(); // Start the thread
    }

    public void addUtensil(ActionEvent event) throws IOException{

        if (txtName.getText().isBlank() || numberPicker.getValue().toString().isBlank()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setHeaderText("Fill in the information!");
            alert.showAndWait();
        }else{
            saveUtensil();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("STATUS UPDATED");
            alert.setHeaderText("Status updated! Please reload the page to update the table.");
            alert.showAndWait();
            setBtnCancel(event);
        }
    }

    private void saveUtensil(){
        connection = DBConnect.getConnect();

        String UtensilName = txtName.getText();
        Integer UtensilQuantity = (int) numberPicker.getValue();
        String UtensilRemarks;
        if (txtRemarks.getText().isBlank()){
            UtensilRemarks = "";
        }else{
            UtensilRemarks=  txtRemarks.getText();
        }
        try (PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO utensillist (UtensilName, UtensilQuantity, UtensilRemarks) VALUES (?,?,?)")) {
            preparedStatement.setString(1, UtensilName);
            preparedStatement.setInt(2, UtensilQuantity);
            preparedStatement.setString(3, UtensilRemarks);


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

