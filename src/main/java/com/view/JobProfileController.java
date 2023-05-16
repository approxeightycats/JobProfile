package com.view;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class JobProfileController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    @FXML
    protected void onOtherButtonClick() { welcomeText.setText("holy hell"); }

}