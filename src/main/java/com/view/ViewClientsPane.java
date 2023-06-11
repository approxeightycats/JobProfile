package com.view;

import com.db.DBController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;

public class ViewClientsPane {

    private final BorderPane viewClientsPane;
    private DBController dbController;
    private ObservableList<String> clients;
    private ListView<String> clientView;
    private ScreenController screenController;

    ViewClientsPane(Scene scene) {

        viewClientsPane = new BorderPane();
        dbController = DBController.getInstance();
        clients = FXCollections.observableArrayList(DBController.getEssentialClientData());
        clientView = new ListView<>(clients);
//        updateList();

        screenController = ScreenController.getInstance(scene);

        Button back = new Button("Back");
        back.setOnAction(e -> {
            screenController.activate("main");
        });

        viewClientsPane.setCenter(clientView);
        viewClientsPane.setBottom(back);

    }

    private void updateList() {

    }

    protected BorderPane getViewClientsPane() {
        return viewClientsPane;
    }

}
