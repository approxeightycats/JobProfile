package com.view;

import com.controller.DBController;
import com.model.Client;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;

public class AddClientStage {

    private Stage addClientStage;
    private final BorderPane addClientPane;
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


    AddClientStage(ViewClientsPane tiedPane) {
        addClientStage = new Stage();
        this.tiedPane = tiedPane;

        addClientStage.setMinHeight(430);
        addClientStage.setMinWidth(250);
        addClientPane = new BorderPane();
        addClientPane.setPadding(new Insets(25, 25, 25, 25));

        initScene();
    }

    protected void showStage() {
        Scene scene = new Scene(addClientPane);
        addClientStage.setScene(scene);
        addClientStage.show();
    }

    protected void closeStage() {
        if (addClientStage != null) {
            addClientStage.close();
            addClientStage = null;
            tiedPane = null;
        }
    }

    private void initScene() {
        initButtons();
        initTextFields();
        Label t = new Label("Add new client");
        t.setMaxWidth(200);
        t.setAlignment(Pos.CENTER);
        BorderPane.setAlignment(t, Pos.CENTER);
        addClientPane.setTop(t);
        addClientPane.setCenter(input);
        addClientPane.setBottom(buttons);

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
        organization = new TextField();
        emailAddress = new TextField();
        phone = new TextField();
        physicalAddress = new TextField();
        contactPrimary = new TextField();

        firstName.setPromptText("First name");
        lastName.setPromptText("Last name");
        title.setPromptText("Title");
        organization.setPromptText("Organization");
        emailAddress.setPromptText("Email address");
        phone.setPromptText("Phone number");
        physicalAddress.setPromptText("Address");
        contactPrimary.setPromptText("Primary contact");

        input.getChildren().addAll(firstName, lastName, title, organization, emailAddress, phone, physicalAddress, contactPrimary);
        ArrayList<TextField> text = new ArrayList<>();
        for (Node n : input.getChildren()) {
            text.add((TextField) n);
        }
        for (TextField t : text) {
            t.setMaxWidth(input.getPrefWidth());
        }
    }

    private void initHandlers() {
        cancelButton.setOnAction(e -> closeStage());
        submitButton.setOnAction(e -> {
            Client c = createClient();
            if (checkValidInput()) {
                DBController.addClientName(c);
                String cid = DBController.getClientID(c.getFirstName(), c.getLastName());
                String[] vals = {
                        c.getFirstName(),
                        c.getLastName(),
                        c.getTitle(),
                        c.getEmail(),
                        c.getPhone(),
                        c.getAddress(),
                        c.getOrg()
                };
                System.out.println(cid);
                DBController.setClientData(vals, cid);
                tiedPane.addClient(c);
            }
        });
    }

    // company name too, not required | one OR the other

    private Client createClient() {
        //TODO: handle formatting
        Client c = new Client();
        c.setFirstName(firstName.getText());
        c.setLastName(lastName.getText());
        c.setTitle(title.getText());
        c.setOrg(organization.getText());
        c.setEmail(emailAddress.getText());
        c.setPhone(phone.getText());
        c.setAddress(physicalAddress.getText());
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
