package com.view;

import com.controller.DBController;
import com.controller.ScreenController;
import com.model.Client;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;

import java.util.ArrayList;
import java.util.List;

public class ViewClientsPane {

    private final BorderPane viewClientsPane;
    private DBController dbController;
    private ObservableList<Client> clients;
    private ListView<String> clientView;
    private final ScreenController screenController;
    private TableView<Client> tableView;

    ViewClientsPane(Scene scene) {

        viewClientsPane = new BorderPane();
        viewClientsPane.setPadding(new Insets(25, 25, 25, 25));
        dbController = DBController.getInstance();
//        clients = FXCollections.observableArrayList(DBController.getEssentialClientData());
//        clientView = new ListView<>(clients);
//        updateList();

        setupTable();

        screenController = ScreenController.getInstance(scene);

        Button back = new Button("Back");
        back.setOnAction(e -> {
            screenController.activate("admin");
        });

        viewClientsPane.setCenter(tableView);
        viewClientsPane.setBottom(back);

    }

    private void updateList() {

    }

    private void setupTable() {
        tableView = new TableView<>();
        ArrayList<Client> list = getClients();
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

    private ArrayList<Client> getClients() {
        ArrayList<Client> cList = new ArrayList<>();
        List<String> clientStrings = DBController.getEssentialClientData();

        for (String s : clientStrings) {
            String[] split = s.split(",");
            Client c = new Client();
            c.setClientID(split[0]);
            c.setFirstName(split[1]);
            c.setLastName(split[2]);
            cList.add(c);
        }

        return cList;
    }

    protected BorderPane getViewClientsPane() {
        return viewClientsPane;
    }

}
