module org.example.inventorymanagement {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.inventorymanagement to javafx.fxml;
    exports org.example.inventorymanagement;
}