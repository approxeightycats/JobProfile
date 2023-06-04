package com.view;

import com.db.DBController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

import java.sql.Array;
import java.util.ArrayList;

public class ViewClientsPane {

    private BorderPane viewClientsPane;
    private DBController dbController;
    private ObservableList<String> clients;
    private ListView<String> clientView;

    ViewClientsPane() {
        viewClientsPane = new BorderPane();
        dbController = new DBController();
        clients = FXCollections.observableArrayList(DBController.getClients());
        clientView = new ListView<>(clients);

//        updateList();

        viewClientsPane.setCenter(clientView);
    }

    private void updateList() {

    }

    protected BorderPane getViewClientsPane() {
        return viewClientsPane;
    }

}
