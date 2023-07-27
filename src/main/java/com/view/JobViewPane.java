package com.view;

import com.controller.DBController;
import com.model.Job;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

public class JobViewPane {

    private final BorderPane viewJobsPane;
    private ObservableList<Job> jobs;
    private TableView<Job> tableView;
    protected final Button back;

    JobViewPane(Pane root) {
        viewJobsPane = new BorderPane();
        viewJobsPane.setPadding(new Insets(25, 25, 25, 25));

        setupTable(root);
        back = new Button("Back");

        Button newJob = new Button("Add new job");
        newJob.setOnAction(e -> {

        });

        HBox buttons = new HBox(10);
        buttons.getChildren().addAll(back, newJob);
        buttons.setPadding(new Insets(25, 25, 25, 25));
        buttons.setAlignment(Pos.CENTER);

        viewJobsPane.setCenter(tableView);
        BorderPane.setAlignment(tableView, Pos.CENTER);
        viewJobsPane.setBottom(buttons);
    }

    protected void refreshTable() {
        jobs.clear();
        jobs = FXCollections.observableArrayList(DBController.getAllJobs());
        tableView.setItems(jobs);
        tableView.refresh();
    }

    protected void addJob() {

    }

    private void editJob() {

    }

    private void deleteJob(Job job) {
        DBController.removeJob(Integer.parseInt(job.getJobId()));
        refreshTable();
    }

    private void setupTable(Pane root) {
        tableView = new TableView<>();
        jobs = FXCollections.observableArrayList(DBController.getAllJobs());
        tableView.setItems(jobs);
        tableView.prefWidthProperty().bind(root.widthProperty().subtract(60));

        tableView.setItems(jobs);
        TableColumn<Job, String> jobIdCol = new TableColumn<>("Job ID");
        jobIdCol.setCellValueFactory(new PropertyValueFactory<>("jobId"));
        TableColumn<Job, String> jobNameCol = new TableColumn<>("Name");
        jobNameCol.setCellValueFactory(new PropertyValueFactory<>("jobName"));
        TableColumn<Job, String> clientNameCol = new TableColumn<>("Client");

        TableColumn<Job, String> clientIdCol = new TableColumn<>("Client ID");
        clientIdCol.setCellValueFactory(new PropertyValueFactory<>("clientId"));
        TableColumn<Job, String> contactCol = new TableColumn<>("Primary Contact");

        TableColumn<Job, String> jobTypesCol = new TableColumn<>("Job Types");

        TableColumn<Job, String> descCol = new TableColumn<>("Description"); //remove?

        TableColumn<Job, String> datesCol = new TableColumn<>("Production Dates");

        TableColumn<Job, String> estimateCol = new TableColumn<>("Estimated cost");

        TableColumn<Job, String> notesCol = new TableColumn<>("Notes"); //remove?

        tableView.getColumns().setAll(jobIdCol, jobNameCol, clientIdCol, clientNameCol,
                contactCol, jobTypesCol, descCol, datesCol, estimateCol, notesCol);

        tableView.setRowFactory(
                tableView -> {
                    final TableRow<Job> row = new TableRow<>();
                    final ContextMenu rowMenu = new ContextMenu();
                    MenuItem openItem = new MenuItem("Open selected");
                    openItem.setOnAction(e -> {
                        System.out.println("opening");
//                            editJob(tableView.getSelectionModel().getSelectedItem());
                    });
                    MenuItem removeItem = new MenuItem("Delete");
                    removeItem.setOnAction(e -> {
                        System.out.println("delete selected");
                        deleteJob(tableView.getSelectionModel().getSelectedItem());
                    });
                    rowMenu.getItems().addAll(openItem, removeItem);

                    // only display context menu for non-empty rows:
                    row.contextMenuProperty().bind(
                            Bindings.when(row.emptyProperty())
                                    .then((ContextMenu) null)
                                    .otherwise(rowMenu));
                    return row;
                });

    }

    public BorderPane getViewJobsPane() {
        return viewJobsPane;
    }

}
