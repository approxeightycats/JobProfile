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
import javafx.stage.Stage;

import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.Arrays;

public class EquipmentAddNewStage {

    private final Stage equipmentAddNewStage;
    private final EquipmentViewPane tiedPane;
    private ArrayList<TextField> inputText;
    private DatePicker dosPicker;
    private ComboBox<String> statusBox;
    private Button cancel;
    private Button add;

    protected EquipmentAddNewStage(EquipmentViewPane pane) {
        tiedPane = pane;
        equipmentAddNewStage = new Stage();
        equipmentAddNewStage.setMinHeight(400);
        equipmentAddNewStage.setMinWidth(250);
        equipmentAddNewStage.setOnCloseRequest(e -> close());

        BorderPane addEquipmentPane = new BorderPane();
        addEquipmentPane.setPadding(new Insets(25, 25, 25, 25));
        setupTextInput(addEquipmentPane);
        addEquipmentPane.setTop(new Label("Add equipment"));
        initHandlers();

        equipmentAddNewStage.setScene(new Scene(addEquipmentPane));
    }

    private void setupTextInput(BorderPane bp) {
        GridPane stuff = new GridPane();

        Label nameLabel = new Label("Name:");
        Label categoryLabel = new Label("Category:");
        Label serialLabel = new Label("Serial #:");
        Label dos = new Label("Date of service:");
        Label statusLabel = new Label("Status:");
        Label replacementPriceLabel = new Label("Replacement cost ($):");
        Label rentalPriceLabel = new Label("Rental rate:");

        TextField nameField = new TextField();
        TextField categoryField = new TextField();
        TextField serialField = new TextField();
        dosPicker = new DatePicker();
        TextField replacementPriceField = new TextField();
        TextField rentalPriceField = new TextField();

        ObservableList<String> boxOptions = FXCollections.observableArrayList(
                "In service", "Broken");

        statusBox = new ComboBox<>(boxOptions);
        statusBox.getSelectionModel().selectFirst();

        inputText = new ArrayList<>(Arrays.asList(
                nameField, categoryField, serialField,
                replacementPriceField, rentalPriceField));

        cancel = new Button("Cancel");
        add = new Button("Add");

        stuff.addColumn(0,
                nameLabel, categoryLabel, serialLabel, dos,
                statusLabel, replacementPriceLabel, rentalPriceLabel);
        stuff.addColumn(1,
                nameField, categoryField, serialField, dosPicker,
                statusBox, replacementPriceField, rentalPriceField);
        stuff.addRow(7,
                cancel, add);
        stuff.setHgap(10);
        stuff.setVgap(10);
        stuff.setPadding(new Insets(25, 25, 25, 25));

        bp.setCenter(stuff);
    }

    private void initHandlers() {
        cancel.setOnAction(e -> close());
        add.setOnAction(e -> {
            // todo: handle errors
            addEquipment();
            close();
            tiedPane.refreshTable();
        });
    }

    private void addEquipment() {
        String eName = inputText.get(0).getText();
        String category = inputText.get(1).getText();
        String serial = inputText.get(2).getText();
        String dos = dosPicker.getValue().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM));
        String status = statusBox.getValue();
        String replacementPrice = inputText.get(3).getText();
        String rentalPrice = inputText.get(4).getText();

        Equipment toAdd = new Equipment();
        toAdd.setItem(eName);
        toAdd.setDepartment(category);
        toAdd.setSerial(serial);
        toAdd.setDateOfService(dos);
        toAdd.setStatus(status);
        toAdd.setReplacementCost(parseString(replacementPrice));
        toAdd.setRentalCost(parseString(rentalPrice));

        DBController.addEquipment(toAdd);
    }

    private int parseString(String val) {
        String[] split = val.split("\\.");

        // splits a $x.xx into $x and xx, converts to pennies
        int leftInt = Integer.parseInt(split[0]) * 100;
        int rightInt = 0;
        if (split.length > 1) {
            rightInt = Integer.parseInt(split[1]);
        }
        return leftInt + rightInt;
    }

    protected Stage getEquipmentAddNewStage() {
        return equipmentAddNewStage;
    }

    protected void show() {
        equipmentAddNewStage.show();
    }

    protected void close() {

        for (TextField t : inputText) {
            t.clear();
        }
        dosPicker.getEditor().clear();

        equipmentAddNewStage.close();
    }

}
