module woods.inventoryapp {
    requires javafx.controls;
    requires javafx.fxml;


    opens woods.inventoryapp to javafx.fxml;
    exports woods.inventoryapp;
    exports controller;
    opens controller to javafx.fxml;
    exports model;
    opens model to javafx.base;
}