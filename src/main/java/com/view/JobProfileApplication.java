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

import java.lang.reflect.Array;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class JobProfileApplication extends Application {

    private boolean testingBoolToggle;
    private Pane root;
    private StackPane mainStackPane;
    private ArrayList<BorderPane> borderPanes;
    private ArrayList<Button> menuButtons;
    private BorderPane activePane;
    private VBox exportCalVBox;
    private VBox viewJobsVBox;
    private VBox adminVBox;
    private ObservableList<String> upcoming;
    private VBox actions;
    private Button createInvoice;
    private Button newJob;
    private Button exportCal;
    private Button viewJobs;
    private Button admin;
    private TextField jobName;
    private DatePicker startDate;
    private TextField startTime;
    private Scene mainPage;
    private ScreenController screenController;
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
        mainBorderPane.setId("mainPage");
        jobCreateBorderPane.setId("jobCreatePage");
        summaryBorderPane.setId("jobSummaryPage");
        invoiceBorderPane.setId("invoicePage");
        borderPanes = new ArrayList<>(Arrays.asList(
                mainBorderPane,
                jobCreateBorderPane,
                summaryBorderPane,
                invoiceBorderPane));
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
        VBox menu = new VBox();
        menu.setId("menu");
        menu.prefHeightProperty().bind(root.heightProperty());
        menu.setPrefWidth(150);

        initMenuButtons();
        menu.getChildren().addAll(menuButtons);

        menu.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
        menu.setTranslateX(-140);
        TranslateTransition menuTranslation = new TranslateTransition(Duration.millis(500), menu);

        menuTranslation.setFromX(-140);
        menuTranslation.setToX(0);

        menu.setOnMouseEntered(evt -> {
            menuTranslation.setRate(1);
            menuTranslation.play();
        });
        menu.setOnMouseExited(evt -> {
            menuTranslation.setRate(-1);
            menuTranslation.play();
        });

        root.getChildren().add(menu);
    }

    private void initMenuButtons() {
        Button newJobBtn = new Button("New Job");
        Button openJobBtn = new Button("Open Job");
        Button clientListBtn = new Button("Client List");
        Button equipmentBtn = new Button("Equipment");
        Button jobListBtn = new Button("Job List");
        Button calendarBtn = new Button("Calendar");
        Button mainBtn = new Button("Main");

        newJobBtn.setId("jobCreatePageBtn");
        openJobBtn.setId("jobOpenPageBtn");
        clientListBtn.setId("mainPageBtn");
        equipmentBtn.setId("mainPageBtn");
        jobListBtn.setId("mainPageBtn");
        calendarBtn.setId("mainPageBtn");
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


    private void initOtherScenes() {
        initInvoiceScene();
        initJobScene();
        initCalExportScene();
        initJobViewScene();
        initAdminScene();
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

    private void initCalExportScene() {
        exportCalVBox = new VBox();

        Button back = new Button("Back");
        back.setOnAction(e -> screenController.activate("main"));

        exportCalVBox.getChildren().addAll(back);
    }

    private void initJobViewScene() {
        viewJobsVBox = new VBox();

        Button back = new Button("Back");
        back.setOnAction(e -> screenController.activate("main"));

        viewJobsVBox.getChildren().addAll(back);
    }

    private void initAdminScene() {
        adminVBox = new VBox();
        adminVBox.setPadding(new Insets(25, 25, 25, 25));
        adminVBox.setSpacing(10);
        adminVBox.setAlignment(Pos.CENTER);

        Button viewEquipment = new Button("View equipment");
        Button newEquipment = new Button("Add new equipment");
        Button viewClients = new Button("View & add clients");
        Button back = new Button("Back");

        viewEquipment.setOnAction(e -> {

        });
        newEquipment.setOnAction(e -> {

        });
        viewClients.setOnAction(e -> screenController.activate("view clients"));
        back.setOnAction(e -> screenController.activate("main"));

        adminVBox.getChildren().addAll(new Label("Administration"), viewEquipment, newEquipment, viewClients, back);
    }

    private void initButtons() {
        createInvoice = new Button("Create Invoice");
        newJob = new Button("New Job");
        exportCal = new Button("Export Calendar");
        viewJobs = new Button("View Jobs");
        admin = new Button("Administration");

        createInvoice.setMinWidth(actions.getPrefWidth());
        newJob.setMinWidth(actions.getPrefWidth());
        exportCal.setMinWidth(actions.getPrefWidth());
        viewJobs.setMinWidth(actions.getPrefWidth());
        admin.setMinWidth(actions.getPrefWidth());

        initButtonHandler();
    }

    private void initButtonHandler() {
        createInvoice.setOnAction(e -> screenController.activate("invoice"));
        newJob.setOnAction(e -> {
//            String job = initChoiceDialog();
            screenController.activate("job");
        });
        exportCal.setOnAction(e -> screenController.activate("export"));
        viewJobs.setOnAction(e -> {
            //screenController.activate("viewJobs");
            Alert a = new Alert(Alert.AlertType.INFORMATION);
            a.setHeaderText("do stuff!!!");
            a.setContentText("this will probably open a calendar in another window");
            a.show();

        });
        admin.setOnAction(e -> screenController.activate("admin"));
    }

    private VBox jobTypeSelector() {
        VBox list = new VBox(10);

        Button mainScreen = new Button("Main");
        Button videoOptions = new Button("Video");
        Button photoOptions = new Button("Photos");
        Button audioOptions = new Button("Sound");

        mainScreen.setOnAction(e -> screenController.activate("job"));
        videoOptions.setOnAction(e -> screenController.activate("video"));
        photoOptions.setOnAction(e -> screenController.activate("photo"));
        audioOptions.setOnAction(e -> screenController.activate("audio"));

        list.getChildren().addAll(mainScreen, videoOptions, photoOptions, audioOptions);
        list.setAlignment(Pos.CENTER_LEFT);
        list.setPrefWidth(100);
        mainScreen.setMinWidth(list.getPrefWidth());
        videoOptions.setMinWidth(list.getPrefWidth());
        photoOptions.setMinWidth(list.getPrefWidth());
        audioOptions.setMinWidth(list.getPrefWidth());

        return list;
    }

    private HBox bottom() {
        HBox buttons = new HBox(10);
        buttons.setPrefWidth(150);
        buttons.setAlignment(Pos.CENTER);
        buttons.setPadding(new Insets(25, 25, 0, 25));

        Button save = new Button("Save and add job");
        Button addEquipment = new Button("Add equipment");
        Button back = new Button("Back");
        save.setMinWidth(buttons.getPrefWidth());
        addEquipment.setMinWidth(buttons.getPrefWidth());
        back.setMinWidth(buttons.getPrefWidth());

        buttons.getChildren().addAll(save, addEquipment, back);

        save.setOnAction(e -> screenController.activate("main"));
        addEquipment.setOnAction(e -> {
            if (equipmentStage == null) {
                addEquipmentStage();
            }
        });
        back.setOnAction(e -> {
            jobName.clear();
            startDate.setValue(LocalDate.now());
            startTime.clear();
            if (equipmentStage != null) {
                equipmentStage.close();
                equipmentStage = null;
            }
            screenController.activate("main");
        });

        return buttons;
    }

    private VBox equipmentView() {
        VBox equipmentListBox = new VBox(10);

        Label title = new Label("Current equipment:");
        ListView<String> equipmentList = new ListView<>(currentJobEquipment);

        equipmentListBox.getChildren().addAll(title, equipmentList);
        equipmentListBox.setAlignment(Pos.CENTER);

        return equipmentListBox;
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