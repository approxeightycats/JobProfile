package com.view;

import com.controller.DBController;
import com.model.Client;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;

public class EditClientStage {

    private Stage editClientStage;
    private final BorderPane editClientPane;
    private VBox input;
    private HBox buttons;
    private Button cancelButton;
    private Button submitButton;
    private TextField firstName;
    private TextField lastName;
    private TextField title;
    private TextField organization;
    private TextField emailAddress;
    private TextField phone;
    private TextField physicalAddress;
    private TextField contactPrimary;
    private ViewClientsPane tiedPane;
    private Client client;

    EditClientStage(ViewClientsPane tiedPane, Client client) {
        editClientStage = new Stage();
        this.tiedPane = tiedPane;
        this.client = client;

        editClientStage.setMinHeight(430);
        editClientStage.setMinWidth(250);
        editClientPane = new BorderPane();
        editClientPane.setPadding(new Insets(25, 25, 25, 25));

        initScene();
    }

    protected void showStage() {
        Scene scene = new Scene(editClientPane);
        editClientStage.setScene(scene);
        editClientStage.show();
    }

    protected void closeStage() {
        if (editClientStage != null) {
            editClientStage.close();
            editClientStage = null;
            client = null;
            tiedPane = null;
        }
    }

    private void initScene() {
        initButtons();
        initTextFields();
        Label t = new Label("Editing client");
        t.setMaxWidth(200);
        t.setAlignment(Pos.CENTER);
        BorderPane.setAlignment(t, Pos.CENTER);
        editClientPane.setTop(t);
        editClientPane.setCenter(input);
        editClientPane.setBottom(buttons);
    }

    private void initButtons() {
        buttons = new HBox(10);
        buttons.setAlignment(Pos.CENTER);

        cancelButton = new Button("Cancel");
        submitButton = new Button("Apply changes");
        initHandlers();

        buttons.getChildren().addAll(cancelButton, submitButton);
    }

    private void initTextFields() {
        input = new VBox(10);
        input.setPadding(new Insets(25, 25, 25, 25));
        input.setAlignment(Pos.CENTER);
        input.setPrefWidth(200);

        firstName = new TextField(client.getFirstName());
        lastName = new TextField(client.getLastName());
        title = new TextField(client.getTitle());
        organization = new TextField(client.getOrg());
        emailAddress = new TextField(client.getEmail());
        phone = new TextField(client.getPhone());
        physicalAddress = new TextField(client.getAddress());
        contactPrimary = new TextField();

        firstName.setMaxWidth(input.getPrefWidth());
        lastName.setMaxWidth(input.getPrefWidth());
        title.setMaxWidth(input.getPrefWidth());
        organization.setMaxWidth(input.getPrefWidth());
        emailAddress.setMaxWidth(input.getPrefWidth());
        phone.setMaxWidth(input.getPrefWidth());
        physicalAddress.setMaxWidth(input.getPrefWidth());
        contactPrimary.setMaxWidth(input.getPrefWidth());

        input.getChildren().addAll(firstName, lastName, title, organization, emailAddress, phone, physicalAddress, contactPrimary);
    }

    private void initHandlers() {
        cancelButton.setOnAction(e -> closeStage());
        submitButton.setOnAction(e -> {
            String clientID = client.getClientID();
            update(clientID);
            tiedPane.updateClientView(client);
        });
    }

    private void update(String clientID) {
        if (!firstName.getText().equals(client.getFirstName()))
            DBController.setClientData("FIRST_NAME", firstName.getText(), clientID);
        if (!lastName.getText().equals(client.getLastName()))
            DBController.setClientData("LAST_NAME", lastName.getText(), clientID);
        if (!title.getText().equals(client.getTitle()))
            DBController.setClientData("TITLE", title.getText(), clientID);
        if (!organization.getText().equals(client.getOrg()))
            DBController.setClientData("ORGANIZATION", organization.getText(), clientID);
        if (!emailAddress.getText().equals(client.getEmail()))
            DBController.setClientData("EMAIL", emailAddress.getText(), clientID);
        if (!phone.getText().equals(client.getPhone()))
            DBController.setClientData("PHONE", phone.getText(), clientID);
//        if (!contactPrimary.getText().equals(client.getPrimaryContact()))
//            DBController.setClientData("", placeholder.getText(), clientID);
    }

}
