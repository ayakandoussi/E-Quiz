module com.projetjava {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;
    requires java.sql;
    requires javafx.base;
    requires javafx.graphics;

    opens com.projetjava to javafx.fxml;
    exports com.projetjava;
    requires jbcrypt;
    requires com.projetjava;
}
