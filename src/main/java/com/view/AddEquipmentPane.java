package com.view;

import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;

public class AddEquipmentPane {

    private Scene equipmentScene;

    public AddEquipmentPane() {
        BorderPane bp = new BorderPane();
        equipmentScene = new Scene(bp);
    }

    protected Scene getEquipmentScene() {
        return equipmentScene;
    }


}
