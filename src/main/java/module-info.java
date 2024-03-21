module org.example.inventorymanagement {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires java.sql;

    requires jfoenix;
    requires de.jensd.fx.glyphs.fontawesome;


    opens org.example.inventorymanagement to javafx.fxml;
    opens Models to javafx.base;
    exports org.example.inventorymanagement;
}