package com.view;

import com.model.Equipment;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.cell.CheckBoxListCell;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class EquipmentAddToJobStage {

    private Stage equipmentStage;

    public EquipmentAddToJobStage(List<Equipment> equipmentList) {
        equipmentStage = new Stage();
        equipmentStage.setMinHeight(400);
        equipmentStage.setMinWidth(250);
        VBox addEquipmentVBox = new VBox();
        addEquipmentVBox.setPadding(new Insets(25, 25, 25, 25));
        addEquipmentVBox.setSpacing(10);

        List<Equipment> equipmentToAdd = new ArrayList<>();

        ObservableList<Equipment> currentEquipmentObservable = FXCollections.observableArrayList(equipmentList);
        ListView<Equipment> currentEquipment = new ListView<>(currentEquipmentObservable);
        ListView<Equipment> availableEquipment = new ListView<>();
        Button addSelected = new Button("Add selected equipment");
        Button cancel = new Button("Close");

        // this is where the check for available equipment will happen
        for (int i = 0; i < equipmentList.size(); i++) {
            Equipment item = new Equipment();
            item.setItem("item " + i);
            item.toggleProperty().addListener((obs, wasOn, isNowOn) -> {
                System.out.println(item.getItem() + " changed on state from " + wasOn + " to " + isNowOn);
                if (isNowOn && !(equipmentToAdd.contains(item))) {
                    equipmentToAdd.add(item);
                } else {
                    equipmentToAdd.remove(item);
                }
            });
            availableEquipment.getItems().add(item);
        }

        availableEquipment.setCellFactory(CheckBoxListCell.forListView(Equipment::toggleProperty));

        addSelected.setOnAction(e -> {
            for (Equipment item : equipmentToAdd) {
                if (!(currentEquipmentObservable.contains(item))) {
                    currentEquipmentObservable.add(item);
                }
            }
        });
        cancel.setOnAction(e -> {
            equipmentStage.close();
        });

        VBox curEquip = new VBox(10);
        VBox avaEquip = new VBox(10);
        VBox buttons = new VBox(10);
        curEquip.setMinWidth(150);
        avaEquip.setPrefWidth(250);
        buttons.setPrefWidth(200);
        curEquip.setAlignment(Pos.CENTER);
        avaEquip.setAlignment(Pos.CENTER);
        buttons.setAlignment(Pos.CENTER);
        addSelected.setMinWidth(buttons.getPrefWidth());
        cancel.setMinWidth(buttons.getPrefWidth());
        curEquip.getChildren().addAll(new Label("Current equipment"), currentEquipment);
        avaEquip.getChildren().addAll(new Label("Available equipment"), availableEquipment);
        buttons.getChildren().addAll(addSelected, cancel);

        addEquipmentVBox.getChildren().addAll(curEquip, avaEquip, buttons);
        addEquipmentVBox.setAlignment(Pos.CENTER);

        Scene equipmentStageScene = new Scene(addEquipmentVBox, 400, 650);
        equipmentStage.setScene(equipmentStageScene);
    }

    protected void showStage() {
        equipmentStage.show();
    }

    protected Stage getEquipmentStage() {
        return equipmentStage;
    }

}
