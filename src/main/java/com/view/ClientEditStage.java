package com.view;

import com.controller.DBController;
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
import java.util.Arrays;

public class ClientEditStage {

    private final Stage editClientStage;
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
        GridPane mainEntryPane = new GridPane();
        GridPane addressPane = new GridPane();

//        Label idLabel = new Label("Client ID:");
        Label nameFirstLabel = new Label("First name:");
        Label nameLastLabel = new Label("Last name:");
        Label titleLabel = new Label("Title:");
        Label emailLabel = new Label("Email address:");
        Label phoneLabel = new Label("Phone #:");
        Label addressLine1Label = new Label("Address line 1:");
        Label addressLine2Label = new Label("Address line 2:");
        Label addressStateLabel = new Label("State:");
        Label addressCityLabel = new Label("City:");
        Label addressZipLabel = new Label("ZIP");
        Label orgLabel = new Label("Organization:");
        Label contactPrimaryLabel = new Label("Primary contact:");
        Label contactSecondaryLabel = new Label("Secondary contact:");
        Label contactTertiaryLabel = new Label("Other contact:");

//        TextField idField = new TextField();
        TextField nameFirstField = new TextField(client.getFirstName());
        TextField nameLastField = new TextField(client.getLastName());
        TextField titleField = new TextField(client.getTitle());
        TextField emailField = new TextField(client.getEmail());
        TextField phoneField = new TextField(client.getPhone());
        TextField addressLine1Field = new TextField(client.getAddressLine1());
        TextField addressLine2Field = new TextField(client.getAddressLine2());
        TextField addressStateField = new TextField(client.getAddressState());
        TextField addressCityField = new TextField(client.getAddressCity());
        TextField addressZipField = new TextField(client.getAddressZip());
        TextField organizationField = new TextField(client.getOrg());
        TextField contactPrimaryField = new TextField(client.getContactPrimary());
        TextField contactSecondaryField = new TextField(client.getContactSecondary());
        TextField contactTertiaryField = new TextField(client.getContactTertiary());

        textInput = new ArrayList<>(Arrays.asList(
                nameFirstField, nameLastField, titleField, emailField,
                phoneField, addressLine1Field, addressLine2Field,
                addressStateField, addressCityField, addressZipField,
                organizationField, contactPrimaryField, contactSecondaryField,
                contactTertiaryField
        ));

        addressPane.addRow(0, addressLine1Label, addressLine1Field);
        addressPane.addRow(1, addressLine2Label, addressLine2Field);
        addressPane.addRow(2, addressStateLabel, addressStateField, addressCityLabel, addressCityField);
        addressPane.addRow(3, addressZipLabel, addressZipField);
        GridPane.setColumnSpan(addressPane, 2);

        mainEntryPane.addColumn(0, nameFirstLabel, nameLastLabel, titleLabel, emailLabel, phoneLabel);
        mainEntryPane.addColumn(1, nameFirstField, nameLastField, titleField, emailField, phoneField);
        mainEntryPane.addRow(5, addressPane);
        mainEntryPane.addRow(6, orgLabel, organizationField);
        mainEntryPane.addRow(7, contactPrimaryLabel, contactPrimaryField);
        mainEntryPane.addRow(8, contactSecondaryLabel, contactSecondaryField);
        mainEntryPane.addRow(9, contactTertiaryLabel, contactTertiaryField);
        initButtons();
        mainEntryPane.addRow(10, buttons);
        pane.setCenter(mainEntryPane);
    }

    private void initButtons() {
        buttons = new HBox(10);
        buttons.setAlignment(Pos.CENTER);

        cancelButton = new Button("Cancel");
        submitButton = new Button("Apply changes");
        initHandlers();

        buttons.getChildren().addAll(cancelButton, submitButton);
    }

    private void initHandlers() {
        cancelButton.setOnAction(e -> close());
        submitButton.setOnAction(e -> {
            updateClient(originalClientData, getNewClientData());
            tiedPane.refreshTable();
            close();
        });
    }

    private Client getNewClientData() {
        Client newClient = new Client();

        newClient.setFirstName(textInput.get(0).getText());
        newClient.setLastName(textInput.get(1).getText());
        newClient.setTitle(textInput.get(2).getText());
        newClient.setEmail(textInput.get(3).getText());
        newClient.setPhone(textInput.get(4).getText());
        newClient.setAddressLine1(textInput.get(5).getText());
        newClient.setAddressLine2(textInput.get(6).getText());
        newClient.setAddressCity(textInput.get(7).getText());
        newClient.setAddressState(textInput.get(8).getText());
        newClient.setAddressZip(textInput.get(9).getText());
        newClient.setOrg(textInput.get(10).getText());
        newClient.setContactPrimary(textInput.get(11).getText());
        newClient.setContactSecondary(textInput.get(12).getText());
        newClient.setContactTertiary(textInput.get(13).getText());

        return newClient;
    }

    private void updateClient(Client oldValues, Client newValues) {
        String clientId = oldValues.getClientID();
        DBController.editClientNameFirst(clientId, newValues.getFirstName());
        DBController.editClientNameLast(clientId, newValues.getLastName());
        DBController.editClientTitle(clientId, newValues.getTitle());
        DBController.editClientPhone(clientId, newValues.getPhone());
        DBController.editClientEmail(clientId, newValues.getEmail());
        DBController.editClientAddressLine1(clientId, newValues.getAddressLine1());
        DBController.editClientAddressLine2(clientId, newValues.getAddressLine2());
        DBController.editClientAddressCity(clientId, newValues.getAddressCity());
        DBController.editClientAddressState(clientId, newValues.getAddressState());
        DBController.editClientAddressZip(clientId, newValues.getAddressZip());
        DBController.editClientContactPrimary(clientId, newValues.getContactPrimary());
        DBController.editClientContactSecondary(clientId, newValues.getContactSecondary());
        DBController.editClientContactTertiary(clientId, newValues.getContactTertiary());
        DBController.editClientOrganization(clientId, newValues.getOrg());
    }

    protected void show() {
        editClientStage.show();
    }

    protected void close() {
        editClientStage.close();
    }

}
