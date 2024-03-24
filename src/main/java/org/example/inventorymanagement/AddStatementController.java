package org.example.inventorymanagement;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;

public class AddStatementController {
    public Button btnConfirm;
    public Button btnClose;
    public TextArea txtDesc;
    public TextField txtRemarks;
    public TextField txtOut;
    public TextField txtIn;


    public void setBtnConfirm(ActionEvent event) {

    }

    public void setBtnClose(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }
}