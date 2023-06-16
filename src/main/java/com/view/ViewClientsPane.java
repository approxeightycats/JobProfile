package com.view;

import com.controller.DBController;
import com.controller.ScreenController;
import com.model.Client;
import javafx.beans.binding.Bindings;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.util.Callback;

import java.util.ArrayList;

public class ViewClientsPane {

    private final BorderPane viewClientsPane;
    private ObservableList<Client> clients;
    private final ScreenController screenController;
    private TableView<Client> tableView;
    private Button back;

    ViewClientsPane(Scene scene) {

        viewClientsPane = new BorderPane();
        viewClientsPane.setPadding(new Insets(25, 25, 25, 25));

        setupTable();

        screenController = ScreenController.getInstance(scene);

        back = new Button("Back");
        back.setOnAction(e -> {
            screenController.activate("admin");
        });
        Button newClient = new Button("Add new client");
        newClient.setOnAction(e -> {
            AddClientStage cStage = new AddClientStage(this);
            cStage.showStage();
            back.addEventHandler(ActionEvent.ACTION, event -> cStage.closeStage());
        });

        HBox buttons = new HBox(10);
        buttons.getChildren().addAll(back, newClient);

        viewClientsPane.setCenter(tableView);
        viewClientsPane.setBottom(buttons);

    }

    protected void addClient(Client client) {
        client.setClientID(DBController.getClientID(client.getFirstName(), client.getLastName()));
        clients.add(client);
        tableView.refresh();
    }

    private void setupTable() {
        tableView = new TableView<>();
        clients = FXCollections.observableArrayList(DBController.getAllClients());
        tableView.setItems(clients);

        TableColumn<Client, String> clientIDCol = new TableColumn<>("Client ID");
        clientIDCol.setCellValueFactory(new PropertyValueFactory<>("clientID"));
        TableColumn<Client, String> lastNameCol = new TableColumn<>("Last Name");
        lastNameCol.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        TableColumn<Client, String> firstNameCol = new TableColumn<>("First Name");
        firstNameCol.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        TableColumn<Client, String> orgNameCol = new TableColumn<>("Organization");
        orgNameCol.setCellValueFactory(new PropertyValueFactory<>("org"));
        TableColumn<Client, String> titleCol = new TableColumn<>("Title");
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        TableColumn<Client, String> emailCol = new TableColumn<>("Email");
        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
        TableColumn<Client, String> phoneCol = new TableColumn<>("Primary phone");
        phoneCol.setCellValueFactory(new PropertyValueFactory<>("phone"));
        TableColumn<Client, String> addressCol = new TableColumn<>("Address");
        addressCol.setCellValueFactory(new PropertyValueFactory<>("address"));

        tableView.getColumns().setAll(clientIDCol, lastNameCol, firstNameCol, orgNameCol, titleCol, emailCol, phoneCol, addressCol);

        tableView.setRowFactory(
                new Callback<TableView<Client>, TableRow<Client>>() {
                    public TableRow<Client> call(TableView<Client> tableView) {
                        final TableRow<Client> row = new TableRow<>();
                        final ContextMenu rowMenu = new ContextMenu();
                        MenuItem editItem = new MenuItem("Edit");
                        editItem.setOnAction(e -> {
                            System.out.println("edit selected");
                            editClient(tableView.getSelectionModel().getSelectedItem());
                        });
                        MenuItem removeItem = new MenuItem("Delete");
                        removeItem.setOnAction(e -> {
                            System.out.println("delete selected");
                            deleteClient(tableView.getSelectionModel().getSelectedItem());
                        });
                        rowMenu.getItems().addAll(editItem, removeItem);

                        // only display context menu for non-empty rows:
                        row.contextMenuProperty().bind(
                                Bindings.when(row.emptyProperty())
                                        .then((ContextMenu) null)
                                        .otherwise(rowMenu));
                        return row;
                    }
                });
    }

    private void editClient(Client client) {
        EditClientStage editClientStage = new EditClientStage(this, client);
        editClientStage.showStage();
        back.addEventHandler(ActionEvent.ACTION, event -> editClientStage.closeStage());
    }

    protected void updateClientView(Client client) {
        for (Client c : clients) {
            if (c.getClientID().equals(client.getClientID())) {
                clients.set(clients.indexOf(c), client);
                tableView.refresh();
                return;
            }
        }
    }

    private void deleteClient(Client client) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("Deleting client.");
        alert.setContentText("Are you sure you want to delete the following client?\n" +
                client.getLastName() + ", " + client.getFirstName());

        alert.showAndWait()
                .filter(response -> response == ButtonType.OK)
                .ifPresent(response -> {
                    DBController.removeClient(client.getClientID());
                    clients.remove(client);
                    tableView.refresh();
        });
    }

    protected BorderPane getViewClientsPane() {
        return viewClientsPane;
    }

}
