package com.view;

import com.controller.DBController;
import com.model.Client;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

public class ClientViewPane {

    private final BorderPane viewClientsPane;
    private ObservableList<Client> clients;
    private TableView<Client> tableView;
    protected final Button back;

    ClientViewPane(Pane root) {

        viewClientsPane = new BorderPane();
        viewClientsPane.setPadding(new Insets(25, 25, 25, 25));

        setupTable(root);

        back = new Button("Back");

        Button newClient = new Button("Add new client");
        newClient.setOnAction(e -> {
            ClientAddStage cStage = new ClientAddStage(this);
            cStage.show();
            back.addEventHandler(ActionEvent.ACTION, event -> cStage.close());
        });

        HBox buttons = new HBox(10);
        buttons.getChildren().addAll(back, newClient);
        buttons.setPadding(new Insets(25, 25, 25, 25));
        buttons.setAlignment(Pos.CENTER);

        viewClientsPane.setCenter(tableView);
        BorderPane.setAlignment(tableView, Pos.CENTER);
        viewClientsPane.setBottom(buttons);

    }

    private void setupTable(Pane root) {
        tableView = new TableView<>();
        clients = FXCollections.observableArrayList(DBController.getAllClients());
        tableView.setItems(clients);
        tableView.prefWidthProperty().bind(root.widthProperty().subtract(60));

        TableColumn<Client, String> clientIDCol = new TableColumn<>("Client ID");
        clientIDCol.setCellValueFactory(f -> f.getValue().clientIDProperty());
        clientIDCol.setCellFactory(c -> new TableCell<>() {
            @Override
            protected void updateItem(String val, boolean empty) {
                super.updateItem(val, empty);
                if (val == null || empty) {
                    setText(null);
                }
                else {
                    setText("C" + ("000000" + val).substring(val.length()) );
                }
            }
        });
        TableColumn<Client, String> lastNameCol = new TableColumn<>("Last Name");
        lastNameCol.setCellValueFactory(f -> f.getValue().lastNameProperty());
        TableColumn<Client, String> firstNameCol = new TableColumn<>("First Name");
        firstNameCol.setCellValueFactory(f -> f.getValue().firstNameProperty());
        TableColumn<Client, String> orgNameCol = new TableColumn<>("Organization");
        orgNameCol.setCellValueFactory(f -> f.getValue().orgProperty());
        TableColumn<Client, String> titleCol = new TableColumn<>("Title");
        titleCol.setCellValueFactory(f -> f.getValue().titleProperty());
        TableColumn<Client, String> emailCol = new TableColumn<>("Email");
        emailCol.setCellValueFactory(f -> f.getValue().emailProperty());
        TableColumn<Client, String> phoneCol = new TableColumn<>("Primary phone");
        phoneCol.setCellValueFactory(f -> f.getValue().phoneProperty());
        TableColumn<Client, String> addressCol = new TableColumn<>("Address");
        addressCol.setCellValueFactory(cellData -> Bindings.createStringBinding(
                () -> cellData.getValue().getAddressLine1() + ", " +
                        cellData.getValue().getAddressCity() + ", " +
                        cellData.getValue().getAddressState() + ", " +
                        cellData.getValue().getAddressZip(),
                cellData.getValue().addressLine1Property(),
                cellData.getValue().addressCityProperty(),
                cellData.getValue().addressStateProperty(),
                cellData.getValue().addressZipProperty()
        ));

        tableView.getColumns().setAll(clientIDCol, lastNameCol, firstNameCol, orgNameCol, titleCol, emailCol, phoneCol, addressCol);

        tableView.setRowFactory(
                tableView -> {
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
                });
    }

    private void editClient(Client client) {
        ClientEditStage clientEditStage = new ClientEditStage(this, client);
        clientEditStage.show();
        back.addEventHandler(ActionEvent.ACTION, event -> clientEditStage.close());
    }

    protected void refreshTable() {
        clients.clear();
        clients = FXCollections.observableArrayList(DBController.getAllClients());
        tableView.setItems(clients);
        tableView.refresh();
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
