package com.view;

import com.model.Client;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;

public class ClientEditStage {

    private final Stage editClientStage;
    private VBox input;
    private HBox buttons;
    private Button cancelButton;
    private Button submitButton;
    ArrayList<TextField> textInput;
    private final ClientViewPane tiedPane;
    private final Client originalClientData;

    ClientEditStage(ClientViewPane pane, Client client) {
        tiedPane = pane;
        originalClientData = client;
        editClientStage = new Stage();
        editClientStage.setMinHeight(800);
        editClientStage.setMinWidth(300);
        editClientStage.setOnCloseRequest(e -> close());

        BorderPane editClientPane = new BorderPane();
        editClientPane.setPadding(new Insets(25, 25, 25, 25));
        initScene(editClientPane, client);

        VBox topLabel = new VBox(5);
        topLabel.getChildren().addAll(
                new Label("Editing selected client."),
                new Label("ID: " + client.getClientID()),
                new Label("Name: " + client.getLastName() + ", " + client.getLastName())
        );
        editClientPane.setTop(topLabel);

        editClientStage.setScene(new Scene(editClientPane));
    }



    private void initScene(BorderPane pane, Client client) {
        GridPane gPane = new GridPane();

        Label idLabel = new Label("Client ID:");
        Label nameFirstLabel = new Label("First name:");
        Label nameLastLabel = new Label("Last name:");
        Label titleLabel = new Label("Title:");
        Label emailLabel = new Label("Email address:");
        Label phoneLabel = new Label("Phone #:");
        Label addressLine1Label = new Label("Address line 1:");
        Label addressLine2Label = new Label("Address line 2:");
        Label addressState;
        Label addressCity;
        Label addressZipCode;
        Label orgLabel = new Label("Organization:");
        Label contactPrimaryLabel = new Label("");
        Label contactSecondaryLabel = new Label("");
        Label contactTertiaryLabel = new Label("");




        initButtons();
        initTextFields();
        Label t = new Label("Editing client");
        t.setMaxWidth(200);
        t.setAlignment(Pos.CENTER);
        BorderPane.setAlignment(t, Pos.CENTER);
        pane.setTop(t);
        pane.setCenter(input);
        pane.setBottom(buttons);
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

        TextField firstName = new TextField(originalClientData.getFirstName());
        TextField lastName = new TextField(originalClientData.getLastName());
        TextField title = new TextField(originalClientData.getTitle());
        TextField organization = new TextField(originalClientData.getOrg());
        TextField emailAddress = new TextField(originalClientData.getEmail());
        TextField phone = new TextField(originalClientData.getPhone());
        TextField physicalAddress = new TextField(originalClientData.getAddress());
        TextField contactPrimary = new TextField();

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
        cancelButton.setOnAction(e -> close());
        submitButton.setOnAction(e -> {
            String clientID = originalClientData.getClientID();
            update(clientID);
            tiedPane.refreshTable();
        });
    }

    private void update(String clientID) {

        String[] vals = {
                originalClientData.getFirstName(),
                originalClientData.getLastName(),
                originalClientData.getTitle(),
                originalClientData.getEmail(),
                originalClientData.getPhone(),
                originalClientData.getAddress(),
                originalClientData.getOrg(),
                clientID
        };

//        DBController.setClientData(vals, clientID);

    }

    protected void show() {
        editClientStage.show();
    }

    protected void close() {
        editClientStage.close();
    }

}
