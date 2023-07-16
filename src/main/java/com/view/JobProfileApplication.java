package com.view;

import com.controller.ScreenController;
import javafx.animation.TranslateTransition;
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
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class JobProfileApplication extends Application {

    private Pane root;
    private StackPane mainStackPane;
    private ArrayList<BorderPane> borderPanes;
    private ArrayList<Button> menuButtons;
    private Boolean menuToggle;
    private BorderPane activePane;
    private Stage equipmentStage;
    private ObservableList<String> currentJobEquipment;
    private ViewClientsPane viewClientsPane;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {

        root = new Pane();
        root.setPrefSize(825, 600);

        initMainStackPane();
        initMenu();

        stage.setMinWidth(825);
        stage.setMinHeight(600);
        stage.setScene(new Scene(root));
        stage.setOnCloseRequest(e -> {
            if (equipmentStage != null) {
                equipmentStage.close();
            }
        });

        stage.centerOnScreen();
        stage.show();
    }

    private void initMainStackPane() {
        mainStackPane = new StackPane();
        initBorderPanes();
        mainStackPane.getChildren().addAll(borderPanes);
        borderPanes.get(0).setVisible(true);
        root.getChildren().add(mainStackPane);
    }

    private void initBorderPanes() {
        BorderPane mainBorderPane = new BorderPane();
        BorderPane jobCreateBorderPane = new BorderPane();
        BorderPane summaryBorderPane = new BorderPane();
        BorderPane invoiceBorderPane = new BorderPane();

        viewClientsPane = new ViewClientsPane(root);
        viewClientsPane.back.setOnAction(e -> swapActiveBorderPane("mainPage"));

        BorderPane clientListBorderPane = viewClientsPane.getViewClientsPane();
        BorderPane equipmentListBorderPane = new BorderPane();
        BorderPane jobListBorderPane = new BorderPane();
        BorderPane calendarPane = new BorderPane();

        mainBorderPane.setId("mainPage");
        jobCreateBorderPane.setId("jobCreatePage");
        summaryBorderPane.setId("jobSummaryPage");
        invoiceBorderPane.setId("invoicePage");

        clientListBorderPane.setId("clientListPage");
        equipmentListBorderPane.setId("equipmentListPage");
        jobListBorderPane.setId("jobListPage");
        calendarPane.setId("calendarPage");

        borderPanes = new ArrayList<>(Arrays.asList(
                mainBorderPane, jobCreateBorderPane,
                summaryBorderPane, invoiceBorderPane,
                clientListBorderPane, equipmentListBorderPane,
                jobListBorderPane, calendarPane));
        for (BorderPane b : borderPanes) {
            b.setPadding(new Insets(25, 25, 25, 25));
            b.setVisible(false);
        }
        activePane = mainBorderPane;
        initMainBorderPane(mainBorderPane);
        initJobCreateBorderPane(jobCreateBorderPane);
    }

    private void initPopupWindows() {

    }

    private void initMainBorderPane(BorderPane bp) {
        Label title = new Label("Welcome to ShowGo!");
        Label desc = new Label("I want to:");
        title.setMaxWidth(Double.MAX_VALUE);
        title.setAlignment(Pos.CENTER);
        VBox actions = new VBox(10);
        Button create = new Button("Create a new job.");
        Button open = new Button("Open an existing job.");
        create.setMinWidth(250);
        open.setMinWidth(250);
        create.setOnAction(e -> swapActiveBorderPane("jobCreatePage"));


        actions.getChildren().addAll(desc, create, open);
        actions.setAlignment(Pos.CENTER_LEFT);
        bp.setTop(title);
        bp.setCenter(actions);
        BorderPane.setAlignment(bp, Pos.CENTER);
    }

    private void initJobCreateBorderPane(BorderPane bp) {
        VBox jobInfoBoxes = new VBox(10);
        Label jobNumberLabel = new Label("Job #: "); // TODO: db method of getting job #
        Label jobNameLabel = new Label("Job name:");
        Label jobStatusLabel = new Label("Job status:");
        Button cInfoLink = new Button("Client info");
        Button productionLink = new Button("Production");
        Button techInfoLink = new Button("Tech info");
        Button equipmentLink = new Button("Equipment");
        Button summaryLink = new Button("Summary");
        Button invoiceLink = new Button("Invoice");
        ArrayList<Button> jobCreatePaneButtons = new ArrayList<>(Arrays.asList(
                cInfoLink, productionLink, techInfoLink, equipmentLink, summaryLink, invoiceLink
        ));
        for (Button b : jobCreatePaneButtons) {
            b.setPrefWidth(100);
            b.setMinWidth(100);
        }
        jobInfoBoxes.getChildren().addAll(Arrays.asList(
                jobNumberLabel, jobNameLabel, jobStatusLabel, cInfoLink, productionLink,
                techInfoLink, equipmentLink, summaryLink, invoiceLink
        ));
        bp.setCenter(jobInfoBoxes);
        jobInfoBoxes.setAlignment(Pos.CENTER_LEFT);
        BorderPane.setAlignment(bp, Pos.CENTER);
    }

    private void initJobSummaryBorderPane(BorderPane bp) {

    }

    private void initInvoiceBorderPane(BorderPane bp) {

    }

    private void initMenu() {
        HBox container = new HBox();
        VBox menu = new VBox();
        menu.setId("menu");
        menu.prefHeightProperty().bind(root.heightProperty());
        container.setPrefWidth(180);

        Button toggle = new Button("<");
        initMenuButtons(menu);
        menu.getChildren().addAll(menuButtons);
        toggle.setAlignment(Pos.TOP_CENTER);
        toggle.setStyle(
                "-fx-pref-width: 30;" +
                "-fx-pref-height: 30;" +
                "-fx-background-color: #c9c9c9;" +
                "-fx-background-radius: 0;"
        );

        container.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());

        container.layoutXProperty().bind(root.widthProperty().subtract(30));
        TranslateTransition menuTranslation = new TranslateTransition(Duration.millis(500), container);

        menuTranslation.setFromX(root.widthProperty().doubleValue());
        menuTranslation.setToX(root.widthProperty().doubleValue() - 150);

        menuToggle = true;
        toggle.setOnAction(evt -> {
            if (menuToggle) {
                toggle.setText(">");
                menuTranslation.setRate(1);
                menuTranslation.play();
            } else {
                toggle.setText("<");
                menuTranslation.setRate(-1);
                menuTranslation.play();
            }
            menuToggle = !menuToggle;
        });

        container.getChildren().addAll(toggle, menu);
        root.getChildren().add(container);
    }

    private void initMenuButtons(VBox menu) {
        Button newJobBtn = new Button("New Job");
        Button openJobBtn = new Button("Open Job");
        Button clientListBtn = new Button("Client List");
        Button equipmentBtn = new Button("Equipment");
        Button jobListBtn = new Button("Job List");
        Button calendarBtn = new Button("Calendar");
        Button mainBtn = new Button("Main");

        newJobBtn.setId("jobCreatePageBtn");
        openJobBtn.setId("jobOpenPageBtn");
        clientListBtn.setId("clientListPageBtn");
        equipmentBtn.setId("equipmentListPageBtn");
        jobListBtn.setId("jobListPageBtn");
        calendarBtn.setId("calendarPageBtn");
        mainBtn.setId("mainPageBtn");

        menuButtons = new ArrayList<>(Arrays.asList(
                newJobBtn,
                openJobBtn,
                clientListBtn,
                equipmentBtn,
                jobListBtn,
                calendarBtn,
                mainBtn
        ));

        initButtonHandlers();
    }

    private void initButtonHandlers() {
        for (Button b : menuButtons) {
            b.setOnAction(e -> swapActiveBorderPane(b.getId().substring(0, b.getId().length() - 3)));
        }
    }

    private void swapActiveBorderPane(String setId) {
        BorderPane newActivePane = activePane;
        for (BorderPane bp : borderPanes) {
            if (bp.getId().equals(activePane.getId())) bp.setVisible(false);
            if (bp.getId().equals(setId)) {
                bp.setVisible(true);
                newActivePane = bp;
            }
        }
        activePane = newActivePane;
    }

    private void initInvoiceScene() {

    }

    private void initJobScene() {
//        newJobBorderPane = new BorderPane();
//        VBox optionsVBox = new VBox();
//
//        newJobBorderPane.setPadding(new Insets(25, 25, 25, 25));
//        optionsVBox.setPadding(new Insets(25, 25, 25, 25));
//        optionsVBox.setSpacing(10);
//
//        currentJobEquipment = FXCollections.observableArrayList();
//        // get equipment list for some task somehow
//        ListView<String> equipmentList = new ListView<>(currentJobEquipment);
//
//        VBox textInput = new VBox();
//        HBox buttons = bottom();
//        VBox equipmentListBox = equipmentView();
//        VBox sidebar = jobTypeSelector();
//
//        Label title = new Label("Add job");
//        jobName = new TextField();
//        jobName.setPromptText("Enter the name of the job.");
//        startDate = new DatePicker(LocalDate.now());
//        startTime = new TextField();
//        startTime.setPromptText("Enter a time range.");
//
//        textInput.setAlignment(Pos.CENTER);
//        textInput.setPrefWidth(200);
//        textInput.setSpacing(10);
//
//        jobName.setMaxWidth(textInput.getPrefWidth());
//        startDate.setMaxWidth(textInput.getPrefWidth());
//        startTime.setMaxWidth(textInput.getPrefWidth());
//
//        textInput.getChildren().addAll(jobName, startDate, startTime);
//        optionsVBox.getChildren().addAll(textInput);
//        optionsVBox.setAlignment(Pos.CENTER);
//
//        newJobBorderPane.setTop(title);
//        title.setAlignment(Pos.CENTER);
//        newJobBorderPane.setCenter(optionsVBox);
//        newJobBorderPane.setRight(equipmentListBox);
//        newJobBorderPane.setLeft(sidebar);
//        newJobBorderPane.setBottom(buttons);
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
        equipmentStage.setOnCloseRequest(e -> equipmentStage = null);

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