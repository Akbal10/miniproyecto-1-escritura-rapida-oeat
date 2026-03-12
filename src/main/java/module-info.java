module com.agredo.escriturarapida {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.agredo.escriturarapida to javafx.fxml;
    exports com.agredo.escriturarapida;
    exports com.agredo.escriturarapida.controller;
    opens com.agredo.escriturarapida.controller to javafx.fxml;
}