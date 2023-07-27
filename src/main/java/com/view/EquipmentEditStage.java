package com.view;

import com.controller.DBController;
import com.model.Equipment;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.Arrays;

public class EquipmentEditStage {

    private DatePicker dosPicker;
    private ComboBox<String> statusBox;
    private ArrayList<TextField> inputText;
    private Button cancel;
    private Button confirm;
    private EquipmentViewPane tiedPane;
    private Stage equipmentEditStage;
    private Equipment originalEquipmentData;

    protected EquipmentEditStage(EquipmentViewPane pane, Equipment equipment) {
        tiedPane = pane;
        originalEquipmentData = equipment;
        equipmentEditStage = new Stage();
        equipmentEditStage.setMinWidth(250);
        equipmentEditStage.setMinHeight(400);
        equipmentEditStage.setOnCloseRequest(e -> close());

        BorderPane editEquipmentPane = new BorderPane();
        editEquipmentPane.setPadding(new Insets(25, 25, 25, 25));
        setupTextInput(editEquipmentPane, equipment);

        VBox topLabel = new VBox(5);
        topLabel.getChildren().addAll(
                new Label("Editing selected equipment."),
                new Label("Serial: " + equipment.getSerial()));

        editEquipmentPane.setTop(topLabel);
        initHandlers();

        equipmentEditStage.setScene(new Scene(editEquipmentPane));
    }

    private void setupTextInput(BorderPane bp, Equipment equipment) {
        GridPane stuff = new GridPane();

        Label nameLabel = new Label("Name:");
        Label categoryLabel = new Label("Category:");
        Label dos = new Label("Date of service:");
        Label statusLabel = new Label("Status:");
        Label replacementPriceLabel = new Label("Replacement cost:");
        Label rentalPriceLabel = new Label("Rental rate:");

        TextField nameField = new TextField(equipment.getItem());
        TextField categoryField = new TextField(equipment.getDepartment());
        dosPicker = new DatePicker();
        dosPicker.setValue(LocalDate.parse(equipment.getDateOfService(),
                DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)));
        TextField replacementPriceField =
                new TextField(parseInteger(equipment.getReplacementCost()));
        TextField rentalPriceField =
                new TextField(parseInteger(equipment.getRentalCost()));

        ObservableList<String> boxOptions = FXCollections.observableArrayList(
                "In service", "Broken");

        statusBox = new ComboBox<>(boxOptions);
        statusBox.getSelectionModel().select(equipment.getStatus());

        inputText = new ArrayList<>(Arrays.asList(
                nameField, categoryField,
                replacementPriceField, rentalPriceField));

        cancel = new Button("Cancel");
        confirm = new Button("Confirm");

        stuff.addColumn(0,
                nameLabel, categoryLabel, dos,
                statusLabel, replacementPriceLabel, rentalPriceLabel);
        stuff.addColumn(1,
                nameField, categoryField, dosPicker,
                statusBox, replacementPriceField, rentalPriceField);
        stuff.addRow(6,
                cancel, confirm);
        stuff.setHgap(10);
        stuff.setVgap(10);
        stuff.setPadding(new Insets(25, 25, 25, 25));

        bp.setCenter(stuff);
    }

    private void updateEquipment(Equipment oldValues, Equipment newValues) {
        String serial = oldValues.getSerial();

        if (!oldValues.getItem().equals(newValues.getItem())) {
            DBController.editEquipmentItem(newValues.getItem(), serial);
        }
        if (!oldValues.getDepartment().equals(newValues.getDepartment())) {
            DBController.editEquipmentDepartment(newValues.getDepartment(), serial);
        }
        if (!oldValues.getDateOfService().equals(newValues.getDateOfService())) {
            DBController.editEquipmentDoS(newValues.getDateOfService(), serial);
        }
        if (!oldValues.getStatus().equals(newValues.getStatus())) {
            DBController.editEquipmentStatus(newValues.getStatus(), serial);
        }
        if (!oldValues.getReplacementCost().equals(newValues.getReplacementCost())) {
            DBController.editEquipmentReplacementCost(parseInteger(newValues.getReplacementCost()), serial);
        }
        if (!oldValues.getRentalCost().equals(newValues.getRentalCost())) {
            DBController.editEquipmentRentalRate(parseInteger(newValues.getRentalCost()), serial);
        }

    }

    private Equipment getNewEquipmentData() {
        Equipment newEquipment = new Equipment();

        newEquipment.setItem(inputText.get(0).getText());
        newEquipment.setDepartment(inputText.get(1).getText());
        newEquipment.setDateOfService(
                dosPicker.getValue().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)));
        newEquipment.setStatus(statusBox.getValue());
        newEquipment.setReplacementCost(parseString(inputText.get(2).getText()));
        newEquipment.setRentalCost(parseString(inputText.get(3).getText()));

        return newEquipment;
    }

    private String parseInteger(Integer val) {
        int left = val / 100;
        String right = String.format("%02d", val % 100);
        return left + "." + right;
    }

    private Integer parseString(String val) {
        String[] split = val.split("\\.");

        // splits an $x.xx into $x and xx, converts to pennies
        int leftInt = Integer.parseInt(split[0]) * 100;
        int rightInt = 0;
        if (split.length > 1) {
            rightInt = Integer.parseInt(split[1]);
        }
        return leftInt + rightInt;
    }

    private void initHandlers() {
        cancel.setOnAction(e -> close());
        confirm.setOnAction(e -> {
            updateEquipment(originalEquipmentData, getNewEquipmentData());
            close();
            tiedPane.refreshTable();
        });
    }

    protected void close() {
        equipmentEditStage.close();
    }

    protected void show() {
        equipmentEditStage.show();
    }

}
