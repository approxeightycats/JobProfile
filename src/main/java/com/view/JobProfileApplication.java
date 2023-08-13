package com.view;

import com.controller.DBController;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

public class JobProfileApplication extends Application {

    private Pane root;
    private StackPane mainStackPane;
    private ArrayList<BorderPane> borderPanes;
    private ArrayList<Button> menuButtons;
    private Boolean menuToggle;
    private BorderPane activePane;
    private ObservableList<String> currentJobEquipment;
    private ClientViewPane clientViewPane;
    private JobViewPane jobViewPane;
    private EquipmentViewPane equipmentViewPane;
    private JobCreatePane jobCreatePane;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {

        root = new Pane();
        root.setPrefSize(825, 600);

        initMainStackPane();
        initMenu();


        Scene rootScene = new Scene(root);
        stage.setMinWidth(825);
        stage.setMinHeight(600);
        stage.setScene(rootScene);
        stage.setOnCloseRequest(e -> {
            if (equipmentViewPane.getEquipmentAddNewStage() != null) {
                equipmentViewPane.closeEquipmentStage();
            }
        });
        rootScene.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.A) {
                equipmentViewPane.refreshTable();
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
        BorderPane summaryBorderPane = new BorderPane();
        BorderPane invoiceBorderPane = new BorderPane();

        clientViewPane = new ClientViewPane(root);
        clientViewPane.back.setOnAction(e -> swapActiveBorderPane("mainPage"));
        jobViewPane = new JobViewPane(root);
        jobViewPane.back.setOnAction(e -> swapActiveBorderPane("mainPage"));
        equipmentViewPane = new EquipmentViewPane(root);
        equipmentViewPane.back.setOnAction(e -> swapActiveBorderPane("mainPage"));
        jobCreatePane = new JobCreatePane(root);
        jobCreatePane.back.setOnAction(e -> swapActiveBorderPane("mainPage"));

        BorderPane clientListBorderPane = clientViewPane.getViewClientsPane();
        BorderPane equipmentListBorderPane = equipmentViewPane.getViewEquipmentPane();
        BorderPane jobListBorderPane = jobViewPane.getViewJobsPane();
        BorderPane jobCreateBorderPane = jobCreatePane.getCreateJobPane();
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
        create.setOnAction(e -> createJob());
        open.setOnAction(e -> swapActiveBorderPane("jobListPage"));

        actions.getChildren().addAll(desc, create, open);
        actions.setAlignment(Pos.CENTER_LEFT);
        bp.setTop(title);
        bp.setCenter(actions);
        BorderPane.setAlignment(bp, Pos.CENTER);
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
        Button calendarBtn = new Button("Calendar");
        Button mainBtn = new Button("Main");

        newJobBtn.setId("jobCreatePageBtn");
        openJobBtn.setId("jobListPageBtn");
        clientListBtn.setId("clientListPageBtn");
        equipmentBtn.setId("equipmentListPageBtn");
        calendarBtn.setId("calendarPageBtn");
        mainBtn.setId("mainPageBtn");

        menuButtons = new ArrayList<>(Arrays.asList(
                newJobBtn,
                openJobBtn,
                clientListBtn,
                equipmentBtn,
                calendarBtn,
                mainBtn
        ));

        initButtonHandlers();
    }

    private void initButtonHandlers() {
        for (Button b : menuButtons) {
            String sub = b.getId().substring(0, b.getId().length() - 3);
            if (sub.equals("jobCreatePage")) {
                b.setOnAction(e -> createJob());
            }
            else
            {
                b.setOnAction(e -> swapActiveBorderPane(sub));
            }

        }

    }

    private void createJob() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Create new job.");
        alert.setHeaderText("Creating a new job.");
        alert.setContentText("Create a new job?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            DBController.addBlankJob();
            jobViewPane.refreshTable();
            jobCreatePane.getCreateJobPane().setCenter(jobCreatePane.createMainVBox());
            swapActiveBorderPane("jobCreatePage");
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

}