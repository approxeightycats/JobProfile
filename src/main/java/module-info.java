module view.jobprofile {
    requires javafx.controls;
    requires java.sql;

    opens com.model to javafx.base;

    exports com.view;
    exports com.controller;
}