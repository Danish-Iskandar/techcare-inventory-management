package org.example.inventorymanagement;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.awt.*;
import java.io.FileInputStream;
import java.io.IOException;

public class MainApp extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("LOGIN.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);

        Image icon = new Image(new FileInputStream("D:\\Coding\\Java\\javafx\\InventoryManagement\\src\\main\\resources\\PNGs\\TECHCARE-LOGO.png"));
        stage.getIcons().add(icon);
        stage.setTitle("Inventory Manager");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch();

    }

}