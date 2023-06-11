package com.view;

import com.controller.DBController;
import com.model.Client;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AddClientStage {

    private Stage clientStage;
    private BorderPane clientPane;
    private VBox input;
    private HBox buttons;
    private Button cancelButton;
    private Button submitButton;
    private TextField firstName;
    private TextField lastName;
    private TextField title;
    private TextField emailAddress;
    private TextField phone;
    private TextField physicalAddress;
    private TextField contactPrimary;


    AddClientStage() {
        clientStage = new Stage();

        clientStage.setMinHeight(400);
        clientStage.setMinWidth(250);
        clientPane = new BorderPane();
        clientPane.setPadding(new Insets(25, 25, 25, 25));

        initScene();
    }

    protected void showStage() {
        Scene scene = new Scene(clientPane);
        clientStage.setScene(scene);
        clientStage.show();
    }

    protected void closeStage() {
        if (clientStage != null) {
            clientStage.close();
            clientStage = null;
        }
    }

    private void initScene() {
        initButtons();
        initTextFields();
        Label t = new Label("Add a client");
        t.setMaxWidth(200);
        t.setAlignment(Pos.CENTER);
        BorderPane.setAlignment(t, Pos.CENTER);
        clientPane.setTop(t);
        clientPane.setCenter(input);
        clientPane.setBottom(buttons);

    }

    private void initButtons() {
        buttons = new HBox(10);
        buttons.setAlignment(Pos.CENTER);

        cancelButton = new Button("Cancel");
        submitButton = new Button("Add new client");
        initHandlers();

        buttons.getChildren().addAll(cancelButton, submitButton);
    }

    private void initTextFields() {
        input = new VBox(10);
        input.setPadding(new Insets(25, 25, 25, 25));
        input.setAlignment(Pos.CENTER);
        input.setPrefWidth(200);

        firstName = new TextField();
        lastName = new TextField();
        title = new TextField();
        emailAddress = new TextField();
        phone = new TextField();
        physicalAddress = new TextField();
        contactPrimary = new TextField();

        firstName.setPromptText("First name");
        lastName.setPromptText("Last name");
        title.setPromptText("Title");
        emailAddress.setPromptText("Email address");
        phone.setPromptText("Phone number");
        physicalAddress.setPromptText("Address");
        contactPrimary.setPromptText("Primary contact");

        firstName.setMaxWidth(input.getPrefWidth());
        lastName.setMaxWidth(input.getPrefWidth());
        title.setMaxWidth(input.getPrefWidth());
        emailAddress.setMaxWidth(input.getPrefWidth());
        phone.setMaxWidth(input.getPrefWidth());
        physicalAddress.setMaxWidth(input.getPrefWidth());
        contactPrimary.setMaxWidth(input.getPrefWidth());

        input.getChildren().addAll(firstName, lastName, title, emailAddress, phone, physicalAddress, contactPrimary);
    }

    private void initHandlers() {
        cancelButton.setOnAction(e -> {
            closeStage();
        });
        submitButton.setOnAction(e -> {
            if (checkValidInput()){
                DBController.addClient(createClient());
            }
        });
    }

    private Client createClient() {
        Client c = new Client();
        c.setFirstName(firstName.getText());
        c.setLastName(lastName.getText());
        return c;
    }

    private boolean checkValidInput() {

        if (!firstName.getText().equals("") && !lastName.getText().equals("")) {
            return true;
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("Invalid input.");
            alert.setContentText("Please enter a valid first/last name.");
            alert.showAndWait();
            return false;
        }
    }

}
