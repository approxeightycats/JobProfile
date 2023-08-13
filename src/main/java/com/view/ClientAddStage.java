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
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Arrays;

public class ClientAddStage {

    private Stage addClientStage;
    private final BorderPane addClientPane;
    private Button cancelButton;
    private Button submitButton;
    private final ClientViewPane tiedPane;
    private ArrayList<TextField> text;
    private ArrayList<String> newClientInfo;


    ClientAddStage(ClientViewPane tiedPane) {
        addClientStage = new Stage();
        this.tiedPane = tiedPane;

        addClientStage.setMinHeight(430);
        addClientStage.setMinWidth(250);
        addClientPane = new BorderPane();
        addClientPane.setPadding(new Insets(25, 25, 25, 25));

        initScene(addClientPane);
        addClientStage.setScene(new Scene(addClientPane));
    }

    protected void show() {
        addClientStage.show();
    }

    protected void close() {
        for (TextField t : text) {
            t.clear();
        }
        addClientStage.close();
    }

    private void initScene(BorderPane bp) {
        initButtons();
        initText(bp);
        Label t = new Label("Add new client");
        t.setMaxWidth(200);
        t.setAlignment(Pos.CENTER);
        BorderPane.setAlignment(t, Pos.CENTER);
        addClientPane.setTop(t);

    }

    private void initButtons() {
        cancelButton = new Button("Cancel");
        submitButton = new Button("Add new client");
        initHandlers();
    }

    private void initText(BorderPane bp) {
        GridPane gp = new GridPane();

        Label firstNameLabel = new Label("First name:");
        Label lastNameLabel = new Label("Last name:");
        Label titleLabel = new Label("Title:");
        Label orgLabel = new Label("Organization:");
        Label emailLabel = new Label("Email:");
        Label phoneLabel = new Label("Phone:");
        Label address1Label = new Label("Address:");
        Label address2Label = new Label("Address line 2:");
        Label addressCityLabel = new Label("City:");
        Label addressStateLabel = new Label("State:");
        Label addressZipLabel = new Label("Zip:");
        Label contactPrimaryLabel = new Label("Primary contact:");
        Label contactSecondaryLabel = new Label("Secondary contact:");
        Label contactTertiaryLabel = new Label("Other contact:");

        TextField firstName = new TextField();
        TextField lastName = new TextField();
        TextField title = new TextField();
        TextField organization = new TextField();
        TextField emailAddress = new TextField();
        TextField phone = new TextField();
        TextField addressLine1 = new TextField();
        TextField addressLine2 = new TextField();
        TextField addressCity = new TextField();
        TextField addressState = new TextField();
        TextField addressZip = new TextField();
        TextField contactPrimary = new TextField();
        TextField contactSecondary = new TextField();
        TextField contactTertiary = new TextField();

        gp.addColumn(0,
                firstNameLabel, lastNameLabel, titleLabel, emailLabel,
                phoneLabel, orgLabel, contactPrimaryLabel, contactSecondaryLabel,
                contactTertiaryLabel, address1Label, address2Label, addressCityLabel,
                addressStateLabel, addressZipLabel);

        text = new ArrayList<>(Arrays.asList(
                firstName, lastName, title, emailAddress, phone, organization,
                contactPrimary, contactSecondary, contactTertiary,
                addressLine1, addressLine2, addressCity, addressState, addressZip
        ));

        for (int i = 0; i < text.size(); i++) {
            gp.add(text.get(i), 1, i);
        }

        gp.add(cancelButton, 0, text.size());
        gp.add(submitButton, 1, text.size());

        bp.setCenter(gp);
    }

    private void initHandlers() {
        cancelButton.setOnAction(e -> close());
        submitButton.setOnAction(e -> {
            if (checkValidInput()) {
                DBController.addClient(createClient());
                tiedPane.refreshTable();
                close();
            }
        });
    }

    // company name too, not required | one OR the other

    private ArrayList<String> createClient() {
        //TODO: handle formatting
        ArrayList<String> clientInfo = new ArrayList<>();

        for (TextField t : text) {
            clientInfo.add(t.getText());
        }

        return clientInfo;
    }

    private boolean checkValidInput() {

        if (!text.get(0).getText().equals("") && !text.get(1).getText().equals("")) {
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
