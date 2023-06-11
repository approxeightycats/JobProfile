package com.view;

import com.controller.DBController;
import com.controller.ScreenController;
import com.model.Client;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

import java.util.ArrayList;

public class ViewClientsPane {

    private final BorderPane viewClientsPane;
    private DBController dbController;
    private ObservableList<Client> clients;
    private final ScreenController screenController;
    private TableView<Client> tableView;

    ViewClientsPane(Scene scene) {

        viewClientsPane = new BorderPane();
        viewClientsPane.setPadding(new Insets(25, 25, 25, 25));
        dbController = DBController.getInstance();

        setupTable();

        screenController = ScreenController.getInstance(scene);

        Button back = new Button("Back");
        back.setOnAction(e -> {
            screenController.activate("admin");
        });
        Button newClient = new Button("Add new client");
        newClient.setOnAction(e -> {
            AddClientStage cStage = new AddClientStage();
            cStage.showStage();
            back.addEventHandler(ActionEvent.ACTION, event -> cStage.closeStage());
        });

        HBox buttons = new HBox(10);
        buttons.getChildren().addAll(back, newClient);

        viewClientsPane.setCenter(tableView);
        viewClientsPane.setBottom(buttons);


    }

    private void updateList() {

    }

    private void setupTable() {
        tableView = new TableView<>();
        ArrayList<Client> list = DBController.getAllClients();
        clients = FXCollections.observableArrayList(list);
        tableView.setItems(clients);

        TableColumn<Client, String> clientIDCol = new TableColumn<>("Client ID");
        clientIDCol.setCellValueFactory(new PropertyValueFactory<>("clientID"));
        TableColumn<Client, String> lastNameCol = new TableColumn<>("Last Name");
        lastNameCol.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        TableColumn<Client, String> firstNameCol = new TableColumn<>("First Name");
        firstNameCol.setCellValueFactory(new PropertyValueFactory<>("firstName"));

        tableView.getColumns().setAll(clientIDCol, lastNameCol, firstNameCol);
    }

    protected BorderPane getViewClientsPane() {
        return viewClientsPane;
    }

}
