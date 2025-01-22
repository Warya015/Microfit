module com.example.microfit {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires com.fazecast.jSerialComm;



    opens com.example.microfit to javafx.fxml;
    exports com.example.microfit;
    exports com.example.microfit.LoginController;
    opens com.example.microfit.LoginController to javafx.fxml;



}

