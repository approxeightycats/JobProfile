package com.view;

import com.db.DBController;
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

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JobProfileApplication extends Application {

    private VBox mainVBox;
    private VBox createInvoiceVBox;
    private BorderPane newJobBorderPane;
    private BorderPane videoBP;
    private BorderPane photoBP;
    private BorderPane audioBP;
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
    private DBController dbController;
    private Stage equipmentStage;
    private ObservableList<String> currentJobEquipment;
    private BorderPane viewClientsPane;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        dbController = new DBController();
        mainVBox = new VBox();
        mainVBox.setPadding(new Insets(25, 25, 25, 25));
        mainVBox.setSpacing(10);
        initMainScene();
        initScreenController();
        stage.setMinWidth(825);
        stage.setMinHeight(450);
        stage.setScene(mainPage);
        stage.setOnCloseRequest(e -> {
            if (equipmentStage != null) {
                equipmentStage.close();
            }
        });
        stage.setWidth(825);
        stage.setHeight(450);
        stage.centerOnScreen();
        stage.show();
    }

    private void initMainScene() {
        actions = new VBox();
        actions.setAlignment(Pos.CENTER);
        actions.setPrefWidth(150);
        actions.setSpacing(10);
        upcoming = FXCollections.observableArrayList("a", "b");
        ListView<String> upcomingJobs = new ListView<>(upcoming);
        upcomingJobs.setMinWidth(150);
        initButtons();
        initOtherScenes();
        actions.getChildren().addAll(createInvoice, newJob, exportCal, viewJobs, admin);
        Label title = new Label("Upcoming jobs");
        mainVBox.getChildren().addAll(title, upcomingJobs, actions);
        mainVBox.setAlignment(Pos.CENTER);
    }

    private void initOtherScenes() {
        initInvoiceScene();
        initJobScene();
        initCalExportScene();
        initJobViewScene();
        initVideoJobOptions();
        initPhotoJobOptions();
        initAudioJobOptions();
        initAdminScene();
    }

    private void initInvoiceScene() {
        createInvoiceVBox = new VBox();

        Button back = new Button("Back");
        back.setOnAction(e -> screenController.activate("main"));

        createInvoiceVBox.getChildren().addAll(back);
    }

    private void initJobScene() {
        newJobBorderPane = new BorderPane();
        VBox optionsVBox = new VBox();

        newJobBorderPane.setPadding(new Insets(25, 25, 25, 25));
        optionsVBox.setPadding(new Insets(25, 25, 25, 25));
        optionsVBox.setSpacing(10);

        currentJobEquipment = FXCollections.observableArrayList();
        // get equipment list for some task somehow
        ListView<String> equipmentList = new ListView<>(currentJobEquipment);

        VBox textInput = new VBox();
        HBox buttons = bottom();
        VBox equipmentListBox = equipmentView();
        VBox sidebar = jobTypeSelector();

        Label title = new Label("Add job");
        jobName = new TextField();
        jobName.setPromptText("Enter the name of the job.");
        startDate = new DatePicker(LocalDate.now());
        startTime = new TextField();
        startTime.setPromptText("Enter a time range.");

        textInput.setAlignment(Pos.CENTER);
        textInput.setPrefWidth(200);
        textInput.setSpacing(10);

        jobName.setMaxWidth(textInput.getPrefWidth());
        startDate.setMaxWidth(textInput.getPrefWidth());
        startTime.setMaxWidth(textInput.getPrefWidth());

        textInput.getChildren().addAll(jobName, startDate, startTime);
        optionsVBox.getChildren().addAll(textInput);
        optionsVBox.setAlignment(Pos.CENTER);

        newJobBorderPane.setTop(title);
        title.setAlignment(Pos.CENTER);
        newJobBorderPane.setCenter(optionsVBox);
        newJobBorderPane.setRight(equipmentListBox);
        newJobBorderPane.setLeft(sidebar);
        newJobBorderPane.setBottom(buttons);
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

    private void initVideoJobOptions() {
        videoBP = new BorderPane();
        videoBP.setPadding(new Insets(25, 25, 25, 25));
        VBox selector = jobTypeSelector();
        VBox equipmentView = equipmentView();
        HBox buttons = bottom();
        GridPane options = new GridPane();
        options.setPadding(new Insets(10, 10, 10, 10));
        options.setHgap(5);
        options.setVgap(5);
        buttons.setPrefWidth(150);

        ComboBox<String> destination = new ComboBox<>();
        ComboBox<String> output = new ComboBox<>();
        ComboBox<String> ratio = new ComboBox<>();
        ComboBox<String> frequency = new ComboBox<>();
        ComboBox<String> resolution = new ComboBox<>();
        ComboBox<String> fps = new ComboBox<>();
        ComboBox<String> location = new ComboBox<>();
        TextField cameras = new TextField();
        cameras.setPromptText("Number of cameras");
        TextField length = new TextField();
        length.setPromptText("Estimated length (hours)");

        options.addRow(0, new Label("Delivery destination:"), destination);
        options.addRow(1, new Label("Output format:"), output);
        options.addRow(2, new Label("Aspect ratio:"), ratio);
        options.addRow(3, new Label("System frequency:"), frequency);
        options.addRow(4, new Label("Resolution:"), resolution);
        options.addRow(5, new Label("FPS:"), fps);
        options.addRow(6, new Label("Indoor/outdoor:"), location);
        options.add(cameras, 0, 7, 2, 1);
        options.add(length, 0, 8, 2, 1);

        cameras.setMinWidth(250);
        length.setMinWidth(250);

        options.setAlignment(Pos.CENTER);
        videoBP.setTop(new Label("Video options."));
        videoBP.setLeft(selector);
        videoBP.setCenter(options);
        videoBP.setRight(equipmentView);
        videoBP.setBottom(buttons);
    }

    private void initPhotoJobOptions() {
        photoBP = new BorderPane();
        photoBP.setPadding(new Insets(25, 25, 25, 25));
        GridPane options = new GridPane();
        options.setHgap(5);
        options.setVgap(5);
        options.setAlignment(Pos.CENTER);
        VBox equipmentView = equipmentView();
        HBox buttons = bottom();
        VBox selector = jobTypeSelector();

        ComboBox<String> destination = new ComboBox<>(); // change to checklist
        ComboBox<String> format = new ComboBox<>();
        ComboBox<String> ratio = new ComboBox<>();
        TextField sizing = new TextField();
        sizing.setPromptText("Other sizing information.");
        TextField PPI = new TextField();
        TextField DPI = new TextField();
        PPI.setPromptText("PPI");
        DPI.setPromptText("DPI");
        ComboBox<String> locations = new ComboBox<>(); // change to checklist
        TextField other = new TextField();
        other.setPromptText("Other information.");

        sizing.setMinWidth(250);
        PPI.setMinWidth(250);
        DPI.setMinWidth(250);
        other.setMinWidth(250);

        options.addRow(0, new Label("Delivery destinations:"), destination);
        options.addRow(1, new Label("Format:"), format);
        options.addRow(2, new Label("Aspect ratio:"), ratio);
        options.add(sizing, 0, 3, 2, 1);
        options.addRow(4, new Label("DPI:"), DPI);
        options.addRow(5, new Label("PPI:"), PPI);
        options.addRow(6, new Label("Locations:"), locations);
        options.add(other, 0, 7, 2, 1);

        photoBP.setTop(new Label("Photo options."));
        photoBP.setLeft(selector);
        photoBP.setRight(equipmentView);
        photoBP.setBottom(buttons);
        photoBP.setCenter(options);

    }

    private void initAudioJobOptions() {
        audioBP = new BorderPane();
        audioBP.setPadding(new Insets(25, 25, 25, 25));
        VBox equipmentView = equipmentView();
        HBox buttons = bottom();
        VBox selector = jobTypeSelector();
        GridPane options = new GridPane();
        options.setHgap(5);
        options.setVgap(5);

        CheckBox liveSound = new CheckBox("Live audio");
        CheckBox recordingLive = new CheckBox("Live recording");
        CheckBox recordingStudio = new CheckBox("Studio recording");
        ComboBox<String> format = new ComboBox<>();
        ComboBox<String> destination = new ComboBox<>();

        options.add(liveSound, 0, 0, 2, 1);
        options.add(recordingLive, 0, 1, 2, 1);
        options.add(recordingStudio, 0, 2, 2, 1);
        options.addRow(3, new Label("Output format:"), format);
        options.addRow(4, new Label("Delivery destination:"), destination);
        options.setAlignment(Pos.CENTER);

        audioBP.setTop(new Label("Audio options."));
        audioBP.setLeft(selector);
        audioBP.setRight(equipmentView);
        audioBP.setBottom(buttons);
        audioBP.setCenter(options);
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
        Button viewClients = new Button("View clients");
        Button addClients = new Button("Add clients");
        Button back = new Button("Back");

        viewEquipment.setOnAction(e -> {

        });
        newEquipment.setOnAction(e -> {

        });
        viewClients.setOnAction(e -> screenController.activate("view clients"));
        addClients.setOnAction(e -> {

        });
        back.setOnAction(e -> screenController.activate("main"));

        adminVBox.getChildren().addAll(new Label("Administration"), viewEquipment, newEquipment, viewClients, addClients, back);
    }

    private void initScreenController() {
        mainPage = new Scene(mainVBox);

        viewClientsPane = new ViewClientsPane().getViewClientsPane();

        screenController = new ScreenController(mainPage);
        screenController.addScreen("main", mainVBox);
        screenController.addScreen("invoice", createInvoiceVBox);
        screenController.addScreen("export", exportCalVBox);
        screenController.addScreen("viewJobs", viewJobsVBox);
        screenController.addScreen("admin", adminVBox);
        screenController.addScreen("job", newJobBorderPane);
        screenController.addScreen("video", videoBP);
        screenController.addScreen("photo", photoBP);
        screenController.addScreen("audio", audioBP);
        screenController.addScreen("view clients", viewClientsPane);
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