module view.jobprofile {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires com.calendarfx.view;


    opens com.view to javafx.fxml;
    exports com.view;
}