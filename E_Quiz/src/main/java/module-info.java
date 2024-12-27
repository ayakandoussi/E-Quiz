module com.projetjava {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;
    requires java.sql;

    opens com.projetjava to javafx.fxml;
    exports com.projetjava;
}
