module view.jobprofile {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.view to javafx.fxml;
    exports com.view;
}