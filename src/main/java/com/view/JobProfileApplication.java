package com.view;

import com.db.DBInterface;
import javafx.application.Application;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxListCell;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JobProfileApplication extends Application {

    private VBox mainVBox;
    private VBox createInvoiceVBox;
    private VBox newEventVBox;
    private VBox exportCalVBox;
    private VBox viewEventsVBox;
    private VBox adminVBox;
    private ObservableList<String> upcoming;
    private VBox actions;
    private Button createInvoice;
    private Button newEvent;
    private Button exportCal;
    private Button viewEvents;
    private Button admin;
    private Scene mainPage;
    private ScreenController screenController;
    private DBInterface dbInterface;
    private Stage equipmentStage;
    private ObservableList<String> currentJobEquipment;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        mainVBox = new VBox();
        mainVBox.setPadding(new Insets(25,25,25,25));
        mainVBox.setSpacing(10);
        initMainScene();
        initScreenController();
        stage.setMinWidth(200);
        stage.setMinHeight(450);
        stage.setScene(mainPage);
        stage.setOnCloseRequest(e -> {
            if (equipmentStage != null) {
                equipmentStage.close();
            }
        });
        stage.setWidth(720);
        stage.setHeight(720);
        stage.centerOnScreen();
        stage.show();
    }

    private void initMainScene() {
        actions = new VBox();
        actions.setAlignment(Pos.CENTER);
        actions.setPrefWidth(150);
        actions.setSpacing(10);
        upcoming = FXCollections.observableArrayList("a", "b");
        ListView<String> upcomingEvents = new ListView<>(upcoming);
        upcomingEvents.setMinWidth(150);
        initButtons();
        initOtherScenes();
        actions.getChildren().addAll(createInvoice, newEvent, exportCal, viewEvents, admin);
        Label title = new Label("Upcoming jobs");
        mainVBox.getChildren().addAll(title, upcomingEvents, actions);
        mainVBox.setAlignment(Pos.CENTER);
    }

    private void initOtherScenes() {
        initInvoiceScene();
        initEventScene();
        initCalExportScene();
        initEventViewScene();
        initAdminScene();
    }

    private void initInvoiceScene() {
        createInvoiceVBox = new VBox();

        Button back = new Button("Back");
        back.setOnAction(e -> {
            screenController.activate("main");
        });

        createInvoiceVBox.getChildren().addAll(back);
    }

    private void initEventScene() {
        newEventVBox = new VBox();
        HBox dividerHBox = new HBox();
        VBox optionsVBox = new VBox();

        newEventVBox.setPadding(new Insets(25, 25, 25, 25));
        newEventVBox.setSpacing(10);
        dividerHBox.setPadding(new Insets(25, 25, 25, 25));
        dividerHBox.setSpacing(10);
        optionsVBox.setPadding(new Insets(25, 25, 25, 25));
        optionsVBox.setSpacing(10);

        currentJobEquipment = FXCollections.observableArrayList();
        // get equipment list for some task somehow
        ListView<String> equipmentList = new ListView<>(currentJobEquipment);

        VBox textInput = new VBox();
        VBox buttons = new VBox();
        VBox equipmentListBox = new VBox();

        Label title = new Label("Add event");
        TextField eventName = new TextField();
        eventName.setPromptText("Enter the name of the event.");
        DatePicker startDate = new DatePicker(LocalDate.now());
        TextField startTime = new TextField();
        startTime.setPromptText("Enter a time range.");

        textInput.setAlignment(Pos.CENTER);
        buttons.setAlignment(Pos.CENTER);
        equipmentListBox.setAlignment(Pos.CENTER);
        textInput.setPrefWidth(200);
        textInput.setSpacing(10);
        buttons.setPrefWidth(150);
        buttons.setSpacing(10);
        equipmentListBox.setSpacing(10);

        Button save = new Button("Save and add event");
        Button addEquipment = new Button("Add equipment");
        Button back = new Button("Back");
        save.setMinWidth(buttons.getPrefWidth());
        addEquipment.setMinWidth(buttons.getPrefWidth());
        back.setMinWidth(buttons.getPrefWidth());

        save.setOnAction(e -> {
            screenController.activate("main");
        });
        addEquipment.setOnAction(e -> {
            if (equipmentStage == null) {
                addEquipmentStage();
            }
        });
        back.setOnAction(e -> {
            eventName.clear();
            startDate.setValue(LocalDate.now());
            startTime.clear();
            if (equipmentStage != null) {
                equipmentStage.close();
                equipmentStage = null;
            }
            screenController.activate("main");
        });

        textInput.getChildren().addAll(eventName, startDate, startTime);
        buttons.getChildren().addAll(save, addEquipment, back);
        equipmentListBox.getChildren().addAll(new Label("Current equipment"), equipmentList);

        optionsVBox.getChildren().addAll(textInput);
        dividerHBox.getChildren().addAll(optionsVBox, equipmentListBox);
        optionsVBox.setAlignment(Pos.CENTER);
        dividerHBox.setAlignment(Pos.CENTER);
        newEventVBox.setAlignment(Pos.CENTER);

        newEventVBox.getChildren().addAll(title, dividerHBox, buttons);
    }

    private void addEquipmentStage() {
        equipmentStage = new Stage();
        equipmentStage.setMinHeight(400);
        equipmentStage.setMinWidth(250);
        VBox addEquipmentVBox = new VBox();
        addEquipmentVBox.setPadding(new Insets(25, 25, 25, 25));
        addEquipmentVBox.setSpacing(10);

        List<Item> equipmentToAdd = new ArrayList<>();
        List<Item> addedEquipment = new ArrayList<>();

        ObservableList<Item> currentEquipmentObservable = FXCollections.observableArrayList(addedEquipment);
        ListView<Item> currentEquipment = new ListView<>(currentEquipmentObservable);
        ListView<Item> availableEquipment = new ListView<>();
        Button addSelected = new Button("Add selected equipment");
        Button cancel = new Button("Close");

        // this is where the check for available equipment will happen
        for (int i = 0; i < 15; i++) {
            Item item = new Item("Item " + i, false);
            item.onProperty().addListener((obs, wasOn, isNowOn) -> {
                System.out.println(item.getName() + " changed on state from " + wasOn + " to " + isNowOn);
                if (isNowOn && !(equipmentToAdd.contains(item))) {
                    equipmentToAdd.add(item);
                } else {
                    equipmentToAdd.remove(item);
                }
            });
            availableEquipment.getItems().add(item);
        }

        availableEquipment.setCellFactory(CheckBoxListCell.forListView(Item::onProperty));

        addSelected.setOnAction(e -> {
            for (Item item : equipmentToAdd) {
                if (!(currentEquipmentObservable.contains(item))) {
                    currentEquipmentObservable.add(item);
                }
            }
        });
        cancel.setOnAction(e -> {
            equipmentStage.close();
            equipmentStage = null;
        });
        equipmentStage.setOnCloseRequest(e -> {
            equipmentStage = null;
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
        equipmentStage.show();

    }

    private void initCalExportScene() {
        exportCalVBox = new VBox();

        Button back = new Button("Back");
        back.setOnAction(e -> {
            screenController.activate("main");
        });

        exportCalVBox.getChildren().addAll(back);
    }

    private void initEventViewScene() {
        viewEventsVBox = new VBox();

        Button back = new Button("Back");
        back.setOnAction(e -> {
            screenController.activate("main");
        });

        viewEventsVBox.getChildren().addAll(back);
    }

    private void initAdminScene() {
        adminVBox = new VBox();
        adminVBox.setPadding(new Insets(25, 25, 25, 25));
        adminVBox.setSpacing(10);
        adminVBox.setAlignment(Pos.CENTER);

        Button viewEquipment = new Button("View equipment");
        Button newEquipment = new Button("Add new equipment");
        Button back = new Button("Back");

        viewEquipment.setOnAction(e -> {

        });
        newEquipment.setOnAction(e -> {

        });
        back.setOnAction(e -> {
            screenController.activate("main");
        });

        adminVBox.getChildren().addAll(new Label("Administration"), viewEquipment, newEquipment, back);
    }

    private void initScreenController() {
        mainPage = new Scene(mainVBox);
        screenController = new ScreenController(mainPage);
        screenController.addScreen("main", mainVBox);
        screenController.addScreen("invoice", createInvoiceVBox);
        screenController.addScreen("export", exportCalVBox);
        screenController.addScreen("viewEvents", viewEventsVBox);
        screenController.addScreen("admin", adminVBox);
        screenController.addScreen("event", newEventVBox);
    }

    private void initButtons() {
        createInvoice = new Button("Create Invoice");
        newEvent = new Button("New Event");
        exportCal = new Button("Export Calendar");
        viewEvents = new Button("View Events");
        admin = new Button("Administration");

        createInvoice.setMinWidth(actions.getPrefWidth());
        newEvent.setMinWidth(actions.getPrefWidth());
        exportCal.setMinWidth(actions.getPrefWidth());
        viewEvents.setMinWidth(actions.getPrefWidth());
        admin.setMinWidth(actions.getPrefWidth());

        initButtonHandler();
    }

    private void initButtonHandler() {
        createInvoice.setOnAction(e -> {
            screenController.activate("invoice");
        });
        newEvent.setOnAction(e -> {
//            String job = initChoiceDialog();
//            if (job.equals("Video production")) {
//
//            }
//            else if (job.equals("Audio production")) {
//
//            }
//            else if (job.equals("Still photography")) {
//
//            }
//            else {
//
//            }
            screenController.activate("event");
        });
        exportCal.setOnAction(e -> {
            screenController.activate("export");
        });
        viewEvents.setOnAction(e -> {
            //screenController.activate("viewEvents");
            Alert a = new Alert(Alert.AlertType.INFORMATION);
            a.setHeaderText("do stuff!!!");
            a.setContentText("this will probably open a calendar in another window");
            a.show();

        });
        admin.setOnAction(e -> {
            screenController.activate("admin");
        });
    }

    private String initChoiceDialog() {
        List<String> choices = new ArrayList<>();
        choices.add("Video production");
        choices.add("Audio production");
        choices.add("Still photography");

        ChoiceDialog<String> choice = new ChoiceDialog<>("Select", choices);
        choice.setHeaderText("Select job type");
        choice.setContentText("Job:");

        Optional<String> result = choice.showAndWait();
        return result.orElse("invalid");
    }

    public static class Item {
        private final StringProperty name = new SimpleStringProperty();
        private final BooleanProperty on = new SimpleBooleanProperty();

        public Item(String name, boolean on) {
            setName(name);
            setOn(on);
        }

        public final StringProperty nameProperty() {
            return this.name;
        }

        public final String getName() {
            return this.nameProperty().get();
        }

        public final void setName(final String name) {
            this.nameProperty().set(name);
        }

        public final BooleanProperty onProperty() {
            return this.on;
        }

        public final boolean isOn() {
            return this.onProperty().get();
        }

        public final void setOn(final boolean on) {
            this.onProperty().set(on);
        }

        @Override
        public String toString() {
            return getName();
        }

    }

}