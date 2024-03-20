module org.example.inventorymanagement {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires java.sql;


    opens org.example.inventorymanagement to javafx.fxml;
    exports org.example.inventorymanagement;
}