module oscar.lab3 {
    requires javafx.controls;
    requires javafx.fxml;


    opens oscar.lab3 to javafx.fxml;
    exports oscar.lab3;
}