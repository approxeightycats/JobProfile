package com.view;

import com.controller.DBController;
import com.controller.ScreenController;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class NavigationBar {

    private static volatile NavigationBar instance;
    private final BorderPane navigationBarPane;
    private ScreenController sc;
    private Button newJob;
    private Button openJob;
    private Button openClientList;
    private Button openEquipment;
    private Button jobList;
    private Button calendar;

    private NavigationBar() {
        navigationBarPane = new BorderPane();
        initBar();
    }

    private void initBar() {
        VBox options = new VBox();
        initButtons();

    }

    private void initButtons() {
        newJob = new Button();
    }

    private void initHandlers() {

    }

    public static NavigationBar getInstance() {
        NavigationBar result = instance;
        if (result != null) {
            return result;
        }
        synchronized (NavigationBar.class) {
            if (instance == null) {
                instance = new NavigationBar();
            }
            return instance;
        }
    }

}
