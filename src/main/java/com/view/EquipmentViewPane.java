package com.view;

import com.controller.DBController;
import com.model.Equipment;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class EquipmentViewPane {

    private final BorderPane viewEquipmentPane;
    private ObservableList<Equipment> equipment;
    private TableView<Equipment> tableView;
    protected final Button back;
    private EquipmentAddNewStage equipmentAddNewStage;
    private EquipmentEditStage equipmentEditStage;


    protected EquipmentViewPane(Pane root) {
        viewEquipmentPane = new BorderPane();
        viewEquipmentPane.setPadding(new Insets(25, 25, 25, 25));

        setupTable(root);

        equipmentAddNewStage = new EquipmentAddNewStage(this);

        back = new Button("Back");
        Button newEquipment = new Button("Add new equipment");
        newEquipment.setOnAction(e -> {
            equipmentAddNewStage.show();
        });

        HBox buttons = new HBox(10);
        buttons.getChildren().addAll(back, newEquipment);
        buttons.setPadding(new Insets(25, 25, 25, 25));
        buttons.setAlignment(Pos.CENTER);

        viewEquipmentPane.setCenter(tableView);
        BorderPane.setAlignment(tableView, Pos.CENTER);
        viewEquipmentPane.setBottom(buttons);
    }

    private void deleteEquipment(Equipment equipment) {
        String toDelete = equipment.getSerial();
        DBController.removeEquipment(toDelete);
        refreshTable();
    }

    private void setupTable(Pane root) {
        tableView = new TableView<>();
        equipment = FXCollections.observableArrayList(DBController.getAllEquipment());
        tableView.setItems(equipment);
        tableView.prefWidthProperty().bind(root.widthProperty().subtract(60));

        TableColumn<Equipment, String> equipmentNameCol = new TableColumn<>("Item");
        equipmentNameCol.setCellValueFactory(f -> f.getValue().itemProperty());
        TableColumn<Equipment, String> equipmentCategoryCol = new TableColumn<>("Department");
        equipmentCategoryCol.setCellValueFactory(f -> f.getValue().departmentProperty());
        TableColumn<Equipment, String> equipmentDOSCol = new TableColumn<>("Date of service");
        equipmentDOSCol.setCellValueFactory(f -> f.getValue().dateOfServiceProperty());
        TableColumn<Equipment, String> equipmentPriceCol = new TableColumn<>("Replacement cost");
        equipmentPriceCol.setCellValueFactory(new PropertyValueFactory<>("replacementCost"));
        TableColumn<Equipment, String> equipmentRateCol = new TableColumn<>("Rental rate");
        equipmentRateCol.setCellValueFactory(new PropertyValueFactory<>("rentalCost"));
        TableColumn<Equipment, String> equipmentStatusCol = new TableColumn<>("Status");
        equipmentStatusCol.setCellValueFactory(f -> f.getValue().statusProperty());
        TableColumn<Equipment, String> equipmentSerialCol = new TableColumn<>("Serial #");
        equipmentSerialCol.setCellValueFactory(f -> f.getValue().serialProperty());

        tableView.getColumns().setAll(equipmentNameCol, equipmentCategoryCol, equipmentDOSCol,
                equipmentPriceCol, equipmentRateCol, equipmentStatusCol, equipmentSerialCol);

        tableView.setRowFactory(
                tableView -> {
                    final TableRow<Equipment> row = new TableRow<>();
                    final ContextMenu rowMenu = new ContextMenu();
                    MenuItem editItem = new MenuItem("Edit");
                    editItem.setOnAction(e -> {
                        System.out.println("edit selected");
                        equipmentEditStage = new EquipmentEditStage(this,
                                tableView.getSelectionModel().getSelectedItem());
                        equipmentEditStage.show();
                    });
                    MenuItem removeItem = new MenuItem("Delete");
                    removeItem.setOnAction(e -> {
                        System.out.println("delete selected");
                        deleteEquipment(tableView.getSelectionModel().getSelectedItem());
                    });
                    MenuItem editSerial = new MenuItem("Edit serial #");
                    editSerial.setOnAction(e -> {
                        editSerialStage(tableView.getSelectionModel().getSelectedItem());
                    });
                    rowMenu.getItems().addAll(editItem, editSerial, removeItem);

                    // only display context menu for non-empty rows:
                    row.contextMenuProperty().bind(
                            Bindings.when(row.emptyProperty())
                                    .then((ContextMenu) null)
                                    .otherwise(rowMenu));
                    return row;
                });
    }

    protected void refreshTable() {
        equipment.clear();
        equipment = FXCollections.observableArrayList(DBController.getAllEquipment());
        tableView.setItems(equipment);
        tableView.refresh();
    }

    protected BorderPane getViewEquipmentPane() {
        return viewEquipmentPane;
    }
    protected ObservableList<Equipment> getEquipmentList() {
        return equipment;
    }

    protected void closeEquipmentStage() {
        equipmentAddNewStage.close();
    }

    protected Stage getEquipmentAddNewStage() {
        return equipmentAddNewStage.getEquipmentAddNewStage();
    }

    private void editSerialStage(Equipment selected) {
        Stage editSerialStage = new Stage();
        editSerialStage.setMinHeight(100);
        editSerialStage.setMinWidth(200);
        BorderPane editSerialPane = new BorderPane();
        editSerialPane.setPadding(new Insets(10, 10, 10, 10));
        TextField serial = new TextField();
        Label l = new Label("New serial: ");
        Button b = new Button("Submit");
        Button c = new Button("Cancel");
        HBox entry = new HBox(10);
        HBox btns = new HBox(10);
        VBox all = new VBox(10);
        entry.getChildren().addAll(l, serial);
        btns.getChildren().addAll(c, b);
        c.setOnAction(e -> editSerialStage.close());
        b.setOnAction(e -> {
            String ser = serial.getText();
            DBController.editEquipmentSerial(ser, selected.getSerial());
            refreshTable();
            editSerialStage.close();
        });
        all.getChildren().addAll(entry, btns);
        editSerialPane.setCenter(all);
        editSerialStage.setScene(new Scene(editSerialPane));
        editSerialStage.show();
    }

}
